package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pet {
    private String identificationPet;
    private String name;
    private byte age;
    private String petType;
    private String breed;
    private Location location;
    private char gender;
    private boolean onfire;
    private boolean bathed;

}
