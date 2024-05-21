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

public class AdminBankAccountsPage {

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
    public AdminBankAccountsPage() {
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

        JLabel lblNewLabel = new JLabel("Bank Accounts");
        lblNewLabel.setBounds(350, 20, 100, 16);
        frame.getContentPane().add(lblNewLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 50, 700, 400);
        frame.getContentPane().add(scrollPane);

        // Create the table with column names
        model = new DefaultTableModel();
        model.addColumn("Account Number");
        model.addColumn("Balance");
        model.addColumn("Customer ID");
        table = new JTable(model);
        scrollPane.setViewportView(table);

        JButton btnNewButton_1 = new JButton("Delete");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteAccount();
            }
        });
        btnNewButton_1.setBounds(350, 500, 117, 29);
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


        displayAccounts();
    }

    public static void displayAccounts() {
        try {
            Connection connection = Database.connection;
            String query = "SELECT * FROM bankaccount";
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet result = stm.executeQuery(query);

            while (result.next()) {
                int accountNumber = result.getInt("accountNumber");
                float balance = result.getFloat("balance");
                int customerID = result.getInt("customerID");
                // Add the data to the table model
                model.addRow(new Object[] { accountNumber, balance, customerID });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void deleteAccount() {
        // Get the selected row index
        int row = table.getSelectedRow();

        // Check if a row is selected
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a bank account", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the account number from the selected row
        int accountNumber = (int) model.getValueAt(row, 0);

        // Show a confirmation dialog before deleting the account
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this account?", "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection connection = Database.connection;
                String query = "DELETE FROM bankaccount WHERE accountNumber=?";
                PreparedStatement stm = connection.prepareStatement(query);
                stm.setInt(1, accountNumber);
                stm.executeUpdate();
                // Remove the row from the table model
                model.removeRow(row);

                JOptionPane.showMessageDialog(null, "Account deleted successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

