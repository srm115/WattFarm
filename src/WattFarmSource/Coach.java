package WattFarmSource;

public class Coach {
	public String name;
	public Team[] teams;
	public int ID;
	
	//going to have to change this, but for now just use PW and UN here
	public String username;
	public String password;
	
	
	public Coach(String name, int ID, String username, String password) {
		this.name = name;		
		this.ID = ID;
		this.username = username;
		this.password = password;
	}
}
