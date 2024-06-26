public class Meeting {
    private int id;
    private String subject;
    private String attendants;
    private String meetingPlace;
    private String date;

    public Meeting(int id, String subject, String attendants, String meetingPlace, String date) {
        this.id = id;
        this.subject = subject;
        this.attendants = attendants;
        this.meetingPlace = meetingPlace;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getAttendants() {
        return attendants;
    }

    public String getMeetingPlace() {
        return meetingPlace;
    }

    public String getDate() {
        return date;
    }
}
