package learning_notinactualproject;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/**
 * A numeric key pad panel using <code>Action</code> with key bindings.
 *
 * @author John B. Matthews; distribution per LGPL
 */
public class KeyPadPanel extends JPanel {

    private int maxDigits = 10;
    private final JTextArea text = new JTextArea(1, maxDigits);
    private final JButton clear = new JButton(new Clear("Clear"));
    private final JButton enter = new JButton(new Enter("Enter"));
    private final List<NumberButton> numbers = new ArrayList<NumberButton>();

    /**
     * Construct a numeric key pad that accepts up to ten digits.
     */
    public KeyPadPanel() {
        super(new BorderLayout());

        JPanel display = new JPanel();
        text.setFont(new Font("Dialog", Font.PLAIN, 24));
        text.setEditable(false);
        text.setFocusable(false);
        display.add(text);
        this.add(display, BorderLayout.NORTH);

        JPanel pad = new JPanel(new GridLayout(4, 3));
        for (int i = 0; i < 10; i++) {
            NumberButton n = new NumberButton(i);
            numbers.add(n);
            if (i > 0) {
                pad.add(n);
            }
        }
        pad.add(clear);
        clear.setFocusable(false);
        this.getInputMap().put(KeyStroke.getKeyStroke(
                KeyEvent.VK_CLEAR, 0), clear.getText());
        this.getActionMap().put(clear.getText(), new Click(clear));
        pad.add(numbers.get(0));
        pad.add(enter);
        enter.setFocusable(false);
        this.getInputMap().put(KeyStroke.getKeyStroke(
                KeyEvent.VK_ENTER, 0), enter.getText());
        this.getActionMap().put(enter.getText(), new Click(enter));
        this.add(pad, BorderLayout.CENTER);
    }

    /**
     * Construct a numeric key pad that accepts up to <code>maxDigits<code>.
     */
    public KeyPadPanel(int maxDigits) {
        this();
        this.maxDigits = maxDigits;
        this.text.setColumns(maxDigits);
    }

    private class Clear extends AbstractAction {

        public Clear(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            text.setText("");
            for (NumberButton n : numbers) {
                n.setEnabled(true);
            }
        }
    }

    private class Enter extends AbstractAction {

        public Enter(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Entered: " + text.getText());
            clear.getAction().actionPerformed(e);
        }
    }

    private class Click extends AbstractAction {

        JButton button;

        public Click(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            button.doClick();
        }
    }

    /*
     * A numeric button with digit key bindings that appends to
     * <code>text<code>, accepting no more than <code>maxDigits<code>.
     */
    private class NumberButton extends JButton {

        public NumberButton(int number) {
            super(String.valueOf(number));
            this.setFocusable(false);
            this.setAction(new AbstractAction(this.getText()) {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String cmd = e.getActionCommand();
                    if (text.getText().length() < maxDigits) {
                        text.append(cmd);
                    }
                    if (text.getText().length() == maxDigits) {
                        for (NumberButton n : numbers) {
                            n.setEnabled(false);
                        }
                    }
                }
            });
            KeyPadPanel.this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_0 + number, 0), this.getText());
            KeyPadPanel.this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0 + number, 0), this.getText());
            KeyPadPanel.this.getActionMap().put(this.getText(), new Click(this));
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame f = new JFrame();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.add(new KeyPadPanel(7));
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });
    }
}



