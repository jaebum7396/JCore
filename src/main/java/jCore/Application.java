package jCore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import jCore.configuration.RedisSubscriber;

@EnableDiscoveryClient
@EnableJpaAuditing
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Application implements CommandLineRunner {
	private final RedisSubscriber redisSubscriber;
	@Autowired
	public Application(RedisSubscriber redisSubscriber) {
		this.redisSubscriber = redisSubscriber;
	}
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Override
	public void run(String... args) {
		redisSubscriber.subscribe();
	}
}
