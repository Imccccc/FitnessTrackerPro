package app.controller;

import impl.org.controlsfx.i18n.Localization;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.Map.Entry;

import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import DBconnector.DBconnector;
import app.MainApp;
import app.model.Activity;
import app.model.ActivityPlan;
import app.model.DayPlan;
import app.model.RealActivityPlan;
import app.model.RealDayPlan;
import app.model.Unit;
import app.model.dayAmount;
import app.ClassSerializer;

public class HomeTabController {
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
    
    private ObservableList<String> dayNames;
    
    private ObservableList<dayAmount> pastCalories;
    
    private XYChart.Series<String, Number> dailyCaloriesData;
    
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
        plotData(7);
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
		ArrayList<app.model.dayAmount> amountList = DBconnector.getExerciseAmount(DBconnector.username);
		//ArrayList<app.model.dayAmount> amountList = makeFakeAmounts();
		if(amountList == null)
			return;
		else{
			pastCalories = getPastCalories(amountList, length);
			
			for(dayAmount day : pastCalories) {
				dayNames.add(day.date.toString().substring(5,10));
			}
		
			setCaloriesData();
			xAxis = new CategoryAxis(dayNames);
			areaChart.setTitle("Statistics");
		}
	}
	
	
	public ObservableList<dayAmount> getPastCalories(ArrayList<app.model.dayAmount> amountList, int length) {
		ObservableList<dayAmount> graph = FXCollections.observableArrayList();
		if(amountList.size()<=length){
			graph.addAll(amountList);
			return graph;
    	}
    	else {
    		int index = amountList.size() - length;
    		while(index < amountList.size()) {
    			graph.add(amountList.get(index));
    			index++;
    		}
    		return graph;
    	}
	}
	
	public void setCaloriesData() {
		dailyCaloriesData = new XYChart.Series<String, Number>();
		dailyCaloriesData.setName("dailySum");
		
		double maxRange = 0;
		for(dayAmount day : pastCalories) {
			dailyCaloriesData.getData().add(new XYChart.Data<String, Number>(day.date.toString().substring(5,10), day.amount));
			if(maxRange<day.amount) {
				maxRange = day.amount;
			}
		}
		yAxis = new NumberAxis(0, maxRange+200, 200);
		yAxis.setAutoRanging(false);
    	areaChart.getData().clear();
    	areaChart.getData().add(dailyCaloriesData);
	}
	
	public ArrayList<app.model.dayAmount> makeFakeAmounts() {
		ArrayList<app.model.dayAmount> exerciseAmounts = new ArrayList<>();
		dayAmount ad1 = new dayAmount();
		dayAmount ad2 = new dayAmount();
		dayAmount ad3 = new dayAmount();
		dayAmount ad4 = new dayAmount();
		dayAmount ad5 = new dayAmount();
		dayAmount ad6 = new dayAmount();
		dayAmount ad7 = new dayAmount();
		dayAmount ad8 = new dayAmount();
		dayAmount ad9 = new dayAmount();
		ad1.date = java.sql.Date.valueOf("2015-03-17");
		ad1.amount = 1000.00;
		ad2.date = java.sql.Date.valueOf("2015-03-18");
		ad2.amount = 1100;
		ad3.date = java.sql.Date.valueOf("2015-03-19");
		ad3.amount = 1200;
		ad4.date = java.sql.Date.valueOf("2015-03-20");
		ad4.amount = 1000.00;
		ad5.date = java.sql.Date.valueOf("2015-03-21");
		ad5.amount = 1100;
		ad6.date = java.sql.Date.valueOf("2015-03-22");
		ad6.amount = 1200;
		ad7.date = java.sql.Date.valueOf("2015-03-23");
		ad7.amount = 1000.00;
		ad8.date = java.sql.Date.valueOf("2015-03-24");
		ad8.amount = 1100.00;
		ad9.date = java.sql.Date.valueOf("2015-03-25");
		ad9.amount = 1200;
		exerciseAmounts.add(ad1);
		exerciseAmounts.add(ad2);
		exerciseAmounts.add(ad3);
		exerciseAmounts.add(ad4);
		exerciseAmounts.add(ad5);
		exerciseAmounts.add(ad6);
		exerciseAmounts.add(ad7);
		exerciseAmounts.add(ad8);
		exerciseAmounts.add(ad9);
		return exerciseAmounts;
	}
	
}
