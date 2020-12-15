
package com.springbatch.wd.batch;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final DataSource dataSource;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory,
                              StepBuilderFactory stepBuilderFactory,
                              DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
    }

    // tag::readerwriterprocessor[]
    
   
    @Bean 
    public MultiResourceItemReader<Autobot> multiResourceItemReader() { 
    	MultiResourceItemReader<Autobot> resourceItemReader = new MultiResourceItemReader<>();
    	ClassLoader cl = this.getClass().getClassLoader(); 
    	ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
    	Resource[] resources = null;
		try {
			resources = resolver.getResources("data/in/*");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	resourceItemReader.setResources(resources); 
    	resourceItemReader.setDelegate(reader());
    	return resourceItemReader; 
    } 
    
    @Bean 
    public FlatFileItemReader<Autobot> reader() { 
    	FlatFileItemReader<Autobot> reader = new FlatFileItemReader<>();
    	reader.setLineMapper(new DefaultLineMapper<Autobot>() {
    		{ 
	    		setLineTokenizer(new DelimitedLineTokenizer() {
	    		
	    			{ 
	    			  setDelimiter("ç");
	                  setNames(new String[]{"id", "variavel1", "variavel2", "variavel3" });
	    			}
	    		}); 
	    		setFieldSetMapper(new BeanWrapperFieldSetMapper<Autobot>() {
	    			{ 
	    				setTargetType(Autobot.class); 
	    			}
	    		}); 
    		}
    	});
    	return reader;
    }

    @Bean
    public JdbcBatchItemWriter<Autobot> writer() {
        JdbcBatchItemWriter<Autobot> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO autobot (id, variavel1, variavel2, variavel3) VALUES (:id, :variavel1, :variavel2, :variavel3)");
        writer.setDataSource(this.dataSource);
        return writer;
    }

    @Bean
    public Job importAutobotJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importAutobotJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @SuppressWarnings("unchecked")
	@Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Autobot, Autobot>chunk(10)
                .reader(multiResourceItemReader())
//                .processor(processor())
                .writer(writer())
                .build();
    }
}
