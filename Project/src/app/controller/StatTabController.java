package app.controller;

import java.text.DateFormatSymbols;
import java.util.Collection;
import java.util.Collections;

import app.model.RealActivityPlan;
import app.model.RealDayPlan;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;

public class StatTabController {
	@FXML
    private AreaChart<String, Number> areaChart;
	@FXML
	private NumberAxis yAxis;
    @FXML
    private ComboBox<String> planNameBox;
    
    ObservableList<RealDayPlan> chartPlans;
    
    private ObservableList<String> dayNames;
    
    private XYChart.Series<String, Number> seriesRealData;
    
    private XYChart.Series<String, Number> seriesPlannedData;
    @FXML
    private CategoryAxis xAxis;

    
    
    public StatTabController() {
    }
    
    @FXML
    private void initialize() {
    	dayNames = FXCollections.observableArrayList();
    	
    	String dayName[] = new DateFormatSymbols().getWeekdays(); //add day name to a string
    	PlansCreator weekPlanCreator = new PlansCreator();
    	chartPlans = weekPlanCreator.create(); //get the last 7-day activity plan as weekPlan
    	for(RealDayPlan dayPlan : chartPlans) {
    		dayNames.add(dayName[dayPlan.getDate().getDay() + 1].substring(0, 3)); //add to our ObservableList of dayNames
    	}
    	//xAxis = new CategoryAxis();
    	xAxis.setCategories(dayNames);
    	xAxis.setLabel("Week");
    	//yAxis = new NumberAxis();
        yAxis.setAutoRanging(false);

    	seriesRealData = new XYChart.Series<String, Number>();
    	seriesPlannedData = new XYChart.Series<String, Number>();
    	
    	areaChart.setTitle("Statistics");
    	
    	addWeekPlanName();
    }
    
    
    public void addWeekPlanName() {
    	Collection<RealActivityPlan> tmpActivityPlans = FXCollections.observableArrayList();
    	ObservableList<String> dayPlanNames = FXCollections.observableArrayList();
    	for(RealDayPlan dayPlan : chartPlans) {
    		tmpActivityPlans = dayPlan.getDayPlan().values();
    		for(RealActivityPlan realActivityPlan : tmpActivityPlans) {
    			if(!dayPlanNames.contains(realActivityPlan.getActivityPlan().getActivity().getActvityName())) { //check if the plan is already in the list
    				dayPlanNames.add(realActivityPlan.getActivityPlan().getActivity().getActvityName());
    			}
    		}
    	}
    	
    	Collections.sort(dayPlanNames);
    	planNameBox.setItems(dayPlanNames);
    	planNameBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
    		public void changed (ObservableValue ov, Number value, Number newValue) {
    			setWeekActivityData();
    		}
		});
    	
    	System.out.println("The first value in the choice box is "+planNameBox.getValue());
    	planNameBox.setValue(dayPlanNames.get(0));
    	planNameBox.setTooltip(new Tooltip("Select activity name please"));
    }
    
    public void setWeekActivityData() {
    	seriesRealData = new XYChart.Series<String, Number>();
    	seriesPlannedData = new XYChart.Series<String, Number>();
    	seriesRealData.setName("RealData");
    	seriesPlannedData.setName("PlannedData");
    	
    	int maxRange = 0;
    	for(RealDayPlan dayPlan : chartPlans) {
    		seriesRealData.getData().add(new XYChart.Data<String, Number>(dayPlan.getDate().toString().substring(0, 3), 
    				dayPlan.getDayPlan().get(planNameBox.getValue()).getRealCount()));	//add data of selected activity to series
    				if(dayPlan.getDayPlan().get(planNameBox.getValue()).getRealCount() > maxRange) {
    					maxRange = dayPlan.getDayPlan().get(planNameBox.getValue()).getRealCount(); //update max-Range of the yAxis
    				}
    		seriesPlannedData.getData().add(new XYChart.Data<String, Number>(dayPlan.getDate().toString().substring(0, 3),
    				dayPlan.getDayPlan().get(planNameBox.getValue()).getActivityPlan().getPlannedCount()));// add data of planned activity to series
    				if(dayPlan.getDayPlan().get(planNameBox.getValue()).getActivityPlan().getPlannedCount() > maxRange) {
    					maxRange = dayPlan.getDayPlan().get(planNameBox.getValue()).getActivityPlan().getPlannedCount();
    				}
    			System.out.println("The real count of "+planNameBox.getValue()+" at "+dayPlan.getDate().toString()+" is "+dayPlan.getDayPlan().get(planNameBox.getValue()).getRealCount());
    			System.out.println("The planned count is "+dayPlan.getDayPlan().get(planNameBox.getValue()).getActivityPlan().getPlannedCount());
    	}
    	yAxis.setUpperBound(maxRange+5-maxRange%5);
    	areaChart.getData().clear();
    	//areaChart.setVerticalGridLinesVisible(true);
    	areaChart.getData().addAll(seriesPlannedData,seriesRealData);
    }
    
	
}
