package graphmaster;

import graphmaster.grapher.Window;

import javax.swing.*;
import java.awt.*;

/**
 * The main entry point of the GraphMaster app.
 */
public enum Main {
    ;


    /**
     * The name of the GraphMaster app.
     */
    private static final String NAME = "GraphMaster";

    /**
     * Starts the GraphMaster app.
     *
     * @param args the command-line arguments (not used in this app)
     */
    public static void main(final String[] args) {
        try {
            // Create a window for the graph display
            Window window = new Window();

            // Create a frame with the window as its content pane
            JFrame frame = new JFrame(NAME);
            frame.add(window);
            frame.setResizable(false);
            frame.pack();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Start a separate thread to run the app logic
            Thread thread = new Thread(window);
            thread.start();
        } catch (HeadlessException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            // Handle any exceptions during window initialization
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while initializing the window.");
        }
    }
}
