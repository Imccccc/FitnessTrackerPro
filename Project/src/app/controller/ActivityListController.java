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
    }
}
