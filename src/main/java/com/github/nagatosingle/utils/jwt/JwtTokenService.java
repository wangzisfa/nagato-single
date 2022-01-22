package com.github.nagatosingle.utils.jwt;

import com.github.nagatosingle.constants.RedisKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


//@Component
public class JwtTokenService {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	// 7天
	public static final long JWT_TOKEN_VALIDITY = 168 * 60 * 60;

	@Value("${jwt.secret}")
	private String secret;

	public String getUsernameFromToken(String token) {
		return (String) getClaimFromToken(token, claims -> claims.get("username"));
	}

	public String getUuidFromToken(String token) {
		return (String) getClaimFromToken(token, claims -> claims.get("id"));
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
		HashOperations<String, String, String> ops = redisTemplate.opsForHash();
		if (ops.hasKey(RedisKey.ACCESS_TOKEN, token)) {
			String uuid = (String) redisTemplate.opsForHash().get(RedisKey.ACCESS_TOKEN, token);
			if (uuid != null && ops.hasKey(RedisKey.ACCESS_TOKEN_REVERSE, uuid)) {
				String tokenInRedis = (String) redisTemplate.opsForHash().get(RedisKey.ACCESS_TOKEN_REVERSE, uuid);
				return StringUtils.equals(token, tokenInRedis);
			}
		}

		return false;
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

		redisTemplate.boundHashOps(RedisKey.ACCESS_TOKEN).put(token, user.getUserNoGenerate());
		redisTemplate.boundHashOps(RedisKey.ACCESS_TOKEN_REVERSE).put(user.getUserNoGenerate(), token);
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

	/**
	 * 通过redis存一个{@code HashMap&lt;String, List&lt;String&gt;&gt;}
	 * @param ip 当前用户ip
	 * @param uri 当前访问接口
	 * @return 可以通过访问 ? true : false
	 */
	public Boolean rateFilterAndJudge(String ip, String uri) {
		BoundHashOperations<String, String, List<BlockMap<String, Date>>> putOps = redisTemplate.boundHashOps(RedisKey.RATE_FILTER);
		HashOperations<String, String, List<BlockMap<String, Date>>> getOps = redisTemplate.opsForHash();
		


		return false;
	}

	@Data
	private class BlockMap<K extends String, V extends Date> {
		K uri;
		V date;

	}
}


