package graphmaster.grapher;

import graphmaster.grapher.expressions.Function;
import graphmaster.grapher.parser.ExpressionParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * The window in which the graph is displayed.
 */
public class Window extends JPanel implements MouseWheelListener, KeyListener, Runnable {
    /**
     * The width of the window.
     */
    private static final int WIDTH = 800;
    /**
     * The height of the window.
     */
    private static final int HEIGHT = 600;

    // The buffered image and graphics context for drawing
    private final BufferedImage buff;
    private final Graphics2D g2d;

    // Expression parser and function to graph
    private final ExpressionParser parser;
    private Function function;

    // Window properties
    private double windowX, windowY, windowWidth, windowHeight;
    private Point mousePt;

    // Field for inputting expressions
    private String textBox;
    // Time variables
    private double yVar;    // Constantly increasing
    private double zVar;    // Cycles from –1 to 1

    /**
     * Constructs a new window.
     */
    public Window() {
        JButton saveButton = new JButton("Save Graph");
        saveButton.setFont(new Font("Courier New", Font.BOLD, 16));
        saveButton.setForeground(new Color(245, 245, 245));
        saveButton.setBackground(Color.BLACK);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> saveImage());
        add(saveButton);

        // Add event listeners
        addMouseWheelListener(this);
        addKeyListener(this);
        addMouseListener(new MyMouseAdapter());
        addMouseMotionListener(new MyMouseMotionAdapter());

