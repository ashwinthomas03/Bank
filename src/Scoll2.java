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

public class Scoll2 {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Scoll2 window = new Scoll2();
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
	private JButton btnNewButton_1;
	public String customerID;


	public Scoll2() { initialize(); }

	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 687, 481);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Database.connect();
		//setupClosingDBConnection();
		
		createScrollPaneAndTable();
		populateTable();
		
		
	}
	
	/*public static void setupClosingDBConnection() {
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
	}*/
	
	public void createScrollPaneAndTable() {
		// Create Scroll Pane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 58, 616, 330);
		frame.getContentPane().add(scrollPane);
		
		// Create Table Inside of Scroll Pane
	    messagesTable = new JTable();
		scrollPane.setViewportView(messagesTable);
		
		messagesTable.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            int row = messagesTable.rowAtPoint(e.getPoint());
	            if (row >= 0) {
	                customerID = messagesTable.getValueAt(row, 0).toString();
	                System.out.println("Selected Customer ID: " + customerID);
	            }
	        }
	    });
	    
	    // Add other components to frame
	    // ...		
		btnNewButton = new JButton("Back to Menu");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		btnNewButton.setBounds(146, 400, 144, 29);
		frame.getContentPane().add(btnNewButton);
		
		lblNewLabel = new JLabel("Customer Information");
		lblNewLabel.setBounds(244, 19, 156, 16);
		frame.getContentPane().add(lblNewLabel);
		
		btnNewButton_1 = new JButton("View Account info");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				balancecall();
				
			}
		});
		btnNewButton_1.setBounds(345, 400, 176, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		
	}
	
	// Performs a 'SELECT' query and populates the Table in the frame with the resulting data
	public void populateTable() {
		try {
			Connection connection = Database.connection;
			String query = "SELECT customerID, cust_fullname,cust_address FROM customers";
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet result = stm.executeQuery(query);
			messagesTable.setModel(DbUtils.resultSetToTableModel(result));
			
			
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
	
	public void balancecall() {
		frame.dispose();
		balances balance = new balances(customerID);
		balance.initialize(customerID);
		balance.frame.setVisible(true);
	}
	
	
}
