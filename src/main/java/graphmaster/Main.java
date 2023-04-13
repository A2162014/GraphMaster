package graphmaster;

import graphmaster.grapher.Window;

import javax.swing.*;

/**
 * The main entry point of the GraphMaster app.
 */
public enum Main {
    // An empty enumeration instance
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
        // Create a window for the graph display
        final Window window = new Window();

        // Create a frame with the window as its content pane
        final JFrame frame = new JFrame(Main.NAME);
        frame.add(window);
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Start a separate thread to run the app logic
        final Thread thread = new Thread(window);
        thread.start();
    }
}
