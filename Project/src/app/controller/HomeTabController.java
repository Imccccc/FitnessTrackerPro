package app.controller;

import java.util.Optional;

import org.controlsfx.dialog.Dialogs;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import app.model.Activity;
import app.model.ActivityPlan;
import app.model.RealActivityPlan;
import app.model.Unit;
import app.MainApp;


public class HomeTabController {
	@FXML
	private TableView<RealActivityPlan> HomePageTable;
    @FXML
    private TableColumn<RealActivityPlan, String> ActivityName;
    @FXML
    private TableColumn<RealActivityPlan, Number> PlanCount;
    @FXML
    private TableColumn<RealActivityPlan, Number> UserCount;
    @FXML
    private Label HomepageLabel;
    
    private ObservableList<RealActivityPlan> activityData = FXCollections.observableArrayList();

    
    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    
    public HomeTabController() {
    	activityData.add(new RealActivityPlan(new ActivityPlan("c1", Unit.TIMES,15), 0));
    	activityData.add(new RealActivityPlan(new ActivityPlan("c2", Unit.TIMES,20), 0));
    	activityData.add(new RealActivityPlan(new ActivityPlan("c3", Unit.TIMES,35), 0));
    	activityData.add(new RealActivityPlan(new ActivityPlan("c4", Unit.TIMES,5), 0));
    	activityData.add(new RealActivityPlan(new ActivityPlan("c5", Unit.TIMES,25), 0));
    	activityData.add(new RealActivityPlan(new ActivityPlan("c6", Unit.TIMES,15), 0));
    	activityData.add(new RealActivityPlan(new ActivityPlan("c7", Unit.TIMES,15), 0));
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
    	ActivityName.setCellValueFactory(cellData -> cellData.getValue().getActivityPlan().getActivity().ActvityNameProperty());
    	PlanCount.setCellValueFactory(cellData -> cellData.getValue().getActivityPlan().plannedCountProperty());
    	UserCount.setCellValueFactory(cellData -> cellData.getValue().realCountProperty());
    	
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
    
    public void clickHandler(){
    	RealActivityPlan temp;
    	temp = HomePageTable.getSelectionModel().getSelectedItem();
    	if(temp != null){
   			Optional<String> response = Dialogs.create()
			        .title("Text Input Count")
			        .message("Please enter your planned count:")
			        .showTextInput("15");
   			response.ifPresent(count -> temp.setRealCount(Integer.parseInt(count)));
    	}
    	
    }
    
    
}
