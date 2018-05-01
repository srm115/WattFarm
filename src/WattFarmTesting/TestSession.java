import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.junit.Test;

import WattFarmSource.Session;
import junit.framework.TestCase;

public class TestSession extends TestCase {
	@Test
	public void testaddUser() throws SQLException, ClassNotFoundException {
		//Test add coach user
		assertEquals(Session.addUser("testaddUserUN", "testaddUserPW", 'C'), true);

		//Test add rower user
		assertEquals(Session.addUser("testaddUserUN", "testaddUserPW", 'R'), true);

		//Test failure
		assertEquals(Session.addUser("testaddUserUN", "testaddUserPW", 'F'), false);
	}

	@Test
	public void testisUser() throws SQLException, ClassNotFoundException {
		//Add a new coach user
		Session.addUser("testisUserUN", "testisUserPW", 'C');
		//Test isUser
		assertEquals(Session.isUser("testisUserUN", "testisUserPW", 'C'), true);


		//Add a new coach user
		Session.addUser("testisUserUN", "testisUserPW", 'R');
		//Test isUser
		assertEquals(Session.isUser("testisUserUN", "testisUserPW", 'R'), true);

		//Test failures
		assertEquals(Session.isUser("testisUserFail", "testisUserFail", 'C'), false);
		assertEquals(Session.isUser("testisUserFail", "testisUserFail", 'R'), false);
		assertEquals(Session.isUser("testisUserFail", "testisUserFail", 'F'), false);
	}


	@Test
	public void testcreateTeam() throws SQLException, ClassNotFoundException {
		//Test create team
		assertEquals(Session.createTeam("testcreateTeamName"), true);

		//Test failure
		assertEquals(Session.createTeam(""), false);
	}

	@Test
	public void testteamExists() throws SQLException, ClassNotFoundException {
		//Create a team
		Session.createTeam("testteamExistsName");

		//Test if team was created
		assertEquals(Session.teamExists("testteamExistsName"), true);

		//Test failure
		assertEquals(Session.teamExists("testteamExistsFail"), false);
	}

	@Test
	public void testgetRoster() throws SQLException, ClassNotFoundException {
		//tests that the JTable is created and can be added to a JFrame and displayed
		JFrame testFrame = new JFrame();

		JTable testTable1 = Session.getRoster();

		assertEquals(testFrame.add(testTable1), testTable1);

		//Create a coach user, log in, create a team
		Session.addUser("testGetRosterUN", "testGetRosterPW", 'C');
		Session.logInCoach("testGetRosterUN", "testGetRosterPW");
		Session.createTeam("testGetRosterTeam");

		//Create a rower user, log in, edit profile to join team
		Session.addUser("testGetRosterUN", "testGetRosterPW", 'R');
		Session.logInRower("testGetRosterUN", "testGetRosterPW");
		Session.editRowerProfile("testName", 0, 0, 0, "testGetRosterTeam");

		//Create a test JTable with just test rower user
		DefaultTableModel model = new DefaultTableModel(new String[]{"ROWERID, NAME"}, 0);
		int id = Session.getRowerID(); //test rower still logged in
		String name = "testName";
		model.addRow(new Object[]{id, name});
		JTable testTable2 = new JTable();
		testTable2.setModel(model);
		testTable2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		//Log coach back in
		Session.logInCoach("testGetRosterUN", "testGetRosterPW");
		
		//Test that tables are the same
		assertEquals(testTable2, Session.getRoster());
		
		//Test Failure
		JTable testFailTable = new JTable();
		assertEquals(testFailTable, Session.getRoster());
	}
	
	@Test
	public void testgetCoachWorkouts() throws ClassNotFoundException , SQLException {
		
	}
}
