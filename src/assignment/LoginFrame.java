import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public LoginFrame() {
        setTitle("APU Medical Centre Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        getContentPane().setBackground(new Color(102, 204, 204));

        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setPreferredSize(new Dimension(300, 70));
        JLabel titleLabel = new JLabel("APU Medical Centre");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.setBackground(new Color(102, 204, 204));
        northPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        northPanel.add(titlePanel);
        add(northPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(102, 204, 204));
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        inputPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(usernameField.getPreferredSize().width, 30));
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        inputPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        inputPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(passwordField.getPreferredSize().width, 30));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        inputPanel.add(passwordField, gbc);
        
        add(inputPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setBackground(new Color(102, 204, 204));
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 35));
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        loginButton.setBackground(new Color(51, 153, 153));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(new LoginListener());
        southPanel.add(loginButton);

        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        southPanel.add(messageLabel);

        add(southPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Username and password are required.");
                return;
            }

            try {
                User authenticatedUser = authenticateUser(username, password);
                if (authenticatedUser != null) {
                    messageLabel.setText("Login successful!");
                    dispose();
                    redirectBasedOnRole(authenticatedUser.getRole());
                } else {
                    messageLabel.setText("Invalid username or password.");
                }
            } catch (IOException ex) {
                messageLabel.setText("Error during login: " + ex.getMessage());
            }
        }

        private User authenticateUser(String username, String password) throws IOException {
            String[] roles = {"Manager", "Staff", "Doctor"};
            String[] files = {"managers.txt", "staff.txt", "doctors.txt"};

            for (int i = 0; i < roles.length; i++) {
                List<User> users = User.loadFromFile(files[i], roles[i]);
                for (User u : users) {
                    if (u.getName().equals(username) && u.getPassword().equals(password)) {
                        return u;
                    }
                }
            }
            return null;
        }

        private void redirectBasedOnRole(String role) {
            if ("Manager".equals(role)) {
                new ManagerDashboard();
            } else if ("Staff".equals(role)) {
                JOptionPane.showMessageDialog(null, "Staff page not implemented yet. 🚧");
            } else if ("Doctor".equals(role)) {
                JOptionPane.showMessageDialog(null, "Doctor page not implemented yet. 🩺");
            } else {
                JOptionPane.showMessageDialog(null, "Customer page not implemented yet. 🧑‍⚕️");
            }
        }
    }
}