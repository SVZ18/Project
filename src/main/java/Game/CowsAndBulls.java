package Game;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class CowsAndBulls {

    public static final Random RANDOM = new Random();
    private int number; // number to find
    private int guesses; //number of guesses before finding
    private boolean guessed;

    private JTextPane textPane;
    private JTextField textField;

    //method that generates a number to find
    private void generateNumber(){
        do {
            number = RANDOM.nextInt(9000) + 1000; //4-digit number between 1000 and 9999
        } while(hasDuplicates(number)); // 4-digit number with no duplicates

        System.out.println("[CHEAT]" + number); // simple cheat to help us to find quickly the number during demo
    }

    private boolean hasDuplicates(int number) {
        boolean[] digits = new boolean[10];

        while (number > 10) {
            int last = number % 10;

            if (digits[last])
                return true;

            digits[last] = true;
            number = number / 10;
        }
        return false;
    }
    // a method that returns bulls and cows
    private int[] bullsAndCows(int entered) {
        int bulls = 0, cows = 0;
        String enteredStr = String.valueOf(entered);
        String numberStr = String.valueOf(number);

        for (int i = 0; i < numberStr.length(); i++) {
            char c = enteredStr.charAt(i);

            if (c == numberStr.charAt(i)) {
                bulls++;
            } else if (numberStr.contains(String.valueOf(c))) {
                cows++;
            }

        }
        return new int[] {bulls, cows};
    }
    //a method that manages the game play with the UI with Swing
    private void play() {
        JFrame frame = new JFrame("Bulls and Cows on SSaurel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        // add buttons
        JPanel buttonsPanel = new JPanel();
        JLabel inputLabel = new JLabel("Input: ");
        buttonsPanel.add(inputLabel, BorderLayout.LINE_START);

        textField = new JTextField(15);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okClick();
            }
        });
        buttonsPanel.add(textField, BorderLayout.LINE_START);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okClick();
            }
        });
        buttonsPanel.add(okButton, BorderLayout.LINE_START);

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                init();
            }
        });

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
        Dimension objDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int coordX = (objDimension.width - frame.getWidth()) / 2;
        int coordY = (objDimension.height - frame.getHeight()) / 2;
        frame.setLocation(coordX, coordY);

        // display the window
        frame.pack();
        frame.setVisible(true);

        init();

    }
    // manage user click on ok
    private void okClick() {
        // we get user input
        String userInput = textField.getText();
        int entered = -1;

        try {
            entered = Integer.parseInt(userInput);
        } catch (Exception e) {
            textField.setText("");
            JOptionPane.showMessageDialog(null, "You must enter an integer", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (hasDuplicates(entered) || entered < 1000 || entered > 9999) {
            textField.setText("");
            JOptionPane.showMessageDialog(null, "You must enter an integer with no duplicates digits and with 4-digits", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        guesses++;
        // count bulls and cows
        int[] bullsAndCows = bullsAndCows(entered);

        if (bullsAndCows[0] == 4) {
            guessed = true;
        } else {
            updateText(entered + "\t\t\t\t" + bullsAndCows[0] + " Bulls and " + bullsAndCows[1] + " Cows");

        }
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
        textPane.setText("You must guess a 4-digits number with no duplicate digits");
        textField.setText("");
    }
    public static void main() {
        CowsAndBulls cowsAndBulls = new CowsAndBulls();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                cowsAndBulls.play();
            }
        });
    }
}

