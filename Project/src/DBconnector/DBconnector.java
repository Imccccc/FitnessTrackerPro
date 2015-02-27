package DBconnector;

import java.sql.*;
import java.util.ArrayList;

import javafx.beans.property.*;
import javafx.collections.*;
import app.model.*;

public abstract class DBconnector {
	
	
	final static String DBaddress="jdbc:mysql://98.223.107.97/";
	final static String DBuser="408";
	final static String DBpass="408";
	final static String DBdatabase="fitness";
	
	
	static String username=null;
	static Connection con=null;
	
	public static void main(String args[]){
		connect();
		login("lhc","123");
		//createActivity("qwe", 0);
		//createActivity("asd", 1);
		//reportToday(5.9);
		getExerciseAmount("lhc\\'");
		
		getAvgRating(1);
		
		
		System.out.print(getPlans().size());
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
	}
	
	public static int login(String username, String password){//0 success -1fail
		try{
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM account WHERE username='"+username+"' AND password='"+password+"'");
			if(!result.next()){  
				System.out.println("Failed login");
				DBconnector.username=null;
				return -1;
			}else{
				System.out.println("Success login");
				DBconnector.username=username;
				return 0;
			}
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int createUser(String username, String password){//0 success -1fail
		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO account (username,password) VALUES ('"+username+"','"+password+"')");
			System.out.println("Successfully creating account");
			return 0;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int createActivity(String name, int unitsecond){// +num=id -1fail
		try{
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM activity WHERE name='"+name+"'");
			if (result.next()==true) {
				System.out.println("Activity exists");
				return -1;
			}
			stmt.executeUpdate("INSERT INTO activity (name,unitsecond) VALUES ('"+name+"','"+unitsecond+"')");
			result = stmt.executeQuery("SELECT * FROM activity WHERE name='"+name+"'");
			result.next();
			int ret=result.getInt("activityid");
			System.out.println("Successfully creating activity at ID"+ret);
			return ret;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int addRating(int planid, double rating, String comments){//0seccess  -1fail
		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO rating (username,planid,rating,comments) VALUES ('"+username+"','"+planid+"','"+rating+"','"+comments+"')");
			System.out.println("Successfully adding rating");
			return 0;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
	public static double getAvgRating(int planid){
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
		try{
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM personalexerciseamount WHERE username='"+username+"'");
			ArrayList<app.model.dayAmount> ret=new ArrayList<app.model.dayAmount>();
			while(result.next()){
				app.model.dayAmount temp=new app.model.dayAmount();
				temp.date=result.getDate("date");
				temp.amount=result.getDouble("calories");
				ret.add(temp);
			}
			System.out.println("Successfully select");
			return ret;
		}catch(SQLException e){
			return null;
		}
	}
	
	public static ArrayList<WeekPlan> getPlans(){
		try{
			
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM sharedplan AS sp LEFT JOIN rating AS r ON sp.`planid` = r.`planid` AND sp.`username` = r.`username`");
			int planid = -1;
			int weekday = -1;
			String planname = "";
			String planType = "";
			int rating = -1;
			String comment = "";
			
			
			ArrayList<WeekPlan> wps = new ArrayList();
			
			while(result.next()){
				
				planid = result.getInt("planid");
				planname = result.getString("planname");
				planType = result.getString("plantype");
				rating = result.getInt("rating");
				comment = result.getString("comments");
				
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
//					System.out.println(map.toString());
					MapProperty<String, ActivityPlan> mapProperty = new SimpleMapProperty<>(map);
					DayPlan dayPlan = new DayPlan(mapProperty);
					
					System.out.println(weekday+" "+dayPlan.toString());
					dayplanlist.add(dayPlan);
					
				}
				System.out.println("next dayplanlist:");
				
				WeekPlan wp = new WeekPlan(dayplanlist,planname);
			
				wps.add(wp);
							
			}
			return wps;
			
			
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
	}
}

