package quizGenerator.multipleChoice;

import java.awt.GridLayout;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class EditQuestionDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTextField editedQuestionField;
	private JComboBox<?> editedAnswerField;
	private List<JTextField> editedChoiceFields;
	private JButton confirmButton;
	private JButton deleteButton;
	private JButton cancelButton;
	private boolean confirmed;
	private boolean delete;

	public EditQuestionDialog(JFrame parent, String question, List<String> choices, String answer) {
		super(parent, "Edit Question", true);
		setSize(600, 400);
		setLocationRelativeTo(parent);
		confirmed = false;
		delete = false;

		editedQuestionField = new JTextField(question);
		editedAnswerField = new JComboBox(new DefaultComboBoxModel(new String[] { "Select", "A", "B", "C", "D", "E" }));
		editedAnswerField.setSelectedItem(answer);
		editedChoiceFields = new ArrayList<>();
		for (int i = 0; i < choices.size(); i++) {
			JTextField choiceField = new JTextField(choices.get(i));
			editedChoiceFields.add(choiceField);
		}

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
				delete = true;
				dispose();
			}
		});

		initializeComponents();
	}

	private void initializeComponents() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(editedChoiceFields.size() + 4, 4));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.add(new JLabel("Edited Question:"));
		panel.add(editedQuestionField);

		panel.add(new JLabel("Edited Answer:"));
		panel.add(editedAnswerField);

		for (int i = 0; i < editedChoiceFields.size(); i++) {
			panel.add(new JLabel("Edited Choice " + (i + 1) + ":"));
			panel.add(editedChoiceFields.get(i));
		}

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