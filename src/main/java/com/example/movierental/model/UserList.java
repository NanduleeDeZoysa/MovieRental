package com.example.movierental.model;

public class UserList {
    private UserNode first;

    //Constructor
    public UserList() {
        this.first = null;
    }

    //isEmpty Method
    public boolean isEmpty() {
        return first == null;
    }

    //InsertFirst Method
    public void insertFirst(UserNode node) {
        UserNode newNode=new UserNode(node.getName(),node.getEmail(),node.getNumber(),node.getRole(),node.getPassword());
        newNode.next = this.first;
        first=newNode;
    }

    //InsertAfter Method
    public void insertAfter(String name,UserNode node) {
       UserNode  current = first;
        while (current != null) {
            if (current.name.equals(name)) {
                UserNode newNode = new UserNode(node.getName(),node.getEmail(),node.getNumber(), node.getRole(), node.getPassword());
                newNode.next = current.next;
                current.next = newNode;
                break;
            } else {
                current = current.next;
            }
        }
    }

    //Find Method
    public UserNode find(String name) {
        UserNode current = first;
        while (current != null) {
            if (current.name.equals(name)) {
                return current;
            } else {
                current = current.next;
            }
        }
        return null;
    }

    //DeleteFirst() Method
    public UserNode deleteFirst() {
        UserNode temp = first;
        first = first.next;
        return temp;
    }

    //DeleteAfter Method
    public boolean delete(String name) {
        UserNode current = first;
        UserNode previous = first;

        while (current != null) {
            if (current.name.equals(name)) {
                if (current == first) {
                    first = first.next;
                } else {
                    previous.next = current.next;
                }
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    //Display Method
    public void displayList() {
        UserNode current = first;
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
        System.out.println();
    }
}
