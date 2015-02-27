package app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class RealActivityPlan {
	private final ObjectProperty<ActivityPlan> activityPlan;
	private final IntegerProperty realCount;
	
	public RealActivityPlan(ActivityPlan activityPlan, int realCount) {
		this.activityPlan = new SimpleObjectProperty<ActivityPlan>(activityPlan);
		this.realCount = new SimpleIntegerProperty(realCount);
	}
	
	public ActivityPlan getActivityPlan() {
		return this.activityPlan.get();
	}
	
	public ObjectProperty<ActivityPlan> activityPlanProperty() {
        return activityPlan;
    }
	
	public void setRealCount(int count) {
		this.realCount.set(count);
	}
	
	public Integer getRealCount() {
        return this.realCount.get();
    }
	
	public IntegerProperty realCountProperty() {
		return realCount;
	}
	
}
