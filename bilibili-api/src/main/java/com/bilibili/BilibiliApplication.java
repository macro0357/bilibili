package com.bilibili;

import com.bilibili.service.websocket.WebSocketService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@EnableFeignClients(basePackages = "com.bilibili.service.feign")
@EnableHystrix
public class BilibiliApplication {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(BilibiliApplication.class, args);
        WebSocketService.setApplicationContext(app);
    }
}
