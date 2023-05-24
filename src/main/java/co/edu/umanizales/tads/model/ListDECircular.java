package co.edu.umanizales.tads.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@Getter
@Setter
public class ListDECircular {
    private NodeDE head;
    private int size;
    private List<Pet> pets = new ArrayList<>();



    public List<Pet> printList() {
        pets.clear();
        NodeDE temp = head;
        do {
            pets.add(temp.getData());
            temp = temp.getNext();
        }
        while (temp != head);
        return pets;
    }

    //Metodo de añadir al inicio
    public void addToStart(Pet pet) {
    NodeDE newPet = new NodeDE(pet);
        if (this.head != null){
        NodeDE temp = this.head;
        while (temp.getNext() != this.head){
            temp = temp.getNext();
        }
        temp.setNext(newPet);
        newPet.setPrevious(temp);
        newPet.setNext(head);
        head.setPrevious(newPet);
        this.head=newPet;
    }
        else{
        newPet.setNext(newPet);
        newPet.setPrevious(newPet);
        this.head = newPet;
    }
    size++;
}



    //Metodo de añadir al final
    public void addToEnd(Pet pet) {
        NodeDE newPet = new NodeDE(pet);
        if (this.head != null){
            NodeDE temp = this.head;
            while (temp.getNext() != this.head){
                temp = temp.getNext();
            }
            temp.setNext(newPet);
            newPet.setPrevious(temp);
            newPet.setNext(this.head);
            this.head.setPrevious(newPet);
        }
        else{
            newPet.setNext(newPet);
            newPet.setPrevious(newPet);
            this.head=newPet;
        }
        size++;
    }

    //Metodo de insertar por posición
    public void addByPosition (Pet pet, int position){
     if (position < 0){
        throw new IllegalArgumentException("ERROR");
    }
    NodeDE newPet = new NodeDE(pet);
        if (this.head != null){
        NodeDE temp = this.head;
        if (position == 0) {
            while (temp.getNext() != this.head){
                temp = temp.getNext();
            }
            temp.setNext(newPet);
            newPet.setPrevious(temp);
            newPet.setNext(head);
            head.setPrevious(newPet);
            this.head=newPet;
        }
        else if (position >= size){
            while (temp.getNext() != this.head){
                temp = temp.getNext();
            }
            temp.setNext(newPet);
            newPet.setPrevious(temp);
            newPet.setNext(this.head);
            this.head.setPrevious(newPet);
        }
        else{
            for (int i = 0; i < position -1; i++){
                temp = temp.getNext();
            }
            newPet.setNext(temp.getNext());
            newPet.setPrevious(temp);
            temp.getNext().setPrevious(newPet);
            temp.setNext(newPet);
        }
    }
        else{
        newPet.setNext(newPet);
        newPet.setPrevious(newPet);
        this.head = newPet;
    }
    size++;
}


    //Metodo de bañar a la mascota
    public int takeShower (String direction) {
        if (head == null) {
            return -1;
        }
        int size = getSize();
        Random random = new Random();
        int randomPosition = random.nextInt(size) + 1;
        NodeDE temp = head;
        int cont = 1;
        if (direction.compareTo("L") == 0) {
            temp = head.getPrevious();
        }
        while (cont < randomPosition) {
            if (direction.compareTo("R") == 0) {
                temp = temp.getNext();
            } else if (direction.compareTo("L") == 0) {
                temp = temp.getPrevious();
            }
            cont++;
        }
        if (direction.compareTo("L") == 0) {
            temp = temp.getNext();
        }
        Pet pet = temp.getData();
        if (!pet.isBathed()) {
            pet.setBathed(true);
            return randomPosition;
        } else {
            return 0;
        }
    }

    //Metodo de cambiar la cabeza con un numero aleatorio
    public int changeHead() {
        Random rand = new Random();
        int randomNum = rand.nextInt(size + 1);
        NodeDE newHead = head;
        for (int i = 0; i < randomNum; i++) {
            newHead = newHead.getNext();
        }
        head = newHead;
        return randomNum;
    }
}