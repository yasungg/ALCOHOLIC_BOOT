package startline.server.dto;

import startline.server.constant.AuthorityName;
import startline.server.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Getter
public class MemberRequestDTO {
    @NotBlank(message = "e-mail을 입력하세요.")
    @Email(message = "올바른 이메일 형식을 입력해 주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{10,}$", message = "비밀번호는 영문 대, 소문자와 숫자, 특수기호를 포함한 10자리 이상의 문자입니다.")
    private String password;

    private String name;

    private String nickname;

    private String phone;

    public User signup(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickname(nickname)
                .phone(phone)
                .build();
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
