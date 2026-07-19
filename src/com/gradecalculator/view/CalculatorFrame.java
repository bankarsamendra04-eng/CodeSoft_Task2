package com.gradecalculator.view;

import com.gradecalculator.model.Student;
import com.gradecalculator.model.SubjectInput;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Statically import the theme so we don't have to type "Theme." every time
import static com.gradecalculator.util.Theme.*;

public class CalculatorFrame extends JFrame {
    private JTextField txtName, txtRollNo;
    private final List<SubjectInput> subjectInputs = new ArrayList<>();
    private JPanel subjectsGrid, panelReportContent, panelStatusBadge;
    private JScrollPane subjectsScrollPane;
    private int currentRow = 0;
    private JLabel valName, valRoll, valTotal, valAverage, valGrade, lblStatusBadge;

    public CalculatorFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Student Performance Evaluator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 680);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout(15, 15));
        contentPane.setBackground(BACKGROUND_COLOR);
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);

        contentPane.add(createHeaderPanel(), BorderLayout.NORTH);

        JPanel mainWorkspace = new JPanel(new GridLayout(1, 2, 15, 0));
        mainWorkspace.setOpaque(false);
        mainWorkspace.add(createInputFormPanel());
        mainWorkspace.add(createReportCardPanel());
        contentPane.add(mainWorkspace, BorderLayout.CENTER);

        contentPane.add(createActionButtonsPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 4, 0, new Color(23, 37, 84)),
                new EmptyBorder(15, 20, 15, 20)
        ));

        JLabel titleLabel = new JLabel("ACADEMIC PERFORMANCE EVALUATION SYSTEM");
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Official Student Grade Calculation Engine");
        subtitleLabel.setFont(FONT_SUBTITLE);
        subtitleLabel.setForeground(new Color(191, 219, 254));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerPanel.add(subtitleLabel);

        return headerPanel;
    }

    private JPanel createInputFormPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(PANEL_BG_COLOR);
        container.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(148, 163, 184), 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel("STUDENT & ACADEMIC DATA ENTRY");
        titleLabel.setFont(FONT_HEADER);
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel infoGrid = new JPanel(new GridBagLayout());
        infoGrid.setBackground(PANEL_BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        infoGrid.add(createStyledLabel("Student Full Name:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        txtName = createStyledTextField();
        infoGrid.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        infoGrid.add(createStyledLabel("Roll / ID Number:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        txtRollNo = createStyledTextField();
        infoGrid.add(txtRollNo, gbc);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setBackground(PANEL_BG_COLOR);
        topContainer.add(titleLabel, BorderLayout.NORTH);
        topContainer.add(infoGrid, BorderLayout.CENTER);

        container.add(topContainer, BorderLayout.NORTH);

        JPanel subjectsArea = new JPanel(new BorderLayout(0, 5));
        subjectsArea.setBackground(PANEL_BG_COLOR);
        subjectsArea.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JPanel colHeaders = new JPanel(new GridBagLayout());
        colHeaders.setBackground(PANEL_BG_COLOR);
        GridBagConstraints hc = new GridBagConstraints();
        hc.fill = GridBagConstraints.HORIZONTAL;
        hc.insets = new Insets(0, 6, 5, 6);
        hc.gridx = 0; hc.weightx = 0.65;
        colHeaders.add(createStyledLabel("Subject Name"), hc);
        hc.gridx = 1; hc.weightx = 0.35;
        colHeaders.add(createStyledLabel("Score (0-100)"), hc);
        subjectsArea.add(colHeaders, BorderLayout.NORTH);

        subjectsGrid = new JPanel(new GridBagLayout());
        subjectsGrid.setBackground(PANEL_BG_COLOR);
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(PANEL_BG_COLOR);
        wrapper.add(subjectsGrid, BorderLayout.NORTH);

        subjectsScrollPane = new JScrollPane(wrapper);
        subjectsScrollPane.setBorder(new LineBorder(BORDER_COLOR, 1));
        subjectsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        subjectsArea.add(subjectsScrollPane, BorderLayout.CENTER);

        JButton btnAddSubject = new JButton("+ Add Another Subject");
        applyInteractiveButton(btnAddSubject, new Color(226, 232, 240), new Color(203, 213, 225), PRIMARY_COLOR);
        btnAddSubject.setPreferredSize(new Dimension(0, 36));

        btnAddSubject.addActionListener(e -> {
            addSubjectRow();
            SwingUtilities.invokeLater(() -> {
                JScrollBar vertical = subjectsScrollPane.getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            });
        });

        JPanel bottomAction = new JPanel(new BorderLayout());
        bottomAction.setBackground(PANEL_BG_COLOR);
        bottomAction.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        bottomAction.add(btnAddSubject, BorderLayout.CENTER);
        subjectsArea.add(bottomAction, BorderLayout.SOUTH);

        container.add(subjectsArea, BorderLayout.CENTER);

        for(int i = 0; i < 5; i++) addSubjectRow();

        return container;
    }

    private void addSubjectRow() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.gridy = currentRow++;

        JTextField txtSubName = createStyledTextField();
        JTextField txtSubMark = createStyledTextField();

        gbc.gridx = 0; gbc.weightx = 0.65;
        subjectsGrid.add(txtSubName, gbc);
        gbc.gridx = 1; gbc.weightx = 0.35;
        subjectsGrid.add(txtSubMark, gbc);

        SubjectInput input = new SubjectInput();
        input.txtName = txtSubName;
        input.txtMark = txtSubMark;
        subjectInputs.add(input);

        subjectsGrid.revalidate();
        subjectsGrid.repaint();
    }

    private JPanel createReportCardPanel() {
        JPanel container = new JPanel(new CardLayout());
        container.setBackground(PANEL_BG_COLOR);
        container.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(148, 163, 184), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblPlaceholder = new JLabel("<html><center><b>Report Card Preview</b><br><br><span style='font-size:11px; color:#64748B;'>Please complete the data entry on the left<br>and press 'Calculate Performance'.</span></center></html>", SwingConstants.CENTER);
        lblPlaceholder.setFont(FONT_LABEL);
        lblPlaceholder.setForeground(TEXT_MUTED);
        container.add(lblPlaceholder, "Placeholder");

        panelReportContent = new JPanel(new BorderLayout(10, 10));
        panelReportContent.setBackground(PANEL_BG_COLOR);

        JPanel reportHeader = new JPanel(new BorderLayout());
        reportHeader.setBackground(PANEL_BG_COLOR);
        JLabel title = new JLabel("OFFICIAL PERFORMANCE STATEMENT", SwingConstants.CENTER);
        title.setFont(FONT_HEADER);
        title.setForeground(PRIMARY_COLOR);
        reportHeader.add(title, BorderLayout.NORTH);
        reportHeader.setBorder(new EmptyBorder(0, 0, 15, 0));

        JPanel reportDetails = new JPanel(new GridBagLayout());
        reportDetails.setBackground(PANEL_BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 5, 12, 5);

        valName = createReportValueLabel();
        valRoll = createReportValueLabel();
        valTotal = createReportValueLabel();
        valAverage = createReportValueLabel();
        valGrade = createReportValueLabel();

        addReportRow(reportDetails, gbc, 0, "Student Name:", valName);
        addReportRow(reportDetails, gbc, 1, "Roll Number:", valRoll);
        addReportRow(reportDetails, gbc, 2, "Cumulative Total:", valTotal);
        addReportRow(reportDetails, gbc, 3, "Average Percentage:", valAverage);
        addReportRow(reportDetails, gbc, 4, "Final Letter Grade Assigned:", valGrade);

        panelStatusBadge = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelStatusBadge.setBorder(new LineBorder(BORDER_COLOR, 1));
        lblStatusBadge = new JLabel("STATUS", SwingConstants.CENTER);
        lblStatusBadge.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelStatusBadge.add(lblStatusBadge);

        panelReportContent.add(reportHeader, BorderLayout.NORTH);
        panelReportContent.add(reportDetails, BorderLayout.CENTER);
        panelReportContent.add(panelStatusBadge, BorderLayout.SOUTH);

        container.add(panelReportContent, "ReportContent");
        return container;
    }

    private void addReportRow(JPanel panel, GridBagConstraints gbc, int row, String label, JLabel val) {
        gbc.gridy = row;
        gbc.gridx = 0; gbc.weightx = 0.55;
        panel.add(createStyledLabel(label), gbc);
        gbc.gridx = 1; gbc.weightx = 0.45;
        panel.add(val, gbc);
    }

    private JPanel createActionButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panel.setOpaque(false);

        JButton btnCalculate = new JButton("Calculate Performance");
        applyInteractiveButton(btnCalculate, PRIMARY_COLOR, new Color(59, 130, 246), Color.WHITE);

        JButton btnClear = new JButton("Clear Form");
        applyInteractiveButton(btnClear, new Color(100, 116, 139), new Color(71, 85, 105), Color.WHITE);

        JButton btnExit = new JButton("Exit");
        applyInteractiveButton(btnExit, ERROR_COLOR, new Color(153, 27, 27), Color.WHITE);

        btnCalculate.addActionListener(e -> processCalculations());
        btnClear.addActionListener(e -> resetForm());
        btnExit.addActionListener(e -> System.exit(0));

        panel.add(btnCalculate);
        panel.add(btnClear);
        panel.add(btnExit);
        return panel;
    }

    private void processCalculations() {
        String name = txtName.getText().trim();
        String roll = txtRollNo.getText().trim();

        if (name.isEmpty() || roll.isEmpty()) {
            showError("Please complete the Name and Roll/ID Number fields.");
            return;
        }

        Map<String, Double> enteredMarks = new LinkedHashMap<>();

        try {
            for (SubjectInput input : subjectInputs) {
                String subName = input.txtName.getText().trim();
                String markText = input.txtMark.getText().trim();

                if (subName.isEmpty() && markText.isEmpty()) continue;

                if (subName.isEmpty()) {
                    showError("Missing subject name for an entered score."); return;
                }
                if (markText.isEmpty()) {
                    showError("Please enter marks for subject: " + subName); return;
                }
                if (enteredMarks.containsKey(subName)) {
                    showError("Duplicate subject detected: '" + subName + "'."); return;
                }

                double marks = Double.parseDouble(markText);
                if (marks < 0 || marks > 100) {
                    showError("Marks for " + subName + " must be between 0.0 and 100.0."); return;
                }
                enteredMarks.put(subName, marks);
            }
        } catch (NumberFormatException e) {
            showError("Please check marks formatting. Non-numeric data detected."); return;
        }

        if (enteredMarks.isEmpty()) {
            showError("Please enter at least one Subject Name and Score."); return;
        }

        Student student = new Student(name, roll, enteredMarks);
        displayReportCard(student);
    }

    // THIS is where the ATKT logic handles the final UI colors!
    private void displayReportCard(Student student) {
        valName.setText(student.getName());
        valRoll.setText(student.getRollNumber());

        int maxTotalScore = student.getSubjectMarks().size() * 100;
        DecimalFormat df = new DecimalFormat("0.00");

        valTotal.setText(df.format(student.calculateTotal()) + " / " + maxTotalScore);
        valAverage.setText(df.format(student.calculateAverage()) + "%");
        valGrade.setText(student.getGrade());

        // --- UPDATED STATUS BADGE LOGIC ---
        String status = student.getStatus();

        if (status.equals("PASS")) {
            panelStatusBadge.setBackground(new Color(220, 252, 231));
            panelStatusBadge.setBorder(new LineBorder(SUCCESS_COLOR, 2));
            lblStatusBadge.setText("ACADEMIC STATUS: PASSED");
            lblStatusBadge.setForeground(SUCCESS_COLOR);

        } else if (status.equals("A.T.K.T.")) {
            // New Amber/Orange style for A.T.K.T.
            panelStatusBadge.setBackground(new Color(254, 243, 199));
            panelStatusBadge.setBorder(new LineBorder(ATKT_COLOR, 2));
            lblStatusBadge.setText("ACADEMIC STATUS: A.T.K.T.");
            lblStatusBadge.setForeground(ATKT_COLOR);

        } else {
            panelStatusBadge.setBackground(new Color(254, 226, 226));
            panelStatusBadge.setBorder(new LineBorder(ERROR_COLOR, 2));
            lblStatusBadge.setText("ACADEMIC STATUS: FAILED");
            lblStatusBadge.setForeground(ERROR_COLOR);
        }

        CardLayout cl = (CardLayout) (panelReportContent.getParent().getLayout());
        cl.show(panelReportContent.getParent(), "ReportContent");
    }

    private void resetForm() {
        txtName.setText(""); txtRollNo.setText("");
        subjectsGrid.removeAll(); subjectInputs.clear(); currentRow = 0;
        for(int i = 0; i < 5; i++) addSubjectRow();

        CardLayout cl = (CardLayout) (panelReportContent.getParent().getLayout());
        cl.show(panelReportContent.getParent(), "Placeholder");
        txtName.requestFocus();
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "System Validation Error", JOptionPane.WARNING_MESSAGE);
    }
}