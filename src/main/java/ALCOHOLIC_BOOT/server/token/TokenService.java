package ALCOHOLIC_BOOT.server.token;


import ALCOHOLIC_BOOT.server.constant.Authority;
import ALCOHOLIC_BOOT.server.entity.User;
import ALCOHOLIC_BOOT.server.repository.RefreshTokenRepositoryInterface;
import ALCOHOLIC_BOOT.server.user.MashilangUserDetails;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static ALCOHOLIC_BOOT.server.constant.Authority.ROLE_USER;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component("TokenService")
@RequiredArgsConstructor
public class TokenService {
    private static final String AUTHORITIES_KEY = "auth";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final TokenGenerator generator;
    private final RefreshTokenRepositoryInterface refreshTokenRepository;

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
            throw new JwtException("클레임 파싱 실패!");
        }
    }
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY)
                                .toString()
                                .split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        MashilangUserDetails principal = new MashilangUserDetails(new User());
        if (claims.get(AUTHORITIES_KEY).equals(ROLE_USER)) {
            principal = new MashilangUserDetails(new User(claims.getSubject(), "", (String) claims.get("nickname"), ROLE_USER));
        }
        if (claims.get(AUTHORITIES_KEY).equals(Authority.ROLE_ADMIN)) {
            principal = new MashilangUserDetails(new User(claims.getSubject(), "", (String) claims.get("nickname"), Authority.ROLE_ADMIN));
        }
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean checkExpireTime(long tokenExpiresIn) {
        boolean isExpired = true;
        long now = new Date().getTime();

        if(tokenExpiresIn - now <= 0) isExpired = false;
        return isExpired;
    }
    public String resolveToken(HttpServletRequest request, HttpServletResponse response) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        throw new JwtException("wrong token, location = tokenservice.resolvetoken method");
    }

    public boolean validateRefreshToken(String refreshToken) {
        boolean isValidated = false;

        if(refreshToken.equals(refreshTokenRepository.findByToken(refreshToken)))
            isValidated = true;

        return isValidated;
    }
}
