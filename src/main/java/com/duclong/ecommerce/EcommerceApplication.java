package com.duclong.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(EcommerceApplication.class);

		app.addListeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
            Dotenv dotenv = Dotenv.configure()
                                  .filename(".env")
                                  .load();

            dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
            );
        });
        app.run(args);
	}

}
