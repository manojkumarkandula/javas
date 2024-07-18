import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ATMMockup extends JFrame implements ActionListener {

    private JTextField accountField, amountField, transferAccountField;
    private JTextArea displayArea;
    private JButton queryButton, withdrawButton, depositButton, transferButton;
    private Map<String, Double> accounts = new HashMap<>();

    public ATMMockup() {
        // Create the frame
        setTitle("ATM Mockup");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Create the input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        // Account input
        inputPanel.add(new JLabel("Account:"));
        accountField = new JTextField();
        inputPanel.add(accountField);

        // Amount input
        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        // Transfer account input
        inputPanel.add(new JLabel("Transfer To Account:"));
        transferAccountField = new JTextField();
        inputPanel.add(transferAccountField);

        // Add input panel to the frame
        add(inputPanel, BorderLayout.NORTH);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));

        // Query button
        queryButton = new JButton("Query");
        queryButton.addActionListener(this);
        buttonPanel.add(queryButton);

        // Withdraw button
        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(this);
        buttonPanel.add(withdrawButton);

        // Deposit button
        depositButton = new JButton("Deposit");
        depositButton.addActionListener(this);
        buttonPanel.add(depositButton);

        // Transfer button
        transferButton = new JButton("Transfer");
        transferButton.addActionListener(this);
        buttonPanel.add(transferButton);

        // Add button panel to the frame
        add(buttonPanel, BorderLayout.SOUTH);

        // Initialize some accounts for testing
        accounts.put("123456", 1000.0);
        accounts.put("654321", 2000.0);
        accounts.put("111111", 500.0);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String account = accountField.getText();
        String amountText = amountField.getText();
        String transferAccount = transferAccountField.getText();

        if (account.isEmpty()) {
            displayArea.append("Account field is empty\n");
            return;
        }

        double amount = 0;
        if (!amountText.isEmpty()) {
            try {
                amount = Double.parseDouble(amountText);
            } catch (NumberFormatException ex) {
                displayArea.append("Invalid amount\n");
                return;
            }
        }

        if (e.getSource() == queryButton) {
            queryAccount(account);
        } else if (e.getSource() == withdrawButton) {
            withdrawFromAccount(account, amount);
        } else if (e.getSource() == depositButton) {
            depositToAccount(account, amount);
        } else if (e.getSource() == transferButton) {
            transferFunds(account, transferAccount, amount);
        }
    }

    private void queryAccount(String account) {
        if (accounts.containsKey(account)) {
            double balance = accounts.get(account);
            displayArea.append("Account " + account + " balance: $" + balance + "\n");
        } else {
            displayArea.append("Account " + account + " not found\n");
        }
    }

    private void withdrawFromAccount(String account, double amount) {
        if (accounts.containsKey(account)) {
            double balance = accounts.get(account);
            if (balance >= amount) {
                accounts.put(account, balance - amount);
                displayArea.append("Withdrawn $" + amount + " from account " + account + "\n");
            } else {
                displayArea.append("Insufficient funds in account " + account + "\n");
            }
        } else {
            displayArea.append("Account " + account + " not found\n");
        }
    }

    private void depositToAccount(String account, double amount) {
        if (accounts.containsKey(account)) {
            double balance = accounts.get(account);
            accounts.put(account, balance + amount);
            displayArea.append("Deposited $" + amount + " to account " + account + "\n");
        } else {
            displayArea.append("Account " + account + " not found\n");
        }
    }

    private void transferFunds(String fromAccount, String toAccount, double amount) {
        if (accounts.containsKey(fromAccount) && accounts.containsKey(toAccount)) {
            double fromBalance = accounts.get(fromAccount);
            if (fromBalance >= amount) {
                accounts.put(fromAccount, fromBalance - amount);
                double toBalance = accounts.get(toAccount);
                accounts.put(toAccount, toBalance + amount);
                displayArea.append("Transferred $" + amount + " from account " + fromAccount + " to account " + toAccount + "\n");
            } else {
                displayArea.append("Insufficient funds in account " + fromAccount + "\n");
            }
        } else {
            displayArea.append("One or both accounts not found\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMMockup());
    }
}
