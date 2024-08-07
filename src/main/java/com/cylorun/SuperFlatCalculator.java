package com.cylorun;

import com.cylorun.gui.CalcFrame;
import com.cylorun.utils.PositionUtil;
import com.cylorun.utils.Vec2i;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;

public class SuperFlatCalculator {
    public static final String VERSION = SuperFlatCalculator.class.getPackage().getImplementationVersion() == null ? "DEV" : SuperFlatCalculator.class.getPackage().getImplementationVersion();

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatDarculaLaf());
        CalcFrame.getInstance();

        new ClipboardReader(SuperFlatCalculator::doUpdate);
    }

    private static void doUpdate(String clipboard) {
        if (PositionUtil.isF3c(clipboard)) {
            Vec2i pos = PositionUtil.getLocation(clipboard);
            Vec2i.Dimension dim = PositionUtil.getDimension(clipboard);
            CalcFrame.getInstance().updateCoords(pos, dim);
        }
    }
}