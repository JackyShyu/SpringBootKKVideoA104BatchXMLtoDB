package org.shyu.springboot.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.shyu.springboot.listener.JobCompletionNotificationListener;
import org.shyu.springboot.model.Person;
import org.shyu.springboot.processor.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
	public StaxEventItemReader<Person> reader(){
		StaxEventItemReader<Person> reader = new StaxEventItemReader<Person>();
		reader.setResource(new ClassPathResource("persons.xml"));
		reader.setFragmentRootElementName("person");
		
		Map<String,String> aliasesMap =new HashMap<String,String>();
		aliasesMap.put("person", "org.shyu.springboot.model.Person");
		XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setAliases(aliasesMap);
		
		reader.setUnmarshaller(marshaller);
		return reader;
}

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Person> writer() {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
        
        //writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        //writer.setSql("INSERT INTO person (first_name, last_name,email,age) VALUES (:firstName, :lastName,:email,:age)");
        writer.setSql("INSERT INTO person (person_id, first_name, last_name,e mail, age) VALUES (?,?,?,?,?)");
        writer.setItemPreparedStatementSetter(new PersonPreparedStatementSetter());
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Person, Person> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
}
