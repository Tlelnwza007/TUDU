import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JPanel loginPanel;
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;

    private JPanel registerPanel;
    private JTextField registerUsernameField;
    private JPasswordField registerPasswordField;
    private JPasswordField registerConfirmPasswordField;

    private csv csv; // เพิ่ม CsvHandler

    public App() {
        setTitle("TUDU - Login / Register");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);

        csv = new csv(); // สร้าง instance ของ CsvHandler

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);

        createLoginPanel();
        createRegisterPanel();

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");

        cardLayout.show(mainPanel, "Login"); // Show login panel initially
    }

    private void createLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(Color.WHITE);

        // Logo
        JLabel logoLabel = new JLabel("TUDU");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 48));
        logoLabel.setForeground(Color.decode("#528C52"));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(Box.createVerticalStrut(50));
        loginPanel.add(logoLabel);
        loginPanel.add(Box.createVerticalStrut(20));

        // Login Title
        JLabel loginTitleLabel = new JLabel("LOGIN");
        loginTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginTitleLabel.setForeground(Color.decode("#333333"));
        loginTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(loginTitleLabel);
        loginPanel.add(Box.createVerticalStrut(20));

        // Username
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        usernamePanel.setBackground(Color.WHITE);
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        usernameLabel.setForeground(Color.decode("#333333"));
        usernamePanel.add(usernameLabel);
        loginUsernameField = new JTextField(20);
        loginUsernameField.setFont(new Font("Arial", Font.PLAIN, 12));
        loginUsernameField.setBorder(BorderFactory.createLineBorder(Color.decode("#cccccc")));
        usernamePanel.add(loginUsernameField);
        loginPanel.add(usernamePanel);
        loginPanel.add(Box.createVerticalStrut(10));


        // Password
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        passwordPanel.setBackground(Color.WHITE);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordLabel.setForeground(Color.decode("#333333"));
        passwordPanel.add(passwordLabel);
        loginPasswordField = new JPasswordField(20);
        loginPasswordField.setFont(new Font("Arial", Font.PLAIN, 12));
        loginPasswordField.setBorder(BorderFactory.createLineBorder(Color.decode("#cccccc")));
        passwordPanel.add(loginPasswordField);
        loginPanel.add(passwordPanel);
        loginPanel.add(Box.createVerticalStrut(10));


        // Remember Me (Checkbox)
        JCheckBox rememberMeCheckBox = new JCheckBox("Remember Me");
        rememberMeCheckBox.setBackground(Color.WHITE);
        rememberMeCheckBox.setForeground(Color.decode("#333333"));
        rememberMeCheckBox.setFont(new Font("Arial", Font.PLAIN, 10));
        rememberMeCheckBox.setFocusPainted(false);
        rememberMeCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT); // Adjust alignment for checkbox
        JPanel checkboxWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); // Wrapper for padding
        checkboxWrapper.setBackground(Color.WHITE);
        checkboxWrapper.add(rememberMeCheckBox);
        loginPanel.add(checkboxWrapper);
        loginPanel.add(Box.createVerticalStrut(20));


        // Login Button
        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(Color.decode("#528C52"));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setPreferredSize(new Dimension(250, 40));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        loginPanel.add(loginButton);
        loginPanel.add(Box.createVerticalStrut(10));

        // Sign up Button (Link to Register)
        JButton signUpButton = new JButton("Sign up");
        signUpButton.setFont(new Font("Arial", Font.PLAIN, 12));
        signUpButton.setBackground(Color.WHITE);
        signUpButton.setForeground(Color.decode("#528C52"));
        signUpButton.setFocusPainted(false);
        signUpButton.setBorderPainted(false);
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Register");
            }
        });
        loginPanel.add(signUpButton);
        loginPanel.add(Box.createVerticalGlue()); // Pushes content to top
    }

    private void createRegisterPanel() {
        registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));
        registerPanel.setBackground(Color.WHITE);

        // Logo
        JLabel logoLabel = new JLabel("TUDU");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 48));
        logoLabel.setForeground(Color.decode("#528C52"));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerPanel.add(Box.createVerticalStrut(50));
        registerPanel.add(logoLabel);
        registerPanel.add(Box.createVerticalStrut(20));

        // Register Title
        JLabel registerTitleLabel = new JLabel("REGISTER");
        registerTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        registerTitleLabel.setForeground(Color.decode("#333333"));
        registerTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerPanel.add(registerTitleLabel);
        registerPanel.add(Box.createVerticalStrut(20));

        // Username
        JPanel regUsernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        regUsernamePanel.setBackground(Color.WHITE);
        JLabel regUsernameLabel = new JLabel("Username");
        regUsernameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        regUsernameLabel.setForeground(Color.decode("#333333"));
        regUsernamePanel.add(regUsernameLabel);
        registerUsernameField = new JTextField(20);
        registerUsernameField.setFont(new Font("Arial", Font.PLAIN, 12));
        registerUsernameField.setBorder(BorderFactory.createLineBorder(Color.decode("#cccccc")));
        regUsernamePanel.add(registerUsernameField);
        registerPanel.add(regUsernamePanel);
        registerPanel.add(Box.createVerticalStrut(10));


        // Password
        JPanel regPasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        regPasswordPanel.setBackground(Color.WHITE);
        JLabel regPasswordLabel = new JLabel("Password");
        regPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        regPasswordLabel.setForeground(Color.decode("#333333"));
        regPasswordPanel.add(regPasswordLabel);
        registerPasswordField = new JPasswordField(20);
        registerPasswordField.setFont(new Font("Arial", Font.PLAIN, 12));
        registerPasswordField.setBorder(BorderFactory.createLineBorder(Color.decode("#cccccc")));
        regPasswordPanel.add(registerPasswordField);
        registerPanel.add(regPasswordPanel);
        registerPanel.add(Box.createVerticalStrut(10));


        // Confirm Password
        JPanel regConfirmPasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        regConfirmPasswordPanel.setBackground(Color.WHITE);
        JLabel regConfirmPasswordLabel = new JLabel("Confirm Password");
        regConfirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        regConfirmPasswordLabel.setForeground(Color.decode("#333333"));
        regConfirmPasswordPanel.add(regConfirmPasswordLabel);
        registerConfirmPasswordField = new JPasswordField(20);
        registerConfirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 12));
        registerConfirmPasswordField.setBorder(BorderFactory.createLineBorder(Color.decode("#cccccc")));
        regConfirmPasswordPanel.add(registerConfirmPasswordField);
        registerPanel.add(regConfirmPasswordPanel);
        registerPanel.add(Box.createVerticalStrut(20));


        // Register Button
        JButton registerButton = new JButton("REGISTER");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(Color.decode("#528C52"));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setPreferredSize(new Dimension(250, 40));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
        registerPanel.add(registerButton);
        registerPanel.add(Box.createVerticalStrut(10));

        // Back to Login Button
        JButton backToLoginButton = new JButton("Back to Login");
        backToLoginButton.setFont(new Font("Arial", Font.PLAIN, 12));
        backToLoginButton.setBackground(Color.WHITE);
        backToLoginButton.setForeground(Color.decode("#528C52"));
        backToLoginButton.setFocusPainted(false);
        backToLoginButton.setBorderPainted(false);
        backToLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Login");
                clearRegisterFields(); // Clear fields when going back
            }
        });
        registerPanel.add(backToLoginButton);
        registerPanel.add(Box.createVerticalGlue()); // Pushes content to top
    }

    private void handleLogin() {
        String username = loginUsernameField.getText();
        String password = new String(loginPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Users authenticatedUser = csv.authenticateUser(username, password);
        if (authenticatedUser != null) {
            JOptionPane.showMessageDialog(this, "Login Successful! Welcome, " + authenticatedUser.getUsername(), "Success", JOptionPane.INFORMATION_MESSAGE);
            // Here you would typically open the main application window
            // For now, we'll just close this login window.
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRegister() {
        String username = registerUsernameField.getText();
        String password = new String(registerPasswordField.getPassword());
        String confirmPassword = new String(registerConfirmPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Users newUser = new Users(username, password);
        if (csv.registerUser(newUser)) {
            JOptionPane.showMessageDialog(this, "Registration Successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "Login");
            clearRegisterFields();
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.", "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearRegisterFields() {
        registerUsernameField.setText("");
        registerPasswordField.setText("");
        registerConfirmPasswordField.setText("");
    }

    public static void main(String[] args) {
        // Ensure GUI updates are done on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App().setVisible(true);
            }
        });
    }
}