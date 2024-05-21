import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class customersignup {

	public JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	customerpage customer = new customerpage();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					customersignup window = new customersignup();
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
	public customersignup() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 431, 458);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(182, 28, 90, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(182, 114, 61, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Full name");
		lblNewLabel_2.setBounds(182, 197, 61, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Address");
		lblNewLabel_3.setBounds(182, 279, 61, 16);
		frame.getContentPane().add(lblNewLabel_3);
		
		textField = new JTextField();
		textField.setBounds(71, 56, 298, 29);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(71, 142, 298, 29);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(71, 225, 298, 29);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(71, 307, 298, 29);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton = new JButton("Sign Up");
		btnNewButton.setBounds(136, 365, 136, 29);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signup();
			    JOptionPane.showMessageDialog(frame, "Account Created Successfully !");
				loginpage();
			}
		});
		
	}
	
	public void signup() {
		try {
			Connection connection = Database.connection;
			String query = "INSERT INTO customers (cust_username, cust_password, cust_fullname, cust_address) VALUES  (?, ?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setString(1, String.valueOf(textField.getText())); 
			stm.setString(2, String.valueOf(textField_1.getText())); 
			stm.setString(3, String.valueOf(textField_2.getText())); 
			stm.setString(4, String.valueOf(textField_3.getText())); 
			stm.executeUpdate();
			
		} catch (Exception ee) {
			System.out.println(ee);
		}
	}
	
	public void loginpage() {
		frame.dispose();
		customerpage login = new customerpage();
		login.initialize();
		login.frame.setVisible(true);
	}

}
