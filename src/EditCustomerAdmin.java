import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditCustomerAdmin extends JDialog {
    private JTextField nameTextField;
    private JTextField addressTextField;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private int userId;

    /**
     * Create the dialog.
     */
    public EditCustomerAdmin(int id) {
        userId = id;
        setTitle("Edit User");
        setBounds(100, 100, 400, 300);
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 400, 250);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 30, 50, 16);
        panel.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(100, 30, 200, 26);
        panel.add(nameTextField);
        nameTextField.setColumns(10);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(20, 70, 80, 16);
        panel.add(addressLabel);

        addressTextField = new JTextField();
        addressTextField.setBounds(100, 70, 200, 26);
        panel.add(addressTextField);
        addressTextField.setColumns(10);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 110, 80, 16);
        panel.add(usernameLabel);

        usernameTextField = new JTextField();
        usernameTextField.setBounds(100, 110, 200, 26);
        panel.add(usernameTextField);
        usernameTextField.setColumns(10);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 150, 80, 16);
        panel.add(passwordLabel);

        passwordTextField = new JTextField();
        passwordTextField.setBounds(100, 150, 200, 26);
        panel.add(passwordTextField);
        passwordTextField.setColumns(10);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });
        saveButton.setBounds(80, 180, 80, 29);
        panel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelButton.setBounds(200, 180, 80, 29);
        panel.add(cancelButton);

        loadUser();
    }

    private void loadUser() {
        try {
            Connection connection = Database.connection;
            String query = "SELECT * FROM customers WHERE customerID = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, userId);
            ResultSet result = stm.executeQuery();

            if (result.next()) {
                String username = result.getString("cust_username");
                String password = result.getString("cust_password");
                String fullname = result.getString("cust_fullname");
                String address = result.getString("cust_address");

                nameTextField.setText(fullname);
                addressTextField.setText(address);
                usernameTextField.setText(username);
                passwordTextField.setText(password);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveChanges() {
        try {
            Connection connection = Database.connection;
            String query = "UPDATE customers SET cust_fullname=?, cust_address=?, cust_username=?, cust_password=? WHERE customerID=?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, nameTextField.getText());
            stm.setString(2, addressTextField.getText());
            stm.setString(3, usernameTextField.getText());
            stm.setString(4, passwordTextField.getText());
            stm.setInt(5, userId);
            stm.executeUpdate();

            JOptionPane.showMessageDialog(null, "User updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Refresh the table

            AdminCustomersPage.refreshTable();
        	} catch (SQLException e) {
        		JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        	} catch (NumberFormatException e) {
        		JOptionPane.showMessageDialog(null, "Invalid balance value", "Error", JOptionPane.ERROR_MESSAGE);
        	}
    }
}

