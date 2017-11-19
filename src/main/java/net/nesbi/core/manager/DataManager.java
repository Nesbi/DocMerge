package net.nesbi.core.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import net.nesbi.core.pdf.PDFDocument;
import net.nesbi.core.pdf.PDFPage;

/**
 * Load and save pdf documents
 *
 */
public class DataManager {

	public static PDDocument loadPDF(File file) throws InvalidPasswordException, IOException {
		if (!file.exists()) {
			throw new FileNotFoundException(
					"Unable to load file \'" + file.getName() + "\' [\'" + file.getAbsolutePath() + "\']");
		}

		PDDocument document = PDDocument.load(file);

		// Check for encryption
		if (document.isEncrypted()) {
			throw new IllegalArgumentException(
					"PDF document \'" + file.getName() + "\' can't be loaded. (Cause: Encrypted document)");
		}

		return document;
	}

	public static void savePDF(File file, PDDocument document) throws IOException {
		document.save(file.getAbsolutePath());
	}

	public static void savePDF(File file, PDFDocument document) throws IOException {
		PDDocument pdDocument = new PDDocument();
		for (PDFPage page : document.getPages()) {
			if (page.isDisplayed()) {
				pdDocument.addPage(page.getPage());
			}
		}
		pdDocument.save(file.getAbsolutePath());
	}
}
