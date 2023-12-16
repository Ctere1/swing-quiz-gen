package quizGenerator.multipleChoice;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;

public class LanguageSelection extends MainWindow {

	private static ResourceBundle resourceBundle;

	public static void setLocale(Locale locale) {
		resourceBundle = ResourceBundle.getBundle("LANGUAGE", locale);
		ConfigurationManager.setSelectedLanguage(locale); 
		updateButtonTexts();
		updateTitles();
		updateComboBoxes();
		updateToolBar();
		updateToolBarMenuItems();
	}

	private static void updateButtonTexts() {
		addQuestionButton.setText(resourceBundle.getString("addQuestionButton"));
		resetButton.setText(resourceBundle.getString("resetButton"));
		editButton.setText(resourceBundle.getString("editButton"));
		cutButton.setText(resourceBundle.getString("cutButton"));
		copyButton.setText(resourceBundle.getString("copyButton"));
		pasteButton.setText(resourceBundle.getString("pasteButton"));
	}

	private static void updateTitles() {
		TitledBorder titledBorder = (TitledBorder) topicField.getBorder();
		titledBorder.setTitle(resourceBundle.getString("topicField"));
		topicField.setBorder(titledBorder);

		titledBorder = (TitledBorder) questionScrollPane.getBorder();
		titledBorder.setTitle(resourceBundle.getString("questionScrollPaneField"));
		questionScrollPane.setBorder(titledBorder);

		titledBorder = (TitledBorder) questionArea.getBorder();
		titledBorder.setTitle(resourceBundle.getString("questionAreaField"));
		questionArea.setBorder(titledBorder);

	}

	private static void updateComboBoxes() {
		TitledBorder titledBorder = (TitledBorder) answerComboBox.getBorder();
		String[] comboBoxItems = resourceBundle.getString("answerComboBoxIndexes").split(",");
		answerComboBox.setModel(new DefaultComboBoxModel(comboBoxItems));
		titledBorder.setTitle(resourceBundle.getString("answerComboBox"));
		answerComboBox.setBorder(titledBorder);

	}

	private static void updateToolBar() {
		toolsMenu.setText(resourceBundle.getString("toolBarMenu"));
		mnHelpMenu.setText(resourceBundle.getString("helpMenu"));
		mnLanguageMenu.setText(resourceBundle.getString("languageMenu"));
	}

	private static void updateToolBarMenuItems() {
		mntmUpdateMenuItem.setText(resourceBundle.getString("updateMenuItem"));
		mntmGeneratePDFMenuItem.setText(resourceBundle.getString("generatePDFMenuItem"));
		mntmGenerateDocxMenuItem.setText(resourceBundle.getString("generateDOCXMenuItem"));
		aboutMenuItem.setText(resourceBundle.getString("aboutMenuItem"));
	}
}
