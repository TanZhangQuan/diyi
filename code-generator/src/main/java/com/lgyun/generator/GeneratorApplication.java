package com.lgyun.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

/**
 * 代码生成服务器
 *
 * @author liangfeihu
 * @since 2018/6/23 18:11.
 */
@SpringBootApplication
@MapperScan("com.lgyun.generator.dao")
public class GeneratorApplication {
    private static Logger logger = LoggerFactory.getLogger(GeneratorApplication.class);

    public static void main(String[] args) {
        logger.info("代码生成服务启动开始");
        SpringApplication.run(GeneratorApplication.class, args);
        logger.info("代码生成服务启动结束");
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "|{}[]\\"));
        return factory;
    }

}
