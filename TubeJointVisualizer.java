import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;
import java.text.DecimalFormat;

/**
 * TubeJointVisualizer - A Java Swing application to visualize rectangular tube joints.
 * This class sets up the main frame, control panel, and visualization panel.
 */
public class TubeJointVisualizer extends JFrame {

    private static final double STEEL_DENSITY_G_PER_MM3 = 0.00785;

    private final TubeParameters params;
    private final VisualizationPanel visualizationPanel;
    private final JTextArea outputArea = new JTextArea(4, 20);
    private final JLabel statusLabel = new JLabel("Hover over the visualization to see coordinates.");

    // Data structure to hold all tube parameters
    private static class TubeParameters {
        double width = 50;
        double height = 100;
        double thickness = 5;
        double length = 300;
        double jointAngle = 90; // The internal angle of the joint in degrees (e.g., 90 for corner)
    }

    public TubeJointVisualizer() {
        // --- 1. Initialization ---
        params = new TubeParameters();
        setTitle("Rectangular Tube Joint Visualizer (Java Desktop)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- 2. Create UI Components ---
        visualizationPanel = new VisualizationPanel(statusLabel);
        JPanel controlPanel = createControlPanel();
        JPanel outputPanel = createOutputPanel();

        // --- 3. Assemble Frame ---
        add(controlPanel, BorderLayout.WEST);
        add(visualizationPanel, BorderLayout.CENTER);
        add(outputPanel, BorderLayout.SOUTH);

        // --- 4. Final Setup ---
        updateVisualizationAndOutput();
        pack(); // Size the frame based on component sizes
        setSize(1000, 700); // Set a preferred starting size
        setLocationRelativeTo(null); // Center the window
    }

    /**
     * Creates the control panel for tube and joint parameter input.
     */
    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(280, 0));
        panel.setBackground(new Color(31, 41, 55)); // Gray-800
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Use DecimalFormat for consistent input parsing
        DecimalFormat df = new DecimalFormat("#.##");

        // --- Tube Dimensions Section ---
        panel.add(createSectionTitle("Tube Parameters (mm)"));
        
        // Width and Height
        panel.add(createNumericInput("Width (W):", params.width, 10, 200, 
            e -> params.width = Double.parseDouble(((JTextField)e.getSource()).getText())));
        panel.add(createNumericInput("Height (H):", params.height, 10, 200,
            e -> params.height = Double.parseDouble(((JTextField)e.getSource()).getText())));
        
        // Thickness and Length
        panel.add(createNumericInput("Thickness (T):", params.thickness, 1, 20,
            e -> params.thickness = Double.parseDouble(((JTextField)e.getSource()).getText())));
        panel.add(createNumericInput("Length (L):", params.length, 50, 800,
            e -> params.length = Double.parseDouble(((JTextField)e.getSource()).getText())));

        // Apply Button
        JButton applyButton = new JButton("Apply Dimensions & Update");
        applyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        applyButton.setBackground(new Color(37, 99, 235)); // Blue-600
        applyButton.setForeground(Color.WHITE);
        applyButton.setFocusPainted(false);
        applyButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        applyButton.setMaximumSize(new Dimension(250, 40));
        applyButton.addActionListener(e -> updateVisualizationAndOutput());
        panel.add(Box.createVerticalStrut(15));
        panel.add(applyButton);
        panel.add(Box.createVerticalStrut(25));


        // --- Joint Angle Section ---
        panel.add(createSectionTitle("Joint Angle (Miter)"));
        
        JLabel angleLabel = new JLabel("Angle: " + df.format(params.jointAngle) + "°");
        angleLabel.setForeground(new Color(147, 197, 253)); // Blue-300
        angleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(angleLabel);

