package app.controller;

import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.ButtonBar.ButtonType;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.AbstractAction;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import app.ClassSerializer;
import app.FileIO;
import app.MainApp;
import app.model.Activity;
import app.model.ActivityPlan;
import app.model.DayPlan;
import app.model.RealActivityPlan;
import app.model.Unit;
import app.model.WeekPlan;


public class ActivityListController {
	private boolean dragable =false;
	private String dragFrom = "";
	private boolean removeAct = false;
	private int removeIndex = -1;
	
	@FXML
	private Button editPlanButton;
	@FXML
	private Button addActivityButton;
	@FXML
	private TableView<Activity> activityList;
	@FXML
	private TableColumn<Activity, String> nameList;
	@FXML
	private TableView<ActivityPlan> sundayList;
	@FXML
	private TableColumn<ActivityPlan, String> sundayList_actCol;
	@FXML
	private TableColumn<ActivityPlan, Number> sundayList_cntCol;
	@FXML
	private TableView<ActivityPlan> mondayList;
	@FXML
	private TableColumn<ActivityPlan, String> mondayList_actCol;
	@FXML
	private TableColumn<ActivityPlan, Number> mondayList_cntCol;
	@FXML
	private TableView<ActivityPlan> tuesdayList;
	@FXML
	private TableColumn<ActivityPlan, String> tuesdayList_actCol;
	@FXML
	private TableColumn<ActivityPlan, Number> tuesdayList_cntCol;
	@FXML
	private TableView<ActivityPlan> wednesdayList;
	@FXML
	private TableColumn<ActivityPlan, String> wednesdayList_actCol;
	@FXML
	private TableColumn<ActivityPlan, Number> wednesdayList_cntCol;
	@FXML
	private TableView<ActivityPlan> thursdayList;
	@FXML
	private TableColumn<ActivityPlan, String> thursdayList_actCol;
	@FXML
	private TableColumn<ActivityPlan, Number> thursdayList_cntCol;
	@FXML
	private TableView<ActivityPlan> fridayList;
	@FXML
	private TableColumn<ActivityPlan, String> fridayList_actCol;
	@FXML
	private TableColumn<ActivityPlan, Number> fridayList_cntCol;
	@FXML
	private TableView<ActivityPlan> saturdayList;
	@FXML
	private TableColumn<ActivityPlan, String> saturdayList_actCol;
	@FXML
	private TableColumn<ActivityPlan, Number> saturdayList_cntCol;
	
	public static final ObservableList<Activity> activities = 
	        FXCollections.observableArrayList();
	public static final ObservableList<ActivityPlan> sundayActivities = 
	        FXCollections.observableArrayList();
	public static final ObservableList<ActivityPlan> mondayActivities = 
	        FXCollections.observableArrayList();
	public static final ObservableList<ActivityPlan> tuesdayActivities = 
	        FXCollections.observableArrayList();
	public static final ObservableList<ActivityPlan> wednesdayActivities = 
	        FXCollections.observableArrayList();
	public static final ObservableList<ActivityPlan> thursdayActivities = 
	        FXCollections.observableArrayList();
	public static final ObservableList<ActivityPlan> fridayActivities = 
	        FXCollections.observableArrayList();
	public static final ObservableList<ActivityPlan> saturdayActivities = 
	        FXCollections.observableArrayList();

	TextField actitvityName;
	ChoiceBox choiceBox;
	
