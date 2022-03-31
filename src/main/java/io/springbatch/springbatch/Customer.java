package io.springbatch.springbatch;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {

    private final long id;
    private final String firstName;
    private final String lastName;
    private final Date birthdate;
}
