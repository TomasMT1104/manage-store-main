package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ListDEController {
    @Autowired
    private ListDEService listDEService;
    @Autowired
    private LocationService locationService;

    @GetMapping (path = "/addpet")
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) throws ListDEException {
        Location location = locationService.getLocationByCode((String) PetDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        listDEService.getPets().add(
                new Pet(PetDTO.getIdentification(), PetDTO.getName(), PetDTO.getAge(), PetDTO.getGender(), PetDTO.getCodeLocation()));

        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado la mascota",
                null), HttpStatus.OK);

    }



}
