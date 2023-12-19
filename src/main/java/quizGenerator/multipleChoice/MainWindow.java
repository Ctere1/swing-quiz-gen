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
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.JMenuItem;

import java.awt.SystemColor;
import javax.swing.border.EtchedBorder;

import com.spire.doc.AutoFitBehaviorType;
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
import com.spire.doc.documents.PageSize;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.ParagraphStyle;
import com.spire.doc.documents.RowAlignment;
import com.spire.doc.documents.Style;
import com.spire.doc.documents.TableRowHeightType;
import com.spire.doc.documents.TextAlignment;
import com.spire.doc.fields.Field;
import com.spire.doc.fields.TextRange;

public class MainWindow {

	private JFrame mainFrame;
	private String fontFilePathArimo = "Arimo-Regular.ttf";
	private String fontFilePathArimoBold = "Arimo-Bold.ttf";

	protected static JTextPane questionArea;
	protected static JScrollPane questionScrollPane;
	private JScrollPane questionAreaScrollPane;
	protected static JTextField topicField;
	protected static JTextField authorField;
	private List<JTextField> choiceFields;
	protected static JButton addQuestionButton;
	protected static JButton resetButton;
	protected static JButton editButton;
	protected static JButton pasteButton;
	protected static JButton copyButton;
	protected static JButton cutButton;
	private String topic;
	private String author;
	protected static String answersString = "Answers";

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

	private LoadingDialog loadingDialog;

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
				// Show loading screen
				SwingUtilities.invokeLater(() -> showLoadingScreen());

