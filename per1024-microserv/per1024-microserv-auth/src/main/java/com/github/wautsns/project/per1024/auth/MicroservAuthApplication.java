package com.github.wautsns.project.per1024.auth;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import tk.mybatis.spring.annotation.MapperScan;

/**
*
* @author wautsns
* @version 0.1.0 Aug 23, 2019
*/
@EnableDiscoveryClient
@EnableDubbo(scanBasePackages = "com.github.wautsns.project.per1024.auth.business.api.rpc")
@MapperScan("com.github.wautsns.project.per1024.auth.business.repository.mapper")
@SpringBootApplication
public class MicroservAuthApplication {

   public static void main(String[] args) throws Exception {
       SpringApplication.run(MicroservAuthApplication.class, args);
   }

}