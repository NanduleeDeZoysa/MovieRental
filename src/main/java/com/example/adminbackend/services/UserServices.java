package com.example.movierental.services;

import com.example.movierental.model.User;
import com.example.movierental.model.UserList;
import com.example.movierental.model.UserNode;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//import java.util.LinkedList;

@Service
public class UserServices {

    public void saveUser(UserList list) {
        String filePath = "users.txt";  // Path to your file

        try {
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);

            while (!list.isEmpty()) {
                User user = list.deleteFirst();
                pw.println(user.toString());
            }
            pw.close();
            fw.close();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }

    public UserList readUsers() {
        String filePath = "users.txt";
        // LinkedList to store users
        UserList usersList = new UserList();
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                // Assuming file format is "Name:Email:Password"
                String[] userDetails = line.split(":");
                // Create a User object and add it to the LinkedList
                if (userDetails.length == 4) {
                    UserNode user = new UserNode(userDetails[0], userDetails[1], userDetails[2], userDetails[3]);
                    usersList.insertFirst(user);
                }
            }

            br.close();
            fr.close();

        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e);
        }
        return usersList;
    }

    public Boolean Adduser(UserNode user) {
        UserList usersList = this.readUsers();
        if (usersList.find(user.getName()) == null) {
            usersList.insertFirst(user);
            this.saveUser(usersList);
            return true;
        }
        return false;
    }

    public Boolean LoginUser(String name, String password) {
        UserList usersList = this.readUsers();
        UserNode user = usersList.find(name);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

    public String getUserName(String name) {
        UserList usersList = this.readUsers();
        UserNode user = usersList.find(name);
        if (user.getName().equals(name)) {
            return user.getName();
        }
        return "Not Found";
    }

    public String getEmail(String name) {
        UserList usersList = this.readUsers();
        UserNode user = usersList.find(name);
        if (user.getName().equals(name)) {
            return user.getEmail();
        }
        return "Not Found";
    }

    public String getNumber(String name) {
        UserList usersList = this.readUsers();
        UserNode user = usersList.find(name);
        if (user.getName().equals(name)) {
            return user.getNumber();
        }
        return "Not Found";
    }

    public Boolean updateUser(String name, UserNode user) {
        UserList usersList = this.readUsers();
        if (usersList.delete(name)) {
            usersList.insertFirst(user);
            this.saveUser(usersList);
            return true;
        }
        return false;
    }

    public Boolean deleteUser(UserList list, String name)
    {
        if (list.delete(name)) {
            this.saveUser(list);
        }
        return true;
    }

    public Optional<User> getfindById(String userID)
    {
        UserList usersList = this.readUsers();
        UserNode user = usersList.find(userID);
        if (user != null && user.getNumber() != null) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public void deleteUser(long userID)
    {
        UserList usersList = this.readUsers();
        if(usersList.delete(String.valueOf(userID)))
        {
            this.saveUser(usersList);
        }
    }

    public List<User> count()
    {
        UserList usersList = this.readUsers();
        List<User> userList = new ArrayList<>();
        UserNode current  = usersList.getFirst();
        while (current != null) {
            usersList.add(current);
            current = current.getNext();
        }
        return (List<User>) usersList;

    }


}

