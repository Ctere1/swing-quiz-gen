package quizGenerator.multipleChoice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
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
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
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

import java.awt.SystemColor;
import javax.swing.border.EtchedBorder;

import com.spire.doc.Document;
import com.spire.doc.FieldType;
import com.spire.doc.FileFormat;
import com.spire.doc.HeaderFooter;
import com.spire.doc.Section;
import com.spire.doc.Table;
import com.spire.doc.TableCell;
import com.spire.doc.TableRow;
import com.spire.doc.documents.BorderStyle;
import com.spire.doc.documents.BreakType;
import com.spire.doc.documents.HorizontalAlignment;
import com.spire.doc.documents.ListStyle;
import com.spire.doc.documents.ListType;
import com.spire.doc.documents.PageSize;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.ParagraphStyle;
import com.spire.doc.documents.RowAlignment;
import com.spire.doc.documents.Style;
import com.spire.doc.documents.TextAlignment;
import com.spire.doc.fields.TextRange;

public class MainWindow {

	private JFrame mainFrame;
	private String fontFilePathArimo = "Arimo-Regular.ttf";
	private String fontFilePathArimoBold = "Arimo-Bold.ttf";

	protected static JTextPane questionArea;
	protected static JScrollPane questionScrollPane;
	private JScrollPane questionAreaScrollPane;
	protected static JTextField topicField;
	private List<JTextField> choiceFields;
	protected static JButton addQuestionButton;
	protected static JButton resetButton;
	protected static JButton editButton;
	protected static JButton pasteButton;
	protected static JButton copyButton;
	protected static JButton cutButton;
	private String topic;

	private List<String> questions;
	private List<String> answerKeys;
	private List<List<String>> choices;

	private JList<String> questionList;
	private DefaultListModel<String> questionListModel;
	protected static JComboBox<?> answerComboBox;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JMenuBar menuBar;
	protected static JMenu toolsMenu;
	protected static JMenu mnHelpMenu;
	protected static JMenu mnLanguageMenu;
	protected static JMenuItem mntmUpdateMenuItem;
	protected static JMenuItem mntmGeneratePDFMenuItem;
	protected static JMenuItem mntmGenerateDocxMenuItem;
	protected static JMenuItem aboutMenuItem;

	private JToolBar toolBar;
	private JMenuItem mntmLanguageMenuItemTurkish;
	private JMenuItem mntmLanguageMenuItemEnglish;

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
		questionScrollPane = new JScrollPane(questionList);
		questionScrollPane.setPreferredSize(new Dimension(50, 30));
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

		// Questions
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		questionScrollPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Questions",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 200, 0)));
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
		sl_middlePanel.putConstraint(SpringLayout.SOUTH, addQuestionButton, -21, SpringLayout.SOUTH, middlePanel);

		middlePanel.setLayout(sl_middlePanel);

		// TOOLS
		menuBar = new JMenuBar();
		mainFrame.setJMenuBar(menuBar);

		toolsMenu = new JMenu("Tools");
		toolsMenu.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(toolsMenu);
		mntmGeneratePDFMenuItem = new JMenuItem("Generate PDF");
		ImageIcon icon = new ImageIcon(getClass().getResource("/generate-pdf.png"));
		Image scaledImage = icon.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
		mntmGeneratePDFMenuItem.setIcon(new ImageIcon(scaledImage));

