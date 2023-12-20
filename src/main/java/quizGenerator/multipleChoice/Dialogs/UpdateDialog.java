package quizGenerator.multipleChoice.Dialogs;

import java.awt.Desktop;
import java.net.URI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import quizGenerator.multipleChoice.Helpers.UpdateHelper;

public class UpdateDialog extends JFrame {
	private JLabel resultLabel;

	public UpdateDialog(JFrame parent) {
		resultLabel = new JLabel();
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

		try {
			if (UpdateHelper.checkForUpdates()) {
				int choice = JOptionPane.showConfirmDialog(parent, "A new version is available. Do you want to update?",
						"Update Available", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					try {
						URI uri = new URI("https://github.com/" + UpdateHelper.REPO_OWNER + "/" + UpdateHelper.REPO_NAME
								+ "/releases");
						Desktop.getDesktop().browse(uri);
						resultLabel.setText("Visit the GitHub Releases page for updates.");
					} catch (Exception e) {
						e.printStackTrace();
						resultLabel.setText("Error checking for updates.");
					}
				}
			} else {

				resultLabel.setText(
						"<html>No updates available.<br>Current version: " + UpdateHelper.CURRENT_VERSION + "</html>");
				JOptionPane.showMessageDialog(parent, resultLabel, "Update Check", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error checking for updates.", "Update Check",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new UpdateDialog(new JFrame()).setVisible(true);
			}
		});
	}
}
