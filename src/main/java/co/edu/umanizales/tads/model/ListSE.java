package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.ReportKidLocationGenderDTO;
import co.edu.umanizales.tads.exception.ListSEException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ListSE {
    private Node head;
    private int size;
    /*
    Algoritmo de adicionar al final
    Entrada
        un niño
    si hay datos
    si
        llamo a un ayudante y le digo que se posicione en la cabeza
        mientras en el brazo exista algo
            pasese al siguiente
        va estar ubicado en el ùltimo

        meto al niño en un costal (nuevo costal)
        y le digo al ultimo que tome el nuevo costal
    no
        metemos el niño en el costal y ese costal es la cabeza
     */
    public void add(Kid kid) throws ListSEException {
        if (head != null) {
            Node temp = head;
            while (temp.getNext() != null) {
                if (temp.getData().getIdentification().equals(kid.getIdentification())) {
                    throw new ListSEException(("Ya existe un niño"));
                }
                temp = temp.getNext();
            }
            if (temp.getData().getIdentification().equals(kid.getIdentification())) {
                throw new ListSEException("Ya existe un niño");
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        } else {
            head = new Node(kid);
        }
        size++;
    }

    /* Adicionar al inicio
    si hay datos
    si
        meto al niño en un costal (nuevocostal)
        le digo a nuevo costal que tome con su brazo a la cabeza
        cabeza es igual a nuevo costal
    no
        meto el niño en un costal y lo asigno a la cabez
     */
    public void addToStart(Kid kid){
        if(head !=null)
        {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        }
        else {
            head = new Node(kid);
        }
        size++;
    }

    public void changeExtremes(){
        if(this.head !=null && this.head.getNext() !=null)
        {
            Node temp = this.head;
            while(temp.getNext()!=null)
            {
                temp = temp.getNext();
            }
            //temp está en el último
            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }

    }


    // Método para separar niños por ciudad
    public int getCountKidsByLocationCode(String code){
        int count =0;
        if( this.head!=null){
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    //Métodos Parcial

    //Metodo 1
    //Invertir la lista
    public void invert() throws NullPointerException{
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
        else {
            throw new NullPointerException("Cabeza es nulo");
        }
    }

    //Metodo 2
    //Niños al inicio y niñas al final
    public void addBoyStart() throws ListSEException {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = head;

            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.add(temp.getData());
                }
                temp = temp.getNext();
            }
            head = listCp.getHead();
        } else {
            throw new ListSEException("No hay niños para completar esta operacion");
        }
    }

    //Metodo 3
    //Intercalar niño, niña, niño, niña
    public void alternateKids() throws ListSEException {
        ListSE alternateList = new ListSE();

        ListSE listBoys = new ListSE();
        ListSE listGirls = new ListSE();

        Node temp = head;

        if (this.head == null && this.head.getNext() == null) {
            throw new ListSEException("No existen niños o no hay suficientes para alternar");
        } else {
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listBoys.add(temp.getData());
                } else {
                    if (temp.getData().getGender() == 'F') {
                        listGirls.add(temp.getData());
                    }
                }
                temp = temp.getNext();
            }

            Node boysNode = listBoys.getHead();
            Node girlsNode = listGirls.getHead();

            while (boysNode != null) {
                if (boysNode != null) {
                    alternateList.add(boysNode.getData());
                    boysNode = boysNode.getNext();
                }
                if (girlsNode != null) {
                    alternateList.add(girlsNode.getData());
                    girlsNode = girlsNode.getNext();
                }
            }
            this.head = alternateList.getHead();
        }
    }

    //Metodo 4
    //Dada una edad eliminar de la lista  a los niños de la edad dada
    public void deleteKidbyAge(byte age)  throws ListSEException {
        Node temp = head;
        ListSE listcopy = new ListSE();
        if (age <= 0) {
            throw new ListSEException("La edad debe ser mayor que cero");
        } else {
            if (this.head == null) {
                throw new ListSEException("No existen niños para realizar la operación");
            } else {

                while (temp != null) {
                    if (temp.getData().getAge() != age) {
                        listcopy.addToStart(temp.getData());
                    }
                    temp = temp.getNext();
                }
                this.head = listcopy.getHead();
            }
        }
    }


    //Metodo 5
    //Obtener el promedio de edad de los niños de la lista
    public int getLength()  {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    public double getAverageAge() throws ListSEException {
        double averageAge = 0;
        Node temp = this.head;
        if (this.head != null) {
            while (temp != null) {
                averageAge = averageAge + temp.getData().getAge();
                temp = temp.getNext();
            }
            averageAge = averageAge / getLength();
            return averageAge;

        }else {
            throw new ListSEException("La lista está vacía");
        }
    }

    //Metodo 6
    //Generar un reporte que me diga cuantos niños hay de cada ciudad
    public int getCountKidByLocationCode(String code) throws ListSEException {
        if (code == null || code.isEmpty()){
            throw new ListSEException("El código de ubicación no puede ser nulo o vacío");
        }
        int count = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        } else {
            return 0;

        }
        return count;
    }


    //Metodo 7
    //Método que me permita defirirle a un niño determinado que adelante un número dado de posiciones
    public void moveKid(String id, int position) {
        Node act = head;
        Node ant = null;
        Node objetive = null;
        int cont = 0;
        while (act != null) {
            if (act.getData().equals(id)) {
                objetive = act;
                break;
            }
            ant = act;
            act = act.getNext();
        }
        if (objetive == null) {
            System.out.print("El niño con esta identificación no está en la lista");
            return;
        }
        act = head;
        while (cont < position - 1 && act != null) {
            objetive = act;
            act = act.getNext();
            cont++;
        }
        if (cont < position - 1) {
            System.out.print("La posición que desea está más allá de lo que va la lista");
            return;
        }
        objetive.setNext(act);
        if (objetive == head) {
            head = act;
        }
    }

    //Metodo 8
    //Método que me permita decirle a un niño determinado que pierda un numero de posiciones dadas
    public int getPostById(String id) throws ListSEException {
        if (head == null) {
            throw new ListSEException("La lista está vacía");
        }
        int count = 0;
        Node temp = head;
        while (temp != null) {
            if (temp.getData().getIdentification().equals(id)) {
                return count;
            }
            temp = temp.getNext();
            count++;
        }
        return -1;
    }

    //Metodo 9
    //Obtener un informe de niños por rango de edades
    public int getReportByRangeAge(int letter, int last) throws ListSEException {
        Node temp = head;
        int count = 0;

        if (this.head == null) {
            throw new ListSEException("No existen niños para poder realizar la operación");
        } else {
            while (temp != null) {
                if (temp.getData().getAge() >= letter && temp.getData().getAge() <= last) {
                    count++;
                }
                temp = temp.getNext();
            }
            return count;
        }
    }

    //Metodo 10
    //Implementar un método que me permita enviar al final de la lista a los niños que su nombre inicie con una letra dada
    public void moveKidToTheEndByLetter(char letter) throws ListSEException {
        ListSE listCopy = new ListSE();
        Node temp = this.head;

        if (this.head == null) {
            throw new ListSEException("No existen niños para poder realizar la operación");
        } else {
            while (temp != null) {
                if (temp.getData().getName().charAt(0) != Character.toUpperCase(letter)) {
                    listCopy.addToStart(temp.getData());
                }
            }
            temp = temp.getNext();
        }

        temp = this.head;

        if (this.head == null) {
            throw new ListSEException("No existen niños para poder realizar la operación");
        } else {
            while (temp != null) {
                if (temp.getData().getName().charAt(0) == Character.toUpperCase(letter)) {
                    listCopy.add(temp.getData());
                }
                temp = temp.getNext();
            }
        }
        this.head = listCopy.getHead();
    }

    public void getReportKidsByLocationGendersByAge(byte age, ReportKidLocationGenderDTO report){
        if(head !=null){
            Node temp = this.head;
            while(temp!=null){
                if(temp.getData().getAge()>age){
                    report.updateQuantity (
                            temp.getData().getLocation().getName(),
                            temp.getData().getGender());
                }
                temp = temp.getNext();
            }
        }
    }

}
