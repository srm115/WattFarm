package WattFarmSource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



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
		parent.setSize(300,200);
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
        
        
        /* Add everything in and display */
        loginPanel.add(usernameField);
        loginPanel.add(passwordField);
        loginPanel.add(option1);
        loginPanel.add(option2);
        loginPanel.add(loginButton);
        loginPanel.add(usernameLabel);
        loginPanel.add(passwordLabel);
        
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
                
                int loginID;
                /*Check if valid Login*/
                if( (Profiles.RowerLogin(enteredUN, enteredPW) > 0) && type == 'R' ){
                	//Login and begin a rower session
                	loginID = Profiles.RowerLogin(enteredUN, enteredPW);
                	Session.setRower(Profiles.getRowerFromDB(loginID));
                	Session.setTypeOfSession('R');
                	parent.dispose();
                	rowerMainMenu();
                }
                else if( (Profiles.CoachLogin(enteredUN, enteredPW) > 0) && type == 'C' ){
                	loginID = Profiles.CoachLogin(enteredUN, enteredPW);
                	Session.setCoach(Profiles.getCoachFromDB(loginID));
                	Session.setTypeOfSession('C');
                	parent.dispose();
                	coachMainMenu();
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
            }
        });
	}

	
	public static void rowerMainMenu() {
		System.out.print("New Rower Session created!");
	}
	
	public static void coachMainMenu() {
		System.out.print("New Coach Session created!");
	}
}