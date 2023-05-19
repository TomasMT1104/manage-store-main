package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Range;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.RangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listse")
public class  ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;

    @Autowired
    private RangeService rangeService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.getKids().getHead(),null), HttpStatus.OK);
    }

    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert(){
        listSEService.invert();
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha invertido la lista",
                null), HttpStatus.OK);

    }

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        listSEService.getKids().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se han intercambiado los extremos",
                null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO) {
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe", null), HttpStatus.OK);
        }
        try {
            listSEService.getKids().add(new Kid(kidDTO.getIdentification(), kidDTO.getName(), kidDTO.getAge(), kidDTO.getGender(), location));

        } catch (ListSEException e){
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200,"Se ha adicionado el petacon",null),HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportKisLocationGenders(@PathVariable byte age) {
        ReportKidLocationGenderDTO report =
                new ReportKidLocationGenderDTO (locationService.getLocationsByCodeSize(8));
        listSEService.getKids()
                .getReportKidsByLocationGendersByAge (age,report);
        return new ResponseEntity<>(new ResponseDTO(
                200,report,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/orderboystostart")
    public ResponseEntity<ResponseDTO> addBoyStart() {
        try {
            if (listSEService.getKids() != null) {
                listSEService.getKids().addBoyStart();
                return new ResponseEntity<>(new ResponseDTO(200, "Se ha ordenado la lista con los niños al comienzo", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/alternatekids")
    public ResponseEntity<ResponseDTO> alternateKids() throws ListSEException {
        try {
            if (listSEService.getKids() != null) {
                listSEService.getKids().alternateKids();
                return new ResponseEntity<>(new ResponseDTO(200, "Se ha alternado la lista", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/reportkidsbyrangeage")
    public ResponseEntity<ResponseDTO> getReportKidsRangeByAge() throws ListSEException{
        try {
            List<RangeDTO> kidsRangeList = new ArrayList<>();
            if (listSEService.getKids() != null) {
                for (
                        Range i : rangeService.getRange()) {
                    int quantity = listSEService.getKids().getReportByRangeAge(i.getFrom(), i.getTo());
                    kidsRangeList.add(new RangeDTO(i,quantity));
                }
                return new ResponseEntity<>(new ResponseDTO(200, "El rango de los niños es: " + kidsRangeList, null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/movekidfinalbyletter/{initial}")
    public ResponseEntity<ResponseDTO> moveKidFinalByLetter(@PathVariable char initial) throws ListSEException{
        try {
            if (listSEService.getKids() != null) {
                listSEService.getKids().moveKidToTheEndByLetter(initial);
                return new ResponseEntity<>(new ResponseDTO(200,
                        "Se han enviado los niños con este inicial al final", null), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}




