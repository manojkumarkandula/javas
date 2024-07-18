import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class OnlineQuizApplication extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextArea questionArea;
    private JRadioButton[] options;
    private ButtonGroup buttonGroup;
    private JButton nextButton;
    private JButton submitButton;
    private JButton viewRankingButton;
    private JLabel scoreLabel;
    private int currentQuestion = 0;
    private int score = 0;
    private Map<String, Integer> rankingBoard = new HashMap<>();
    private String[] questions = {
            "What is the capital of France?",
            "What is 2 + 2?",
            "What is the color of the sky?"
    };
    private String[][] optionsData = {
            {"Paris", "London", "Berlin", "Madrid"},
            {"3", "4", "5", "6"},
            {"Blue", "Green", "Red", "Yellow"}
    };
    private String[] correctAnswers = {"Paris", "4", "Blue"};

    public OnlineQuizApplication() {
        setTitle("Online Quiz Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create question panel
        JPanel questionPanel = new JPanel(new BorderLayout());
        questionArea = new JTextArea();
        questionArea.setEditable(false);
        questionPanel.add(questionArea, BorderLayout.NORTH);

        // Create options panel
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        options = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            buttonGroup.add(options[i]);
            optionsPanel.add(options[i]);
        }
        questionPanel.add(optionsPanel, BorderLayout.CENTER);

        // Create navigation panel
        JPanel navigationPanel = new JPanel();
        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        navigationPanel.add(nextButton);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        navigationPanel.add(submitButton);
        questionPanel.add(navigationPanel, BorderLayout.SOUTH);

        cardPanel.add(questionPanel, "Quiz");

        // Create score panel
        JPanel scorePanel = new JPanel(new BorderLayout());
        scoreLabel = new JLabel();
        scorePanel.add(scoreLabel, BorderLayout.NORTH);
        viewRankingButton = new JButton("View Ranking");
        viewRankingButton.addActionListener(this);
        scorePanel.add(viewRankingButton, BorderLayout.SOUTH);

        cardPanel.add(scorePanel, "Score");

        // Create ranking panel
        JPanel rankingPanel = new JPanel(new BorderLayout());
        JTextArea rankingArea = new JTextArea();
        rankingArea.setEditable(false);
        rankingPanel.add(new JScrollPane(rankingArea), BorderLayout.CENTER);
        cardPanel.add(rankingPanel, "Ranking");

        add(cardPanel);

        displayQuestion();

        setVisible(true);
    }

    private void displayQuestion() {
        if (currentQuestion < questions.length) {
            questionArea.setText(questions[currentQuestion]);
            for (int i = 0; i < 4; i++) {
                options[i].setText(optionsData[currentQuestion][i]);
                options[i].setSelected(false);
            }
        } else {
            cardLayout.show(cardPanel, "Score");
            scoreLabel.setText("Your score: " + score + "/" + questions.length);
        }
    }

    private void checkAnswer() {
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected() && options[i].getText().equals(correctAnswers[currentQuestion])) {
                score++;
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            checkAnswer();
            currentQuestion++;
            displayQuestion();
        } else if (e.getSource() == submitButton) {
            checkAnswer();
            String name = JOptionPane.showInputDialog(this, "Enter your name:");
            rankingBoard.put(name, score);
            cardLayout.show(cardPanel, "Score");
            scoreLabel.setText("Your score: " + score + "/" + questions.length);
        } else if (e.getSource() == viewRankingButton) {
            JTextArea rankingArea = (JTextArea) ((JScrollPane) ((JPanel) cardPanel.getComponent(2)).getComponent(0)).getViewport().getView();
            rankingArea.setText("Ranking Board:\n");
            rankingBoard.forEach((name, score) -> rankingArea.append(name + ": " + score + "\n"));
            cardLayout.show(cardPanel, "Ranking");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OnlineQuizApplication::new);
    }
}
