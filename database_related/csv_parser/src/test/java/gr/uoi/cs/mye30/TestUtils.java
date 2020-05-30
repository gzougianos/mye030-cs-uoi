package gr.uoi.cs.mye30;

import java.io.File;

public class TestUtils {
	private TestUtils() {
	}

	public static File getResourceFile(String path) {
		if (!path.startsWith("/"))
			path = "/" + path;

		return new File(TestUtils.class.getResource(path).getFile());
	}
}
