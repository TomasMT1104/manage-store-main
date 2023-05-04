package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListDE {
    private NodeDE headDE;
    private int size;
    private NodeDE head;
    private Object tail;

    private void addToStart(Pet data) {
    }

    public NodeDE getHead() {
        return headDE;
    }

    public void add(Pet pet) {
        if (headDE != null) {
            NodeDE temp = headDE;
            while (temp.getNext() != null) {
                temp.getNext();
            }
            NodeDE newNodeDE = new NodeDE(pet);
            temp.setNext(newNodeDE);
            newNodeDE.setNext(newNodeDE);
        } else {
            headDE = new NodeDE(pet);
        }

    }


    //Metodos Parcial

    //Metodo 1
    //Invertir la lista
    public void invert() throws NullPointerException {
        if (this.head != null) {
            ListDE listCp = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
            NodeDE currentNode = this.head;
            while (currentNode.getNext() != null) {
                NodeDE nextNode = currentNode.getNext();
                nextNode.setPrev(currentNode);
                currentNode = nextNode;
            }
        } else {
            throw new NullPointerException("Cabeza es nulo");
        }
    }
    
    //Metodo 2 
    //Niños al inicio y niñas al final 
    public void getorderBoysToStart() throws ListDEException {
        if (this.head != null) {
            ListDE listDoublyLinked = new ListDE();
            NodeDE temp = this.head;
            NodeDE lastBoy = null;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    if (lastBoy != null) {
                        listDoublyLinked.addToStart(lastBoy.getData());
                    }
                    lastBoy = temp;
                } else {
                    listDoublyLinked.add(temp.getData());
                }
                temp = temp.getNext();
            }
            if (lastBoy != null) {
                listDoublyLinked.addToStart(lastBoy.getData());
            }
            this.head = listDoublyLinked.getHead();
            this.tail = listDoublyLinked.getTail();
        } else {
            throw new ListDEException ("La lista está vacía");
        }
    }

    //Metodo 3
    //Intercalar niño, niña, niño, niña
    public void getAlternatePets() throws ListDEException {
        NodeDE males = head;
        NodeDE females = head.getNext();
        NodeDE femalesHead = females;
        if (head == null || head.getNext() == null) {
            throw new ListDEException("La lista esta vacia o solo tiene un elemento");
        }
        while (females != null && males != null) {
            males.setNext(females.getNext());
            if (females.getNext() != null) {
                females.getNext().setPrevious(females.getPrevious());
            }
            females.setPrevious(males);
            males = males.getNext();
            if (males != null) {
                females = males.getNext();
            }
        }
        if (females == null) {
            males.setNext(femalesHead);
            if (femalesHead != null) {
                femalesHead.setPrev(males);
            }
        } else {
            females.setPrevious(males);
            if (males != null) {
                males.setNext(females);
            }
        }
    }

    //Metodo 4
    //Dada una edad eliminar de la lista  a los niños de la edad dada
    public void deletePetbyAge(Node head, byte age) throws ListDEException {
        if (age <= 0) {
            throw new ListDEException("La edad debe ser un valor positivo mayor que cero");
        }
        Node temp = head;
        ListDE listDLECp = new ListDE();
        while (temp != null) {
            if (temp.getData().getAge() != age) {
                listDLECp.addToStart(temp.getData());
            }
            temp = temp.getNext();
        }
        if (listDLECp.getHead() == null) {
            throw new ListDEException("No hay mascotas con la edad dada en la lista");
        }
        this.head = listDLECp.getHead();
        this.head.setPrev(null);
        NodeDE tail = this.head;
        while (tail.getNext() != null) {
            tail = tail.getNext();
            tail.setPrev(tail.getPrevious().getPrevious());
        }
    }

    //Metodo 5
    //Obetener el promedio de edad de las mascotas de la lista






}
