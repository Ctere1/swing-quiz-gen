package quizGenerator.multipleChoice;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String REPO_OWNER = "Ctere1";
	private static final String REPO_NAME = "swing-quiz-gen";
	protected static final String CURRENT_VERSION = "0.1.0";
	private JLabel resultLabel;

	public UpdateChecker() {
		resultLabel = new JLabel();
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(resultLabel);
	}

	public void checkForUpdates(Component parentComponent) {
		try {
			URI uri = new URI("https://api.github.com/repos/" + REPO_OWNER + "/" + REPO_NAME + "/releases");
			URL url = uri.toURL();
			URLConnection connection = url.openConnection();
			InputStream inputStream = connection.getInputStream();

			StringBuilder response = new StringBuilder();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
			}

			JSONArray releases = new JSONArray(response.toString());

			if (releases.length() > 0) {
				JSONObject latestRelease = releases.getJSONObject(0);
				String latestVersion = latestRelease.getString("tag_name");

				if (isNewVersionAvailable(CURRENT_VERSION, latestVersion)) {
					promptForUpdate(parentComponent);
				} else {
					JLabel messageLabel = new JLabel(
							"<html>No updates available.<br>Current version: " + CURRENT_VERSION + "</html>");
					// messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
					JOptionPane.showMessageDialog(parentComponent, messageLabel, "Update Check",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}

		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error checking for updates.", "Update Check",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean isNewVersionAvailable(String currentVersion, String latestVersion) {

		return currentVersion.compareTo(latestVersion) < 0;
	}

	private void promptForUpdate(Component parentComponent) {
		int choice = JOptionPane.showConfirmDialog(parentComponent,
				"A new version is available. Do you want to update?", "Update Available", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			try {
				URI uri = new URI("https://github.com/" + REPO_OWNER + "/" + REPO_NAME + "/releases");
				Desktop.getDesktop().browse(uri);
				resultLabel.setText("Visit the GitHub Releases page for updates.");
			} catch (Exception e) {
				e.printStackTrace();
				resultLabel.setText("Error checking for updates.");
			}

		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new UpdateChecker().setVisible(true);
			}
		});
	}
}
