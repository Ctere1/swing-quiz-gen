package quizGenerator.multipleChoice.Helpers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class UpdateHelper {

	public static final String REPO_OWNER = "Ctere1";
	public static final String REPO_NAME = "swing-quiz-gen";
	public static final String CURRENT_VERSION = "v0.2.0";

	public static boolean checkForUpdates() throws Exception {
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

				if (CURRENT_VERSION.compareTo(latestVersion) < 0) {
					return true;
				} else {
					return false;
				}
			}

		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			throw e;
		}
		return false;
	}

}
