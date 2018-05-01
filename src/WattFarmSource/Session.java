package WattFarmSource;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Session{

	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:WATTFARM_DB";//;"create=true;";


	//-------------------- LOGIN METHODS -------------------------------

	//only one of these will be logged in at a time, the other will be -1
	private static int rowerID = -1;
	public static int getRowerID() {
		return rowerID;
	}


	public static void setRowerID(int rowerID) {
		Session.rowerID = rowerID;
	}


	public static int getCoachID() {
		return coachID;
	}


	public static void setCoachID(int coachID) {
		Session.coachID = coachID;
	}



	private static int coachID = -1;

	//is the entered info a user on the sys (rower OR coach)
	public static boolean isUser(String enteredUN, String enteredPW, char type) throws ClassNotFoundException , SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();

		if(type == 'C') {

			String sql = "SELECT coachID, username, password FROM COACH_LOGIN" +
					" WHERE username = '" + enteredUN + "' AND password = '" + enteredPW + "'";

			ResultSet rs = stmt.executeQuery(sql);

			if(!rs.next()) {
				connection.close();
				return false;
			}
			else {
				connection.close();
				return true;
			}
		}

		if(type == 'R') {

			String sql = "SELECT rowerID, username, password FROM ROWER_LOGIN" +
					" WHERE username = '" + enteredUN + "' AND password = '" + enteredPW + "'";

			ResultSet rs = stmt.executeQuery(sql);

			if(!rs.next()) {
				connection.close();
				return false;
			}
			else {
				connection.close();
				return true;
			}
		}


		return false;
	}


	//log in a coach user, log out rower user
	//assumes user exists
	public static boolean logInCoach(String enteredUN, String enteredPW) throws ClassNotFoundException , SQLException{

		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();

		String sql = "SELECT coachID FROM COACH_LOGIN" +
				" WHERE username = '" + enteredUN + "' AND password = '" + enteredPW + "'";

		ResultSet rs = stmt.executeQuery(sql);

		rs.next();
		coachID = rs.getInt("coachID");
		rowerID = -1;
		return true;
	}


	//log in a rower user, log out coach user
	//assumes user exists
	public static boolean logInRower(String enteredUN, String enteredPW) throws ClassNotFoundException , SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();

		String sql = "SELECT rowerID FROM ROWER_LOGIN" +
				" WHERE username = '" + enteredUN + "' AND password = '" + enteredPW + "'";

		ResultSet rs = stmt.executeQuery(sql);

		rs.next();
		rowerID = rs.getInt("rowerID");
		coachID = -1;
		return true;
	}


	//add a coach or rower user to the database
	//assumes user doesn't already exist
	public static boolean addUser(String username, String password, char type) throws ClassNotFoundException , SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();


		if(type == 'C') {
			//insertLogin information into login table
			String sql = "INSERT INTO COACH_LOGIN (username, password)" +
					"VALUES ('" + username +"', '" + password + "')";
			stmt.executeUpdate(sql);

			//get newly created ID
			sql = "SELECT coachID FROM COACH_LOGIN" +
					" WHERE username = '" + username + "' AND password = '" + password + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int id = rs.getInt("coachID");

			//create a new profile with new id
			sql = "INSERT INTO coaches (coachID) VALUES (" + id + ")";
			stmt.executeUpdate(sql);

			connection.close();
			return true;
		}
		else {
			String sql = "INSERT INTO ROWER_LOGIN (username, password)" +
					"VALUES ('" + username +"', '" + password + "')";
			stmt.executeUpdate(sql);

			//get newly created ID
			sql = "SELECT rowerID FROM ROWER_LOGIN" +
					" WHERE username = '" + username + "' AND password = '" + password + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int id = rs.getInt("rowerID");

			//create a new profile with new id
			sql = "INSERT INTO rowers (rowerID) VALUES (" + id + ")";
			stmt.executeUpdate(sql);

			connection.close();
			return true;
		}
	}



	//-------------------- GENERAL METHODS -----------------------------

	public static String[] getTeams() throws ClassNotFoundException , SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		ResultSet resultSet = connection.createStatement().executeQuery("select * from TEAMS");
		ArrayList<String> teams = new ArrayList<String>();


		while(resultSet.next()) {
			teams.add(resultSet.getString("name"));
		}

		String[] teamsStr = new String[teams.size()];

		for(int i = 0; i < teams.size(); i++) {
			teamsStr[i] = teams.get(i);
		}

		return teamsStr;
	}




	//-------------------- ROWER METHODS -------------------------------

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

	public static JTable getRowerWorkouts() throws ClassNotFoundException , SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		ResultSet resultSet = connection.createStatement().executeQuery("select * from WORKOUTS where ROWERID = " + rowerID);

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
		return newTable;
	}

	public static JPanel getRowerProfile() throws ClassNotFoundException , SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		//get info from rowers table
		String sql = "SELECT * FROM rowers WHERE rowerID = " + rowerID; 
		ResultSet resultSet = connection.createStatement().executeQuery(sql);
		resultSet.next();

		int id = resultSet.getInt("rowerID");
		String name = resultSet.getString("name");
		int age = resultSet.getInt("age");
		int height = resultSet.getInt("height");
		int weight = resultSet.getInt("weight");

		//get team name from teams table
		int teamID = resultSet.getInt("teamID");
		sql = "SELECT * FROM teams WHERE teamID = " + teamID; 
		resultSet = connection.createStatement().executeQuery(sql);
		String team;
		if(resultSet.next()) {
			team = resultSet.getString("name");
		} else {
			team = null;
		}

		JLabel nameField = new JLabel("Name: " + name);
		JPanel namePanel = new JPanel();
		namePanel.add(nameField);

		JLabel idField = new JLabel("ID: " + Integer.toString(id));
		JPanel idPanel = new JPanel();
		idPanel.add(idField);

		JLabel ageField = new JLabel("Age: " + Integer.toString(age));
		JPanel agePanel = new JPanel();
		agePanel.add(ageField);

		JLabel heightField = new JLabel("Height: " + Integer.toString(height));
		JPanel heightPanel = new JPanel();
		heightPanel.add(heightField);

		JLabel weightField = new JLabel("Weight: " + Integer.toString(weight));
		JPanel weightPanel = new JPanel();
		weightPanel.add(weightField);

		JLabel teamField = new JLabel("Team: " + team);
		JPanel teamPanel = new JPanel();
		teamPanel.add(teamField);

		Box infoBox = Box.createVerticalBox();
		infoBox.add(namePanel);
		infoBox.add(idPanel);
		infoBox.add(agePanel);
		infoBox.add(heightPanel);
		infoBox.add(weightPanel);
		infoBox.add(teamPanel);

		JPanel infoPanel = new JPanel();
		infoPanel.add(infoBox);
		return infoPanel;
	}

	public static boolean editRowerProfile(String name, int age, int height, int weight, String team)
			throws ClassNotFoundException, SQLException {

		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();

		//Get rower's team
		String sql = "SELECT teamID FROM teams WHERE name = '" + team + "'";
		ResultSet resultSet = connection.createStatement().executeQuery(sql);
		resultSet.next();
		String rowerTeamID = resultSet.getString("teamID");

		//change table values on this rower's profile
		sql = "UPDATE rowers " +
				"SET name = '" + name + "', " +
				"age = " + age + ", " +
				"height = " + height + ", " +
				"weight = " + weight + ", " +
				"teamID = " + rowerTeamID + "" +
				"WHERE rowerID = " + rowerID;
		stmt.executeUpdate(sql);

		if (connection != null) connection.close();
		return true;
	}

	public static int getNumRowerWorkouts() throws ClassNotFoundException , SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		ResultSet resultSet = connection.createStatement().executeQuery("select * from WORKOUTS where ROWERID = " + rowerID);

		int count = 0;
		while(resultSet.next())
		{
			count++;
		}

		return count;
	}

	public static ArrayList<Integer> getYValues(String param, int n) throws ClassNotFoundException , SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		ResultSet resultSet = connection.createStatement().executeQuery(
				"SELECT " + param + 
				" FROM workouts WHERE rowerID = " + rowerID +
				"ORDER BY dateCompleted");

		ArrayList<Integer> vals = new ArrayList<Integer>();
		int count = 0;
		while(resultSet.next() || count < n)
		{
			count++;
			vals.add(Integer.parseInt(resultSet.getString(param)));
		}
		
		return vals;
	}
	
	//-------------------- COACH METHODS -------------------------------

	public static JTable getCoachWorkouts() throws ClassNotFoundException , SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();

		/*

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
		 */
		JTable newTable = new JTable();
		//newTable.setModel(model);
		newTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		return newTable;


	}

	public static JTable getRoster() throws ClassNotFoundException , SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		//Get coach's team
		String sql = "SELECT name FROM teams WHERE coachID = " + coachID;
		ResultSet resultSet = connection.createStatement().executeQuery(sql);
		resultSet.next();
		String coachTeam = resultSet.getString("name");


		//Get rowers on coach's team
		sql = "SELECT rowerID name FROM rowers WHERE name = '" + coachTeam +"'";
		resultSet = connection.createStatement().executeQuery(sql);

		DefaultTableModel model = new DefaultTableModel(new String[]{"ROWERID, NAME"}, 0);

		while(resultSet.next())
		{
			int id = Integer.parseInt(resultSet.getString("rowerID"));
			String name = resultSet.getString("name");
			model.addRow(new Object[]{id, name});
		}

		JTable newTable = new JTable();
		newTable.setModel(model);
		newTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		return newTable;
	}

	public static boolean teamExists(String teamName) throws ClassNotFoundException , SQLException  {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();

		String sql = "SELECT * FROM TEAMS" +
				" WHERE name = '" + teamName + "'";

		ResultSet rs = stmt.executeQuery(sql);

		if(!rs.next()) {
			connection.close();
			return false;
		}

		return true;
	}
	
	//Assumes teamName is unique
	public static boolean createTeam(String teamName) throws ClassNotFoundException , SQLException  {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();
		
		if(teamName.equals("")) {
			connection.close();
			return false;
		}
		
		String sql = "INSERT INTO TEAMS (name, coachID)" +
				"VALUES ('" + teamName +"', " + coachID + ")";
		stmt.executeUpdate(sql);

		connection.close();
		return true;
	}








	//-------------------- DATABASE METHODS -------------------------------

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
				"(rowerID INTEGER not NULL, " +
				" name VARCHAR(25), " + 
				" age INTEGER, " + 
				" height INTEGER, " + 
				" weight INTEGER, " +
				" teamID INTEGER) ");

		stmt.executeUpdate("CREATE TABLE COACHES " +
				"(coachID INTEGER not NULL, " +
				" name VARCHAR(25), " + 
				" teamID INTEGER) ");

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
				"(teamID int GENERATED ALWAYS AS IDENTITY not null primary key, " +
				" name VARCHAR(25), " + 
				" coachID INTEGER)");
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

		System.out.println("");

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

		System.out.println("");

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

		/*
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
		/*
		 */
	}


}
