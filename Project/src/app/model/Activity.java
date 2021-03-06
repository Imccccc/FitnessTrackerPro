package app.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * Model class for a Activity.
 *
 */

public class Activity {
    private final StringProperty activityName;
    private final ObjectProperty<Unit> unit;
    
    
    public Activity(String name, Unit unit) {
        this.activityName = new SimpleStringProperty(name);
        this.unit = new SimpleObjectProperty<Unit>(unit);
    }
    
    public Activity(Activity a) {
        this.activityName = new SimpleStringProperty(a.getActvityName());
        this.unit = new SimpleObjectProperty<Unit>(a.getUnit());
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