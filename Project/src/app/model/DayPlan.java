package app.model;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.ObservableMap;


public class DayPlan {
	private final MapProperty<String, ActivityPlan> dayPlan;
	
	
	public DayPlan(MapProperty<String, ActivityPlan>dayPlan) {
		this.dayPlan = new SimpleMapProperty<String, ActivityPlan>(dayPlan);
	}

	
	public void setDayPlan(ObservableMap<String, ActivityPlan> dayPlan) {
		this.dayPlan.set(dayPlan);
	}
	
	public ObservableMap<String, ActivityPlan> getDayPlan() {
		return this.dayPlan.get();
	}
	
	public MapProperty<String, ActivityPlan> mapProperty() {
		return dayPlan;
	}
	
}
