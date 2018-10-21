package org.matoujun.cloud.common;

import org.springframework.boot.SpringApplication;

//@ComponentScan("org.matoujun.cloud")
//@Configuration
//@EnableAutoConfiguration
//@SpringBootApplication
////@EnableCaching
////@EnableAsync
//@EnableDiscoveryClient
public class CommonApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "dev");
		SpringApplication.run(CommonApplication.class, args);
	}
}
