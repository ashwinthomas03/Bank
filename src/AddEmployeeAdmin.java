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

public class AddEmployeeAdmin extends JDialog {
    private JTextField nameTextField;
    private JTextField usernameTextField;
    private JTextField passwordTextField;

    /**
     * Create the dialog.
     */
    public AddEmployeeAdmin() {
        setTitle("Create Employee");
        setBounds(100, 100, 400, 250);
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 400, 200);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 30, 50, 16);
        panel.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(100, 30, 200, 26);
        panel.add(nameTextField);
        nameTextField.setColumns(10);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 70, 80, 16);
        panel.add(usernameLabel);

        usernameTextField = new JTextField();
        usernameTextField.setBounds(100, 70, 200, 26);
        panel.add(usernameTextField);
        usernameTextField.setColumns(10);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 110, 80, 16);
        panel.add(passwordLabel);

        passwordTextField = new JTextField();
        passwordTextField.setBounds(100, 110, 200, 26);
        panel.add(passwordTextField);
        passwordTextField.setColumns(10);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createEmployee();
            }
        });
        saveButton.setBounds(80, 150, 80, 29);
        panel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelButton.setBounds(200, 150, 80, 29);
        panel.add(cancelButton);
    }
    
    private void createEmployee() {
        try {
            Connection connection = Database.connection;
            String query = "INSERT INTO employee (emp_fullname, emp_username, emp_password) VALUES (?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, nameTextField.getText());
            stm.setString(2, usernameTextField.getText());
            stm.setString(3, passwordTextField.getText());
            stm.executeUpdate();

            JOptionPane.showMessageDialog(null, "Employee created successfully", "Success Message", JOptionPane.INFORMATION_MESSAGE);
            dispose();

            AdminEmployeesPage.refreshTable();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Entry", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}





