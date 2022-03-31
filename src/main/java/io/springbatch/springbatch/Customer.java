package io.springbatch.springbatch;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private long id;
    private String firstName;
    private String lastName;
    private Date birthdate;
}
