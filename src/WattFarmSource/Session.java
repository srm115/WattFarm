package WattFarmSource;

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
	
	
}
