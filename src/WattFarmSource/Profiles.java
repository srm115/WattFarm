package WattFarmSource;

//This will probably become database access methods

public class Profiles {
	public static Rower[] RowerList = {new Rower("Sean" , 1, "rower1", "password")};
	public static Coach[] CoachList = {new Coach("Coach" , 1, "coach1", "password")};
	
	public static int CoachLogin (String username, String password){
		for(int i = 0; i < CoachList.length ; i++) {
			if(CoachList[i].password.equals(password) && CoachList[i].username.equals(username)) {
				return CoachList[i].ID;
			}
		}
		return -1; //invalid login
	}
	
	public static int RowerLogin (String username, String password){
		for(int i = 0; i < RowerList.length; i++) {
			if(RowerList[i].password.equals(password) && RowerList[i].username.equals(username)) {
				return RowerList[i].ID;
			}
		}
		return -1; //invalid login
	}
	
	
	public static Rower getRowerFromDB(int ID) {
		for(int i = 0; i < RowerList.length ; i++) {
			if(RowerList[i].ID == ID) {
				return RowerList[i];
			}
		}
		return null; //invalid no userExists with that ID
	}
	
	public static Coach getCoachFromDB(int ID) {
		for(int i = 0; i < CoachList.length ; i++) {
			if(CoachList[i].ID == ID) {
				return CoachList[i];
			}
		}
		return null; //invalid no userExists with that ID
	}
}
