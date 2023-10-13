package com.fse.restaurant.springbatch.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.fse.restaurant.springbatch.entity.Restaurant;

@Configuration
@EnableBatchProcessing
public class RestaurantBatchConfig {
	@Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public DataSource dataSource;
    @Autowired
    public EntityManagerFactory entityManagerFactory;
    
    @Bean
    public FlatFileItemReader<RestaurantMenuDTO> restaurantMenuReader() {
        FlatFileItemReader<RestaurantMenuDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource("C:\\Development\\FSE5\\restaurant.csv")); // Set the CSV file location
        reader.setLineMapper(new DefaultLineMapper<RestaurantMenuDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("restaurantName","restaurantAddress", "restaurantRating", "menuName", "menuPrice");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<RestaurantMenuDTO>() {{
                setTargetType(RestaurantMenuDTO.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<RestaurantMenuDTO, Restaurant> restaurantProcessor() {
        return new RestaurantProcessor();
    }

    @Bean
    public ItemWriter<Restaurant> restaurantWriter() {
        return new RestaurantWriter();
    }
    
    @Bean
    public Job importRestaurantMenuJob(JobBuilderFactory jobBuilderFactory, Step s1) {
        return jobBuilderFactory.get("importRestaurantMenuJob")
            .incrementer(new RunIdIncrementer())
            .start(s1)
            .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<RestaurantMenuDTO> reader,
                     ItemProcessor<RestaurantMenuDTO, Restaurant> processor, ItemWriter<Restaurant> writer) {
        return stepBuilderFactory.get("step1")
            .<RestaurantMenuDTO, Restaurant>chunk(10)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }



}
