package WattFarmSource;

public class Rower {
	private String name;
	private int age;
	private Workout[] workouts;
	private Workout[] PRs;
	private Workout[] goals;
	private int height;
	private int weight;
	private int ID;
	
	//going to have to change this, but for now just use PW and UN here
	private String username;
	private String password;
	
	
	public Rower(String name, int age, Workout[] workouts, Workout[] pRs, Workout[] goals, int height, int weight,
			int iD, String username, String password) {
		this.name = name;
		this.age = age;
		this.workouts = workouts;
		PRs = pRs;
		this.goals = goals;
		this.height = height;
		this.weight = weight;
		ID = iD;
		this.username = username;
		this.password = password;
	}

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public Workout[] getWorkouts() {
		return workouts;
	}


	public void setWorkouts(Workout[] workouts) {
		this.workouts = workouts;
	}


	public Workout[] getPRs() {
		return PRs;
	}


	public void setPRs(Workout[] pRs) {
		PRs = pRs;
	}


	public Workout[] getGoals() {
		return goals;
	}


	public void setGoals(Workout[] goals) {
		this.goals = goals;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getWeight() {
		return weight;
	}


	public void setWeight(int weight) {
		this.weight = weight;
	}


	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
