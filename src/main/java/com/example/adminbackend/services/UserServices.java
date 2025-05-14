package com.example.adminbackend.services;

import com.example.movierental.model.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.LinkedList;

@Service
public class UserServices {

    public void saveUser(LinkedList<User> list) {
        String filePath = "users.txt";  // Path to your file

        try {
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);

            while(!list.isEmpty()) {
                User user = list.removeFirst();
                pw.println(user.getName() + ":" + user.getEmail() + ":" + user.password);

            }
            pw.close();
            fw.close();

        } catch (IOException e){
            System.out.println("An error occurred: " + e);
        }
    }

    public LinkedList<User> readUsers() {
        String filePath = "users.txt";
        // LinkedList to store users
        LinkedList<User> usersList = new LinkedList<>();
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                // Assuming file format is "Name:Email:Password"
                String[] userDetails = line.split(":");
                // Create a User object and add it to the LinkedList
                if(userDetails.length==3) {
                    User user = new User(userDetails[0], userDetails[1], userDetails[2]);
                    usersList.add(user);
                }
            }

            br.close();
            fr.close();

        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e);
        }
        return usersList;
    }

    // Method to search for a user
    public boolean searchUser(LinkedList<User> usersList, String searchTerm) {
        for(User user: usersList){
            if (user.getName().equalsIgnoreCase(searchTerm))
                return true;  // Return the user if name or email matches
        }
        return false;  // Return null if user is not found
    }

    // Method to search for a user
    public <User> User deleteUser(LinkedList<User> usersList, String searchTerm) {
        for (User user : usersList) {
            if (user.getName().equalsIgnoreCase(searchTerm)) {
                return user;  // Return the user if name or email matches
            }
        }
        return null;  // Return null if user is not found
    }

    public Boolean Adduser(User user) {
        LinkedList<User> usersList = this.readUsers();
        if(!searchUser(usersList,user.getName())) {
            usersList.add(user);
            this.saveUser(usersList);
            return true;
        }
        return false;
    }

    public Boolean LoginUser(String name, String password) {
        LinkedList<User> usersList = this.readUsers();
        for(User user: usersList){
            if(user.getName().equals(name) && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
}