        JSlider angleSlider = new JSlider(JSlider.HORIZONTAL, 30, 150, (int)params.jointAngle);
        angleSlider.setBackground(new Color(31, 41, 55));
        angleSlider.setForeground(Color.WHITE);
        angleSlider.setMajorTickSpacing(30);
        angleSlider.setMinorTickSpacing(5);
        angleSlider.setPaintTicks(true);
        angleSlider.setPaintLabels(true);
        angleSlider.addChangeListener(e -> {
            params.jointAngle = angleSlider.getValue();
            angleLabel.setText("Angle: " + df.format(params.jointAngle) + "°");
            updateVisualizationAndOutput();
        });
        angleSlider.setMaximumSize(new Dimension(250, 50));
        panel.add(angleSlider);
        
        panel.add(Box.createVerticalStrut(10));
        
        // Preset Buttons
        JPanel presetPanel = new JPanel(new GridLayout(1, 3, 5, 0));
        presetPanel.setBackground(new Color(31, 41, 55));
        int[] presetAngles = {45, 90, 135};
        for (int angle : presetAngles) {
            JButton presetBtn = new JButton(angle + "°");
            presetBtn.addActionListener(e -> {
                params.jointAngle = angle;
                angleSlider.setValue(angle);
                angleLabel.setText("Angle: " + angle + "°");
                updateVisualizationAndOutput();
            });
            presetPanel.add(presetBtn);
        }
        presetPanel.setMaximumSize(new Dimension(250, 30));
        panel.add(presetPanel);


        panel.add(Box.createVerticalGlue()); // Push everything to the top

