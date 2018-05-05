import java.sql.SQLException;
import java.text.ParseException;

import org.junit.Test;

import WattFarmSource.Session;
import WattFarmSource.Workout;
import junit.framework.TestCase;

public class TestSession extends TestCase {
	@Test
	public void testCoachMethods() throws SQLException, ClassNotFoundException {
		//Test add coach user
		assertEquals(Session.addUser("testaddUserUN", "testaddUserPW", 'C'), true);

		//set to high val
		Session.setCoachID(1111111);

		//test get and sets
		assertEquals(Session.getCoachID(), 1111111);

		assertEquals(Session.isUser("testaddUserUN", "testaddUserPW", 'C'), true);

		//test login
		assertEquals(Session.logInCoach("testaddUserUN", "testaddUserPW"), true);

		//test create a team
		assertEquals(Session.createTeam("testTeam1"), true);

		//test teamExists method
		assertEquals(Session.teamExists("testTeam1"), true);

		//test changeTeamCoachID
		assertEquals(Session.changeTeamCoachID("testTeam1", 1111111), true);

		//test getTeamName
		assertEquals(Session.getTeamName(), "testTeam1");

		//test changeCoachTeamID
		assertEquals(Session.changeCoachTeamID(11111), true);

		//test getTeamIDFromName
		assertEquals(Session.getTeamIDFromName("asdf"), -1); //team doesn't exist

		//test getCoachTeamID
		assertEquals(Session.getCoachTeamID(), 11111);

		//test getCoachTeamID
		assertEquals(Session.getCoachTeamID(), 11111);

		//test changeTeamCoachID
		assertEquals(Session.changeTeamCoachID("testTeam1", 1111111), true);

		//test changeCoachName
		assertEquals(Session.changeCoachName("testName"), true);
	}

	@Test
	public void testRowerMethods() throws SQLException, ClassNotFoundException {
		//Test add coach user
		assertEquals(Session.addUser("testaddRowerUN", "testaddRowerPW", 'R'), true);

		//test login
		assertEquals(Session.logInRower("testaddRowerUN", "testaddRowerPW"), true);

		//Create fake workout and test that it gets added
		Workout workout = null;

		try {
			workout = new Workout(1,1,1,1,1,"","");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(Session.addNewWorkout(workout), true);

		//test edit profile
		assertEquals(Session.editRowerProfile("testName", 1, 1, 1, ""),true);
		
		//test get and set for rowerID
		Session.setRowerID(99999); //high values
		assertEquals(Session.getRowerID(), 99999);
		
		//test getRowerName
		assertEquals(Session.getRowerName(99999), "testName");
		
		//test getNumRowerWorkouts
		assertEquals(Session.getNumRowerWorkouts(), 1);
	}
}
