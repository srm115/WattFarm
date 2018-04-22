
import org.junit.Test;

import WattFarmSource.Coach;
import WattFarmSource.Team;

import static org.junit.Assert.fail;

public class CoachClassTest {
	

	//test to check constructor
	@Test
	public void testCoachConstructor() {
		//create a coach with test values
		String testString = "test";
		Team[] testTeams = {null};
		int testInt = 0;
		
		try {
			Coach coach = new Coach(testString, testTeams, testInt, testString, testString);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		
	}
}