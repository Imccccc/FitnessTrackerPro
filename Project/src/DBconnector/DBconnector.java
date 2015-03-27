package DBconnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

import javafx.beans.property.*;
import javafx.collections.*;
import app.model.*;

public abstract class DBconnector {
	
	
	final static String DBaddress="jdbc:mysql://98.223.107.97/";
	final static String DBuser="408";
	final static String DBpass="408";
	final static String DBdatabase="fitness";

	public static String username=null;
	static Connection con=null;
	
	static{
		connect();
		createUser("lhc3","123'");
		//login("lhc","123");
	}
	
	public static void main(String args[]){
		
		
		
		login("tester","111111");
		setCompeteable(true);
		System.out.println(isCompeteable("tester"));
		//addRating(30,4,"hello");
		
//		System.out.println(SQLSpecialChar("asd' OR 1=1--\\"));
		
//		System.out.println("getplans1:"+getPlans().size());
		
//		System.out.println("getplans2:"+getPlans("1|3").size());
		
	//	System.out.println("getplans2:"+getPlans("1|3",true).size());
		
//		System.out.println("getplans2:"+getPlans(true).toString());
		
	}
	
	public static boolean isLoggedIn(){
		return username!=null;
	}
	
	public static int connect(){//0 success -1fail
		if (con!=null) return -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DBaddress+DBdatabase,DBuser,DBpass);
			return 0;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static void close(){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con=null;
		username=null;
	}


