package WattFarmSource;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		try {
			Session.createDB(); //try to set up database, it might already be created
		} catch (Exception e1) {
			//Don't create it
		}


		JFrame parent = new JFrame("WattFarm Login");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

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
			JOptionPane.showMessageDialog(null,"Error");
			loginSys();
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


		//ActionListeners
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
					JOptionPane.showMessageDialog(null,"Error");
					loginSys();
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null,"Error");
					loginSys();
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
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		createLoginFrame.setLocation(dim.width/2-createLoginFrame.getSize().width/2, dim.height/2-createLoginFrame.getSize().height/2);
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
					else if(enteredUN.equals("") && enteredPW.equals("")){
						JOptionPane.showMessageDialog(null,"Please create a Username and Password");
					}
					else{
						JOptionPane.showMessageDialog(null,"User already exists! Please try again.");
					}
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					loginSys();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					loginSys();
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
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

		/*Content in loginPanel */

		JButton teamButton = new JButton("Team");
		teamButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton graphicsButton = new JButton("Graphics");
		graphicsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton profileButton = new JButton("Profile");
		profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton workoutsButton = new JButton("Workouts");
		workoutsButton.setAlignmentX(Component.CENTER_ALIGNMENT);


		/* Add everything in and display */
		menuPanel.add(teamButton);
		menuPanel.add(graphicsButton);
		menuPanel.add(profileButton);
		menuPanel.add(workoutsButton);


		parent.getContentPane().add(menuPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);

		/* Action Listeners */
		teamButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamMenu();
				parent.dispose();
			}
		});

		graphicsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(Session.getNumCoachWorkouts() < 2) {
						JOptionPane.showMessageDialog(null,"You need at least 2 logged team workouts to generate a Graphic.");
						parent.dispose();
						coachMainMenu();
					}
					else {
						coachCreateGraphicPage();
						parent.dispose();
					}
				} catch (HeadlessException e3) {
					JOptionPane.showMessageDialog(null,"Error");
					loginSys();
				} catch (ClassNotFoundException e3) {
					JOptionPane.showMessageDialog(null,"Error");
					loginSys();
				} catch (SQLException e3) {
					JOptionPane.showMessageDialog(null,"Error");
					loginSys();
				}
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

	public static void coachCreateGraphicPage() {
		JFrame parent = new JFrame("Create Team Graphic:");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

		Box formBox = Box.createVerticalBox();

		/*Content in loginPanel */
		JLabel paramLabel = new JLabel("Create a new graphic based on: ");
		String[] params = {"DISTANCE", "SPLIT", "TIME", "AVGWATTS", "SPM"};
		SpinnerListModel strModel1 = new SpinnerListModel(params);
		JSpinner paramField = new JSpinner(strModel1);
		JPanel paramPanel = new JPanel();
		paramPanel.add(paramLabel);
		paramPanel.add(paramField);
		paramPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		//Get only specific types of results, or get all results
		JLabel tagLabel = new JLabel("Filter workout type:");
		String[] tags = {"All workout types" , "UT2", "UT1", "AT", "TR", "AN"};
		SpinnerListModel strModel2 = new SpinnerListModel(tags);
		JSpinner tagField = new JSpinner(strModel2);
		JPanel tagPanel = new JPanel();
		tagPanel.add(tagLabel);
		tagPanel.add(tagField);

		JButton enterButton = new JButton("Generate Graphic");
		enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton mainButton = new JButton("Main Menu");
		enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		/* Add stuff to panel */
		formBox.add(paramPanel);
		formBox.add(tagPanel);
		formBox.add(enterButton);
		formBox.add(mainButton);

		parent.getContentPane().add(formBox);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.pack();
		parent.setVisible(true);

		//ActionListeners
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Integer> values = null;
				try {
					values = Session.getCoachYVals((String)paramField.getValue() , (String)tagField.getValue());

					if(values.size() < 2) {
						JOptionPane.showMessageDialog(null,"There needs to be at least 2 workouts with"
								+ " the specified options to create a graphic.");
						parent.dispose();
						coachCreateGraphicPage();
					} 
					else {
						Graphic.createPlot(values, (String) paramField.getValue());
						parent.dispose();
						coachMainMenu();
					}
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					coachMainMenu();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					coachMainMenu();
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

	public static void coachProfileMenu() {
		JFrame parent = new JFrame("Profile Menu");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

		JPanel profilePanel = new JPanel();
		profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));

		//Obtains rower's information in JPanel format
		try {
			profilePanel.add(Session.getCoachProfile());
		} catch (ClassNotFoundException e1) {
			JOptionPane.showMessageDialog(null,"Error");
			coachMainMenu();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null,"Error");
			coachMainMenu();
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
				coachEditProfilePage();
			}
		});

		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.dispose();
				coachMainMenu();
			}
		});
	}

	public static void coachEditProfilePage() {
		JFrame parent = new JFrame("Enter Profile Information");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);
		parent.setLayout(new BorderLayout());

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

		/*Content in loginPanel */
		JLabel nameLabel = new JLabel("Name: ");
		JTextField nameField = new JTextField(25);

		JLabel teamLabel = new JLabel("Team:");
		JPanel teamPanel = new JPanel();
		teamPanel.add(teamLabel);
		JSpinner teamField;

		//get teams
		String[] teams = null;
		final boolean NOTEAMS;

		try {
			teams = Session.getTeams();
		} catch (ClassNotFoundException e2) {
			JOptionPane.showMessageDialog(null,"Error");
			coachMainMenu();
		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null,"Error");
			coachMainMenu();
		}


		//If no teams have been created, set no Teams to true and put in a placeholder option
		if(teams.length < 1) {
			NOTEAMS = true;
			teams = new String[] {"No teams have been created"};
		}
		else {
			NOTEAMS = false;
		}

		SpinnerListModel strModel = new SpinnerListModel(teams);
		teamField = new JSpinner(strModel);
		teamPanel.add(teamField);

		JButton confirmButton = new JButton("Confirm");

		/* Add stuff to panel */
		JPanel namePanel = new JPanel();
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		formPanel.add(namePanel);

		formPanel.add(teamPanel);

		JPanel confirmPanel = new JPanel();
		confirmPanel.add(confirmButton);

		parent.getContentPane().add(formPanel, BorderLayout.CENTER);
		parent.getContentPane().add(confirmPanel, BorderLayout.SOUTH);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.pack();
		parent.setVisible(true);

		//ActionListeners
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					if(!NOTEAMS) {
						//get newTeamID based on selected name
						String newTeamName = (String) teamField.getValue();
						int newTeamID = Session.getTeamIDFromName((String) teamField.getValue());
						
						//get currentCoachID
						int currentCoachID = Session.getCoachID();
						
						//get Coach's Current Team ID
						int oldTeamID = Session.getCoachTeamID();
						String oldTeamName = Session.getTeamName();
						
						//If team doesn't need to be changed, don't do anything
						if(!newTeamName.equals(oldTeamName)) {
							if(oldTeamID == -1 ) {
								//coach doesn't have a team
								if(!Session.changeCoachTeamID(newTeamID)) {
									JOptionPane.showMessageDialog(null,"Error");
								}
								else if(!Session.changeTeamCoachID(newTeamName, currentCoachID)) {
									JOptionPane.showMessageDialog(null,"Error");
								}
							}
							else {
								//coach has a team
								
								//set old team's coachID to -1
								if(!Session.changeTeamCoachID(oldTeamName, -1)) {
									JOptionPane.showMessageDialog(null,"Error");
								}
								else if(!Session.changeCoachTeamID(newTeamID)) {
									JOptionPane.showMessageDialog(null,"Error");
								}
								else if(!Session.changeTeamCoachID(newTeamName, currentCoachID)) {
									JOptionPane.showMessageDialog(null,"Error");
								}
							}
						}
						
					}
					
					
					Session.changeCoachName(nameField.getText());
					
					parent.dispose();
					coachMainMenu();
					
				}
				catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					coachMainMenu();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					coachMainMenu();
				}
			}
		});
	}

	public static void teamMenu() {
		JFrame parent = new JFrame("Team Menu");
		parent.setSize(300,200);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

		Box menuPanel = Box.createVerticalBox();

		/*Content in loginPanel */

		JButton viewButton = new JButton("View Team");
		viewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton createButton = new JButton("Create Team");
		createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton mainButton = new JButton("Main Menu");
		mainButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		/* Add everything in and display */
		menuPanel.add(viewButton);
		menuPanel.add(createButton);
		menuPanel.add(mainButton);

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

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createTeamPage();
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

	public static void viewTeamPage() {
		String name = "";

		try {
			name = Session.getTeamName();
		} catch (ClassNotFoundException e2) {
			JOptionPane.showMessageDialog(null,"Error");
			coachMainMenu();
		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null,"Error");
			coachMainMenu();
		}

		if(name.equals("")) {
			JOptionPane.showMessageDialog(null, "No team added to profile, add one to view roster.");
			teamMenu();
			return;
		}

		JFrame parent = new JFrame(name);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

		Box teamPanel = Box.createVerticalBox();

		/*Content in teamPanel */
		JButton mainButton = new JButton("Main Menu");


		JScrollPane scroller = new JScrollPane();

		/*Display all workouts belonging to that rower */
		try {
			scroller = new JScrollPane(Session.getRoster());
		} catch (ClassNotFoundException e1) {
			JOptionPane.showMessageDialog(null,"Error");
			coachMainMenu();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null,"Error");
			coachMainMenu();
		}

		teamPanel.add(scroller);

		/* Add everything in and display */
		JPanel menuPanel = new JPanel();
		menuPanel.add(mainButton);

		teamPanel.add(menuPanel);

		parent.getContentPane().add(teamPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.pack();
		parent.setVisible(true);

		/* Action Listeners */
		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coachMainMenu();
				parent.dispose();
			}
		});
	}

	public static void createTeamPage() {
		JFrame parent = new JFrame("Create New Team");

		JButton createButton = new JButton("Create");
		JPanel newTeamPanel = new JPanel();

		JTextField nameField = new JTextField(25); //max UN length = 25 char

		JLabel nameLabel = new JLabel("Team Name: ");


		parent.setSize(500,200);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

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
						Session.createTeam(enteredName);
						JOptionPane.showMessageDialog(null,"New team created. Edit your profile to join.");
						parent.dispose();
						teamMenu();
					}
					else {
						JOptionPane.showMessageDialog(null,"Team already exists. Please try again.");
					}
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					coachMainMenu();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					coachMainMenu();
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
		parent.setSize(300,100);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

		Box menuPanel = Box.createVerticalBox();

		JButton viewButton = new JButton("View Workouts");
		viewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton mainButton = new JButton("Main Menu");
		mainButton.setAlignmentX(Component.CENTER_ALIGNMENT);

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
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

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
			JOptionPane.showMessageDialog(null,"Error");
			coachMainMenu();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null,"Error");
			coachMainMenu();
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
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

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
				try {
					if(Session.getNumRowerWorkouts() < 2) {
						JOptionPane.showMessageDialog(null,"Please log at least 2 workouts to create a graphic.");
						parent.dispose();
						rowerMainMenu();
					}
					else {
						rowerCreateGraphicPage();
						parent.dispose();
					}
				} catch (HeadlessException e3) {
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				} catch (ClassNotFoundException e3) {
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				} catch (SQLException e3) {
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				}
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
				parent.dispose();
				rowerWorkoutsMenu();
			}
		});
	}

	public static void rowerCreateGraphicPage() {
		JFrame parent = new JFrame("Create Graphic:");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

		Box formBox = Box.createVerticalBox();

		/*Content in loginPanel */
		JLabel paramLabel = new JLabel("Create a new graphic based on: ");
		String[] params = {"DISTANCE", "SPLIT", "TIME", "AVGWATTS", "SPM"};
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
			JOptionPane.showMessageDialog(null,"Error");
			rowerMainMenu();
		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null,"Error");
			rowerMainMenu();
		}
		JSpinner nField = new JSpinner( new SpinnerNumberModel(
				2, //initial value
				2, //min
				maxVals, //max
				1)); //step
		JPanel nPanel = new JPanel();
		nPanel.add(nLabel);
		nPanel.add(nField);
		nPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton enterButton = new JButton("Generate Graphic");
		enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton mainButton = new JButton("Main Menu");
		mainButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		/* Add stuff to panel */
		formBox.add(paramPanel);
		formBox.add(nPanel);
		formBox.add(enterButton);
		formBox.add(mainButton);

		parent.getContentPane().add(formBox);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.pack();
		parent.setVisible(true);

		//ActionListeners
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Integer> values = null;
				try {
					values = Session.getRowerYVals((String)paramField.getValue() , (Integer)nField.getValue());
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				}

				Graphic.createPlot(values, (String) paramField.getValue());
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
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

		JPanel profilePanel = new JPanel();
		profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));

		//Obtains rower's information in JPanel format
		try {
			profilePanel.add(Session.getRowerProfile());
		} catch (ClassNotFoundException e1) {
			JOptionPane.showMessageDialog(null,"Error");
			rowerMainMenu();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null,"Error");
			rowerMainMenu();
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
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);
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
		JPanel teamPanel = new JPanel();
		teamPanel.add(teamLabel);
		JSpinner teamField;

		//get teams
		String[] teams = null;
		final boolean NOTEAMS;

		try {
			teams = Session.getTeams();
		} catch (ClassNotFoundException e2) {
			JOptionPane.showMessageDialog(null,"Error");
			rowerMainMenu();
		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null,"Error");
			rowerMainMenu();
		}


		//If no teams have been created, set no Teams to true and put in a placeholder option
		if(teams.length < 1) {
			NOTEAMS = true;
			teams = new String[] {"No teams have been created"};
		}
		else {
			NOTEAMS = false;
		}

		SpinnerListModel strModel = new SpinnerListModel(teams);
		teamField = new JSpinner(strModel);
		teamPanel.add(teamField);



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

		formPanel.add(teamPanel);

		JPanel confirmPanel = new JPanel();
		confirmPanel.add(confirmButton);

		parent.getContentPane().add(formPanel, BorderLayout.CENTER);
		parent.getContentPane().add(confirmPanel, BorderLayout.SOUTH);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.pack();
		parent.setVisible(true);

		//ActionListeners
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String teamValue = "";
					if(!NOTEAMS) {
						teamValue = (String) teamField.getValue();
					}

					if(Session.editRowerProfile(
							(String) nameField.getText(),
							(Integer) ageField.getValue(),
							(Integer) heightField.getValue(),
							(Integer) weightField.getValue(),
							teamValue)){

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
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				}
			}
		});
	}

	public static void rowerWorkoutsMenu() {
		JFrame parent = new JFrame("Workouts Menu");
		parent.setPreferredSize(new Dimension(300, 200));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

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
				try {
					if(Session.getNumRowerWorkouts() < 1) {
						JOptionPane.showMessageDialog(null,"You have no workouts logged.");
						parent.dispose();
						rowerWorkoutsMenu();
					}
					else {
						editWorkoutSelectPage();
						parent.dispose();
					}
				} catch (HeadlessException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				}
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
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

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

		//ActionListeners
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
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}
		});
	}

	public static void editWorkoutSelectPage() {
		JFrame parent = new JFrame("Edit Workout");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

		Box formPanel = Box.createVerticalBox();

		/*Content in loginPanel */
		JLabel optionsLabel = new JLabel("Choose the workout you would like to edit:");
		optionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		String[][] optionsAndIDs = null;

		try {
			optionsAndIDs = Session.getWorkoutOptions();
		} catch (ClassNotFoundException e2) {
			JOptionPane.showMessageDialog(null,"Error");
			rowerMainMenu();
		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null,"Error");
			rowerMainMenu();;
		}

		String[] options = new String[optionsAndIDs.length];
		String[] ids = new String[optionsAndIDs.length];

		for(int i = 0; i < optionsAndIDs.length; i++) {
			options[i] = optionsAndIDs[i][0];
			ids[i] = optionsAndIDs[i][1];
		}



		SpinnerListModel strModel = new SpinnerListModel(options);
		JSpinner optionsField = new JSpinner(strModel);
		formPanel.add(optionsLabel);
		formPanel.add(optionsField);

		JButton confirmButton = new JButton("Confirm");
		JButton mainButton = new JButton("Main Menu");

		JPanel enterPanel = new JPanel();
		enterPanel.add(confirmButton);
		enterPanel.add(mainButton);
		formPanel.add(enterPanel);

		parent.getContentPane().add(formPanel);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.pack();
		parent.setVisible(true);

		//Action Listeners
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int workoutID = -1;
				for(int i = 0; i < options.length; i++) {
					if(options[i].equals(optionsField.getValue())) {
						workoutID = Integer.parseInt(ids[i]);
					}
				}
				parent.dispose();
				editWorkoutPage(workoutID);
			}
		});

		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.dispose();
				rowerMainMenu();
			}
		});
	}

	public static void editWorkoutPage(int workoutID) {
		JFrame parent = new JFrame("Enter Workout Information");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);
		Box formPanel = Box.createVerticalBox();

		/*Content in workout form */
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

		//Action Listeners
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String newDateStr = format.format(dateField.getValue());

					if(Session.editWorkout(new Workout(
							(Integer) distanceField.getValue(),
							(Integer) timeField.getValue(),
							(Integer) splitField.getValue(),
							(Integer) wattsField.getValue(),
							(Integer) spmField.getValue(),
							newDateStr,
							(String) tagField.getValue()), workoutID)){

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
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				}


			}
		});
	}

	public static void viewRowerWorkoutsPage() {
		JFrame parent = new JFrame("Workouts");
		parent.setSize(500, 500);;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		parent.setLocation(dim.width/2-parent.getSize().width/2, dim.height/2-parent.getSize().height/2);

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
			JOptionPane.showMessageDialog(null,"Error");
			rowerMainMenu();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null,"Error");
			rowerMainMenu();
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
				try {
					if(Session.getNumRowerWorkouts() < 1) {
						JOptionPane.showMessageDialog(null,"You have no workouts logged.");
						parent.dispose();
						rowerWorkoutsMenu();
					}
					else {
						editWorkoutSelectPage();
						parent.dispose();
					}
				} catch (HeadlessException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"Error");
					rowerMainMenu();
				}
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