package org.matoujun.cloud.gw;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@SpringBootConfiguration
@EnableDiscoveryClient
@EnableAutoConfiguration
@Slf4j

public class GwMain {

    @Autowired
    private EurekaClientConfigBean eurekaClientConfigBean;

    @Autowired
    private EurekaInstanceConfigBean eurekaInstanceConfigBean;

    @PostConstruct
    public void printEurekaClientInitParas() {
        log.info("EurekaClientConfig: " + eurekaClientConfigBean.toString());
        log.info("EurekaInstanceConfig: " + eurekaInstanceConfigBean.toString());
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/user/*").or().path("/welcome").uri("lb://UAA"))
                .route(r -> r.path("/api/**").filters(f ->
                        f.filter(new ThrottleGatewayFilter()
                                .setCapacity(1)
                                .setRefillTokens(1)
                                .setRefillPeriod(100)
                                .setRefillUnit(TimeUnit.SECONDS))).uri("lb://API"))
                .route(r -> r.path("/discovery/**").filters(f -> f.filter(new
                        RewriteDiscoveryIpFilter())).uri("no://op"))
                .route(r -> r.path("/eureka/**").uri("http://localhost:8602"))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(GwMain.class, args);
    }
}