	final Action actionLogin = new AbstractAction("Confirm") {
	    // This method is called when the login button is clicked ...
	    public void handle(ActionEvent ae) {
	        Dialog d = (Dialog) ae.getSource();
	        String newActName = actitvityName.getText();
	        Unit newActUnit = choiceBox.getSelectionModel().getSelectedItem().equals("Times")?Unit.TIMES:Unit.MINUTE;
	        System.out.println(newActName);
	        System.out.println(newActUnit.toString());
	        System.out.println(newActName);
	        activities.add(new Activity(newActName, newActUnit));
	        ClassSerializer.ActivitySerializer(activities);
	        d.hide();
	    }
	};


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
    	activities.addAll(ClassSerializer.ActivityUnserializer());
    	loadWeekPlan();
        // Initialize the list with the activities.
//    	activities.addAll(new Activity("Adam", Unit.TIMES),
//    						new Activity("Alex", Unit.TIMES),
//    						new Activity("Alfred", Unit.TIMES),
//    						new Activity("Albert", Unit.TIMES),
//    						new Activity("Brenda", Unit.TIMES),
//    						new Activity("Connie", Unit.TIMES),
//    						new Activity("Derek", Unit.TIMES),
//    						new Activity("Donny", Unit.TIMES),
//    						new Activity("Lynne", Unit.TIMES),
//    						new Activity("Myrtle", Unit.TIMES),
//    						new Activity("Rose", Unit.TIMES),
//    						new Activity("Rudolph", Unit.TIMES),
//    						new Activity("Tony", Unit.TIMES),
//    						new Activity("Trudy", Unit.TIMES),
//    						new Activity("Williams", Unit.TIMES),
//    						new Activity("Zach", Unit.TIMES));

    	activityList.setItems(activities);
    	nameList.setCellValueFactory(cellData -> cellData.getValue().ActvityNameProperty());
    	
    	// Set all 7 days data binding
    	sundayList_actCol.setCellValueFactory(cellData -> cellData.getValue().getActivity().ActvityNameProperty());
    	sundayList_cntCol.setCellValueFactory(cellData -> cellData.getValue().plannedCountProperty());
    	sundayList.setItems(sundayActivities);
    	mondayList_actCol.setCellValueFactory(cellData -> cellData.getValue().getActivity().ActvityNameProperty());
    	mondayList_cntCol.setCellValueFactory(cellData -> cellData.getValue().plannedCountProperty());
    	mondayList.setItems(mondayActivities);
    	tuesdayList_actCol.setCellValueFactory(cellData -> cellData.getValue().getActivity().ActvityNameProperty());
    	tuesdayList_cntCol.setCellValueFactory(cellData -> cellData.getValue().plannedCountProperty());
    	tuesdayList.setItems(tuesdayActivities);
    	wednesdayList_actCol.setCellValueFactory(cellData -> cellData.getValue().getActivity().ActvityNameProperty());
    	wednesdayList_cntCol.setCellValueFactory(cellData -> cellData.getValue().plannedCountProperty());
    	wednesdayList.setItems(wednesdayActivities);
    	thursdayList_actCol.setCellValueFactory(cellData -> cellData.getValue().getActivity().ActvityNameProperty());
    	thursdayList_cntCol.setCellValueFactory(cellData -> cellData.getValue().plannedCountProperty());
    	thursdayList.setItems(thursdayActivities);
    	fridayList_actCol.setCellValueFactory(cellData -> cellData.getValue().getActivity().ActvityNameProperty());
    	fridayList_cntCol.setCellValueFactory(cellData -> cellData.getValue().plannedCountProperty());
    	fridayList.setItems(fridayActivities);
    	saturdayList_actCol.setCellValueFactory(cellData -> cellData.getValue().getActivity().ActvityNameProperty());
    	saturdayList_cntCol.setCellValueFactory(cellData -> cellData.getValue().plannedCountProperty());
    	saturdayList.setItems(saturdayActivities);
    	
