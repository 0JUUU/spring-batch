package io.springbatch.springbatch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class CustomerFieldSetMapper implements FieldSetMapper<Customer> {

    @Override
    public Customer mapFieldSet(FieldSet fieldSet) {
        Customer customer = new Customer();

        customer.setName(fieldSet.readString("name"));
        customer.setYear(fieldSet.readString("year"));
        customer.setAge(fieldSet.readInt("age"));

        return customer;
    }
}
