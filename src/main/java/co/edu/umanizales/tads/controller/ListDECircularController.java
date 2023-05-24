package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.ResponseDTO;
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
                200, listDECircularService.getPets().printList(), null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addPet(@RequestBody Pet pet){
        listDECircularService.getPets().addToEnd(pet);
        return new ResponseEntity<>(new ResponseDTO(200, "La mascota ha sido añadida", null),
                HttpStatus.OK);
    }


    @PostMapping(path = "/addtostart")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody Pet pet){
        listDECircularService.getPets().addToStart(pet);
        return new ResponseEntity<>(new ResponseDTO(200, "La mascota ha sido añadida al inicio", null),
                HttpStatus.OK);
    }

    @PostMapping(path = "/addbyposition/{position}")
    public ResponseEntity<ResponseDTO> addByPosition(@PathVariable int position, @RequestBody Pet pet){
        listDECircularService.getPets().addByPosition(pet, position);
        return new ResponseEntity<>(new ResponseDTO(200, "La mascota ha sido añadida en la posición dada", null),
                HttpStatus.OK);
    }

    @GetMapping(path = "/takeashower/{direction}")
    public ResponseEntity<ResponseDTO> takeShower(@PathVariable String direction){
        listDECircularService.getPets().takeShower(direction);
        return new ResponseEntity<>(new ResponseDTO(200, "Una mascota aletaria ha sido bañada", null),
                HttpStatus.OK);
    }

    @GetMapping("/changehead")
    public ResponseEntity<ResponseDTO> changeHead() {

        int  random = listDECircularService.getPets().changeHead();
        String identificationPet = listDECircularService.getPets().getHead().getData().getIdentificationPet();
        return new ResponseEntity<>(new ResponseDTO(200, "El numero aleatorio es: " + random + "  La nueva cabeza es: "+ identificationPet
                , null), HttpStatus.OK);
    }

}
