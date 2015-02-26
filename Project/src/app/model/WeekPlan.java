package app.model;

import java.io.Serializable;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WeekPlan implements Serializable{
	private static final long serialVersionUID = 1L;
	private final SimpleListProperty<DayPlan> dayPlanList;
	private final StringProperty planNameProperty;

	
	public WeekPlan(SimpleListProperty<DayPlan> dayPlanList, String name){
		this.dayPlanList = dayPlanList;
		this.planNameProperty = new SimpleStringProperty(name);
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
}
