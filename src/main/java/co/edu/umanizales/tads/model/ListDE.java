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
public class ListDE {
    private NodeDE headDE;
    private int size;
    private NodeDE head;
    private Object tail;
    private List<Pet> pets = new ArrayList<>();


    private void addToStart(Pet data) {
    }

    public NodeDE getHead() {
        return headDE;
    }

    public List <Pet> print(){
       if (size == 0){
           return null;
       }
        if (head != null){
            NodeDE temp = head;
            while (temp != null){
                pets.add(temp.getData());
                temp = temp.getNext();
            }
        }
        return pets;
    }


    public void winPositionPet(String identificationPet, int position) {
    }
    public void losePositionPet(String identificationPet, int positionpet) {
    }

    public void addPet(Pet pet) throws ListDEException{
        if (pet == null) {
            throw new ListDEException("La mascota no puede ser nulo");
        }
        if (head == null) {
            head = new NodeDE(pet);
        } else {
            NodeDE newNode = new NodeDE(pet);
            NodeDE current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
            newNode.setPrev(current);
        }
        size++;
    }

    public void addPetToStart(Pet data) throws ListDEException {
        if (headDE == null) {
            throw new ListDEException("La lista está vacía y no se puede realizar la operacion");
        }
        ListDE listCopy = new ListDE();
        NodeDE temp = headDE;
        while (temp != null) {
            if (temp.getData().getGender() == 'M') {
                listCopy.addPetToStart(temp.getData());
            } else {
                listCopy.addPet(temp.getData());
            }
            temp = temp.getNext();
        }
        headDE = listCopy.getHeadDE();
    }


    //Metodos Parcial

    //Metodo 1
    //Invertir la lista
    public void invert() throws ListDEException {
        if (this.headDE == null) {
            throw new ListDEException("No hay niños para poder invertir la lista");
        } else {
            ListDE listDLECp = new ListDE();
            NodeDE temp = this.headDE;
            while (temp != null) {
                listDLECp.addPetToStart(temp.getData());
                temp = temp.getNext();
            }
            this.headDE = listDLECp.getHeadDE();
        }
    }


    //Metodo 2 
    //Machos al inicio y Hembras al final
    public void getOrderMalesToStart() throws ListDEException {
        if (headDE == null) {
            throw new ListDEException("La lista está vacía y no se puede realizar la operacion");
        }
        ListDE listCopy = new ListDE();
        NodeDE temp = headDE;
        while (temp != null) {
            if (temp.getData().getGender() == 'M') {
                listCopy.addPetToStart(temp.getData());
            } else {
                listCopy.addPet(temp.getData());
            }
            temp = temp.getNext();
        }
        headDE = listCopy.getHeadDE();
    }

