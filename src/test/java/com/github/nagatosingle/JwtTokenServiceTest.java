package com.github.nagatosingle;

import com.github.nagatosingle.constants.RedisKey;
import com.github.nagatosingle.utils.jwt.JwtUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.function.Function;


@SpringBootTest
@Slf4j
public class JwtTokenServiceTest {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Test
	public void test() {
		HashMap<String, Object> claims = new HashMap<>();
		claims.put("id", "asdfas");
		claims.put("username", "wangzisfa");

		String res = Jwts.builder()
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + 100000000))
				.signWith(SignatureAlgorithm.HS256, "wangzisfainusewithsuzumiyaharuhibackendservice")
				.compact();
		System.out.println(getClaimFromToken("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjRkYmM3YTZiYzAyYTQyYzA5NWZmZjAyNmJlOGIzZmE1IiwiZXhwIjoxNjQzMzg2NDYzLCJpYXQiOjE2NDI3ODE2NjMsInVzZXJuYW1lIjpudWxsfQ.xC9T32BnxYPal3Gzx4RRvnxcRjOiNP3MIXYm_LvUzzE", claims1 -> claims1.get("id")) + " this is id");
		System.out.println(res);
	}

	public Claims getAllClaimsFromToken(String token) {
		System.out.println(token);
		return Jwts.parser()
				.setSigningKey("wangzisfainusewithsuzumiyaharuhibackendservice")
				.parseClaimsJws(token)
				.getBody();
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private String doGenerateTokenNonExpiration(Map<String, Object> claims) {
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(SignatureAlgorithm.HS256, "wangzisfainusewithsuzumiyaharuhibackendservice")
				.compact();
	}

	private String doGenerateToken(Map<String, Object> claims) {
		System.out.println("wangzisfainusewithsuzumiyaharuhibackendservice");
//		byte[] keyBytes = Decoders.BASE64.decode(secret);
//		Key key = Keys.hmacShaKeyFor(keyBytes);
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000))
				.signWith(SignatureAlgorithm.HS256, "wangzisfainusewithsuzumiyaharuhibackendservice")
				.compact();
	}

	public String generateToken(JwtUserDetail user, boolean hasExpiration) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getUserNoGenerate());
		claims.put("username", user.getUsername());
		String token;
		if (hasExpiration) token = doGenerateToken(claims);
		else token = doGenerateTokenNonExpiration(claims);

		redisTemplate.boundHashOps(RedisKey.ACCESS_TOKEN).put(token, "");
//		redisTemplate.boundSetOps(RedisKey.ACCESS_TOKEN).add(token);
		return token;
	}

	@Test
	public void generateTokenTest() {
		System.out.println(generateToken(JwtUserDetail
						.builder()
						.username("wangzisfa")
						.userNoGenerate("asdfasdf")
						.build(),
				true));
	}


	public Boolean validateToken(String token, JwtUserDetail user, boolean hasExpiration) {
		String tokenInRedis = (String) redisTemplate.opsForHash().get(RedisKey.ACCESS_TOKEN, token);
		if (tokenInRedis == null) {
			log.error("token 不存在");
			return false;
		}
		Claims claims = getAllClaimsFromToken(token);

		return hasExpiration ? claims.get("id").equals(user.getUserNoGenerate()) &&
				claims.get("username").equals(user.getUsername()) &&
				!isTokenExpired(token) :
				claims.get("id").equals(user.getUserNoGenerate()) &&
				claims.get("username").equals(user.getUsername());
	}


	@Test
	public void validationTest() {
		log.info(validateToken("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6ImFzZGZhc2RmIiwiZXhwIjoxNjQxODI5NTA0LCJpYXQiOjE2NDE4MTE1MDQsInVzZXJuYW1lIjoid2FuZ3ppc2ZhIn0._5cHrqi-wvij3QnWIYuXJn8yvHrNokcFEhLeuwfSNyo",
				JwtUserDetail.builder()
						.username("wangzisfa")
						.userNoGenerate("asdfasdf")
						.exp(new Date())
						.build(), true) + "wangasdfasdfasdfasdf");
	}
}