        return panel;
    }

    /**
     * Creates a text area panel for technical output/dimensions.
     */
    private JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        panel.setBackground(new Color(17, 24, 39)); // Gray-900

        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setForeground(new Color(255, 255, 255));
        outputArea.setBackground(new Color(17, 24, 39));
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(75, 85, 99))); // Gray-500
        JButton copyButton = new JButton("Copy Analysis");
        copyButton.setToolTipText("Copy the output text to your clipboard.");
        copyButton.addActionListener(e -> copyOutputToClipboard());

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        statusLabel.setForeground(new Color(191, 219, 254));
        footer.add(copyButton, BorderLayout.WEST);
        footer.add(statusLabel, BorderLayout.CENTER);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Helper to create styled section titles.
     */
    private JLabel createSectionTitle(String title) {
        JLabel label = new JLabel(title);
        label.setFont(new Font("Inter", Font.BOLD, 14));
        label.setForeground(new Color(52, 211, 163)); // Emerald Green
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(75, 85, 99)));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setMaximumSize(new Dimension(250, 25));
        return label;
    }

    /**
     * Helper to create numeric input fields.
     */
    private JPanel createNumericInput(String labelText, double initialValue, double min, double max, ActionListener listener) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setBackground(new Color(31, 41, 55));
        
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        panel.add(label, BorderLayout.WEST);

        JTextField textField = new JTextField(String.valueOf(initialValue));
        textField.setBackground(new Color(17, 24, 39));
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setPreferredSize(new Dimension(80, 25));
        
        // Add action listener to update on Enter or focus loss (for simplicity, only enter is used)
        textField.addActionListener(listener);
        textField.addActionListener(e -> updateVisualizationAndOutput());
        
        panel.add(textField, BorderLayout.EAST);
        panel.setMaximumSize(new Dimension(250, 30));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        return panel;
    }

    /**
     * Updates the visualization panel and the technical output area.
     */
    private void updateVisualizationAndOutput() {
        // Validation check for thickness
        if (params.thickness * 2 >= Math.min(params.width, params.height)) {
            JOptionPane.showMessageDialog(this, 
                "Error: Thickness is too large! Wall thickness must be less than half of the minimum dimension (Width or Height).", 
                "Geometry Error", JOptionPane.ERROR_MESSAGE);
            // Reset to a safe value
            params.thickness = Math.min(params.width, params.height) * 0.1;
            // A professional app would update the input field here, but we proceed with current params.
            return;
        }

        // --- 1. Update Visualization ---
        visualizationPanel.setParameters(params);
        
        // --- 2. Update Technical Output ---
        DecimalFormat df = new DecimalFormat("#.##");
        
        // Calculate the cut angle
        double cutAngle = params.jointAngle / 2.0;

        double crossSectionArea = computeCrossSectionArea(params);
        double weightPerMeterGrams = crossSectionArea * STEEL_DENSITY_G_PER_MM3 * 1000; // length = 1000mm
        double weightPerMeterKg = weightPerMeterGrams / 1000.0;

        String output = "--- Joint Geometry Analysis ---\n";
        output += "Internal Joint Angle (θ): " + df.format(params.jointAngle) + "°\n";
        output += "Miter Cut Angle (α): " + df.format(cutAngle) + "° (relative to face)\n";
        output += "Tube Cross-Section: " + df.format(params.width) + "W x " + df.format(params.height) + "H\n";
        output += "Wall Thickness: " + df.format(params.thickness) + "T\n";
        output += "Overall Length (L): " + df.format(params.length) + " mm (Note: Only cut face shown in 2D view)\n\n";
        output += "--- Material Estimate (Steel) ---\n";
        output += "Net Cross-Sectional Area: " + df.format(crossSectionArea) + " mm²\n";
        output += "Approx. Weight per Meter: " + df.format(weightPerMeterKg) + " kg";
        
        outputArea.setText(output);
    }

    private void copyOutputToClipboard() {
        StringSelection selection = new StringSelection(outputArea.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        statusLabel.setText("Analysis copied to clipboard.");
    }

    private double computeCrossSectionArea(TubeParameters p) {
        double outerArea = p.width * p.height;
        double innerWidth = Math.max(0, p.width - 2 * p.thickness);
        double innerHeight = Math.max(0, p.height - 2 * p.thickness);
        double innerArea = innerWidth * innerHeight;
        return Math.max(0, outerArea - innerArea);
    }

    /**
     * VisualizationPanel - A custom JPanel for drawing the 2D joint cross-section.
     */
    private static class VisualizationPanel extends JPanel {
        private TubeParameters currentParams;
        private final JLabel statusLabel;

        public VisualizationPanel(JLabel statusLabel) {
            this.statusLabel = statusLabel;
            setBackground(new Color(17, 24, 39)); // Gray-900 background
            // Placeholder label for 3D description
            JLabel info = new JLabel("2D Cross-Section View (Simulates 3D Joint Cut)", SwingConstants.CENTER);
            info.setFont(new Font("Inter", Font.PLAIN, 16));
            info.setForeground(new Color(107, 114, 128)); // Gray-400
            setLayout(new BorderLayout());
            add(info, BorderLayout.NORTH);

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    statusLabel.setText("Mouse: (" + e.getX() + ", " + e.getY() + ")");
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    statusLabel.setText("Hover over the visualization to see coordinates.");
                }
            });
        }

        public void setParameters(TubeParameters params) {
            this.currentParams = params;
            repaint(); // Trigger drawing update
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (currentParams == null) return;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            
            // Scaling factor to fit the max dimension (100mm) into the panel area
            double scale = Math.min(getWidth(), getHeight()) / (currentParams.height * 2.5);

            // Calculate the miter cut angle (alpha)
            double cutAngleDeg = currentParams.jointAngle / 2.0;
            double cutAngleRad = Math.toRadians(cutAngleDeg);

            // --- Draw Tube A (Horizontal, Fixed) ---
            g2d.setColor(new Color(52, 211, 163)); // Emerald Green
            drawCutTube(g2d, centerX, centerY, currentParams, scale, -cutAngleRad, 0.0);

            // --- Draw Tube B (Rotated) ---
            g2d.setColor(new Color(251, 146, 60)); // Orange
            double rotationAngleRad = Math.toRadians(180.0 - currentParams.jointAngle);
            drawCutTube(g2d, centerX, centerY, currentParams, scale, cutAngleRad, rotationAngleRad);
            
            // --- Draw Joint Center Marker ---
            g2d.setColor(Color.WHITE);
            g2d.fillOval(centerX - 4, centerY - 4, 8, 8);
        }

        /**
         * Draws the miter-cut end face of a tube.
         * Note: This is a simplified 2D representation of the cut geometry.
         */
        private void drawCutTube(Graphics2D g2d, int centerX, int centerY, TubeParameters p, double scale, double cutAngleRad, double rotationAngle) {
            double W = p.width * scale;
            double H = p.height * scale;
            double T = p.thickness * scale;
            
            // Length along the cut line (simplified for 2D cross-section view)
            double cutLength = W / Math.sin(cutAngleRad);

            // Outer Miter Cut Shape (Trapezoid or Triangle)
            Path2D.Double outerShape = new Path2D.Double();
            
            // Start at the center point (0, 0 in relative coordinates)
            outerShape.moveTo(0, H / 2); // Top corner
            outerShape.lineTo(W / 2, H / 2); // Top right corner
            outerShape.lineTo(W / 2, -H / 2); // Bottom right corner
            outerShape.lineTo(0, -H / 2); // Bottom corner
            
            // This drawing needs to represent the *projection* of the cut end face, which is complex.
            // Simplified approach: Draw the full rectangular cross-section and the inner hole.
            
            // 1. Draw Outer Cross-Section (Centered at (0, 0))
            g2d.translate(centerX, centerY);
            g2d.rotate(rotationAngle);
            
            // Outer Rectangle
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect((int)(-W / 2), (int)(-H / 2), (int)W, (int)H);

            // 2. Draw Inner Hole
            double Wi = W - 2 * T;
            double Hi = H - 2 * T;
            g2d.setColor(getBackground()); // Use background color to simulate the hole
            g2d.fillRect((int)(-Wi / 2), (int)(-Hi / 2), (int)Wi, (int)Hi);

            // 3. Draw Inner Hole Outline (for clarity)
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawRect((int)(-Wi / 2), (int)(-Hi / 2), (int)Wi, (int)Hi);
            
            // 4. Draw the Cut Angle Line (Simulated)
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(3));
            
            // The cut line is shown across the W dimension.
            // The slope of the cut line is determined by the angle.
            // Simplified: Draw a line from (-W/2, H/2) to (W/2, H/2) and rotate it.
            // For a proper 2D side view, the cut would be a line. For a 2D end view, it's just the cross-section.
            // We draw a line to indicate the cut plane intersection for demonstration.
            
            // Draw a line representing the cut edge.
            // Length of the cut edge is H (if W > H) or W (if H > W).
            double miterLength = Math.max(W, H) / 2.0;

            // Simple line representing the cut edge in the local X-Z plane (shown as X-Y here)
            // This line visualization is a *conceptual* representation of the cut angle in 2D.
            // It runs along the Y (Height) dimension.
            // X-position of the cut depends on the angle.
            
            double xCut = Math.sin(cutAngleRad) * miterLength;
            double yCut = Math.cos(cutAngleRad) * miterLength;

            // In the 2D plane (X, Y), the cut is a single line, we draw the centerline rotation.
            // Draw the angle indication
            g2d.setColor(new Color(255, 255, 255, 150));
            g2d.rotate(-rotationAngle); // Reset rotation for drawing the cut indicator line in global space
            g2d.drawLine(0, 0, (int)(miterLength * Math.cos(cutAngleRad) * (rotationAngle == 0 ? 1 : -1)), (int)(miterLength * Math.sin(cutAngleRad) * (rotationAngle == 0 ? 1 : -1)));

            g2d.rotate(rotationAngle); // Restore rotation for subsequent drawing
            
            // Reset transformation
            g2d.rotate(-rotationAngle);
            g2d.translate(-centerX, -centerY);
        }
    }

    public static void main(String[] args) {
        // Use a clean look and feel for a desktop app
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set System Look and Feel.");
        }
        
        // Launch the application on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new TubeJointVisualizer().setVisible(true);
        });
    }
}