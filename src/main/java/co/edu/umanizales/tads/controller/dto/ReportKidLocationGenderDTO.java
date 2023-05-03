package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Location;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReportKidLocationGenderDTO {

    private List<LocationGenderQuantityDTO> locationGenderQuantityDTOS;

    public ReportKidLocationGenderDTO(List<Location> cities) {
        locationGenderQuantityDTOS = new ArrayList<>();
        for (Location location : cities) {
            locationGenderQuantityDTOS.add(new
                    LocationGenderQuantityDTO(location.getName()));
        }
    }

    //Método Actualizar
    public void updateQuantity(String city, char gender) {
        for (LocationGenderQuantityDTO loc : locationGenderQuantityDTOS) {
            if (loc.getCity().equals(city)) {
                for(GenderQuantityDTO genderDTO: loc.getGenders()){
                    if(genderDTO.getGender() == gender){
                        genderDTO.setQuantity(genderDTO.getQuantity()+1);
                        loc.setTotal(loc.getTotal()+1);
                        return;

                    }
                }
            }
        }

    }

}
