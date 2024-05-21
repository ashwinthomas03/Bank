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
import javax.swing.JOptionPane;

public class accReq {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					accReq window = new accReq();
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
	public JTable requestTable;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	public String requestID;
	public String balance;
	public String customerID;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;


	public accReq() { initialize(); }

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
	    requestTable = new JTable();
		scrollPane.setViewportView(requestTable);
		
		requestTable.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            int row = requestTable.rowAtPoint(e.getPoint());
	            if (row >= 0) {
	            	requestID = requestTable.getValueAt(row, 0).toString();
	                balance = requestTable.getValueAt(row, 1).toString();
	                customerID = requestTable.getValueAt(row, 2).toString();
	                System.out.println("Selected balance: " + balance);
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
		btnNewButton.setBounds(42, 400, 144, 29);
		frame.getContentPane().add(btnNewButton);
		
		lblNewLabel = new JLabel("Bank Account Information");
		lblNewLabel.setBounds(244, 19, 228, 16);
		frame.getContentPane().add(lblNewLabel);
		
		btnNewButton_1 = new JButton("Accept");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accept();
				
			}
		});
		btnNewButton_1.setBounds(355, 400, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Decline");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		btnNewButton_2.setBounds(495, 400, 117, 29);
		frame.getContentPane().add(btnNewButton_2);
		
		
	}
	
	// Performs a 'SELECT' query and populates the Table in the frame with the resulting data
	public void populateTable() {
		try {
			Connection connection = Database.connection;
			String query = "SELECT reqBankAcc.requestID, reqBankAcc.startingbalance, customers.customerID, customers.cust_fullname FROM reqBankAcc "
					+ "INNER JOIN customers ON reqBankAcc.customerID = customers.customerID";
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet result = stm.executeQuery(query);
			requestTable.setModel(DbUtils.resultSetToTableModel(result));
			
			
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
	
	public void accept() {
		try {
			Connection connection = Database.connection;
			String query = "INSERT INTO bankaccount (balance, customerID) values (?,?)";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setFloat(1, Float.parseFloat(balance)); 
			stm.setInt(2, Integer.parseInt(customerID)); 
			stm.executeUpdate();
			
			String query1 = "DELETE FROM reqBankAcc WHERE requestID = ?";
			PreparedStatement stm1 = connection.prepareStatement(query1);
				 // doctor_id (only question mark)
			stm1.setInt(1, Integer.parseInt(requestID));
			stm1.executeUpdate();
			
			JOptionPane.showMessageDialog(frame, "Request Accepted");
			back();
			
		} 
		catch(NullPointerException t) {
			JOptionPane.showMessageDialog(frame,"Error: Please select a request to accept/decline");
		}
		catch (Exception ee) {
			System.out.println(ee);
		}
	}
	
	public void delete() {
		try {
			Connection connection = Database.connection;
						
			String query1 = "DELETE FROM reqBankAcc WHERE requestID = ?";
			PreparedStatement stm1 = connection.prepareStatement(query1);
				 // doctor_id (only question mark)
			stm1.setInt(1, Integer.parseInt(requestID));
			stm1.executeUpdate();
			
			JOptionPane.showMessageDialog(frame, "Request Declined");
			back();
			
		} 
		catch(NullPointerException t) {
			JOptionPane.showMessageDialog(frame,"Error: Please select a request to accept/decline");
		}
		catch (NumberFormatException n) {
			JOptionPane.showMessageDialog(frame,"Error: Please select a request to accept/decline");
		}
		catch (Exception ee) {
			System.out.println(ee);
		}
	}
}
