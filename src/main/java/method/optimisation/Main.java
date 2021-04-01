package method.optimisation;

/**
 * Created by Danil Lyskin at 09:14 01.04.2021
 */

import org.springframework.data.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

import static method.optimisation.DrawFrame.*;

public class Main
{
    public static void main(String[] args)
    {
        DrawFrame frame = new DrawFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

enum MethodType {
    FIB,
    DICH,
    GOLD,
    PARABOLA,
    BRENT
}

class DrawFrame extends JFrame implements ActionListener {
    public DrawFrame() {
        final int X = 150;

        setTitle("Графики");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        JButton dichotomyButton = new JButton("Метод дихотомии");
        JButton fibonachiButton = new JButton("Метод фибоначчи");
        JButton goldenRatioButton = new JButton("Метод золотого сечения");
        JButton parabolaButton = new JButton("Метод параболы");
        JButton brentButton = new JButton("Метод Брента");
        dichotomyButton.setBounds(DEFAULT_WIDTH / 2 + X, 100, 200, 30);
        fibonachiButton.setBounds(DEFAULT_WIDTH / 2 + X, 140, 200, 30);
        goldenRatioButton.setBounds(DEFAULT_WIDTH / 2 + X, 180, 200, 30);
        parabolaButton.setBounds(DEFAULT_WIDTH / 2 + X, 220, 200, 30);
        brentButton.setBounds(DEFAULT_WIDTH / 2 + X, 260, 200, 30);
        slider.setBounds(0, DEFAULT_HEIGHT / 2 + 4 * DEL, DEFAULT_WIDTH / 2 + DEL, 30);

        dichotomyButton.addActionListener(this);
        fibonachiButton.addActionListener(this);
        goldenRatioButton.addActionListener(this);
        parabolaButton.addActionListener(this);
        brentButton.addActionListener(this);

        dichotomyButton.setActionCommand("DICH");
        fibonachiButton.setActionCommand("FIB");
        goldenRatioButton.setActionCommand("GOLD");
        parabolaButton.setActionCommand("PARABOLA");
        brentButton.setActionCommand("BRENT");

        slider.addChangeListener(e -> {
            int value = ((JSlider)e.getSource()).getValue();
            switch (methodType) {
                case FIB:
                    pair = MethodFibonachi.fibonacchiOptimisation(0.5, 2.5, value);
                    break;
                case DICH:
                    pair = MethodDichotomy.solve(0.5, 2.5, value);
                    break;
                case PARABOLA:
                    break;
                case GOLD:
                    pair = MethodGoldenRatio.goldenRatio(0.5, 2.5, value);
                    break;
                case BRENT:
            }
            panel.updateUI();
        });

        panel.setLayout(null);
        panel.add(dichotomyButton);
        panel.add(fibonachiButton);
        panel.add(goldenRatioButton);
        panel.add(parabolaButton);
        panel.add(brentButton);
        panel.add(slider);
        add(panel);
    }

    public static double f(double x) {
        return 0.2 * x * Math.log10(x) + Math.pow((x - 2.3), 2);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        switch (action) {
            case "DICH":
                methodType = MethodType.DICH;
                break;
            case "FIB":
                methodType = MethodType.FIB;
                break;
            case "GOLD":
                methodType = MethodType.GOLD;
                break;
            case "PARABOLA":
                methodType = MethodType.PARABOLA;
                break;
            case "BRENT":
                methodType = MethodType.BRENT;
        }
        slider.setValue(0);
        pair = null;
        triple = null;
        panel.updateUI();

    }

    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;
    public static MethodType methodType = MethodType.DICH;
    public static final int DEL = 10;
    public static final double mX = 100;
    public static final double mY = 80;
    public static Pair<Double, Double> pair = null;
    public static Pair<Double, Pair<Double, Double>> triple = null;
    public static Graphics2D g2 = null;
    public static JSlider slider = new JSlider(0, 50, 0);
    public static JPanel panel = new DrawPanel();
}

class DrawPanel extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D)g;
        paintGrid(g2);

