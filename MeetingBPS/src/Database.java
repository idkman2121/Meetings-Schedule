import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Database {
    private static final String URL = "jdbc:sqlite:meetings.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS meetings (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "subject TEXT NOT NULL," +
                         "attendants TEXT NOT NULL," +
                         "meetingPlace TEXT NOT NULL," +
                         "date TEXT NOT NULL)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addMeeting(String subject, String attendants, String meetingPlace, String date) {
        String sql = "INSERT INTO meetings(subject, attendants, meetingPlace, date) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subject);
            pstmt.setString(2, attendants);
            pstmt.setString(3, meetingPlace);
            pstmt.setString(4, date);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Meeting> getAllMeetings() {
        List<Meeting> meetings = new ArrayList<>();
        String sql = "SELECT * FROM meetings";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                meetings.add(new Meeting(
                    rs.getInt("id"),
                    rs.getString("subject"),
                    rs.getString("attendants"),
                    rs.getString("meetingPlace"),
                    rs.getString("date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meetings;
    }

    public static void updateMeeting(int id, String subject, String attendants, String meetingPlace, String date) {
        String sql = "UPDATE meetings SET subject = ?, attendants = ?, meetingPlace = ?, date = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subject);
            pstmt.setString(2, attendants);
            pstmt.setString(3, meetingPlace);
            pstmt.setString(4, date);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void exportToCSV() {
        String csvFile = "meetings.csv";
        try (FileWriter writer = new FileWriter(csvFile)) {
            List<Meeting> meetings = getAllMeetings();
            for (Meeting meeting : meetings) {
                writer.write(meeting.getSubject() + "," +
                             meeting.getAttendants() + "," +
                             meeting.getMeetingPlace() + "," +
                             meeting.getDate() + "\n");
            }
            JOptionPane.showMessageDialog(null, "Meetings exported to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteMeeting(int meetingId) {
        String sql = "DELETE FROM meetings WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, meetingId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


