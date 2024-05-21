import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminMainPage {

    static JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminMainPage window = new AdminMainPage();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AdminMainPage() {
        initialize();
    }

    void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		JButton btnNewButton = new JButton("Customer Accounts");
		btnNewButton.setBounds(149, 36, 148, 34);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				admin_customers();
			}
		});
		
		
		JButton btnNewButton_1 = new JButton("Employee Accounts");
		btnNewButton_1.setBounds(149, 106, 148, 34);
		frame.getContentPane().add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				admin_employees();
			}
		});
		
		
		JButton btnNewButton_2 = new JButton("Bank Accounts");
		btnNewButton_2.setBounds(149, 180, 148, 34);
		frame.getContentPane().add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				admin_bankaccounts();
			}
		});
		
		JButton btnNewButton_5 = new JButton("Sign Out");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signoutcall();
			}
		});
		btnNewButton_5.setBounds(42, 222, 117, 29);
		frame.getContentPane().add(btnNewButton_5);
		
		
	}
    
    public void admin_customers() {
		frame.dispose();
		AdminCustomersPage c1 = new AdminCustomersPage();
		c1.initialize();
		c1.frame.setVisible(true);
	}
	
	public void admin_employees() {
		frame.dispose();
		AdminEmployeesPage c1 = new AdminEmployeesPage();
		c1.initialize();
		c1.frame.setVisible(true);
	}
	
	public void admin_bankaccounts() {
		frame.dispose();
		AdminBankAccountsPage c1 = new AdminBankAccountsPage();
		c1.initialize();
		c1.frame.setVisible(true);
	}
	
	public static void signoutcall() {
		frame.dispose();
		AdminLogin e = new AdminLogin();
		e.initialize();
		e.frame.setVisible(true);
	}
}

