package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.PetsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.LocationService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listde")
public class ListDEController {
    @Autowired
    private ListDEService listDEService;
    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) throws ListDEException{
        try {
            Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
            if (location == null){
                throw new ListDEException("La ubicación no existe");
            }
            Pet newPet = new Pet(petDTO.getIdentificationPet(), petDTO.getName(),
                    petDTO.getAge(), petDTO.getPetType(), petDTO.getBreed(),
                    location, petDTO.getGender(),false);

            listDEService.getPets().addPet(newPet);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado a la mascota con éxito", null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(404, e.getMessage(), null), HttpStatus.OK);
        }
    }
    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {
        try {
            return new ResponseEntity<>(new ResponseDTO(
                    200, listDEService.getPets().print(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al obtener la lista de mascotas" + e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/invert")
    public ResponseEntity<ResponseDTO> invert() {
        try {
            listDEService.getPets().invert();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La lista fue invertida", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al invertir la lista", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/ordermalestostart")
    public ResponseEntity<ResponseDTO> getOrderMalesToStart()  {
        try {
            if (listDEService.getPets() != null) {
                listDEService.getPets().getOrderMalesToStart();
                return new ResponseEntity<>(new ResponseDTO(200,
                        "Se ha organizado la lista con los machos al inicio con exito ",
                        null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción tenga cuidado", null), HttpStatus.BAD_REQUEST);
            }
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor ", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/alternatepets")
    public ResponseEntity<ResponseDTO> getAlternatePets() {
        try {
            listDEService.getPets().getAlternatePets();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al alternar las mascotas tenga cuidado ", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "La lista se alternó exitosamente", null), HttpStatus.OK);
    }

    @DeleteMapping(path = "/deletepetbyage/{age}")
    public ResponseEntity<ResponseDTO> deletePetbyAge(byte age)  {
        try {
            listDEService.getPets().deletePetByAge(age);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota fue eliminada con exito", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Hay un error al eliminar la mascota estar atento ", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/averageagepet")
    public ResponseEntity<ResponseDTO> getAverageAge()  {
        try {
            float averageAge = (float) listDEService.getPets().getAverageAge();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La edad promedio de las mascotas que ingresaste son : " + averageAge, null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al calcular la edad promedio de las mascotas", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/petsbylocation")
    public ResponseEntity<ResponseDTO> getCountPetsByLocationCode() {
        List<PetsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        try {
            for (Location location : locationService.getLocations()) {
                int count = listDEService.getPets().getCountPetByLocationCode(location.getCode());
                if (count > 0) {
                    petsByLocationDTOList.add(new PetsByLocationDTO(location, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, petsByLocationDTOList,
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/winpositionpet/{id}/{position}")
    public ResponseEntity<ResponseDTO> winPositionPet(String id, int position){
        try {
            listDEService.getPets().winPositionPet(id,position);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota gano las posiciones en la lista especificadas", null)
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error en ganar posiciones de la mascota en la lista",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/losepositionpet/{identificaionPet}/{positionpet}")
    public ResponseEntity<ResponseDTO>losePositionPet(String id, int positionpet){
        try {
            listDEService.getPets().losePositionPet(id,positionpet);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota perdio posiciones en la lista", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se obtuvo un error al perder la mascota en la lista", null)
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping(path = "/addtofinalpetbyletter/{letter}")
    public ResponseEntity<ResponseDTO> addToFinalPetbyLetter(@PathVariable char letter) {
        try {
            listDEService.getPets().addToFinalPetbyLetter(Character.toUpperCase(letter));
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Las mascotas con la letra dada se han enviado al final",
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Ejercicio 08-05-2023
    @DeleteMapping(path = "/deletepetbyidentifiaction/{identificationPet}")
    public ResponseEntity<ResponseDTO> deletePetbyIdentification(@NotNull String identification) {
        try {
            listDEService.getPets().deletePetbyIdentification(identification);
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Se removió a la mascota por identificación", null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(400, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }




}
