package org.example.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Owner {

    private int owner_id;
    private String first_name;
    private String last_name;

    private List<Pet> petList = new ArrayList<>();

    public Owner(int ownerId, String firstName, String lastName) {
    }
}
