package za.ac.uct.cs.ddd.lambda.gui;

import za.ac.uct.cs.ddd.lambda.evaluator.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;

/**
 *
 */
class CalcView extends ContentWindow {

    private static final String ERROR = "error";
    private static final String EXPRESSION = "expression";
    private static ImageIcon setInputIcon = null;

    private HashSet<String> history;
    private LambdaExpression expression;

    //... Components
    private JComboBox<String> inputBox;
    private JPanel body;
    private CardLayout bodyLayout;
    private JLabel errorLabel;
    private ExpressionTree expressionTree;
    private ReductionOrder reductionOrder;
    private JPanel reductionPanel;

    public CalcView(Window parent) {
        super(parent);


        if (setInputIcon == null) {
            setInputIcon = new ImageIcon(getClass().getResource("/icon_set_input.gif"), "set input");
        }

        history = new HashSet<String>();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.weightx = 30;


        inputBox = new JComboBox<String>();
        inputBox.setEditable(true);
        inputBox.addActionListener(event -> setInput((String) inputBox.getSelectedItem()));
        PlainDocument doc = (PlainDocument) ((JTextComponent) inputBox.getEditor().getEditorComponent()).getDocument();
        doc.setDocumentFilter(new LambdaDocFilter());
        add(inputBox, gbc);


        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 0, 10);
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(event -> setInput((String) inputBox.getSelectedItem()));
        add(calculateButton, gbc);

        body = new JPanel(new CardLayout());
        bodyLayout = (CardLayout) body.getLayout();

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 6;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 2;
        gbc.weighty = 5;
        gbc.insets = new Insets(10, 5, 10, 10);
        add(body, gbc);

        JPanel errorPanel = new JPanel(new GridBagLayout());
        errorLabel = new JLabel("Waiting for input");
        gbc.gridy = 0;
        gbc.weighty = 0;
        errorPanel.add(errorLabel, gbc);
        gbc.gridy = 1;
        gbc.weighty = 1;
        errorPanel.add(Box.createGlue(), gbc);
        body.add(errorPanel, ERROR);
        bodyLayout.show(body, ERROR);


        JPanel expressionPanel = new JPanel(new GridBagLayout());

        gbc.gridy = 0;
        expressionTree = new ExpressionTree();
        expressionPanel.add(new JScrollPane(expressionTree), gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        JButton reduceButton = new JButton("Reduce");
        reduceButton.setPreferredSize(reduceButton.getMinimumSize());
        reduceButton.addActionListener(event -> reduce());
        expressionPanel.add(reduceButton, gbc);

        ButtonGroup orderButtons = new ButtonGroup();
        gbc.gridx = 1;
        JRadioButton applicativeOrderButton = new JRadioButton("Applicative order");
        applicativeOrderButton.addActionListener(event -> reductionOrder = ReductionOrder.APPLICATIVE);
        orderButtons.add(applicativeOrderButton);
        expressionPanel.add(applicativeOrderButton, gbc);
        gbc.gridx = 2;
        JRadioButton normalOrderButton = new JRadioButton("Normal order");
        normalOrderButton.addActionListener(e -> reductionOrder = ReductionOrder.NORMAL);
        orderButtons.add(normalOrderButton);
        expressionPanel.add(normalOrderButton, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        expressionPanel.add(Box.createHorizontalGlue(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        reductionPanel = new JPanel();
        reductionPanel.setLayout(new GridBagLayout());
        expressionPanel.add(new JScrollPane(reductionPanel), gbc);

        body.add(expressionPanel, EXPRESSION);
    }

    /**
     *
     * @param input
     */

    public void setInput(String input) {
        if (input == null) return;

        history.add(input);
        if (history.contains(input)) {
            inputBox.removeItem(input);
        }
        inputBox.insertItemAt(input, 0);
        inputBox.setSelectedItem(input);

        try {
            expression = Parser.parse(input);
            expressionTree.setExpression(expression);
            reductionPanel.removeAll();
            bodyLayout.show(body, EXPRESSION);
        } catch (InvalidExpressionException exception) {
            errorLabel.setText("Syntax error: " + exception.getMessage());
            bodyLayout.show(body, ERROR);
        }
    }

    /**
     *
     */
    public void reduce() {
        if (reductionOrder == null) return;

        reductionPanel.removeAll();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.weightx = 0;
        constraints.weighty = 0;

        List<ReductionResult> reductions = expression.reductions(reductionOrder);
        if (reductions.isEmpty()) {
            reductionPanel.add(new JLabel("No reductions"), constraints);
            constraints.gridy++;
        } else {
            for (ReductionResult reduction : reductions) {
                String reductionString = reduction.getReducedExpression().toString();

                constraints.gridx = 0;
                JButton button = new JButton(setInputIcon);
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);
              //  button.setPreferredSize(new Dimension(17, 17));
                button.addActionListener(event -> setInput(reductionString));
                reductionPanel.add(button, constraints);

                constraints.gridx = 1;
                reductionPanel.add(new JLabel(reductionString), constraints);

                constraints.gridy++;
            }
        }

        constraints.gridx = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        reductionPanel.add(Box.createGlue(), constraints);

        body.revalidate();
    }

    private class LambdaDocFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {

            fb.insertString(offset, text.replaceAll("\\\\", "\u03bb"), attr);

        }

        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
            fb.replace(offset, length, text.replaceAll("\\\\", "\u03bb"), attr);

        }
    }
}
