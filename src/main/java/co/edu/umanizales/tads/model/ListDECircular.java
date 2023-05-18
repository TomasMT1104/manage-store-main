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


    public void addPet(Pet pet) {
        if (pet == null) {
            return;
        }

        NodeDE newNode = new NodeDE(pet);

        if (head == null) {
            head = newNode;
            head.setNext(newNode);
            head.setPrev(newNode);
        } else {
            NodeDE lastNode = head.getPrev();
            newNode.setNext(head);
            newNode.setPrev(lastNode);
            lastNode.setNext(newNode);
            head.setPrev(newNode);
        }

        size++;
    }

    public List <Pet> print(){
        pets.clear();

        NodeDE temp = head;
        do {

            pets.add(temp.getData());
            temp = temp.getNext();


        } while (temp != head);
        return pets;
    }

    //Metodo de añadir al inicio
    public void addToStart(Pet pet) {
        if (head == null) {
            addPet(pet);
        }else{
            NodeDE newNode = new NodeDE(pet);
            NodeDE temp = head.getPrev();
            temp.setNext(newNode);
            newNode.setPrev(temp);
            newNode.setNext(head);
            head.setPrev(newNode);
            head=newNode;
            size++;
        }
    }

    //Metodo de añadir al final
    public void addToEnd(Pet pet) {
        if (head == null) {
            addPet(pet);
        } else {
            NodeDE newNode = new NodeDE(pet);
            NodeDE lastNode = head.getPrev();
            lastNode.setNext(newNode);
            newNode.setPrev(lastNode);
            newNode.setNext(head);
            head.setPrev(newNode);
            size++;
        }
    }

    //Metodo de insertar por posición
    public void addByPosition(Pet pet, int position) {
        if (position == 1) {
            addToStart(pet);
        } else {
            NodeDE temp = head;
            int count = 1;

            while (count < position -1) {
                temp = temp.getNext();
                count++;
            }
            NodeDE newNode = new NodeDE(pet);
            newNode.setNext(temp.getNext());
            newNode.setPrev(temp);
            temp.getNext().setPrev(newNode);
            temp.setNext(newNode);
            size++;
        }
    }

    //Metodo de bañar a la mascota
    public Pet takeShower(char direction) {

        Random rand = new Random();
        int randomNum = rand.nextInt(size + 1);

        NodeDE temp = head;
        if (direction == 'd') {
            for (int i = 1; i < randomNum; i++) {
                temp = temp.getNext();
            }
        }
        if (direction == 'i') {
            for (int i = size; i > randomNum; i--) {
                temp = temp.getPrev();
            }
        }
        Pet pet = temp.getData();
        if (pet.getStatus() != true) {
            temp.getData().setStatus(true);
            return pet;
        } else {
            return null;
        }
    }

}
