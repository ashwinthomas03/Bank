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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AdminCustomersPage {

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
     * @wbp.parser.entryPoint
     */
    public AdminCustomersPage() {
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

        JLabel lblNewLabel = new JLabel("Customer List");
        lblNewLabel.setBounds(350, 20, 100, 16);
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
        model.addColumn("Address");
        table = new JTable(model);
        scrollPane.setViewportView(table);

        JButton btnNewButton = new JButton("Edit");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editUser();
            }
        });
        btnNewButton.setBounds(200, 500, 117, 29);
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Delete");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUser();
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

        

        displayUsers();
    }

    public static void displayUsers() {
        try {
            Connection connection = Database.connection;
            String query = "SELECT * FROM customers";
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet result = stm.executeQuery(query);

            while (result.next()) {
                int id = result.getInt("customerID");
                String username = result.getString("cust_username");
                String password = result.getString("cust_password");
                String fullname = result.getString("cust_fullname");
                String address = result.getString("cust_address");
                // Add the data to the table model
                model.addRow(new Object[] { id, username, password, fullname, address });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void editUser() {
        // Get the selected row index
        int row = table.getSelectedRow();

        // Check if a row is selected
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a user", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the user ID from the selected row
        int id = (int) model.getValueAt(row, 0);

        // Open the Edit User dialog
        EditCustomerAdmin dialog = new EditCustomerAdmin(id);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

    public static void deleteUser() {
        // Get the selected row index
        int row = table.getSelectedRow();

        // Check if a row is selected
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a user", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the user ID from the selected row
        int id = (int) model.getValueAt(row, 0);

        // Confirm the deletion with the user
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
        	try {
        	    Connection connection = Database.connection;
        	    String messageQuery = "DELETE FROM messages WHERE customerID=?";
        	    String bankQuery = "DELETE FROM bankaccount WHERE customerID=?";
        	    String appointmentQuery = "DELETE FROM employee_appointments WHERE customerID=?";
        	    String customerQuery = "DELETE FROM customers WHERE customerID=?";
        	    
        	    PreparedStatement messageStm = connection.prepareStatement(messageQuery);
        	    PreparedStatement bankStm = connection.prepareStatement(bankQuery);
        	    PreparedStatement appointmentStm = connection.prepareStatement(appointmentQuery);
        	    PreparedStatement customerStm = connection.prepareStatement(customerQuery);
        	    
        	    messageStm.setInt(1, id);
        	    bankStm.setInt(1, id);
        	    appointmentStm.setInt(1, id);
        	    customerStm.setInt(1, id);
        	    
        	    // Execute the DELETE statements in the correct order
        	    messageStm.executeUpdate();
        	    bankStm.executeUpdate();
        	    appointmentStm.executeUpdate();
        	    customerStm.executeUpdate();
        	    
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
        displayUsers();
    }

}

