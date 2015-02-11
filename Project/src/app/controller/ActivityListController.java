package app.controller;

import java.util.Optional;

import org.controlsfx.dialog.Dialogs;
import org.omg.CORBA.PRIVATE_MEMBER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import app.MainApp;
import app.model.Activity;


public class ActivityListController {

	@FXML
	private ListView<String> activityList;
	@FXML
	private TableView<Activity> sundayList;
	@FXML
	private TableColumn<Activity, String> sundayList_actCol;
	@FXML
	private TableColumn<Activity, Number> sundayList_cntCol;
	@FXML
	private TableView<Activity> mondayList;
	@FXML
	private TableColumn<Activity, String> mondayList_actCol;
	@FXML
	private TableColumn<Activity, Number> mondayList_cntCol;
	@FXML
	private TableView<Activity> tuesdayList;
	@FXML
	private TableColumn<Activity, String> tuesdayList_actCol;
	@FXML
	private TableColumn<Activity, Number> tuesdayList_cntCol;
	@FXML
	private TableView<Activity> wednesdayList;
	@FXML
	private TableColumn<Activity, String> wednesdayList_actCol;
	@FXML
	private TableColumn<Activity, Number> wednesdayList_cntCol;
	@FXML
	private TableView<Activity> thursdayList;
	@FXML
	private TableColumn<Activity, String> thursdayList_actCol;
	@FXML
	private TableColumn<Activity, Number> thursdayList_cntCol;
	@FXML
	private TableView<Activity> fridayList;
	@FXML
	private TableColumn<Activity, String> fridayList_actCol;
	@FXML
	private TableColumn<Activity, Number> fridayList_cntCol;
	@FXML
	private TableView<Activity> saturdayList;
	@FXML
	private TableColumn<Activity, String> saturdayList_actCol;
	@FXML
	private TableColumn<Activity, Number> saturdayList_cntCol;
	
	public static final ObservableList<String> activities = 
	        FXCollections.observableArrayList();
	public static final ObservableList<Activity> sundayActivities = 
	        FXCollections.observableArrayList();
	public static final ObservableList<Activity> mondayActivities = 
	        FXCollections.observableArrayList();
	public static final ObservableList<Activity> tuesdayActivities = 
	        FXCollections.observableArrayList();
	public static final ObservableList<Activity> wednesdayActivities = 
	        FXCollections.observableArrayList();
	public static final ObservableList<Activity> thursdayActivities = 
	        FXCollections.observableArrayList();
	public static final ObservableList<Activity> fridayActivities = 
	        FXCollections.observableArrayList();
	public static final ObservableList<Activity> saturdayActivities = 
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
    	//sundayActivities.addAll("");
    	activityList.setItems(activities);
    	
    	// Set all 7 days data binding
    	sundayList_actCol.setCellValueFactory(cellData -> cellData.getValue().ActvityNameProperty());
    	sundayList_cntCol.setCellValueFactory(cellData -> cellData.getValue().planCountProperty());
    	sundayList.setItems(sundayActivities);
    	mondayList_actCol.setCellValueFactory(cellData -> cellData.getValue().ActvityNameProperty());
    	mondayList_cntCol.setCellValueFactory(cellData -> cellData.getValue().planCountProperty());
    	mondayList.setItems(mondayActivities);
    	tuesdayList_actCol.setCellValueFactory(cellData -> cellData.getValue().ActvityNameProperty());
    	tuesdayList_cntCol.setCellValueFactory(cellData -> cellData.getValue().planCountProperty());
    	tuesdayList.setItems(tuesdayActivities);
    	wednesdayList_actCol.setCellValueFactory(cellData -> cellData.getValue().ActvityNameProperty());
    	wednesdayList_cntCol.setCellValueFactory(cellData -> cellData.getValue().planCountProperty());
    	wednesdayList.setItems(wednesdayActivities);
    	thursdayList_actCol.setCellValueFactory(cellData -> cellData.getValue().ActvityNameProperty());
    	thursdayList_cntCol.setCellValueFactory(cellData -> cellData.getValue().planCountProperty());
    	thursdayList.setItems(thursdayActivities);
    	fridayList_actCol.setCellValueFactory(cellData -> cellData.getValue().ActvityNameProperty());
    	fridayList_cntCol.setCellValueFactory(cellData -> cellData.getValue().planCountProperty());
    	fridayList.setItems(fridayActivities);
    	saturdayList_actCol.setCellValueFactory(cellData -> cellData.getValue().ActvityNameProperty());
    	saturdayList_cntCol.setCellValueFactory(cellData -> cellData.getValue().planCountProperty());
    	saturdayList.setItems(saturdayActivities);
    	
