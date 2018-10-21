package org.matoujun.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.eureka.server.EurekaServerConfigBean;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryMain {
	private static final Logger log = LoggerFactory.getLogger(DiscoveryMain.class);

	@Autowired
	private EurekaServerConfigBean eurekaServerConfigBean;

	@Autowired
	private EurekaInstanceConfigBean eurekaInstanceConfigBean;

	@PostConstruct
	public void printEurekaServerConfig(){
		log.info("EurekaServerConfig: " + eurekaServerConfigBean.toString());
		log.info("EurekaInstanceConfig: " + eurekaInstanceConfigBean.toString());
	}


	public static void main(String[] args) {
		new SpringApplicationBuilder(DiscoveryMain.class).run(args);
	}

}
