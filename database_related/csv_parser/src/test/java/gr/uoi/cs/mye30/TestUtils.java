package gr.uoi.cs.mye30;

import java.io.File;
import java.net.URL;

public class TestUtils {
	private TestUtils() {
	}

	public static File getResourceFile(String path) {
		if (!path.startsWith("/"))
			path = "/" + path;

		return new File(TestUtils.class.getResource(path).getFile());
	}

	public static URL getResourceFileURL(String path) {
		if (!path.startsWith("/"))
			path = "/" + path;

		return TestUtils.class.getResource(path);
	}
}
