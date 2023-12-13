package quizGenerator.multipleChoice;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import org.apache.commons.text.WordUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.SwingConstants;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private String fontFilePathArial = "Arimo-Regular.ttf";
	private String fontFilePathArialBold = "Arimo-Bold.ttf";

	private JTextArea questionArea;
	private JTextField topicField;
	private List<JTextField> choiceFields;
	private JButton addQuestionButton;
	private JButton generatePdfButton;
	private JButton resetButton;
	private String topic;

	private List<String> questions;
	private List<String> answerKeys;
	private List<List<String>> choices;

	private JList<String> questionList;
	private DefaultListModel<String> questionListModel;
	private JButton editButton;
	private JComboBox<?> comboBox;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JMenuBar menuBar;
	private JMenu toolsMenu;
	private JMenu mnNewMenu;

	public MainWindow() {
		setTitle("Quiz Generator");
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		answerKeys = new ArrayList<>();
		questions = new ArrayList<>();
		choices = new ArrayList<>();

		initializeComponents();
	}

	private void initializeComponents() {

		choiceFields = new ArrayList<>();
		String[] choiceLabels = { "A", "B", "C", "D", "E" };

		// Buttons
		addQuestionButton = new JButton("Add Question");

		questionListModel = new DefaultListModel<>();
		questionList = new JList<>(questionListModel);
		JScrollPane questionScrollPane = new JScrollPane(questionList);

		editButton = new JButton("Edit Question");

		addQuestionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addQuestion();
			}
		});

		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = questionList.getSelectedIndex();
				if (selectedIndex != -1) {
					editQuestion(selectedIndex);
				}
			}
		});

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(new JLabel("Questions"), BorderLayout.NORTH);
		leftPanel.add(questionScrollPane, BorderLayout.CENTER);
		resetButton = new JButton("Reset Questions");

		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetQuestions();
			}
		});

		JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
		buttonPanel.add(editButton);
		buttonPanel.add(resetButton);
		leftPanel.add(buttonPanel, BorderLayout.SOUTH);

		JPanel middlePanel = new JPanel();
		SpringLayout sl_middlePanel = new SpringLayout();
		sl_middlePanel.putConstraint(SpringLayout.SOUTH, addQuestionButton, -10, SpringLayout.SOUTH, middlePanel);
		middlePanel.setLayout(sl_middlePanel);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		toolsMenu = new JMenu("Tools");
		toolsMenu.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(toolsMenu);
		generatePdfButton = new JButton("Generate PDF");
		toolsMenu.add(generatePdfButton);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, generatePdfButton, 10, SpringLayout.NORTH, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.EAST, generatePdfButton, 0, SpringLayout.EAST, comboBox);

		mnNewMenu = new JMenu("About");
		menuBar.add(mnNewMenu);

		// UPDATES

		JButton checkUpdateButton = new JButton("Check for Updates");
		mnNewMenu.add(checkUpdateButton);

		checkUpdateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				performUpdateCheck();
			}
		});

		generatePdfButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generatePdf();
			}
		});

		// Topic
		JLabel label = new JLabel("Topic:");
		sl_middlePanel.putConstraint(SpringLayout.NORTH, label, 10, SpringLayout.NORTH, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.WEST, label, 28, SpringLayout.WEST, middlePanel);
		middlePanel.add(label);
		topicField = new JTextField(20);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, topicField, 6, SpringLayout.SOUTH, label);
		sl_middlePanel.putConstraint(SpringLayout.WEST, topicField, 0, SpringLayout.WEST, label);
		sl_middlePanel.putConstraint(SpringLayout.EAST, topicField, -299, SpringLayout.EAST, middlePanel);
