package app.controller;

import impl.org.controlsfx.i18n.Localization;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.ButtonBar.ButtonType;
import org.controlsfx.control.action.AbstractAction;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;

import app.model.dayAmount;
import DBconnector.DBconnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class MainController {
	@FXML
	Button registerButton;
	@FXML
	Button loginButton;
	@FXML
	Button searchButton;
	@FXML
	TextField searchField;
	@FXML
	TextField username;
	
	@FXML
	Tab ShareTab;
	@FXML
	ActivityListController planTabController;
	@FXML
	WishListTabController wishListTabController;
	@FXML
	ShareTabController shareTabController;
	@FXML 
	HomeTabController hometabController;
	
	
	PasswordField password;
	PasswordField password_confirm;
	
	public MainController(){

	}
	
	
    @FXML
    private void initialize() {
    	// Initialize the controller
    	shareTabController.init(this);
    	wishListTabController.init(this);
    	planTabController.init(this);
    	
    	// Set some access control
    	ShareTab.setDisable(true);
    	planTabController.sharePlanButton.setDisable(true);
    	searchButton.setVisible(false);
    	searchField.setVisible(false);
    	searchField.setPromptText("Compete a friend");
    }
	
	
	@FXML
	public void registerButtonClicked(){
		if(DBconnector.username != null){
			DBconnector.username = null;
			UpdateToolBar();
			return;
		}
		username = new TextField();
		password = new PasswordField();
		password_confirm = new PasswordField();
		AtomicBoolean username_flag = new AtomicBoolean(true);
		AtomicBoolean password_flag = new AtomicBoolean(true);
		AtomicBoolean passwordConf_flag = new AtomicBoolean(true);

		Dialog dlg = new Dialog( registerButton, "Registration");
    	Localization.setLocale(new Locale("en", "EN"));
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

			int ret = DBconnector.createUser(usernameString, passString);//0 success -1fail -2already exist
			if(ret==-2){
				showMessageDialog("Registration Dialog", "Sorry, your username has been already taken. Please choose another one.");
			}
			else if(ret==-1){
				showMessageDialog("Registration Dialog", "Sorry, some errors just happen. Please try again.");
			}
			else if(ret==0){
				showMessageDialog("Registration Dialog", "Congratulations! Your registration succeed!");

				d.hide();
				DBconnector.username = usernameString;
				UpdateToolBar();
				// Show sharetab
				ShareTab.setDisable(false);
				//Enable share button, etc.
				planTabController.sharePlanButton.setDisable(false);
			}
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

	private boolean checkUsername(String usernameString) { // Only check alphanumeric
		if(!usernameString.matches("[A-Za-z0-9]+")){
			showMessageDialog("Registration Dialog", "Error! Your username is not alphanumeric! Please only include number and character in your username.");			
			return false;
		}

		return true;
	}
	

	private void showMessageDialog(String title, String message){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();	
	}
	
	private void UpdateToolBar() {
		if(DBconnector.username != null){
			registerButton.setText("Logout");
			//loginButton.setVisible(false);
			loginButton.setText(DBconnector.username);
			//loginButton.setDisable(true);
			searchButton.setVisible(true);
			searchField.setVisible(true);
		}
		else{
			registerButton.setText("Register");
			loginButton.setText("Login");
			loginButton.setVisible(true);
			//loginButton.setDisable(false);
			searchButton.setVisible(false);
			searchField.setVisible(false);
			ShareTab.setDisable(true);
			planTabController.sharePlanButton.setDisable(true);
		}
	}
	
	@FXML
	public void loginButtonClicked(){
		if(DBconnector.username != null){
			return;
		}
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
	    	Localization.setLocale(new Locale("en", "EN"));
			// Validate the username (whether alphanumeric)
			String usernameString = username.getText();
			if(!usernameString.matches("[A-Za-z0-9]+")){
				showMessageDialog("Login Dialog", "Error! Your username is not alphanumeric! Your username sholud only contain numbers and characters..");			
				return;
			}
			
			// Validate the password (whether length in 6~32)
			String passString = password.getText();
			if(passString.length() < 6 || passString.length() > 32){
				showMessageDialog("Login Dialog", "Error! Your password should in the range of 6~32. Please enter again.");
				return;
			}

			// Check with DB
			int ret = DBconnector.login(usernameString, passString);  //0 success -1fail -2dne -3wrong pass			
			if(ret==-2){
				// Do not exist current username
				showMessageDialog("Login Dialog", "The username you entered does not exist!");
			}
			else if(ret==-3){
				// Wrong password
				showMessageDialog("Login Dialog", "Wrong password! please try again.");
			}
			else if(ret==0){
				// Success
				showMessageDialog("Login Dialog", "Welcome back to the Fitness Track Pro! Your login request succeed!");
			
				d.hide();
				DBconnector.username = usernameString;
				UpdateToolBar();
				
				ShareTab.setDisable(false);
				planTabController.sharePlanButton.setDisable(false);
			}
			else{
				// Other error
				showMessageDialog("Login Dialog", "Sorry, some error happens, please try again.");
			}
		}
	};
	
	
	public void searchClick(){
		boolean userExist = true;
		
		//String username = searchField.getText();
		//ArrayList<app.model.dayAmount> amountlist = DBconnector.getExerciseAmount("lhc");
		
		if(userExist){
    		Dialog dialog = new Dialog(null, "Compete result");
    		GridPane grid = new GridPane();
    		
    		// Setup the graph
    		CategoryAxis xAxis = new CategoryAxis(HomeTabController.dayNames);
            NumberAxis yAxis = new NumberAxis();
    		AreaChart<String, Number> areaChart = new AreaChart<>(xAxis, yAxis);
    		
    		XYChart.Series<String, Number> myDataSeries = new XYChart.Series<String, Number>();
    		myDataSeries.setName("My Daily Calories");
    		
    		double maxRange = 0;
    		for(dayAmount day :HomeTabController.pastCalories) {
    			myDataSeries.getData().add(new XYChart.Data<String, Number>(day.getDate().toString().substring(4,10), day.getAmount()));
    			if(maxRange < day.getAmount()) {
    				maxRange = day.getAmount();
    			}
    		}
    		yAxis = new NumberAxis(0, maxRange+200, 200);
    		yAxis.setAutoRanging(false);
        	areaChart.getData().clear();
        	areaChart.getData().add(myDataSeries);
    		
    		areaChart.setTitle("Compete Graph");
    		
    		
    		

    		// Set layout
    		grid.setHgap(10);
    		grid.setVgap(10);
    		grid.setPadding(new Insets(20, 150, 10, 10));
    		grid.add(new Label("Compete result"), 0, 0);
    		grid.add(areaChart, 1, 0);
    		dialog.setContent(grid);
    		dialog.show();
		}else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Sorry");
			alert.setHeaderText(null);
			alert.setContentText("No such user!\nInvite your firend!");
			alert.showAndWait();
		}
	}
}
