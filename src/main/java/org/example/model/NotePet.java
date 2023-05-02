package org.example.model;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class NotePet {
    private int id;
    private int pet_id;
    private int note_id;
}
