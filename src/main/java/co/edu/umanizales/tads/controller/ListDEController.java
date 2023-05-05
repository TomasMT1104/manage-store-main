package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ListDEController {
    @Autowired
    private ListDEService listDEService;
    @Autowired
    private LocationService locationService;
}
