// Feedback.java (unchanged, included for completeness)
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Feedback {
    private String id;
    private String userId;
    private String content;

    public Feedback(String id, String userId, String content) {
        this.id = id;
        this.userId = userId;
        this.content = content;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getContent() { return content; }

    public static List<Feedback> loadFeedbacks(String filename) throws IOException {
        List<Feedback> feedbacks = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return feedbacks;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    feedbacks.add(new Feedback(parts[0], parts[1], parts[2]));
                }
            }
        }
        return feedbacks;
    }
}