        double lastY = 0;
        for(double x = 0.5; x <= 2.6; x += 0.1) {
            double res = f(x);
            if (lastY == 0) {
                lastY = res;
                continue;
            }

            g2.draw(new Line2D.Double(
                    mX * (x - 0.1) + DEL,
                    DEFAULT_HEIGHT / 2 - mY * lastY,
                    mX * x + DEL,
                    DEFAULT_HEIGHT / 2 - mY * res));
            lastY = res;
        }

        if (pair != null) {
            switch (methodType) {
                case FIB:
                case DICH:
                case GOLD:
                    drawPoint();
                    break;
                case PARABOLA:

                    double x1 = triple.getFirst();
                    double x2 = triple.getSecond().getFirst();
                    double x3 = triple.getSecond().getSecond();

                    double y1 = f(x1);
                    double y2 = f(x2);
                    double y3 = f(x3);

                    double a = (y3 - (x3 * (y2 - y1) + x2 * y1 - x1 * y2) / (x2 - x1)) / (x3 * (x3 - x1 - x2) + x1 * x2);
                    double b = (y2 - y1) / (x2 - x1) - a * (x1 + x2);
                    double c = (x2 * y1 - x1 * y2) / (x2 - x1) + a * x1 * x2;

                    lastY = 0;
                    for (double x = x2 - 1.5; x <= x2 + 1.5; x += 0.1) {
                        double res = a * x * x + b * x + c;

                        if (lastY == 0) {
                            lastY = res;
                            continue;
                        }
                        g2.draw(new Line2D.Double(
                                mX * (x - 0.1) + DEL,
                                DEFAULT_HEIGHT / 2 - mY * lastY,
                                mX * x + DEL,
                                DEFAULT_HEIGHT / 2 - mY * res));
                        lastY = res;
                    }

                    break;
                case BRENT:
            }
        }
    }

    private void drawPoint() {
        g2.drawOval((int)Math.round(mX * pair.getFirst() + DEL), (int)Math.round(DEFAULT_HEIGHT / 2 - mY * f(pair.getFirst())),
                6, 6);
        g2.drawOval((int)Math.round(mX * pair.getSecond() + DEL), (int)Math.round(DEFAULT_HEIGHT / 2 - mY * f(pair.getSecond())),
                6, 6);
    }

    private void drawArrow(Graphics2D g, double x, double y) {
        int headlen = 10;
        double angle = Math.atan2(y - (double) 300, x - (double) 10);

        g.draw(new Line2D.Double(
                x,
                y,
                x - headlen * Math.cos(angle - Math.PI / 6),
                y - headlen * Math.sin(angle - Math.PI / 6)));


        g.draw(new Line2D.Double(
                x,
                y,
                x - headlen * Math.cos(angle + Math.PI / 6),
                y - headlen * Math.sin(angle + Math.PI / 6)));
    }

    public void paintGrid(Graphics2D g) {
        drawArrow(g,DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2);
        drawArrow(g, DEL, 20);

        g.draw(new Line2D.Double(
                0,
                DEFAULT_HEIGHT / 2,
                DEFAULT_WIDTH / 2,
                DEFAULT_HEIGHT / 2));

        g.draw(new Line2D.Double(
                DEL,
                20,
                DEL,
                DEFAULT_HEIGHT / 2 + DEL));

        for (double x = 0.2; x <= 4; x += 0.2) {
            g.draw(new Line2D.Double(
                    x * mX,
                    DEFAULT_HEIGHT / 2 - DEL / 3,
                    x * mX,
                    DEFAULT_HEIGHT / 2 + DEL / 3));

            if (DEFAULT_HEIGHT / 2 - x * mY > 40) {
                g.draw(new Line2D.Double(
                        DEL * 3 / 4,
                        DEFAULT_HEIGHT / 2 - x * mY,
                        DEL * 4 / 3,
                        DEFAULT_HEIGHT / 2 - x * mY));
            }
        }
    }
}
