package Game;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.lang.Math;

public class HardGame {
    public static final Random RANDOM = new Random();
    private int guesses; //number of guesses before finding
    private boolean guessed;
    private String m_computer_number;//Number to find

    private JTextPane textPane;
    private JTextField textField;

    //a method that manages the game play with the UI with Swing
    private void play() {
        JFrame frame = new JFrame("Bulls and Cows - Hard edition");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        // add buttons
        JPanel buttonsPanel = new JPanel();
        JLabel inputLabel = new JLabel("Input: ");

        textField = new JTextField(15);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okClick();
            }
        });


        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okClick();

            }
        });


        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                init();
            }
        });


        buttonsPanel.add(inputLabel, BorderLayout.LINE_START);
        buttonsPanel.add(textField, BorderLayout.LINE_START);
        buttonsPanel.add(okButton, BorderLayout.LINE_START);
        buttonsPanel.add(newGameButton, BorderLayout.LINE_END);

        contentPane.add(buttonsPanel, BorderLayout.PAGE_END);

        // add a text area to display tries and other messages to the user
        textPane = new JTextPane();
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);

        // customize rendering in the JTextPane
        SimpleAttributeSet bSet = new SimpleAttributeSet();
        StyleConstants.setAlignment(bSet, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontSize(bSet, 20);
        StyledDocument doc = textPane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), bSet, false);

        contentPane.add(scrollPane, BorderLayout.CENTER);

        frame.setMinimumSize(new Dimension(600, 500));

        //center JFrame on the screen
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int coordX = (objDimension.width - frame.getWidth()) / 2;
        int coordY = (objDimension.height - frame.getHeight()) / 2;
        frame.setLocation(coordX, coordY);

        // display the window
        frame.pack();
        frame.setVisible(true);

        init();

    }

    //method that generates a number to find
    void generateNumber() {

        m_computer_number = String.valueOf(RANDOM.nextInt(9000) + 1000);  //4-digit number between 1000 and 9999 with duplicates

        System.out.println("[CHEAT]" + m_computer_number); // simple cheat to help us to find quickly the number during demo
    }
    //a method that help to count bulls and cows
    int countBetween(char what, String where, int from, int to) {
        int count = 0;
        for (int i = from; i < to; ++i) {
            if(where.charAt(i) == what)
                count++;
        }
        return count;
    }
    // a method that returns bulls and cows, firstly count bulls, then cows(number that is bull is not counted like cow)
    Pair<Integer, Integer> bullsAndCows(String value, String m_computer_number) {
        int bulls = 0, cows = 0;
        StringBuilder enteredStr = new StringBuilder(value);
        StringBuilder numberStr = new StringBuilder(m_computer_number);

        for (int i = 0; i < numberStr.length(); i++) {

            if (value.charAt(i) == m_computer_number.charAt(i)) {

                bulls++;
                enteredStr.setCharAt(i, ' ');
                numberStr.setCharAt(i, ' ');

            }
        }
        value = enteredStr.toString();
        m_computer_number = numberStr.toString();

        for (int i = 0; i < numberStr.length(); i++) {
            if (value.charAt(i) == ' ') continue;
            int countLeft = countBetween(value.charAt(i), value, 0, i);
            int countInComputerNumber = countBetween(value.charAt(i), m_computer_number, 0, m_computer_number.length());
            if (countInComputerNumber != 0 && countLeft < countInComputerNumber)
                cows++;
        }
        return new Pair<Integer, Integer>(bulls, cows);
    }
    // manage user click on ok
    private void okClick() {
        // we get user input
        String userInput = textField.getText();
        int entered = -1;


        entered = Integer.parseInt(userInput);
        if(entered < 1000 || entered > 9999){
            textField.setText("");
            JOptionPane.showMessageDialog(null, "You must enter an integer from 1000 to 9999", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Pair<Integer, Integer> bullsAndCows= bullsAndCows(userInput, m_computer_number);

        if (userInput.equals(m_computer_number)) {
            guessed = true;
        } else {
            updateText(entered + "\t\t\t\t" + bullsAndCows.first + " Bulls and " + bullsAndCows.second + " Cows");

        }

        guesses++;
        //count  and show guesses
        if (guessed) {
            updateText("\n" + entered + "\n\n You won after " + guesses + " guesses!");

        }
        textField.setText("");
    }
    private void updateText(String msg) {
        textPane.setText(textPane.getText() + "\n" + msg);
    }
    private void init() {
        generateNumber();
        guesses = 0;
        guessed = false;
        textPane.setText("You must guess a 4-digits number from 1000 to 9999");
        textField.setText("");
    }

    public static void main() {
        HardGame hardGame = new HardGame();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                hardGame.play();
            }
        });
    }
}
