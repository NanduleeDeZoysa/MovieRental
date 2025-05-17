package com.example.movierental;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServices {
    private static final String FILE_PATH = "users.txt";

    public synchronized void saveUser(LinkedList<User> list) {
        try (FileWriter fw = new FileWriter(FILE_PATH);
             PrintWriter pw = new PrintWriter(fw)) {
            for (User user : list) {
                pw.println(user.getName() + "::" + user.getPassword() + ":" +
                        String.join(",", user.getWatchlist().stream().map(String::valueOf).toArray(String[]::new)));
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public synchronized LinkedList<User> readUsers() {
        LinkedList<User> usersList = new LinkedList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return usersList;
        }

        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 3) {
                    User user = new User(parts[0], parts[2]); // name and password only
                    if (parts.length > 3 && !parts[3].isEmpty()) {
                        for (String id : parts[3].split(",")) {
                            try {
                                user.addToWatchlist(Long.parseLong(id));
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid watchlist ID format: " + id);
                            }
                        }
                    }
                    usersList.add(user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading users: " + e.getMessage());
        }
        return usersList;
    }

    public User getUserByName(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        return readUsers().stream()
                .filter(u -> username.equals(u.getName()))
                .findFirst()
                .orElse(null);
    }

    public synchronized void updateUserWatchlist(String username, List<Long> watchlist) {
        if (username == null || username.trim().isEmpty()) {
            return;
        }
        LinkedList<User> users = readUsers();
        users.stream()
                .filter(u -> username.equals(u.getName()))
                .findFirst()
                .ifPresent(u -> {
                    u.clearWatchlist();
                    watchlist.forEach(u::addToWatchlist);
                    saveUser(users);
                });
    }

    public synchronized boolean registerUser(User newUser) {
        if (newUser == null || newUser.getName() == null || newUser.getName().trim().isEmpty()) {
            return false;
        }
        LinkedList<User> users = readUsers();
        if (users.stream().noneMatch(u -> newUser.getName().equals(u.getName()))) {
            users.add(newUser);
            saveUser(users);
            return true;
        }
        return false;
    }

    public User loginUser(String name, String password) {
        if (name == null || password == null) {
            return null;
        }
        return readUsers().stream()
                .filter(u -> name.equals(u.getName()) && password.equals(u.getPassword()))
                .findFirst()
                .orElse(null);
    }
}