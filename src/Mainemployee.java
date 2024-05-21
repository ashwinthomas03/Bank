import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

public class Mainemployee {

	public static JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					employeepagee window = new employeepagee();
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
	public Mainemployee() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 442, 442);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		employeepagee e = new employeepagee();
		
		JButton btnNewButton = new JButton("Customer Info");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cutsomerinfo();
			}
		});
		btnNewButton.setBounds(219, 87, 202, 29);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Account Requests");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountrequestcall();
			}
		});
		btnNewButton_1.setBounds(219, 198, 202, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Delete bank account");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletecall();
			}
		});
		btnNewButton_2.setBounds(219, 252, 202, 29);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("View Bank Accounts");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bankaccountscall();
			}
		});
		btnNewButton_3.setBounds(219, 141, 202, 29);
		frame.getContentPane().add(btnNewButton_3);
		
		JLabel lblNewLabel = new JLabel("Please choose the operation");
		lblNewLabel.setBounds(219, 39, 186, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Main Menu");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(53, 76, 104, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Welcome");
		lblNewLabel_2.setBounds(65, 156, 61, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton_4 = new JButton("View Appointments");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appointmentscall();
			}
		});
		btnNewButton_4.setBounds(219, 305, 202, 29);
		frame.getContentPane().add(btnNewButton_4);
		
		textField = new JTextField();
		textField.setBounds(37, 184, 130, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setText(e.name);
		textField.setEditable(false);
		
		JButton btnNewButton_5 = new JButton("Sign Out");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signoutcall();
			}
		});
		btnNewButton_5.setBounds(42, 222, 117, 29);
		frame.getContentPane().add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("Send message to customers");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messagecall();
			}
		});
		btnNewButton_6.setBounds(219, 361, 202, 29);
		frame.getContentPane().add(btnNewButton_6);
	}
	
	public void cutsomerinfo() {
		frame.dispose();
		Scoll2 customer = new Scoll2();
		customer.initialize();
		customer.frame.setVisible(true);
	}
	
	public void deletecall() {
		frame.dispose();
		delete d1 =new delete();
		d1.initialize();
		d1.frame.setVisible(true);
	}
	
	public void bankaccountscall() {
		frame.dispose();
		bankaccounts b1 = new bankaccounts();
		b1.initialize();
		b1.frame.setVisible(true);
	}
	
	public void accountrequestcall() {
		frame.dispose();
		accReq a = new accReq();
		a.initialize();
		a.frame.setVisible(true);
	}
	
	public static void signoutcall() {
		frame.dispose();
		employeepagee e = new employeepagee();
		e.initialize();
		e.frame.setVisible(true);
	}
	
	public void appointmentscall() {
		frame.dispose();
		listofappointments list = new listofappointments();
		list.initialize();
		list.frame.setVisible(true);
	}
	
	public void messagecall() {
		frame.dispose();
		insert_message main = new insert_message();
		main.initialize();
		main.frame.setVisible(true);
	}
}



