package com.example.movierental.model;

public class AdminList {
    private AdminNode first;

    public AdminList() {
        this.first = null;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void insertFirst(AdminNode node) {
        AdminNode newNode = new AdminNode(node.getName(), node.getEmail(), node.getNumber(), node.getRole(), node.getPassword());
        newNode.next = this.first;
        first = newNode;
    }

    public AdminNode find(String name) {
        AdminNode current = first;
        while (current != null) {
            if (current.name.equals(name)) return current;
            current = current.next;
        }
        return null;
    }

    public boolean delete(String name) {
        AdminNode current = first;
        AdminNode previous = first;

        while (!current.name.equals(name)) {
            if (current.next == null) return false;
            previous = current;
            current = current.next;
            break;
        }
        if (current == first) {
            first = first.next;
        } else {
            previous.next = current.next;
        }
        return true;
    }

    public AdminNode deleteFirst() {
        AdminNode temp = first;
        first = first.next;
        return temp;
    }
}
