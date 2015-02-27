package app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Map.Entry;
import org.controlsfx.dialog.Dialogs;
import javafx.collections.FXCollections;
import javafx.scene.control.Dialog;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import app.MainApp;
import app.model.Activity;
import app.model.ActivityPlan;
import app.model.DayPlan;
import app.model.RealActivityPlan;
import app.ClassSerializer;

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
    Date date;
	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	String today;
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    
    public HomeTabController() {
    	date = new Date();
		today = formatter.format(date);

    	c = Calendar.getInstance();
    	c.setTime(date);
    	dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
    	
    	
    	if(MainApp.weekPlan==null)	{
    		System.out.println("Empty week plan");
    		return;}
    	if (ClassSerializer.TodayPlanUnserializer(today)!=null) {
    		activityData.addAll(ClassSerializer.TodayPlanUnserializer(today));
			System.out.println("Load day plan from TodayPlan.Fitness");
		}else{
	    	loadDayPlan(dayOfWeek-1, activityData);
			System.out.println("Load day plan from week plan");
			ClassSerializer.TodayPlanSerializer(activityData, today);
		}
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
       // System.out.println("initialize");
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
			ClassSerializer.TodayPlanSerializer(activityData, today);
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
    			ClassSerializer.TodayPlanSerializer(activityData, today);
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
