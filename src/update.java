import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class update {

	public static JFrame frame;
	private static JTextField textField;
	private static JTextField textField_1;
	static mainpage main = new mainpage();
	public static String newName  = main.lblNewLabel_6.getText();
	public static String newAddress = main.lblNewLabel_5.getText();
	public static String customerName = "";
	public static String customerAddress = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					update window = new update();
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
	public update() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Update Profile Information");
		lblNewLabel.setBounds(135, 24, 174, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Name: ");
		lblNewLabel_1.setBounds(43, 85, 66, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Address: ");
		lblNewLabel_2.setBounds(43, 133, 61, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(153, 80, 238, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(153, 128, 238, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		customerpage customer = new customerpage();
		
		try {
			Connection connection = Database.connection;
			String query = "SELECT * FROM customers where customerID = " + customer.customerID;
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				customerName = rs.getString("cust_fullname");
				customerAddress = rs.getString("cust_address");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		textField.setText(customerName);
		textField_1.setText(customerAddress);
		
		JButton btnNewButton = new JButton("Update");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to update your information?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    // Code for creating a new account 
                	update();
                }
                
			}});
		btnNewButton.setBounds(235, 198, 105, 26);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Back to menu");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu();
			}
		});
		btnNewButton_1.setBounds(94, 197, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		
		
	}
	
	public static void update() {
		try {
		Connection connection = Database.connection;
		customerpage customer = new customerpage();
		String query = "UPDATE customers SET cust_fullname = ?, cust_address = ? WHERE customerID = ?";
		PreparedStatement stm = connection.prepareStatement(query);
		newName = textField.getText();
		newAddress = textField_1.getText();
	

		stm.setString(1, textField.getText());
		stm.setString(2, textField_1.getText());
		stm.setInt(3, customer.customerID);
		stm.executeUpdate();
	    JOptionPane.showMessageDialog(frame, "Successfully updated information !");

		
		
		frame.dispose();
		main.lblNewLabel.setText("Hi " + newName);//customerpage.customername
		main.lblNewLabel_6.setText("Name: " + newName);
		main.lblNewLabel_5.setText("Address: " + newAddress);
		main.initialize();
		main.frame.setVisible(true);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static void menu() {
		frame.dispose();
		mainpage m1 = new mainpage();
		m1.initialize();
		m1.frame.setVisible(true);
	}

}
