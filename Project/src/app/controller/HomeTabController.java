package app.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Map.Entry;

import org.controlsfx.dialog.Dialogs;

import com.mysql.fabric.xmlrpc.base.Data;

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
import app.MainApp;
import app.model.Activity;
import app.model.ActivityPlan;
import app.model.DayPlan;
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
    Calendar c;
    int dayOfWeek;
    Date date = new Date();
    // Reference to the main application.

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    
    public HomeTabController() {
    	c = Calendar.getInstance();
    	c.setTime(date);
    	dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
    	
    	if(MainApp.weekPlan==null)	{
    		System.out.println("Empty week plan");
    		return;
    	}
    	
    	loadDayPlan(dayOfWeek, activityData);
    }
    
	private void loadDayPlan(int index, ObservableList<RealActivityPlan> dayList) {
		DayPlan dayplan = MainApp.weekPlan.getDayPlan(index);
		for(Entry<String, ActivityPlan> entry : dayplan.getDayPlan().entrySet()){
            ActivityPlan a = entry.getValue();
            dayList.add(new RealActivityPlan(a, 0));
        }
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
    		Dialog<String> dialog = new Dialog<>();
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
    		
    		for (Activity a : MainApp.activities) {
				activitylist.getItems().add(a.getActvityName());
			}
    		activitylist.setPromptText("Activity");
    		
    		grid.add(new Label("Activity:"), 0, 0);
    		grid.add(activitylist, 1, 0);


    		dialog.getDialogPane().setContent(grid);
    		dialog.setResultConverter(dialogButton -> {
    		    if (dialogButton == loginButtonType) {
    		        return new String(activitylist.getValue() );
    		    }
    		    return null;
    		});
    		
    		Optional<String> result = dialog.showAndWait();

    		result.ifPresent(Activityname -> {
    			Activity temp = null;
    		    System.out.println("Activity=" + Activityname );
    		    for (Activity a : MainApp.activities) {
					if (a.getActvityName().equals(Activityname)) {
						temp = new Activity(a);
					}
				}
    		    activityData.add(new RealActivityPlan(new ActivityPlan(temp, 0), 0));
    		});
    		return true;
    	}catch(Exception e){
    		e.printStackTrace();
    		System.out.println("Something wrong");
    		return false;
    	}
    	
    }
    
    
    private int RealActivityPlan(ActivityPlan activityPlan) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void buttonClickHnadler(){
    	 System.out.println("Button clicked");
    	 boolean okClicked = openNewDialog();
    }
    
}