		toolsMenu.add(mntmGeneratePDFMenuItem);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, mntmGeneratePDFMenuItem, 10, SpringLayout.NORTH, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.EAST, mntmGeneratePDFMenuItem, 0, SpringLayout.EAST, answerComboBox);

		mntmGenerateDocxMenuItem = new JMenuItem("Generate DOCX");
		icon = new ImageIcon(getClass().getResource("/generate-docx.png"));
		scaledImage = icon.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
		mntmGenerateDocxMenuItem.setIcon(new ImageIcon(scaledImage));
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
		mnHelpMenu = new JMenu("Help");
		menuBar.add(mnHelpMenu);

		JMenuItem githubMenuItem = new JMenuItem("Github");
		icon = new ImageIcon(getClass().getResource("/github.png"));
		scaledImage = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		githubMenuItem.setIcon(new ImageIcon(scaledImage));
		mnHelpMenu.add(githubMenuItem);

		aboutMenuItem = new JMenuItem("About");
		icon = new ImageIcon(getClass().getResource("/about.png"));
		scaledImage = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		aboutMenuItem.setIcon(new ImageIcon(scaledImage));
		mnHelpMenu.add(aboutMenuItem);

		mnHelpMenu.add(new JSeparator());

		mntmUpdateMenuItem = new JMenuItem("Check for Updates");
		icon = new ImageIcon(getClass().getResource("/check-updates.png"));
		scaledImage = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		mntmUpdateMenuItem.setIcon(new ImageIcon(scaledImage));
		mnHelpMenu.add(mntmUpdateMenuItem);

		mnLanguageMenu = new JMenu("Language");
		menuBar.add(mnLanguageMenu);

		mntmLanguageMenuItemTurkish = new JMenuItem("Türkçe");
		icon = new ImageIcon(getClass().getResource("/turkey-flag.png"));
		scaledImage = icon.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
		mntmLanguageMenuItemTurkish.setIcon(new ImageIcon(scaledImage));
		mnLanguageMenu.add(mntmLanguageMenuItemTurkish);

		mntmLanguageMenuItemEnglish = new JMenuItem("English");
		icon = new ImageIcon(getClass().getResource("/uk-flag.png"));
		scaledImage = icon.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
		mntmLanguageMenuItemEnglish.setIcon(new ImageIcon(scaledImage));
		mnLanguageMenu.add(mntmLanguageMenuItemEnglish);

		mntmLanguageMenuItemEnglish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				LanguageSelection.setLocale(Locale.forLanguageTag("en"));
			}
		});

		mntmLanguageMenuItemTurkish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				LanguageSelection.setLocale(Locale.forLanguageTag("tr"));
			}
		});

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

		githubMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// GitHub repository URL'sini belirtin
					URI uri = new URI("https://github.com/Ctere1/swing-quiz-gen");
					// Varsayılan web tarayıcısını aç
					Desktop.getDesktop().browse(uri);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		// TOPIC
		topicField = new JTextField(20);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, topicField, 30, SpringLayout.NORTH, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.WEST, topicField, 28, SpringLayout.WEST, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.EAST, topicField, -299, SpringLayout.EAST, middlePanel);
		topicField.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Topic",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 200, 0)));
		middlePanel.add(topicField);

		// Answer
		answerComboBox = new JComboBox();
		sl_middlePanel.putConstraint(SpringLayout.NORTH, addQuestionButton, 132, SpringLayout.SOUTH, answerComboBox);
		sl_middlePanel.putConstraint(SpringLayout.EAST, addQuestionButton, 0, SpringLayout.EAST, answerComboBox);
		answerComboBox.setModel(new DefaultComboBoxModel(new String[] { "Select", "A", "B", "C", "D", "E" }));
		answerComboBox.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Answer",
				TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(255, 200, 0)));
		middlePanel.add(answerComboBox);
		middlePanel.add(addQuestionButton);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());

		// Question Area
		questionArea = new JTextPane();
		questionArea.setContentType("text/plain");
		questionArea.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Question",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 200, 0)));
		questionAreaScrollPane = new JScrollPane(questionArea);
		sl_middlePanel.putConstraint(SpringLayout.WEST, answerComboBox, -165, SpringLayout.EAST,
				questionAreaScrollPane);
		sl_middlePanel.putConstraint(SpringLayout.EAST, answerComboBox, 0, SpringLayout.EAST, questionAreaScrollPane);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, questionAreaScrollPane, 26, SpringLayout.SOUTH, topicField);
		sl_middlePanel.putConstraint(SpringLayout.WEST, questionAreaScrollPane, 28, SpringLayout.WEST, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.SOUTH, questionAreaScrollPane, -335, SpringLayout.SOUTH, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.EAST, questionAreaScrollPane, -49, SpringLayout.EAST, middlePanel);
		questionAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		questionAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		questionArea.setEditable(true);
		middlePanel.add(questionAreaScrollPane);

		textField = new JTextField();
		sl_middlePanel.putConstraint(SpringLayout.NORTH, textField, 26, SpringLayout.SOUTH, questionAreaScrollPane);
		sl_middlePanel.putConstraint(SpringLayout.WEST, textField, 28, SpringLayout.WEST, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.EAST, textField, -240, SpringLayout.EAST, middlePanel);
		middlePanel.add(textField);
		choiceFields.add(textField);
		textField.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), choiceLabels[0],
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 200, 0)));
		textField.setColumns(50);

		textField_1 = new JTextField();
		sl_middlePanel.putConstraint(SpringLayout.WEST, textField_1, 28, SpringLayout.WEST, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.EAST, textField_1, -240, SpringLayout.EAST, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, textField_1, 6, SpringLayout.SOUTH, textField);
		textField_1.setColumns(50);
		choiceFields.add(textField_1);
		textField_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), choiceLabels[1],
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.ORANGE));
		middlePanel.add(textField_1);

		textField_2 = new JTextField();
		sl_middlePanel.putConstraint(SpringLayout.NORTH, answerComboBox, -5, SpringLayout.NORTH, textField_2);
		sl_middlePanel.putConstraint(SpringLayout.SOUTH, answerComboBox, 39, SpringLayout.NORTH, textField_2);
		sl_middlePanel.putConstraint(SpringLayout.WEST, textField_2, 28, SpringLayout.WEST, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.EAST, textField_2, 0, SpringLayout.EAST, textField);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, textField_2, 6, SpringLayout.SOUTH, textField_1);
		textField_2.setColumns(50);
		choiceFields.add(textField_2);
		textField_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), choiceLabels[2],
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 200, 0)));
		middlePanel.add(textField_2);

		textField_3 = new JTextField();
		sl_middlePanel.putConstraint(SpringLayout.NORTH, textField_3, 6, SpringLayout.SOUTH, textField_2);
		sl_middlePanel.putConstraint(SpringLayout.WEST, textField_3, 28, SpringLayout.WEST, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.EAST, textField_3, 0, SpringLayout.EAST, textField);
		textField_3.setColumns(50);
		choiceFields.add(textField_3);
		textField_3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), choiceLabels[3],
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 200, 0)));
		middlePanel.add(textField_3);

		textField_4 = new JTextField();
		sl_middlePanel.putConstraint(SpringLayout.NORTH, textField_4, 6, SpringLayout.SOUTH, textField_3);
		sl_middlePanel.putConstraint(SpringLayout.WEST, textField_4, 28, SpringLayout.WEST, middlePanel);
		sl_middlePanel.putConstraint(SpringLayout.EAST, textField_4, 0, SpringLayout.EAST, textField);
		textField_4.setColumns(50);
		choiceFields.add(textField_4);
		textField_4.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), choiceLabels[4],
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 200, 0)));
		middlePanel.add(textField_4);

		JPanel mainPanel = new JPanel(new BorderLayout(25, 25));
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(rightPanel, BorderLayout.EAST);

		// TOOLBAR
		toolBar = new JToolBar();
		toolBar.setBackground(SystemColor.controlDkShadow);
		toolBar.setFloatable(false);

		int toolbarHeight = 30;
		Dimension toolbarDimension = new Dimension(toolBar.getWidth(), toolbarHeight);
		toolBar.setPreferredSize(toolbarDimension);
		addToolbarButtons();
		mainFrame.getContentPane().add(toolBar, BorderLayout.NORTH);

		mainFrame.getContentPane().add(mainPanel);

		// LANGUAGE CONFIG
		Locale selectedLanguage = ConfigurationManager.getSelectedLanguage();
		if (selectedLanguage != null) {
			LanguageSelection.setLocale(selectedLanguage);
		}

	}

	private void addToolbarButtons() {
		Image scaledImage = null;
		ImageIcon icon = null;
		int iconWidth = 16;
		int iconHeight = 16;

		// COPY
		copyButton = new JButton(new DefaultEditorKit.CopyAction());
		copyButton.setText("Copy");
		copyButton.setToolTipText("Copy");
		copyButton.setFocusable(false);

		icon = new ImageIcon(getClass().getResource("/copy.png"));
		scaledImage = icon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
		copyButton.setIcon(new ImageIcon(scaledImage));
		toolBar.add(copyButton);

		// CUT
		cutButton = new JButton(new DefaultEditorKit.CutAction());
		cutButton.setText("Copy");
		cutButton.setToolTipText("Copy");
		cutButton.setFocusable(false);

		icon = new ImageIcon(getClass().getResource("/cut.png"));
		scaledImage = icon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
		cutButton.setIcon(new ImageIcon(scaledImage));
		toolBar.add(cutButton);

		// PASTE
		pasteButton = new JButton(new DefaultEditorKit.PasteAction());
		pasteButton.setText("Copy");
		pasteButton.setToolTipText("Copy");
		pasteButton.setFocusable(false);

		icon = new ImageIcon(getClass().getResource("/paste.png"));
		scaledImage = icon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
		pasteButton.setIcon(new ImageIcon(scaledImage));
		toolBar.add(pasteButton);

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

		answerKeys.add(answerComboBox.getSelectedItem().toString());

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
			Document document = new Document();
			ListStyle listStyle = new ListStyle(document, ListType.Numbered);
			document.getListStyles().add(listStyle);

			Section sec = document.addSection();
			sec.getPageSetup().setPageSize(PageSize.A4);
			// Get header and footer from a section
			HeaderFooter header = sec.getHeadersFooters().getHeader();
			HeaderFooter footer = sec.getHeadersFooters().getFooter();

			// Add a paragraph to the header
			Paragraph headerParagraph = header.addParagraph();
			// Add text to the header paragraph
			TextRange text = headerParagraph.appendText(topic);
			text.getCharacterFormat().setFontName("Arial");
			text.getCharacterFormat().setFontSize(12);
			text.getCharacterFormat().setItalic(true);
			headerParagraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);

			// Set the bottom border style of the header paragraph
			headerParagraph.getFormat().getBorders().getBottom().setBorderType(BorderStyle.Single);
			headerParagraph.getFormat().getBorders().getBottom().setLineWidth(1f);

			// Add a paragraph to the footer
			Paragraph footerParagraph = footer.addParagraph();
			// Add Field_Page and Field_Num_Pages fields to the footer paragraph
			footerParagraph.appendField("Page Number", FieldType.Field_Page);
