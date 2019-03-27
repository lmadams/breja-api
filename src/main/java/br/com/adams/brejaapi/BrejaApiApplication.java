package br.com.adams.brejaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BrejaApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(BrejaApiApplication.class, args);
  }
}
