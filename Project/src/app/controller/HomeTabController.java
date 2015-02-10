package app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import app.model.Activity;
import app.MainApp;


public class HomeTabController {
	@FXML
	private TableView<Activity> HomePageTable;
    @FXML
    private TableColumn<Activity, String> ActivityName;
    @FXML
    private TableColumn<Activity, String> PlanCount;
    @FXML
    private TableColumn<Activity, String> UserCount;
    @FXML
    private Label HomepageLabel;
    
    private ObservableList<Activity> activityData = FXCollections.observableArrayList();

    
    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public HomeTabController() {
    	activityData.add(new Activity("c1", 1));
    	activityData.add(new Activity("c2", 2));
    	activityData.add(new Activity("c3", 3));
    	activityData.add(new Activity("c4", 4));
    	activityData.add(new Activity("c5", 5));
    	activityData.add(new Activity("c6", 6));
    	activityData.add(new Activity("c7", 7));
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
    	ActivityName.setCellValueFactory(cellData -> cellData.getValue().ActvityNameProperty());
    	PlanCount.setCellValueFactory(cellData -> cellData.getValue().planCountProperty());
    	UserCount.setCellValueFactory(cellData -> cellData.getValue().userCountProperty());
    	
        HomePageTable.setItems(activityData);
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
    }
    
    
}
