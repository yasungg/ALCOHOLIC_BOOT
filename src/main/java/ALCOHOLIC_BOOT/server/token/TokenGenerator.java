package ALCOHOLIC_BOOT.server.token;

import ALCOHOLIC_BOOT.server.dto.TokenDTO;
import ALCOHOLIC_BOOT.server.entity.RefreshToken;
import ALCOHOLIC_BOOT.server.repository.RefreshTokenRepositoryInterface;
import ALCOHOLIC_BOOT.server.repository.UserRepositoryInterface;
import ALCOHOLIC_BOOT.server.user.MashilangUserDetails;
import ALCOHOLIC_BOOT.server.user.MashilangUserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component("TokenGenerator")
@RequiredArgsConstructor
public class TokenGenerator {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 40;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7L * 24 * 60 * 1000;
    @Value("${springboot.jwt.secret}")
    private static String SECRET_KEY; // application.properties에서 jwt를 암호화할 64byte의 문자열을 불러옴

    // 이 아래로는 의존성 주입
    private final UserRepositoryInterface userRepositoryInterface;
    private final MashilangUserDetailsService userDetailsService;
    private final RefreshTokenRepositoryInterface refreshTokenRepository;

    protected Key keyEncoder(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    public TokenDTO generateTokens(Authentication authentication) {
        MashilangUserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

        //권한정보 세팅
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        //만료 시간 세팅
        long now = new Date().getTime();
        Date tokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        //claim에 담을 nickname 불러오기
        String nickname = userDetails.getNickname();

        //generate access token
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("nickname", nickname)
                .setExpiration(tokenExpiresIn)
                .signWith(keyEncoder(SECRET_KEY), SignatureAlgorithm.HS512)
                .compact();
        //generate refresh token
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(refreshTokenExpiresIn)
                .signWith(keyEncoder(SECRET_KEY), SignatureAlgorithm.HS512)
                .compact();

        //생성된 refresh token db에 저장
        saveRefreshTokenToDB(refreshToken);

        return TokenDTO.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenExpiresIn(tokenExpiresIn.getTime())
                .refreshTokenExpiresIn(refreshTokenExpiresIn.getTime())
                .build();
    }
    public TokenDTO generateAccessToken(Authentication authentication) {
        MashilangUserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

        //권한정보 세팅
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        //만료 시간 세팅
        long now = new Date().getTime();
        Date tokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        //claim에 담을 nickname 불러오기
        String nickname = userDetails.getNickname();

        //generate access token
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("nickname", nickname)
                .setExpiration(tokenExpiresIn)
                .signWith(keyEncoder(SECRET_KEY), SignatureAlgorithm.HS512)
                .compact();

        return TokenDTO.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .tokenExpiresIn(tokenExpiresIn.getTime())
                .build();
    }
    public TokenDTO generateRefreshToken(Authentication authentication) {
        //권한정보 세팅
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        //만료 시간 세팅
        long now = new Date().getTime();
        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        //generate refresh token
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(refreshTokenExpiresIn)
                .signWith(keyEncoder(SECRET_KEY), SignatureAlgorithm.HS512)
                .compact();

        //생성된 refresh token db에 저장
        saveRefreshTokenToDB(refreshToken);

        return TokenDTO.builder()
                .grantType(BEARER_TYPE)
                .refreshToken(refreshToken)
                .refreshTokenExpiresIn(refreshTokenExpiresIn.getTime())
                .build();
    }
    private void saveRefreshTokenToDB(String refreshToken) {
        RefreshToken entity = new RefreshToken(refreshToken);
        refreshTokenRepository.save(entity);
    }
}
