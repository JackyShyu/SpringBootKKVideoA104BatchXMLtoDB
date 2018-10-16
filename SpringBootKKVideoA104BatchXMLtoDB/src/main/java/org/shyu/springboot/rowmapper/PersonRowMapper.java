package org.shyu.springboot.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.shyu.springboot.model.Person;
import org.springframework.jdbc.core.RowMapper;

public class PersonRowMapper implements RowMapper<Person> {

	@Override
	public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Person person = new Person();
		person.setPersonId(resultSet.getInt("person_id"));
		person.setFirstName(resultSet.getString("first_name"));
		person.setLastName(resultSet.getString("last_name"));
		person.setEmail(resultSet.getString("email"));
		person.setAge(resultSet.getInt("age"));
		return person;
	}

}
