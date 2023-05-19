package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class ListDECircular {
    private NodeDE head;
    private int size;
    private List<Pet> pets = new ArrayList<>();



    public ArrayList<Pet> printList(){
        ArrayList<Pet> pets = new ArrayList<>();
        if (this.head != null) {
            NodeDE temp = this.head;

            do {
                pets.add(temp.getData());
                temp = temp.getNext();
            } while (temp != this.head);
        }
        return pets;

    }

    //Metodo de añadir al inicio
    public void addToStart(Pet pet) throws ListDEException {
        NodeDE newNode = new NodeDE(pet);
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp.getNext() != head) {
                if (temp.getData().getIdentificationPet().equals(pet.getIdentificationPet())) {
                    throw new ListDEException("Ya existe una mascota con ese codigo");
                }
                temp = temp.getNext();
            }
            if (temp.getData().getIdentificationPet().equals(pet.getIdentificationPet())) {
                throw new ListDEException("Ya existe una mascota con ese codigo");
            }
            newNode.setNext(this.head.getNext());
            newNode.setPrevious(this.head);
            this.head.getNext().setPrevious(newNode);
            this.head.setNext(newNode);
            this.head = newNode;
        } else {
            addToEnd(pet);
        }
        size++;
    }



    //Metodo de añadir al final
    public void addToEnd(Pet pet) throws ListDEException {
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp.getNext() != head) {
                if (temp.getData().getIdentificationPet().equals(pet.getIdentificationPet())) {
                    throw new ListDEException("Ya existe una mascota con ese codigo");
                }
                temp = temp.getNext();
            }
            if (temp.getData().getIdentificationPet().equals(pet.getIdentificationPet())) {
                throw new ListDEException("Ya existe una mascota con ese codigo");
            }

            NodeDE newNode = new NodeDE(pet);
            newNode.setPrevious(this.head.getPrevious());
            this.head.getPrevious().setNext(newNode);
            newNode.setNext(this.head);
            this.head.setPrevious(newNode);
        } else {
            this.head = new NodeDE(pet);
            this.head.setPrevious(head);
            this.head.setNext(head);
        }
        size++;
    }


    //Metodo de insertar por posición
    public void addByPosition(Pet pet, int position2) throws ListDEException {
        NodeDE temp = head;
        NodeDE newNode = new NodeDE(pet);
        if (this.head == null) {
            addToEnd(pet);
            return;
        }
        if (position2 == 0) {
            addToStart(pet);
        }
        if (position2 > 0) {
            for (int i = 1; i < position2; i++) {
                temp = temp.getNext();
            }
            newNode.setPrevious(temp);
            newNode.setNext(temp.getNext());
            temp.getNext().setPrevious(newNode);
            temp.setNext(newNode);
        } else if (position2 < 0) {
            int position = position2 * (-1);
            for (int i = 1; i < position2; i++) {
                temp = temp.getPrevious();
            }
            newNode.setPrevious(temp.getPrevious());
            newNode.setNext(temp);
            temp.setPrevious(newNode);
            temp.getPrevious().setNext(newNode);
        }
        size++;

    }

    //Metodo de bañar a la mascota
    public void takeShower(char direction) throws ListDEException {
        if (this.head != null) {
            int val = (int) Math.floor(Math.random() * 1000);
            NodeDE temp = this.head;
            if (direction == 'i' || direction == 'I') {
                for (int i = 0; i < val; i++) {
                    temp = temp.getPrevious();
                }
                if (temp.getData().isBathed() == true) {
                    throw new ListDEException( "La mascota ya está bañada");
                } else {
                    temp.getData().setBathed(true);
                    return;
                }
            } else if (direction == 'd' || direction == 'D') {
                for (int i = 0; i < val; i++) {
                    temp = temp.getNext();
                }
                if (temp.getData().isBathed() == true) {
                    throw new ListDEException( "La mascota ya está bañada");
                } else {
                    temp.getData().setBathed(true);
                    return;
                }
            }else{
                throw new ListDEException("Envió mal la variable de dirección");
            }
        }
        throw new ListDEException("La lista está vacía");
    }
}

