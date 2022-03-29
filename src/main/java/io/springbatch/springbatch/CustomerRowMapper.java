package io.springbatch.springbatch;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int i) throws SQLException {
        Customer customer = new Customer();

        customer.setId(rs.getLong("id"));
        customer.setFirstName(rs.getString("firstName"));
        customer.setLastName(rs.getString("lastname"));
        customer.setBirthdate(rs.getString("birthdate"));

        return customer;
    }
}
