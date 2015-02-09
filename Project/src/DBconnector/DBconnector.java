package DBconnector;

import java.sql.*;

public abstract class DBconnector {
	
	
	final static String DBaddress="jdbc:mysql://98.223.107.97/";
	final static String DBuser="408";
	final static String DBpass="408";
	final static String DBdatabase="fitness";
	
	
	static String username=null;
	static Connection con=null;
	
	public static void main(String args[]){
		connect();
		login("aaa","bbb");
		login("lhc","123");
		login("lhc","1234");
	}
	
	public static void connect(){
		if (con!=null) return;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DBaddress+DBdatabase,DBuser,DBpass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con=null;
	}
	
	public static int  login(String username, String password){//0 success -1fail
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
}

