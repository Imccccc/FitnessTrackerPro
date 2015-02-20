package app.controller;

import java.util.Optional;

import org.controlsfx.dialog.Dialogs;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import app.model.Activity;
import app.model.ActivityPlan;
import app.model.RealActivityPlan;
import app.model.Unit;


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
    
    public void clickHandler(){
    	RealActivityPlan temp;
    	temp = HomePageTable.getSelectionModel().getSelectedItem();
    	if(temp != null){
   			Optional<String> response = Dialogs.create()
			        .title("Text Input Count")
			        .message("Please enter your planned count:")
			        .showTextInput(Integer.toString(temp.getRealCount()));
   			response.ifPresent(count -> temp.setRealCount(Integer.parseInt(count)));
    	}
    }
    
    public boolean openNewDialog(){
    	try{
    		Dialog<Pair<String, String>> dialog = new Dialog<>();
    		dialog.setTitle("Add new Activity");
    		dialog.setHeaderText("Select from the activity from Drop Down"
    				+ "\nThen input the count");

    		ButtonType loginButtonType = new ButtonType("Record", ButtonData.OK_DONE);
    		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
    		
    		GridPane grid = new GridPane();
    		grid.setHgap(10);
    		grid.setVgap(10);
    		grid.setPadding(new Insets(20, 150, 10, 10));
    		
    		ComboBox<String> activitylist = new ComboBox<String>();
    		activitylist.getItems().addAll(
    				new String("c1"),
    				new String("c2"),
    				new String("c1"));
    		activitylist.setPromptText("Activity");
    		
    		ComboBox<String> unitlist = new ComboBox<String>();
    		unitlist.getItems().addAll(
    				new String("time"),
    				new String("mins"));
    		unitlist.setPromptText("Unit");
    		
    		grid.add(new Label("Activity:"), 0, 0);
    		grid.add(activitylist, 1, 0);
    		grid.add(new Label(" Count :"), 0, 1);
    		grid.add(unitlist, 1, 1);

    		dialog.getDialogPane().setContent(grid);
    		dialog.setResultConverter(dialogButton -> {
    		    if (dialogButton == loginButtonType) {
    		        return new Pair<>(activitylist.getValue() , unitlist.getValue());
    		    }
    		    return null;
    		});
    		
    		Optional<Pair<String, String>> result = dialog.showAndWait();

    		result.ifPresent(ActivityUnit -> {
    		    System.out.println("Activity=" + ActivityUnit.getKey() + ", Unit=" + ActivityUnit.getValue());
    		    if(ActivityUnit.getKey()!=null && ActivityUnit.getValue() != null){
    		    	Activity temp = new Activity(ActivityUnit.getKey(), Unit.TIMES);
    		    }
    		});
    		return true;
    	}catch(Exception e){
    		e.printStackTrace();
    		System.out.println("Something wrong");
    		return false;
    	}
    	
    }
    
    
    public void buttonClickHnadler(){
    	 System.out.println("Button clicked");
    	 boolean okClicked = openNewDialog();
    }
    
}
