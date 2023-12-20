package quizGenerator.multipleChoice.Configuration;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;

import quizGenerator.multipleChoice.MainWindow;
import quizGenerator.multipleChoice.WordDocument;

public class LanguageSelection {

	private static ResourceBundle resourceBundle;
	private MainWindow mainWindow;
	private WordDocument wordDocument;

	public LanguageSelection(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		wordDocument = new WordDocument();
	}

	public LanguageSelection() {

	}

	public void setLocale(Locale locale) {
		resourceBundle = ResourceBundle.getBundle("LANGUAGE", locale);
		ConfigurationManager.setSelectedLanguage(locale);
		updateButtonTexts();
		updateTitles();
		updateComboBoxes();
		updateToolBar();
		updateToolBarMenuItems();
		updateAnswersString();
		updateCloseDialogString();
	}

	private void updateButtonTexts() {
		mainWindow.getAddQuestionButton().setText(resourceBundle.getString("addQuestionButton"));
		mainWindow.getResetButton().setText(resourceBundle.getString("resetButton"));
		mainWindow.getEditButton().setText(resourceBundle.getString("editButton"));
		mainWindow.getCutButton().setText(resourceBundle.getString("cutButton"));
		mainWindow.getCopyButton().setText(resourceBundle.getString("copyButton"));
		mainWindow.getPasteButton().setText(resourceBundle.getString("pasteButton"));
	}

	private void updateTitles() {
		TitledBorder titledBorder = (TitledBorder) mainWindow.getTopicField().getBorder();
		titledBorder.setTitle(resourceBundle.getString("topicField"));
		mainWindow.getTopicField().setBorder(titledBorder);

		titledBorder = (TitledBorder) mainWindow.getAuthorField().getBorder();
		titledBorder.setTitle(resourceBundle.getString("authorField"));
		mainWindow.getAuthorField().setBorder(titledBorder);

		titledBorder = (TitledBorder) mainWindow.getQuestionScrollPane().getBorder();
		titledBorder.setTitle(resourceBundle.getString("questionScrollPaneField"));
		mainWindow.getQuestionScrollPane().setBorder(titledBorder);

		titledBorder = (TitledBorder) mainWindow.getQuestionArea().getBorder();
		titledBorder.setTitle(resourceBundle.getString("questionAreaField"));
		mainWindow.getQuestionArea().setBorder(titledBorder);

	}

	private void updateComboBoxes() {
		TitledBorder titledBorder = (TitledBorder) mainWindow.getAnswerComboBox().getBorder();
		String[] comboBoxItems = resourceBundle.getString("answerComboBoxIndexes").split(",");
		mainWindow.getAnswerComboBox().setModel(new DefaultComboBoxModel(comboBoxItems));
		titledBorder.setTitle(resourceBundle.getString("answerComboBox"));
		mainWindow.getAnswerComboBox().setBorder(titledBorder);

	}

	private void updateToolBar() {
		mainWindow.getToolsMenu().setText(resourceBundle.getString("toolBarMenu"));
		mainWindow.getMnHelpMenu().setText(resourceBundle.getString("helpMenu"));
		mainWindow.getMnLanguageMenu().setText(resourceBundle.getString("languageMenu"));
	}

	private void updateToolBarMenuItems() {
		mainWindow.getMntmUpdateMenuItem().setText(resourceBundle.getString("updateMenuItem"));
		mainWindow.getMntmGeneratePDFMenuItem().setText(resourceBundle.getString("generatePDFMenuItem"));
		mainWindow.getMntmGenerateDocxMenuItem().setText(resourceBundle.getString("generateDOCXMenuItem"));
		mainWindow.getAboutMenuItem().setText(resourceBundle.getString("aboutMenuItem"));
	}

	private void updateAnswersString() {
		String answersString = resourceBundle.getString("answersString");
		wordDocument.setAnswersString(answersString);
	}

	private void updateCloseDialogString() {
		String closeConfirmationDialogString = resourceBundle.getString("closeConfirmationDialog");
		mainWindow.setCloseConfirmationDialog(closeConfirmationDialogString);
	}
}
