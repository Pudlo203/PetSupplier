package org.example.model;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Pet {

    private int pet_id;
    private String pet_name;
    private String kind;
    private float weight;

    private int owner_id;

    public Pet(int ownerId) {
    }


}
