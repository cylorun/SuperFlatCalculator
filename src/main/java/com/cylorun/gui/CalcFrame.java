package com.cylorun.gui;

import com.cylorun.SuperFlatCalculator;
import com.cylorun.SuperFlatCalculatorOptions;
import com.cylorun.utils.PositionUtil;
import com.cylorun.utils.ResourceUtil;
import com.cylorun.utils.Vec2i;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class CalcFrame extends JFrame {

    private JPanel strongholdPanel;

    private JPanel headerPanel;
    private static CalcFrame instance;

    private CalcFrame() {
        super("SFC " + SuperFlatCalculator.VERSION);
        this.setLayout(new GridLayout(3,1));
        this.setSize(new Dimension(350, 250));
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setAppIcon();
        this.addHeaders();
        this.strongholdPanel = new JPanel(new GridLayout(6,1));
        this.strongholdPanel.add(new JLabel("Waiting for F3+C"));
        this.add(this.strongholdPanel);
        SuperFlatCalculatorOptions options = SuperFlatCalculatorOptions.getInstance();

        if (options.win_loc.length == 2) {
            this.setLocation(options.win_loc[0], options.win_loc[1]);
        }

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SuperFlatCalculatorOptions options = SuperFlatCalculatorOptions.getInstance();
                options.win_loc[0] = e.getWindow().getX();
                options.win_loc[1] = e.getWindow().getY();
                SuperFlatCalculatorOptions.save();
            }
        });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void addHeaders() {
        this.headerPanel = new JPanel(new GridLayout(1,4));
        JLabel loc = new JLabel("Location");
        loc.setHorizontalAlignment(SwingConstants.CENTER);
        this.headerPanel.add(loc);
        JLabel dist = new JLabel("Distance");
        dist.setHorizontalAlignment(SwingConstants.CENTER);
        this.headerPanel.add(dist);
        JLabel nether = new JLabel("Nether");
        nether.setHorizontalAlignment(SwingConstants.CENTER);
        this.headerPanel.add(nether);
        JLabel angle = new JLabel("Angle");
        angle.setHorizontalAlignment(SwingConstants.CENTER);
        this.headerPanel.add(angle);


        this.add(headerPanel);
    }

    private void setAppIcon() {
        Image img;
        try {
            img = ResourceUtil.loadImageResource("app_icon.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.setIconImage(img);
    }

    public void updateCoords(Vec2i pos, Vec2i.Dimension dim) {
        this.strongholdPanel.removeAll();
        List<Vec2i> strongholds = PositionUtil.getClosestStrongholds(pos, dim);
        for (Vec2i sh : strongholds) {
            StrongholdPanelEntry entry = new StrongholdPanelEntry(sh, pos, dim);
            if (strongholds.indexOf(sh) == 0) {
                entry.setBold();
            }
            this.strongholdPanel.add(entry);
            this.strongholdPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
            this.strongholdPanel.repaint();
            this.strongholdPanel.revalidate();
        }
    }


    public static synchronized CalcFrame getInstance() {
        if (instance == null) {
            instance = new CalcFrame();
        }
        return instance;
    }
}
