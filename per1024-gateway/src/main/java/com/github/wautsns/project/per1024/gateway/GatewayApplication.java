package com.github.wautsns.project.per1024.gateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 22, 2019
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.github.wautsns.project.per1024.gateway.filter")
public class GatewayApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
