package com.lgyun.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 检查服务是否健康的接口
 *
 * @author liangfeihu
 * @since 2019/11/1 17:32
 */
@Slf4j
@RestController
@RequestMapping("/healthy")
public class HealthyController {

    @GetMapping("/check")
    public ResponseEntity check() {
        log.info("[HealthyController] url=/healthy/check OK");
        return ResponseEntity.ok("system check success");
    }

}
