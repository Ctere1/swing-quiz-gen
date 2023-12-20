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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import com.formdev.flatlaf.FlatDarkLaf;

import quizGenerator.multipleChoice.Configuration.ConfigurationManager;
import quizGenerator.multipleChoice.Configuration.LanguageSelection;
import quizGenerator.multipleChoice.Dialogs.AboutDialog;
import quizGenerator.multipleChoice.Dialogs.LoadingDialog;
import quizGenerator.multipleChoice.Dialogs.UpdateDialog;
import quizGenerator.multipleChoice.Helpers.ResourceHelper;

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

public class MainWindow {

	private JFrame mainFrame;

	private static JTextPane questionArea;
	private static JScrollPane questionScrollPane;
	private JScrollPane questionAreaScrollPane;
	private static JTextField topicField;
	private static JTextField authorField;
	private List<JTextField> choiceFields;

	private static JButton addQuestionButton;
	private static JButton resetButton;
	private static JButton editButton;
	private static JButton pasteButton;
	private static JButton copyButton;
	private static JButton cutButton;

	private JList<String> questionList;
	private DefaultListModel<String> questionListModel;
	private static JComboBox<?> answerComboBox;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	private JMenuBar menuBar;
	private static JMenu toolsMenu;
	private static JMenu mnHelpMenu;
	private static JMenu mnLanguageMenu;
	private static JMenuItem mntmUpdateMenuItem;
	private static JMenuItem mntmGeneratePDFMenuItem;
	private static JMenuItem mntmGenerateDocxMenuItem;
	private static JMenuItem aboutMenuItem;

	private JToolBar toolBar;
	private JMenuItem mntmLanguageMenuItemTurkish;
	private JMenuItem mntmLanguageMenuItemEnglish;

	private LoadingDialog loadingDialog;

	private PdfDocument pdfDocument;
	private WordDocument wordDocument;
	private Questions questionsClass;
	private LanguageSelection languageSelection;

	private String closeConfirmationDialog = "Are you sure you want to close the application?";

	public String getCloseConfirmationDialog() {
		return closeConfirmationDialog;
	}

	public void setCloseConfirmationDialog(String closeConfirmationDialog) {
		this.closeConfirmationDialog = closeConfirmationDialog;
	}

	public JTextField getTopicField() {
		return topicField;
	}

	public void setTopicField(JTextField topicField) {
		MainWindow.topicField = topicField;
	}

	public JTextField getAuthorField() {
		return authorField;
	}

	public void setAuthorField(JTextField authorField) {
		MainWindow.authorField = authorField;
	}

	public JTextPane getQuestionArea() {
		return questionArea;
	}

	public JComboBox<?> getAnswerComboBox() {
		return answerComboBox;
	}

	public void setAnswerComboBox(JComboBox<?> answerComboBox) {
		MainWindow.answerComboBox = answerComboBox;
	}

	public JButton getResetButton() {
		return resetButton;
	}

