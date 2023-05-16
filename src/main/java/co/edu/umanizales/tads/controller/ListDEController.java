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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
                    location, petDTO.getGender());

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

    @GetMapping(path = "/orderpetstostart")
    public ResponseEntity<ResponseDTO> getOrderPetsToStart()  {
        try {
            if (listDEService.getPets() != null) {
                listDEService.getPets().getorderMalesToStart();
                return new ResponseEntity<>(new ResponseDTO(200,
                        "Se ha organizado la lista con las mascotas masculinas  al comienzo con exito ",
                        null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción tenga cuidado", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor ", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        try {
            return new ResponseEntity<>("No se encontró el elemento solicitado: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Ha ocurrido un error al manejar la excepción: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/getalternatepets")
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

    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        try {
            return new ResponseEntity<>("Ocurrió un estado ilegal: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al manejar la excepción: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/deletepetbyage/{age}")
    public ResponseEntity<ResponseDTO> removePetByAge(Byte age)  {
        try {
            listDEService.getPets().deletePetByAge(age);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota fue eliminada con exito", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Hay un error al eliminar la mascota estar atento ", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(value = {FileNotFoundException.class})
    public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException ex) {
        try {
            // Manejo del error
            return new ResponseEntity<>("El archivo no fue encontrado: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Manejo de cualquier otro error que pueda ocurrir durante la ejecución del método
            return new ResponseEntity<>("Error al manejar la excepción: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @ExceptionHandler(value = ListDEException.class)
    public ResponseEntity<Object> handleListSEException(ListDEException ex) {
        try {
            String errorMessage = "Error al generar un reporte de mascota/s por ciudad " + ex.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inesperado al manejar la excepción: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @ExceptionHandler(value = {IndexOutOfBoundsException.class})
    public ResponseEntity<String> handleIndexOutOfBoundsException(IndexOutOfBoundsException exception) {
        try {
            return new ResponseEntity<>("Se ha producido una excepción de índice fuera de rango", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IndexOutOfBoundsException exception2) {
            return new ResponseEntity<>("Se ha producido un error al manejar la excepción de índice fuera de rango"+exception, HttpStatus.INTERNAL_SERVER_ERROR);
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

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(NullPointerException nullPointerException) {
        try {
            return new ResponseEntity<>("No se ha añadido el perro ", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NullPointerException pointerException) {
            return new ResponseEntity<>("Tiene un error al pedir a la mascota/s perder el numero de posciciones", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/losepositionpet/{id}/{positionpet}")
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

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        try {
            return new ResponseEntity<>("No se encuentra la mascota/s añadido ", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalStateException illegalStateException) {
            return new ResponseEntity<>("No puedo enviar a ningun/a mascota/s de la lista,No se encuentra ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/addtofinalpetbyletter/{letter}")
    public ResponseEntity<ResponseDTO> petToFinishByLetter(@PathVariable char letter) {
        try {
            listDEService.getPets().addToFinalPetbyLetter(Character.toUpperCase(letter));
            return new ResponseEntity<>(new ResponseDTO(
                    200, "los niños con la letra dada se han enviado al final",
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Ejercicio 08-05-2023
    @GetMapping(path = "/removeNodeByIdentificationPet(identification)")
    public ResponseEntity<ResponseDTO> removeNodeByIdentificationPet(@NotNull String identification) {
        try {
            listDEService.getPets().deletePetbyIdentification(identification);
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Se removió al niño por identificación", null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(400, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }




}
