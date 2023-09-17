package ALCOHOLIC_BOOT.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "admin Controller", description = "관리자 권한을 가진 자들만 접근할 수 있는 엔드포인트입니다.")
@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/test")
    ResponseEntity<String> adminTest() {
        return ResponseEntity.ok("admin 통과!");
    }
}
