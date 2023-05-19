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


    public void addPet(Pet newPet) {
        NodeDE newNode = new NodeDE(newPet);

        if(head == null) {
            head = newNode;
            newNode.setNext(head);
            newNode.setPrev(head);
        }else {
            NodeDE temp = head;
            if (temp.getPrev() == head) {
                temp.setNext(newNode);
                temp.setPrev(newNode);
                newNode.setNext(temp);
                newNode.setPrev(temp);
            } else {

                temp.getPrev().setNext(newNode);
                newNode.setPrev(temp.getPrev());
                temp.setPrev(newNode);
                newNode.setNext(temp);
            }
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
    public void addToStart(Pet newPet) {
        NodeDE newNode = new NodeDE(newPet);

        if(head == null) {
            head = newNode;
            newNode.setNext(head);
            newNode.setPrev(head);
        }
        NodeDE temp= head;
        if(temp.getPrev() == head){
            temp.setNext(newNode);
            temp.setPrev(newNode);
            newNode.setNext(temp);
            newNode.setPrev(temp);
            head=newNode;
        }else{

            temp.getPrev().setNext(newNode);
            newNode.setPrev(temp.getPrev());
            temp.setPrev(newNode);
            newNode.setNext(temp);
            head=newNode;
        }

        size++;
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
        NodeDE newNode = new NodeDE(pet);

        if (position <= 1 || head == null) {
            if (head == null) {
                head = newNode;
                newNode.setNext(head);
                newNode.setPrev(head);
            } else {
                NodeDE temp = head.getPrev();
                newNode.setNext(head);
                newNode.setPrev(temp);
                head.setPrev(newNode);
                temp.setNext(newNode);
                head = newNode;
            }
        } else {
            NodeDE prevNode = head;
            for (int i = 1; i < position && prevNode.getNext() != head; i++) {
                prevNode = prevNode.getNext();
            }

            NodeDE currentNode = prevNode.getNext();
            prevNode.setNext(newNode);
            newNode.setPrev(prevNode);
            newNode.setNext(currentNode);
            if (currentNode != head) {
                currentNode.setPrev(newNode);
            } else {
                head.setPrev(newNode);
            }
        }

        size++;
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