//			footerParagraph.appendText(" of ");
//			footerParagraph.appendField("Number of Pages", FieldType.Field_Num_Pages);
			footerParagraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);

			// Set the top border style of the footer paragraph
//			footerParagraph.getFormat().getBorders().getTop().setBorderType(BorderStyle.Single);
//			footerParagraph.getFormat().getBorders().getTop().setLineWidth(1f);

			int questionCount = questions.size();
			int questionIndex = 0;
			int questionColumn = 2;
			int questionRow = 3;
			int pageCount = (int) Math.ceil((double) questionCount / (questionRow * questionColumn));

			// Questions PAGE
			for (int pageIdx = 0; pageIdx < pageCount; pageIdx++) {
				// Add a section
				if (pageIdx != 0) {
					sec = document.addSection();
				}

				// Create a table
				Table table = sec.addTable(false);
				table.getTableFormat().getBorders().getVertical().setBorderType(BorderStyle.Basic_Thin_Lines);
				table.getTableFormat().getPaddings().setRight(10f);

				table.getTableFormat().getBorders().getVertical().setLineWidth(0.5f);
				table.getTableFormat().getPositioning().setVertPosition(60f);
				table.resetCells(questionRow, questionColumn);

				// table.getTableFormat().getPositioning().setVertRelationTo(VerticalRelation.Margin);
				// table.getTableFormat().getPositioning().setVertPositionAbs(VerticalPosition.Bottom);
				// table.getTableFormat().getPositioning().setHorizPositionAbs(HorizontalPosition.Center);
				// table.getTableFormat().getPositioning().setDistanceFromBottom(500f);
				// table.getTableFormat().setCellSpacing(100f);
				// table.getTableFormat().setLayoutType(LayoutType.Fixed);

				// Questions COLUMN
				for (int i = 0; i < questionColumn; i++) {

					// Questions ROW
					for (int j = 0; j < questionRow; j++) {
						if (j == 1 || j == 4) {

							// ____________
							// |j=0 | j=3 |
							// |____|_____|
							// |j=1 | j=4 |
							// |____|_____|
							// |j=2 | j=5 |
							// |____|_____|

							// Skipping this two ^^^^

							continue;
						}

//						int questionIndex = (pageIdx * questionColumn * questionRow) + (i * questionRow) + j;

						if (questionIndex < questionCount) {

							String question = questions.get(questionIndex);
							List<String> choicesForQuestion = choices.get(questionIndex);

							TableRow row = table.getRows().get(j);
							Paragraph paragraph = row.getCells().get(i).addParagraph();

							// Check if the style already exists
							Style style = table.getDocument().getStyles().findByName("titleStyle");
							if (style == null) {
								// Create the style if it doesn't exist
								style = new ParagraphStyle(table.getDocument());
								style.setName("titleStyle");
								// style.getCharacterFormat().setTextColor(Color.BLUE);
								style.getCharacterFormat().setFontName("Arial");
								style.getCharacterFormat().setFontSize(9f);
								table.getDocument().getStyles().add(style);
							}

							paragraph.applyStyle("titleStyle");
							paragraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Justify);
							paragraph.getFormat().setFirstLineIndent(0);
							// Set paragraph after spacing
							paragraph.getFormat().setAfterSpacing(10f);
							// Set line spacing
							paragraph.getFormat().setLineSpacing(15f);

							paragraph.appendText((questionIndex + 1) + ".").getCharacterFormat().setBold(true);
							paragraph.appendBreak(BreakType.Line_Break);

							paragraph.appendText(question);
							paragraph.appendBreak(BreakType.Line_Break);

							// Questions CHOICES
							for (int k = 0; k < choicesForQuestion.size(); k++) {
								paragraph.appendText((char) ('A' + k) + ") ").getCharacterFormat().setBold(true);
								paragraph.appendText(choicesForQuestion.get(k));
								paragraph.appendBreak(BreakType.Line_Break);
							}
							questionIndex++;
						}
					}
				}
			}

			addAnswerKeyPage(document);
			// Save the Word document
			String docxFileName = topic + "_.docx";
			// String pdfFileName = topic + "_.pdf";

			try (FileOutputStream out = new FileOutputStream(docxFileName)) {
				// Save the result document
				document.saveToFile(docxFileName, FileFormat.Docx_2013);
				// document.saveToFile(pdfFileName);

				JOptionPane.showMessageDialog(mainFrame, "Word Document Generated: " + docxFileName, "Info",
						JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mainFrame, "An unexpected error occurred: \n" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addAnswerKeyPage(Document document) throws IOException {
		// Add a section
		Section section = document.addSection();
		section.getPageSetup().setPageSize(PageSize.A4);

//		section.getHeadersFooters().getHeader().getLastChild().close();
//		section.getHeadersFooters().getFooter().getLastChild().close();

		Paragraph paragraph = section.addParagraph();
		paragraph.appendBreak(BreakType.Line_Break);
		paragraph.appendText("Answers");
		paragraph.appendBreak(BreakType.Line_Break);

		// Create a table with one row and as many columns as there are answers
		Table table = section.addTable(true);
		table.resetCells(2, 10);
		table.getTableFormat().setHorizontalAlignment(RowAlignment.Center);

		// Populate the table with answers
		TableRow indexRow = table.getRows().get(0);
		for (int i = 0; i < answerKeys.size(); i++) {
			TableCell indexCell = indexRow.getCells().get(i);
			Paragraph indexParagraph = indexCell.addParagraph();
			indexParagraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
			indexParagraph.getFormat().setTextAlignment(TextAlignment.Center);
			indexParagraph.appendText((i + 1) + "");

		}

		// Populate the inner table with answer keys in the second row
		TableRow answerRow = table.getRows().get(1);
		for (int i = 0; i < answerKeys.size(); i++) {
			TableCell answerCell = answerRow.getCells().get(i);
			Paragraph answerParagraph = answerCell.addParagraph();
			answerParagraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
			answerParagraph.getFormat().setTextAlignment(TextAlignment.Center);
			answerParagraph.appendText(answerKeys.get(i));

		}
	}

	private void generatePdf() {
		try {

			topic = topicField.getText();
			PDDocument document = new PDDocument();

			int questionCount = questions.size();
			int questionColumn = 2;
			int questionRow = 2;
			int pageCount = (int) Math.ceil((double) questionCount / (questionRow * questionColumn));

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
				for (int i = 0; i < questionColumn; i++) {
					float nextXStart = margin + (tableWidth / 2) * i;

					// Questions
					for (int j = 0; j < questionRow; j++) {
						int questionIndex = (pageIdx * questionColumn * questionRow) + (i * questionRow) + j;
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