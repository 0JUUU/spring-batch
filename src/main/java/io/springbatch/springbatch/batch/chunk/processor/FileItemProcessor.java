package io.springbatch.springbatch.batch.chunk.processor;

import io.springbatch.springbatch.batch.domain.Product;
import io.springbatch.springbatch.batch.domain.ProductVo;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;


public class FileItemProcessor implements ItemProcessor<ProductVo, Product> {

    @Override
    public Product process(ProductVo item) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(item, Product.class);

        return product;
    }
}