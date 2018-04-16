package WattFarmSource;

public class Team {
	private Rower[] rowers;
	private Coach coach;
	private Workout[] workouts;	
	
	public Team(Rower[] rowers, Coach coach, Workout[] workouts) {
		this.rowers = rowers;
		this.coach = coach;
		this.workouts = workouts;
	}
	
	
	public Rower[] getRowers() {
		return rowers;
	}
	public void setRowers(Rower[] rowers) {
		this.rowers = rowers;
	}
	public Coach getCoach() {
		return coach;
	}
	public void setCoach(Coach coach) {
		this.coach = coach;
	}
	public Workout[] getWorkouts() {
		return workouts;
	}
	public void setWorkouts(Workout[] workouts) {
		this.workouts = workouts;
	}
	
	
}
