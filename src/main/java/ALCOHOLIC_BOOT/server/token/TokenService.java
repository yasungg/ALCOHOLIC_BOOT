package ALCOHOLIC_BOOT.server.token;


import ALCOHOLIC_BOOT.server.constant.Authority;
import ALCOHOLIC_BOOT.server.entity.User;
import ALCOHOLIC_BOOT.server.user.MashilangUserDetails;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component("TokenService")
@RequiredArgsConstructor
public class TokenService {
    private static final String AUTHORITIES_KEY = "auth";

    private final TokenGenerator generator;

    @Value("${springboot.jwt.secret}")
    private String secretKey;
    public boolean validateToken(String token) throws Exception {
        Key key = generator.keyEncoder(secretKey);
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch(MalformedJwtException e) {
            e.printStackTrace();
            throw new JwtException("잘못된 jwt 서명입니다.");
        } catch(ExpiredJwtException e) {
            e.printStackTrace();
            throw new JwtException("만료된 jwt입니다.");
        } catch(UnsupportedJwtException e) {
            e.printStackTrace();
            throw new JwtException("지원되지 않는 token입니다.");
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
            throw new JwtException("access token이 잘못되었습니다.");
        }
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(generator.keyEncoder(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch(ExpiredJwtException e) {
            e.getClaims();
        }
    }
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        if(claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY)
                        .toString()
                        .split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // 구축 중
//        MashilangUserDetails userDetails = new MashilangUserDetails(new User());
//        if(claims.get(AUTHORITIES_KEY).equals(Authority.ROLE_USER)) {
//            userDetails = new MashilangUserDetails(new User(claims.getSubject(), "", (String) claims.get("nickname"), Authority.ROLE_USER));
//
//        }
}
