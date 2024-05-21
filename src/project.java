import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Toolkit;

public class project {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					project window = new project();
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
	public project() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {

	      
		frame = new JFrame();
		frame.setForeground(Color.ORANGE);
		frame.setBounds(100, 100, 450, 345);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Welcome to NYIT Bank");
		//frame.getContentPane().setBackground(Color.RGBtoHSB(0, 0,0, 1.1));
		
		
		JButton btnNewButton = new JButton("Customer login");
		btnNewButton.setBounds(149, 116, 148, 34);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customers();
			}
		});
		
		
		JButton btnNewButton_1 = new JButton("Employee login");
		btnNewButton_1.setBackground(Color.PINK);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				employeecall();
			}
		});
		btnNewButton_1.setBounds(149, 177, 148, 34);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Admin login");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				admin();
			}
		});
		btnNewButton_2.setBounds(149, 242, 148, 34);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("Please select User Role");
		lblNewLabel.setBounds(149, 78, 158, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Welcome to the Bank !");
		lblNewLabel_1.setBounds(29, 25, 148, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		Timer timer = new Timer(20, new ActionListener() {
		    int x = 29;

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        lblNewLabel_1.setBounds(x, 25, 148, 16);
		        x++;
		        if (x > frame.getContentPane().getWidth()) {
		            x = -lblNewLabel_1.getWidth();
		        }
		    }
		});

		timer.start();	
	}
	
	
	public void customers() {
		frame.dispose();
		customerpage c1 = new customerpage();
		c1.initialize();
		c1.frame.setVisible(true);
	}
	
	public void employeecall() {
		frame.dispose();
		employeepagee employee = new employeepagee();
		employee.initialize();
		employee.frame.setVisible(true);
	}
	
	public void admin() {
		frame.dispose();
		AdminLogin c1 = new AdminLogin();
		c1.initialize();
		c1.frame.setVisible(true);
	}
	
	
	
}
