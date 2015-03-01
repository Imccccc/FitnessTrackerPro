package app.model;

import java.io.Serializable;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WeekPlan implements Serializable{
	private static final long serialVersionUID = 1L;
	private final SimpleListProperty<DayPlan> dayPlanList;
	private final StringProperty planNameProperty;
	private SimpleListProperty<Rating> ratingList;
	private StringProperty planType;
	
	public WeekPlan(SimpleListProperty<DayPlan> dayPlanList, String name){
		this.dayPlanList = dayPlanList;
		this.planNameProperty = new SimpleStringProperty(name);
		this.planType=new SimpleStringProperty("");
		this.ratingList=new SimpleListProperty<Rating>();
	}
	
	public WeekPlan(SimpleListProperty<DayPlan> dayPlanList, String name, String plantype){
		this(dayPlanList, name);
		this.planType=new SimpleStringProperty(plantype);
	}
	
	public void addRating(Rating rating){
		ratingList.add(rating);
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
}
