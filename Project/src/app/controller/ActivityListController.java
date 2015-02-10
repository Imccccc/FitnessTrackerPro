package app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import app.MainApp;
import app.model.*;

public class ActivityListController {

	@FXML
	private ListView<String> activityList;
	
	public static final ObservableList<String> activities = 
	        FXCollections.observableArrayList();
	
	// Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ActivityListController() {
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the list with the activities.
    	activities.addAll("Adam", "Alex", "Alfred", "Albert",
                "Brenda", "Connie", "Derek", "Donny", 
                "Lynne", "Myrtle", "Rose", "Rudolph", 
                "Tony", "Trudy", "Williams", "Zach");
    	activityList.setItems(activities);
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        //personTable.setItems(mainApp.getPersonData());
    }
}
