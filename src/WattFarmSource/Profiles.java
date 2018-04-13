package WattFarmSource;

public class Profiles {
	public static String[][] RowerList = { {"sean123" , "password"} , {"rower45" , "ergometer"}};
	public static String[][] CoachList = { {"coach123" , "password"} , {"wattFarmer" , "megaphone"}};
	
	public static boolean validRowerLogin (String username, String password){
		for(int i = 0; i < RowerList.length; i++) {
			for(int j = 0; j < RowerList[i].length; j++) {
				if(username.equals(RowerList[i][j])) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public static boolean validCoachLogin (String username, String password){
		for(int i = 0; i < CoachList.length; i++) {
			for(int j = 0; j < CoachList[i].length; j++) {
				if(username.equals(CoachList[i][j])) {
					return true;
				}
			}
		}
		return false;
	}
}
