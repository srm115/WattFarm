package WattFarmSource;

import java.sql.*;

import javax.swing.JOptionPane;

public class Session{

	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:WATTFARM_DB";//;"create=true;";


	//only one of these will be logged in at a time, the other will be null
	private static int rowerID;
	private static int coachID;



	public static boolean checkUser(String enteredUN, String enteredPW, char type) throws ClassNotFoundException , SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();


		if(type == 'C') {

			String sql = "SELECT coachID, username, password FROM COACH_LOGIN" +
					" WHERE username = '" + enteredUN + "' AND password = '" + enteredPW + "'";

			ResultSet rs = stmt.executeQuery(sql);

			if(!rs.next()) {
				if (connection != null) connection.close();
				return false;
			}
			else {
				while(rs.next()){
					//Retrieve by column name
					coachID = rs.getInt("coachID");
					String DBusername  = rs.getString("username");
					String DBpassword = rs.getString("password");

					JOptionPane.showMessageDialog(null,"Logged in.");
					if (connection != null) connection.close();
					return true;
				}
			}
		}
		
		if(type == 'R') {

			String sql = "SELECT rowerID, username, password FROM ROWER_LOGIN" +
					" WHERE username = '" + enteredUN + "' AND password = '" + enteredPW + "'";

			ResultSet rs = stmt.executeQuery(sql);

			if(!rs.next()) {
				return false;
			}
			else {
				while(rs.next()){
					//Retrieve by column name
					rowerID = rs.getInt("rowerID");
					String DBusername  = rs.getString("username");
					String DBpassword = rs.getString("password");

					JOptionPane.showMessageDialog(null,"Logged in.");
					if (connection != null) connection.close();
					return true;
				}
			}
		}

		return false;
	}

	public static boolean addUser(String username, String password, char type) throws ClassNotFoundException , SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();


		if(type == 'C') {

			String sql = "INSERT INTO COACH_LOGIN (username, password)" +
					"VALUES ('" + username +"', '" + password + "')";
			stmt.executeUpdate(sql);
		}
		else {
			String sql = "INSERT INTO ROWER_LOGIN (username, password)" +
					"VALUES ('" + username +"', '" + password + "')";
			stmt.executeUpdate(sql);
		}

		if (connection != null) connection.close();
		return true;
	}

	public static void main(String[] args) throws ClassNotFoundException , SQLException {
		//createDB();

		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();


		ResultSet resultSet = connection.createStatement().executeQuery("select * from COACH_LOGIN");
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();
		for(int i = 1; i <= columnCount; i++) {
			System.out.format("%20s" , resultSetMetaData.getColumnName(i) + " | ");
		}
		while (resultSet.next()) {
			System.out.println("");
			for(int i = 1; i <= columnCount; i++) {
				System.out.format("%20s" , resultSet.getString(i) + " | ");
			}
		}


		resultSet = connection.createStatement().executeQuery("select * from ROWER_LOGIN");
		resultSetMetaData = resultSet.getMetaData();
		columnCount = resultSetMetaData.getColumnCount();
		for(int i = 1; i <= columnCount; i++) {
			System.out.format("%20s" , resultSetMetaData.getColumnName(i) + " | ");
		}
		while (resultSet.next()) {
			System.out.println("");
			for(int i = 1; i <= columnCount; i++) {
				System.out.format("%20s" , resultSet.getString(i) + " | ");
			}
		}

		resultSet = connection.createStatement().executeQuery("select * from ROWERS");
		resultSetMetaData = resultSet.getMetaData();
		columnCount = resultSetMetaData.getColumnCount();
		for(int i = 1; i <= columnCount; i++) {
			System.out.format("%20s" , resultSetMetaData.getColumnName(i) + " | ");
		}
		while (resultSet.next()) {
			System.out.println("");
			for(int i = 1; i <= columnCount; i++) {
				System.out.format("%20s" , resultSet.getString(i) + " | ");
			}
		}

		resultSet = connection.createStatement().executeQuery("select * from COACHES");
		resultSetMetaData = resultSet.getMetaData();
		columnCount = resultSetMetaData.getColumnCount();
		for(int i = 1; i <= columnCount; i++) {
			System.out.format("%20s" , resultSetMetaData.getColumnName(i) + " | ");
		}
		while (resultSet.next()) {
			System.out.println("");
			for(int i = 1; i <= columnCount; i++) {
				System.out.format("%20s" , resultSet.getString(i) + " | ");
			}
		}

		resultSet = connection.createStatement().executeQuery("select * from WORKOUTS");
		resultSetMetaData = resultSet.getMetaData();
		columnCount = resultSetMetaData.getColumnCount();
		for(int i = 1; i <= columnCount; i++) {
			System.out.format("%20s" , resultSetMetaData.getColumnName(i) + " | ");
		}
		while (resultSet.next()) {
			System.out.println("");
			for(int i = 1; i <= columnCount; i++) {
				System.out.format("%20s" , resultSet.getString(i) + " | ");
			}
		}
		if (connection != null) connection.close();
	}

	public static void createDB() throws ClassNotFoundException , SQLException{
		String URL = "jdbc:derby:WATTFARM_DB;create=true";
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(URL);
		Statement stmt = connection.createStatement();

		stmt.executeUpdate("CREATE TABLE COACH_LOGIN " +
				"(coachID int GENERATED ALWAYS AS IDENTITY not null primary key, " +
				" username VARCHAR(25), " + 
				" password VARCHAR(25)) ");


		stmt.executeUpdate("CREATE TABLE ROWER_LOGIN " +
				"(rowerID int GENERATED ALWAYS AS IDENTITY not null primary key, " +
				" username VARCHAR(25), " + 
				" password VARCHAR(25)) "); 

		stmt.executeUpdate("CREATE TABLE ROWERS " +
				"(rowerID int GENERATED ALWAYS AS IDENTITY not null primary key, " +
				" name VARCHAR(25), " + 
				" age INTEGER, " + 
				" height INTEGER, " + 
				" weight INTEGER )");

		stmt.executeUpdate("CREATE TABLE COACHES " +
				"(coachID int GENERATED ALWAYS AS IDENTITY not null primary key, " +
				" name VARCHAR(25), " + 
				" teamID INTEGER not NULL) ");

		stmt.executeUpdate("CREATE TABLE WORKOUTS " +
				"(workoutID int GENERATED ALWAYS AS IDENTITY not null primary key, " +
				" rowerID INTEGER not NULL, " + 
				" distance INTEGER, " +
				" split INTEGER, " + 
				" time INTEGER, " + 
				" avgWatts INTEGER, " + 
				" spm INTEGER, " + 
				" dateCompleted DATE, " + 
				" tag VARCHAR(225)) ");

		stmt.executeUpdate("CREATE TABLE TEAMS " +
				"(coachID int GENERATED ALWAYS AS IDENTITY not null primary key, " +
				" name VARCHAR(25), " + 
				" teamID INTEGER not NULL)");
	}
}
