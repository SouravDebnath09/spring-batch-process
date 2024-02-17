package com.example.springbatch.config;

import com.example.springbatch.entity.Employee;
import com.example.springbatch.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    private JobRepository jobRepository;

    private PlatformTransactionManager transactionManager;

    private EmployeeRepository employeeRepository;

    @Bean
    public FlatFileItemReader<Employee> reader(){
        FlatFileItemReader<Employee> itemReader = new FlatFileItemReader<>();

        itemReader.setName("employeeCsvReader");
        itemReader.setResource(new FileSystemResource("src/main/resources/employees.csv"));
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());

    return  itemReader;
    }

    @Bean
    public EmployeeProcessor processor(){
        return new EmployeeProcessor();
    }

    @Bean
    public RepositoryItemWriter<Employee> writer(){
        RepositoryItemWriter<Employee> writer= new RepositoryItemWriter<>();
        writer.setRepository(employeeRepository);
        writer.setMethodName("save");

    return writer;
    }

    @Bean
    public Step step1(){
        return new StepBuilder("csv-step",jobRepository)
                .<Employee,Employee>chunk(10,transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job runJob(){
        return new JobBuilder("load-employeelist",jobRepository).flow(step1()).end().build();
    }

    private LineMapper<Employee> lineMapper() {

        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("Employee ID","First Name","Last Name","Age","Department","Salary");
        lineTokenizer.setStrict(false);

        BeanWrapperFieldSetMapper<Employee> fieldSetMapper= new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Employee.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
}
