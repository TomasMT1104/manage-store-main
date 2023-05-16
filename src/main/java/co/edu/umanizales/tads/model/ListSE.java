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


    //Metodo 3
    //Intercalar niño, niña, niño, niña
    public void getAlternateKids() throws ListSEException {
        Node boys = head;
        Node girls = head.getNext();
        Node girlsHead = girls;
        if (head == null || head.getNext() == null) {
            throw new ListSEException("La lista esta vacia o solo tiene un elemento");
        }
        while (girls != null && boys != null) {
            boys.setNext(girls.getNext());
            if (girls.getNext() != null) {
                girls.setNext(girls.getNext().getNext());
            }
            boys = boys.getNext();
            girls = girls.getNext();
        }
        if (girls == null) {
            boys.setNext(girlsHead);
        } else {
            girls.setNext(girlsHead);
        }
    }

    //Metodo 4
    //Dada una edad eliminar de la lista  a los niños de la edad dada
    public void deleteKidbyAge(Node head, byte age) throws ListSEException {
        if (age <= 0) {
            throw new ListSEException ("La edad debe ser un valor positivo mayor que cero");
        }
        Node temp = this.head;
        ListSE listSECp = new ListSE();
        while(temp!=null) {
            if (temp.getData().getAge() != age) {
                listSECp.addToStart(temp.getData());
                temp.getNext();
            }
        }
        this.head = listSECp.getHead();
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
    public void MoveKid(String id, int posicion) {
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
        while (cont < posicion - 1 && act != null) {
            objetive = act;
            act = act.getNext();
            cont++;
        }
        if (cont < posicion - 1) {
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
    public void ReportByAge(byte minAge, byte maxAge) throws ListSEException {
        Node current = head;
        boolean found = false;
        while (current != null) {
            byte edad = current.getData().getAge();
            if (edad >= minAge && edad <= maxAge) {
                String name = current.getData().getName();
                // Aquí puedes hacer lo que quieras con los datos del niño encontrado
                found = true;
            }
            current = current.getNext();
        }
        if (!found) {
            throw new ListSEException("No se encontraron niños dentro del rango de edad especificado.");
        }
    }

    //Metodo 10
    //Implementar un método que me permita enviar al final de la lista a los niños que su nombre inicie con una letra dada
    public void moveKid(char letter) throws ListSEException {
        if (head == null) {
            throw new ListSEException("La lista está vacía");
        }
        Node prev = null;
        Node current = head;
        Node last = null;
        while (current != null) {
            if (current.name.startsWith(String.valueOf(letter))) {
                if (prev == null) {
                    head = current.getNext();
                } else {
                    prev.setNext(current.getNext());
                }
                if (last == null) {
                    last = current;
                } else {
                    last.setNext(current);
                    last = current;
                }
                current = current.getNext();
                last.setNext(null);
            } else {
                prev = current;
                current = current.getNext();
            }
        }
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
