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
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class AdminLogin {

	public static JFrame frame;
	private static JTextField textField;
	private static JPasswordField passwordField;
	static String adminusername;
	private static JLabel lblNewLabel_2;
	private static JButton btnNewButton_1;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminLogin window = new AdminLogin();
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
	public AdminLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public static void display() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 309);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Admin Username");
		lblNewLabel.setBounds(166, 40, 135, 16);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(126, 67, 211, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Admin Password");
		lblNewLabel_1.setBounds(174, 105, 163, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(126, 131, 211, 26);
		frame.getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btnNewButton.setBounds(166, 180, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		lblNewLabel_2 = new JLabel("Choose a different role ?");
		lblNewLabel_2.setBounds(60, 247, 163, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		btnNewButton_1 = new JButton("Click here");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				homescreen();
			}
		});
		btnNewButton_1.setBounds(222, 242, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
		
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
	
	
	public static void mainp() {
		frame.dispose();
		AdminMainPage m = new AdminMainPage();
		m.initialize();
		m.frame.setVisible(true);
	}
	
	
	public static void login() {
		//frame.dispose();
		try {
			Connection connection = Database.connection;
			String query = "SELECT * FROM admin where admin_username = '" + textField.getText() + "'";
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet result = stm.executeQuery(query);
			while (result.next()) {
				String enteredPassword = new String(passwordField.getPassword());
				String actualPasswordFromDB = result.getString("admin_password");
				adminusername = result.getString("admin_username");
				
				if (enteredPassword.equals(actualPasswordFromDB)) {
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
	