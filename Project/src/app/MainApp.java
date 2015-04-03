package app;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import app.model.Activity;
import app.model.WeekPlan;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	// Global variable
	public static WeekPlan weekPlan;
	public static ObservableList<Activity> activities = 
	        FXCollections.observableArrayList();
	
	
    private Stage primaryStage;
    private AnchorPane rootLayout;

    private ObservableList<Activity> activityData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp(){
    	
    }
    
    public ObservableList<Activity> getActivityData() {
        return activityData;
    }
    
    private void chooseDefaultPlan(){
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Select deault plan");
    	alert.setContentText("Choose your option.");

    	ButtonType buttonTypeOne = new ButtonType("Fat Burn");
    	ButtonType buttonTypeTwo = new ButtonType("Muscle Building");
    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

    	alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

    	Optional<ButtonType> result = alert.showAndWait();
    	
		File plan1 = new File("./Fat");
		File plan2 = new File("./Muscle");
		File empty = new File("./Empty");
		File weekplan = new File("./weekPlan");
    	
    	if (result.get() == buttonTypeOne){
    		if (plan1.exists()) {
				plan1.renameTo(weekplan);
				plan2.delete();
				empty.delete();
			}
    	} else if (result.get() == buttonTypeTwo) {
    		if (plan1.exists()) {
				plan1.renameTo(weekplan);
				plan2.delete();
				empty.delete();
			}
    	} else {
    		if (empty.exists()) {
    			empty.renameTo(weekplan);
				plan1.delete();
				plan2.delete();
			}
    	}
    }
    
    @Override
    public void start(Stage primaryStage) {
    	File file = new File("weekPlan");
    	if (!file.exists()) {
    		chooseDefaultPlan();
		}
    	
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Fitness Tracker Pro");
        
        // Load weekPlan
        weekPlan = ClassSerializer.WeekPlanUnserializer();
        // Load activity list
        activities.addAll(ClassSerializer.ActivityUnserializer());
        
        initRootLayout();
        //DBconnector.login("lhc","123");
        //DBconnector.writePlan(weekPlan);
    }
    
    
	/**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/app/view/MainLayout.fxml"));
            rootLayout = (AnchorPane)loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
