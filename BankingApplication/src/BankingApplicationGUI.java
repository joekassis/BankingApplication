import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BankingApplicationGUI {
    private static double balance = 0.0;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Banking Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(null); 
        frame.getContentPane().setBackground(Color.WHITE); 

        
        JLabel titleLabel = new JLabel("Banking Application");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(0, 128, 0)); 
        titleLabel.setBounds(100, 30, 300, 30);
        frame.add(titleLabel);

        
        JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                g2.setColor(new Color(200, 200, 200)); // Light gray border
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
            }
        };
        centerPanel.setBounds(100, 100, 300, 300);
        centerPanel.setLayout(null);
        frame.add(centerPanel);

       
        JButton depositButton = createRoundedButton("Deposit");
        JButton withdrawButton = createRoundedButton("Withdraw");
        JButton checkBalanceButton = createRoundedButton("Check Balance");
        JButton exitButton = createRoundedButton("Exit");

        
        depositButton.setBounds(60, 50, 180, 40);
        withdrawButton.setBounds(60, 110, 180, 40);
        checkBalanceButton.setBounds(60, 170, 180, 40);
        exitButton.setBounds(60, 230, 180, 40);

       
        centerPanel.add(depositButton);
        centerPanel.add(withdrawButton);
        centerPanel.add(checkBalanceButton);
        centerPanel.add(exitButton);

        
        JLabel footerLabel = new JLabel("Developed by Joe");
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(150, 150, 150)); 
        footerLabel.setBounds(0, 430, 500, 30);
        frame.add(footerLabel);

       
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int frameWidth = frame.getWidth();
                int frameHeight = frame.getHeight();
                titleLabel.setBounds((frameWidth - 300) / 2, 30, 300, 30);
                centerPanel.setBounds((frameWidth - 300) / 2, (frameHeight - 300) / 2, 300, 300);
                footerLabel.setBounds((frameWidth - 500) / 2, frameHeight - 70, 500, 30);
            }
        });

        depositButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter deposit amount:");
            if (isValidAmount(input)) {
                double amount = Double.parseDouble(input);
                deposit(amount);
                JOptionPane.showMessageDialog(frame, "Deposit Successful! New Balance: $" + balance);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid amount. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        withdrawButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter withdrawal amount:");
            if (isValidAmount(input)) {
                double amount = Double.parseDouble(input);
                if (withdraw(amount)) {
                    JOptionPane.showMessageDialog(frame, "Withdrawal Successful! New Balance: $" + balance);
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient funds. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid amount. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        checkBalanceButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Current Balance: $" + balance);
        });

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frame, "Thank you for using our banking application!");
                System.exit(0);
            }
        });

        
        frame.setVisible(true);
    }

   
    private static JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 128, 0)); 
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(button.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                super.paint(g2, c);
            }
        });
        return button;
    }

    private static void deposit(double amount) {
        balance += amount;
    }

    private static boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    private static boolean isValidAmount(String input) {
        try {
            double amount = Double.parseDouble(input);
            return amount > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}