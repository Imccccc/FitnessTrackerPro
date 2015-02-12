package app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ActivityPlan {
	private final ObjectProperty<Activity> activity;
	private final IntegerProperty plannedCount;
	
	public ActivityPlan(Activity activity, int plannedCount) {
		this.activity = new SimpleObjectProperty<Activity>(activity);
		this.plannedCount = new SimpleIntegerProperty(plannedCount);
	}
	
	public ActivityPlan(String name, Unit unit, int plannedCount) {
        this.activity = new SimpleObjectProperty<Activity>(new Activity(name, unit));
        this.plannedCount = new SimpleIntegerProperty(plannedCount);
    }
	
	public void setActivity(Activity activity) {
		this.activity.set(activity);
	}
	
	public Activity getActivity() {
		return this.activity.get();
	}
	
	public ObjectProperty<Activity> activityProperty() {
        return activity;
    }
	
	public void setPlannedCount(int count) {
		this.plannedCount.set(count);
	}
	
	public Integer getPlannedCount() {
        return this.plannedCount.get();
    }
	
	public IntegerProperty plannedCountProperty() {
		return plannedCount;
	}
	
}