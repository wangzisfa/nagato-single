package com.github.nagatosingle.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


//@Component
public class JwtTokenService {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	@Value("${jwt.secret}")
	private String secret;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public Claims getAllClaimsFromToken(String token) {
		System.out.println(token);
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}

	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Boolean validateToken(String token) {
		String tokenInRedis = (String) redisTemplate.opsForHash().get("access-token", token);
		return tokenInRedis != null;
//		Claims claims = getAllClaimsFromToken(token);
//
//		return hasExpiration ? claims.get("id").equals(user.getUserNoGenerate()) &&
//				claims.get("username").equals(user.getUsername()) &&
//				!isTokenExpired(token) :
//				claims.get("id").equals(user.getUserNoGenerate()) &&
//						claims.get("username").equals(user.getUsername());
	}

	public String generateToken(JwtUserDetail user, boolean hasExpiration) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getUserNoGenerate());
		claims.put("username", user.getUsername());
		String token;
		if (hasExpiration) token = doGenerateToken(claims);
		else token = doGenerateTokenNonExpiration(claims);

		redisTemplate.boundHashOps("access-token").put(user.getUserNoGenerate(), token);
		return token;
	}

	private String doGenerateTokenNonExpiration(Map<String, Object> claims) {
		return "Bearer " + Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	private String doGenerateToken(Map<String, Object> claims) {
		System.out.println(secret);
//		byte[] keyBytes = Decoders.BASE64.decode(secret);
//		Key key = Keys.hmacShaKeyFor(keyBytes);
		return "Bearer " + Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();

	}
}


