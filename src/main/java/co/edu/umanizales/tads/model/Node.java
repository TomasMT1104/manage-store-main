package co.edu.umanizales.tads.model;

import lombok.Data;

@Data
public class Node {
    public String name;
    private Kid data;
    private Node next;

    public Node(Kid data) {
        this.data = data;
    }
}
