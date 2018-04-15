package WattFarmSource;

public class Rower {
	public String name;
	public int age;
	public Workout[] workouts;
	public Workout[] PRs;
	public Workout[] goals;
	public int height;
	public int weight;
	public int ID;
	
	//going to have to change this, but for now just use PW and UN here
	public String username;
	public String password;
	
	
	public Rower(String name, int ID, String username, String password) {
		this.name = name;		
		this.ID = ID;
		this.username = username;
		this.password = password;
	}
}
