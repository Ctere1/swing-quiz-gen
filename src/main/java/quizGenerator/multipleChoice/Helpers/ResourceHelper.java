package quizGenerator.multipleChoice.Helpers;

import java.io.IOException;
import java.io.InputStream;

public class ResourceHelper {

	public static InputStream getResourceAsStream(String resourcePath) throws IOException {

		ClassLoader classLoader = ResourceHelper.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(resourcePath);

		if (inputStream == null) {
			throw new IOException("Resource not found: " + resourcePath);
		}

		return inputStream;
	}
}