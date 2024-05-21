import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;


public class appointments {

	public JFrame frame;
	private JComboBox<String> comboBox;
	private JComboBox<String> comboBox_1;
	static int appointmentID = 0;
	customerpage customer = new customerpage();
	static int newappt=0;
	static String appointmentTime;


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
	 * @wbp.parser.entryPoint
	 */
	public appointments() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		Database.connect();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 538, 321);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Schedule an Appointment");
		lblNewLabel.setBounds(174, 22, 185, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Select Appointment Date");
		lblNewLabel_1.setBounds(55, 88, 176, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Select Appointment Time");
		lblNewLabel_2.setBounds(55, 154, 176, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(243, 84, 213, 27);
		frame.getContentPane().add(comboBox);
		loadDate();
		comboBox.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selectedDate = comboBox.getSelectedItem().toString();
		        loadTime(selectedDate); // Call the loadTime() method with the selected date as a parameter
		    }
		});
		
		comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(243, 150, 213, 27);
		frame.getContentPane().add(comboBox_1);
		
		JButton btnNewButton = new JButton("Schedule");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to book an appointment?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    // Code for creating a new account 
                	insert_appt();
                }
			}
		});
		btnNewButton.setBounds(294, 209, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Back to Menu");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu();
			}
		});
		btnNewButton_1.setBounds(125, 209, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
	}
	
	public void loadDate() {
		try {
			Connection connection = Database.connection;
			String query = "SELECT * FROM appointments where available =" + 1;
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet rs = stm.executeQuery();
	        while(rs.next()) {
	            String date = rs.getString("date");
	            if (!isDateAlreadyAdded(date)) { 
	                comboBox.addItem(date);
	            }
	        }
		}
	        catch(Exception e) {
	            System.out.println(e);
	        }
	}
	
	private boolean isDateAlreadyAdded(String date) {
	    for (int i = 0; i < comboBox.getItemCount(); i++) {
	        String item = comboBox.getItemAt(i).toString();
	        if (item.equals(date)) {
	            return true; // If date already exists in combobox, return true
	        }
	    }
	    return false; // If date not found in combobox, return false
	}
	
	public void loadTime(String date) {
	    try {
	        Connection connection = Database.connection;
	        String query = "SELECT * FROM appointments WHERE date = ? AND available = ?";
	        PreparedStatement stm = connection.prepareStatement(query);
	        stm.setString(1, date); // Set the date parameter
	        stm.setInt(2, 1);
	        ResultSet rs = stm.executeQuery();
	        comboBox_1.removeAllItems(); // Remove all items from the time combobox
	        while(rs.next()) {
	            String time = rs.getString("time");
	            if (!isTimeAlreadyAdded(time)) { 
	                comboBox_1.addItem(time);
	            }
	        }
	    }
	    catch(Exception e) {
	        System.out.println(e);
	    }
	}
	
	private boolean isTimeAlreadyAdded(String time) {
	    for (int i = 0; i < comboBox_1.getItemCount(); i++) {
	        String item = comboBox_1.getItemAt(i).toString();
	        if (item.equals(time)) {
	            return true; // If date already exists in combobox, return true
	        }
	    }
	    return false; // If date not found in combobox, return false
	}
	
	
	public void select_apptID() {
		try {
			Connection connection = Database.connection;
			String query = "SELECT * FROM appointments where date = ? AND time = ? AND available = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, comboBox.getSelectedItem().toString()); 
	        stm.setString(2, comboBox_1.getSelectedItem().toString());
	        stm.setInt(3, 1);
			ResultSet rs = stm.executeQuery();
			 if (rs.next()) {
		            appointmentID = rs.getInt("appointmentID"); // Retrieve the appointmentID from the ResultSet
		            appointmentTime = rs.getString("time");
		        } 
			 else {
		        System.out.println("No appointment found for the given date and time.");
		     }
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
	
	public void insert_appt() {
		try {
			select_apptID();
			Connection connection = Database.connection;		
			String query = "INSERT INTO employee_appointments (appointmentID, customerID) VALUES  (?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setInt(1, appointmentID); 
			stm.setInt(2, customer.customerID); 
			//stm.executeUpdate();
			stm.executeUpdate();
			
			String query1 = "UPDATE appointments SET available = ? WHERE appointmentID = ?";
			PreparedStatement stm1 = connection.prepareStatement(query1);
			
			stm1.setInt(1, 0);
			stm1.setInt(2, appointmentID);
			stm1.executeUpdate();
		    JOptionPane.showMessageDialog(frame, "Successfully booked appointment !");
   
			
			frame.dispose();
			mainpage main = new mainpage();
			main.initialize();
			main.frame.setVisible(true);
		} 
		catch (SQLException s) {
		    JOptionPane.showMessageDialog(null, "Please choose a date and time for appointment","Error",JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception ee) {
			System.out.println(ee);
		}
	}

	public void menu() {
		frame.dispose();
		mainpage main = new mainpage();
		main.initialize();
		main.frame.setVisible(true);
	}
	

}
