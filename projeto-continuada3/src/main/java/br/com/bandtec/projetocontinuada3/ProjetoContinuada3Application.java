package br.com.bandtec.projetocontinuada3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ProjetoContinuada3Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoContinuada3Application.class, args);
	}

}
