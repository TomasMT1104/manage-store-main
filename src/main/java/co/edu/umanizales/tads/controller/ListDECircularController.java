package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListDECircularService;
import co.edu.umanizales.tads.service.LocationService;
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
                200, listDECircularService.getPetCircular().print(), null), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) throws ListDEException {
        try {
            Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
            if (location == null) {
                throw new ListDEException("La ubicación no existe");
            }
            Pet newPet = new Pet(petDTO.getIdentificationPet(), petDTO.getName(),
                    petDTO.getAge(), petDTO.getPetType(), petDTO.getBreed(),
                    location, petDTO.getGender(),false);
            listDECircularService.getPetCircular().addPet(newPet);

            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado a la mascota con éxito", null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(404, e.getMessage(), null), HttpStatus.OK);
        }
    }


    @PostMapping(path = "/addtostart")
    public ResponseEntity<ResponseDTO> addToStar(@RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        listDECircularService.getPetCircular().addToStart(
                new Pet(petDTO.getIdentificationPet(), petDTO.getName(),
                        petDTO.getAge(), petDTO.getPetType(), petDTO.getBreed(),
                        location, petDTO.getGender(),false));

        return new ResponseEntity<>(new ResponseDTO(200, "Mascota adicionada al inicio", null),
                HttpStatus.OK);
    }

    @PostMapping(path = "/addtoend")
    public ResponseEntity<ResponseDTO> addToEnd(@RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        listDECircularService.getPetCircular().addToEnd(
                new Pet(petDTO.getIdentificationPet(), petDTO.getName(),
                        petDTO.getAge(), petDTO.getPetType(), petDTO.getBreed(),
                        location, petDTO.getGender(),false));

        return new ResponseEntity<>(new ResponseDTO(200, "Mascota adicionada al final", null),
                HttpStatus.OK);
    }

    @PostMapping(path = "/addbyposition/{position}")
    public ResponseEntity<ResponseDTO> addByPosition(@RequestBody PetDTO petDTO, @PathVariable int position) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        listDECircularService.getPetCircular().addByPosition(
                new Pet(petDTO.getIdentificationPet(), petDTO.getName(),
                        petDTO.getAge(), petDTO.getPetType(), petDTO.getBreed(),
                        location, petDTO.getGender(),false),position);

        return new ResponseEntity<>(new ResponseDTO(200, "Mascota adicionada en la posicion: "
                + position, null),
                HttpStatus.OK);
    }

    @GetMapping(path = "/takeashower")
    public ResponseEntity<ResponseDTO> takeShower(@PathVariable char direction) {
        Pet pet = listDECircularService.getPetCircular().takeShower(direction);
        if (pet == null) {
            return new ResponseEntity<>(new ResponseDTO(200, "La mascota ya estaba bañada", null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO(200, "La mascota " + pet.getIdentificationPet() + " se bañó", null), HttpStatus.OK);
        }
    }

}

