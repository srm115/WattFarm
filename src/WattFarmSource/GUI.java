package WattFarmSource;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.text.DateFormatter;
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
					if(Session.checkUser(enteredUN, enteredPW, type) != 1) {
						if(type == 'R') {
							Session.logInRower(enteredUN, enteredPW, type);
							JOptionPane.showMessageDialog(null,"Logged in.");
							rowerMainMenu();
							parent.dispose();
						} else {
							Session.logInRower(enteredUN, enteredPW, type);
							JOptionPane.showMessageDialog(null,"Logged in.");
							coachMainMenu();
							parent.dispose();
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
				parent.dispose();
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
					if(Session.checkUser(enteredUN, enteredPW, type) == -1) {
						JOptionPane.showMessageDialog(null,"New user created.");
						createLoginFrame.dispose();
						loginSys();
					}
					else {
						JOptionPane.showMessageDialog(null,"User already exists! Please try again.");
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
		JButton mainButton = new JButton("Main Menu");

		/* Layout of panel */
		createButton.setBounds(10, 10, 100, 20);
		viewButton.setBounds(10, 40, 100, 20);
		mainButton.setBounds(120, 10, 100, 20);


		/* Add everything in and display */
		menuPanel.add(createButton);
		menuPanel.add(viewButton);
		menuPanel.add(mainButton);

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

		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coachMainMenu();
			}
		});
	}

	public static void coachCreateGraphicPage() {
		System.exit(0);
	}

	public static void coachViewGraphicPage() {
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
		System.exit(0);
	}

	public static void editTeamsPage() {
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
				parent.dispose();
			}
		});

		workoutsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rowerWorkoutsMenu();
				parent.dispose();
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
		System.exit(0);
	}

	public static void rowerViewGraphicPage() {
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
		JButton mainButton = new JButton("Main Menu");

		/* Layout of panel */
		newButton.setBounds(10, 10, 100, 20);
		editButton.setBounds(10, 40, 100, 20);
		viewButton.setBounds(10, 70, 100, 20);
		mainButton.setBounds(120, 10, 100, 20);


		/* Add everything in and display */
		menuPanel.add(newButton);
		menuPanel.add(editButton);
		menuPanel.add(viewButton);
		menuPanel.add(mainButton);


		parent.getContentPane().add(menuPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newWorkoutPage();
				parent.dispose();
			}
		});

		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editWorkoutPage();
				parent.dispose();
			}
		});

		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewRowerWorkoutsPage();
				parent.dispose();
			}
		});

		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rowerMainMenu();
				parent.dispose();
			}
		});
	}

	public static void newWorkoutPage() {
		JFrame parent = new JFrame("Enter Workout Information");
		parent.setSize(300,375);
		parent.setLocation(500,280);

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new FlowLayout());

		/*Content in loginPanel */
		JLabel distanceLabel = new JLabel("Distance (m): ");
		JSpinner distanceField = new JSpinner( new SpinnerNumberModel(
				2000, //initial value
				0, //min
				100000, //max
				10));//step);

		JLabel splitLabel = new JLabel("Time/500m (s): ");
		JSpinner splitField = new JSpinner( new SpinnerNumberModel(
				120, //initial value
				0, //min
				500, //max
				1));//step);

		JLabel timeLabel = new JLabel("Time (s):         ");
		JSpinner timeField = new JSpinner( new SpinnerNumberModel(
				480, //initial value
				0, //min
				12000000, //max
				1));//step);

		JLabel wattsLabel = new JLabel("Average Watts (W): ");
		JSpinner wattsField = new JSpinner( new SpinnerNumberModel(
				10, //initial value
				0, //min
				2000, //max
				1));//step);

		JLabel spmLabel = new JLabel("Strokes/Minute: ");
		JSpinner spmField = new JSpinner( new SpinnerNumberModel(
				20, //initial value
				0, //min
				100, //max
				1));//step);

		JLabel dateLabel = new JLabel("Date Completed: ");
		SpinnerDateModel model = new SpinnerDateModel();
		JSpinner dateField = new JSpinner(model);

		JSpinner.DateEditor editor = new JSpinner.DateEditor(dateField, "yyyy.MM.dd");
		DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
		formatter.setAllowsInvalid(false);
		formatter.setOverwriteMode(true);

		JLabel tagLabel = new JLabel("Workout Tag:");
		String[] tags = {"UT2", "UT1", "AT", "TR", "AN"};
		SpinnerListModel strModel = new SpinnerListModel(tags);
		JSpinner tagField = new JSpinner(strModel);


		JButton enterButton = new JButton("Enter Workout");

		/* Add stuff to panel */
		JPanel distancePanel = new JPanel();
		distancePanel.add(distanceLabel);
		distancePanel.add(distanceField);
		formPanel.add(distancePanel);

		JPanel splitPanel = new JPanel();
		splitPanel.add(splitLabel);
		splitPanel.add(splitField);
		formPanel.add(splitPanel);

		JPanel timePanel = new JPanel();
		timePanel.add(timeLabel);
		timePanel.add(timeField);
		formPanel.add(timePanel);


		JPanel wattsPanel = new JPanel();
		wattsPanel.add(wattsLabel);
		wattsPanel.add(wattsField);
		formPanel.add(wattsPanel);

		JPanel spmPanel = new JPanel();
		spmPanel.add(spmLabel);
		spmPanel.add(spmField);
		formPanel.add(spmPanel);

		JPanel datePanel = new JPanel();
		datePanel.add(dateLabel);
		datePanel.add(dateField);
		formPanel.add(datePanel);

		JPanel tagPanel = new JPanel();
		tagPanel.add(tagLabel);
		tagPanel.add(tagField);
		formPanel.add(tagPanel);

		JPanel enterPanel = new JPanel();
		enterPanel.add(enterButton);
		formPanel.add(enterPanel);

		parent.getContentPane().add(formPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/*verify login info*/
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String newDateStr = format.format(dateField.getValue());

					if(Session.addNewWorkout(new Workout(
							(Integer) distanceField.getValue(),
							(Integer) timeField.getValue(),
							(Integer) splitField.getValue(),
							(Integer) wattsField.getValue(),
							(Integer) spmField.getValue(),
							newDateStr,
							(String) tagField.getValue()))){

						JOptionPane.showMessageDialog(null,"Workout logged!");
						parent.dispose();
						rowerMainMenu();
					}
					else {
						JOptionPane.showMessageDialog(null,"Error, please try again.");
						parent.dispose();
						rowerMainMenu();
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}
		});
	}

	public static void editWorkoutPage() {
		
	}

	public static void viewRowerWorkoutsPage() {
		JFrame parent = new JFrame("Workouts Menu");
		parent.setSize(500, 500);;
		parent.setLocation(500,280);

		JPanel workoutsPanel = new JPanel();
		//workoutsPanel.setLayout (new BorderLayout());
		
		/*Content in menuPanel */

		JButton editButton = new JButton("Edit Workout");
		JButton mainButton = new JButton("Main Menu");
		
		
		JScrollPane scroller = new JScrollPane();
		scroller.setLayout(null);
		/*Display all workouts belonging to that rower */
		try {
			scroller = new JScrollPane(Session.getRowerWorkouts());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		workoutsPanel.add(scroller);

		/* Add everything in and display */
		JPanel menuPanel = new JPanel();
		menuPanel.add(editButton);
		menuPanel.add(mainButton);
		
		workoutsPanel.add(menuPanel);
		
		parent.getContentPane().add(workoutsPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */

		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editWorkoutPage();
				parent.dispose();
			}
		});


		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rowerMainMenu();
				parent.dispose();
			}
		});
	}
}