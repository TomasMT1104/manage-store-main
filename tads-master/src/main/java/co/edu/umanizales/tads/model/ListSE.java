package co.edu.umanizales.tads.model;

import ch.qos.logback.core.joran.spi.ElementSelector;
import lombok.Data;

@Data
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
    public void add(Kid kid){
        if(head != null){
            Node temp = head;
            while(temp.getNext() !=null)
            {
                temp = temp.getNext();
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        }
        else {
            head = new Node(kid);
        }
        size ++;
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

    public void invert(){
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void orderBoysToStart(){
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getGender()=='M')
                {
                    listCp.addToStart(temp.getData());
                }
                else{
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
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

    //Ejercicios Parcial

    //Ejercicio 4
    //Dada una edad eliminar de la lista  a los niños de la edad dada
    public void deleteKidbyAge(Node head, byte age) {
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


    //Ejercicio 7
    // Método que me permia defirirle a un niño determinado que adelante un número dado de posiciones
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


    // Método para separar niños por ciudad
    public int getByLocationCode(String code) {
        int count = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count = count + 1;
                }
                temp = temp.getNext();
            }
        }
        return count;
}



}
