// Appointment.java (unchanged, included for completeness)
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Appointment {
    private String id;
    private String customerId;
    private String doctorId;
    private String date;
    private String status;

    public Appointment(String id, String customerId, String doctorId, String date, String status) {
        this.id = id;
        this.customerId = customerId;
        this.doctorId = doctorId;
        this.date = date;
        this.status = status;
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public String getDoctorId() { return doctorId; }
    public String getDate() { return date; }
    public String getStatus() { return status; }

    public static List<Appointment> loadAppointments(String filename) throws IOException {
        List<Appointment> appointments = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return appointments;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    appointments.add(new Appointment(parts[0], parts[1], parts[2], parts[3], parts[4]));
                }
            }
        }
        return appointments;
    }
}