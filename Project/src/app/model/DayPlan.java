package app.model;

import java.sql.Date;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableMap;


public class DayPlan {
	private final MapProperty<String, RealActivityPlan> dayPlan;
	private final ObjectProperty<Date> date;
	
	public DayPlan(MapProperty<String, RealActivityPlan>dayPlan, Date date) {
		this.dayPlan = new SimpleMapProperty<String, RealActivityPlan>(dayPlan);
		this.date = new SimpleObjectProperty<Date>(date);
	}
	
	public void setDate(Date date) {
		this.date.set(date);
	}
	
	public Date getDate() {
		return this.date.get();
	}
	
	public ObjectProperty<Date> dateProperty() {
		return date;
	}
	
	public void setDayPlan(ObservableMap<String, RealActivityPlan> dayPlan) {
		this.dayPlan.set(dayPlan);
	}
	
	public ObservableMap<String, RealActivityPlan> getDayPlan() {
		return this.dayPlan.get();
	}
	
	public MapProperty<String, RealActivityPlan> mapProperty() {
		return dayPlan;
	}
	
}
