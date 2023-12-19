package quizGenerator.multipleChoice;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class LoadingDialog extends JDialog {

	public LoadingDialog(JFrame parent) {
		super(parent, "Loading...", true);
		setSize(400, 250);
		setLocationRelativeTo(parent);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);

		getContentPane().setLayout(new BorderLayout());

		setSize(300, 100);
		setLocationRelativeTo(parent);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		// Ensure the loading screen can't be closed by the user
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Ignore close attempts
			}
		});

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(progressBar, BorderLayout.NORTH);

		getContentPane().add(panel);
		JLabel label = new JLabel("Please wait...");
		int padding = 10; // You can adjust the padding value
		label.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
		panel.add(label, BorderLayout.CENTER);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoadingDialog(new JFrame()).setVisible(true);
			}
		});
	}
}
