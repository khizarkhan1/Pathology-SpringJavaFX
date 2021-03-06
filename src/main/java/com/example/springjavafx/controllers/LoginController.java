package com.example.springjavafx.controllers;

import com.example.springjavafx.entities.User;
import com.example.springjavafx.repositories.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static com.example.springjavafx.Helper.goTo;

@Slf4j
public class LoginController {
    @FXML
    public TextField userField;
    public TextField passwordField;
    public Label  alertLabel;

    @Value("${primaryScene}")
    public Resource primaryScene;
    @Value("${registerScene}")
    public Resource registerScene;
    @Autowired
    private FXMLLoader loader;
    @Autowired
    private UserRepository userRepository;
    
    public void onLogin(ActionEvent event) throws IOException {
        String username = userField.getText();
        String password = passwordField.getText();
        
        if (checkUserPassword(username, password)) {
            goTo(event, loader, primaryScene.getURL());
        } else {
            userField.setText("");
            passwordField.setText("");
            alertLabel.setText("Wrong Username or Password!");
        }
    }

    private boolean checkUserPassword(String username, String password) {
        User user = userRepository.findByNameAndPassword(username, password);
        if (user != null && user.getName().equals(username)){
            user.setActive(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    
    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER){
        
        }
    }
    
    public void onRegister(ActionEvent event) throws IOException {
        goTo(event, loader, registerScene.getURL());
    }
}
