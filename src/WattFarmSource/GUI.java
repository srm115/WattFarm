package WattFarmSource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.text.JTextComponent;



public class GUI {
	//main method for WattFarm. Launches software
	public static void main(String[] args) {
		loginSys();
	}


	/**
	 * Login system GUI
	 */
	public static void loginSys() {
		JFrame parent = new JFrame("WattFarm Login");
		parent.setSize(300,300);
		parent.setLocation(500,280);

		JPanel loginPanel = new JPanel();
		loginPanel.setLayout (null);

		/*Content in loginPanel */
		JTextField usernameField = new JTextField(15);
		JTextField passwordField = new JPasswordField(15);

		JRadioButton option1 = new JRadioButton("Rower");
		JRadioButton option2 = new JRadioButton("Coach");
		ButtonGroup group = new ButtonGroup();
		group.add(option1);
		option1.setSelected(true);
		group.add(option2);

		JButton loginButton = new JButton("Login");
		JButton createLoginButton = new JButton("Create New Login");

		JLabel usernameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");

		/* Layout of panel */
		usernameField.setBounds(100,30,150,20);
		passwordField.setBounds(100,65,150,20);
		loginButton.setBounds(110,100,80,20);
		option1.setBounds(60,135,80,20);
		option2.setBounds(140,135,80,20);
		usernameLabel.setBounds(20,28,80,20);
		passwordLabel.setBounds(20,63,80,20);
		createLoginButton.setBounds(100, 200, 150, 20);


		/* Add everything in and display */
		loginPanel.add(usernameField);
		loginPanel.add(passwordField);
		loginPanel.add(option1);
		loginPanel.add(option2);
		loginPanel.add(loginButton);
		loginPanel.add(usernameLabel);
		loginPanel.add(passwordLabel);
		loginPanel.add(createLoginButton);

		parent.getContentPane().add(loginPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);


		/*verify login info*/
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*Get entered info*/
				String enteredUN = usernameField.getText();
				String enteredPW = passwordField.getText();
				char type;
				if(option1.isSelected() == true ){
					type = 'R';
				}
				else {
					type = 'C';
				}


				/*Check if valid Login*/


				try {
					if(Session.checkUser(enteredUN, enteredPW, type)) {
						if(type == 'R') {
							rowerMainMenu();
						} else {
							coachMainMenu();
						}

					}

					else if(enteredUN.equals("") && enteredPW.equals("")){
						JOptionPane.showMessageDialog(null,"Please insert Username and Password");
					}
					else {
						JOptionPane.showMessageDialog(null,"Wrong Username / Password");
						usernameField.setText("");
						passwordField.setText("");
						usernameField.requestFocus();
					}

				} catch (ClassNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});

