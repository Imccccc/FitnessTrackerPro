package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

import com.mysql.fabric.xmlrpc.base.Data;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import app.model.Activity;
import app.model.ActivityPlan;
import app.model.DayPlan;
import app.model.RealActivityPlan;
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

	public static void TodayPlanSerializer(ObservableList<RealActivityPlan> todayList, String date){
		System.out.println("TodayPlanSerializer called");
		try {
			File file = new File("./TodayPlan.Fitness");
			PrintWriter pw = new PrintWriter(file);
			pw.println("<Date>\n" + date + "\n</Date>");
			Activity temp; 
			for(RealActivityPlan real : todayList){
				temp = real.getActivityPlan().getActivity();
				pw.println(temp.getActvityName()+"|"+temp.getUnit().toString()+"|" + 
						real.getActivityPlan().getPlannedCount()+"|"+real.getRealCount());
			}
			pw.println();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ObservableList<RealActivityPlan> TodayPlanUnserializer(String date){
		try {
			String input;
			File file = new File("./TodayPlan.Fitness");
			Scanner scan = new Scanner(file);
			ObservableList<RealActivityPlan> today =FXCollections.observableArrayList();
			if (!scan.hasNextLine()) {
				return null;
			}
			input = scan.nextLine();
			if (input.equals("<Date>")) {
				input = scan.nextLine();
				if(input.equals(date)){
					input = scan.nextLine();
					input = scan.nextLine();
					while (!input.isEmpty()) {
						//System.out.println("TodayPlanUnserializer "+input);
						String[] info = input.split("\\|");
						//System.out.println(info[0]+info[1]+info[2]+info[3]);
						ActivityPlan Plan = new ActivityPlan(new Activity(
								info[0], info[1].equals("TIMES") ? Unit.TIMES: Unit.MINUTE),
								Integer.parseInt(info[2]));
						RealActivityPlan realPlan = new RealActivityPlan(Plan, Integer.parseInt(info[3]));
						today.add(realPlan);
						input = scan.nextLine();
					}
				}else{
					System.out.println("TodayPlanUnserializer: Should Add to history");
					String content = new Scanner(file).useDelimiter("\\Z").next();
					put_gap_day(input, content);
					String day = new String(input);


					PrintWriter out = new PrintWriter(file);
					out.print("");
					out.close();
					return null;
				}
			}
			return today;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void HistorySerializer(String dayplan){
		try{
			//we should check the last date and 
			File file = new File("./History.Fitness");
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./History.Fitness", true)));
			out.println("<DayHistory>");
			out.print(dayplan);
			out.println("</DayHistory>");
			out.close();

		}catch(Exception e){

		}
	}

	public static boolean put_gap_day(String input_date, String today_plan){ // if last day of history is yesterday, do nothing, otherwise put gaps days
		FileReader fr;
		String date = null;
		StringBuilder sb = new StringBuilder();
		boolean sameDay;
		boolean skipday;
		try {
			fr = new FileReader("./History.Fitness");
			BufferedReader br = new BufferedReader(fr);
			String ch;

			List<String> tmp = new ArrayList<String>();
			do {
				ch = br.readLine();
				tmp.add(ch);
			} while (ch != null);
			fr.close();
			for(int i=tmp.size()-1;i>=0;i--) {
				if(tmp.get(i)!= null && tmp.get(i).equals("</Date>")){
					date = new String(tmp.get(i-1));
					System.out.println("last in history is " + date);
					break;
				}
				//System.out.print(tmp.get(i)+"\n");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date lastday = sdf.parse(date);
			Date today = new Date();
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			Calendar cal3 = Calendar.getInstance();
			cal1.setTime(lastday); // last date in history
			cal2.setTime(today);
			cal3.setTime(sdf.parse(input_date)); // the date that today plan stored
			cal1.add(Calendar.DATE, 1);  // number of days to add
			sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
					cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
			if(sameDay){
				return false;
			}
			while(!sameDay){
				if(cal1.get(Calendar.YEAR) == cal3.get(Calendar.YEAR) &&
						cal1.get(Calendar.DAY_OF_YEAR) == cal3.get(Calendar.DAY_OF_YEAR)){
					HistorySerializer(today_plan);
					cal1.add(Calendar.DATE, 1);  // number of days to add
					sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
							cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
					continue;
				}else{
					date = sdf.format(cal1.getTime());  // dt is now the new date
					sb = new StringBuilder();
					sb.append("<Date>\n").append(date).append("\n</Date>\n");
					//System.out.print("Data(dd/MM/yyyy) is " + date);
					int dayOfWeek = cal1.get(Calendar.DAY_OF_WEEK);
					//System.out.println(" and the "+ (dayOfWeek-1) +"th of the week");
					DayPlan dayplan = MainApp.weekPlan.getDayPlan((dayOfWeek-1));
					for(Entry<String, ActivityPlan> entry: dayplan.getDayPlan().entrySet()){
						Activity temp = entry.getValue().getActivity();
						//System.out.println(temp.getActvityName()+"|"+temp.getUnit().toString()+"|" + 
						//entry.getValue().getPlannedCount()+"|"+0);
						sb.append(temp.getActvityName()+"|"+temp.getUnit().toString()+"|" + 
								entry.getValue().getPlannedCount()+"|"+0 + "\n");
					}
					HistorySerializer(sb.toString());
					cal1.add(Calendar.DATE, 1);  // number of days to add
					sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
							cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
}
