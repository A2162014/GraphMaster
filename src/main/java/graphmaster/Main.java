package graphmaster;

import graphmaster.grapher.Window;

import javax.swing.*;

public class Main {

    public Main() {
        super();
    }

    public static void main(final String[] args) {
        final Window window = new Window();

        final JFrame frame = new JFrame("GraphMaster");
        frame.add(window);
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        final Thread thread = new Thread(window);
        thread.start();
    }
}
