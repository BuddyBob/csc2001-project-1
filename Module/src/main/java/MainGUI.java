import javax.swing.*;
import java.awt.*;
import java.util.List;
public class MainGUI extends JFrame {
    private JTextField idField;
    private JTextField titleField;
    private JTextField mentorField;
    private JTextField dateField;
    private JTextField locationField;
    private JTextField maxField;

    private JTextArea outputArea;

    //This is a linked list of type SessionList.Node
    private SessionList.Node sessions;

    // there should be a private member variable named `sessions` :
    // private SomethingOrOther sessions;

    // the constructor for the class. This will initialize
    // the class's member variables:
    public MainGUI() {
        // set sessions to a new empty list:
        sessions = null;
        setTitle("Employee Mentorship and Inclusion Manager");
        setSize(600, 600);
        // when this frame/window closes, halt the whole program:
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createGUI();
        setVisible(true);

    }

    // Create all of the display elements in the frame:
    private void createGUI() {
        // first, the input panel contains all of the field entry elements:
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8,2,5,5));
        // these are all of the input fields that will be in the frame:
        idField = new JTextField();
        titleField = new JTextField();
        mentorField = new JTextField();
        dateField = new JTextField();
        locationField = new JTextField();
        maxField = new JTextField();
        inputPanel.add(new JLabel("Session ID"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Title"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Mentor"));
        inputPanel.add(mentorField);
        inputPanel.add(new JLabel("Date"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Location"));
        inputPanel.add(locationField);
        inputPanel.add(new JLabel("Max Participants"));
        inputPanel.add(maxField);
        add(inputPanel, BorderLayout.NORTH);

        // next, the lower half of the window contains an output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);
        add(scroll, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Session");
        JButton displayButton = new JButton("Display");
        JButton searchButton = new JButton("Search");
        JButton removeButton = new JButton("Remove");
        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");
        buttonPanel.add(addButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> addSession());
        displayButton.addActionListener(e -> displaySessions());
        searchButton.addActionListener(e -> searchSession());
        removeButton.addActionListener(e -> removeSession());
        registerButton.addActionListener(e -> registerParticipant());
        exitButton.addActionListener(e -> System.exit(0));
    }

    // set all input fields to empty strings, give focus to the first
    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        mentorField.setText("");
        dateField.setText("");
        locationField.setText("");
        maxField.setText("");
        // Put the cursor back in the first field
        idField.requestFocus();
    }

    // the action of the Add Session button
    private void addSession() {
        try {
            int id = Integer.parseInt(idField.getText());
            String title = titleField.getText();
            String mentor = mentorField.getText();
            String date = dateField.getText();
            String location = locationField.getText();
            int maxParticipants = Integer.parseInt(maxField.getText());

            //Create a session object
            Session sesh = new Session(id, title,mentor, date, location, maxParticipants);
            sessions = SessionList.Node.insertNode(sessions, sesh);


            outputArea.setText("Session Added Successfully\n");
            // Clear the input fields
            clearFields();
        }
        catch(Exception e) {
            outputArea.setText("Invalid input");
        }
    }

    // display all sessions in the output area
    private void displaySessions() {
        outputArea.setText("");
        //using a throwaway pointer
        SessionList.Node current = sessions;

        while(current!=null){
            Session s = current.first();
            outputArea.append("ID: " + s.id + "\nTitle: " + s.title + "\nMentor: " + s.mentor
                    + "\nDate: " + s.date + "\nLocation: " + s.location
                    + "\nParticipants: " + s.participants + "/" + s.maxParticipants);
            outputArea.append("\n--------------------\n");
            current=current.rest();
        }
        // iterate over sessions; display each one
        // to the output window, using the `append`
        // method of the outputArea.

        // between each one, print a separator line,
        // as e.g.


    }
    private Session searchByID(SessionList.Node lst, int id) {
        return switch (lst) {
            case null -> null;
            case SessionList.Node(Session first, SessionList.Node rest) -> {
                if (first.id == id) {
                    yield first;
                } else {
                    yield searchByID(rest, id);
                }
            }
        };
    }

    private SessionList.Node searchByMentor(SessionList.Node lst, String mentor) {
        return switch (lst) {
            case null -> null;
            case SessionList.Node(Session first, SessionList.Node rest) -> {
                if (mentor.equals(first.mentor)) {
                    yield new SessionList.Node(first, searchByMentor(rest, mentor));
                } else {
                    yield searchByMentor(rest, mentor);
                }
            }
        };
    }

    // search by ID if presesnt, mentor otherwise, display results
    private void searchSession() {
        // if ID exists
        if (!idField.getText().trim().isEmpty()) {
            int id = Integer.parseInt(idField.getText().trim());

            // find session by ID, using a `searchByID` method
            // ... code here ...
            Session result = searchByID(sessions, id);

            if (result != null){
                outputArea.append("ID: " + result.id + "\nTitle: " + result.title + "\nMentor: " + result.mentor +
                        "\nDate: " + result.date + "\nLocation: " + result.location
                        + "\nParticipants: " + result.participants + "/" + result.maxParticipants);
                outputArea.append("\n--------------------\n");
            }else{
                outputArea.setText("Session not found.");
            }
        }
        // Otherwise, search by mentor if the Mentor field is not empty
        else if (!mentorField.getText().trim().isEmpty()) {
            String mentor = mentorField.getText().trim();
            SessionList.Node result = searchByMentor(sessions, mentor);
            if (result != null) {
                outputArea.setText("");
                SessionList.Node current = result;
                while (current != null) {
                    Session s = current.first();
                    outputArea.append("ID: " + s.id + "\nTitle: " + s.title + "\nMentor: " + s.mentor
                            + "\nDate: " + s.date + "\nLocation: " + s.location
                            + "\nParticipants: " + s.participants + "/" + s.maxParticipants);
                    outputArea.append("\n--------------------\n");
                    current = current.rest();
                }
            } else {
                outputArea.setText("No session found for mentor: " + mentor);
            }
        }
        // Nothing entered
        else {
            outputArea.setText("Please enter a Session ID or Mentor name.");
        }
    }

    // given an id, remove that session from the list
    private SessionList.Node removeByID(SessionList.Node head,int id){
        if(head==null){
            return null;
        }
        if(head.first().id ==id){
            return head.rest();
        }
        return new SessionList.Node(head.first(),removeByID(head.rest(),id));
    }
    private void removeSession() {
        int id = Integer.parseInt(idField.getText());
        if(searchByID(id)==null){
            outputArea.setText("ID not Found");
            return;
        }
        sessions =removeByID(sessions,id);
        outputArea.setText("Session removed successfully");
        // remove the session, print an error to the outputArea
        // if it's not found
        // ... code here ...
    }

    // add one to the count of the specified session.
    // MUTATES participant count of session.
    private void registerParticipant() {
        int id = Integer.parseInt(idField.getText());
        // increment participants field of session,
        // print success or failure message.
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}
