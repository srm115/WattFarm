package WattFarmSource;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DateFormatter;


public class GUI {
	//main method for WattFarm. Launches software
	public static void main(String[] args){
		loginSys();
	}


	/**
	 * Login system GUI
	 */
	public static void loginSys(){
		JFrame parent = new JFrame("WattFarm Login");
		parent.setLocation(750,500);

		//Add logo image to Panel
		JLabel logoLabel = new JLabel();
		logoLabel.setSize(new Dimension(750, 200));
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		Image logoImage = null;
		try{
			File f = new File("WFLogo.png");
			logoImage = ImageIO.read(f);
		}
		catch (IOException e){
			e.printStackTrace();
		}

		Image scaledLogo = logoImage.getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(),
				Image.SCALE_SMOOTH);
		ImageIcon logoIcon = new ImageIcon(scaledLogo);
		logoLabel.setIcon((Icon) logoIcon);

		//Create Login Box
		Box loginBox = Box.createVerticalBox();

		JTextField usernameField = new JTextField(15);
		JTextField passwordField = new JPasswordField(15);

		JRadioButton option1 = new JRadioButton("Rower");
		JRadioButton option2 = new JRadioButton("Coach");
		ButtonGroup group = new ButtonGroup();
		group.add(option1);
		option1.setSelected(true);
		group.add(option2);

		JButton loginButton = new JButton("Login");
		loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton createLoginButton = new JButton("Create New Login");
		createLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel usernameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");

		//packing everything into panels

		JPanel unPanel = new JPanel();
		unPanel.add(usernameLabel);
		unPanel.add(usernameField);

		JPanel pwPanel = new JPanel();
		pwPanel.add(passwordLabel);
		pwPanel.add(passwordField);

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(option1);
		optionsPanel.add(option2);

		JPanel menuButtonsPanel = new JPanel();
		menuButtonsPanel.add(loginButton);
		menuButtonsPanel.add(createLoginButton);

		/* Add everything in and display */
		loginBox.add(logoLabel);
		loginBox.add(unPanel);
		loginBox.add(pwPanel);
		loginBox.add(optionsPanel);
		loginBox.add(menuButtonsPanel);


		parent.getContentPane().add(loginBox);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.pack();
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
					if(Session.isUser(enteredUN, enteredPW, type)) { //user exists
						if(type == 'R') {
							Session.logInRower(enteredUN, enteredPW);
							JOptionPane.showMessageDialog(null,"Logged in.");
							rowerMainMenu();
							parent.dispose();
						} else {
							Session.logInCoach(enteredUN, enteredPW);
							JOptionPane.showMessageDialog(null,"Logged in.");
							coachMainMenu();
							parent.dispose();
						}

					}

