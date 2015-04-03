package app.controller;

import impl.org.controlsfx.i18n.Localization;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

import javafx.collections.FXCollections;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Dialog;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import app.model.RealDayPlan;
import app.model.dayAmount;
import app.ClassSerializer;

public class HomeTabController {
	// FXML Variables
	@FXML
	private TableView<RealActivityPlan> HomePageTable;
	@FXML
    private AreaChart<String, Number> areaChart;
	@FXML
    private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;
    @FXML
    private ComboBox<String> planNameBox;
    @FXML
    private TableColumn<RealActivityPlan, String> ActivityName;
    @FXML
    private TableColumn<RealActivityPlan, Number> PlanCount;
    @FXML
    private TableColumn<RealActivityPlan, Number> UserCount;
    @FXML
    private Label HomepageLabel;

    // Global Variables
    public static ObservableList<String> dayNames;
    public static ObservableList<dayAmount> pastCalories;
    
    private MainController mainController;
    
    private ObservableList<RealDayPlan> amountList;
    
    private XYChart.Series<String, Number> dailyCaloriesData;
    
    private ObservableList<RealActivityPlan> activityData = FXCollections.observableArrayList();
    
    
    Calendar c;
    int dayOfWeek;
    Date date;
	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	String today;
    
	public void init(MainController mainController) {
		this.mainController = mainController;
	}
	
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
    	
    	
    	if(MainApp.weekPlan == null){
    		System.out.println("Home Tab: Empty week plan");
    		return;
    	}
    	
    	if (ClassSerializer.TodayPlanUnserializer(today) != null) {
    		activityData.addAll(ClassSerializer.TodayPlanUnserializer(today));
			System.out.println("Home Tab: Load day plan from TodayPlan.Fitness");
		}
    	else{
	    	loadDayPlan(dayOfWeek-1, activityData);
			System.out.println("Home Tab: Load day plan from week plan");
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

        plotData(7);
    }
    
    public void updateTodayPlan(){
    	// Keep all the finished activity plan
    	ObservableList<RealActivityPlan> savedActivityPlan = FXCollections.observableArrayList();
    	for(RealActivityPlan realActivityPlan : activityData){
    		if(realActivityPlan.getRealCount() != 0){
    			savedActivityPlan.add(realActivityPlan);
    		}
    	}
    	
    	activityData = savedActivityPlan;
    	
    	// Add updated plan
    	DayPlan dayPlan = MainApp.weekPlan.getDayPlan(dayOfWeek-1);
    	for(ActivityPlan activityPlan : dayPlan.getDayPlan().values()){
    		activityData.add(new RealActivityPlan(activityPlan, 0));
    	}
    	
    	// Re-bind the data
    	HomePageTable.setItems(activityData);
    	
    	// Save the updated to local file
		ClassSerializer.TodayPlanSerializer(activityData, today);
    }
    
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     */
    
    
    public void clickHandler(){
    	RealActivityPlan temp;
    	temp = HomePageTable.getSelectionModel().getSelectedItem();
    	Localization.setLocale(new Locale("en", "EN"));
    	if(temp != null){
   			Optional<String> response = Dialogs.create()
   					.style(DialogStyle.NATIVE)
			        .title("Text Input Count")
			        .message("Please enter your count:")
			        .showTextInput(Integer.toString(temp.getRealCount()));
   			response.ifPresent(count -> {
   				try{
   					int input = Integer.parseInt(count);
   					if(input < 0){
   						throw new NumberFormatException();
   					}
   					temp.setRealCount(Integer.parseInt(count));
   				}catch(NumberFormatException e){
   					Alert alert = new Alert(AlertType.INFORMATION);
   					alert.setTitle("Input Alert");
   					alert.setHeaderText(null);
   					alert.setContentText("Only positive Integer!");
   					alert.showAndWait();
   				}catch(Exception e){

   				}
   				});
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
    			Activity temp2 = null;
    		    System.out.println("Activity=" + Activityname );
    		    for (Activity a : MainApp.activities) {
					if (a.getActvityName().equals(Activityname)) {
						temp = new Activity(a);
					}
				}
    		    for(RealActivityPlan a :activityData){
    		    	temp2 = a.getActivityPlan().getActivity();
    		    	if (temp2.getActvityName().equals(Activityname)) {
						return;
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
    	 openNewDialog();
    }
	
	
	public void plotData(int length) {
		dayNames = FXCollections.observableArrayList();
		amountList = StatTabController.getChartData(StatTabController.loadChartData(), length);
		//ArrayList<app.model.dayAmount> amountList = makeFakeAmounts();
		if(amountList == null)
			return;
		else{
			pastCalories = getPastCalories(amountList);
			
			for(dayAmount day : pastCalories) {
				dayNames.add(day.getDate().toString().substring(4,10));
			}
		
			setCaloriesData();
			xAxis = new CategoryAxis(dayNames);
			areaChart.setTitle("Statistics");
		}
	}
	
	
	public static ObservableList<dayAmount> getPastCalories(ObservableList<RealDayPlan> amountList) {
		ObservableList<dayAmount> graph = FXCollections.observableArrayList();
		double dailyCalories = 0;
		for(RealDayPlan dayPlan : amountList) {
			dailyCalories = 0;
			for(Map.Entry<String, RealActivityPlan> Entry : dayPlan.getDayPlan().entrySet()) {
				if(Entry.getValue().getActivityPlan().getActivity().getUnit().equals("MINUTE"))
					dailyCalories += Entry.getValue().getRealCount()*10;
				else {
					dailyCalories += Entry.getValue().getRealCount()*0.1;
				}
			}
			graph.add(new dayAmount(dayPlan.getDate(), dailyCalories));
		}
		return graph;
	}
	
	public void setCaloriesData() {
		dailyCaloriesData = new XYChart.Series<String, Number>();
		dailyCaloriesData.setName("Daily Calories");
		
		double maxRange = 0;
		for(dayAmount day : pastCalories) {
			dailyCaloriesData.getData().add(new XYChart.Data<String, Number>(day.getDate().toString().substring(4,10), day.getAmount()));
			if(maxRange<day.getAmount()) {
				maxRange = day.getAmount();
			}
		}
		yAxis = new NumberAxis(0, maxRange+200, 200);
		yAxis.setAutoRanging(false);
    	areaChart.getData().clear();
    	areaChart.getData().add(dailyCaloriesData);
	}
	
	
}
