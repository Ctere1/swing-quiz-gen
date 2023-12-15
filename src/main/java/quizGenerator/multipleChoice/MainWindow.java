package quizGenerator.multipleChoice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

import org.apache.commons.text.WordUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.JMenuItem;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import java.awt.SystemColor;

public class MainWindow {

	private JFrame mainFrame;
	private String fontFilePathArimo = "Arimo-Regular.ttf";
	private String fontFilePathArimoBold = "Arimo-Bold.ttf";

	private JTextPane questionArea;
	private JScrollPane questionScrollPane;
	private JScrollPane questionAreaScrollPane;
	private JTextField topicField;
	private List<JTextField> choiceFields;
	private JButton addQuestionButton;
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
	private JMenuItem mntmUpdateMenuItem;
	private JMenuItem mntmGeneratePDFMenuItem;
	private JMenuItem mntmGenerateDocxMenuItem;

	private JToolBar toolBar;

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initializeComponents();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeComponents() {
		FlatDarkLaf.setup();
		try {
			System.setProperty("flatlaf.useWindowDecorations", "true");
			System.setProperty("flatlaf.menuBarEmbedded", "true");
			UIManager.setLookAndFeel(new FlatDarkLaf());

		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		answerKeys = new ArrayList<>();
		questions = new ArrayList<>();
		choices = new ArrayList<>();
		// MAIN FRAME
		mainFrame = new JFrame("Quiz Generator");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(400, 300);
		mainFrame.setSize(1000, 600);
		mainFrame.setMinimumSize(new Dimension(800, 500));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.getRootPane().putClientProperty("JRootPane.titleBarShowIcon", true);
		mainFrame.getRootPane().putClientProperty("JRootPane.titleBarBackground", Color.DARK_GRAY);

		try {
			Image iconImage = ImageIO.read(ResourceHelper.getResourceAsStream("file.png"));
			mainFrame.setIconImage(iconImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainFrame.setVisible(true);

		choiceFields = new ArrayList<>();
		String[] choiceLabels = { "A", "B", "C", "D", "E" };

		// Buttons
		addQuestionButton = new JButton("Add Question");

		questionListModel = new DefaultListModel<>();
		questionList = new JList<>(questionListModel);
		questionList.setToolTipText("");
		JScrollPane questionScrollPane = new JScrollPane(questionList);
		questionScrollPane.setToolTipText("");

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
		JLabel label_4 = new JLabel("Questions");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		leftPanel.add(label_4, BorderLayout.NORTH);
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

		// TOOLS
		menuBar = new JMenuBar();
		mainFrame.setJMenuBar(menuBar);

		toolsMenu = new JMenu("Tools");
		toolsMenu.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(toolsMenu);
		mntmGeneratePDFMenuItem = new JMenuItem("Generate PDF");
		toolsMenu.add(mntmGeneratePDFMenuItem);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, mntmGeneratePDFMenuItem, 10, SpringLayout.NORTH, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.EAST, mntmGeneratePDFMenuItem, 0, SpringLayout.EAST, comboBox);

		mntmGenerateDocxMenuItem = new JMenuItem("Generate DOCX");
		toolsMenu.add(mntmGenerateDocxMenuItem);

		mntmGenerateDocxMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateWordDocument();
			}
		});

		mntmGeneratePDFMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generatePdf();
			}
		});
		// UPDATES
		mnNewMenu = new JMenu("Help");
		menuBar.add(mnNewMenu);

		mntmUpdateMenuItem = new JMenuItem("Check for Updates");
		mnNewMenu.add(mntmUpdateMenuItem);
		JMenuItem aboutMenuItem = new JMenuItem("About");
		mnNewMenu.add(aboutMenuItem);

		mntmUpdateMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				performUpdateCheck();
			}
		});

		aboutMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AboutDialog aboutDialog = new AboutDialog(mainFrame);
				aboutDialog.setVisible(true);
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
		middlePanel.add(comboBox);

		// Choices
		JLabel label_3 = new JLabel("Choices:");
		sl_middlePanel.putConstraint(SpringLayout.WEST, label_3, 0, SpringLayout.WEST, label);
		middlePanel.add(label_3);
		middlePanel.add(addQuestionButton);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());

		questionArea = new JTextPane();
		questionAreaScrollPane = new JScrollPane(questionArea);
		questionAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		questionAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sl_middlePanel.putConstraint(SpringLayout.EAST, comboBox, 0, SpringLayout.EAST, questionAreaScrollPane);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, label_3, 6, SpringLayout.SOUTH, questionAreaScrollPane);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, questionAreaScrollPane, 6, SpringLayout.SOUTH, label_1);
		sl_middlePanel.putConstraint(SpringLayout.SOUTH, questionAreaScrollPane, -335, SpringLayout.SOUTH, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.WEST, questionAreaScrollPane, 0, SpringLayout.WEST, label);
		sl_middlePanel.putConstraint(SpringLayout.EAST, questionAreaScrollPane, -49, SpringLayout.EAST, middlePanel);
		questionArea.setEditable(true);
		middlePanel.add(questionAreaScrollPane);

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

		// TOOLBAR
		toolBar = new JToolBar();
		toolBar.setBackground(SystemColor.controlDkShadow);
		toolBar.setFloatable(false);

		addToolbarButtons();
		mainFrame.getContentPane().add(toolBar, BorderLayout.NORTH);

		mainFrame.getContentPane().add(mainPanel);
	}

	private void addToolbarButtons() {
		addButtonToToolBar("Copy", "copy.png", new DefaultEditorKit.CopyAction(), 16, 16);
		addButtonToToolBar("Cut", "cut.png", new DefaultEditorKit.CutAction(), 16, 16);
		addButtonToToolBar("Paste", "paste.png", new DefaultEditorKit.PasteAction(), 16, 16);

		/**
		 * CAN NOT SAVE THESE IN PDF FOR NOW SO I DISABLED
		 */
//		toolBar.addSeparator();
//		addStyleButton("Bold", "bold.png", "font-bold", 16, 16);
//		addStyleButton("Italic", "italic.png", "font-italic", 16, 16);
//		addStyleButton("Underline", "underline.png", "font-underline", 16, 16);
//
//		toolBar.addSeparator();
//		addAlignmentButton("Left", "align-left.png", StyleConstants.ALIGN_LEFT, 16, 16);
//		addAlignmentButton("Center", "align-center.png", StyleConstants.ALIGN_CENTER, 16, 16);
//		addAlignmentButton("Right", "align-right.png", StyleConstants.ALIGN_RIGHT, 16, 16);
//		addAlignmentButton("Justify", "align-justify.png", StyleConstants.ALIGN_JUSTIFIED, 16, 16);
//
//		toolBar.addSeparator();
//		addFontSelector();
	}

	private void addStyleButton(String text, String iconName, final String styleName, int iconWidth, int iconHeight) {
		Action action = new StyledEditorKit.StyledTextAction(text) {
			@Override
			public void actionPerformed(ActionEvent e) {
				JEditorPane editor = getEditor(e);
				if (editor != null) {
					StyledEditorKit kit = getStyledEditorKit(editor);
					MutableAttributeSet attr = kit.getInputAttributes();
					boolean isStyleSet = StyleConstants.isBold(attr) || StyleConstants.isItalic(attr)
							|| StyleConstants.isUnderline(attr);

					switch (styleName) {
					case "bold":
						StyleConstants.setBold(attr, !isStyleSet);
						break;
					case "italic":
						StyleConstants.setItalic(attr, !isStyleSet);
						break;
					case "underline":
						StyleConstants.setUnderline(attr, !isStyleSet);
						break;
					}
					setCharacterAttributes(editor, new SimpleAttributeSet(), false);

				}
			}
		};

		addToggleButtonToToolBar(text, iconName, action, iconWidth, iconHeight);
	}

	private void addAlignmentButton(String text, String iconName, int alignment, int iconWidth, int iconHeight) {
		Action action = new StyledEditorKit.AlignmentAction(text, alignment);
		addToggleButtonToToolBar(text, iconName, action, iconWidth, iconHeight);
	}

	private void addButtonToToolBar(String text, String iconName, Action action, int iconWidth, int iconHeight) {
		JButton button = new JButton(action);
		button.setText(text);
		button.setToolTipText(text);
		button.setFocusable(false);

		ImageIcon icon = new ImageIcon(getClass().getResource("/" + iconName));
		Image scaledImage = icon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
		button.setIcon(new ImageIcon(scaledImage));
		toolBar.add(button);
	}

	private void addToggleButtonToToolBar(String text, String iconName, Action action, int iconWidth, int iconHeight) {
		JToggleButton button = new JToggleButton(action);
		button.setText(text);
		button.setToolTipText(text);
		button.setFocusable(false);

		ImageIcon icon = new ImageIcon(getClass().getResource("/" + iconName));
		Image scaledImage = icon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
		button.setIcon(new ImageIcon(scaledImage));
		toolBar.add(button);
	}

	private void addFontSelector() {
		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		JComboBox<String> fontSelector = new JComboBox<>(fontNames);
		fontSelector.addActionListener(e -> {
			String selectedFont = (String) fontSelector.getSelectedItem();
			if (selectedFont != null) {
				Action action = new StyledEditorKit.FontFamilyAction(selectedFont, selectedFont);
				action.actionPerformed(new ActionEvent(fontSelector, ActionEvent.ACTION_PERFORMED, null));
			}
		});

		toolBar.add(new JLabel("Font: "));
		toolBar.add(fontSelector);
	}

	private void performUpdateCheck() {
		// UpdateChecker sınıfını kullanarak güncelleme kontrolü yapın
		UpdateChecker updateChecker = new UpdateChecker();
		updateChecker.checkForUpdates(mainFrame);
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
		int result = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to reset all questions?",
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

		EditQuestionDialog editDialog = new EditQuestionDialog(mainFrame, question, choicesForQuestion, answer);
		editDialog.setVisible(true);

		if (editDialog.isDelete()) {
			deleteQuestion(index);
		}

		if (editDialog.isConfirmed()) {
			questions.set(index, editDialog.getEditedQuestion());
			answerKeys.set(index, editDialog.getEditedAnswer());
			choices.set(index, editDialog.getEditedChoices());
			questionListModel.setElementAt(index + 1 + ". Question", index);
		}
	}

	private void deleteQuestion(int index) {
		int result = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to delete this question?",
				"Delete Confirmation", JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			questions.remove(index);
			choices.remove(index);
			answerKeys.remove(index);
			questionListModel.remove(index);
		}
	}

	private void generateWordDocument() {
		try {
			topic = topicField.getText();
			XWPFDocument document = new XWPFDocument();

			for (int questionIndex = 0; questionIndex < questions.size(); questionIndex++) {
				String question = questions.get(questionIndex);
				List<String> choicesForQuestion = choices.get(questionIndex);

				// Add question to document
				XWPFParagraph paragraph = document.createParagraph();
				XWPFRun run = paragraph.createRun();
				run.setBold(true);
				run.setText((questionIndex + 1) + ")");
				run.setBold(false);
				run.addBreak();

				run.setText(question);

				// Add choices to document
				for (int choiceIndex = 0; choiceIndex < choicesForQuestion.size(); choiceIndex++) {
					paragraph = document.createParagraph();
					run = paragraph.createRun();
					run.setText((char) ('A' + choiceIndex) + ") " + choicesForQuestion.get(choiceIndex));
				}
				run.addBreak();
			}

			// Add page break before the inserting answers
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();
			run.addBreak(BreakType.PAGE);
			addAnswerKeyPage(document);

			// Save the Word document
			String docxFileName = topic + ".docx";

			File docxFile = new File(docxFileName);
			int fileCount = 1;
			while (docxFile.exists()) {
				docxFileName = topic + "_" + fileCount + ".docx";
				docxFile = new File(docxFileName);
				fileCount++;
			}

			try (FileOutputStream out = new FileOutputStream(docxFileName)) {
				document.write(out);
				JOptionPane.showMessageDialog(mainFrame, "Word Document Generated: " + docxFileName, "Info",
						JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mainFrame, "An unexpected error occurred: \n" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addAnswerKeyPage(XWPFDocument document) throws IOException {
		XWPFParagraph paragraph = document.createParagraph();
		setBoldText(paragraph.createRun(), "Answers", 14);

		for (int i = 0; i < answerKeys.size(); i++) {
			XWPFParagraph answerParagraph = document.createParagraph();
			setNormalText(answerParagraph.createRun(), (i + 1) + "-) " + answerKeys.get(i), 12);
		}
	}

	private void setBoldText(XWPFRun run, String text, int fontSize) {
		run.setText(text);
		run.setBold(true);
		run.setFontSize(fontSize);
	}

	private void setNormalText(XWPFRun run, String text, int fontSize) {
		run.setText(text);
		run.setBold(false);
		run.setFontSize(fontSize);
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
				float yStart = page.getMediaBox().getHeight();
				float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
				float bottomMargin = 70;
				float tableHeight = yStart - bottomMargin;

				// Add topic
				contentStream.beginText();
				contentStream.setFont(
						PDType0Font.load(document, ResourceHelper.getResourceAsStream(fontFilePathArimoBold)), 11);
				contentStream.newLineAtOffset(margin, yStart - margin);
				contentStream.setLeading(14.5f);
				contentStream.showText(topic);
				contentStream.endText();

				// Draw the table headers
				float headerYPosition = yStart - margin;
				float headerYStart = headerYPosition;
				float tableYPosition = yStart - 20;

				// Draw the middle line
				for (int i = 0; i < 2; i++) {
					float nextXStart = margin + (tableWidth / 2) * i;
					contentStream.moveTo(nextXStart, headerYPosition);
					if (i == 1) {
						contentStream.lineTo(nextXStart, headerYStart - tableHeight);
					}
					contentStream.setLineWidth(0.5f);
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
							contentStream.setFont(PDType0Font.load(document,
									ResourceHelper.getResourceAsStream(fontFilePathArimoBold)), 9);
							if (j == 0) {
								contentStream.newLineAtOffset(nextXStart + 10, tableYPosition - 50);
							} else {
								contentStream.newLineAtOffset(nextXStart + 10, tableYPosition - 450);
							}

							// QUESTION NUMBER TEXT
							contentStream.showText((questionIndex + 1) + ") ");
							contentStream.setFont(
									PDType0Font.load(document, ResourceHelper.getResourceAsStream(fontFilePathArimo)),
									9);
							contentStream.newLineAtOffset(0, -15);

							/**
							 * 
							 * TRYING NEW ALG
							 * 
							 * 
							 */
//							char[] charArray = question.toCharArray();
//							StringBuilder stringBuilder = new StringBuilder();
//							PDType0Font font = PDType0Font.load(document,
//									ResourceHelper.getResourceAsStream(fontFilePathArial));
//
//							for (int q = 0; q < charArray.length; q++) {
//								stringBuilder.append(charArray[q]);
//
//								// New line at 60 char
//								if ((q + 1) % 60 == 0) {
//									contentStream.setFont(font, 9);
//									System.out.println(stringBuilder.toString().replaceAll("[\\r\\n]", ""));
//									contentStream.showText(stringBuilder.toString().replaceAll("[\\r\\n]", ""));
//									contentStream.newLineAtOffset(0, -15);
//									stringBuilder.setLength(0); // StringBuilder'ı sıfırla
//								}
//							}
//
//							
//							if (stringBuilder.length() > 0) {
//								contentStream.setFont(font, 9);
//								System.out.println(stringBuilder.toString().replaceAll("[\\r\\n]", ""));
//								contentStream.showText(stringBuilder.toString().replaceAll("[\\r\\n]", ""));
//								contentStream.newLineAtOffset(0, -15);
//							}
							/**
							 * 
							 * TRYING NEW ALG
							 * 
							 * 
							 */

							// QUESTION TEXT
							String[] wrappedText = WordUtils.wrap(question, 55).split("\\r?\\n");
							for (int k = 0; k < wrappedText.length; k++) {
								contentStream.showText(wrappedText[k]);
								contentStream.newLineAtOffset(0, -15); // Add a space before moving to a new line
							}

							// QUESTION CHOICES write index ABCDE
							for (int k = 0; k < choicesForQuestion.size(); k++) {
								contentStream.setFont(PDType0Font.load(document,
										ResourceHelper.getResourceAsStream(fontFilePathArimoBold)), 9);
								contentStream.showText((char) ('A' + k) + ") ");

								// check choice's lenght, write text
								String[] wrappedChoice = WordUtils.wrap(choicesForQuestion.get(k), 55).split("\\r?\\n");
								contentStream.setFont(PDType0Font.load(document,
										ResourceHelper.getResourceAsStream(fontFilePathArimo)), 9);
								for (int o = 0; o < wrappedChoice.length; o++) {
									contentStream.showText(wrappedChoice[o]);
									contentStream.newLineAtOffset(0, -15); // Add a space before moving to a new line
								}

							}

							contentStream.endText();
						}
					}
				}

				// Add page number
				contentStream.beginText();
				contentStream.setFont(
						PDType0Font.load(document, ResourceHelper.getResourceAsStream(fontFilePathArimoBold)), 11);
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

			JOptionPane.showMessageDialog(mainFrame, "PDF Generated: " + pdfFileName, "Info",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mainFrame, "File not found: " + e.getMessage(), "File Not Found",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mainFrame, "Error during PDF generation: \n" + e.getMessage(), "IO Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mainFrame, "An unexpected error occurred: \n" + e.getMessage(), "Error",
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
		contentStream.setFont(PDType0Font.load(document, ResourceHelper.getResourceAsStream(fontFilePathArimoBold)),
				11);
		contentStream.newLineAtOffset(margin, yStart);
		contentStream.showText("Answers");
		contentStream.endText();

		float tableWidth = answerKeyPage.getMediaBox().getWidth() - 2 * margin;
		float yPosition = yStart - 20;

		int numColumns = 10;
		int numRows = (int) Math.ceil((double) answerKeys.size() / numColumns);

		float columnWidth = tableWidth / numColumns;
		contentStream.setFont(PDType0Font.load(document, ResourceHelper.getResourceAsStream(fontFilePathArimo)), 9);
		// Draw table content
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				int index = i * numColumns + j;
				if (index < answerKeys.size()) {
					float xPosition = margin + j * columnWidth;

					// Draw cell borders
					contentStream.setLineWidth(0.5f);

					// Draw left border of the cell
					contentStream.moveTo(xPosition, yPosition);
					contentStream.lineTo(xPosition, yPosition - 20);
					contentStream.stroke();

					// Draw top border of the cell
					contentStream.moveTo(xPosition, yPosition);
					contentStream.lineTo(xPosition + columnWidth, yPosition);
					contentStream.stroke();

					// Draw right border of the cell
					contentStream.moveTo(xPosition + columnWidth, yPosition);
					contentStream.lineTo(xPosition + columnWidth, yPosition - 20);
					contentStream.stroke();

					// Draw bottom border of the cell
					contentStream.moveTo(xPosition, yPosition - 20);
					contentStream.lineTo(xPosition + columnWidth, yPosition - 20);
					contentStream.stroke();

					contentStream.beginText();
					contentStream.newLineAtOffset(xPosition + 5, yPosition - 13);
					contentStream.showText((index + 1) + ") " + answerKeys.get(index));
					contentStream.endText();
				}
			}
			yPosition -= 20;
		}

		contentStream.close();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}