package app.model;

import java.io.Serializable;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WeekPlan implements Serializable{
	private static final long serialVersionUID = 1L;
	private final SimpleListProperty<DayPlan> dayPlanList;
	private final StringProperty planNameProperty;
	private SimpleListProperty<Rating> ratingList;
	private StringProperty planType;
	private StringProperty planUserName;
	private double avgRatig;
	private int planID=0;
	
	public int getPlanID() {
		return planID;
	}

	public void setPlanID(int planID) {
		this.planID = planID;
	}

	public WeekPlan(SimpleListProperty<DayPlan> dayPlanList, String name){
		this.dayPlanList = dayPlanList;
		this.planNameProperty = new SimpleStringProperty(name);
		this.planType=new SimpleStringProperty("");
		this.planUserName=new SimpleStringProperty("");
		ObservableList<Rating> observabledayplanlist = FXCollections.observableArrayList();
		this.ratingList=new SimpleListProperty<Rating>(observabledayplanlist);
		this.avgRatig = 0;
	}
	
	public WeekPlan(SimpleListProperty<DayPlan> dayPlanList, String name, String plantype){
		this(dayPlanList, name);
		this.planType=new SimpleStringProperty(plantype);
	}
	
	public WeekPlan(SimpleListProperty<DayPlan> dayPlanList, String name, String plantype,String username,Double rate){
		this(dayPlanList, name,plantype);
		this.planUserName = new SimpleStringProperty(username);
		this.avgRatig = rate;
	}
	
	
	public void addRating(Rating rating){
		ratingList.add(rating);
	}
	
	public void setPlanType(String type){
		this.planType = new SimpleStringProperty(type);
	}
	
	public String getPlanType(){
		return planType.get();
	}
	
	public SimpleListProperty<Rating> getRating(){
		return ratingList;
	}
	
	/*Index:
	 * 0 - Sunday
	 * 1 - Monday
	 * ...
	 * 6 - Saturday
	 * */
	public DayPlan getDayPlan(int index){
		return this.dayPlanList.get(index);
	}

	public void setDayPlan(int index, DayPlan dayplan){
		this.dayPlanList.set(index, dayplan);
	}
	
	public StringProperty getPlanNameProperty(){
		return this.planNameProperty;
	}
	
	public String getPlanName(){
		return this.planNameProperty.get();
	}
	
	public void setPlanName(String name){
		this.planNameProperty.set(name);
	}
	
	public String getPlanUserName(){
		return planUserName.get();
	}
	
	public double getAvg(){
		return this.avgRatig;
	}
	
}
