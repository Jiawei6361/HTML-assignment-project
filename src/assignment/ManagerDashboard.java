import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class ManagerDashboard extends JFrame {
    private static final String MANAGERS_FILE = "managers.txt";
    private static final String STAFF_FILE = "staff.txt";
    private static final String DOCTORS_FILE = "doctors.txt";
    private static final String APPOINTMENTS_FILE = "appointments.txt";
    private static final String FEEDBACKS_FILE = "feedbacks.txt";

    public ManagerDashboard() {
        setTitle("Manager Dashboard");
        setSize(500, 600); // Adjusted size for better button spacing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the background color for the content pane (consistent turquoise)
        getContentPane().setBackground(new Color(102, 204, 204)); 

        // Use BoxLayout for vertical arrangement of buttons, with a main panel for centering
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(102, 204, 204)); // Match background
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Add padding around the buttons
        
        // Create buttons with the desired style
        JButton manageManagersBtn = createStyledButton("Manage Manager");
        manageManagersBtn.addActionListener(new ManageUserListener("Manager", MANAGERS_FILE));
        mainPanel.add(manageManagersBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer between buttons

        JButton manageStaffBtn = createStyledButton("Manage Staff");
        manageStaffBtn.addActionListener(new ManageUserListener("Staff", STAFF_FILE));
        mainPanel.add(manageStaffBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer

        JButton manageDoctorsBtn = createStyledButton("Manage Doctor");
        manageDoctorsBtn.addActionListener(new ManageUserListener("Doctor", DOCTORS_FILE));
        mainPanel.add(manageDoctorsBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer

        JButton viewAppointmentsBtn = createStyledButton("View all appointment");
        viewAppointmentsBtn.addActionListener(e -> viewAppointments());
        mainPanel.add(viewAppointmentsBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer

        JButton viewFeedbacksBtn = createStyledButton("View all feedback and comment");
        viewFeedbacksBtn.addActionListener(e -> viewFeedbacks());
        mainPanel.add(viewFeedbacksBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer

        JButton generateReportsBtn = createStyledButton("Generate report");
        generateReportsBtn.addActionListener(e -> generateReports());
        mainPanel.add(generateReportsBtn);

        // Add the mainPanel to the frame, centered
        add(mainPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    // Helper method to create consistently styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        button.setMaximumSize(new Dimension(300, 50)); // Fixed size for buttons
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK); // Set text color to black
        button.setFont(new Font("SansSerif", Font.BOLD, 18)); // Larger, bold font
        button.setFocusPainted(false); // Remove focus border
        return button;
    }

    private class ManageUserListener implements ActionListener {
        private String role;
        private String filename;

        public ManageUserListener(String role, String filename) {
            this.role = role;
            this.filename = filename;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new ManageUserFrame(role, filename);
        }
    }

    private void viewAppointments() {
        try {
            List<Appointment> appointments = Appointment.loadAppointments(APPOINTMENTS_FILE);
            StringBuilder sb = new StringBuilder("All Appointments:\n");
            if (appointments.isEmpty()) {
                sb.append("No appointments found.");
            } else {
                for (Appointment app : appointments) {
                    sb.append("ID: ").append(app.getId())
                      .append(", Customer: ").append(app.getCustomerId())
                      .append(", Doctor: ").append(app.getDoctorId())
                      .append(", Date: ").append(app.getDate())
                      .append(", Status: ").append(app.getStatus()).append("\n");
                }
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "All Appointments", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading appointments: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewFeedbacks() {
        try {
            List<Feedback> feedbacks = Feedback.loadFeedbacks(FEEDBACKS_FILE);
            StringBuilder sb = new StringBuilder("All Feedbacks and Comments:\n");
            if (feedbacks.isEmpty()) {
                sb.append("No feedbacks found.");
            } else {
                for (Feedback fb : feedbacks) {
                    sb.append("ID: ").append(fb.getId())
                      .append(", User: ").append(fb.getUserId())
                      .append(", Content: ").append(fb.getContent()).append("\n");
                }
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "All Feedbacks", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading feedbacks: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateReports() {
        try {
            List<Appointment> appointments = Appointment.loadAppointments(APPOINTMENTS_FILE);
            List<Feedback> feedbacks = Feedback.loadFeedbacks(FEEDBACKS_FILE);

            int totalAppointments = appointments.size();
            long pendingAppointments = appointments.stream().filter(a -> "Pending".equals(a.getStatus())).count(); // Use .equals() for string comparison
            long completedAppointments = appointments.stream().filter(a -> "Completed".equals(a.getStatus())).count();
            long cancelledAppointments = appointments.stream().filter(a -> "Cancelled".equals(a.getStatus())).count();


            int totalFeedbacks = feedbacks.size();

            StringBuilder reportBuilder = new StringBuilder();
            reportBuilder.append("Analysed Report:\n");
            reportBuilder.append("----------------------------------\n");
            reportBuilder.append("Total Appointments: ").append(totalAppointments).append("\n");
            reportBuilder.append("  - Pending: ").append(pendingAppointments).append("\n");
            reportBuilder.append("  - Completed: ").append(completedAppointments).append("\n");
            reportBuilder.append("  - Cancelled: ").append(cancelledAppointments).append("\n");
            reportBuilder.append("Total Feedbacks/Comments: ").append(totalFeedbacks).append("\n");
            reportBuilder.append("----------------------------------\n");

            String report = reportBuilder.toString();

            JOptionPane.showMessageDialog(this, report, "Generated Report", JOptionPane.INFORMATION_MESSAGE);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("reports.txt", true))) {
                writer.write("Report Generated on " + new java.util.Date() + "\n"); // Add timestamp to report file
                writer.write(report);
                writer.write("\n----------------------------------\n\n");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Start with login page
        SwingUtilities.invokeLater(() -> new LoginFrame()); // Ensure GUI updates on EDT
    }
}