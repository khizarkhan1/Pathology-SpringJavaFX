package com.example.springjavafx.controllers;

import com.example.springjavafx.entities.User;
import com.example.springjavafx.repositories.UserRepository;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.springjavafx.Helper.goTo;

public class RegisterController implements Initializable {
	
	public JFXTextField userName;
	public JFXPasswordField password;
	public JFXPasswordField rePassword;
	public JFXComboBox<String> authorization;
	public JFXButton register;
	public Label userFieldLabel;
	public Label passwordLabel;
	public Label rePasswordLabel;
	
	@Value("${loginScene}")
	Resource loginScene;
	@Autowired
	private FXMLLoader loader;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ObservableList<String> authList = FXCollections.observableArrayList("User", "Admin");
		authorization.setItems(authList);
	}
	
	public void onRegister(ActionEvent event) throws IOException {
		Boolean isCorrect = false;
		userFieldLabel.setText("");
		passwordLabel.setText("");
		rePasswordLabel.setText("");
		
		//Validate that name has only alphabets.
		if (userName.getText().matches(".*\\d+.*")){
			userName.clear();
			userFieldLabel.setText("Enter Only Alphabets!(A-Z, a-z)");
		} else {
			userFieldLabel.setText("");
//			isCorrect = true;
		}
		
		//Validates password length is 8.
		if (password.getText().length() > 8){
			passwordLabel.setText("");
			//Validates if matches.
			if (password.getText().equals(rePassword.getText())){
				rePasswordLabel.setText("");
				isCorrect = true;
			} else {
				rePasswordLabel.setText("Password do not match!");
				passwordLabel.setText("Password do not match!");
				isCorrect = false;
				
			}
		} else {
			password.clear();
			rePassword.clear();
			passwordLabel.setText("Password Must be 8 characters long!");
			isCorrect = false;
		}
		
		if (isCorrect){
			User user = new User(userName.getText(), password.getText(), authorization.getValue());
			userRepository.save(user);
			goTo(event, loader, loginScene.getURL());
		}
		
	}
	
}
