package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Map.Entry;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import app.model.Activity;
import app.model.ActivityPlan;
import app.model.DayPlan;
import app.model.Unit;
import app.model.WeekPlan;

public class ClassSerializer {
	public static void ActivitySerializer(ObservableList<Activity> activityList){
		try {
			File file = new File("./activityList");
			PrintWriter pw = new PrintWriter(file);
			pw.println("<ActivityList>");
			for(Activity activity : activityList){
				pw.println(activity.getActvityName()+"|"+activity.getUnit().toString());
			}
			pw.println("</ActivityList>");
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ObservableList<Activity> ActivityUnserializer(){
		ObservableList<Activity> activityList = FXCollections.observableArrayList();
		try{
			File file = new File("./activityList");
			Scanner scan = new Scanner(file);
			String input;
			input = scan.nextLine();
			if(!input.equals("<ActivityList>"))	{
				scan.close();
				return null;
				}
			input = scan.nextLine();
			while(!input.equals("</ActivityList>")){
				String info[] = input.split("\\|");
				Activity newActivity = new Activity(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE);
				activityList.add(newActivity);
				input = scan.nextLine();
			}
			scan.close();
			return activityList;
		}
		catch(FileNotFoundException e){
			return activityList;
		}
	}
	
	public static void WeekPlanSerializer(WeekPlan weekPlan){
		try {
			File file = new File("./weekPlan");
			PrintWriter pw = new PrintWriter(file);
			pw.println("<WeekPlan>");
			for(int i=0; i < 7; i++){
				pw.println("<Day"+i+">");
				DayPlan dayPlan = weekPlan.getDayPlan(i);
				for(Entry<String, ActivityPlan> entry : dayPlan.getDayPlan().entrySet()){
		            ActivityPlan a = entry.getValue();
		            pw.println(a.getActivity().getActvityName()+"|"+a.getActivity().getUnit().toString()+"|"+a.getPlannedCount());
		        }
				pw.println("</Day"+i+">");
			}
			pw.println("</WeekPlan>");
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static WeekPlan WeekPlanUnserializer(){
		try{
			File file = new File("./weekPlan");
			Scanner scan = new Scanner(file);
			ObservableList<DayPlan> dayPlanList = FXCollections.observableArrayList();
			String input;
			input = scan.nextLine();
			if(!input.equals("<WeekPlan>"))	{
				scan.close();
				return null;
			}
			input = scan.nextLine();
			for(int i=0;i<7;i++){
				assert(input.equals("<Day"+i+">"));
				ObservableMap<String, ActivityPlan> observableMap = FXCollections.observableHashMap();
				input = scan.nextLine();
				while(!input.equals("</Day"+i+">")){
					String[] info = input.split("\\|");
					ActivityPlan actPlan = new ActivityPlan(
							new Activity(info[0], info[1].equals("TIMES")?Unit.TIMES:Unit.MINUTE), 
							Integer.parseInt(info[2]));
					observableMap.put(info[0], actPlan);
					input = scan.nextLine();
				}
				
				DayPlan dayPlan = new DayPlan(new SimpleMapProperty<>(observableMap));
				dayPlanList.add(dayPlan);
				input = scan.nextLine();
			}
			assert(input.equals("</WeekPlan>"));
			
			WeekPlan weekPlan = new WeekPlan(new SimpleListProperty<>(dayPlanList), "Current Plan");
			scan.close();
			return weekPlan;
		}
		catch(FileNotFoundException e){
			return null;
		}
	}
	
	public static void WishListSerializer(ObservableMap<String, WeekPlan> wishList){
		try {
			File file = new File("./wishList");
			PrintWriter pw = new PrintWriter(file);
			pw.println("<WishList>");
			for(WeekPlan weekPlan : wishList.values()){
				pw.println("<WishPlan>");
				pw.println("<Name>");
				pw.println(weekPlan.getPlanName());
				pw.println("</Name>");
				for(int i=0; i < 7; i++){
					pw.println("<Day"+i+">");
					DayPlan dayPlan = weekPlan.getDayPlan(i);
					for(Entry<String, ActivityPlan> entry : dayPlan.getDayPlan().entrySet()){
			            ActivityPlan a = entry.getValue();
			            pw.println(a.getActivity().getActvityName()+"|"+a.getActivity().getUnit().toString()+"|"+a.getPlannedCount());
			        }
					pw.println("</Day"+i+">");
				}
				pw.println("</WishPlan>");
			}		
			pw.println("</WishList>");
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ObservableMap<String, WeekPlan> WishListUnserializer(){
		try{
			File file = new File("./wishList");
			Scanner scan = new Scanner(file);
			String planName;
			ObservableMap<String, WeekPlan> wishList = FXCollections.observableHashMap();
			
			String input;
			input = scan.nextLine();
			
			if(!input.equals("<WishList>"))	{
				scan.close();
				return null;
			}
			
			input = scan.nextLine();
			
			while(input.equals("<WishPlan>")){
				// Get name
				input = scan.nextLine();
				assert( input.equals("<Name>") );

				planName = scan.nextLine();
				
				input = scan.nextLine();
				assert( input.equals("</Name>"));
				
				input = scan.nextLine();
				
				ObservableList<DayPlan> dayPlanList = FXCollections.observableArrayList();
				// Get plan
				for (int i = 0; i < 7; i++) {
					assert (input.equals("<Day" + i + ">"));
					ObservableMap<String, ActivityPlan> observableMap = FXCollections.observableHashMap();
					input = scan.nextLine();
					
					while (!input.equals("</Day" + i + ">")) {
						String[] info = input.split("\\|");
						//System.out.println(input);
						ActivityPlan actPlan = new ActivityPlan(new Activity(
								info[0], info[1].equals("TIMES") ? Unit.TIMES: Unit.MINUTE),
								Integer.parseInt(info[2]));
						observableMap.put(info[0], actPlan);
						
						input = scan.nextLine();
					}
					
					// Generate dayPlan List and add into weekPlan
					//System.out.println(planName+"||"+i+"||"+observableMap.size());
					DayPlan dayPlan = new DayPlan(new SimpleMapProperty<>(observableMap));
					dayPlanList.add(dayPlan);
					
					input = scan.nextLine();
				}
				
				assert(input.equals("</WishPlan>"));
				
				// Generate weekPlan using dayPlan list
				WeekPlan weekPlan = new WeekPlan(new SimpleListProperty<>(dayPlanList), planName);
				
				//Insert into the wishList
				wishList.put(planName, weekPlan);
				
				input = scan.nextLine();
			}
			
			assert(input.equals("</WishList>"));
						
			scan.close();
			return wishList;
		}
		catch(FileNotFoundException e){
			return null;
		}
	}
	
}
