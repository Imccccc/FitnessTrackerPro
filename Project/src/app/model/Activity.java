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

public class Activity {
    private final StringProperty activityName;
    private final StringProperty planCount; //Should saved in plan
    private final StringProperty userCount; //Should saved in plan
    private final SimpleStringProperty unitsecond;

    /**
     * Default constructor.
     */
    public Activity() {
        this(null, (Integer) null);
    }
    
    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     * @param lastName
     */
    public Activity(String name, int count) {
        this.activityName = new SimpleStringProperty(name);
        this.planCount = new SimpleStringProperty(Integer.toString(count));

        // Some initial dummy data, just for convenient testing.
        this.userCount = new SimpleStringProperty(Integer.toString(0));
        this.unitsecond = new SimpleStringProperty(Integer.toString(0));
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
    
    public String getPlanCount() {
        return this.planCount.get();
    }
    
    public void setPlanCount(int count) {
        this.planCount.set(Integer.toString(count));
    }
    
	public StringProperty planCountProperty() {
		return planCount;
	}
    
    public String getUserCount() {
        return this.userCount.get();
    }
    
    public void setUserCount(int count) {
        this.userCount.set(Integer.toString(count));
    }
    	
	public StringProperty userCountProperty() {
		return userCount;
	}    
}


