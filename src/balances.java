import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class balances {

	public JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnNewButton;
	Scoll2 customer = new Scoll2();
	private static String customerID;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					balances window = new balances(customerID);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param  
	 */
	public balances(String customerID) {
		initialize(customerID);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(String customerID) {

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 312);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Customer Holdings");
		lblNewLabel.setBounds(156, 26, 131, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Total number of accounts: ");
		lblNewLabel_1.setBounds(26, 99, 182, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Sum of balances: $");
		lblNewLabel_2.setBounds(71, 180, 131, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(203, 94, 217, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setEditable(false);
		
		textField_1 = new JTextField();
		textField_1.setBounds(203, 175, 217, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		
		btnNewButton = new JButton("Back to customer page");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backcall();
			}
		});
		btnNewButton.setBounds(139, 233, 170, 34);
		frame.getContentPane().add(btnNewButton);
		
		holdings(customerID);

	}

	
	public void holdings(String customerID) {
		try {
			//int index = customer.messagesTable.getSelectedRow();
			//Object selectedvalue = customer.messagesTable.getValueAt(index, 0);
			Connection connection = Database.connection;
			String query = "SELECT COUNT(*), SUM(balance) FROM bankaccount where customerID = " + customerID ;
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				int count = rs.getInt("COUNT(*)");
				float total_balance = rs.getFloat("SUM(balance)");
				textField.setText(String.valueOf(count));
				textField_1.setText(String.valueOf(total_balance));
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	public void backcall() {
		frame.dispose();
		Scoll2 call = new Scoll2();
		call.initialize();
		call.frame.setVisible(true);
	}

}
