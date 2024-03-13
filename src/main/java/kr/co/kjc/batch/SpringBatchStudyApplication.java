package kr.co.kjc.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchStudyApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBatchStudyApplication.class, args);
  }

}
