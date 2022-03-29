package io.springbatch.springbatch;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long Id;
    private String firstname;
    private String lastname;
    private String birthdate;
}
