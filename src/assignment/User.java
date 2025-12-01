import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

abstract class User {
    private String id;
    private String name;
    private String password;
    private String gender;
    private String email;
    private String phone;
    private int age;

    public User(String id, String name, String password, String gender, String email, String phone, int age) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.age = age;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public abstract String getRole();

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(id + "," + name + "," + password + "," + gender + "," + email + "," + phone + "," + age);
            writer.newLine();
        }
    }

    public static List<User> loadFromFile(String filename, String role) throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    try {
                        String id = parts[0];
                        String name = parts[1];
                        String password = parts[2];
                        String gender = parts[3];
                        String email = parts[4];
                        String phone = parts[5];
                        int age = Integer.parseInt(parts[6]);
                        
                        switch (role) {
                            case "Manager": users.add(new Manager(id, name, password, gender, email, phone, age)); break;
                            case "Staff": users.add(new Staff(id, name, password, gender, email, phone, age)); break;
                            case "Doctor": users.add(new Doctor(id, name, password, gender, email, phone, age)); break;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format in file: " + line);
                    }
                }
            }
        }
        return users;
    }

    public static void updateInFile(String filename, User updatedUser) throws IOException {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(updatedUser.getId())) {
                    line = updatedUser.getId() + "," + updatedUser.getName() + "," + updatedUser.getPassword() + "," + updatedUser.getGender() + "," + updatedUser.getEmail() + "," + updatedUser.getPhone() + "," + updatedUser.getAge();
                }
                lines.add(line);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public static void deleteFromFile(String filename, String id) throws IOException {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && !parts[0].equals(id)) {
                    lines.add(line);
                }
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public static String generateNewId(String role, String filename) throws IOException {
        String prefix = "";
        switch (role) {
            case "Manager": prefix = "M"; break;
            case "Staff": prefix = "S"; break;
            case "Doctor": prefix = "D"; break;
        }

        List<Integer> existingIds = new ArrayList<>();
        File file = new File(filename);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 0 && parts[0].startsWith(prefix)) {
                        try {
                            existingIds.add(Integer.parseInt(parts[0].substring(1)));
                        } catch (NumberFormatException e) {
                            // Ignore malformed IDs
                        }
                    }
                }
            }
        }

        int nextIdNumber = 1;
        if (!existingIds.isEmpty()) {
            nextIdNumber = Collections.max(existingIds) + 1;
        }
        
        return String.format("%s%03d", prefix, nextIdNumber);
    }
}