package com.gradecalculator;

import com.gradecalculator.view.CalculatorFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        // Run application on Event Dispatch Thread (EDT) for Swing Thread Safety
        SwingUtilities.invokeLater(() -> {
            try {
                // Apply the native system look and feel for a better window wrapper
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // If it fails, Swing will safely fall back to the default cross-platform theme
            }

            // Launch the main window
            CalculatorFrame mainApp = new CalculatorFrame();
            mainApp.setVisible(true);
        });
    }
}