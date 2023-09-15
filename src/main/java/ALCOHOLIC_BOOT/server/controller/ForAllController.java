package ALCOHOLIC_BOOT.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "forall Controller", description = "회원가입을 하지 않아도 이용 가능한 서비스로 이어지는 컨트롤러입니다.")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/forall")
public class ForAllController {
    @PostMapping("/test")
    ResponseEntity<String> test() {
        log.info("필터 통과 성공");
        return ResponseEntity.ok("filter 통과 성공!");
    }
}
