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

import java.util.NoSuchElementException;

@RestController
@RequestMapping
public class ListDEController {
    @Autowired
    private ListDEService listDEService;
    @Autowired
    private LocationService locationService;

    @PostMapping (path = "/addpet")
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

    @GetMapping(path = "getpets")
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



}
