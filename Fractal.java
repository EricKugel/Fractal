import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.util.Hashtable;

public class Fractal extends JFrame {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private static double angle = Math.PI / 8;
    private static double lengthChange = .75;

    public Fractal() {
        setTitle("Fractal");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        JPanel main = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(500, 500);
            }

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                draw(g, Math.PI / 2, Fractal.HEIGHT / 4.0, Fractal.WIDTH / 2, Fractal.HEIGHT);
            }
        };
        add(main);
        pack();

        JFrame controlFrame = new JFrame();
        controlFrame.setVisible(true);
        controlFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        controlFrame.setResizable(false);

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
        controlFrame.add(sliderPanel);
        JSlider angleSlider = new JSlider(JSlider.HORIZONTAL, 0, (int) (Math.PI * 100), (int) angle);
        JSlider lengthChangeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (lengthChange * 100));

        Hashtable<Integer, JLabel> angleLabels = new Hashtable<Integer, JLabel>();
        angleLabels.put(0, new JLabel("0"));
        angleLabels.put(314, new JLabel("3.14"));
        angleSlider.setLabelTable(angleLabels);
        Hashtable<Integer, JLabel> lengthChangeLabels = new Hashtable<Integer, JLabel>();
        lengthChangeLabels.put(0, new JLabel("0"));
        lengthChangeLabels.put(100, new JLabel("1"));
        lengthChangeSlider.setLabelTable(lengthChangeLabels);
        
        for (JSlider slider : new JSlider[] {angleSlider, lengthChangeSlider}) {
            slider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    Fractal.setAngle(angleSlider.getValue() / 100.0);
                    Fractal.setLengthChange(lengthChangeSlider.getValue() / 100.0);
                    main.repaint();
                }
            });
            slider.setMajorTickSpacing(10);
            slider.setPaintLabels(true);
            slider.setPaintTicks(true);
            sliderPanel.add(slider);
        }
        controlFrame.pack();
    }   

    public void draw(Graphics g, double angle, double length, int x, int y) {
        if (length < 1) {
            return;
        } else {
            for (int i = -1; i <= 1; i += 2) {
                double theta = angle + Fractal.angle * i;
                int x1 = (int) (x + length * Math.cos(theta));
                int y1 = (int) (y - length * Math.sin(theta));
                g.drawLine(x, y, x1, y1);
                draw(g, theta, length * Fractal.lengthChange, x1, y1);
            }
        }
    }

    public static void main(String[] arg0) {
        new Fractal();
    }

    public static void setAngle(double angle) {
        Fractal.angle = angle;
    }

    public static void setLengthChange(double lengthChange) {
        Fractal.lengthChange = lengthChange;
    }
}