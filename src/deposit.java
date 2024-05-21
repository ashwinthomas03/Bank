import java.awt.EventQueue;
import java.sql.*;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class deposit {

	public static JFrame frame;
	private JTextField textField;
	private JComboBox<String> comboBox;
	customerpage customer = new customerpage();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					deposit window = new deposit();
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
	public deposit() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		Database.connect();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 277);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Deposit Amount");
		lblNewLabel.setBounds(178, 30, 129, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Enter amount to deposit: $");
		lblNewLabel_1.setBounds(27, 138, 187, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(209, 133, 225, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Deposit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deposit();
			}
		});
		btnNewButton.setBounds(238, 194, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Select Account:");
		lblNewLabel_2.setBounds(27, 83, 115, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(209, 79, 225, 27);
		frame.getContentPane().add(comboBox);
		
		JButton btnNewButton_1 = new JButton("Back to Menu");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu();
			}
		});
		btnNewButton_1.setBounds(83, 194, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		
		loadAccounts();
		
	}
	
	public void loadAccounts() {
		try {
			Connection connection = Database.connection;
			String query = "SELECT * FROM bankaccount where customerID = " + customer.customerID;
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				comboBox.addItem(rs.getString("accountNumber"));
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void deposit() {
		try {
			Connection connection = Database.connection;
			String selectedAccount = comboBox.getSelectedItem().toString();
			float depositAmount = Float.parseFloat(textField.getText());
			String query = "UPDATE bankaccount SET balance = balance + ? WHERE accountNumber = ? AND customerID = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setFloat(1, depositAmount);
			stm.setString(2, selectedAccount);
			stm.setInt(3, customer.customerID);
			stm.executeUpdate();
		    JOptionPane.showMessageDialog(frame, "Successfully deposited !");

			frame.dispose();
			mainpage main = new mainpage();
			main.initialize();
			main.frame.setVisible(true);
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Error: Enter valid numbers !");
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