				// Use SwingWorker to run the document generation in the background
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						generateWordDocument();
						return null;
					}

					@Override
					protected void done() {
						// Hide loading screen when the document generation is complete
						SwingUtilities.invokeLater(() -> hideLoadingScreen());
					}
				};

				worker.execute();
			}
		});

		mntmGeneratePDFMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Show loading screen
				SwingUtilities.invokeLater(() -> showLoadingScreen());

				// Use SwingWorker to run the document generation in the background
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						generatePdf();
						return null;
					}

					@Override
					protected void done() {
						// Hide loading screen when the document generation is complete
						SwingUtilities.invokeLater(() -> hideLoadingScreen());
					}
				};
				worker.execute();
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
				// Show loading screen
				SwingUtilities.invokeLater(() -> showLoadingScreen());

				// Use SwingWorker to run the document generation in the background
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						performUpdateCheck();
						return null;
					}

					@Override
					protected void done() {
						// Hide loading screen when the document generation is complete
						SwingUtilities.invokeLater(() -> hideLoadingScreen());
					}
				};
				worker.execute();
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

		// AUTHOR
		authorField = new JTextField(14);
		sl_middlePanel.putConstraint(SpringLayout.NORTH, authorField, 0, SpringLayout.NORTH, topicField);
		sl_middlePanel.putConstraint(SpringLayout.WEST, authorField, 0, SpringLayout.WEST, answerComboBox);
		authorField.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Author",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 200, 0)));
		middlePanel.add(authorField);

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

	private void showLoadingScreen() {
		loadingDialog = new LoadingDialog(mainFrame);
		loadingDialog.setVisible(true);

	}

	private void hideLoadingScreen() {
		if (loadingDialog != null) {
			loadingDialog.dispose();
			loadingDialog.setVisible(false);
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
			author = authorField.getText();
			Document document = new Document();

			Section sec = document.addSection();
			sec.getPageSetup().setPageSize(PageSize.A4);
			sec.getPageSetup().getMargins().setTop(56.75f); // 2cm
			sec.getPageSetup().getMargins().setBottom(56.75f);// 2cm
			sec.getPageSetup().getMargins().setLeft(71f); // 2.5cm
			sec.getPageSetup().getMargins().setRight(56.75f);// 2cm
			// Get header and footer from a section
			HeaderFooter header = sec.getHeadersFooters().getHeader();
			HeaderFooter footer = sec.getHeadersFooters().getFooter();

			// Add a Header
			Table headerTable1 = header.addTable(false);
			headerTable1.resetCells(1, 2);
			headerTable1.getTableFormat().getBorders().getRight().setBorderType(BorderStyle.Outset);
			headerTable1.getTableFormat().getBorders().getLeft().setBorderType(BorderStyle.Outset);
			headerTable1.getTableFormat().getBorders().getTop().setBorderType(BorderStyle.Outset);
			headerTable1.getTableFormat().getBorders().getHorizontal().setBorderType(BorderStyle.Outset);

			headerTable1.getTableFormat().getBorders().getRight().setLineWidth(0.2f); // 1/4 pt
			headerTable1.getTableFormat().getBorders().getLeft().setLineWidth(0.2f); // 1/4 pt
			headerTable1.getTableFormat().getBorders().getTop().setLineWidth(0.2f); // 1/4 pt
			headerTable1.getTableFormat().getBorders().getHorizontal().setLineWidth(0.2f); // 1/4 pt

			headerTable1.getTableFormat().getPaddings().setAll(6f);

			Table headerTable2 = header.addTable(false);
			headerTable2.resetCells(1, 2);

			TableRow headerRow1 = headerTable1.getRows().get(0);
			TableRow headerRow2 = headerTable2.getRows().get(0);

			Paragraph headerParagraph = headerRow1.getCells().get(0).addParagraph();
			// Add text to the header paragraph
			TextRange text = headerParagraph.appendText(topic);
			text.getCharacterFormat().setFontName("TimesNewRoman");
			text.getCharacterFormat().setFontSize(11);

			headerRow2.setHeight(5.75f); // 0,2 cm -->0.75f == 0,02 cm
			headerRow2.setHeightType(TableRowHeightType.Exactly);
			headerRow2.getCells().get(0).getCellFormat().setBackColor(new Color(220, 220, 220));
			headerRow2.getCells().get(1).getCellFormat().setBackColor(new Color(220, 220, 220));
			// Add a break line after the table (addParagraph() will add break)
			Paragraph breakLineParagraph = header.addParagraph();

			// Add a Footer
			Table footerTable1 = footer.addTable(false);
			footerTable1.resetCells(1, 2);

			Table footerTable2 = footer.addTable(false);
			footerTable2.resetCells(1, 2);
			footerTable2.getTableFormat().getPaddings().setAll(6f);

			TableRow footerRow1 = footerTable1.getRows().get(0);
			TableRow footerRow2 = footerTable2.getRows().get(0);

			Paragraph footerParagraph1 = footerRow2.getCells().get(1).addParagraph();
			Paragraph footerParagraph2 = footerRow2.getCells().get(0).addParagraph();
			// Add Field_Page and Field_Num_Pages fields to the footer paragraph
			Field pageField = footerParagraph1.appendField("Page Number", FieldType.Field_Page);
//			footerParagraph.appendText(" of ");
//			footerParagraph.appendField("Number of Pages", FieldType.Field_Num_Pages);
			pageField.getCharacterFormat().setFontName("TimesNewRoman");
			pageField.getCharacterFormat().setFontSize(9);
			footerParagraph1.getFormat().setHorizontalAlignment(HorizontalAlignment.Right);

			footerRow1.setHeight(5.75f); // 0,2 cm -->0.75f == 0,02 cm
			footerRow1.setHeightType(TableRowHeightType.Exactly);
			footerRow1.getCells().get(0).getCellFormat().setBackColor(new Color(220, 220, 220));
			footerRow1.getCells().get(1).getCellFormat().setBackColor(new Color(220, 220, 220));

			// Add text to the footer paragraph
			footerParagraph2.getFormat().setHorizontalAlignment(HorizontalAlignment.Left);
			TextRange footerText = footerParagraph2.appendText(author);
			footerText.getCharacterFormat().setFontName("TimesNewRoman");
			footerText.getCharacterFormat().setAllCaps(true);
			footerText.getCharacterFormat().setTextColor(new Color(130, 130, 130));
			footerText.getCharacterFormat().setFontSize(9);

			// Adjust the header distance
			sec.getPageSetup().setHeaderDistance(35.5f); // 1,25 cm
			// Adjust the footer distance
			sec.getPageSetup().setFooterDistance(35.5f); // 1,25 cm

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
					// Adjust the header distance
					sec.getPageSetup().setHeaderDistance(35.5f); // 1,25 cm
					// Adjust the footer distance
					sec.getPageSetup().setFooterDistance(35.5f); // 1,25 cm
				}

				// Create a table
				Table table = sec.addTable(false);
				table.getTableFormat().getBorders().getVertical().setBorderType(BorderStyle.Outset);
				table.getTableFormat().getBorders().getVertical().setLineWidth(0.2f); // It doesn't affect !!
				table.resetCells(questionRow, questionColumn);

				// Questions COLUMN
				for (int i = 0; i < questionColumn; i++) {
					// Questions ROW
					for (int j = 0; j < questionRow; j++) {
						if (questionIndex < questionCount) {

							Table nestedTable = table.get(j, i).addTable();
							nestedTable.resetCells(1, 2);
							nestedTable.autoFit(AutoFitBehaviorType.Auto_Fit_To_Contents);

							String question = questions.get(questionIndex);
							List<String> choicesForQuestion = choices.get(questionIndex);

							TableRow row = nestedTable.getRows().get(0);
							Paragraph questionNumberParagraph = row.getCells().get(0).addParagraph();
							Paragraph questionTextParagraph = row.getCells().get(1).addParagraph();

							// Check if the style already exists
							Style style = nestedTable.getDocument().getStyles().findByName("questionsStyle");
							if (style == null) {
								// Create the style if it doesn't exist
								style = new ParagraphStyle(nestedTable.getDocument());
								style.setName("questionsStyle");
								style.getCharacterFormat().setFontName("TimesNewRoman");
								style.getCharacterFormat().setFontSize(9f);
								nestedTable.getDocument().getStyles().add(style);
							}

							questionTextParagraph.applyStyle("questionsStyle");
							questionNumberParagraph.applyStyle("questionsStyle");

							questionNumberParagraph.getFormat().setFirstLineIndent(0);
							questionNumberParagraph.getFormat().setLeftIndent(-5.7f); // 0,2cm
							questionNumberParagraph.appendText((questionIndex + 1) + ".").getCharacterFormat()
									.setBold(true);

							questionTextParagraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Justify);
							questionTextParagraph.getFormat().setLeftIndent(-5.7f); // 0,2cm
							questionTextParagraph.getFormat().setRightIndent(-5.7f); // - 0,2cm
							questionTextParagraph.getFormat().setFirstLineIndent(0);
							// Set paragraph after spacing
							questionTextParagraph.getFormat().setAfterSpacing(6f); // 6pt
							// Set line spacing
							questionTextParagraph.getFormat().setLineSpacing(13.8f); // 1,15
							questionTextParagraph.appendText(question);

							Paragraph questionChoiceParagraph = row.getCells().get(1).addParagraph();
							// Questions CHOICES
							for (int k = 0; k < choicesForQuestion.size(); k++) {

								questionChoiceParagraph.applyStyle("questionsStyle");
								questionChoiceParagraph.getFormat().setLineSpacing(13.8f); // 1,15
								questionChoiceParagraph.getFormat().setAfterSpacing(6f); // 6pt
								questionChoiceParagraph.getFormat().setLeftIndent(-5.7f); // -0,2cm

								questionChoiceParagraph.appendText((char) ('A' + k) + ") ");
								questionChoiceParagraph.appendText(choicesForQuestion.get(k));
								questionChoiceParagraph = row.getCells().get(1).addParagraph();

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
		section.getPageSetup().setHeaderDistance(35.5f); // 1,25 cm
		section.getPageSetup().setFooterDistance(35.5f); // 1,25 cm
		section.getPageSetup().setPageSize(PageSize.A4);
		section.getPageSetup().getMargins().setTop(56.75f); // 2cm
		section.getPageSetup().getMargins().setBottom(56.75f);// 2cm
		section.getPageSetup().getMargins().setLeft(71f); // 2.5cm
		section.getPageSetup().getMargins().setRight(56.75f);// 2cm

		Paragraph paragraph = section.addParagraph();
		Style styleTitle = paragraph.getDocument().getStyles().findByName("answersTitleStyle");
		if (styleTitle == null) {
			styleTitle = new ParagraphStyle(paragraph.getDocument());
			styleTitle.setName("answersTitleStyle");
			styleTitle.getCharacterFormat().setFontName("TimesNewRoman");
			styleTitle.getCharacterFormat().setFontSize(9f);
			styleTitle.getCharacterFormat().setBold(true);
			styleTitle.getCharacterFormat().setAllCaps(true);
			styleTitle.getDocument().getStyles().add(styleTitle);
		}
		paragraph.appendBreak(BreakType.Line_Break);
		paragraph.appendText(answersString).getCharacterFormat().setBold(true);
		paragraph.getFormat().setLineSpacing(13.75f); // 1,15
		paragraph.getFormat().setAfterSpacing(6f); // 6pt
		paragraph.appendBreak(BreakType.Line_Break);
		paragraph.applyStyle("answersTitleStyle");

		// Create a table with one row and as many columns as there are answers
		Table table = section.addTable(true);
		int numColumns = 10;
		int numRows = (int) Math.ceil((double) answerKeys.size() / numColumns) * 2;
		table.resetCells(numRows, numColumns);
		table.getTableFormat().setHorizontalAlignment(RowAlignment.Center);

		Style style = table.getDocument().getStyles().findByName("AnswersStyle");
		if (style == null) {
			style = new ParagraphStyle(table.getDocument());
			style.setName("AnswersStyle");
			style.getCharacterFormat().setFontName("TimesNewRoman");
			style.getCharacterFormat().setFontSize(9f);
			style.getCharacterFormat().setBold(true);
			table.getDocument().getStyles().add(style);
		}

		int j = 1;
		// Populate the table with answers
		for (int k = 0; k < numRows; k += 2) {
			TableRow indexRow = table.getRows().get(k);
			for (int i = 0; i < numColumns; i++) {
				// Set Background Color
				indexRow.getCells().get(i).getCellFormat().setBackColor(new Color(220, 220, 220));

				TableCell indexCell = indexRow.getCells().get(i);
				Paragraph indexParagraph = indexCell.addParagraph();
				indexParagraph.applyStyle("AnswersStyle");
				indexParagraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
				indexParagraph.getFormat().setTextAlignment(TextAlignment.Center);
				indexParagraph.getFormat().setBeforeSpacing(4);
				indexParagraph.getFormat().setAfterSpacing(4);
				indexParagraph.appendText((j) + "");
				;
				j++;
			}
		}

		j = 0;
		// Populate the inner table with answer keys
		for (int k = 1; k < numRows; k += 2) {
			TableRow answerRow = table.getRows().get(k);
			for (int i = 0; i < numColumns; i++) {
				if (j < answerKeys.size()) {
					TableCell answerCell = answerRow.getCells().get(i);
					Paragraph answerParagraph = answerCell.addParagraph();
					answerParagraph.applyStyle("AnswersStyle");
					answerParagraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
					answerParagraph.getFormat().setTextAlignment(TextAlignment.Center);
					answerParagraph.getFormat().setBeforeSpacing(4);
					answerParagraph.getFormat().setAfterSpacing(4);
					answerParagraph.appendText(answerKeys.get(j)).getCharacterFormat().setBold(false);
					j++;
				}
			}
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