        // Set focus and size
        setFocusable(true);
        requestFocusInWindow();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));

        // Create the buffered image and graphics context
        buff = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g2d = buff.createGraphics();

        // Create expression parser and function
        parser = new ExpressionParser();
        textBox = "0";
        function = parser.parse(textBox);

        // Initialize window properties
        windowX = 0.0;
        windowY = 0.0;
        windowHeight = 2.0;
        windowWidth = windowHeight * WIDTH / HEIGHT;
    }

    // This method takes a KeyEvent object and returns a boolean indicating whether the event represents a valid character
    // for a Boolean expression.
    private static boolean isaBoolean(KeyEvent e) {
        // The method checks whether the key character of the KeyEvent is a letter or digit, '^', '-', '+', '', '/', '(',
        // ')', '%', ',', or '.'. If the character is any of these, it returns true; otherwise, it returns false.
        return Character.isLetterOrDigit(e.getKeyChar()) || '^' == e.getKeyChar() || '-' == e.getKeyChar() ||
                '+' == e.getKeyChar() || '*' == e.getKeyChar() || '/' == e.getKeyChar() || '(' == e.getKeyChar() ||
                ')' == e.getKeyChar() || '%' == e.getKeyChar() || ',' == e.getKeyChar() || '.' == e.getKeyChar();
    }

    /**
     * Updates the time variables.
     *
     * @param dt the change in time
     */
    private synchronized void updateDT(double dt) {
        yVar += dt;
        zVar = StrictMath.sin(yVar);
    }

    /**
     * This method is called to paint the part on the screen.
     * <p>
     * It clears the screen, evaluates a function, plots the points and draws the graph.
     * <p>
     * It draws the x-axis, y-axis, function equation, and image.
     *
     * @param g —the graphics context to use for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Clear the screen
        g2d.setColor(new Color(38, 38, 38));
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        synchronized (this) {
            // Evaluate function and plot points
            List<Double> xs = new ArrayList<>();
            List<Double> ys = new ArrayList<>();
            for (int x = 0; WIDTH > x; x++) {
                double xx = toRealX(x);
                double yy = 0.0;
                if (null != this.function) yy = this.function.evaluateAt(xx, this.yVar, this.zVar);
                double scaledY = toScreenY(yy);
                scaledY = Math.min(Math.max(scaledY, -5), HEIGHT + 5);

                xs.add((double) x);
                ys.add(scaledY);
            }

            // Convert point lists to arrays
            int[] xa = new int[xs.size()];
            int[] ya = new int[ys.size()];

            // Converting xs and ys to xa and ya respectively
            for (int i = 0; i < xa.length; i++) {
                Double aDouble = xs.get(i);
                xa[i] = aDouble.intValue();
            }
            for (int i = 0; i < ya.length; i++) {
                Double aDouble = ys.get(i);
                ya[i] = aDouble.intValue();
            }

            // Drawing x-axis and y-axis
            g2d.setColor(new Color(128, 128, 128));
            int xAxisY = toScreenY(0.0);
            g2d.drawLine(0, xAxisY, WIDTH, xAxisY);
            int yAxisX = this.toScreenX();
            g2d.drawLine(yAxisX, 0, yAxisX, HEIGHT);

            // Drawing the graph
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(new Color(92, 179, 133));
            g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
            g2d.drawPolyline(xa, ya, xa.length);

            // Drawing the function equation
            g2d.setFont(new Font("Courier New", Font.PLAIN, 40));
            g2d.setColor(Color.BLACK);
            FontMetrics fontMetrics = this.g2d.getFontMetrics();
            this.g2d.fillRect(0, HEIGHT - fontMetrics.getHeight(), WIDTH, HEIGHT);
            g2d.setColor(new Color(245, 245, 245));
            g2d.drawString("f(x) = " + textBox, 0.0f, HEIGHT - 10.0f);

            // Drawing the axis labels
            g2d.drawString("X", 0, xAxisY - 10);
            this.g2d.drawString("Y", yAxisX + 10, fontMetrics.getHeight() - 20);
        }

        // Drawing the image
        g.drawImage(buff, 0, 0, null);
    }

    // Override the run method of the Runnable interface.
    @Override
    public void run() {

        // Initialize the oldTime variable to zero and the dt variable.
        long oldTime = 0;
        double dt;

        // Enters an infinite loop that updates the dt variable and repaints the GUI.
        while (true) {

            // Get the current time and calculate the elapsed time since the last update.
            long newTime = System.nanoTime();
            dt = (newTime - oldTime) / 1.0E09;
            oldTime = newTime;

            // Update the simulation with the new dt value and repaint the GUI.
            updateDT(dt);
            repaint();

            // Pause the thread for one millisecond to avoid overloading the CPU.
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    // Overrides the keyTyped method of the KeyListener interface.
    @Override
    public void keyTyped(KeyEvent e) {

    }

    // Overrides the keyPressed method of the KeyListener interface.
    @Override
    public void keyPressed(KeyEvent e) {
        // If the backspace key is pressed, removes the last character from the field.
        if (KeyEvent.VK_BACK_SPACE == e.getKeyCode()) {
            if (!textBox.isEmpty()) {
                textBox = textBox.substring(0, textBox.length() - 1);
            }
        } else if (isaBoolean(e)) { // If a letter or number is pressed, adds it to the field.
            textBox += e.getKeyChar();
        } else if (KeyEvent.VK_ENTER == e.getKeyCode()) {    // If the enter key is pressed,
            // parses the field as a function
            // and updates the function variable.
            function = parser.parse(textBox);
            if (null == function) {
                textBox = "";
            }
        }
    }

    // Overrides the keyReleased method of the KeyListener interface.
    @Override
    public void keyReleased(KeyEvent e) {

    }

    // Calculates the bottom and right edges of the GUI.
    private double bottom() {
        return windowY - halfWindowHeight();
    }

    private double right() {
        return windowX - halfWindowWidth();
    }

    // Converts a screen coordinate to a real coordinate.
    private double toRealX(int screenX) {
        return screenX / (double) WIDTH * windowWidth + right();
    }

    private double toRealY(int screenY) {
        return (HEIGHT - screenY) / (double) HEIGHT * windowHeight + bottom();
    }

    // Converts a real coordinate to a screen coordinate.
    private int toScreenX() {
        return (int) ((0.0 - right()) / windowWidth * WIDTH);
    }

    private int toScreenY(double realY) {
        return HEIGHT - (int) ((realY - bottom()) / windowHeight * HEIGHT);
    }

    // Calculates half the width and height of the GUI.
    private double halfWindowWidth() {
        return windowWidth / 2.0;
    }

    private double halfWindowHeight() {
        return windowHeight / 2.0;
    }

    // Overrides the mouseWheelMoved method of the MouseWheelListener interface.
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // Calculates the scale factor based on the wheel rotation
        double scale = StrictMath.pow(1.15, e.getPreciseWheelRotation());

        // Calculates the coordinates of the mouse cursor in the real coordinate system.
        double mxReal = toRealX(e.getX());
        double myReal = toRealY(e.getY());

        // Calculates the relative position of the mouse cursor in the current window.
        double sx = (windowX - mxReal) / windowWidth;
        double sy = (windowY - myReal) / windowHeight;

        // Scales the window size with the calculated scale factor
        windowWidth *= scale;
        windowHeight *= scale;

        // Calculates the new position of the window based on the earlier position and the relative position of the mouse cursor.
        windowX = mxReal + sx * windowWidth;
        windowY = myReal + sy * windowHeight;
    }

    // Inner class that extends MouseAdapter
    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            // Stores the coordinates of the mouse cursor
            mousePt = e.getPoint();
            // Redraws the part
            repaint();
        }
    }

    // Inner class that extends MouseMotionAdapter
    private class MyMouseMotionAdapter extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            // Calculates the difference between the current, and the earlier coordinates of the mouse cursor.
            int dx = e.getX() - mousePt.x;
            int dy = e.getY() - mousePt.y;

            // Calculates the relative movement of the window based on the mouse movement, and the current window size.
            windowX -= dx / (double) WIDTH * windowWidth;
            windowY += dy / (double) HEIGHT * windowHeight;

            // Stores the new coordinates of the mouse cursor
            mousePt = e.getPoint();

            // Redraws the part
            repaint();
        }
    }

    private void saveImage() {
        try {
            // Ask user for a filename and location
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Graph as Image");
            int userSelection = fileChooser.showSaveDialog(this);
            if (JFileChooser.APPROVE_OPTION == userSelection) {
                // Get selected file and create image file
                String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                if (!fileName.endsWith(".png")) {
                    fileName += ".png";
                }
                File imageFile = new File(fileName);

                // Create an image of the current graph
                BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = image.createGraphics();
                paintComponent(g2);
                g2.dispose();

                // Write an image to file
                ImageIO.write(image, "png", imageFile);

                // Show a success message to user
                JOptionPane.showMessageDialog(this, "Image saved successfully.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving image: " + ex.getMessage());
        }
    }

}