					else if(enteredUN.equals("") && enteredPW.equals("")){
						JOptionPane.showMessageDialog(null,"Please insert Username and Password");
					}
					else { //invalid login or user doesn't exist
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

		JButton backButton = new JButton("Back to Login");

		createLoginFrame.setSize(300,250);
		createLoginFrame.setLocation(500,280);
		newUserPanel.setLayout (null); 


		usernameField.setBounds(70,30,150,20);
		passwordField.setBounds(70,65,150,20);
		newUserPanel.setBounds(110,100,80,20);
		option1.setBounds(60,100,80,20);
		option2.setBounds(140,100,80,20);
		usernameLabel.setBounds(20,28,80,20);
		passwordLabel.setBounds(20,63,80,20);
		createButton.setBounds(100, 130,100,20);
		backButton.setBounds(100, 160, 100, 20);

		newUserPanel.add(createButton);
		newUserPanel.add(usernameField);
		newUserPanel.add(passwordField);
		newUserPanel.add(option1);
		newUserPanel.add(option2);
		newUserPanel.add(backButton);

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
					if(!Session.isUser(enteredUN, enteredPW, type)) {
						Session.addUser(enteredUN, enteredPW, type);
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

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createLoginFrame.dispose();
				loginSys();
			}
		});
	}




	//Coach Pages
	public static void coachMainMenu() {
		JFrame parent = new JFrame("WattFarm Main Menu");
		parent.setSize(300,150);
		parent.setLocation(750,500);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

		/*Content in loginPanel */

		JButton teamsButton = new JButton("Teams");
		teamsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton graphicsButton = new JButton("Graphics");
		graphicsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton profileButton = new JButton("Profile");
		profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton workoutsButton = new JButton("Workouts");
		workoutsButton.setAlignmentX(Component.CENTER_ALIGNMENT);


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
				parent.dispose();
			}
		});

		graphicsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coachGraphicsMenu();
				parent.dispose();
			}
		});

		profileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coachProfileMenu();
				parent.dispose();
			}
		});

		workoutsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coachWorkoutsMenu();
				parent.dispose();
			}
		});
	}

	public static void coachGraphicsMenu() {
		JFrame parent = new JFrame("Graphics Menu");
		parent.setSize(300,200);
		parent.setLocation(750,500);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

		/*Content in loginPanel */

		JButton createButton = new JButton("Create Graphic");
		createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton viewButton = new JButton("View Graphic");
		viewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton mainButton = new JButton("Main Menu");
		mainButton.setAlignmentX(Component.CENTER_ALIGNMENT);


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
				parent.dispose();
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
		parent.setLocation(750,500);

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
				parent.dispose();
			}
		});
	}

	public static void coachEditProfilePage() {
		System.exit(0);
	}

	public static void teamsMenu() {
		JFrame parent = new JFrame("Teams Menu");
		parent.setSize(300,200);
		parent.setLocation(750,500);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout (null);

		/*Content in loginPanel */

		JButton viewButton = new JButton("View Teams");
		JButton editButton = new JButton("Edit Teams");
		JButton createButton = new JButton("Create Team");

		/* Layout of panel */
		viewButton.setBounds(10, 10, 100, 20);
		editButton.setBounds(10, 40, 100, 20);
		createButton.setBounds(10, 70, 100, 20);

		/* Add everything in and display */
		menuPanel.add(viewButton);
		menuPanel.add(editButton);
		menuPanel.add(createButton);

		parent.getContentPane().add(menuPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */
		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewTeamPage();
				parent.dispose();
			}
		});

		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editTeamsPage();
				parent.dispose();
			}
		});

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createTeamPage();
				parent.dispose();
			}
		});
	}

	public static void viewTeamPage() {
		JFrame parent = new JFrame();
		parent.setSize(500, 500);
		parent.setLocation(750,500);

		JPanel teamPanel = new JPanel();

		/*Content in teamPanel */
		JButton mainButton = new JButton("Main Menu");


		JScrollPane scroller = new JScrollPane();

		/*Display all workouts belonging to that rower */
		try {
			scroller = new JScrollPane(Session.getRoster());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		teamPanel.add(scroller);

		/* Add everything in and display */
		JPanel menuPanel = new JPanel();
		menuPanel.add(mainButton);

		teamPanel.add(menuPanel);

		parent.getContentPane().add(teamPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */
		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coachMainMenu();
				parent.dispose();
			}
		});
	}

	public static void editTeamsPage() {
		System.exit(0);
	}

	public static void createTeamPage() {
		JFrame parent = new JFrame("Create New Team");

		JButton createButton = new JButton("Create");
		JPanel newTeamPanel = new JPanel();

		JTextField nameField = new JTextField(25); //max UN length = 25 char

		JLabel nameLabel = new JLabel("Team Name: ");


		parent.setSize(500,200);
		parent.setLocation(750,500);

		JPanel namePanel = new JPanel();
		namePanel.add(nameLabel);
		namePanel.add(nameField);

		JButton mainButton = new JButton("Main Menu");


		newTeamPanel.add(namePanel);
		newTeamPanel.add(createButton);
		newTeamPanel.add(mainButton);

		parent.getContentPane().add(newTeamPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);


		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				/*Get entered info*/
				String enteredName = nameField.getText();


				try {
					if(!Session.teamExists(enteredName)) {
						JOptionPane.showMessageDialog(null,"New team created.");
						Session.createTeam(enteredName);
						parent.dispose();
						teamsMenu();
					}
					else {
						JOptionPane.showMessageDialog(null,"Team already exists. Please try again.");
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

		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.dispose();
				coachMainMenu();
			}
		});
	}

	public static void coachWorkoutsMenu() {
		JFrame parent = new JFrame("Workouts Menu");
		parent.setSize(300,200);
		parent.setLocation(750,500);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout (null);

		/*Content in loginPanel */

		JButton viewButton = new JButton("View Workouts");
		JButton mainButton = new JButton("Main Menu");

		/* Layout of panel */
		viewButton.setBounds(10, 10, 100, 20);
		mainButton.setBounds(120, 10, 100, 20);


		/* Add everything in and display */
		menuPanel.add(viewButton);
		menuPanel.add(mainButton);


		parent.getContentPane().add(menuPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */
		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coachViewWorkoutPage();
				parent.dispose();
			}
		});

		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.dispose();
				coachMainMenu();
			}
		});
	}

	public static void coachViewWorkoutPage() {
		JFrame parent = new JFrame("Workouts");
		parent.setSize(500, 500);;
		parent.setLocation(750,500);

		JPanel workoutsPanel = new JPanel();
		//workoutsPanel.setLayout (new BorderLayout());

		/*Content in menuPanel */

		JButton mainButton = new JButton("Main Menu");


		JScrollPane scroller = new JScrollPane();
		scroller.setLayout(null);
		/*Display all workouts belonging to that coach */
		try {
			scroller = new JScrollPane(Session.getCoachWorkouts());
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
		menuPanel.add(mainButton);

		workoutsPanel.add(menuPanel);

		parent.getContentPane().add(workoutsPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */
		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coachMainMenu();
				parent.dispose();
			}
		});
	}










	//Rower Pages
	public static void rowerMainMenu() {
		JFrame parent = new JFrame("WattFarm Main Menu");
		parent.setSize(300,150);
		parent.setLocation(750,500);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

		/*Content in loginPanel */

		JButton graphicsButton = new JButton("Graphics");
		graphicsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton profileButton = new JButton("Profile");
		profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton workoutsButton = new JButton("Workouts");
		workoutsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

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
				rowerCreateGraphicPage();
				parent.dispose();
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

	public static void rowerCreateGraphicPage() {
		JFrame parent = new JFrame("Create Graphic:");
		parent.setLocation(750,500);

		Box formBox = Box.createVerticalBox();

		/*Content in loginPanel */
		JLabel paramLabel = new JLabel("Create a new graphic based on: ");
		String[] params = {"DISTANCE", "SPLIT", "TIME", "AVG WATTS", "SPM"};
		SpinnerListModel strModel = new SpinnerListModel(params);
		JSpinner paramField = new JSpinner(strModel);
		JPanel paramPanel = new JPanel();
		paramPanel.add(paramLabel);
		paramPanel.add(paramField);
		paramPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel nLabel = new JLabel("Number of workouts: ");
		int maxVals = 0;
		try {
			maxVals = Session.getNumRowerWorkouts();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		JSpinner nField = new JSpinner( new SpinnerNumberModel(
				0, //initial value
				0, //min
				maxVals, //max
				1)); //step
		JPanel nPanel = new JPanel();
		nPanel.add(nLabel);
		nPanel.add(nField);
		nPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton enterButton = new JButton("Generate Graphic");
		enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton mainButton = new JButton("Main Menu");
		enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		/* Add stuff to panel */
		formBox.add(paramPanel);
		formBox.add(nPanel);
		formBox.add(enterButton);
		formBox.add(mainButton);

		parent.getContentPane().add(formBox);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.pack();
		parent.setVisible(true);

		/*verify login info*/
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Integer> yValues = null;
				try {
					yValues = Session.getYValues((String)paramField.getValue() , (Integer)nField.getValue());
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Graphic.createPlot(yValues, (String) paramField.getValue());
				parent.dispose();
				rowerMainMenu();
			}
		});
		
		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.dispose();
				rowerMainMenu();
			}
		});
	}

	public static void rowerProfileMenu() {
		JFrame parent = new JFrame("Profile Menu");
		parent.setLocation(750,500);

		JPanel profilePanel = new JPanel();
		profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));

		//Obtains rower's information in JPanel format
		try {
			profilePanel.add(Session.getRowerProfile());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//MenuPanel
		JButton mainButton = new JButton("Main Menu");
		JButton editButton = new JButton("Edit Profile");

		JPanel menuPanel = new JPanel();
		menuPanel.add(mainButton);
		menuPanel.add(editButton);

		profilePanel.add(menuPanel);

		parent.getContentPane().add(profilePanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.pack();
		parent.setVisible(true);

		/* Action Listeners */
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.dispose();
				rowerEditProfilePage();
			}
		});

		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.dispose();
				rowerMainMenu();
			}
		});
	}

	public static void rowerEditProfilePage() {
		JFrame parent = new JFrame("Enter Profile Information");
		parent.setLocation(750,500);
		parent.setLayout(new BorderLayout());

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

		/*Content in loginPanel */
		JLabel nameLabel = new JLabel("Name: ");
		JTextField nameField = new JTextField(25);

		JLabel ageLabel = new JLabel("Age: ");
		JSpinner ageField = new JSpinner( new SpinnerNumberModel(
				18, //initial value
				0, //min
				105, //max
				1));//step);

		JLabel heightLabel = new JLabel("Height (in): ");
		JSpinner heightField = new JSpinner( new SpinnerNumberModel(
				60, //initial value
				0, //min
				100, //max
				1));//step);

		JLabel weightLabel = new JLabel("Weight (lbs): ");
		JSpinner weightField = new JSpinner( new SpinnerNumberModel(
				150, //initial value
				0, //min
				400, //max
				1));//step);

		JLabel teamLabel = new JLabel("Team:");
		String[] teams = null;
		try {
			teams = Session.getTeams();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} //Spinner vals from teams table
		SpinnerListModel strModel = new SpinnerListModel(teams);
		JSpinner teamField = new JSpinner(strModel);


		JButton confirmButton = new JButton("Confirm");

		/* Add stuff to panel */
		JPanel namePanel = new JPanel();
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		formPanel.add(namePanel);

		JPanel agePanel = new JPanel();
		agePanel.add(ageLabel);
		agePanel.add(ageField);
		formPanel.add(agePanel);

		JPanel heightPanel = new JPanel();
		heightPanel.add(heightLabel);
		heightPanel.add(heightField);
		formPanel.add(heightPanel);

		JPanel weightPanel = new JPanel();
		weightPanel.add(weightLabel);
		weightPanel.add(weightField);
		formPanel.add(weightPanel);

		JPanel teamPanel = new JPanel();
		teamPanel.add(teamLabel);
		teamPanel.add(teamField);
		formPanel.add(teamPanel);

		JPanel confirmPanel = new JPanel();
		confirmPanel.add(confirmButton);

		parent.getContentPane().add(formPanel, BorderLayout.CENTER);
		parent.getContentPane().add(confirmPanel, BorderLayout.SOUTH);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.pack();
		parent.setVisible(true);

		/*verify login info*/
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(Session.editRowerProfile(
							(String) nameField.getText(),
							(Integer) ageField.getValue(),
							(Integer) heightField.getValue(),
							(Integer) weightField.getValue(),
							(String)teamField.getValue())){

						JOptionPane.showMessageDialog(null,"Profile updated!");
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
				}
			}
		});




	}

	public static void rowerWorkoutsMenu() {
		JFrame parent = new JFrame("Workouts Menu");
		parent.setPreferredSize(new Dimension(300, 200));
		parent.setLocation(750,500);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout (new FlowLayout());

		Box menuBox = Box.createVerticalBox();
		/*Content in loginPanel */

		JButton newButton = new JButton("New Workout");
		newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton editButton = new JButton("Edit Workout");
		editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton viewButton = new JButton("View Workouts");
		viewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton mainButton = new JButton("Main Menu");
		mainButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		menuBox.add(newButton);
		menuBox.add(editButton);
		menuBox.add(viewButton);
		menuBox.add(mainButton);

		menuPanel.add(menuBox);


		parent.getContentPane().add(menuPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.pack();
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
		parent.setLocation(750,500);

		Box formPanel = Box.createVerticalBox();

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
		parent.pack();
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
		JFrame parent = new JFrame("Workouts");
		parent.setSize(500, 500);;
		parent.setLocation(750,500);

		JPanel workoutsPanel = new JPanel();
		workoutsPanel.setLayout(new BoxLayout(workoutsPanel, BoxLayout.Y_AXIS));

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