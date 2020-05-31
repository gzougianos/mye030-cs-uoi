package gr.uoi.cs.mye30;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.logging.Logger;

import net.lingala.zip4j.ZipFile;

public class CountryCsvFilesFetcher {
	private static final Logger logger = Logger.getGlobal();
	private URL zipFileUrl;
	private File zipFile;
	private File indicatorsMetadataFile;
	private File countryMetadataFile;
	private File yearValuesFile;

	public CountryCsvFilesFetcher(URL zipFileUrl) throws IOException {
		this.zipFileUrl = zipFileUrl;

		zipFile = File.createTempFile("country", ".zip");
		zipFile.deleteOnExit();

		fetchZip();

		unZip();
	}

	public CountryCsvFilesFetcher(String zipFileUrl) throws IOException {
		this(new URL(zipFileUrl));
	}

	private void unZip() throws IOException {
		File extractDir = Files.createTempDirectory("extract").toFile();
		logger.info("Unzipping: " + zipFile.getAbsolutePath() + " to " + extractDir.getAbsolutePath());

		ZipFile zip = new ZipFile(zipFile);
		zip.extractAll(extractDir.getAbsolutePath());

		findEachFile(extractDir);

	}

	private void findEachFile(File extractDir) {
		indicatorsMetadataFile = getFileFromDirectoryMatchingPattern(extractDir, ".*metadata_indicator.*");
		countryMetadataFile = getFileFromDirectoryMatchingPattern(extractDir, ".*metadata_country.*");
		yearValuesFile = getFileFromDirectoryMatchingPattern(extractDir, "^api.*");
	}

	private File getFileFromDirectoryMatchingPattern(File directory, String pattern) {
		return directory.listFiles(f -> f.getName().toLowerCase().matches(pattern))[0];
	}

	private void fetchZip() throws IOException {
		logger.info("Fetching zip file from: " + zipFileUrl.toExternalForm());

		ReadableByteChannel rbc = Channels.newChannel(zipFileUrl.openStream());

		try (FileOutputStream fos = new FileOutputStream(zipFile)) {
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		}

		logger.info("Fetched zip and saved to " + zipFile.getAbsolutePath());
	}

	public File getIndicatorsMetadataFile() {
		return indicatorsMetadataFile;
	}

	public File getCountryMetadataFile() {
		return countryMetadataFile;
	}

	public File getYearValuesFile() {
		return yearValuesFile;
	}

}