    //Metodo 3
    //Intercalar macho, hembra, macho, hembra
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
    //Dada una edad eliminar de la lista a las mascotas de la edad dada
    public void deletePetByAge(Byte age) throws ListDEException {
        if (age <= 0) {
            throw new ListDEException("La edad debe ser un valor positivo mayor que cero");
        }
        NodeDE temp = head;
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
    //Obtener el promedio de edad de las mascotas de la lista
    public int getLength() {
        int count = 0;
        NodeDE current = head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    public double getAverageAge() throws ListDEException {
        double averageAge = 0;
        NodeDE temp = this.head;
        if (this.head != null) {
            while (temp != null) {
                averageAge = averageAge + temp.getData().getAge();
                temp = temp.getNext();
            }
            averageAge = averageAge / getLength();
            return (double) averageAge;
        } else {
            throw new ListDEException("La lista está vacía");
        }
    }

    //Metodo 6
    //Generar un reporte que me diga cuantas mascotas hay de cada ciudad
    public int getCountPetByLocationCode(String code) throws ListDEException {
        if (code == null || code.isEmpty()) {
            throw new ListDEException("El código de ubicación no puede ser nulo o vacío");
        }
        int count = 0;
        if (this.head != null) {
            NodeDE current = this.head;
            while (current != null) {
                if (current.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                NodeDE previous = current;
                current = current.getNext();
                if (current != null) {
                    current.setPrevious(previous);
                }
            }
        } else {
            return 0;
        }
        return count;
    }

    //Metodo 7
    //Método que me permita defirirle a una mascota determinado que adelante un número dado de posiciones
    public void movePet(String identification, int posicion) {
        NodeDE current = head;
        NodeDE targetNode = null;
        int currentPos = 0;
        while (current != null) {
            if (current.getData().getIdentificationPet().equals(identification)) {
                targetNode = current;
                break;
            }
            current = current.getNext();
        }
        if (targetNode.getData() == null) {
            System.out.println("La mascota con esta identificación no está en la lista");
            return;
        }
        current = head;
        while (current != null && currentPos < posicion) {
            current = current.getNext();
            currentPos++;
        }
        if (currentPos < posicion) {
            System.out.println("La posición a la que desea moverse está más allá del final de la lista");
            return;
        }
        NodeDE prevNode = current.getPrevious();
        targetNode.setNext(current);
        targetNode.setPrev(prevNode);
        current.setPrev(targetNode);
        if (prevNode != null) {
            prevNode.setNext(targetNode);
        } else {
            head = targetNode;
        }
    }

    //Metodo 8
    //Método que me permita decirle a una mascota determinada que pierda un numero de posiciones dadas
    public int getPostByIdReverse(String id) throws ListDEException {
        if (head == null) {
            throw new ListDEException("La lista está vacía");
        }
        int count = getLength() - 1;
        NodeDE temp = (NodeDE) tail;
        while (temp != null) {
            if (temp.getData().getIdentificationPet().equals(id)) {
                return count;
            }
            temp = temp.getPrev();
            count--;
        }
        return -1;
    }

    //Metodo 9
    //Obtener un informe de mascotas por rango de edades
    public void reportByAge(byte minAge, byte maxAge) throws ListDEException {
        if (head == null) {
            throw new ListDEException("La lista está vacía.");
        }
        NodeDE current = head;
        boolean found = false;
        while (current != null) {
            byte edad = current.getData().getAge();
            if (edad >= minAge && edad <= maxAge) {
                String name = current.getData().getName();

                found = true;
            }
            current = current.getNext();
        }
        if (!found) {
            throw new ListDEException("No se encontraron mascotas dentro del rango de edad especificado.");
        }
    }

    //Metodo 10
    //Implementar un método que me permita enviar al final de la lista a las mascotas que su nombre inicie con una letra dada
    public void addToFinalPetbyLetter (char letter) throws ListDEException {
        if (head == null) {
            throw new ListDEException("La lista está vacía.");
        }
        ListDE listDECp = new ListDE();
        NodeDE temp = headDE;
        if (this.head != null) {
            while (temp!= null) {
                if (temp.getData().getName().startsWith(String.valueOf(letter))) {
                    listDECp.addPet(temp.getData());
                } else {
                    listDECp.addToStart(temp.getData());
                }
                temp = temp.getNext();
            }
        }
        this.head = listDECp.getHead();
    }




    // Método de eliminar camicase
    // Sustentación 08/05/2023

    /*
    Creo el  metodo deletePetbyIdentification para implementar el metodo.
    Ahora creo las excepciones que en este caso serian si la cabeza ve que no tiene datos se retornaria que no hay datos,
    y la otra sería que si la posicion dada es menor a 1 o es mayor a las mascotas que hay en la lista se retornaria que
    no se puede eliminar a la mascota, ya que la posicion no es valida o no se encuetra la mascota.
    Luego empiezo a nombrar el NodeDE como temp que seria el nodo temporal, luego tendria que utilizar el getNext que seria
    el nodo que buuscaria el temp hasta encontrar el nodo a elimina.
    Acá es donde entraria la cabeza que con el uso del getNext y del getPrevious se buscaria el nodo correspodiente que se
    quiere eliminar, para establecer la cabeza ahí, y así el return haría que se devuelva el nodo eliminado

     */

    public void deletePetbyIdentification(String identification) throws ListDEException {
        if (headDE == null) {
            throw new ListDEException("La lista está vacia");
        }
        NodeDE temp = headDE;
        NodeDE previousNode = null;

        while (temp != null) {
            temp = temp.getNext();
            if (temp.getName().equals(identification)) {
                if (previousNode != null) {
                    previousNode.setNext(temp.getNext());
                } else {
                    headDE = temp.getNext();
                }

                if (temp.getNext() != null) {
                    temp.getNext().setPrevious(previousNode);
                }

                temp.setPrevious(null);
                temp.setNext(null);

                return;
            }

            previousNode = temp;
            temp = temp.getNext();
        }

    }

}


