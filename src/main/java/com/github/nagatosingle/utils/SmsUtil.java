package com.github.nagatosingle.utils;


import com.github.nagatosingle.constants.RedisKey;
import com.github.nagatosingle.constants.ResponseMessage;
import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SmsUtil {
	@Value("${sms.secret-id}")
	private String SECRET_ID;
	@Value("${sms.secret-key}")
	private String SECRET_KEY;
	@Value("${sms.sdk-appid}")
	private String SDK_APPID;
	@Value("${sms.sign-name}")
	private String SIGN_NAME;
	@Value("${sms.template-id}")
	private String TEMPLATE_ID;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;


	public NagatoResponseEntity sendSms(String number, String remoteAddress) {
		try{
			// 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
			// 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
			Credential cred = new Credential(SECRET_ID, SECRET_KEY);
			// 实例化一个http选项，可选的，没有特殊需求可以跳过
			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("sms.tencentcloudapi.com");
			// 实例化一个client选项，可选的，没有特殊需求可以跳过
			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);
			// 实例化要请求产品的client对象,clientProfile是可选的
			SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
			// 实例化一个请求对象,每个接口都会对应一个request对象
			SendSmsRequest req = new SendSmsRequest();
			String[] phoneNumberSet1 = {number};
			req.setPhoneNumberSet(phoneNumberSet1);

			req.setSmsSdkAppId(SDK_APPID);
			req.setSignName(SIGN_NAME);
			req.setTemplateId(TEMPLATE_ID);

			// 生成验证码
			String verificationCode = randomVerificationCode();
			log.info("SmsUtil verificationCode : " + verificationCode);
			// 上传redis并设置过期时间
			if (!uploadVerificationCode(verificationCode, remoteAddress)) {
				return new NagatoResponseEntity()
						.message(ResponseMessage.SMS_REDIS_KEY_NOT_EXPIRED);
			}

			String[] templateParamSet1 = {verificationCode};
			req.setTemplateParamSet(templateParamSet1);

			// 返回的resp是一个SendSmsResponse的实例，与请求对象对应
			SendSmsResponse resp = client.SendSms(req);
			// 输出json格式的字符串回包
			System.out.println(SendSmsResponse.toJsonString(resp));
			return new NagatoResponseEntity()
					.data(resp)
					.message(ResponseMessage.OK);
		} catch (TencentCloudSDKException e) {
			System.out.println(e);
			return new NagatoResponseEntity()
					.message(ResponseMessage.SMS_ERROR);
		}
	}

	private String randomVerificationCode() {
		String res = Double.toString(Math.random());
		res = res.substring(res.indexOf(".") + 1, res.indexOf(".") + 7);
		return res;
	}

	private boolean uploadVerificationCode(String code , String remoteAddress) {
		if (redisTemplate.opsForValue().get(remoteAddress) == null) {
			redisTemplate.opsForValue().set(remoteAddress, code, 300, TimeUnit.SECONDS);
			return true;
		}
		return false;
	}

	public static Boolean verify(String redisCode, String currentCode) {
		return StringUtils.equals(redisCode, currentCode);
	}
}