//		topicField.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				topic = topicField.getText();
//			}
//		});
		middlePanel.add(topicField);

		// Question
		JLabel label_1 = new JLabel("Question:");
		sl_middlePanel.putConstraint(SpringLayout.NORTH, label_1, 6, SpringLayout.SOUTH, topicField);
		sl_middlePanel.putConstraint(SpringLayout.WEST, label_1, 0, SpringLayout.WEST, label);
		middlePanel.add(label_1);

		// Answer Keys
		JLabel label_2 = new JLabel("Answer:");
		sl_middlePanel.putConstraint(SpringLayout.WEST, addQuestionButton, 0, SpringLayout.WEST, label_2);
		sl_middlePanel.putConstraint(SpringLayout.SOUTH, label_2, -227, SpringLayout.SOUTH, middlePanel);
		middlePanel.add(label_2);
		comboBox = new JComboBox();
		sl_middlePanel.putConstraint(SpringLayout.EAST, addQuestionButton, 0, SpringLayout.EAST, comboBox);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, comboBox, 6, SpringLayout.SOUTH, label_2);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Select", "A", "B", "C", "D", "E" }));

//		comboBox.addActionListener(e -> {
//			JComboBox c = (JComboBox) e.getSource();
//			answerKeys.add(c.getSelectedItem().toString());
//		});

		middlePanel.add(comboBox);

		// Choices
		JLabel label_3 = new JLabel("Choices:");
		sl_middlePanel.putConstraint(SpringLayout.WEST, label_3, 0, SpringLayout.WEST, label);
		middlePanel.add(label_3);
		middlePanel.add(addQuestionButton);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());

		questionArea = new JTextArea(15, 15);
		sl_middlePanel.putConstraint(SpringLayout.EAST, comboBox, 0, SpringLayout.EAST, questionArea);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, label_3, 6, SpringLayout.SOUTH, questionArea);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, questionArea, 6, SpringLayout.SOUTH, label_1);
		sl_middlePanel.putConstraint(SpringLayout.SOUTH, questionArea, -335, SpringLayout.SOUTH, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.WEST, questionArea, 0, SpringLayout.WEST, label);
		sl_middlePanel.putConstraint(SpringLayout.EAST, questionArea, -49, SpringLayout.EAST, middlePanel);
		questionArea.setTabSize(15);
		questionArea.setLineWrap(true);
		middlePanel.add(questionArea);

		textField = new JTextField();
		sl_middlePanel.putConstraint(SpringLayout.WEST, comboBox, 26, SpringLayout.EAST, textField);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, textField, 6, SpringLayout.SOUTH, label_3);
		sl_middlePanel.putConstraint(SpringLayout.WEST, textField, 0, SpringLayout.WEST, label);
		sl_middlePanel.putConstraint(SpringLayout.EAST, textField, -240, SpringLayout.EAST, middlePanel);
		middlePanel.add(textField);
		choiceFields.add(textField);
		textField.setBorder(BorderFactory.createTitledBorder(choiceLabels[0]));
		textField.setColumns(50);

		textField_1 = new JTextField();
		sl_middlePanel.putConstraint(SpringLayout.WEST, label_2, 26, SpringLayout.EAST, textField_1);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, textField_1, 6, SpringLayout.SOUTH, textField);
		sl_middlePanel.putConstraint(SpringLayout.WEST, textField_1, 0, SpringLayout.WEST, label);
		sl_middlePanel.putConstraint(SpringLayout.EAST, textField_1, -240, SpringLayout.EAST, middlePanel);
		textField_1.setColumns(50);
		choiceFields.add(textField_1);
		textField_1.setBorder(BorderFactory.createTitledBorder(choiceLabels[1]));
		middlePanel.add(textField_1);

		textField_2 = new JTextField();
		sl_middlePanel.putConstraint(SpringLayout.NORTH, textField_2, 6, SpringLayout.SOUTH, textField_1);
		sl_middlePanel.putConstraint(SpringLayout.WEST, textField_2, 0, SpringLayout.WEST, label);
		sl_middlePanel.putConstraint(SpringLayout.EAST, textField_2, -240, SpringLayout.EAST, middlePanel);
		textField_2.setColumns(50);
		choiceFields.add(textField_2);
		textField_2.setBorder(BorderFactory.createTitledBorder(choiceLabels[2]));
		middlePanel.add(textField_2);

		textField_3 = new JTextField();
		sl_middlePanel.putConstraint(SpringLayout.NORTH, textField_3, 6, SpringLayout.SOUTH, textField_2);
		sl_middlePanel.putConstraint(SpringLayout.WEST, textField_3, 0, SpringLayout.WEST, label);
		sl_middlePanel.putConstraint(SpringLayout.EAST, textField_3, 0, SpringLayout.EAST, textField);
		textField_3.setColumns(50);
		choiceFields.add(textField_3);
		textField_3.setBorder(BorderFactory.createTitledBorder(choiceLabels[3]));
		middlePanel.add(textField_3);

		textField_4 = new JTextField();
		sl_middlePanel.putConstraint(SpringLayout.NORTH, textField_4, 6, SpringLayout.SOUTH, textField_3);
		sl_middlePanel.putConstraint(SpringLayout.WEST, textField_4, 0, SpringLayout.WEST, label);
		sl_middlePanel.putConstraint(SpringLayout.EAST, textField_4, 0, SpringLayout.EAST, textField);
		textField_4.setColumns(50);
		choiceFields.add(textField_4);
		textField_4.setBorder(BorderFactory.createTitledBorder(choiceLabels[4]));
		middlePanel.add(textField_4);
		JPanel mainPanel = new JPanel(new BorderLayout(25, 25));
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(rightPanel, BorderLayout.EAST);

		getContentPane().add(mainPanel);
	}

	private void performUpdateCheck() {
		// UpdateChecker sınıfını kullanarak güncelleme kontrolü yapın
		UpdateChecker updateChecker = new UpdateChecker();
		updateChecker.checkForUpdates(this);
	}

	private void addQuestion() {
		String question = questionArea.getText();
		List<String> choicesForQuestion = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			choicesForQuestion.add(choiceFields.get(i).getText());
			choiceFields.get(i).setText("");
		}

		answerKeys.add(comboBox.getSelectedItem().toString());

		questions.add(question);
		choices.add(choicesForQuestion);

		int index = questionListModel.size() + 1;
		questionListModel.addElement(index + ". Question");

		resetButton.setVisible(true);

		questionArea.setText("");
	}

	private void resetQuestions() {
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset all questions?",
				"Reset Confirmation", JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			questions.clear();
			answerKeys.clear();
			choices.clear();
			questionListModel.clear();
		}
	}

	private void editQuestion(int index) {
		String question = questions.get(index);
		String answer = answerKeys.get(index);
		List<String> choicesForQuestion = choices.get(index);

		EditQuestionDialog editDialog = new EditQuestionDialog(this, question, choicesForQuestion, answer);
		editDialog.setVisible(true);

		if (editDialog.isDelete()) {
			deleteQuestion(index);
		}

		if (editDialog.isConfirmed()) {
			questions.set(index, editDialog.getEditedQuestion());
			answerKeys.set(index, editDialog.getEditedAnswer());
			choices.set(index, editDialog.getEditedChoices());
			questionListModel.setElementAt(index + 1 + ". Soru", index);
		}
	}

	private void deleteQuestion(int index) {
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this question?",
				"Delete Confirmation", JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			questions.remove(index);
			choices.remove(index);
			questionListModel.remove(index);
		}
	}

	private void generatePdf() {
		try {

			topic = topicField.getText();
			PDDocument document = new PDDocument();

			int questionCount = questions.size();
			int pageCount = (int) Math.ceil((double) questionCount / 4); // Page number

			for (int pageIdx = 0; pageIdx < pageCount; pageIdx++) {
				PDPage page = new PDPage(PDRectangle.A4);
				document.addPage(page);
				PDPageContentStream contentStream = new PDPageContentStream(document, page);

				float margin = 25;
				float yStart = page.getMediaBox().getHeight() - margin;
				float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
				float yPosition = yStart;
				float bottomMargin = 70;
				float tableHeight = yStart - bottomMargin;

				// Add topic
				contentStream.beginText();
				contentStream.setFont(
						PDType0Font.load(document, ResourceHelper.getResourceAsStream(fontFilePathArialBold)), 14);
				contentStream.newLineAtOffset(margin, yStart);
				contentStream.showText(topic);
				contentStream.endText();

				// Draw the table headers
				float headerYPosition = yPosition;
				float headerYStart = headerYPosition;
				float tableYPosition = yStart - 20;

				// Draw the middle line
				for (int i = 0; i < 2; i++) {
					float nextXStart = margin + (tableWidth / 2) * i;
					contentStream.moveTo(nextXStart, headerYPosition);
					if (i == 1) {
						contentStream.lineTo(nextXStart, headerYStart - tableHeight);
					}
					contentStream.stroke();
				}

				headerYPosition -= 15;

				// Draw the content
				for (int i = 0; i < 2; i++) {
					float nextXStart = margin + (tableWidth / 2) * i;

					// Questions
					for (int j = 0; j < 2; j++) {
						int questionIndex = pageIdx * 4 + i * 2 + j;
						if (questionIndex < questionCount) {
							String question = questions.get(questionIndex);
							List<String> choicesForQuestion = choices.get(questionIndex);

							contentStream.beginText();
							// Set the font
							contentStream.setFont(
									PDType0Font.load(document, ResourceHelper.getResourceAsStream(fontFilePathArial)),
									10);
							if (j == 0) {
								contentStream.newLineAtOffset(nextXStart + 10, tableYPosition - 50);
							} else {
								contentStream.newLineAtOffset(nextXStart + 10, tableYPosition - 400);
							}

							contentStream.showText((questionIndex + 1) + ") ");
							contentStream.setFont(PDType0Font.load(document,
									ResourceHelper.getResourceAsStream(fontFilePathArialBold)), 10);
							contentStream.newLineAtOffset(0, -15);
							String[] wrappedText = WordUtils.wrap(question, 100).split("\\r?\\n");

							for (int k = 0; k < wrappedText.length; k++) {
								if (k > 0) {
									contentStream.newLineAtOffset(0, -15); // Add a space before moving to a new line
								}
								contentStream.showText(wrappedText[k]);
							}

							// Choices
							for (int k = 0; k < choicesForQuestion.size(); k++) {
								contentStream.newLineAtOffset(0, -15);
								contentStream.showText((char) ('A' + k) + ") " + choicesForQuestion.get(k));
							}

							contentStream.endText();
						}
					}
				}

				// Add page number
				contentStream.beginText();
				contentStream.setFont(
						PDType0Font.load(document, ResourceHelper.getResourceAsStream(fontFilePathArialBold)), 12);
				float centerX = (page.getMediaBox().getWidth() / 2) - 3;
				float centerY = headerYStart - tableHeight - 20;
				contentStream.newLineAtOffset(centerX, centerY);
				contentStream.showText("" + (pageIdx + 1));
				contentStream.endText();

				contentStream.close();
			}

			addAnswerKeyPage(document);

			// Check existing file name
			String pdfFileName = topic + ".pdf";

			File pdfFile = new File(pdfFileName);
			int fileCount = 1;
			while (pdfFile.exists()) {
				pdfFileName = topic + "_" + fileCount + ".pdf";
				pdfFile = new File(pdfFileName);
				fileCount++;
			}

			document.save(pdfFileName);
			document.close();

			JOptionPane.showMessageDialog(this, "PDF Generated: " + pdfFileName, "Info",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "File not found: " + e.getMessage(), "File Not Found",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error during PDF generation: \n" + e.getMessage(), "IO Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "An unexpected error occurred: \n" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addAnswerKeyPage(PDDocument document) throws IOException {

		PDPage answerKeyPage = new PDPage(PDRectangle.A4);
		document.addPage(answerKeyPage);

		PDPageContentStream contentStream = new PDPageContentStream(document, answerKeyPage);
		float margin = 25;
		float yStart = answerKeyPage.getMediaBox().getHeight() - margin;

		contentStream.beginText();
		contentStream.setFont(PDType0Font.load(document, ResourceHelper.getResourceAsStream(fontFilePathArialBold)),
				12);
		contentStream.newLineAtOffset(margin, yStart);
		contentStream.showText("Answers");
		contentStream.endText();

		float yPosition = yStart - 20;
		for (int i = 0; i < answerKeys.size(); i++) {
			contentStream.beginText();
			contentStream.setFont(PDType0Font.load(document, ResourceHelper.getResourceAsStream(fontFilePathArial)),
					10);
			contentStream.newLineAtOffset(margin, yPosition);
			contentStream.showText((i + 1) + "-) " + answerKeys.get(i));
			contentStream.endText();
			yPosition -= 20;
		}

		contentStream.close();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainWindow().setVisible(true);
			}
		});
	}
}