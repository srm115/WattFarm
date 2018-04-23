package WattFarmSource;

//This will probably become database access methods

public class Profiles {
	public static Rower[] RowerList = {new Rower(
			"Sean",
			21,
			new Workout[] {new Workout()},
			new Workout[] {new Workout()},
			new Workout[] {new Workout()},
			0,
			0,
			1,
			"rower1",
			"password")};
	
	
	public static Coach[] CoachList = {new Coach("Coach" , new Team(new Rower[] {}, null, null), 1, "coach1" , "password")};
	
	public static int CoachLogin (String username, String password){
		for(int i = 0; i < CoachList.length ; i++) {
			if(CoachList[i].getPassword().equals(password) && CoachList[i].getUsername().equals(username)) {
				return CoachList[i].getID();
			}
		}
		return -1; //invalid login
	}
	
	public static int RowerLogin (String username, String password){
		for(int i = 0; i < RowerList.length; i++) {
			
			if(RowerList[i].getPassword().equals(password) && RowerList[i].getUsername().equals(username)) {
				return RowerList[i].getID();
			}
		}
		return -1; //invalid login
	}
	
	
	public static Rower getRowerFromDB(int ID) {
		for(int i = 0; i < RowerList.length ; i++) {
			if(RowerList[i].getID() == ID) {
				return RowerList[i];
			}
		}
		return null; //invalid no userExists with that ID
	}
	
	public static Coach getCoachFromDB(int ID) {
		for(int i = 0; i < CoachList.length ; i++) {
			if(CoachList[i].getID() == ID) {
				return CoachList[i];
			}
		}
		return null; //invalid no userExists with that ID
	}
}
