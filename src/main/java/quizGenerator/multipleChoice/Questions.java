package quizGenerator.multipleChoice;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import quizGenerator.multipleChoice.Dialogs.EditQuestionDialog;

public class Questions {

	private MainWindow mainWindow;

	private List<String> questions = new ArrayList<>();
	private List<String> answerKeys = new ArrayList<>();
	private List<List<String>> choices = new ArrayList<>();

	public List<String> getQuestions() {
		return questions;
	}

	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}

	public List<String> getAnswerKeys() {
		return answerKeys;
	}

	public void setAnswerKeys(List<String> answerKeys) {
		this.answerKeys = answerKeys;
	}

	public List<List<String>> getChoices() {
		return choices;
	}

	public void setChoices(List<List<String>> choices) {
		this.choices = choices;
	}

	public Questions(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public Questions() {

	}

	public void addQuestion() {
		String question = mainWindow.getQuestionArea().getText();
		List<String> choicesForQuestion = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			choicesForQuestion.add(mainWindow.getChoiceFields().get(i).getText());
			mainWindow.getChoiceFields().get(i).setText("");
		}

		answerKeys.add(mainWindow.getAnswerComboBox().getSelectedItem().toString());

		questions.add(question);
		choices.add(choicesForQuestion);

		int index = mainWindow.getQuestionListModel().size() + 1;
		mainWindow.getQuestionListModel().addElement(index + ". Question");

		mainWindow.getResetButton().setVisible(true);

		mainWindow.getQuestionArea().setText("");
	}

	public void resetQuestions() {
		int result = JOptionPane.showConfirmDialog(mainWindow.getMainFrame(),
				"Are you sure you want to reset all questions?", "Reset Confirmation", JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			questions.clear();
			answerKeys.clear();
			choices.clear();
			mainWindow.getQuestionListModel().clear();
		}
	}

	public void editQuestion(int index) {
		String question = questions.get(index);
		String answer = answerKeys.get(index);
		List<String> choicesForQuestion = choices.get(index);

		EditQuestionDialog editDialog = new EditQuestionDialog(mainWindow.getMainFrame(), question, choicesForQuestion,
				answer);
		editDialog.setVisible(true);

		if (editDialog.isDelete()) {
			deleteQuestion(index);
		}

		if (editDialog.isConfirmed()) {
			questions.set(index, editDialog.getEditedQuestion());
			answerKeys.set(index, editDialog.getEditedAnswer());
			choices.set(index, editDialog.getEditedChoices());
			mainWindow.getQuestionListModel().setElementAt(index + 1 + ". Question", index);
		}
	}

	public void deleteQuestion(int index) {
		questions.remove(index);
		choices.remove(index);
		answerKeys.remove(index);
		mainWindow.getQuestionListModel().remove(index);
	}

}
