package app.controller;

import java.util.concurrent.atomic.AtomicBoolean;

import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.ButtonBar.ButtonType;
import org.controlsfx.control.action.AbstractAction;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;

import com.oracle.xmlns.internal.webservices.jaxws_databinding.ExistingAnnotationsType;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;

public class MainController {
	@FXML
	Button registerButton;
	@FXML
	Button loginButton;

	TextField username;
	PasswordField password;
	PasswordField password_confirm;

	@FXML
	public void registerButtonClicked(){
		username = new TextField();
		password = new PasswordField();
		password_confirm = new PasswordField();
		AtomicBoolean username_flag = new AtomicBoolean(true);
		AtomicBoolean password_flag = new AtomicBoolean(true);
		AtomicBoolean passwordConf_flag = new AtomicBoolean(true);

		Dialog dlg = new Dialog( registerButton, "Registration");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		grid.add(new Label("Enter your username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Enter a password:"), 0, 1);
		grid.add(password, 1, 1);
		grid.add(new Label("Confirm your password:"), 0, 2);
		grid.add(password_confirm, 1, 2);

		ButtonBar.setType(actionRegister, ButtonType.OK_DONE);
		actionRegister.disabledProperty().set(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
			username_flag.set(newValue.trim().isEmpty());
			actionRegister.disabledProperty().set(username_flag.get() || password_flag.get() || passwordConf_flag.get());
		});
		password.textProperty().addListener((observable, oldValue, newValue) -> {
			password_flag.set(newValue.trim().isEmpty());
			actionRegister.disabledProperty().set(username_flag.get() || password_flag.get() || passwordConf_flag.get());
		});
		password_confirm.textProperty().addListener((observable, oldValue, newValue) -> {
			passwordConf_flag.set(newValue.trim().isEmpty());
			actionRegister.disabledProperty().set(username_flag.get() || password_flag.get() || passwordConf_flag.get());
		});

		dlg.setMasthead("Please enter a username and set password to register an account");
		dlg.setContent(grid);
		dlg.getActions().addAll(actionRegister, Dialog.Actions.CANCEL);

		dlg.show();
	}

	final Action actionRegister = new AbstractAction("Confirm") {
		// This method is called when the login button is clicked ...
		public void handle(ActionEvent ae) {			
			Dialog d = (Dialog) ae.getSource();
			
			String usernameString = username.getText();
			if(!checkUsername(usernameString))
				return;
			
			String passString = password.getText();
			String passConfString = password_confirm.getText();
			if(!checkPassword(passString, passConfString))
				return;

			showMessageDialog("Registration Dialog", "Congratulations! Your registration succeed!");
			
			d.hide();
		}

	};

	private boolean checkPassword(String passString, String passConfString) {
		if(!passString.equals(passConfString)){
			showMessageDialog("Registration Dialog", "Error! Password inconsistent! Please confirm again.");
			return false;
		}
		if(passString.length() < 6 || passString.length() > 32){
			showMessageDialog("Registration Dialog", "Error! Your password must in the range of 6~32. Please enter again.");
			return false;
		}
		return true;
	}

	private boolean checkUsername(String usernameString) {
		if(!usernameString.matches("[A-Za-z0-9]+")){
			showMessageDialog("Registration Dialog", "Error! Your username is not alphanumeric! Please only include number and character in your username.");			
			return false;
		}
//		if(usernameString.length() < 6 || usernameString.length() > 32){
//			showMessageDialog("Error! Your username must in the range of 6~32. Please enter again.");
//			return false;
//		}
		if(existUsername(usernameString)){
			showMessageDialog("Registration Dialog", "Sorry, your username has already existed. Please enter another one.");
			return false;
		}
		return true;
	}
	
	
	private boolean existUsername(String usernameString) {
		// TODO Auto-generated method stub
		return false;
	}

	private void showMessageDialog(String title, String message){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();	
	}
	
	@FXML
	public void loginButtonClicked(){
		username = new TextField();
		password = new PasswordField();
		AtomicBoolean username_flag = new AtomicBoolean(true);
		AtomicBoolean password_flag = new AtomicBoolean(true);

		Dialog dlg = new Dialog( loginButton, "Login");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		grid.add(new Label("Enter your username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Enter a password:"), 0, 1);
		grid.add(password, 1, 1);

		ButtonBar.setType(actionLogin, ButtonType.OK_DONE);
		actionLogin.disabledProperty().set(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
			username_flag.set(newValue.trim().isEmpty());
			actionLogin.disabledProperty().set(username_flag.get() || password_flag.get());
		});
		password.textProperty().addListener((observable, oldValue, newValue) -> {
			password_flag.set(newValue.trim().isEmpty());
			actionLogin.disabledProperty().set(username_flag.get() || password_flag.get());
		});


		dlg.setMasthead("Please enter a username and set password to log into your account");
		dlg.setContent(grid);
		dlg.getActions().addAll(actionLogin, Dialog.Actions.CANCEL);

		dlg.show();
	}

	final Action actionLogin = new AbstractAction("Confirm") {
		// This method is called when the login button is clicked ...
		public void handle(ActionEvent ae) {			
			Dialog d = (Dialog) ae.getSource();
			
			String usernameString = username.getText();
			if(!usernameString.matches("[A-Za-z0-9]+")){
				showMessageDialog("Login Dialog", "Error! Your username is not alphanumeric! Your username sholud only contain numbers and characters..");			
				return;
			}
			
			String passString = password.getText();
			if(passString.length() < 6 || passString.length() > 32){
				showMessageDialog("Login Dialog", "Error! Your password should in the range of 6~32. Please enter again.");
				return;
			}

			if(!checkUsernamePassworPair(usernameString, passString)){				
				return;
			}

			showMessageDialog("Login Dialog", "Welcome back to the Fitness Track Pro! Your login request succeed!");
			
			d.hide();
		}

		private boolean checkUsernamePassworPair(String usernameString, String passString) {
			// TODO Auto-generated method stub
			// Should contain two cases, they popup different error messages
			// Case 1: Username does not exist
			
			// Case 2: Username exists, but password does not match
			
			return true;
		}

	};
}
