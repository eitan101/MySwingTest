package swingtest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.ease.Spline;
import org.pushingpixels.trident.ease.TimelineEase;
import org.pushingpixels.trident.interpolator.PropertyInterpolator;

public class ButtonFg extends JFrame {
    public ButtonFg() {

        final JButton button = new JButton("sample");
        final Color beginColor = new Color(100, 100, 100);
        final Color endColor = new Color(30, 30, 30);
        button.setForeground(Color.white);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setBackground(beginColor);
        final Dimension endDim = new Dimension(110, 55);
        final Dimension startDim = new Dimension(100, 50);
        button.setPreferredSize(startDim);
        this.setLayout(new FlowLayout());
        this.add(button);
        final Timeline rolloverTimeline = new Timeline(button);
        rolloverTimeline.addPropertyToInterpolate(Timeline.<Color>property("background").from(beginColor).to(endColor));
        rolloverTimeline.addPropertyToInterpolate(Timeline.<Dimension>property("size").from(startDim).to(endDim).interpolatedWith(DimensionInterpolator));
        rolloverTimeline.setEase(new Spline(0.9f));

        rolloverTimeline.setDuration(200);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rolloverTimeline.play();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rolloverTimeline.playReverse();
            }
        });
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ButtonFg().setVisible(true);
            }
        });
    }
    public static PropertyInterpolator<Dimension> DimensionInterpolator = new PropertyInterpolator<Dimension>() {
        @Override
        public Class getBasePropertyClass() {
            return Dimension.class;
        }

        @Override
        public Dimension interpolate(Dimension t, Dimension t1, float f) {
            return new Dimension(
                    (int) (f * t1.getWidth() + (1 - f) * t.getWidth()),
                    (int) (f * t1.getHeight() + (1 - f) * t.getHeight()));

        }
    };
}
