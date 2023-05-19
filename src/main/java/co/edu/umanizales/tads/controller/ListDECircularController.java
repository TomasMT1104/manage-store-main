package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListDECircularService;
import co.edu.umanizales.tads.service.LocationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/listdecircular")
public class ListDECircularController {
    @Autowired
    private ListDECircularService listDECircularService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listDECircularService.getPets().printList(), null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addPet(@Valid @RequestBody PetDTO petDTO) throws Exception {
        try {
            Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
            if (location == null) {
                return new ResponseEntity<>(new ResponseDTO(
                        404, "La ubicación no existe",
                        null), HttpStatus.BAD_REQUEST);
            }
            listDECircularService.addToEnd(
                    new Pet(petDTO.getIdentificationPet(), petDTO.getName(),
                            petDTO.getAge(), petDTO.getPetType(), petDTO.getBreed(), location, petDTO.getGender(), false));
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado a las mascota",
                    null), HttpStatus.OK);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @PostMapping(path = "/addtostart")
    public ResponseEntity<ResponseDTO> addToStart(@Valid @RequestBody PetDTO petDTO) throws Exception {
        try {
            Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
            if (location == null) {
                return new ResponseEntity<>(new ResponseDTO(
                        404, "La ubicación no existe",
                        null), HttpStatus.OK);
            }
            listDECircularService.addToStart(
                    new Pet(petDTO.getIdentificationPet(), petDTO.getName(),
                            petDTO.getAge(), petDTO.getPetType(), petDTO.getBreed(), location, petDTO.getGender(), false));
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado a la mascota",
                    null), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping(path = "/addbyposition/{position}")
    public ResponseEntity<ResponseDTO> addByPosition(@Valid @RequestBody PetDTO petDTO, @Min(0) @PathVariable int position2) throws Exception {
        try {
            Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
            if (location == null) {
                return new ResponseEntity<>(new ResponseDTO(
                        404, "La ubicación no existe",
                        null), HttpStatus.BAD_REQUEST);
            }
            listDECircularService.addByPosition(
                    new Pet(petDTO.getIdentificationPet(), petDTO.getName(),
                            petDTO.getAge(), petDTO.getPetType(), petDTO.getBreed(), location, petDTO.getGender(), false));
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado a la mascota",
                    null), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping(path = "/takeashower/{direction}")
    public ResponseEntity<ResponseDTO> takeShower(@RequestBody char direction) throws Exception {
        try {
            listDECircularService.takeShower(direction);
            return new ResponseEntity<>(new ResponseDTO(200, "La mascota ha sido bañada", null), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
