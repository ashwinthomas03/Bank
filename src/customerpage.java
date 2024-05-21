import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;

public class customerpage {

	public static JFrame frame;
	private static JTextField textField;
	private static JTextField textField_1;
	static String customername;
	static String customeraddress;
	static float balance;
	static int customerID;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					customerpage window = new customerpage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public customerpage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public static void display() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 363);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Customer Login");

		
		JLabel lblNewLabel = new JLabel("Customer Username");
		lblNewLabel.setBounds(158, 39, 135, 16);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(126, 67, 211, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Customer password");
		lblNewLabel_1.setBounds(158, 133, 163, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_1 = new JPasswordField();
		textField_1.setBounds(126, 161, 211, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btnNewButton.setBounds(171, 211, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Dont have an account ?");
		lblNewLabel_2.setBounds(52, 266, 163, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton_1 = new JButton("Sign up");
		btnNewButton_1.setBounds(211, 261, 93, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel_3 = new JLabel("Choose a different role ?");
		lblNewLabel_3.setBounds(52, 299, 163, 16);
		frame.getContentPane().add(lblNewLabel_3);
		
		JButton btnNewButton_2 = new JButton("Click here");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				homescreen();
			}
		});
		btnNewButton_2.setBounds(211, 294, 117, 29);
		frame.getContentPane().add(btnNewButton_2);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signup();
			}
		});
	}
	
	public static void initialize() {
		
		Database.connect();
		setupClosingDBConnection();
		
		display();
		
	}
	
	
	
	
	
	public static void setupClosingDBConnection() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				try {
					Database.connection.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}, "Shutdown-thread"));
	}
	
	public static void signup() {
		frame.dispose();
		customersignup signup = new customersignup();
		signup.initialize();
		signup.frame.setVisible(true);
	}
	
	public static void mainp() {
		frame.dispose();
		mainpage m = new mainpage();
		m.initialize();
		m.frame.setVisible(true);
	}
	
	
	public static void login() {
		//frame.dispose();
		try {
			Connection connection = Database.connection;
			String query = "SELECT * FROM customers where cust_username = '" + textField.getText() + "'";
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet result = stm.executeQuery(query);
			
			while (result.next()) {
				String enteredPassword = textField_1.getText();
				String actualPasswordFromDB = result.getString("cust_password");
				String enteredUsername = textField.getText();
				String actualUsernameFromDB = result.getString("cust_username");
				customerID = result.getInt("customerID");
				
				String query1 = "SELECT * FROM bankaccount where customerID =  '" + String.valueOf(customerID) + " ' ";
				PreparedStatement stm1 = connection.prepareStatement(query1);
				ResultSet result1 = stm1.executeQuery(query1);
				
				
				if (enteredUsername.equals(actualUsernameFromDB) && enteredPassword.equals(actualPasswordFromDB)) {
					customername = result.getString("cust_fullname");
					customeraddress = result.getString("cust_address");
					//customerID = result.getInt("customerID");
					if (!result1.next()) {
					    //JOptionPane.showMessageDialog(frame, "No bank account exist, Please request for an account in the main menu page !");
					} else {
					    do {
					        balance += result1.getFloat("balance");
					    } while (result1.next());
					}
					mainp();	
				}
				
				else {
	                int option = JOptionPane.showOptionDialog(frame, "Incorrect password or username", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"OK"} , null);
				}
			}
			
			if (!result.first()) {
                int option = JOptionPane.showOptionDialog(frame, "Incorrect password or username", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"OK"} , null);
			}
		
		} 
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void homescreen() {
		frame.dispose();
		project p = new project();
		p.initialize();
		p.frame.setVisible(true);
	}
	
}
