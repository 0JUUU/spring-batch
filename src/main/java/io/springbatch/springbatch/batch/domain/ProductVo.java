package io.springbatch.springbatch.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductVo {

    private Long id;
    private String name;
    private int price;
    private String type;
}
