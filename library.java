import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class LibraryResourceAdministrator extends Frame implements ActionListener {

    private List<String> volumes = new ArrayList<>();
    private List<String> patrons = new ArrayList<>();
    private TextArea displayArea;
    private TextField inputField;
    private Button addVolumeButton, addPatronButton, loanButton, repayButton, searchButton;
    private Choice operationChoice;

    public LibraryResourceAdministrator() {
        setLayout(new BorderLayout());

        // Top Panel
        Panel topPanel = new Panel();
        topPanel.setLayout(new GridLayout(2, 1));

        // Input Field
        inputField = new TextField();
        topPanel.add(inputField);

        // Operation Choice
        operationChoice = new Choice();
        operationChoice.add("Volume");
        operationChoice.add("Patron");
        topPanel.add(operationChoice);

        add(topPanel, BorderLayout.NORTH);

        // Display Area
        displayArea = new TextArea();
        add(displayArea, BorderLayout.CENTER);

        // Bottom Panel
        Panel bottomPanel = new Panel();
        bottomPanel.setLayout(new FlowLayout());

        // Buttons
        addVolumeButton = new Button("Add Volume");
        addPatronButton = new Button("Add Patron");
        loanButton = new Button("Loan");
        repayButton = new Button("Repay");
        searchButton = new Button("Search");

        addVolumeButton.addActionListener(this);
        addPatronButton.addActionListener(this);
        loanButton.addActionListener(this);
        repayButton.addActionListener(this);
        searchButton.addActionListener(this);

        bottomPanel.add(addVolumeButton);
        bottomPanel.add(addPatronButton);
        bottomPanel.add(loanButton);
        bottomPanel.add(repayButton);
        bottomPanel.add(searchButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Frame settings
        setTitle("Library Resource Administrator");
        setSize(400, 300);
        setVisible(true);

        // Window closing event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        String input = inputField.getText();
        String operation = operationChoice.getSelectedItem();

        if (e.getSource() == addVolumeButton) {
            volumes.add(input);
            displayArea.append("Added Volume: " + input + "\n");
        } else if (e.getSource() == addPatronButton) {
            patrons.add(input);
            displayArea.append("Added Patron: " + input + "\n");
        } else if (e.getSource() == loanButton) {
            if (operation.equals("Volume")) {
                displayArea.append("Loaned Volume: " + input + "\n");
            }
        } else if (e.getSource() == repayButton) {
            if (operation.equals("Volume")) {
                displayArea.append("Repaid Volume: " + input + "\n");
            }
        } else if (e.getSource() == searchButton) {
            if (operation.equals("Volume")) {
                if (volumes.contains(input)) {
                    displayArea.append("Volume Found: " + input + "\n");
                } else {
                    displayArea.append("Volume Not Found: " + input + "\n");
                }
            } else if (operation.equals("Patron")) {
                if (patrons.contains(input)) {
                    displayArea.append("Patron Found: " + input + "\n");
                } else {
                    displayArea.append("Patron Not Found: " + input + "\n");
                }
            }
        }
    }

    public static void main(String[] args) {
        new LibraryResourceAdministrator();
    }
}