import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;

public class message {


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Scoll window = new Scoll();
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
	public JTable messagesTable;
	private JButton btnNewButton;
	private JLabel lblNewLabel;

	public message() { initialize(); }

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
	    messagesTable = new JTable();
		scrollPane.setViewportView(messagesTable);
		
		btnNewButton = new JButton("Back to Menu");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu();
			}
		});
		btnNewButton.setBounds(255, 400, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		lblNewLabel = new JLabel("Messages");
		lblNewLabel.setBounds(281, 19, 61, 16);
		frame.getContentPane().add(lblNewLabel);
	}
	
	// Performs a 'SELECT' query and populates the Table in the frame with the resulting data
	public void populateTable() {
		try {
			customerpage customer = new customerpage();
			Connection connection = Database.connection;
			String query = "SELECT * FROM messages WHERE customerID = " + customer.customerID;
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet result = stm.executeQuery(query);
			messagesTable.setModel(DbUtils.resultSetToTableModel(result));
		} catch (Exception e) {
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
