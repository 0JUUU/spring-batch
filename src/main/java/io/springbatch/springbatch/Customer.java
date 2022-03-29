package io.springbatch.springbatch;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {

    private long id;
    private String name;
    private int age;
}