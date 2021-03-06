package org.shyu.springboot.listener;

import java.util.List;

import org.shyu.springboot.model.Person;
import org.shyu.springboot.rowmapper.PersonRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	
	private JdbcTemplate jdbcTemplate;
	
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");
			List<Person> results = jdbcTemplate.query("select person_id, last_name, first_name, email, age from person", new PersonRowMapper());
			for (Person person: results) {
				log.info("Found <" + person + "> in the database.");
			}
		}
	}
}
