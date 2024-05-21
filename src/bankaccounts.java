import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;

public class bankaccounts {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bankaccounts window = new bankaccounts();
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

	public static JFrame frame;
	public JTable bankTable;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	public String customerID;


	public bankaccounts() { initialize(); }

	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 636, 481);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Database.connect();
		//setupClosingDBConnection();
		
		createScrollPaneAndTable();
		populateTable();
		
		
	}
	
	
	
	public void createScrollPaneAndTable() {
		// Create Scroll Pane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 58, 555, 330);
		frame.getContentPane().add(scrollPane);
		
		// Create Table Inside of Scroll Pane
	    bankTable = new JTable();
		scrollPane.setViewportView(bankTable);
		
	    
	    // Add other components to frame
	    // ...		
		btnNewButton = new JButton("Back to Menu");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		btnNewButton.setBounds(256, 400, 144, 29);
		frame.getContentPane().add(btnNewButton);
		
		lblNewLabel = new JLabel("Bank Account Information");
		lblNewLabel.setBounds(244, 19, 228, 16);
		frame.getContentPane().add(lblNewLabel);
		
		
	}
	
	// Performs a 'SELECT' query and populates the Table in the frame with the resulting data
	public void populateTable() {
		try {
			Connection connection = Database.connection;
			String query = "SELECT bankaccount.accountNumber, bankaccount.balance, bankaccount.customerID, "
					+ "customers.cust_fullname FROM bankaccount INNER JOIN customers ON bankaccount.customerID = customers.customerID "
					+ "ORDER BY customers.cust_fullname DESC";
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet result = stm.executeQuery(query);
			bankTable.setModel(DbUtils.resultSetToTableModel(result));
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	

	
	public void back() {
		frame.dispose();
		Mainemployee mainpage = new Mainemployee();
		mainpage.initialize();
		mainpage.frame.setVisible(true);
		
	}
	
	
}
