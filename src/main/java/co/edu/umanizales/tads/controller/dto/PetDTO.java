package co.edu.umanizales.tads.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PetDTO {
    @NotBlank(message = "Este campo no puede ir vacío")
    private String identificationPet;

    @Size(max = 30, message = "el nombre debe ser máximo de 30 caracteres")
    @NotBlank(message = "Este campo no puede ir vacío")
    private String name;
    @Min(value = 1)
    @Max(value = 14)
    private byte age;
    @NotBlank(message = "Este campo no puede ir vacío")
    private String petType;
    @NotBlank(message = "Este campo no puede ir vacío")
    private String breed;
    @NotBlank(message = "Este campo no puede ir vacío")
    private String codeLocation;
    private char gender;
    private boolean bathed;

}
