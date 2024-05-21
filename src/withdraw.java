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
import javax.swing.JComboBox;

public class withdraw {

	public static JFrame frame;
	private JTextField textField;
	private JComboBox<String> comboBox;
	customerpage customer = new customerpage();
	float balance =0;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					withdraw window = new withdraw();
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
	public withdraw() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 275);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Withdraw Amount");
		lblNewLabel.setBounds(155, 29, 123, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Enter amount to withdraw: $");
		lblNewLabel_1.setBounds(18, 143, 180, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(205, 138, 224, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Withdraw");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				withdraw();
			}
		});
		btnNewButton.setBounds(237, 198, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Select Account:");
		lblNewLabel_2.setBounds(18, 91, 180, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(205, 87, 224, 27);
		frame.getContentPane().add(comboBox);
		
		JButton btnNewButton_1 = new JButton("Back to menu");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu();
			}
		});
		btnNewButton_1.setBounds(83, 198, 117, 29);
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
				balance = rs.getFloat("balance");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void withdraw() {
		try {
			Connection connection = Database.connection;
			String selectedAccount = comboBox.getSelectedItem().toString();
			float withdrawAmount = Float.parseFloat(textField.getText());
			String query = "UPDATE bankaccount SET balance = balance - ? WHERE accountNumber = ? AND customerID = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setFloat(1, withdrawAmount);
			stm.setString(2, selectedAccount);
			stm.setInt(3, customer.customerID);
			if (balance>=withdrawAmount) {
			stm.executeUpdate();
		    JOptionPane.showMessageDialog(frame, "Successfully withdrawn !");}
			else {
			    JOptionPane.showMessageDialog(frame, "Error: Overdraft amount !");}
			
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
