package io.springbatch.springbatch;

import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Customer, Customer2> {

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Customer2 process(Customer item) throws Exception {
        Customer2 customer2 = modelMapper.map(item, Customer2.class);
        return customer2;
    }
}
