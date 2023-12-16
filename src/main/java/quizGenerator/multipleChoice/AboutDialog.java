package quizGenerator.multipleChoice;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {

	String version = UpdateChecker.CURRENT_VERSION;

	public AboutDialog(JFrame parent) {
		super(parent, "About Quiz Generator", true);
		setSize(400, 250);
		setLocationRelativeTo(parent);

		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/file.png"));

		Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel imageLabel = new JLabel(scaledIcon);

		JLabel label = new JLabel("<html><center><b>Quiz Generator</b><br>Version " + version
				+ "<br>Developed by Cemil Tan</center></html>");
		label.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(imageLabel, BorderLayout.NORTH);
		panel.add(label, BorderLayout.CENTER);

		getContentPane().add(panel);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AboutDialog(new JFrame()).setVisible(true);
			}
		});
	}
}
