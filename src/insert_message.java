import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class insert_message {

	public JFrame frame;
	private JComboBox<String> comboBox;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JTextArea textArea;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					insert_message window = new insert_message();
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
	public insert_message() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		Database.connect();

		frame = new JFrame();
		frame.setBounds(100, 100, 523, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Send Messages");
		lblNewLabel.setBounds(202, 25, 111, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Selected Customer ID: ");
		lblNewLabel_1.setBounds(33, 109, 148, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		comboBox = new JComboBox<String>();
		//JComboBox comboBox = new JComboBox();
		comboBox.setBounds(183, 105, 299, 27);
		frame.getContentPane().add(comboBox);
		
		JLabel lblNewLabel_2 = new JLabel("Enter message here: ");
		lblNewLabel_2.setBounds(33, 235, 138, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		btnNewButton = new JButton("Send Message");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert_message();
			}
		});
		btnNewButton.setBounds(256, 322, 130, 29);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("Back to Menu");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			    Mainemployee main = new Mainemployee();
			    main.initialize();
			    main.frame.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(106, 322, 138, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		textArea = new JTextArea();
		textArea.setBounds(183, 188, 299, 114);
		frame.getContentPane().add(textArea);
		
		loadCustomerID();
		
	}
	
	public void loadCustomerID() {
		try {
			Connection connection = Database.connection;
			String query = "SELECT customerID FROM customers";
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				comboBox.addItem(rs.getString("customerID"));
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void insert_message() {
		try {
			
			Connection connection = Database.connection;
			String message = textArea.getText().toString();
			int customerID = Integer.parseInt(comboBox.getSelectedItem().toString());
			String query = "INSERT INTO messages (message, customerID) VALUES  (?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setString(1, message); 
			stm.setInt(2, customerID); 
			//stm.executeUpdate();
			stm.executeUpdate();
			
			
		    JOptionPane.showMessageDialog(frame, "Message Sent Successfully !");
		    
		    frame.dispose();
		    Mainemployee main = new Mainemployee();
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

}
