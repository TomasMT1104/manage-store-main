package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.model.Range;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.RangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/listde")
public class ListDEController {
    @Autowired
    private ListDEService listDEService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private RangeService rangeService;

    @PostMapping
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) throws ListDEException {
        try {
            if(petDTO.getGender() == 'M' && petDTO.isOnfire()){
                return new ResponseEntity<>(new ResponseDTO(406,"Los machos no pueden estar en calor",
                        null), HttpStatus.OK);
            }
            Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
            if (location == null){
                throw new ListDEException("La ubicación no existe");
            }
            Pet newPet = new Pet(petDTO.getIdentificationPet(), petDTO.getName(),
                    petDTO.getAge(), petDTO.getPetType(), petDTO.getBreed(), location, petDTO.getGender(),petDTO.isOnfire(), false);
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

    @GetMapping(path = "/changeextremes")
    public ResponseEntity<ResponseDTO> changeExtremes() throws ListDEException {
        listDEService.getPets().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se han intercambiado los extremos", null), HttpStatus.OK);
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
    public ResponseEntity<ResponseDTO> deletePetByAge(@PathVariable byte age) {
        try {
            listDEService.getPets().deletePetByAge(age);
            return new ResponseEntity<>(new ResponseDTO(200, "La mascota fue eliminada exitosamente", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ocurrió un error al eliminar la mascota", null), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping(path = "/winpositionpet")
    public ResponseEntity<ResponseDTO> winPositionPet(@RequestBody Map<String, Object> requestBody) throws ListDEException{
        String identificationPet =(String) requestBody.get("id");
        Integer win = (Integer) requestBody.get("win");
        listDEService.getPets().winPositionPet(identificationPet,win);
        return new ResponseEntity<>(new ResponseDTO(200,"Las posiciones se reordenaron",null),HttpStatus.OK);
    }


    @GetMapping(path = "/losepositionpet")
    public ResponseEntity<ResponseDTO> losePositionPet(@RequestBody Map<String, Object> requestBody) throws ListDEException{
        String identificationPet =(String) requestBody.get("id");
        Integer gain = (Integer) requestBody.get("lose");
        listDEService.getPets().losePositionPet(identificationPet,gain);
        return new ResponseEntity<>(new ResponseDTO(200,"Las posiciones se reordenaron",null),HttpStatus.OK);
    }

    @GetMapping(path = "/reportrangebyage")
    public ResponseEntity<ResponseDTO> getReportRangeByAgePets(){
        try {
            List<RangeDTO> petsRangeList = new ArrayList<>();
            for (Range i : rangeService.getRange()){
                int quantity = listDEService.getPets().getReportPetByRangeAge(i.getFrom(), i.getTo());
                petsRangeList.add(new RangeDTO(i,quantity));
            }
            return new ResponseEntity<>(new ResponseDTO(200,"Accion realizada con exito, el rango de los niños es: "+ petsRangeList, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,"Error al obtener el rango de edades", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/sendtofinalpetbyletter/{letter}")
    public ResponseEntity<ResponseDTO> sendToFinalPetbyLetter(@PathVariable char letter){
        try{
            listDEService.getPets().sendToFinalPetbyLetter(Character.toUpperCase(letter));
            return new ResponseEntity<>(new ResponseDTO(200,"Accion realizada con exito, las mascotas con la letra dada se han enviado al final de la lista", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Ejercicio 08-05-2023
    @DeleteMapping(path = "/deletepetbyidentification/{id}")
    public ResponseEntity<ResponseDTO> deletePetbyIdentification(@PathVariable String id) throws ListDEException {
        if (listDEService.getPets().print() != null) {
            listDEService.getPets().deletePetbyIdentification(id);
            return new ResponseEntity<>(new ResponseDTO(200, "La mascota se elimino con exito", null), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseDTO(400, "No se pudo realizar la operacion debido a que no existian mascotas", null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path= "/reportspets")
    public ResponseEntity<ResponseDTO> getReportsPetOnFire(){
    ReportPetLocationGenderDTO report = new ReportPetLocationGenderDTO(locationService.getLocationsByCodeSize(8));
        listDEService.getPets().getReportOnFireByLocation(report);
        return new ResponseEntity(new ResponseDTO(200,report,null), HttpStatus.OK);
    }

}
