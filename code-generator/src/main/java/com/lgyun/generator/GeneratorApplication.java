package com.lgyun.generator;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

/**
 * 代码生成服务器
 *
 * @author tzq
 * @since 2018/6/23 18:11.
 */
@Slf4j
@SpringBootApplication
@MapperScan("com.lgyun.generator.dao")
public class GeneratorApplication {

    public static void main(String[] args) {
        log.info("代码生成服务启动开始");
        SpringApplication.run(GeneratorApplication.class, args);
        log.info("代码生成服务启动结束");
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "|{}[]\\"));
        return factory;
    }

}
