package quizGenerator.multipleChoice.Dialogs;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;

public class EditQuestionDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTextPane editedQuestionField;
	private JScrollPane editedQuestionAreaScrollPane;
	private JComboBox<?> editedAnswerField;
	private JTextField choiceFieldA;
	private JTextField choiceFieldB;
	private JTextField choiceFieldC;
	private JTextField choiceFieldD;
	private JTextField choiceFieldE;
	private List<JTextField> editedChoiceFields;
	private JButton confirmButton;
	private JButton deleteButton;
	private JButton cancelButton;
	private boolean confirmed;
	private boolean delete;

	public EditQuestionDialog(JFrame parent, String question, List<String> choices, String answer) {
		super(parent, "Edit Question", true);
		setSize(800, 600);
		setMinimumSize(new Dimension(800, 600));
		setLocationRelativeTo(parent);
		confirmed = false;
		delete = false;

		editedQuestionField = new JTextPane();
		editedQuestionField.setText(question);
		editedQuestionField.setContentType("text/plain");
		editedQuestionAreaScrollPane = new JScrollPane(editedQuestionField);
		editedQuestionAreaScrollPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"Question", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 200, 0)));

		editedAnswerField = new JComboBox(new DefaultComboBoxModel(new String[] { "Select", "A", "B", "C", "D", "E" }));
		editedAnswerField.setSelectedItem(answer);
		editedAnswerField.setPreferredSize(new Dimension(80, 40));
		editedAnswerField.setMinimumSize(new Dimension(55, 30));

		editedAnswerField.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Answer",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 200, 0)));

		editedChoiceFields = new ArrayList<>();
		String[] choiceLabels = { "A", "B", "C", "D", "E" };

		choiceFieldA = new JTextField(choices.get(0));
		choiceFieldA.setColumns(35);
		choiceFieldA.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), choiceLabels[0],
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 200, 0)));
		editedChoiceFields.add(choiceFieldA);

		choiceFieldB = new JTextField(choices.get(1));
		choiceFieldB.setColumns(35);
		choiceFieldB.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), choiceLabels[1],
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 200, 0)));
		editedChoiceFields.add(choiceFieldB);

		choiceFieldC = new JTextField(choices.get(2));
		choiceFieldC.setColumns(35);
		choiceFieldC.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), choiceLabels[2],
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 200, 0)));
		editedChoiceFields.add(choiceFieldC);

		choiceFieldD = new JTextField(choices.get(3));
		choiceFieldD.setColumns(35);
		choiceFieldD.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), choiceLabels[3],
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 200, 0)));
		editedChoiceFields.add(choiceFieldD);

		choiceFieldE = new JTextField(choices.get(4));
		choiceFieldE.setColumns(35);
		choiceFieldE.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), choiceLabels[4],
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 200, 0)));
		editedChoiceFields.add(choiceFieldE);

		confirmButton = new JButton("Confirm");
		cancelButton = new JButton("Cancel");
		deleteButton = new JButton("Delete");

		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmed = true;
				dispose();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(parent, "Are you sure you want to delete this question?",
						"Delete Confirmation", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
					delete = true;
					dispose();
				}
			}
		});

		initializeComponents();
	}

	private void initializeComponents() {
		JPanel panel = new JPanel();
		SpringLayout sl_panel = new SpringLayout();
		sl_panel.putConstraint(SpringLayout.WEST, choiceFieldE, 0, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, choiceFieldE, -252, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.WEST, choiceFieldD, 0, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, choiceFieldD, -252, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.WEST, editedAnswerField, 112, SpringLayout.EAST, choiceFieldC);
		sl_panel.putConstraint(SpringLayout.EAST, editedAnswerField, -10, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, editedQuestionAreaScrollPane, 0, SpringLayout.EAST, confirmButton);
		sl_panel.putConstraint(SpringLayout.NORTH, choiceFieldE, 6, SpringLayout.SOUTH, choiceFieldD);
		sl_panel.putConstraint(SpringLayout.SOUTH, choiceFieldE, -70, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, choiceFieldD, 6, SpringLayout.SOUTH, choiceFieldC);
		sl_panel.putConstraint(SpringLayout.SOUTH, choiceFieldD, -116, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, choiceFieldC, -162, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, editedQuestionAreaScrollPane, 0, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, editedAnswerField, -2, SpringLayout.NORTH, choiceFieldC);
		sl_panel.putConstraint(SpringLayout.NORTH, editedQuestionAreaScrollPane, 10, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, editedQuestionAreaScrollPane, -6, SpringLayout.NORTH, choiceFieldA);
		sl_panel.putConstraint(SpringLayout.WEST, choiceFieldB, 0, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, choiceFieldB, -252, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.WEST, choiceFieldA, 0, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, choiceFieldA, -6, SpringLayout.NORTH, choiceFieldB);
		sl_panel.putConstraint(SpringLayout.EAST, choiceFieldA, 0, SpringLayout.EAST, choiceFieldB);
		sl_panel.putConstraint(SpringLayout.SOUTH, choiceFieldB, -6, SpringLayout.NORTH, choiceFieldC);
		sl_panel.putConstraint(SpringLayout.WEST, choiceFieldC, 0, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, choiceFieldC, -252, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, deleteButton, 0, SpringLayout.NORTH, confirmButton);
		sl_panel.putConstraint(SpringLayout.EAST, deleteButton, -8, SpringLayout.WEST, cancelButton);
		sl_panel.putConstraint(SpringLayout.NORTH, cancelButton, 0, SpringLayout.NORTH, confirmButton);
		sl_panel.putConstraint(SpringLayout.EAST, cancelButton, -6, SpringLayout.WEST, confirmButton);
		sl_panel.putConstraint(SpringLayout.SOUTH, confirmButton, 0, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, confirmButton, 0, SpringLayout.EAST, panel);

		panel.setLayout(sl_panel);
		panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

		panel.add(editedQuestionAreaScrollPane);
		panel.add(editedAnswerField);

		panel.add(choiceFieldA);
		panel.add(choiceFieldB);
		panel.add(choiceFieldC);
		panel.add(choiceFieldD);
		panel.add(choiceFieldE);

		panel.add(confirmButton);
		panel.add(cancelButton);
		panel.add(deleteButton);

		getContentPane().add(panel);
	}

	public String getEditedQuestion() {
		return editedQuestionField.getText();
	}

	public String getEditedAnswer() {
		Object selectedAnswer = editedAnswerField.getSelectedItem();

		return (selectedAnswer != null) ? selectedAnswer.toString() : "";
	}

	public List<String> getEditedChoices() {
		List<String> editedChoices = new ArrayList<>();
		for (int i = 0; i < editedChoiceFields.size(); i++) {
			editedChoices.add(editedChoiceFields.get(i).getText());
		}
		return editedChoices;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public boolean isDelete() {
		return delete;
	}
}