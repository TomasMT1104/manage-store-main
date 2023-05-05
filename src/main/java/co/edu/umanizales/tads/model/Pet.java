package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pet {
    private String name;

    private byte age;

    private String breed;

    private char gender;

    private Location location;

    private String identification;

    public Pet getData() {
        return null;
    }

    public Pet(String identification, String name, int sum) {
    }
}