	public static int login(String username, String password){//0 success -1other error  -2not exist user -3wrong password
		if (con==null) {
			System.out.println("Connot connect to database");
			return -1;
		}

		try{
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM account WHERE username='"+username+"'");
			if(!result.next()){  
				System.out.println("not existing username");
				DBconnector.username=null;
				return -2;
			}else{
				if (password.equals(result.getString("password"))){
					System.out.println("Success login");
					DBconnector.username=username;
					return 0;
				}else{
					System.out.println("wrong password");
					return -3;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
	

	public static int createUser(String username, String password){//0 success -1other error -2existing
		if (con==null) {
			System.out.println("Connot connect to database");
			return -1;
		}
		username=SQLSpecialChar(username);
		password=SQLSpecialChar(password);

		try{
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM account WHERE username='"+username+"'");
			if(result.next()){  
				System.out.println("Existing username");
				return -2;
			}
			stmt.executeUpdate("INSERT INTO account (username,password) VALUES ('"+username+"','"+password+"')");
			DBconnector.username=username;
			System.out.println("Successfully creating account");
			return 0;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int addRating(int planid, double rating, String comments){//0seccess  -1fail
		if (username==null) {
			System.out.println("Not logged in");
			return -1;
		}
		comments=SQLSpecialChar(comments);
		
		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO rating (username,planid,rating,comments) VALUES ('"+username+"','"+planid+"','"+rating+"','"+comments+"')");
			System.out.println("Successfully adding rating");
			return 0;
		}catch(SQLException e){
			try {
				Statement stmt = con.createStatement();
				String str = "update rating set rating = "+rating+" , comments = '"+comments+"' where username = '"+username+"' and planid = "+planid;
				stmt.executeUpdate(str);
				System.out.println("Successfully adding rating");
				return 0;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return -1;
		}
	}
	
	public static double getAvgRating(int planid){
		if (con==null) {
			System.out.println("Connot connect to database");
			return -1;
		}
		try{
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT avg(rating) from rating where planid = "+planid);
			Double res = 0.0;
			if(result.next()){
				res = result.getDouble(1)*100;	
				res = (double) Math.round(res)/100;
				System.out.println("Successfully get avg rating"+ res);
			}
			return res;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}	
	}
	
	public static int reportToday(double calories){//0seccess  -1fail
		if (username==null) {
			System.out.println("Not logged in");
			return -1;
		}
		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO personalexerciseamount (username,date,calories) VALUES ('"+username+"',curdate(),"+calories+")");
			System.out.println("Successfully reporting data of today");
			return 0;
		}catch(SQLException e){
			if (e.getErrorCode()==1062){
				try {
					Statement stmt = con.createStatement();
					String a="UPDATE personalexerciseamount calories="+calories+" WHERE date=curdate() AND username='"+username+"'";
					System.out.println(a);
					stmt.executeUpdate("UPDATE personalexerciseamount SET calories="+calories+" WHERE date=curdate() AND username='"+username+"'");
				} catch (SQLException e1) {
					e1.printStackTrace();
					return -1;
				}
			}
			return 0;
		}
	}
	
	public static ArrayList<app.model.dayAmount> getExerciseAmount(String username){//0seccess  -1fail
		if (con==null) {
			System.out.println("Connot connect to database");
			return null;
		}
		
		try{
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM personalexerciseamount WHERE username='"+username+"'");
			ArrayList<app.model.dayAmount> ret=new ArrayList<app.model.dayAmount>();
			while(result.next()){
				app.model.dayAmount temp=new app.model.dayAmount(result.getDate("date"),result.getDouble("calories"));
				ret.add(temp);
			}
			if (ret.isEmpty()) return null;
			System.out.println("Successfully select");
			return ret;
		}catch(SQLException e){
			return null;
		}
	}
	
	public static ArrayList<WeekPlan> getPlans(String filters){
		if (con==null) {
			System.out.println("Connot connect to database");
			return null;
		}
		try{
			String[] subfilter = filters.split("\\|");
			
			String where = "";

			for(int i = 0 ; i< (subfilter.length-1); i++){
				where += (" plantype LIKE '"+subfilter[i]+"' OR ");
			}
			where += ("plantype LIKE '"+subfilter[subfilter.length-1]+"'");
		
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM sharedplan where "+where);
			int planid = -1;
			int weekday = -1;
			String planname = "";
			String planType = "";
			String spusername = "";
			
			ArrayList<WeekPlan> wps = new ArrayList<WeekPlan>();
			
			while(result.next()){
				
				planid = result.getInt("planid");
				planname = result.getString("planname");
				planType = result.getString("plantype"); 
				spusername = result.getString("username"); 
				
				Statement stmt2 = con.createStatement();
				ResultSet result2 = stmt2.executeQuery("SELECT DISTINCT WEEKDAY FROM plan where planid="+planid);
				
				ObservableList<DayPlan> observabledayplanlist = FXCollections.observableArrayList();
				SimpleListProperty<DayPlan> dayplanlist=new SimpleListProperty<DayPlan>(observabledayplanlist);
				while(result2.next()){
					
					weekday = result2.getInt("weekday");
					
					Statement stmt3 = con.createStatement();
					ResultSet result3 = stmt3.executeQuery("SELECT * FROM plan p INNER JOIN activity a ON p.`activityid` = a.`activityid` WHERE p.`planid`= "+planid+" AND p.`weekday` = "+weekday);
					
					ObservableMap<String, ActivityPlan> map = FXCollections.observableHashMap();
					while(result3.next()){
						Activity ac = new Activity(result3.getString("name"),result3.getInt("unitsecond")==0?Unit.TIMES:Unit.MINUTE);
						
						ActivityPlan ap = new ActivityPlan(ac,result3.getInt("amount"));
										
						map.put(result3.getString("name"), ap);
						
					}
					MapProperty<String, ActivityPlan> mapProperty = new SimpleMapProperty<>(map);
					DayPlan dayPlan = new DayPlan(mapProperty);
					
					System.out.println(weekday+" "+dayPlan.toString());
					dayplanlist.add(weekday,dayPlan);
					
				}
	//			System.out.println("next dayplanlist:");
				Double rate = getAvgRating(planid);
				
				WeekPlan wp = new WeekPlan(dayplanlist,planname,planType,spusername,rate);
				
				Statement stmt5 = con.createStatement();
				ResultSet result5 = stmt5.executeQuery("SELECT * FROM rating WHERE planid = "+planid);
				while(result5.next()){
					Rating r = new Rating(result5.getString("username"),result5.getDouble("rating"),result5.getString("comments"));
					wp.addRating(r);
	//				System.out.println("rating:"+r.toString()+" by "+r.getUsername());
				}
				wp.setPlanID(planid);
				wps.add(wp);
							
			}
			return wps;
			
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static ArrayList<WeekPlan> getPlans(){
		if (con==null) {
			System.out.println("Connot connect to database");
			return null;
		}
		try{
			
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM sharedplan");
			int planid = -1;
			int weekday = -1;
			String planname = "";
			String planType = "";
			String spusername = "";
			
			ArrayList<WeekPlan> wps = new ArrayList<WeekPlan>();
			
			while(result.next()){
				
				planid = result.getInt("planid");
				planname = result.getString("planname");
				planType = result.getString("plantype"); 
				spusername = result.getString("username"); 
				
				Statement stmt2 = con.createStatement();
				ResultSet result2 = stmt2.executeQuery("SELECT DISTINCT WEEKDAY FROM plan where planid="+planid);
				
				ObservableList<DayPlan> observabledayplanlist = FXCollections.observableArrayList();
				SimpleListProperty<DayPlan> dayplanlist=new SimpleListProperty<DayPlan>(observabledayplanlist);
				while(result2.next()){
					
					weekday = result2.getInt("weekday");
					
					Statement stmt3 = con.createStatement();
					ResultSet result3 = stmt3.executeQuery("SELECT * FROM plan p INNER JOIN activity a ON p.`activityid` = a.`activityid` WHERE p.`planid`= "+planid+" AND p.`weekday` = "+weekday);
					
					ObservableMap<String, ActivityPlan> map = FXCollections.observableHashMap();
					while(result3.next()){
						Activity ac = new Activity(result3.getString("name"),result3.getInt("unitsecond")==0?Unit.TIMES:Unit.MINUTE);
						
						ActivityPlan ap = new ActivityPlan(ac,result3.getInt("amount"));
										
						map.put(result3.getString("name"), ap);
						
					}
					MapProperty<String, ActivityPlan> mapProperty = new SimpleMapProperty<>(map);
					DayPlan dayPlan = new DayPlan(mapProperty);
					
	//				System.out.println(weekday+" "+dayPlan.toString());
					dayplanlist.add(weekday,dayPlan);
					
				}
		//		System.out.println("next dayplanlist:");
				Double rate = getAvgRating(planid);
				WeekPlan wp = new WeekPlan(dayplanlist,planname,planType,spusername,rate);
				
				Statement stmt5 = con.createStatement();
				ResultSet result5 = stmt5.executeQuery("SELECT * FROM rating WHERE planid = "+planid);
				while(result5.next()){
					Rating r = new Rating(result5.getString("username"),result5.getDouble("rating"),result5.getString("comments"));
					wp.addRating(r);
		//			System.out.println("rating:"+r.toString()+" by "+r.getUsername());
				}
				wp.setPlanID(planid);
				wps.add(wp);
							
			}
			return wps;
			
			
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	//get filtered plans order by avgrating desc if parameter is true
	public static ArrayList<WeekPlan> getPlans(String filters,boolean order){
		if (con==null) {
			System.out.println("Connot connect to database");
			return null;
		}
		try{
			String[] subfilter = filters.split("\\|");
			
			String where = "";

			for(int i = 0 ; i< (subfilter.length-1); i++){
				where += (" plantype LIKE '"+subfilter[i]+"' OR ");
			}
			where += ("plantype LIKE '"+subfilter[subfilter.length-1]+"'");
		
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM sharedplan where "+where);
			int planid = -1;
			int weekday = -1;
			String planname = "";
			String planType = "";
			String spusername = "";
			
			ArrayList<WeekPlan> wps = new ArrayList<WeekPlan>();
			
			while(result.next()){
				
				planid = result.getInt("planid");
				planname = result.getString("planname");
				planType = result.getString("plantype"); 
				spusername = result.getString("username"); 
				
				Statement stmt2 = con.createStatement();
				ResultSet result2 = stmt2.executeQuery("SELECT DISTINCT WEEKDAY FROM plan where planid="+planid);
				
				ObservableList<DayPlan> observabledayplanlist = FXCollections.observableArrayList();
				SimpleListProperty<DayPlan> dayplanlist=new SimpleListProperty<DayPlan>(observabledayplanlist);
				while(result2.next()){
					
					weekday = result2.getInt("weekday");
					
					Statement stmt3 = con.createStatement();
					ResultSet result3 = stmt3.executeQuery("SELECT * FROM plan p INNER JOIN activity a ON p.`activityid` = a.`activityid` WHERE p.`planid`= "+planid+" AND p.`weekday` = "+weekday);
					
					ObservableMap<String, ActivityPlan> map = FXCollections.observableHashMap();
					while(result3.next()){
						Activity ac = new Activity(result3.getString("name"),result3.getInt("unitsecond")==0?Unit.TIMES:Unit.MINUTE);
						
						ActivityPlan ap = new ActivityPlan(ac,result3.getInt("amount"));
										
						map.put(result3.getString("name"), ap);
						
					}
					MapProperty<String, ActivityPlan> mapProperty = new SimpleMapProperty<>(map);
					DayPlan dayPlan = new DayPlan(mapProperty);
					
//					System.out.println(weekday+" "+dayPlan.toString());
					dayplanlist.add(weekday,dayPlan);
					
				}
				Double rate = getAvgRating(planid);
				
				WeekPlan wp = new WeekPlan(dayplanlist,planname,planType,spusername,rate);
				
				Statement stmt5 = con.createStatement();
				ResultSet result5 = stmt5.executeQuery("SELECT * FROM rating WHERE planid = "+planid);
				while(result5.next()){
					Rating r = new Rating(result5.getString("username"),result5.getDouble("rating"),result5.getString("comments"));
					wp.addRating(r);
					System.out.println("rating:"+r.toString()+" by "+r.getUsername());
				}
				wp.setPlanID(planid);
				wps.add(wp);
				wps.sort(new Comparator<WeekPlan>(){
					@Override
					public int compare(WeekPlan arg0, WeekPlan arg1) {
						return arg0.getAvg()>arg1.getAvg()?-1:arg0.getAvg()==arg1.getAvg()?0:1;
					}
				});
							
			}
			return wps;
			
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	//get plans order by avgrating desc if parameter is true
	public static ArrayList<WeekPlan> getPlans(boolean order){
		if (con==null) {
			System.out.println("Connot connect to database");
			return null;
		}
		try{
			
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM sharedplan");
			int planid = -1;
			int weekday = -1;
			String planname = "";
			String planType = "";
			String spusername = "";
			
			ArrayList<WeekPlan> wps = new ArrayList<WeekPlan>();
			
			while(result.next()){
				
				planid = result.getInt("planid");
				planname = result.getString("planname");
				planType = result.getString("plantype"); 
				spusername = result.getString("username"); 
				
				Statement stmt2 = con.createStatement();
				ResultSet result2 = stmt2.executeQuery("SELECT DISTINCT WEEKDAY FROM plan where planid="+planid);
				
				ObservableList<DayPlan> observabledayplanlist = FXCollections.observableArrayList();
				SimpleListProperty<DayPlan> dayplanlist=new SimpleListProperty<DayPlan>(observabledayplanlist);
				while(result2.next()){
					
					weekday = result2.getInt("weekday");
					
					Statement stmt3 = con.createStatement();
					ResultSet result3 = stmt3.executeQuery("SELECT * FROM plan p INNER JOIN activity a ON p.`activityid` = a.`activityid` WHERE p.`planid`= "+planid+" AND p.`weekday` = "+weekday);
					
					ObservableMap<String, ActivityPlan> map = FXCollections.observableHashMap();
					while(result3.next()){
						Activity ac = new Activity(result3.getString("name"),result3.getInt("unitsecond")==0?Unit.TIMES:Unit.MINUTE);
						
						ActivityPlan ap = new ActivityPlan(ac,result3.getInt("amount"));
										
						map.put(result3.getString("name"), ap);
						
					}
					MapProperty<String, ActivityPlan> mapProperty = new SimpleMapProperty<>(map);
					DayPlan dayPlan = new DayPlan(mapProperty);
					
	//				System.out.println(weekday+" "+dayPlan.toString());
					dayplanlist.add(weekday,dayPlan);
					
				}
				System.out.println("next dayplanlist:");
				Double rate = getAvgRating(planid);
				WeekPlan wp = new WeekPlan(dayplanlist,planname,planType,spusername,rate);
				
				Statement stmt5 = con.createStatement();
				ResultSet result5 = stmt5.executeQuery("SELECT * FROM rating WHERE planid = "+planid);
				while(result5.next()){
					Rating r = new Rating(result5.getString("username"),result5.getDouble("rating"),result5.getString("comments"));
					wp.addRating(r);
					System.out.println("rating:"+r.toString()+" by "+r.getUsername());
				}
				wp.setPlanID(planid);
				wps.add(wp);
				wps.sort(new Comparator<WeekPlan>(){
					@Override
					public int compare(WeekPlan arg0, WeekPlan arg1) {
						return arg0.getAvg()>arg1.getAvg()?-1:arg0.getAvg()==arg1.getAvg()?0:1;
					}
				});		
			}
			return wps;
			
			
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static int writePlan(WeekPlan plan){
		if (username==null) {
			System.out.println("Not logged in");
			return -1;
		}
		int planid=newPlan(plan);
		for (int weekday=0;weekday<7;weekday++){
			app.model.DayPlan t=plan.getDayPlan(weekday);
			for (app.model.ActivityPlan ac:t.getDayPlan().values()){
				int activityID=getActivityID(ac.getActivity());
				int amount=ac.getPlannedCount();
				try{
					Statement stmt = con.createStatement();
					stmt.executeUpdate("INSERT INTO plan (planid,weekday,activityid,amount) VALUES "
							+ "('"+planid+"','"+weekday+"','"+activityID+"','"+amount+"')");
				}catch(SQLException e){
					e.printStackTrace();
					return -1;
				}
			}
		}
		return 0;
	}
	
	public static int getActivityID(Activity ac){
		try{
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM activity WHERE name='"+ac.getActvityName()+"'");
			while(result.next()){
				return result.getInt("activityid");
			}
			stmt.executeUpdate("INSERT INTO activity (name,unitsecond) VALUES ('"+ac.getActvityName()+"','"+(ac.getUnit()==app.model.Unit.MINUTE?1:0)+"')");
			result = stmt.executeQuery("SELECT * FROM activity WHERE name='"+ac.getActvityName()+"'");
			while(result.next()){
				return result.getInt("activityid");
			}
			return -1;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int newPlan(WeekPlan plan){
		if (username==null) return -1;
		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO sharedplan (username,plantype,planname) VALUES ('"
			+username+"','"+SQLSpecialChar(plan.getPlanType())+"','"+SQLSpecialChar(plan.getPlanName())+"')");
			ResultSet result = stmt.executeQuery("SELECT * FROM sharedplan order by planid DESC");
			while(result.next()){
				return result.getInt("planid");
			}
			return -1;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
	
	public static String SQLSpecialChar(String input){
		if (input==null) return null;
		return input.replaceAll("(['\\\\])", "\\\\$1");
	}
	
	public static boolean isCompeteable(String username){
		try{
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT Competeable FROM account WHERE username='"+username+"'");
			while(result.next()){
				return result.getBoolean("Competeable");
			}
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			return true;
		}
	}
	
	public static int setCompeteable(boolean competeable){
		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update account set Competeable="+competeable+" where username='"+username+"'");
			return 0;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
}

