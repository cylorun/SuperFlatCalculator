package com.cylorun;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ClipboardReader extends Thread {
    private String lastClipboardString;
    private final Clipboard clipboard;
    private Consumer<String> consumer;

    public ClipboardReader(Consumer<String> consumer) {
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        this.lastClipboardString = "";
        this.consumer = consumer;
        this.start();
    }

    public String getClipboardContent() {
        return lastClipboardString;
    }

    public void setClipboardContent(String clipboardString) {
        if (!lastClipboardString.equals(clipboardString)) {
            lastClipboardString = clipboardString;
            this.consumer.accept(clipboardString);
        }
    }

    @Override
    public void run() {
        while (true) {
            String clipboardString = null;
            try {
                clipboardString = (String) clipboard.getData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IllegalStateException | IOException ignored) {
            }

            if (clipboardString != null && !this.lastClipboardString.equals(clipboardString)) {
                setClipboardContent(clipboardString);
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
    }
}