package app.controller;


import java.util.Map.Entry;

import org.controlsfx.dialog.Dialog;

import app.ClassSerializer;
import app.MainApp;
import app.model.ActivityPlan;
import app.model.DayPlan;
import app.model.WeekPlan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class WishListTabController{
	
	public static ObservableMap<String, WeekPlan> wishList;
	
	public MainController mainController;
	
	@FXML
	private AnchorPane wishListPane;
	@FXML
	private ScrollPane wishListScrollPane;
	
	private static WeekPlan popUpPlan;
	
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
	
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public WishListTabController() {
    }
    
    public void init(MainController main){
    	mainController = main;
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML 
    public void initialize() {
    	GridPane grid = new GridPane();
    	grid.setMinSize(1000, 600);
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(0, 20, 0, 20)); 
    	
    	ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(220);
        column1.setMaxWidth(220);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(220);
        column2.setMaxWidth(220);
    	ColumnConstraints column3 = new ColumnConstraints();
    	column3.setMinWidth(220);
        column3.setMaxWidth(220);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setMinWidth(220);
        column4.setMaxWidth(220);
        grid.getColumnConstraints().addAll(column1, column2, column3, column4);
    	
        wishList = ClassSerializer.WishListUnserializer();
        //System.out.println(wishList.size());       
        
       	Button[] button = new Button[wishList.size()];
       	String[] planNameSet = new String[wishList.size()];
       	wishList.keySet().toArray(planNameSet);
       	
        for(int i=0; i<wishList.size(); i++){
        	button[i] = new Button(planNameSet[i]);
        	button[i].setMaxSize(210, 180);
        	button[i].setOnAction((event) -> {
        	    // Button was clicked, do something...
        		Button b = (Button) event.getSource();
        	    popupPlan(b, b.getText());
        	}); 
        }
        
        int numRow = (int) Math.ceil(wishList.size() / 4.0);
        
        for(int i=0; i < numRow; i++){
        	for(int j=0; j < 4; j++){
        		if(4*i+j < button.length)
        			grid.add(button[4*i+j], j, i);
        		
        	}
        	grid.getRowConstraints().add(new RowConstraints(200));
        }    	

    	wishListScrollPane.setContent(grid);
    }

	private void popupPlan(Button button, String text) {
		
		loadWeekPlan(text);
		
		Dialog dlg = new Dialog(button, "Plan Detail");
		GridPane gPane = new GridPane();
		gPane.setMaxSize(1000, 600);
		gPane.setMinSize(1000, 600);
		gPane.getRowConstraints().add(new RowConstraints(550));
		gPane.getRowConstraints().add(new RowConstraints(50));
		gPane.getColumnConstraints().add(new ColumnConstraints(880));
		gPane.getColumnConstraints().add(new ColumnConstraints(60));
		gPane.getColumnConstraints().add(new ColumnConstraints(60));
		
		SplitPane sp = new SplitPane();
		sp.setMaxSize(1000, 550);
		sp.setMinSize(1000, 550);
		final TableView<ActivityPlan> sundayList = new TableView<ActivityPlan>();
		makeColumn(sundayList);	
		sundayList.setItems(sundayActivities);
		final TableView<ActivityPlan> mondayList = new TableView<ActivityPlan>();
		makeColumn(mondayList);
		mondayList.setItems(mondayActivities);
		final TableView<ActivityPlan> tuesdayList = new TableView<ActivityPlan>();
		makeColumn(tuesdayList);
		tuesdayList.setItems(tuesdayActivities);
		final TableView<ActivityPlan> wednesdayList = new TableView<ActivityPlan>();
		makeColumn(wednesdayList);		 
		wednesdayList.setItems(wednesdayActivities);
		final TableView<ActivityPlan> thursdayList = new TableView<ActivityPlan>();
		makeColumn(thursdayList);
		thursdayList.setItems(thursdayActivities);
		final TableView<ActivityPlan> fridayList = new TableView<ActivityPlan>();
		makeColumn(fridayList);
		fridayList.setItems(fridayActivities);
		final TableView<ActivityPlan> saturdayList = new TableView<ActivityPlan>();
		makeColumn(saturdayList);
		saturdayList.setItems(saturdayActivities);
		
		sp.getItems().addAll(sundayList, mondayList, tuesdayList, wednesdayList, thursdayList, fridayList, saturdayList);
		sp.setDividerPositions(0.1428, 0.2856, 0.4284, 0.5714, 0.7142, 0.8571, 1);

		gPane.add(sp, 0, 0);
		
		Button applyButton = new Button("Apply");
		applyButton.setOnAction((event) -> {
    	    MainApp.weekPlan = wishList.get(text);
    	    ActivityListController.updateWeekPlan();
    	    dlg.hide();
    	}); 
		gPane.add(applyButton, 1, 1);
		
		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction((event) -> {
    	    if(wishList.remove(text)==null)
    	    	System.out.println("No such Plan");
    	    ClassSerializer.WishListSerializer(wishList);
    	    initialize();
    	    dlg.hide();
    	}); 
		gPane.add(deleteButton, 2, 1);
		
		dlg.setContent(gPane);
		dlg.show();
	}
	
    private void loadWeekPlan(String planName) {    	
    	popUpPlan = wishList.get(planName);
    	
    	sundayActivities.clear();
    	mondayActivities.clear();
    	tuesdayActivities.clear();
    	wednesdayActivities.clear();
    	thursdayActivities.clear();
    	fridayActivities.clear();
    	saturdayActivities.clear();
		loadDayPlan(1, mondayActivities);
		loadDayPlan(2, tuesdayActivities);
		loadDayPlan(3, wednesdayActivities);
		loadDayPlan(4, thursdayActivities);
		loadDayPlan(5, fridayActivities);
		loadDayPlan(6, saturdayActivities);
		loadDayPlan(0, sundayActivities);
	}
	
	private void loadDayPlan(int index, ObservableList<ActivityPlan> dayList) {
		DayPlan dayplan = popUpPlan.getDayPlan(index);
		for(Entry<String, ActivityPlan> entry : dayplan.getDayPlan().entrySet()){
            ActivityPlan a = entry.getValue();
            dayList.add(a);
        }
	}
	
	private void makeColumn(TableView<ActivityPlan> tableView) {
		TableColumn<ActivityPlan,String> name_Col = new TableColumn<>("Activity");
		TableColumn<ActivityPlan,Number> count_Col = new TableColumn<>("Count");
		name_Col.prefWidthProperty().bind(tableView.widthProperty().divide(4.0/3));
		name_Col.maxWidthProperty().bind(name_Col.prefWidthProperty());
		name_Col.setResizable(false);
		count_Col.prefWidthProperty().bind(tableView.widthProperty().divide(4));
		count_Col.maxWidthProperty().bind(count_Col.prefWidthProperty());
		count_Col.setResizable(false);

		name_Col.setCellValueFactory(cellData -> cellData.getValue().getActivity().ActvityNameProperty());
		count_Col.setCellValueFactory(cellData -> cellData.getValue().plannedCountProperty());

		tableView.getColumns().addAll(name_Col, count_Col);
	}
	  
}
