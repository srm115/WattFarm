package WattFarmTesting;

import org.junit.Test;

import WattFarmSource.Coach;
import WattFarmSource.Rower;
import WattFarmSource.Team;
import WattFarmSource.Workout;

import static org.junit.Assert.fail;

public class TeamClassTest {
	

	//test to check constructor
	@Test
	public void testCoachConstructor() {
		//create a team with test values
		Rower[] testRowers = {null};
		Coach testCoach = null;
		Workout[] testWorkouts = {null};
		
		try {
			Team team = new Team(testRowers, testCoach, testWorkouts);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		
	}
}