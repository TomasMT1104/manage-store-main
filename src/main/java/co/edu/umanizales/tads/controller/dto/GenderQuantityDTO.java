package co.edu.umanizales.tads.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenderQuantityDTO {
    private char gender;
    private int quantity;
    private int onfire;
     public void incrementOnFire(){
         onfire++;
     }

}