	public void setResetButton(JButton resetButton) {
		MainWindow.resetButton = resetButton;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public DefaultListModel<String> getQuestionListModel() {
		return questionListModel;
	}

	public void setQuestionListModel(DefaultListModel<String> questionListModel) {
		this.questionListModel = questionListModel;
	}

	public void setQuestionArea(JTextPane questionArea) {
		MainWindow.questionArea = questionArea;
	}

	public List<JTextField> getChoiceFields() {
		return choiceFields;
	}

	public void setChoiceFields(List<JTextField> choiceFields) {
		this.choiceFields = choiceFields;
	}

	public JButton getAddQuestionButton() {
		return addQuestionButton;
	}

	public void setAddQuestionButton(JButton addQuestionButton) {
		MainWindow.addQuestionButton = addQuestionButton;
	}

	public JButton getEditButton() {
		return editButton;
	}

	public void setEditButton(JButton editButton) {
		MainWindow.editButton = editButton;
	}

	public JButton getPasteButton() {
		return pasteButton;
	}

	public void setPasteButton(JButton pasteButton) {
		MainWindow.pasteButton = pasteButton;
	}

	public JButton getCopyButton() {
		return copyButton;
	}

	public void setCopyButton(JButton copyButton) {
		MainWindow.copyButton = copyButton;
	}

	public JButton getCutButton() {
		return cutButton;
	}

	public void setCutButton(JButton cutButton) {
		MainWindow.cutButton = cutButton;
	}

	public JScrollPane getQuestionScrollPane() {
		return questionScrollPane;
	}

	public void setQuestionScrollPane(JScrollPane questionScrollPane) {
		MainWindow.questionScrollPane = questionScrollPane;
	}

	public JMenu getToolsMenu() {
		return toolsMenu;
	}

	public void setToolsMenu(JMenu toolsMenu) {
		MainWindow.toolsMenu = toolsMenu;
	}

	public JMenu getMnHelpMenu() {
		return mnHelpMenu;
	}

	public void setMnHelpMenu(JMenu mnHelpMenu) {
		MainWindow.mnHelpMenu = mnHelpMenu;
	}

	public JMenu getMnLanguageMenu() {
		return mnLanguageMenu;
	}

	public void setMnLanguageMenu(JMenu mnLanguageMenu) {
		MainWindow.mnLanguageMenu = mnLanguageMenu;
	}

	public JMenuItem getMntmUpdateMenuItem() {
		return mntmUpdateMenuItem;
	}

	public void setMntmUpdateMenuItem(JMenuItem mntmUpdateMenuItem) {
		MainWindow.mntmUpdateMenuItem = mntmUpdateMenuItem;
	}

	public JMenuItem getMntmGeneratePDFMenuItem() {
		return mntmGeneratePDFMenuItem;
	}

	public void setMntmGeneratePDFMenuItem(JMenuItem mntmGeneratePDFMenuItem) {
		MainWindow.mntmGeneratePDFMenuItem = mntmGeneratePDFMenuItem;
	}

	public JMenuItem getMntmGenerateDocxMenuItem() {
		return mntmGenerateDocxMenuItem;
	}

	public void setMntmGenerateDocxMenuItem(JMenuItem mntmGenerateDocxMenuItem) {
		MainWindow.mntmGenerateDocxMenuItem = mntmGenerateDocxMenuItem;
	}

	public JMenuItem getAboutMenuItem() {
		return aboutMenuItem;
	}

	public void setAboutMenuItem(JMenuItem aboutMenuItem) {
		MainWindow.aboutMenuItem = aboutMenuItem;
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initializeClasses();
		initializeComponents();
	}

	private void initializeClasses() {
		questionsClass = new Questions(this);
		wordDocument = new WordDocument(this, questionsClass);
		pdfDocument = new PdfDocument(this, questionsClass);
		languageSelection = new LanguageSelection(this);
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

		// MAIN FRAME
		mainFrame = new JFrame("Quiz Generator");
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.setSize(400, 300);
		mainFrame.setSize(1000, 600);
		mainFrame.setMinimumSize(new Dimension(800, 500));
		mainFrame.setLocationRelativeTo(null);
		mainFrame.getRootPane().putClientProperty("JRootPane.titleBarShowIcon", true);
		mainFrame.getRootPane().putClientProperty("JRootPane.titleBarBackground", Color.DARK_GRAY);

		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				onCloseButtonClick();
			}
		});

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
				questionsClass.addQuestion();
			}
		});

		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = questionList.getSelectedIndex();
				if (selectedIndex != -1) {
					questionsClass.editQuestion(selectedIndex);
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
				questionsClass.resetQuestions();
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
						wordDocument.generate();
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
						pdfDocument.generate();
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

				languageSelection.setLocale(Locale.forLanguageTag("en"));
			}
		});

		mntmLanguageMenuItemTurkish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				languageSelection.setLocale(Locale.forLanguageTag("tr"));
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
						new UpdateDialog(mainFrame);
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
					URI uri = new URI("https://github.com/Ctere1/swing-quiz-gen");
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
			languageSelection.setLocale(selectedLanguage);
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

	private void onCloseButtonClick() {
		// Show a confirmation dialog
		int response = JOptionPane.showConfirmDialog(mainFrame, closeConfirmationDialog, "Confirmation",
				JOptionPane.OK_CANCEL_OPTION);

		// If the user clicks OK, dispose of the main frame (close the application)
		if (response == JOptionPane.OK_OPTION) {
			mainFrame.dispose();
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