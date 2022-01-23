package com.github.nagatosingle.utils.minio;


import com.alibaba.fastjson.JSON;
import com.github.nagatosingle.constants.ResponseMessage;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static com.github.nagatosingle.controller.BaseController.returnStatement;

@RestController
@RequestMapping("/minio")
@AllArgsConstructor
@Slf4j
public class MinioController {
	private final MinioClient client;


	@GetMapping("/list")
	public ResponseEntity<?> list(@RequestParam("name") String MINIO_BUCKET_NAME) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
		Iterable<Result<Item>> objs = client.listObjects(ListObjectsArgs.builder().bucket(MINIO_BUCKET_NAME).build());
		Iterator<Result<Item>> iterator = objs.iterator();
		List<Object> items = new ArrayList<>();
		String format = "{'fileName':'%s','fileSize':'%s'}";

		while (iterator.hasNext()) {
			Item item = iterator.next().get();
			items.add(JSON.parse(String.format(format, item.objectName(), item.size())));
		}

		return returnStatement(new NagatoResponseEntity()
				.data(items)
				.message(ResponseMessage.OK)
		);
	}


	public ResponseEntity<?> putObject(MultipartFile[] images) {
		List<String> uuidList = new ArrayList<>();
		try {
			log.info(images.length + "image length");
			for (MultipartFile image : images) {
				byte[] bytes = image.getBytes();
				InputStream stream = new ByteArrayInputStream(bytes);
				String uuid = UUID.randomUUID().toString();
				String fileType = image.getContentType();
				// 这边可能会出现问题, 如果 image/png 这种形式就没问题
				fileType = "." + fileType.substring(fileType.indexOf("/") + 1);
				uuidList.add("/nagato/" + uuid + fileType);
				client.putObject(
						PutObjectArgs
								.builder()
								.bucket("nagato")
								.contentType(image.getContentType())
								.stream(stream, image.getSize(), -1)
								.object(uuid + fileType)
								.build()
				);
			}
		} catch (ServerException | InsufficientDataException | ErrorResponseException | IOException | NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException | InternalException e) {
			e.printStackTrace();
			return returnStatement(new NagatoResponseEntity()
					.data(e.getMessage())
					.message(ResponseMessage.MINIO_SERVER_ERROR)
			);
		}


		return returnStatement(new NagatoResponseEntity()
				.data(uuidList)
				.message(ResponseMessage.OK)
		);
	}
}
