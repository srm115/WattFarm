package WattFarmTesting;

import org.junit.Test;

import WattFarmSource.Workout;

import static org.junit.Assert.fail;

import java.sql.Date;

public class WorkoutClassTest {
	

	//test to check constructor
	@Test
	public void testCoachConstructor() {
		//create a team with test values
		double[] testDoubleArray = {0};
		int[] testIntArray = {0};
		int testInt = 0;
		Date testDate = null;
		
		try {
			Workout workout = new Workout(testDoubleArray, testDoubleArray, testDoubleArray, 
					testDoubleArray, testIntArray, testDate, testInt, testInt);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		
	}
}