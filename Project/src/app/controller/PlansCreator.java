package app.controller;

import java.util.Date;

import app.model.ActivityPlan;
import app.model.RealDayPlan;
import app.model.RealActivityPlan;
import app.model.Unit;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class PlansCreator {
	ObservableList<RealDayPlan> weekPlan = FXCollections.observableArrayList();
	
	
	ObservableMap<String, RealActivityPlan> map1 = FXCollections.observableHashMap();
    ObservableMap<String, RealActivityPlan> map2 = FXCollections.observableHashMap();
    ObservableMap<String, RealActivityPlan> map3 = FXCollections.observableHashMap();
    ObservableMap<String, RealActivityPlan> map4 = FXCollections.observableHashMap();
    ObservableMap<String, RealActivityPlan> map5 = FXCollections.observableHashMap();
    ObservableMap<String, RealActivityPlan> map6 = FXCollections.observableHashMap();
    ObservableMap<String, RealActivityPlan> map7 = FXCollections.observableHashMap();
    
    MapProperty<String, RealActivityPlan> mapP1;
    MapProperty<String, RealActivityPlan> mapP2;
    MapProperty<String, RealActivityPlan> mapP3;
    MapProperty<String, RealActivityPlan> mapP4;
    MapProperty<String, RealActivityPlan> mapP5;
    MapProperty<String, RealActivityPlan> mapP6;
    MapProperty<String, RealActivityPlan> mapP7;
    
    Date todayDate;
    
	public PlansCreator() {
		
		
		map1.put("Running", new RealActivityPlan(new ActivityPlan("Running", Unit.TIMES, 20), 11));
    	map1.put("c2", new RealActivityPlan(new ActivityPlan("c2", Unit.TIMES, 10), 9));
    	map1.put("c3", new RealActivityPlan(new ActivityPlan("c3", Unit.TIMES, 20), 17));
    	
    	map2.put("Running", new RealActivityPlan(new ActivityPlan("Running", Unit.TIMES, 30), 12));
    	map2.put("c2", new RealActivityPlan(new ActivityPlan("c2", Unit.TIMES, 10), 11));
    	map2.put("c3", new RealActivityPlan(new ActivityPlan("c3", Unit.TIMES, 20), 19));
    	
    	map3.put("Running", new RealActivityPlan(new ActivityPlan("Running", Unit.TIMES, 20), 13));
    	map3.put("c2", new RealActivityPlan(new ActivityPlan("c2", Unit.TIMES, 10), 9));
    	map3.put("c3", new RealActivityPlan(new ActivityPlan("c3", Unit.TIMES, 10), 12));
    	
    	map4.put("Running", new RealActivityPlan(new ActivityPlan("Running", Unit.TIMES, 22), 14));
    	map4.put("c2", new RealActivityPlan(new ActivityPlan("c2", Unit.TIMES, 10), 13));
    	map4.put("c3", new RealActivityPlan(new ActivityPlan("c3", Unit.TIMES, 20), 10));
    	
    	map5.put("Running", new RealActivityPlan(new ActivityPlan("Running", Unit.TIMES, 10), 15));
    	map5.put("c2", new RealActivityPlan(new ActivityPlan("c2", Unit.TIMES, 10), 9));
    	map5.put("c3", new RealActivityPlan(new ActivityPlan("c3", Unit.TIMES, 20), 10));

    	map6.put("Running", new RealActivityPlan(new ActivityPlan("Running", Unit.TIMES, 20), 16));
    	map6.put("c2", new RealActivityPlan(new ActivityPlan("c2", Unit.TIMES, 10), 9));
    	map6.put("c3", new RealActivityPlan(new ActivityPlan("c3", Unit.TIMES, 20), 17));
    	
    	map7.put("Running", new RealActivityPlan(new ActivityPlan("Running", Unit.TIMES, 20), 17));
    	map7.put("c2", new RealActivityPlan(new ActivityPlan("c2", Unit.TIMES, 10), 9));
    	map7.put("c3", new RealActivityPlan(new ActivityPlan("c3", Unit.TIMES, 20), 17));
    	
    	
    	mapP1 = new SimpleMapProperty<>(map1);
    	mapP2 = new SimpleMapProperty<>(map2);
    	mapP3 = new SimpleMapProperty<>(map3);
    	mapP4 = new SimpleMapProperty<>(map4);
    	mapP5 = new SimpleMapProperty<>(map5);
    	mapP6 = new SimpleMapProperty<>(map6);
    	mapP7 = new SimpleMapProperty<>(map7);
    	
    	
	}
	
	public ObservableList<RealDayPlan> create() {
		todayDate = new Date();
		//weekPlan.add(new RealDayPlan(mapP7, new Date(todayDate.getTime() - 6*24*3600*1000)));
		weekPlan.add(new RealDayPlan(mapP6, new Date(todayDate.getTime() - 5*24*3600*1000)));
		weekPlan.add(new RealDayPlan(mapP5, new Date(todayDate.getTime() - 4*24*3600*1000)));
		weekPlan.add(new RealDayPlan(mapP4, new Date(todayDate.getTime() - 3*24*3600*1000)));
		weekPlan.add(new RealDayPlan(mapP3, new Date(todayDate.getTime() - 2*24*3600*1000)));
		weekPlan.add(new RealDayPlan(mapP2, new Date(todayDate.getTime() - 1*24*3600*1000)));
		weekPlan.add(new RealDayPlan(mapP1, todayDate));
		return weekPlan;
	}
}
