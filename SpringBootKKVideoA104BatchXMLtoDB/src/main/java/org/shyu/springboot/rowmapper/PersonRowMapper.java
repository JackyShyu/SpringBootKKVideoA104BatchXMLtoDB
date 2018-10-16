package org.shyu.springboot.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.shyu.springboot.model.Person;
import org.springframework.jdbc.core.RowMapper;

public class PersonRowMapper implements RowMapper<Person> {

	@Override
	public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Person person = new Person(resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getInt("age"));
		return person;
	}

}
