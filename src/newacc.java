import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class newacc {

	public static JFrame frame;
	public static JTextField textField;
	public JButton btnNewButton;
	static customerpage customer = new customerpage();

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
	public newacc() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 232);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New Account Form");
		lblNewLabel.setBounds(157, 23, 130, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Enter starting balance: 				$");
		lblNewLabel_1.setBounds(16, 86, 183, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(211, 81, 220, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Done");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				create();
			    JOptionPane.showMessageDialog(frame, "Account Creation Request Sent! Please Check Back Later");
				menu();
			}
		});
		btnNewButton.setBounds(157, 142, 117, 29);
		frame.getContentPane().add(btnNewButton);
	}
	
	public static void create() {
		try {
			Connection connection = Database.connection;
			String query = "INSERT INTO reqBankAcc (startingbalance, customerID) VALUES (?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setFloat(1, Float.parseFloat(textField.getText())); 
			stm.setInt(2, customer.customerID); 
			stm.executeUpdate();
		} catch (Exception ee) {
			System.out.println(ee);
		}
	}
	
	public static void menu() {
		frame.dispose();
		mainpage m1 = new mainpage();
		m1.initialize();
		m1.frame.setVisible(true);
	}

}
