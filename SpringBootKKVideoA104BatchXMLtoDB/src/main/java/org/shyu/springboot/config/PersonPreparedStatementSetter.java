package org.shyu.springboot.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.shyu.springboot.model.Person;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

public class PersonPreparedStatementSetter implements ItemPreparedStatementSetter<Person> {
	@Override
	public void setValues(Person person, PreparedStatement ps) throws SQLException {
		ps.setInt(0, person.getPersonId());
		ps.setString(1, person.getFirstName());
		ps.setString(2, person.getLastName());
		ps.setString(3, person.getEmail());
		ps.setInt(4, person.getAge());
	}

}