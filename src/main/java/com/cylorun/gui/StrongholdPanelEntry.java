package com.cylorun.gui;

import com.cylorun.utils.PositionUtil;
import com.cylorun.utils.Vec2i;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StrongholdPanelEntry extends JPanel {

    private Vec2i owCoords;
    private Vec2i playerPos;
    private Vec2i.Dimension playerdim;

    public StrongholdPanelEntry(Vec2i owCoords, Vec2i playerPos, Vec2i.Dimension playerdim) {
        super(new GridLayout(1, 4));
        this.owCoords = owCoords;
        this.playerdim = playerdim;
        this.playerPos = playerPos;
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.draw();
    }

    public void draw() {
        this.draw(false);
    }

    public void draw(boolean isClosest) {
        this.removeAll();
        this.add(createCenteredLabel(String.format("(%s, %s)", owCoords.x, owCoords.z), isClosest));
        this.add(createCenteredLabelWithColor(String.format("%s", this.getDistance()), PositionUtil.getDistanceHexColor(this.getDistance(), this.playerdim), isClosest));
        this.add(createCenteredLabel(String.format("(%s, %s)", owCoords.div(8).x, owCoords.div(8).z), isClosest));
        this.add(createCenteredLabel(String.format("%s", this.getAngle()), isClosest));
    }

    public void setBold() {
        this.draw(true);
    }

    private int getDistance() {
        return playerdim == Vec2i.Dimension.NETHER ? owCoords.div(8).distanceTo(playerPos) : owCoords.distanceTo(playerPos);
    }

    private double getAngle() {
        return playerdim == Vec2i.Dimension.NETHER ? owCoords.div(8).angle(playerPos) : owCoords.angle(playerPos);
    }

    private JLabel createCenteredLabel(String text, boolean isClosest) {
        text = isClosest ? String.format("<b>%s</b>", text) : text;
        String htmlText = String.format("<html><body style='text-align:center;'>%s</body></html>", text);
        JLabel label = new JLabel(htmlText);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JLabel createCenteredLabelWithColor(String text, String color, boolean isClosest) {
        text = isClosest ? String.format("<b>%s<u></b>", text) : text;
        String htmlText = String.format("<html><body style='text-align:center;color:%s;'>%s</body></html>", color, text);
        JLabel label = new JLabel(htmlText);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
}
