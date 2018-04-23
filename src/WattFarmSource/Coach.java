package WattFarmSource;

public class Coach {
	private String name;
	private Team team;
	private int ID;
	
	//going to have to change this, but for now just use PW and UN here
	private String username;
	private String password;
	
	
	public Coach(String name, Team team, int iD, String username, String password) {
		this.name = name;
		this.team = team;
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
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
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
