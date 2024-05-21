import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AdminEmployeesPage {


public static JFrame frame;
private static JTable table;
private static DefaultTableModel model;

/**
 * Launch the application.
 */
public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
        public void run() {
            try {
                AdminLogin window = new AdminLogin();
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
public AdminEmployeesPage() {
    initialize();
}

/**
 * Initialize the contents of the frame.
 */
public void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);

    JLabel lblNewLabel = new JLabel("Employee Accounts");
    lblNewLabel.setBounds(325, 20, 150, 16);
    frame.getContentPane().add(lblNewLabel);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(50, 50, 700, 400);
    frame.getContentPane().add(scrollPane);

    // Create the table with column names
    model = new DefaultTableModel();
    model.addColumn("ID");
    model.addColumn("Username");
    model.addColumn("Password");
    model.addColumn("Full Name");
    table = new JTable(model);
    scrollPane.setViewportView(table);
    
    JButton btnNewButton_2 = new JButton("Add");
    btnNewButton_2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            addEmployee();
        }
    });
    btnNewButton_2.setBounds(600, 500, 117, 29);
    frame.getContentPane().add(btnNewButton_2);


    JButton btnNewButton = new JButton("Edit");
    btnNewButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            editEmployee();
        }
    });
    btnNewButton.setBounds(200, 500, 117, 29);
    frame.getContentPane().add(btnNewButton);

    JButton btnNewButton_1 = new JButton("Delete");
    btnNewButton_1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            deleteEmployee();
        }
    });
    btnNewButton_1.setBounds(400, 500, 117, 29);
    frame.getContentPane().add(btnNewButton_1);
    
    JButton btnGoBack = new JButton("Go Back");
    btnGoBack.setBounds(50, 500, 117, 29);
    frame.getContentPane().add(btnGoBack);

    btnGoBack.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            AdminMainPage.main(new String[] {});
        }
    });


    displayEmployees();
}

public static void displayEmployees() {
    try {
        Connection connection = Database.connection;
        String query = "SELECT * FROM employee";
        PreparedStatement stm = connection.prepareStatement(query);
        ResultSet result = stm.executeQuery(query);

        while (result.next()) {
            int id = result.getInt("employeeID");
            String username = result.getString("emp_username");
            String password = result.getString("emp_password");
            String fullname = result.getString("emp_fullname");
            // Add the data to the table model
            model.addRow(new Object[] { id, username, password, fullname });
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

public static void editEmployee() {
    // Get the selected row index
    int row = table.getSelectedRow();

    // Check if a row is selected
    if (row == -1) {
        JOptionPane.showMessageDialog(null, "Please select an employee", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Get the employee ID from the selected row
    int id = (int) model.getValueAt(row, 0);

    // Open the Edit Employee dialog
    EditEmployeeAdmin dialog = new EditEmployeeAdmin(id);
    dialog.setVisible(true);
    }
	
	public static void addEmployee() {
		AddEmployeeAdmin dialog = new AddEmployeeAdmin();
		dialog.setVisible(true);
	}

    public static void deleteEmployee() {
    // Get the selected row index
    int row = table.getSelectedRow();
    
 // Check if a row is selected
    if (row == -1) {
        JOptionPane.showMessageDialog(null, "Please select an employee", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Get the employee ID from the selected row
    int id = (int) model.getValueAt(row, 0);

    // Show a confirmation dialog before deleting the employee
    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this employee?", "Confirm",
            JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        try {
            Connection connection = Database.connection;
            String query = "DELETE FROM employee WHERE employeeID=?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, id);
            stm.executeUpdate();

            // Remove the row from the table model
            model.removeRow(row);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }
    
    public static void refreshTable() {
        // Clear the existing data from the table model
        model.setRowCount(0);
        
        // Fetch the updated data and add it to the table model
        displayEmployees();
    }
}

   
