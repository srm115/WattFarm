package WattFarmSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Session{

	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:WATTFARM_DB";//;"create=true;";


	//only one of these will be logged in at a time, the other will be null
	private static int rowerID = -1;
	private static int coachID = -1;

	public static boolean logInRower(String enteredUN, String enteredPW, char type) throws ClassNotFoundException , SQLException{
		int userID = checkUser(enteredUN, enteredPW, type);

		if(userID != -1) {
			rowerID = userID;
			coachID = -1;
			return true;
		}
		return false;
	}

	public static boolean logInCoach(String enteredUN, String enteredPW, char type) throws ClassNotFoundException , SQLException{
		int userID = checkUser(enteredUN, enteredPW, type);

		if(userID != -1) {
			coachID = userID;
			rowerID = -1;
			return true;
		}
		return false;
	}

	//returns -1 if not a user, userID if a user
	public static int checkUser(String enteredUN, String enteredPW, char type) throws ClassNotFoundException , SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();


		if(type == 'C') {

			String sql = "SELECT coachID, username, password FROM COACH_LOGIN" +
					" WHERE username = '" + enteredUN + "' AND password = '" + enteredPW + "'";

			ResultSet rs = stmt.executeQuery(sql);

			if(!rs.next()) {
				if (connection != null) connection.close();
				return -1;
			}
			else {
				while(rs.next()){
					int id = rs.getInt("coachID"); 
					if (connection != null) connection.close();
					return id;
				}
			}
		}

		if(type == 'R') {

			String sql = "SELECT rowerID, username, password FROM ROWER_LOGIN" +
					" WHERE username = '" + enteredUN + "' AND password = '" + enteredPW + "'";

			ResultSet rs = stmt.executeQuery(sql);

			if(rs.equals(null)) {
				return -1;
			}
			else {
				while(rs.next()){
					int id = rs.getInt("rowerID"); 
					if (connection != null) connection.close();
					return id;
				}
			}
		}
		
		return -1;
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

	public static boolean addNewWorkout(Workout newWorkout) throws ClassNotFoundException , SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();


		String sql = "INSERT INTO WORKOUTS (rowerID, distance, split, time, avgWatts, spm, dateCompleted, tag)" +
				"VALUES (" + rowerID + ", " +
				newWorkout.getDistance() +", " +
				newWorkout.getSplit() + ", " +
				newWorkout.getTime() +", " +
				newWorkout.getAvgWatt() +", " +
				newWorkout.getSpm() +", '" +
				newWorkout.getDatePerformed() +"', '" +
				newWorkout.getTag() + "')";
		stmt.executeUpdate(sql);

		if (connection != null) connection.close();
		return true;
	}

	public static void main(String[] args) throws ClassNotFoundException , SQLException {		
		/*
		JFrame parent = new JFrame("Workouts Menu");
		parent.setSize(300,200);
		parent.setLocation(500,280);


		JTable table = getRowerWorks

		parent.getContentPane().add(menuPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);
		 */

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

		System.out.println("");

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
		/*
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

		 */
	}

	public static JTable getRowerWorkouts() throws ClassNotFoundException , SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();



		ResultSet resultSet = connection.createStatement().executeQuery("select * from WORKOUTS where ROWERID = " + rowerID);
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();

		DefaultTableModel model = new DefaultTableModel(new String[]{"WORKOUTID", "DISTANCE", "SPLIT", "TIME", "AVGWATTS", "SPM", "DATECOMPLETED"}, 0);

		while(resultSet.next())
		{
			int w = Integer.parseInt(resultSet.getString("WORKOUTID"));
			int d = Integer.parseInt(resultSet.getString("DISTANCE"));
			int s = Integer.parseInt(resultSet.getString("SPLIT"));
			int t = Integer.parseInt(resultSet.getString("TIME"));
			int a = Integer.parseInt(resultSet.getString("AVGWATTS"));
			int spm = Integer.parseInt(resultSet.getString("SPM"));
			String date = resultSet.getString("DATECOMPLETED");
			model.addRow(new Object[]{w, d, s, t, a, spm, date});
		}

		JTable newTable = new JTable();
		newTable.setModel(model);
		newTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		return newTable;
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
