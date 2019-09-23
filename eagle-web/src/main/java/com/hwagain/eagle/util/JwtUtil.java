package com.hwagain.eagle.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * jwt工具
 */
//读取配置文件
@Service("jwtUtil")
@ConfigurationProperties("jwt.config")
public class JwtUtil {
    private String key;
    private String key2;
    private long ttl;// 一个小时

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /**
     * 生成JWT
     *
     * @param id
     * @param subject
     * @return
     */
    public String createJWT(String id, String subject,String plateNumber, String loginname,String mobile,Long parentId) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key)
                .claim("Loginname", loginname)
                .claim("Mobile", mobile)
                .claim("PlateNumber", plateNumber)
                .claim("ParentId", parentId);
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     *
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr) {
//    	System.err.println(jwtStr);
    	System.err.println(Jwts.parser().setSigningKey(key).parseClaimsJws(jwtStr).getBody());
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwtStr).getBody();
    }
}

