package com.example.movierental.model;

//fixed-size array.
public class MyStack {
    private int maxSize;         // max size of stack
    private Movie[] stackArray;  // array to hold Movie objects
    private int top;             // index of top element

    public MyStack(int s) {
        maxSize = s;
        stackArray = new Movie[maxSize];
        top = -1; // empty stack
    }

    public void push(Movie m) {
        if (top < maxSize - 1) {
            top++;
            stackArray[top] = m;
        } else {
            // If stack is full, shift left to remove the oldest
            for (int i = 0; i < maxSize - 1; i++) {
                stackArray[i] = stackArray[i + 1];
            }
            stackArray[top] = m;
        }
    }

    //remove and return the last pushed movie.
    public Movie pop() {
        if (isEmpty()) {
            return null;
        }
        return stackArray[top--];
    }

    //return the top movie.
    public Movie peek() {
        if (isEmpty()) {
            return null;
        }
        return stackArray[top];
    }

    //Checks if the stack has no items.
    public boolean isEmpty() {
        return top == -1;
    }

    //Returns the number of items in the stack.
    public int size() {
        return top + 1;
    }


    //a new array containing all movies in the stack from bottom to top.
    //Used to send the stack to the UI (HTML/Thymeleaf) or APIs.
    public Movie[] toArray() {
        Movie[] result = new Movie[top + 1];
        for (int i = 0; i <= top; i++) {
            result[i] = stackArray[i];
        }
        return result;
    }

}
