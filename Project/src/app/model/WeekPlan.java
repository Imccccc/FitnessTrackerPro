package app.model;

import java.io.Serializable;

import javafx.beans.property.SimpleListProperty;

public class WeekPlan implements Serializable{
	private static final long serialVersionUID = 1L;
	private final SimpleListProperty<DayPlan> dayPlanList;

	
	public WeekPlan(SimpleListProperty<DayPlan> dayPlanList){
		this.dayPlanList = dayPlanList;
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
}
