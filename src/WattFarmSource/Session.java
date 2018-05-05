package WattFarmSource;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.Box;
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
				connection.commit();
				connection.close();
				return false;
			}
			else {
				connection.commit();
				connection.close();
				return true;
			}
		}

		if(type == 'R') {

			String sql = "SELECT rowerID, username, password FROM ROWER_LOGIN" +
					" WHERE username = '" + enteredUN + "' AND password = '" + enteredPW + "'";

			ResultSet rs = stmt.executeQuery(sql);

			if(!rs.next()) {
				connection.commit();
				connection.close();
				return false;
			}
			else {
				connection.commit();
				connection.close();
				return true;
			}
		}

		connection.commit();
		connection.close();
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

		connection.commit();
		connection.close();
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

		connection.commit();
		connection.close();
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

			connection.commit();
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

			connection.commit();
			connection.close();
			return true;
		}
	}



	//-------------------- GENERAL METHODS -----------------------------

	public static String[] getTeams() throws ClassNotFoundException , SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		ResultSet resultSet = connection.createStatement().executeQuery("select * from TEAMS WHERE NOT name = 'blank'");
		ArrayList<String> teams = new ArrayList<String>();


		while(resultSet.next()) {
			teams.add(resultSet.getString("name"));
		}

		String[] teamsStr = new String[teams.size()];

		for(int i = 0; i < teams.size(); i++) {
			teamsStr[i] = teams.get(i);
		}

		connection.commit();
		connection.close();
		return teamsStr;
	}

	public static String getRowerName(int rowerID) throws ClassNotFoundException , SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		//Get coach's team
		String sql = "SELECT name FROM rowers WHERE rowerID = " + rowerID;
		ResultSet resultSet = connection.createStatement().executeQuery(sql);


		String returnVal = "";
		if(resultSet.next()) {
			returnVal = resultSet.getString("name");
			connection.commit();
			connection.close();
			return returnVal;
		} else{
			connection.commit();
			connection.close();
			return "";
		}
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

		connection.commit();
		connection.close();
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
		connection.commit();
		connection.close();
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

		connection.commit();
		connection.close();
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

		//there is a team
		if(resultSet.next() == true) {
			String rowerTeamID = resultSet.getString("teamID");
			//change table values on this rower's profile
			sql = "UPDATE rowers " +
					"SET name = '" + name + "', " +
					"age = " + age + ", " +
					"height = " + height + ", " +
					"weight = " + weight + ", " +
					"teamID = " + rowerTeamID + " " +
					"WHERE rowerID = " + rowerID;
			stmt.executeUpdate(sql);
		}
		//there isn't a team
		else {
			//change table values on this rower's profile w/out team
			sql = "UPDATE rowers " +
					"SET name = '" + name + "', " +
					"age = " + age + ", " +
					"height = " + height + ", " +
					"weight = " + weight + " " +
					"WHERE rowerID = " + rowerID;
			stmt.executeUpdate(sql);
		}


		connection.commit();
		connection.close();
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


		connection.commit();
		connection.close();
		return count;
	}

	public static ArrayList<Integer> getRowerYVals(String param, int n) throws ClassNotFoundException , SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		ResultSet resultSet = connection.createStatement().executeQuery(
				"SELECT " + param + 
				" FROM workouts WHERE rowerID = " + rowerID +
				"ORDER BY dateCompleted");

		ArrayList<Integer> vals = new ArrayList<Integer>();
		int count = 0;
		while(resultSet.next() && count < n)
		{
			count++;
			vals.add(Integer.parseInt(resultSet.getString(param)));
		}

		connection.commit();
		connection.close();
		return vals;
	}

	public static String[][] getWorkoutOptions() throws ClassNotFoundException , SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		ResultSet resultSet = connection.createStatement().executeQuery("SELECT workoutID, distance, dateCompleted "
				+ "from workouts WHERE rowerID = " + rowerID);
		ArrayList<String> options = new ArrayList<String>();
		ArrayList<String> ids = new ArrayList<String>();


		while(resultSet.next()) {
			options.add("Workout " + resultSet.getString("workoutID") + ":\n\n"
					+ resultSet.getString("distance") + "m\n\n"
					+ resultSet.getString("dateCompleted"));
			ids.add(resultSet.getString("workoutID"));
		}

		String[][] optionsArr = new String[options.size()][2];

		for(int i = 0; i < options.size(); i++) {
			optionsArr[i][0] = options.get(i);
			optionsArr[i][1] = ids.get(i);
		}


		connection.commit();
		connection.close();
		return optionsArr;
	}

	public static boolean editWorkout(Workout newWorkoutData, int id) throws ClassNotFoundException , SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();


		String sql = "UPDATE workouts SET " +
				"distance = " + newWorkoutData.getDistance() +", " +
				"split = " + newWorkoutData.getSplit() + ", " +
				"time = " + newWorkoutData.getTime() +", " +
				"avgWatts = " + newWorkoutData.getAvgWatt() +", " +
				"spm = " + newWorkoutData.getSpm() +", " +
				"dateCompleted = '" + newWorkoutData.getDatePerformed() +"', " +
				"tag = '" + newWorkoutData.getTag() + "'" +
				"WHERE workoutID = " + id;
		stmt.executeUpdate(sql);



		connection.commit();
		connection.close();
		return true;
	}


	//-------------------- COACH METHODS -------------------------------

	public static JTable getCoachWorkouts() throws ClassNotFoundException , SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		//Get coach's team ID
		String sql = "SELECT teamID FROM teams WHERE coachID = " + coachID;
		ResultSet resultSet = connection.createStatement().executeQuery(sql);

		int coachTeamID = -1;
		if(resultSet.next()) {
			coachTeamID = resultSet.getInt("teamID");
		}

		sql = "SELECT * FROM workouts";
		resultSet = connection.createStatement().executeQuery(sql);
		DefaultTableModel model = new DefaultTableModel(new String[]{"ROWER" , "WORKOUTID", "DISTANCE", "SPLIT", "TIME", "AVGWATTS", "SPM", "DATECOMPLETED"}, 0);

		//Add values to table if the rower is on the coach's team
		while(resultSet.next())
		{
			if(rowerOnTeam( Integer.parseInt(resultSet.getString("rowerID")) , coachTeamID)){
				String n = getRowerName(Integer.parseInt(resultSet.getString("rowerID")));
				int w = Integer.parseInt(resultSet.getString("WORKOUTID"));
				int d = Integer.parseInt(resultSet.getString("DISTANCE"));
				int s = Integer.parseInt(resultSet.getString("SPLIT"));
				int t = Integer.parseInt(resultSet.getString("TIME"));
				int a = Integer.parseInt(resultSet.getString("AVGWATTS"));
				int spm = Integer.parseInt(resultSet.getString("SPM"));
				String date = resultSet.getString("DATECOMPLETED");
				model.addRow(new Object[]{n, w, d, s, t, a, spm, date});
			}
		}

		JTable newTable = new JTable();
		newTable.setModel(model);

		connection.commit();
		connection.close();
		return newTable;
	}



	public static JTable getRoster() throws ClassNotFoundException , SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		//Get coach's team
		String sql = "SELECT teamID FROM teams WHERE coachID = " + coachID;
		ResultSet resultSet = connection.createStatement().executeQuery(sql);

		int teamID = -1;
		if(resultSet.next()) {
			teamID = resultSet.getInt("teamID");
		}


		//Get rowers on coach's team
		sql = "SELECT * FROM rowers WHERE teamID = " + teamID;
		resultSet = connection.createStatement().executeQuery(sql);

		DefaultTableModel model = new DefaultTableModel(new String[]{"ROWERID" , "NAME"}, 0);

		while(resultSet.next())
		{
			int id = Integer.parseInt(resultSet.getString("rowerID"));
			String name = resultSet.getString("name");
			model.addRow(new Object[]{id, name});
		}

		JTable newTable = new JTable();
		newTable.setModel(model);
		newTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		connection.commit();
		connection.close();
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
			connection.commit();
			connection.close();
			return false;
		}

		connection.commit();
		connection.close();
		return true;
	}

	//Assumes teamName is unique
	public static boolean createTeam(String teamName) throws ClassNotFoundException , SQLException  {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();

		if(teamName.equals("")) {
			connection.commit();
			connection.close();
			return false;
		}

		//Create new team
		String sql = "INSERT INTO teams (name) " +
				"VALUES ('" + teamName +"')";
		stmt.executeUpdate(sql);


		connection.commit();
		connection.close();
		return true;
	}

	public static String getTeamName() throws ClassNotFoundException , SQLException  {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		String sql = "SELECT * FROM teams WHERE coachID = " + coachID;
		ResultSet resultSet = connection.createStatement().executeQuery(sql);

		if(!resultSet.next()) {
			connection.commit();
			connection.close();
			return "";
		}

		String returnVal = resultSet.getString("name");
		connection.commit();
		connection.close();
		return returnVal;
	}

	//Returns the teamID of the team of given name. Returns -1 if team doesn't exist
	public static int getTeamIDFromName(String teamName) throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		String sql = "SELECT teamID FROM teams WHERE name = '" + teamName + "'";
		ResultSet resultSet = connection.createStatement().executeQuery(sql);

		if(!resultSet.next()) {

			connection.commit();
			connection.close();
			return -1;
		}

		int returnVal = Integer.parseInt(resultSet.getString("teamID"));

		connection.commit();
		connection.close();
		return returnVal;
	}

	public static JPanel getCoachProfile() throws ClassNotFoundException , SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		//get info from rowers table
		String sql = "SELECT * FROM coaches WHERE coachID = " + coachID; 
		ResultSet resultSet = connection.createStatement().executeQuery(sql);
		resultSet.next();

		int id = resultSet.getInt("coachID");
		String name = resultSet.getString("name");

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

		JLabel teamField = new JLabel("Team: " + team);
		JPanel teamPanel = new JPanel();
		teamPanel.add(teamField);

		Box infoBox = Box.createVerticalBox();
		infoBox.add(namePanel);
		infoBox.add(idPanel);
		infoBox.add(teamPanel);

		JPanel infoPanel = new JPanel();
		infoPanel.add(infoBox);

		connection.commit();
		connection.close();
		return infoPanel;
	}


	//Gets logged in coach's current teamID, returns -1 if coach doesn't have a team
	public static int getCoachTeamID() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		String sql = "SELECT teamID FROM teams WHERE coachID = " + coachID;
		ResultSet resultSet = connection.createStatement().executeQuery(sql);

		if(!resultSet.next()) {

			connection.commit();
			connection.close();
			return -1;
		}

		int returnVal = Integer.parseInt(resultSet.getString("teamID"));
		connection.commit();
		connection.close();
		return returnVal;
	}


	//changes coach's teamID to given
	public static boolean changeCoachTeamID(int teamID) throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		if(teamID == -1) {

			connection.commit();
			connection.close();
			return false;
		}

		//Change the team's coachID to coachID
		String sql = "UPDATE coaches " +
				"SET teamID = " + teamID + " " +
				"WHERE coachID = " + coachID;
		PreparedStatement updateTeam = connection.prepareStatement(sql);
		updateTeam.executeUpdate();

		connection.commit();
		connection.close();
		return true;
	}

	//Changes the the team with teamID's coachID to the given param
	public static boolean changeTeamCoachID (String teamName, int myCoachID) throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();

		//change table values on this coach's profile
		String sql = "UPDATE teams " +
				"SET coachID = " + myCoachID  + " " + 
				"WHERE name = '" + teamName + "'";
		stmt.executeUpdate(sql);

		connection.commit();
		connection.close();
		return true;
	}

	//change coach's name
	public static boolean changeCoachName (String name) throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();

		//change table values on this coach's profile
		String sql = "UPDATE coaches " +
				"SET name = '" + name + "' " + 
				"WHERE coachID = " + coachID;
		stmt.executeUpdate(sql);


		connection.commit();
		connection.close();
		return true;
	}

	public static ArrayList<Integer> getCoachYVals (String param, String tag) throws ClassNotFoundException , SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		//Get coach's team ID
		String sql = "SELECT teamID FROM teams WHERE coachID = " + coachID;
		ResultSet resultSet = connection.createStatement().executeQuery(sql);

		int coachTeamID = -1;
		if(resultSet.next()) {
			coachTeamID = resultSet.getInt("teamID");
		}

		if(tag.equals("All workout types")) {
			//Get all workouts
			sql = "SELECT * FROM workouts ORDER BY dateCompleted";
		} else {
			//Get workout of specified tag
			sql = "SELECT * FROM workouts WHERE tag = '" + tag + "' "
					+ "ORDER BY dateCompleted";
		}

		resultSet = connection.createStatement().executeQuery(sql);

		//Add values to y values if the rower is on the coach's team
		ArrayList<Integer> vals = new ArrayList<Integer>();
		while(resultSet.next())
		{
			if(rowerOnTeam( Integer.parseInt(resultSet.getString("rowerID")) , coachTeamID)){
				vals.add(Integer.parseInt(resultSet.getString(param)));
			}
		}

		connection.commit();
		connection.close();
		return vals;
	}

	public static boolean rowerOnTeam (int rowerID, int teamID) throws ClassNotFoundException , SQLException  {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();


		String sql = "SELECT * FROM rowers" +
				" WHERE rowerID = " + rowerID + " AND teamID = " + teamID;

		ResultSet rs = stmt.executeQuery(sql);

		//if there is a result, then the rower exists
		if(!rs.next()) {
			connection.commit();
			connection.close();
			return false;
		}
		else {
			connection.commit();
			connection.close();
			return true;
		}
	}

	public static int getNumCoachWorkouts() throws ClassNotFoundException , SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);

		//Get coach's team ID
		String sql = "SELECT teamID FROM teams WHERE coachID = " + coachID;
		ResultSet resultSet = connection.createStatement().executeQuery(sql);

		int coachTeamID = -1;
		if(resultSet.next()) {
			coachTeamID = resultSet.getInt("teamID");
		}

		sql = "SELECT * FROM workouts ORDER BY dateCompleted";
		resultSet = connection.createStatement().executeQuery(sql);

		//increment count if the rower is on the coach's team
		int count = 0;
		while(resultSet.next())
		{
			if(rowerOnTeam( Integer.parseInt(resultSet.getString("rowerID")) , coachTeamID)){
				count++;
			}
		}

		connection.commit();
		connection.close();
		return count;
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


	/* For Testing only
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




		System.out.println("");

		resultSet = connection.createStatement().executeQuery("select * from TEAMS");
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

	}*/


}
