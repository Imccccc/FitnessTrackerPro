package app;

import java.io.IOException;
import app.controller.ActivityListController;
import app.controller.HomeTabController;
import app.model.Activity;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;
     
    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Activity> activityData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp(){
    	
    }
    
    public ObservableList<Activity> getActivityData() {
        return activityData;
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainLayout.fxml"));
            rootLayout = (AnchorPane)loader.load();

            // Give the controller access to the main app.
            //ActivityListController controller = loader.getController();
            //controller.setMainApp(this);
            
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
