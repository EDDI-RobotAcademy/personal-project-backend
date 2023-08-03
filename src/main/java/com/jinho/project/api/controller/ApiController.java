package com.jinho.project.api.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@NoArgsConstructor
@RestController
@RequestMapping("/api")
public class ApiController {

    @PostMapping("/getApiKey")
    public String getApiKey() {
        // 실제로는 안전한 방법으로 API 키를 획득해야 하지만, 간단한 예시를 위해 하드코딩합니다.
        return "EJ4D2keQlbn1MZd5ugucU9ZB6hdFJxQzcmdRbgoF3R%2BGfTwhPtHwDHRucvEcOaA3plAadNTYpDKZGJ9GcIWKFQ%3D%3D"; // 실제 API 키로 대체해야 합니다.
    }

}
