package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.ListDECircular;
import co.edu.umanizales.tads.model.Pet;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class ListDECircularService {
    private ListDECircular pets;

    public ListDECircularService() {
        this.pets = new ListDECircular();
    }

    public void addToEnd(Pet pet) {
    }

    public void addToStart(Pet pet) {
    }

    public void addByPosition(Pet pet) {
    }

    public void takeShower(char direction) {
    }
}
