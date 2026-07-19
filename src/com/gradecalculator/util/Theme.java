package com.gradecalculator.util;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class Theme {
    // Colors
    public static final Color PRIMARY_COLOR = new Color(30, 58, 138);
    public static final Color BACKGROUND_COLOR = new Color(203, 213, 225);
    public static final Color PANEL_BG_COLOR = new Color(241, 245, 249);
    public static final Color INPUT_BG = Color.WHITE;
    public static final Color TEXT_DARK = new Color(15, 23, 42);
    public static final Color TEXT_MUTED = new Color(100, 116, 139);

    // Status Badge Colors
    public static final Color SUCCESS_COLOR = new Color(22, 163, 74); // Green
    public static final Color ERROR_COLOR = new Color(220, 38, 38);   // Red
    public static final Color ATKT_COLOR = new Color(217, 119, 6);    // Amber/Orange

    public static final Color BORDER_COLOR = new Color(203, 213, 225);

    // Fonts
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);

    // Reusable UI Components
    public static void applyInteractiveButton(JButton btn, Color defaultBg, Color hoverBg, Color fg) {
        btn.setFont(FONT_BUTTON);
        btn.setBackground(defaultBg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(defaultBg.darker(), 1),
                new EmptyBorder(10, 18, 10, 18)
        ));

        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(hoverBg); }
            @Override public void mouseExited(MouseEvent e) { btn.setBackground(defaultBg); }
            @Override public void mousePressed(MouseEvent e) { btn.setBackground(defaultBg.darker()); }
            @Override public void mouseReleased(MouseEvent e) { btn.setBackground(hoverBg); }
        });
    }

    public static JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(FONT_INPUT);
        field.setForeground(TEXT_DARK);
        field.setBackground(INPUT_BG);

        Border defaultBorder = BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(7, 10, 7, 10)
        );
        Border focusedBorder = BorderFactory.createCompoundBorder(
                new LineBorder(new Color(59, 130, 246), 2),
                new EmptyBorder(6, 9, 6, 9)
        );

        field.setBorder(defaultBorder);
        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                field.setBorder(focusedBorder);
                field.setBackground(new Color(248, 250, 252));
            }
            @Override public void focusLost(FocusEvent e) {
                field.setBorder(defaultBorder);
                field.setBackground(INPUT_BG);
            }
        });
        return field;
    }

    public static JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT_DARK);
        return label;
    }

    public static JLabel createReportValueLabel() {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(PRIMARY_COLOR);
        return label;
    }
}