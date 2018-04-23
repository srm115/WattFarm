package WattFarmSource;

import java.sql.*;

public class Session{
	//only one of these will be logged in at a time, the other will be null
	private static Rower rower;
	private static Coach coach;
	private static char typeOfSession;
	
	public static boolean setRower(Rower newRower) {
		rower = newRower;
		return true;
	}
	
	public static boolean setCoach(Coach newCoach) {
		coach = newCoach;
		return true;
	}
	
	public static Coach getCoach() {
		return coach;
	}
	
	public static Rower getRower() {
		return rower;
	}

	public static char getTypeOfSession() {
		return typeOfSession;
	}

	public static void setTypeOfSession(char typeOfSession) {
		Session.typeOfSession = typeOfSession;
	}
	
	
	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:WATTFARM_DB;create=true";
	
	public static void main(String[] args) throws ClassNotFoundException , SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("CREATE TABLE COACH_LOGIN " +
                "(coachID INTEGER not NULL, " +
                " username VARCHAR(25), " + 
                " password VARCHAR(25), " + 
                " PRIMARY KEY ( coachID ))");
		
		
		stmt.executeUpdate("CREATE TABLE ROWER_LOGIN " +
                "(rowerID INTEGER not NULL, " +
                " username VARCHAR(25), " + 
                " password VARCHAR(25), " + 
                " PRIMARY KEY ( rowerID ))"); 
		
		stmt.executeUpdate("CREATE TABLE ROWERS " +
                "(rowerID INTEGER not NULL, " +
                " name VARCHAR(25), " + 
                " age INTEGER, " + 
                " height INTEGER, " + 
                " weight INTEGER, " + 
                " PRIMARY KEY ( rowerID ))");
		
		stmt.executeUpdate("CREATE TABLE COACHES " +
                "(coachID INTEGER not NULL, " +
                " name VARCHAR(25), " + 
                " teamID INTEGER not NULL, " + 
                " PRIMARY KEY ( coachID ))");
		
		stmt.executeUpdate("CREATE TABLE WORKOUTS " +
                "(workoutID INTEGER not NULL, " +
                " rowerID INTEGER not NULL, " + 
                " distance INTEGER, " +
                " split INTEGER, " + 
                " time INTEGER, " + 
                " avgWatts INTEGER, " + 
                " spm INTEGER, " + 
                " dateCompleted DATE, " + 
                " tag VARCHAR(225), " + 
                " PRIMARY KEY ( workoutID ))");
		
		stmt.executeUpdate("CREATE TABLE TEAMS " +
                "(coachID INTEGER not NULL, " +
                " name VARCHAR(25), " + 
                " teamID INTEGER not NULL, " + 
                " PRIMARY KEY ( coachID ))");
		
		//connection.createStatement().execute("insert into poop4 values " + "1");
		
		
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
}
