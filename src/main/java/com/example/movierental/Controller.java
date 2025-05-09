package com.example.movierental;

import com.example.movierental.model.User;
import com.example.movierental.model.UserNode;
import com.example.movierental.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    UserServices userServices;
    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Hello World");
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/profile")
    public String profile(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        String name = userServices.getUserName(username);
        model.addAttribute("name", name);
        String email = userServices.getEmail(username);
        model.addAttribute("email", email);
        String number = userServices.getNumber(username);
        model.addAttribute("number", number);
        return "profile";
    }

    @PostMapping("/registration")
    //@ResponseBody
    public String registration(Model model,
                               @RequestParam String name, @RequestParam String email, @RequestParam String number, @RequestParam String password, @RequestParam String repeatPassword) {
        if (!password.equals(repeatPassword)) {
            model.addAttribute("message2", "Passwords do not match");
           return "registration";
       }
        UserNode u1 = new UserNode(name, email, number, password);
        if(userServices.Adduser(u1))
            return "login";
        else {
            model.addAttribute("message1", "User already exists");
            return "registration";
        }
    }

    @PostMapping("/login")
    public String login(Model model, @RequestParam String name, @RequestParam String password) {
        if (userServices.LoginUser(name, password))
            return "redirect:/profile?username=" + name;
        else {
            model.addAttribute("message3", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/EditProfile")
    public String editProfile(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "EditProfile";
    }

    @PostMapping("/saveprofile")
    public String saveProfile(Model model, @RequestParam String username , @RequestParam String email,
                              @RequestParam String number, @RequestParam String oldPassword ,
                              @RequestParam String newPassword , @RequestParam String repeatPassword) {

        if (userServices.LoginUser(username, oldPassword)) {
            if (newPassword.equals(repeatPassword)) {
                UserNode u1 = new UserNode(username, email, number, newPassword);
                if(userServices.updateUser(username,u1)){
                    return "login";
                }else{
                    model.addAttribute("username", username);
                    model.addAttribute("message2", "User update failed");
                    return "/EditProfile";
                }
            }
            else {
                model.addAttribute("username", username);
                model.addAttribute("message2", "Passwords do not match");
                return "/EditProfile";
            }
        }
        else {
            model.addAttribute("username", username);
            model.addAttribute("message1", "Invalid Old password");
            return "EditProfile";
        }

    }

    @GetMapping("/EditProfileImage")
    public String editProfileImage(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "uploadImage";
    }


    @PostMapping("/uploadImage")
    public String uploadImage( Model model,@RequestParam String username,
                               @RequestParam("image") MultipartFile imageFile) {

        model.addAttribute("username", username);
        if (imageFile.isEmpty()) {
            model.addAttribute("message1", "No image file selected") ;
        }
        try {
            // Save the file with original filename
            String filePath = imageFile.getOriginalFilename();
            File dir = new File(username);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File dest = new File(username+".jpg");
            imageFile.transferTo(dest);

        } catch (IOException e) {
            model.addAttribute("message1", "Failed to save image") ;
        }
    return "profile";
    }

}

