package kr.co.kjc.batch.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@EnableBatchProcessing
@Configuration
@Profile("tasklet")
public class TaskletJobConfig {

  @Bean
  public Job myJob(JobRepository jobRepository, Step step) {
    return new JobBuilder("myJob", jobRepository)
        .start(step)
        .build();
  }

  @Bean
  public Step myStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("myStep", jobRepository)
        .<String, String>chunk(1000,transactionManager)
        .reader(itemReader())
        .processor(itemProcessor())
        .writer(itemWriter())
        .build();
  }

  @Bean
  public ItemReader<String> itemReader(){
    return new ItemReader<String>() {
      @Override
      public String read()
          throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return "Read OK";
      }
    } ;
  }

  @Bean
  public ItemProcessor<String, String> itemProcessor() {
    return new ItemProcessor() {
      @Override
      public Object process(Object item) throws Exception {
        return "Processer OK";
      }
    } ;
  }

  @Bean
  public ItemWriter<String> itemWriter(){
    return strList -> {
      strList.forEach(
          str -> log.info("str: {}", str)
      );
    };
  }

}
