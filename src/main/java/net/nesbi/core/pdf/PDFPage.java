package net.nesbi.core.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * Page wrapper for PDF document pages
 *
 */
public class PDFPage {

	private boolean isDisplayed;
	private PDDocument document;
	private PDPage page;
	
	public PDFPage(PDDocument document, PDPage page) {
		this.document = document;
		this.page = page;
		this.isDisplayed = true;
	}

	public PDFPage(PDDocument document, int pageIndex) {
		this(document, document.getPage(pageIndex));
		if (page == null) {
			throw new IllegalArgumentException("Page " + pageIndex + " could not be found.");
		}
	}

	public PDDocument getDocument() {
		return document;
	}

	public PDPage getPage() {
		return page;
	}

	public boolean isDisplayed() {
		return isDisplayed;
	}

	public void display() {
		this.isDisplayed = true;
	}

	public void hide() {
		this.isDisplayed = false;
	}
}
