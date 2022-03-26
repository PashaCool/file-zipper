package io.pavel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FileZipperApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileZipperApplication.class, args);
	}

}
