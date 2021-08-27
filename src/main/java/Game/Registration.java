package Game;

import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Registration {
    private static JLabel userNameLabel, repeatPsLabel, accountLabel, failLabel;
    private static JTextField userNameText;
    private static JLabel passwordLabel;
    private static JPasswordField passwordText, repeatPsText;
    private static JButton button;
    private static JButton button2, button3, button4, button5;
    private static JLabel success;
    private static Font titleFont = new Font("Times New Roman", Font.BOLD, 30);
    private static Font normalTitle = new Font("Times New Roman", Font.PLAIN, 25);
    private String username;
    private String password;

    public static void main() {


        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        accountLabel = new JLabel("Create your account");
        accountLabel.setBounds(100, 70, 600, 50);
        accountLabel.setFont(normalTitle);
        panel.add(accountLabel);

        userNameLabel = new JLabel("Username");
        userNameLabel.setBounds(100, 200, 80, 25);
        panel.add(userNameLabel);

        userNameText = new JTextField(20);
        userNameText.setBounds(240, 200, 165, 25);
        panel.add(userNameText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 240, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(240, 240, 165, 25);
        panel.add(passwordText);

        repeatPsLabel = new JLabel("Repeat password");
        repeatPsLabel.setBounds(100, 280, 100, 25);
        panel.add(repeatPsLabel);

        repeatPsText = new JPasswordField();
        repeatPsText.setBounds(240, 280, 165, 25);
        panel.add(repeatPsText);

        button = new JButton("Exit");
        button.setBounds(100, 320, 100, 25);
        panel.add(button);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });


                button2 = new JButton("Register");
                button2.setBounds(240, 320, 100, 25);
                panel.add(button2);
                frame.setVisible(true);
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String userName = userNameText.getText();
                        String password = passwordText.getText();

                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/game", "root", "pate9gaV!");

                            String query = "INSERT INTO users(username, password)" + "VALUES('" + userName + "', '" + password + "')";
                            Statement sta = connection.createStatement();
                            int x = sta.executeUpdate(query);


                            JFrame frame2 = new JFrame();
                            JPanel panel2 = new JPanel();
                            frame2.setSize(600, 600);
                            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frame2.add(panel2);

                            panel2.setLayout(null);

                            success = new JLabel("Registration successful.");
                            success.setBounds(50, 150, 300, 25);
                            panel2.add(success);

                            button3 = new JButton("Start new game");
                            button3.setBounds(50, 200, 200, 25);
                            panel2.add(button3);


                            button3.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    CowsAndBulls.main();

                                }
                            });

                            button = new JButton("Exit");
                            button.setBounds(50, 250, 100, 25);
                            panel2.add(button);
                            frame2.setVisible(true);
                            button.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.exit(0);
                                }
                            });


                        } catch (Exception exception) {

//                            JFrame frame3 = new JFrame();
//                            JPanel panel3 = new JPanel();
//                            frame3.setSize(500, 300);
//                            frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                            frame3.add(panel3);
//
//                            panel3.setLayout(null);
//
//                            failLabel = new JLabel("Username already exists ");
//                            failLabel.setBounds(30, 30, 400, 25);
//                            panel3.add(failLabel);
//
//                            panel3.setVisible(true);


                        }


                    }

                });

    }
}

