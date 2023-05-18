package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.ListDECircular;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class ListDECircularService {
    private ListDECircular petCircular;

    public ListDECircularService() {
        this.petCircular = new ListDECircular();
    }

}
