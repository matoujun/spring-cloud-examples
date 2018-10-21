package org.matoujun.cloud.api;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan("org.matoujun.cloud")
@EnableAutoConfiguration
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableDiscoveryClient
@EnableSwagger2
@EnableJpaRepositories("org.matoujun.cloud.api.dao")
@EnableFeignClients
public class ApiMain {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(ApiMain.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.setWebApplicationType(WebApplicationType.SERVLET);
        application.run(args);
    }
}
