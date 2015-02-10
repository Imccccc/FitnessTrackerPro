package app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * Model class for a Person.
 *
 */

public class Activity {
    private final StringProperty activityName;
    private final IntegerProperty planCount;
    private final IntegerProperty userCount;
    //private final IntegerProperty unitsecond;

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
        this.planCount = new SimpleIntegerProperty(count);

        // Some initial dummy data, just for convenient testing.
        this.userCount = new SimpleIntegerProperty(0);
    }
    
    public String getActvityName() {
        return activityName.get();
    }
    
    public void setActvityName(String Name) {
        this.activityName.set(Name);
    }
 
    public int getPlanCount() {
        return this.planCount.get();
    }
    
    public int getUserCount() {
        return this.userCount.get();
    }
}