    	initializeListeners();
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
    
    
    private void initializeListeners()
    {
    	activityList.setOnDragDetected(new EventHandler<MouseEvent>()
    	{
    		@Override
    		public void handle(MouseEvent event)
    		{
    			//System.out.println("setOnDragDetected");
    			Dragboard dragBoard = activityList.startDragAndDrop(TransferMode.MOVE);
    			ClipboardContent content = new ClipboardContent();
    			content.putString(activityList.getSelectionModel().getSelectedItem());
    			dragBoard.setContent(content);
    		}
    	});
    
    	
    	activityList.setOnDragDone(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragDone");    
    			// This is not the ideal place to remove the player because the drag might not have been exited on the target.
    			// String player = dragEvent.getDragboard().getString();
    			// playersList.remove(new Player(player));
    		}
    	});
    
    	sundayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragEntered");    
    			sundayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	sundayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragExited");  
    			sundayList.setBlendMode(null);
    		}
    	});
    
    	sundayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragOver");
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	sundayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragDropped");
    			String newActivity = dragEvent.getDragboard().getString();
 
    			// Pop up a dialog to accept planed count
    			Optional<String> response = Dialogs.create()
    			        .title("Text Input Count")
    			        .message("Please enter your planned count:")
    			        .showTextInput("15");

    			// Add activity to the correspond observableList
    			response.ifPresent(count -> sundayActivities.add(new Activity(newActivity, Integer.parseInt(count))));
   
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	mondayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragEntered");    
    			mondayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	mondayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragExited");  
    			mondayList.setBlendMode(null);
    		}
    	});
    
    	mondayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragOver");
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	mondayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragDropped");
    			String newActivity = dragEvent.getDragboard().getString();
 
    			// Pop up a dialog to accept planed count
    			Optional<String> response = Dialogs.create()
    			        .title("Text Input Count")
    			        .message("Please enter your planned count:")
    			        .showTextInput("15");

    			// Add activity to the correspond observableList
    			response.ifPresent(count -> mondayActivities.add(new Activity(newActivity, Integer.parseInt(count))));
   
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	tuesdayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragEntered");    
    			tuesdayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	tuesdayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragExited");  
    			tuesdayList.setBlendMode(null);
    		}
    	});
    
    	tuesdayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragOver");
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	tuesdayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragDropped");
    			String newActivity = dragEvent.getDragboard().getString();
 
    			// Pop up a dialog to accept planed count
    			Optional<String> response = Dialogs.create()
    			        .title("Text Input Count")
    			        .message("Please enter your planned count:")
    			        .showTextInput("15");

    			// Add activity to the correspond observableList
    			response.ifPresent(count -> tuesdayActivities.add(new Activity(newActivity, Integer.parseInt(count))));
   
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	wednesdayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragEntered");    
    			wednesdayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	wednesdayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragExited");  
    			wednesdayList.setBlendMode(null);
    		}
    	});
    
    	wednesdayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragOver");
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	wednesdayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragDropped");
    			String newActivity = dragEvent.getDragboard().getString();
 
    			// Pop up a dialog to accept planed count
    			Optional<String> response = Dialogs.create()
    			        .title("Text Input Count")
    			        .message("Please enter your planned count:")
    			        .showTextInput("15");

    			// Add activity to the correspond observableList
    			response.ifPresent(count -> wednesdayActivities.add(new Activity(newActivity, Integer.parseInt(count))));
   
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	thursdayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragEntered");    
    			thursdayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	thursdayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragExited");  
    			thursdayList.setBlendMode(null);
    		}
    	});
    
    	thursdayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragOver");
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	thursdayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragDropped");
    			String newActivity = dragEvent.getDragboard().getString();
 
    			// Pop up a dialog to accept planed count
    			Optional<String> response = Dialogs.create()
    			        .title("Text Input Count")
    			        .message("Please enter your planned count:")
    			        .showTextInput("15");

    			// Add activity to the correspond observableList
    			response.ifPresent(count -> thursdayActivities.add(new Activity(newActivity, Integer.parseInt(count))));
   
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	fridayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragEntered");    
    			fridayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	fridayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragExited");  
    			fridayList.setBlendMode(null);
    		}
    	});
    
    	fridayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragOver");
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	fridayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragDropped");
    			String newActivity = dragEvent.getDragboard().getString();
 
    			// Pop up a dialog to accept planed count
    			Optional<String> response = Dialogs.create()
    			        .title("Text Input Count")
    			        .message("Please enter your planned count:")
    			        .showTextInput("15");

    			// Add activity to the correspond observableList
    			response.ifPresent(count -> fridayActivities.add(new Activity(newActivity, Integer.parseInt(count))));
   
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	saturdayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragEntered");    
    			saturdayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	saturdayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragExited");  
    			saturdayList.setBlendMode(null);
    		}
    	});
    
    	saturdayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragOver");
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	saturdayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			//System.out.println("setOnDragDropped");
    			String newActivity = dragEvent.getDragboard().getString();
 
    			// Pop up a dialog to accept planed count
    			Optional<String> response = Dialogs.create()
    			        .title("Text Input Count")
    			        .message("Please enter your planned count:")
    			        .showTextInput("15");

    			// Add activity to the correspond observableList
    			response.ifPresent(count -> saturdayActivities.add(new Activity(newActivity, Integer.parseInt(count))));
   
    			dragEvent.setDropCompleted(true);
    		}
    	});
    }
}
