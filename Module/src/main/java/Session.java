public class Session {
    int id;
    String title;
    String mentor;
    String date;
    String location;
    int participants;
    int maxParticipants;

    public Session(int id, String title, String mentor,
                   String date, String location, int maxParticipants) {
        this.id = id;
        this.title = title;
        this.mentor = mentor;
        this.date = date;
        this.location = location;
        this.participants = 0;
        this.maxParticipants = maxParticipants;
    }
}