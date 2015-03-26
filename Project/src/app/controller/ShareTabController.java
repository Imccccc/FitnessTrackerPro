package app.controller;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.Rating;
import org.controlsfx.control.ButtonBar.ButtonType;
import org.controlsfx.control.action.AbstractAction;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;

import DBconnector.DBconnector;
import app.model.ActivityPlan;
import app.model.DayPlan;
import app.model.WeekPlan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ShareTabController {

	@FXML
	private ScrollPane shareTabScrollPane;
	@FXML
	private CheckBox cb1;
	@FXML
	private CheckBox cb2;
	@FXML
	private CheckBox cb3;
	@FXML
	private CheckBox cb4;
	@FXML
	private CheckBox cb5;	
	
	private ArrayList<WeekPlan> shareList;
	
	private static WeekPlan popUpPlan;
	
	private TextArea commentArea;
	private Rating rating;
	
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
	
	ArrayList<CheckBox> cblist;
	StringBuilder cbs;
	 /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ShareTabController() {
    	cblist = new ArrayList<CheckBox>();
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        //shareList= DBconnector.getPlans();
        //System.out.println("ShareList Size: "+shareList.size());       
    	//updateLayout();
    	
    	cb1.setOnAction(this::handleCheckboxAction);
    	cb2.setOnAction(this::handleCheckboxAction);
    	cb3.setOnAction(this::handleCheckboxAction);
    	cb4.setOnAction(this::handleCheckboxAction);
    	cb5.setOnAction(this::handleCheckboxAction);
    	cb1.setText("Arm");
    	cb2.setText("Back");
    	cb3.setText("Chest");
    	cb4.setText("Core");
    	cb5.setText("Leg");
    	cblist.add(cb1);
    	cblist.add(cb2);
    	cblist.add(cb3);
    	cblist.add(cb4);
    	cblist.add(cb5); 
    }

	public void updateLayout() {
		shareList= DBconnector.getPlans();
		
    	GridPane grid = new GridPane();
    	grid.setMinSize(750, 600);
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(0, 5, 0, 5)); 
    	
        grid.getColumnConstraints().addAll(new ColumnConstraints(200), new ColumnConstraints(475), new ColumnConstraints(75));
    	

        int shareList_size = shareList.size();
        
       	Button[] button = new Button[shareList_size];
       	
        for(int i=0; i<shareList_size; i++){
        	button[i] = new Button(shareList.get(i).getPlanName());
        	button[i].setMaxSize(180, 90);
        	button[i].setMinSize(180, 90);
        	button[i].setOnAction(new EventHandler<ActionEvent>() {
				int index;
				@Override
				public void handle(ActionEvent event) {
					Button b = (Button) event.getSource();
	        		System.out.println(b.getText());
	        		popupPlan(b, index);			
				}
				public EventHandler<ActionEvent> init(int i)
				{
					this.index = i;
					return this;
				}
			}.init(i)); 
        }
        
        
        for(int i=0; i < shareList_size; i++){
        	grid.add(button[i], 0, i);
        	
        	GridPane CLGrid = new GridPane();
        	CLGrid.setHgap(10);
        	CLGrid.setPadding(new Insets(5, 0, 5, 5));
        	int comment_num = 5;
        	for(int i1=0;i1<comment_num;i1++){
        		CLGrid.add(new Label("Here is the"+i1+"th comment"), 0, i1);
        	}
        	grid.add(CLGrid, 1, i);    	
        	
        	GridPane RCGrid = new GridPane();
        	RCGrid.setMinSize(175, 100);
        	RCGrid.setHgap(10);
        	RCGrid.setPadding(new Insets(15, 5, 5, 5));
        	RCGrid.getColumnConstraints().add(new ColumnConstraints(175));
        	RCGrid.getRowConstraints().addAll(new RowConstraints(30), new RowConstraints(50));
        	String rating = "4.3/5";
        	Button commentButton = new Button("Comment");
        	commentButton.setOnAction(new EventHandler<ActionEvent>() {
				int index;

				@Override
				public void handle(ActionEvent event) {
					Button b = (Button) event.getSource();
	        		popupCommentDlg(b, index);			
				}
				public EventHandler<ActionEvent> init(int i)
				{
					this.index = i;
					return this;
				}
        	}.init(i));
        	RCGrid.add(new Label(rating), 0, 0);
        	RCGrid.add(commentButton, 0, 1);        	
        	grid.add(RCGrid, 2, i);
        	grid.getRowConstraints().add(new RowConstraints(100));
        }    	

    	shareTabScrollPane.setContent(grid);
		
	}

	private void popupPlan(Button button, int index) {
		popUpPlan = shareList.get(index);
		
		loadWeekPlan();
		
		Dialog dlg = new Dialog(button, "Plan Detail: "+popUpPlan.getPlanName());
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
    	    //MainApp.weekPlan = wishList.get(text);
    	    //ActivityListController.updateWeekPlan();
    	    dlg.hide();
    	}); 
		gPane.add(applyButton, 1, 1);
		
		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction((event) -> {
//    	    if(wishList.remove(text)==null)
//    	    	System.out.println("No such Plan");
//    	    ClassSerializer.WishListSerializer(wishList);
//    	    initialize();
    	    dlg.hide();
    	}); 
		gPane.add(deleteButton, 2, 1);
		
		dlg.setContent(gPane);
		dlg.show();
	}
	
    private void loadWeekPlan() {    	
    	
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
	
	private void popupCommentDlg(Button b, int index) {
		commentArea = new TextArea();
		commentArea.setMinSize(300, 100);
		commentArea.setMaxSize(300, 100);
		commentArea.setWrapText(true);
    	rating = new Rating();       	  	
    	rating.setMaxSize(175, 30);
    	rating.setPrefSize(175, 30);
    	rating.setMinSize(175, 30);

		Dialog dlg = new Dialog( b, "Comment page");
		
		GridPane grid = new GridPane();
		grid.setMaxSize(320, 200);
		grid.setMinSize(320, 200);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		grid.add(new Label("Indicate your rating:"), 0, 0);
		grid.add(rating, 1, 0);
		grid.add(new Label("Your comment:"), 0, 1);
		GridPane.setColumnSpan(commentArea, 2);
		grid.add(commentArea, 0, 2);


		ButtonBar.setType(actionComment, ButtonType.OK_DONE);
		actionComment.disabledProperty().set(true);

		// Do some validation (using the Java 8 lambda syntax).
		commentArea.textProperty().addListener((observable, oldValue, newValue) -> {
			actionComment.disabledProperty().set(newValue.trim().isEmpty());
		});

//
//		dlg.setMasthead("Please enter a username and set password to register an account");
		dlg.setContent(grid);
		dlg.getActions().addAll(actionComment, Dialog.Actions.CANCEL);

		dlg.show();
	}
	
	final Action actionComment = new AbstractAction("Submit") {
		// This method is called when the login button is clicked ...
		public void handle(ActionEvent ae) {			
			Dialog d = (Dialog) ae.getSource();

			String comment = commentArea.getText();
			double rate = rating.getRating();

			System.out.println("Comment: "+comment);
			System.out.println("Rating:  "+rate);
			//TODO: update to the database
			
			d.hide();

		}
	};
	
	private void handleCheckboxAction(ActionEvent event){
		cbs = new StringBuilder();
		for(CheckBox c : cblist){
			if(c.isSelected()){
				cbs.append(c.getText()+"|");
			}
		}
		String s = cbs.toString();
		if(s.length() > 1){
			s = s.substring(0, s.length()-1);
		}
		
		System.out.println(s);
		
		if(s.equals("")){
			shareList = DBconnector.getPlans();
		}
		else{
			shareList = DBconnector.getPlans(s);
		}
		updateLayout();
	}
	

}
