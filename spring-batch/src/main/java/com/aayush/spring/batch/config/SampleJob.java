package com.aayush.spring.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.aayush.spring.batch.listener.FirstJobListener;
import com.aayush.spring.batch.listener.FirstStepListener;
import com.aayush.spring.batch.processor.FirstItemProcessor;
import com.aayush.spring.batch.reader.FirstItemReader;
import com.aayush.spring.batch.service.SecondTasklet;
import com.aayush.spring.batch.writer.FirstItemWriter;

@Configuration
@Component
public class SampleJob {

	@Autowired
	private JobBuilderFactory jobBuilder;

	@Autowired
	private StepBuilderFactory stepBuilder;

	@Autowired
	private SecondTasklet secondTasklet;

	@Autowired
	private FirstJobListener firstJobListener;

	@Autowired
	private FirstStepListener firstStepListener;

	@Autowired
	private FirstItemProcessor firstItemProcessor;

	@Autowired
	private FirstItemReader firstItemReader;

	@Autowired
	private FirstItemWriter firstItemWriter;

	// @Bean
	public Job firstJob() {
		return jobBuilder.get("First Job").incrementer(new RunIdIncrementer()).start(firstStep()).next(secondStep())
				.listener(firstJobListener).build();
	}

	@Bean
	public Job secondJob() {
		return jobBuilder.get("Second Job").incrementer(new RunIdIncrementer()).start(firstChunkStep()).next(secondStep()).build();
	}

	private Step firstStep() {
		return stepBuilder.get("First Step").tasklet(firstTask()).listener(firstStepListener).build();
	}

	private Step secondStep() {
		return stepBuilder.get("Second Step").tasklet(secondTasklet).build();
	}

	private Step firstChunkStep() {
		return stepBuilder.get("First Chunk Step").<Integer, Long>chunk(3).reader(firstItemReader)
				.processor(firstItemProcessor).writer(firstItemWriter).build();
	}

	private Tasklet firstTask() {
		return new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("This is first Tasklet Step");
				return RepeatStatus.FINISHED;
			}
		};
	}

}
