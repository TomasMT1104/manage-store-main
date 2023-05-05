package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.ListDE;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListDEService {
    private ListDE pets;
    public ListDEService() {


    }
}