    	initializeListeners();
    }
    
    private void loadWeekPlan() {    	
    	if(MainApp.weekPlan==null)	{
    		System.out.println("Empty week plan");
    		return;
    	}
    	
		loadDayPlan(1, mondayActivities);
		loadDayPlan(2, tuesdayActivities);
		loadDayPlan(3, wednesdayActivities);
		loadDayPlan(4, thursdayActivities);
		loadDayPlan(5, fridayActivities);
		loadDayPlan(6, saturdayActivities);
		loadDayPlan(0, sundayActivities);
	}

	private void loadDayPlan(int index, ObservableList<ActivityPlan> dayList) {
		DayPlan dayplan = MainApp.weekPlan.getDayPlan(index);
		for(Entry<String, ActivityPlan> entry : dayplan.getDayPlan().entrySet()){
            ActivityPlan a = entry.getValue();
            dayList.add(a);
        }
	}

	@FXML
    public void addActivityButtonClicked(){
    	actitvityName = new TextField();
    	ObservableList<String> choices =  FXCollections.observableArrayList();
    	choices.add("Times");
    	choices.add("Minute");
    	choiceBox = new ChoiceBox<>(choices); 
    	choiceBox.setValue("Times");
    	
    	Dialog dlg = new Dialog( addActivityButton, "Add new activity");

    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(0, 10, 0, 10));

    	grid.add(new Label("Activity Name:"), 0, 0);
    	grid.add(actitvityName, 1, 0);
    	grid.add(new Label("Choose Unit:"), 0, 1);
    	grid.add(choiceBox, 1, 1);

 	    ButtonBar.setType(actionLogin, ButtonType.OK_DONE);
    	actionLogin.disabledProperty().set(true);

        // Do some validation (using the Java 8 lambda syntax).
    	actitvityName.textProperty().addListener((observable, oldValue, newValue) -> {
            actionLogin.disabledProperty().set(newValue.trim().isEmpty());
        });
    	
    	dlg.setMasthead("Please add a new activity by entering the activity name and select how this activity will be measured");
    	dlg.setContent(grid);
    	dlg.getActions().addAll(actionLogin, Dialog.Actions.CANCEL);

    	dlg.show();
    }
    
    @FXML
    public void editPlanButtonClicked(){
    	if(dragable){
    		saveWeekPlan();
    		editPlanButton.setText("Edit");
    	}
    	else{
    		editPlanButton.setText("Finish");
    	}
    	dragable = !dragable;
    }
    
    private void saveWeekPlan() {

    	ObservableList<DayPlan> dayPlanList = FXCollections.observableArrayList();
    	dayPlanList.add(generateDayPlan(sundayActivities));
    	dayPlanList.add(generateDayPlan(mondayActivities));
    	dayPlanList.add(generateDayPlan(tuesdayActivities));
    	dayPlanList.add(generateDayPlan(wednesdayActivities));
    	dayPlanList.add(generateDayPlan(thursdayActivities));
    	dayPlanList.add(generateDayPlan(fridayActivities));
    	dayPlanList.add(generateDayPlan(saturdayActivities));   	
    	SimpleListProperty<DayPlan> dayPlanListProperty = new SimpleListProperty<>(dayPlanList);
    	
    	WeekPlan newWeekPlan = new WeekPlan(dayPlanListProperty);
    	MainApp.weekPlan = newWeekPlan;
       	ClassSerializer.WeekPlanSerializer(MainApp.weekPlan);
	}

	private DayPlan generateDayPlan(ObservableList<ActivityPlan> activitiesList) {
		ObservableMap<String, ActivityPlan> omap = FXCollections.observableHashMap();
		for(ActivityPlan activityPlan : activitiesList){
			omap.put(activityPlan.getActivity().getActvityName(), activityPlan);
		}
		MapProperty<String, ActivityPlan> mapProperty = new SimpleMapProperty<>(omap);
		DayPlan dayPlan = new DayPlan(mapProperty);
		
		return dayPlan;
	}

	@FXML
    public void clearPlanButtonClicked(){
    	sundayActivities.clear();
    	mondayActivities.clear();
    	tuesdayActivities.clear();
    	wednesdayActivities.clear();
    	thursdayActivities.clear();
    	fridayActivities.clear();
    	saturdayActivities.clear();
    }
    
    private void initializeListeners()
    {
    	activityList.setOnDragDetected(new EventHandler<MouseEvent>()
    	{
    		@Override
    		public void handle(MouseEvent event)
    		{
    			if(dragable){
    				//System.out.println("setOnDragDetected");
    				dragFrom = "activityList";
    				Dragboard dragBoard = activityList.startDragAndDrop(TransferMode.MOVE);
    				ClipboardContent content = new ClipboardContent();
    				String copyString = activityList.getSelectionModel().getSelectedItem().getActvityName()
    						+"|"+activityList.getSelectionModel().getSelectedItem().getUnit().toString();
    				//System.out.println(copyString);
    				content.putString(copyString);
    				dragBoard.setContent(content);
    			}
    		}
    	});    
  	
    	activityList.setOnDragDone(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("activityList->setOnDragDone");    

    		}
    	});

    	
    	
    	sundayList.setOnDragDetected(new EventHandler<MouseEvent>()
    	{
    		@Override
    		public void handle(MouseEvent event)
    		{
    			if(dragable && sundayActivities.size()>0){
    				System.out.println("sunday->setOnDragDetected");
    				dragFrom = "sundayList";
    				Dragboard dragBoard = sundayList.startDragAndDrop(TransferMode.MOVE);
    				ClipboardContent content = new ClipboardContent();
    				// Format String as: Name|Unit|Count
    				String copyString = sundayList.getSelectionModel().getSelectedItem().getActivity().getActvityName() +
    						"|" + sundayList.getSelectionModel().getSelectedItem().getActivity().getUnit().toString() +
    						"|" + sundayList.getSelectionModel().getSelectedItem().getPlannedCount();
    				//System.out.println(copyString);
    				content.putString(copyString);
    				removeAct = true;
    				removeIndex = sundayList.getSelectionModel().getSelectedIndex();
    				//System.out.println(removeIndex);
    				dragBoard.setContent(content);
    			}
    		}
    	});   
    	
    	sundayList.setOnDragDone(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("sunday->setOnDragDone");    
    			if(removeAct){
    				//remove that selected activity
    				sundayActivities.remove(removeIndex);
    				removeAct = false;
    				//System.out.println(sundayActivities.size());
    			}
    			removeIndex = -1;
    		}
    	});   	
    	
    	sundayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("sunday->setOnDragEntered");    
    			sundayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	sundayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("sunday->setOnDragExited");  
    			sundayList.setBlendMode(null);
    		}
    	});
    
    	sundayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("sunday->setOnDragOver");
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	sundayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("sunday->setOnDragDropped");
    			if(dragFrom.equals("sundayList")){ 
    				removeAct = false;
    				return;
    			}
    			String newActivity = dragEvent.getDragboard().getString();
    			// Split the string by "|"
    			System.out.println(newActivity);
				String[] info = newActivity.split("\\|");
				
				
    			if(dragFrom.equals("activityList")){     				
    				// Pop up a dialog to accept planed count
    				Optional<String> response = Dialogs.create()
    						.title("Text Input Count")
    						.message("Please enter your planned count:")
    						.showTextInput("15");
    			
    				// Add activity to the correspond observableList
    				response.ifPresent(count -> sundayActivities.add(new ActivityPlan(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE, Integer.parseInt(count))));
    			}
    			else{// Comes from other dayList
    				sundayActivities.add(new ActivityPlan(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE, Integer.parseInt(info[2])));
    			}
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	
    	
    	mondayList.setOnDragDetected(new EventHandler<MouseEvent>()
    	{
    		@Override
    		public void handle(MouseEvent event)
    		{
    			if(dragable && mondayActivities.size()>0){
    				System.out.println("monday->setOnDragDetected");
    				dragFrom = "mondayList";
    				Dragboard dragBoard = mondayList.startDragAndDrop(TransferMode.MOVE);
    				ClipboardContent content = new ClipboardContent();
    				// Format String as: Name|Unit|Count
    				String copyString = mondayList.getSelectionModel().getSelectedItem().getActivity().getActvityName() +
    						"|" + mondayList.getSelectionModel().getSelectedItem().getActivity().getUnit().toString() +
    						"|" + mondayList.getSelectionModel().getSelectedItem().getPlannedCount();
    				//System.out.println(copyString);
    				content.putString(copyString);
    				removeAct = true;
    				removeIndex = mondayList.getSelectionModel().getSelectedIndex();
    				//System.out.println(removeIndex);
    				dragBoard.setContent(content);
    			}
    		}
    	});   
    	
    	mondayList.setOnDragDone(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("monday->setOnDragDone");    
    			if(removeAct){
    				//remove that selected activity
    				mondayActivities.remove(removeIndex);
    				removeAct = false;
    				//System.out.println(mondayActivities.size());
    			}
    			removeIndex = -1;
    		}
    	});   	
    	
    	mondayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("monday->setOnDragEntered");    
    			mondayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	mondayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("monday->setOnDragExited");  
    			mondayList.setBlendMode(null);
    		}
    	});
    
    	mondayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("monday->setOnDragOver");
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	mondayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("monday->setOnDragDropped");
    			if(dragFrom.equals("mondayList")){ 
    				removeAct = false;
    				return;
    			}
    			String newActivity = dragEvent.getDragboard().getString();
    			// Split the string by "|"
    			System.out.println(newActivity);
				String[] info = newActivity.split("\\|");
				//for(String string : info){
				//	System.out.println(string);
				//}
				
    			if(dragFrom.equals("activityList")){     				
    				// Pop up a dialog to accept planed count
    				Optional<String> response = Dialogs.create()
    						.title("Text Input Count")
    						.message("Please enter your planned count:")
    						.showTextInput("15");
    			
    				// Add activity to the correspond observableList
    				response.ifPresent(count -> mondayActivities.add(new ActivityPlan(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE, Integer.parseInt(count))));
    			}
    			else{// Comes from other dayList
    				mondayActivities.add(new ActivityPlan(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE, Integer.parseInt(info[2])));
    			}
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	
    	

    	tuesdayList.setOnDragDetected(new EventHandler<MouseEvent>()
    	{
    		@Override
    		public void handle(MouseEvent event)
    		{
    			if(dragable && tuesdayActivities.size()>0){
    				System.out.println("tuesday->setOnDragDetected");
    				dragFrom = "tuesdayList";
    				Dragboard dragBoard = tuesdayList.startDragAndDrop(TransferMode.MOVE);
    				ClipboardContent content = new ClipboardContent();
    				// Format String as: Name|Unit|Count
    				String copyString = tuesdayList.getSelectionModel().getSelectedItem().getActivity().getActvityName() +
    						"|" + tuesdayList.getSelectionModel().getSelectedItem().getActivity().getUnit().toString() +
    						"|" + tuesdayList.getSelectionModel().getSelectedItem().getPlannedCount();
    				//System.out.println(copyString);
    				content.putString(copyString);
    				removeAct = true;
    				removeIndex = tuesdayList.getSelectionModel().getSelectedIndex();
    				//System.out.println(removeIndex);
    				dragBoard.setContent(content);
    			}
    		}
    	});   
    	
    	tuesdayList.setOnDragDone(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("tuesday->setOnDragDone");    
    			if(removeAct){
    				//remove that selected activity
    				tuesdayActivities.remove(removeIndex);
    				removeAct = false;
    				//System.out.println(tuesdayActivities.size());
    			}
    			removeIndex = -1;
    		}
    	});   	
    	
    	tuesdayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("tuesday->setOnDragEntered");    
    			tuesdayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	tuesdayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("tuesday->setOnDragExited");  
    			tuesdayList.setBlendMode(null);
    		}
    	});
    
    	tuesdayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("tuesday->setOnDragOver");
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	tuesdayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("tuesday->setOnDragDropped");
    			if(dragFrom.equals("tuesdayList")){ 
    				removeAct = false;
    				return;
    			}
    			String newActivity = dragEvent.getDragboard().getString();
    			// Split the string by "|"
    			System.out.println(newActivity);
				String[] info = newActivity.split("\\|");
				//for(String string : info){
				//	System.out.println(string);
				//}
				
    			if(dragFrom.equals("activityList")){     				
    				// Pop up a dialog to accept planed count
    				Optional<String> response = Dialogs.create()
    						.title("Text Input Count")
    						.message("Please enter your planned count:")
    						.showTextInput("15");
    			
    				// Add activity to the correspond observableList
    				response.ifPresent(count -> tuesdayActivities.add(new ActivityPlan(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE, Integer.parseInt(count))));
    			}
    			else{// Comes from other dayList
    				tuesdayActivities.add(new ActivityPlan(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE, Integer.parseInt(info[2])));
    			}
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	
    	
    	wednesdayList.setOnDragDetected(new EventHandler<MouseEvent>()
    	{
    		@Override
    		public void handle(MouseEvent event)
    		{
    			if(dragable && wednesdayActivities.size()>0){
    				System.out.println("wednesday->setOnDragDetected");
    				dragFrom = "wednesdayList";
    				Dragboard dragBoard = wednesdayList.startDragAndDrop(TransferMode.MOVE);
    				ClipboardContent content = new ClipboardContent();
    				// Format String as: Name|Unit|Count
    				String copyString = wednesdayList.getSelectionModel().getSelectedItem().getActivity().getActvityName() +
    						"|" + wednesdayList.getSelectionModel().getSelectedItem().getActivity().getUnit().toString() +
    						"|" + wednesdayList.getSelectionModel().getSelectedItem().getPlannedCount();
    				//System.out.println(copyString);
    				content.putString(copyString);
    				removeAct = true;
    				removeIndex = wednesdayList.getSelectionModel().getSelectedIndex();
    				//System.out.println(removeIndex);
    				dragBoard.setContent(content);
    			}
    		}
    	});   
    	
    	wednesdayList.setOnDragDone(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("wednesday->setOnDragDone");    
    			if(removeAct){
    				//remove that selected activity
    				wednesdayActivities.remove(removeIndex);
    				removeAct = false;
    				//System.out.println(wednesdayActivities.size());
    			}
    			removeIndex = -1;
    		}
    	});   	
    	
    	wednesdayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("wednesday->setOnDragEntered");    
    			wednesdayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	wednesdayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("wednesday->setOnDragExited");  
    			wednesdayList.setBlendMode(null);
    		}
    	});
    
    	wednesdayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("wednesday->setOnDragOver");
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	wednesdayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("wednesday->setOnDragDropped");
    			if(dragFrom.equals("wednesdayList")){ 
    				removeAct = false;
    				return;
    			}
    			String newActivity = dragEvent.getDragboard().getString();
    			// Split the string by "|"
    			System.out.println(newActivity);
				String[] info = newActivity.split("\\|");
				//for(String string : info){
				//	System.out.println(string);
				//}
				
    			if(dragFrom.equals("activityList")){     				
    				// Pop up a dialog to accept planed count
    				Optional<String> response = Dialogs.create()
    						.title("Text Input Count")
    						.message("Please enter your planned count:")
    						.showTextInput("15");
    			
    				// Add activity to the correspond observableList
    				response.ifPresent(count -> wednesdayActivities.add(new ActivityPlan(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE, Integer.parseInt(count))));
    			}
    			else{// Comes from other dayList
    				wednesdayActivities.add(new ActivityPlan(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE, Integer.parseInt(info[2])));
    			}
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	
    	
    	thursdayList.setOnDragDetected(new EventHandler<MouseEvent>()
    	{
    		@Override
    		public void handle(MouseEvent event)
    		{
    			if(dragable && thursdayActivities.size()>0){
    				System.out.println("thursday->setOnDragDetected");
    				dragFrom = "thursdayList";
    				Dragboard dragBoard = thursdayList.startDragAndDrop(TransferMode.MOVE);
    				ClipboardContent content = new ClipboardContent();
    				// Format String as: Name|Unit|Count
    				String copyString = thursdayList.getSelectionModel().getSelectedItem().getActivity().getActvityName() +
    						"|" + thursdayList.getSelectionModel().getSelectedItem().getActivity().getUnit().toString() +
    						"|" + thursdayList.getSelectionModel().getSelectedItem().getPlannedCount();
    				//System.out.println(copyString);
    				content.putString(copyString);
    				removeAct = true;
    				removeIndex = thursdayList.getSelectionModel().getSelectedIndex();
    				//System.out.println(removeIndex);
    				dragBoard.setContent(content);
    			}
    		}
    	});   
    	
    	thursdayList.setOnDragDone(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("thursday->setOnDragDone");    
    			if(removeAct){
    				//remove that selected activity
    				thursdayActivities.remove(removeIndex);
    				removeAct = false;
    				//System.out.println(thursdayActivities.size());
    			}
    			removeIndex = -1;
    		}
    	});   	
    	
    	thursdayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("thursday->setOnDragEntered");    
    			thursdayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	thursdayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("thursday->setOnDragExited");  
    			thursdayList.setBlendMode(null);
    		}
    	});
    
    	thursdayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("thursday->setOnDragOver");
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	thursdayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("thursday->setOnDragDropped");
    			if(dragFrom.equals("thursdayList")){ 
    				removeAct = false;
    				return;
    			}
    			String newActivity = dragEvent.getDragboard().getString();
    			// Split the string by "|"
    			System.out.println(newActivity);
				String[] info = newActivity.split("\\|");
				//for(String string : info){
				//	System.out.println(string);
				//}
				
    			if(dragFrom.equals("activityList")){     				
    				// Pop up a dialog to accept planed count
    				Optional<String> response = Dialogs.create()
    						.title("Text Input Count")
    						.message("Please enter your planned count:")
    						.showTextInput("15");
    			
    				// Add activity to the correspond observableList
    				response.ifPresent(count -> thursdayActivities.add(new ActivityPlan(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE, Integer.parseInt(count))));
    			}
    			else{// Comes from other dayList
    				thursdayActivities.add(new ActivityPlan(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE, Integer.parseInt(info[2])));
    			}
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	
    	
    	fridayList.setOnDragDetected(new EventHandler<MouseEvent>()
    	{
    		@Override
    		public void handle(MouseEvent event)
    		{
    			if(dragable && fridayActivities.size()>0){
    				System.out.println("friday->setOnDragDetected");
    				dragFrom = "fridayList";
    				Dragboard dragBoard = fridayList.startDragAndDrop(TransferMode.MOVE);
    				ClipboardContent content = new ClipboardContent();
    				// Format String as: Name|Unit|Count
    				String copyString = fridayList.getSelectionModel().getSelectedItem().getActivity().getActvityName() +
    						"|" + fridayList.getSelectionModel().getSelectedItem().getActivity().getUnit().toString() +
    						"|" + fridayList.getSelectionModel().getSelectedItem().getPlannedCount();
    				//System.out.println(copyString);
    				content.putString(copyString);
    				removeAct = true;
    				removeIndex = fridayList.getSelectionModel().getSelectedIndex();
    				//System.out.println(removeIndex);
    				dragBoard.setContent(content);
    			}
    		}
    	});   
    	
    	fridayList.setOnDragDone(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("friday->setOnDragDone");    
    			if(removeAct){
    				//remove that selected activity
    				fridayActivities.remove(removeIndex);
    				removeAct = false;
    				//System.out.println(fridayActivities.size());
    			}
    			removeIndex = -1;
    		}
    	});   	
    	
    	fridayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("friday->setOnDragEntered");    
    			fridayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	fridayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("friday->setOnDragExited");  
    			fridayList.setBlendMode(null);
    		}
    	});
    
    	fridayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("friday->setOnDragOver");
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	fridayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("friday->setOnDragDropped");
    			if(dragFrom.equals("fridayList")){ 
    				removeAct = false;
    				return;
    			}
    			String newActivity = dragEvent.getDragboard().getString();
    			// Split the string by "|"
    			System.out.println(newActivity);
				String[] info = newActivity.split("\\|");
				//for(String string : info){
				//	System.out.println(string);
				//}
				
    			if(dragFrom.equals("activityList")){     				
    				// Pop up a dialog to accept planed count
    				Optional<String> response = Dialogs.create()
    						.title("Text Input Count")
    						.message("Please enter your planned count:")
    						.showTextInput("15");
    			
    				// Add activity to the correspond observableList
    				response.ifPresent(count -> fridayActivities.add(new ActivityPlan(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE, Integer.parseInt(count))));
    			}
    			else{// Comes from other dayList
    				fridayActivities.add(new ActivityPlan(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE, Integer.parseInt(info[2])));
    			}
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	
    	
    	saturdayList.setOnDragDetected(new EventHandler<MouseEvent>()
    	{
    		@Override
    		public void handle(MouseEvent event)
    		{
    			if(dragable && saturdayActivities.size()>0){
    				System.out.println("saturday->setOnDragDetected");
    				dragFrom = "saturdayList";
    				Dragboard dragBoard = saturdayList.startDragAndDrop(TransferMode.MOVE);
    				ClipboardContent content = new ClipboardContent();
    				// Format String as: Name|Unit|Count
    				String copyString = saturdayList.getSelectionModel().getSelectedItem().getActivity().getActvityName() +
    						"|" + saturdayList.getSelectionModel().getSelectedItem().getActivity().getUnit().toString() +
    						"|" + saturdayList.getSelectionModel().getSelectedItem().getPlannedCount();
    				//System.out.println(copyString);
    				content.putString(copyString);
    				removeAct = true;
    				removeIndex = saturdayList.getSelectionModel().getSelectedIndex();
    				//System.out.println(removeIndex);
    				dragBoard.setContent(content);
    			}
    		}
    	});   
    	
    	saturdayList.setOnDragDone(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("saturday->setOnDragDone");    
    			if(removeAct){
    				//remove that selected activity
    				saturdayActivities.remove(removeIndex);
    				removeAct = false;
    				//System.out.println(saturdayActivities.size());
    			}
    			removeIndex = -1;
    		}
    	});   	
    	
    	saturdayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("saturday->setOnDragEntered");    
    			saturdayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	saturdayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("saturday->setOnDragExited");  
    			saturdayList.setBlendMode(null);
    		}
    	});
    
    	saturdayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("saturday->setOnDragOver");
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	saturdayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("saturday->setOnDragDropped");
    			if(dragFrom.equals("saturdayList")){ 
    				removeAct = false;
    				return;
    			}
    			String newActivity = dragEvent.getDragboard().getString();
    			// Split the string by "|"
    			System.out.println(newActivity);
				String[] info = newActivity.split("\\|");
				//for(String string : info){
				//	System.out.println(string);
				//}
				
    			if(dragFrom.equals("activityList")){     				
    				// Pop up a dialog to accept planed count
    				Optional<String> response = Dialogs.create()
    						.title("Text Input Count")
    						.message("Please enter your planned count:")
    						.showTextInput("15");
    			
    				// Add activity to the correspond observableList
    				response.ifPresent(count -> saturdayActivities.add(new ActivityPlan(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE, Integer.parseInt(count))));
    			}
    			else{// Comes from other dayList
    				saturdayActivities.add(new ActivityPlan(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE, Integer.parseInt(info[2])));
    			}
    			dragEvent.setDropCompleted(true);
    		}
    	});
    }
}
