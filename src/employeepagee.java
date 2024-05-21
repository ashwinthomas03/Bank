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

public class employeepagee {

	public static JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	static String name;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					employeepagee window = new employeepagee();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public employeepagee() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		
		Database.connect();
		setupClosingDBConnection();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 341);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Employee Username");
		lblNewLabel.setBounds(158, 39, 135, 16);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(126, 67, 211, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Employee password");
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
		
		lblNewLabel_2 = new JLabel("Choose a different role ?");
		lblNewLabel_2.setBounds(46, 279, 163, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		btnNewButton_1 = new JButton("Click here");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				homescreen();
			}
		});
		btnNewButton_1.setBounds(220, 274, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
		
	}
	
	public static void setupClosingDBConnection() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				try {
					Database.connection.close();
					System.out.println("Application Closed - DB Connection Closed");
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}, "Shutdown-thread"));
	}
	
	
	public void login() {
		try {
			Connection connection = Database.connection;
			String query = "SELECT * FROM employee where emp_username = '" + textField.getText() + "'";
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet result = stm.executeQuery(query);
			while (result.next()) {
				String enteredPassword = textField_1.getText();
				String actualPasswordFromDB = result.getString("emp_password");
				name = result.getString("emp_fullname");
				
				if (enteredPassword.equals(actualPasswordFromDB)) {
					Login();
				} else {
	                int option = JOptionPane.showOptionDialog(frame, "Incorrect password or username", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"OK"} , null);
					
				}
			}
			if (!result.first()) {
                int option = JOptionPane.showOptionDialog(frame, "Incorrect password or username", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"OK"} , null);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void Login() {
		frame.dispose();
		Mainemployee login = new Mainemployee();
		login.initialize();
		login.frame.setVisible(true);
	}
	
	public static void homescreen() {
		frame.dispose();
		project p = new project();
		p.initialize();
		p.frame.setVisible(true);
	}
	
}




