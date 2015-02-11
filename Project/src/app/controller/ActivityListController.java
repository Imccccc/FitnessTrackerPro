package app.controller;

import org.omg.CORBA.PRIVATE_MEMBER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import app.MainApp;


public class ActivityListController {

	@FXML
	private ListView<String> activityList;
	@FXML
	private ListView<String> sundayList;
	@FXML
	private ListView<String> mondayList;
	@FXML
	private ListView<String> tuesdayList;
	@FXML
	private ListView<String> wednesdayList;
	@FXML
	private ListView<String> thursdayList;
	@FXML
	private ListView<String> fridayList;
	@FXML
	private ListView<String> saturdayList;

	
	public static final ObservableList<String> activities = 
	        FXCollections.observableArrayList();
	public static final ObservableList<String> sundayActivities = 
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
    	//sundayList.setItems(sundayActivities);
    	
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
    
//    /**
//     * Called when the drag gesture detected in activity list
//     */
//    @FXML
//    private void dragActivity() {
//        int selectedIndex = activityList.
//        personTable.getItems().remove(selectedIndex);
//    }
    
    private void initializeListeners()
    {
    	activityList.setOnDragDetected(new EventHandler<MouseEvent>()
    	{
    		@Override
    		public void handle(MouseEvent event)
    		{
    			System.out.println("setOnDragDetected");
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
    			System.out.println("setOnDragEntered");
    
    			sundayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	sundayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragExited");
    
    			sundayList.setBlendMode(null);
    		}
    	});
    
    	sundayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragOver");
    
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	sundayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragDropped");
    
    			String newActivity = dragEvent.getDragboard().getString();
    
    			sundayList.getItems().addAll(newActivity);
    
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	mondayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragEntered");
    
    			mondayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	mondayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragExited");
    
    			mondayList.setBlendMode(null);
    		}
    	});
    
    	mondayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragOver");
    
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	mondayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragDropped");
    
    			String newActivity = dragEvent.getDragboard().getString();
    
    			mondayList.getItems().addAll(newActivity);
    
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	tuesdayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragEntered");
    
    			tuesdayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	tuesdayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragExited");
    
    			tuesdayList.setBlendMode(null);
    		}
    	});
    
    	tuesdayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragOver");
    
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	tuesdayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragDropped");
    
    			String newActivity = dragEvent.getDragboard().getString();
    
    			tuesdayList.getItems().addAll(newActivity);
    
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	wednesdayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragEntered");
    
    			wednesdayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	wednesdayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragExited");
    
    			wednesdayList.setBlendMode(null);
    		}
    	});
    
    	wednesdayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragOver");
    
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	wednesdayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragDropped");
    
    			String newActivity = dragEvent.getDragboard().getString();
    
    			wednesdayList.getItems().addAll(newActivity);
    
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	thursdayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragEntered");
    
    			thursdayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	thursdayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragExited");
    
    			thursdayList.setBlendMode(null);
    		}
    	});
    
    	thursdayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragOver");
    
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	thursdayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragDropped");
    
    			String newActivity = dragEvent.getDragboard().getString();
    
    			thursdayList.getItems().addAll(newActivity);
    
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	fridayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragEntered");
    
    			fridayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	fridayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragExited");
    
    			fridayList.setBlendMode(null);
    		}
    	});
    
    	fridayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragOver");
    
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	fridayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragDropped");
    
    			String newActivity = dragEvent.getDragboard().getString();
    
    			fridayList.getItems().addAll(newActivity);
    
    			dragEvent.setDropCompleted(true);
    		}
    	});
    	
    	saturdayList.setOnDragEntered(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragEntered");
    
    			saturdayList.setBlendMode(BlendMode.DIFFERENCE);
    		}
    	});
    
    	saturdayList.setOnDragExited(new EventHandler<DragEvent>()
        {
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragExited");
    
    			saturdayList.setBlendMode(null);
    		}
    	});
    
    	saturdayList.setOnDragOver(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragOver");
    
    			dragEvent.acceptTransferModes(TransferMode.MOVE);
    		}
    	});
    
    	saturdayList.setOnDragDropped(new EventHandler<DragEvent>()
    	{
    		@Override
    		public void handle(DragEvent dragEvent)
    		{
    			System.out.println("setOnDragDropped");
    
    			String newActivity = dragEvent.getDragboard().getString();
    
    			saturdayList.getItems().addAll(newActivity);
    
    			dragEvent.setDropCompleted(true);
    		}
    	});
    }
}
