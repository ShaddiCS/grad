package topjava.grad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GradApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradApplication.class, args);
	}

}
