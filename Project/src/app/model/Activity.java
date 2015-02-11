package app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.*;

/**
 * 
 * Model class for a Person.
 *
 */
enum Unit {
	TIMES, MINUTE
	}

public class Activity {
    private final StringProperty activityName;
    private final IntegerProperty planCount; //Should saved in plan
    private final IntegerProperty userCount; //Should saved in plan
    private final ObjectProperty<Unit> unit;

    
    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     * @param lastName
     */
    public Activity(String name, int count, Unit unit) {
        this.activityName = new SimpleStringProperty(name);
        this.planCount = new SimpleIntegerProperty(count);
        this.userCount = new SimpleIntegerProperty(0);
        this.unit = new SimpleObjectProperty<Unit>(unit);
    }
    
    public Activity(String name, int count) {
        this.activityName = new SimpleStringProperty(name);
        this.planCount = new SimpleIntegerProperty(count);
        this.userCount = new SimpleIntegerProperty(0);
        this.unit = null;
    }
    
    
    
    public String getActvityName() {
        return activityName.get();
    }
    
    public void setActvityName(String Name) {
        this.activityName.set(Name);
    }
 
	public StringProperty ActvityNameProperty() {
		return activityName;
	}
    
    public Integer getPlanCount() {
        return this.planCount.get();
    }
    
    public void setPlanCount(int count) {
        this.planCount.set(count);
    }
    
	public IntegerProperty planCountProperty() {
		return planCount;
	}
    
    public Integer getUserCount() {
        return this.userCount.get();
    }
    
    public void setUserCount(int count) {
        this.userCount.set(count);
    }
    	
	public IntegerProperty userCountProperty() {
		return userCount;
	}    
    
    public Unit getUnit(){
    	return this.unit.get();
    }
    
    public void setUnit(Unit unit){
    	this.unit.set(unit);
    }
    
    public ObjectProperty<Unit> unitProperty() {
        return unit;
    }
}