		//Create New Login Button Listener
		createLoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createLogin();
			}
		});
	}


	//Create Login
	public static void createLogin(){
		JFrame createLoginFrame = new JFrame("Create New Login");

		JButton createButton = new JButton("Create");
		JPanel newUserPanel = new JPanel();
		JTextField usernameField = new JTextField(25); //max UN length = 25 char
		JTextField passwordField = new JTextField(25); //max PW length = 25 char
		JRadioButton option1 = new JRadioButton("Rower");
		JRadioButton option2 = new JRadioButton("Coach");
		ButtonGroup group = new ButtonGroup();
		group.add(option1);
		option1.setSelected(true);
		group.add(option2);

		JLabel usernameLabel = new JLabel("New Username: ");
		JLabel passwordLabel = new JLabel("New Password: ");


		createLoginFrame.setSize(300,200);
		createLoginFrame.setLocation(500,280);
		newUserPanel.setLayout (null); 


		usernameField.setBounds(70,30,150,20);
		passwordField.setBounds(70,65,150,20);
		newUserPanel.setBounds(110,100,80,20);
		option1.setBounds(60,135,80,20);
		option2.setBounds(140,135,80,20);
		usernameLabel.setBounds(20,28,80,20);
		passwordLabel.setBounds(20,63,80,20);
		createButton.setBounds(110,100,80,20);

		newUserPanel.add(createButton);
		newUserPanel.add(usernameField);
		newUserPanel.add(passwordField);
		newUserPanel.add(option1);
		newUserPanel.add(option2);

		createLoginFrame.getContentPane().add(newUserPanel);
		createLoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createLoginFrame.setVisible(true);


		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				/*Get entered info*/
				String enteredUN = usernameField.getText();
				String enteredPW = passwordField.getText();
				char type;
				if(option1.isSelected() == true ){
					type = 'R';
				}
				else {
					type = 'C';
				}

				try {
					if(Session.addUser(enteredUN, enteredPW, type)) {
						System.out.println("New User created");
						createLoginFrame.dispose();
					}
					else {
						System.out.println("Error, user alread exists. Please try again");
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}




	//Coach Pages
	public static void coachMainMenu() {
		JFrame parent = new JFrame("WattFarm Main Menu");
		parent.setSize(300,200);
		parent.setLocation(500,280);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout (null);

		/*Content in loginPanel */

		JButton teamsButton = new JButton("Teams");
		JButton graphicsButton = new JButton("Graphics");
		JButton profileButton = new JButton("Profile");
		JButton workoutsButton = new JButton("Workouts");

		/* Layout of panel */
		teamsButton.setBounds(10, 10, 100, 20);
		graphicsButton.setBounds(10, 40, 100, 20);
		profileButton.setBounds(10, 70, 100, 20);
		workoutsButton.setBounds(10, 100, 100, 20);


		/* Add everything in and display */
		menuPanel.add(teamsButton);
		menuPanel.add(graphicsButton);
		menuPanel.add(profileButton);
		menuPanel.add(workoutsButton);

		parent.getContentPane().add(menuPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */
		teamsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamsMenu();
			}
		});

		graphicsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coachGraphicsMenu();
			}
		});

		profileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coachProfileMenu();
			}
		});

		workoutsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coachWorkoutsMenu();
			}
		});
	}

	public static void coachGraphicsMenu() {
		JFrame parent = new JFrame("Graphics Menu");
		parent.setSize(300,200);
		parent.setLocation(500,280);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout (null);

		/*Content in loginPanel */

		JButton createButton = new JButton("Create Graphic");
		JButton viewButton = new JButton("View Graphic");

		/* Layout of panel */
		createButton.setBounds(10, 10, 100, 20);
		viewButton.setBounds(10, 40, 100, 20);


		/* Add everything in and display */
		menuPanel.add(createButton);
		menuPanel.add(viewButton);

		parent.getContentPane().add(menuPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coachCreateGraphicPage();
			}
		});

		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coachViewGraphicPage();
			}
		});
	}

	public static void coachCreateGraphicPage() {
		System.out.println("Coach Create Graphic Page!");
		System.exit(0);
	}

	public static void coachViewGraphicPage() {
		System.out.println("Coach View Graphic Page!");
		System.exit(0);
	}

	public static void coachProfileMenu() {
		JFrame parent = new JFrame("Profile Menu");
		parent.setSize(300,200);
		parent.setLocation(500,280);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout (null);

		/*Content in loginPanel */

		JButton editButton = new JButton("Edit Profile");

		/* Layout of panel */
		editButton.setBounds(10, 10, 100, 20);


		/* Add everything in and display */
		menuPanel.add(editButton);

		parent.getContentPane().add(menuPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coachEditProfilePage();
			}
		});
	}

	public static void coachEditProfilePage() {
		System.out.println("Coach Edit Profile Page!");
		System.exit(0);
	}

	public static void teamsMenu() {
		JFrame parent = new JFrame("Teams Menu");
		parent.setSize(300,200);
		parent.setLocation(500,280);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout (null);

		/*Content in loginPanel */

		JButton viewButton = new JButton("View Teams");
		JButton editButton = new JButton("Edit Teams");

		/* Layout of panel */
		viewButton.setBounds(10, 10, 100, 20);
		editButton.setBounds(10, 40, 100, 20);


		/* Add everything in and display */
		menuPanel.add(viewButton);
		menuPanel.add(editButton);

		parent.getContentPane().add(menuPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */
		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewTeamsPage();
			}
		});

		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editTeamsPage();
			}
		});
	}

	public static void viewTeamsPage() {
		System.out.println("View Teams Page!");
		System.exit(0);
	}

	public static void editTeamsPage() {
		System.out.println("Edit Teams Page!");
		System.exit(0);
	}

	public static void coachWorkoutsMenu() {
		JFrame parent = new JFrame("Workouts Menu");
		parent.setSize(300,200);
		parent.setLocation(500,280);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout (null);

		/*Content in loginPanel */

		JButton viewButton = new JButton("View Workouts");

		/* Layout of panel */
		viewButton.setBounds(10, 10, 100, 20);


		/* Add everything in and display */
		menuPanel.add(viewButton);

		parent.getContentPane().add(menuPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */
		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coachViewWorkoutPage();
			}
		});
	}

	public static void coachViewWorkoutPage() {
		System.out.println("Coach View Workout Page!");
		System.exit(0);
	}










	//Rower Pages
	public static void rowerMainMenu() {
		JFrame parent = new JFrame("WattFarm Main Menu");
		parent.setSize(300,200);
		parent.setLocation(500,280);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout (null);

		/*Content in loginPanel */

		JButton graphicsButton = new JButton("Graphics");
		JButton profileButton = new JButton("Profile");
		JButton workoutsButton = new JButton("Workouts");

		/* Layout of panel */
		graphicsButton.setBounds(10, 10, 100, 20);
		profileButton.setBounds(10, 40, 100, 20);
		workoutsButton.setBounds(10, 70, 100, 20);


		/* Add everything in and display */
		menuPanel.add(graphicsButton);
		menuPanel.add(profileButton);
		menuPanel.add(workoutsButton);

		parent.getContentPane().add(menuPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */
		graphicsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rowerGraphicsMenu();
			}
		});

		profileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rowerProfileMenu();
			}
		});

		workoutsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rowerWorkoutsMenu();
			}
		});
	}

	public static void rowerGraphicsMenu() {
		JFrame parent = new JFrame("Graphics Menu");
		parent.setSize(300,200);
		parent.setLocation(500,280);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout (null);

		/*Content in loginPanel */

		JButton createButton = new JButton("Create Graphic");
		JButton viewButton = new JButton("View Graphic");

		/* Layout of panel */
		createButton.setBounds(10, 10, 100, 20);
		viewButton.setBounds(10, 40, 100, 20);


		/* Add everything in and display */
		menuPanel.add(createButton);
		menuPanel.add(viewButton);

		parent.getContentPane().add(menuPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rowerCreateGraphicPage();
			}
		});

		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rowerViewGraphicPage();
			}
		});
	}

	public static void rowerCreateGraphicPage() {
		System.out.println("Rower Create Graphic Page!");
		System.exit(0);
	}

	public static void rowerViewGraphicPage() {
		System.out.println("Rower View Graphic Page!");
		System.exit(0);
	}

	public static void rowerProfileMenu() {
		JFrame parent = new JFrame("Profile Menu");
		parent.setSize(300,200);
		parent.setLocation(500,280);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout (null);

		/*Content in loginPanel */

		JButton editButton = new JButton("Edit Profile");

		/* Layout of panel */
		editButton.setBounds(10, 10, 100, 20);


		/* Add everything in and display */
		menuPanel.add(editButton);

		parent.getContentPane().add(menuPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rowerEditProfilePage();
			}
		});
	}

	public static void rowerEditProfilePage() {
		System.out.println("Coach Edit Profile Page!");
		System.exit(0);
	}

	public static void rowerWorkoutsMenu() {
		JFrame parent = new JFrame("Workouts Menu");
		parent.setSize(300,200);
		parent.setLocation(500,280);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout (null);

		/*Content in loginPanel */

		JButton newButton = new JButton("New Workout");
		JButton editButton = new JButton("Edit Workout");
		JButton viewButton = new JButton("View Workouts");

		/* Layout of panel */
		newButton.setBounds(10, 10, 100, 20);
		editButton.setBounds(10, 40, 100, 20);
		viewButton.setBounds(10, 70, 100, 20);


		/* Add everything in and display */
		menuPanel.add(newButton);
		menuPanel.add(editButton);
		menuPanel.add(viewButton);

		parent.getContentPane().add(menuPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newWorkoutPage();
			}
		});

		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editWorkoutPage();
			}
		});

		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewRowerWorkoutsPage();
			}
		});
	}

	public static void newWorkoutPage() {
		System.out.println("New Workout Page!");
		System.exit(0);
	}

	public static void editWorkoutPage() {
		System.out.println("Edit Workout Page!");
		System.exit(0);
	}

	public static void viewRowerWorkoutsPage() {
		System.out.println("View Rower Workouts Page!");
		System.exit(0);
	}

}