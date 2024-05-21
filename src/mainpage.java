import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.Timer;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class mainpage {

	public static JFrame frame;
	
	static String customerName = "";
	static String customerAddress = "";
	JLabel lblNewLabel;
	JLabel lblNewLabel_5;
	JLabel lblNewLabel_6;
	
	
	
	static customerpage customer = new customerpage();
	//static JLabel lblNewLabel = new JLabel("Hi " + customerpage.customername);//customerpage.customername
	//static JLabel lblNewLabel_6 = new JLabel("Name: " + customerpage.customername);
	//static JLabel lblNewLabel_5 = new JLabel("Address: " + customerpage.customeraddress);//address

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainpage window = new mainpage();
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
	public mainpage() {
		initialize();
		Database.connect();
		setupClosingDBConnection();
	}
	
	public void signouting() {
		try {
			customerpage customer = new customerpage();
			Connection connection = Database.connection;
			String query = "SELECT cust_fullname,cust_address FROM customers WHERE customerID = " + customer.customerID;
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet result = stm.executeQuery(query);
			while(result.next()) {
				customerName = result.getString("cust_fullname");
				customerAddress = result.getString("cust_address");
			}
			lblNewLabel = new JLabel("Hi " + customerName);//customerpage.customername
			lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
			lblNewLabel_6 = new JLabel("Name: " + customerName);
			lblNewLabel_5 = new JLabel("Address: " + customerAddress);//address
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		

	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 602, 488);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Main Menu Page");
		
		JButton btnNewButton_1 = new JButton("   Request a New Bank Account   ");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to create a new account?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    // Code for creating a new account 
                	newaccountcall();
                }
                else {
                	initialize();
                }
			}
		});
		btnNewButton_1.setBounds(39, 23, 532, 45);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Deposit");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				depositcall();
			}
		});
		btnNewButton_2.setBounds(454, 251, 137, 28);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Withdraw");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				withdrawcall();
			}
		});
		btnNewButton_3.setBounds(454, 291, 137, 28);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Update information");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatecall();
			}
		});
		btnNewButton_4.setBounds(24, 261, 142, 37);
		frame.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Book an Online Appointment");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appointmentscall();
			}
		});
		btnNewButton_5.setBounds(86, 401, 212, 37);
		frame.getContentPane().add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("Click here for Messages");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messagecall();
			}
		});
		btnNewButton_6.setBounds(378, 403, 167, 37);
		frame.getContentPane().add(btnNewButton_6);
		
		signouting();
		
		lblNewLabel.setBounds(234, 91, 151, 29);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Any Questions ?");
		lblNewLabel_1.setBounds(135, 381, 117, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("View Messages :");
		lblNewLabel_2.setBounds(408, 381, 102, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Total Balance Amount: ");
		lblNewLabel_3.setBounds(444, 158, 152, 16);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Profile Information: ");
		lblNewLabel_4.setBounds(24, 158, 136, 16);
		frame.getContentPane().add(lblNewLabel_4);
		
		lblNewLabel_5.setBounds(24, 231, 342, 16);
		frame.getContentPane().add(lblNewLabel_5);
		
		lblNewLabel_6.setBounds(24, 196, 167, 16);
		frame.getContentPane().add(lblNewLabel_6);
		
		
		
		
		float amountlabel= 0;
		try {
			Connection connection = Database.connection;
			String query = "SELECT balance FROM bankaccount where customerID = " + customer.customerID;
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				amountlabel += rs.getFloat("balance");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		JLabel lblNewLabel_7 = new JLabel(String.valueOf(amountlabel));
		lblNewLabel_7.setBounds(493, 186, 87, 16);
		frame.getContentPane().add(lblNewLabel_7);
		
		JButton btnNewButton = new JButton("Account Balances");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				balancecall();
			}
		});
		btnNewButton.setBounds(454, 211, 137, 28);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_7 = new JButton("Sign out");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to sign out?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                	signoutcall();
                }
                else {
                	initialize();
                }
			}
		});
		btnNewButton_7.setBounds(249, 121, 117, 29);
		frame.getContentPane().add(btnNewButton_7);
	}
	
	public static void setupClosingDBConnection() {
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
	}
	
	public static void depositcall() {
		frame.dispose();
		deposit d1 = new deposit();
		d1.initialize();
		d1.frame.setVisible(true);
	}
	
	public static void withdrawcall() {
		frame.dispose();
		withdraw w1 = new withdraw();
		w1.initialize();
		w1.frame.setVisible(true);
	}
	
	public static void updatecall() {
		frame.dispose();
		update u1 = new update();
		u1.initialize();

		u1.frame.setVisible(true);
	}
	
	public static void messagecall() {
		frame.dispose();
		message m1 = new message();
		m1.initialize();
		m1.frame.setVisible(true);
	}
	
	public static void balancecall() {
		frame.dispose();
		individual_balance b1 = new individual_balance();
		b1.initialize();
		b1.frame.setVisible(true);
	}
	
	public static void newaccountcall() {
		frame.dispose();
		newacc newaccount = new newacc();
    	newaccount.initialize();
    	newaccount.frame.setVisible(true);
	}
	
	public static void signoutcall() {
		frame.dispose();
		customer.initialize();
		customer.frame.setVisible(true);
	}
	
	public static void appointmentscall() {
		frame.dispose();
		appointments appointment = new appointments();
		appointment.initialize();
		appointment.frame.setVisible(true);
	}
	
}
