package net.nesbi.core.actions;

import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import net.nesbi.core.pdf.PDFDocument;
import net.nesbi.core.pdf.PDFPage;

/**
 * Revocable action for adding pages to a PDFDocument
 *
 */
public class ActionAddPages implements Action{

	private boolean isExecuted = false;
	private List<PDFPage> pages;
	private PDFDocument document;

	public ActionAddPages(PDFDocument document, PDDocument pdDocument) {
		this.document = document;
		this.pages = new LinkedList<PDFPage>();
		for (PDPage pdPage : pdDocument.getPages()) {
			pages.add(new PDFPage(pdDocument, pdPage));
		}
	}
	
	public ActionAddPages(PDFDocument document, List<PDFPage> pages) {
		this.document = document;
		this.pages = pages;
	}
	
	@Override
	public void execute() {
		if (!isExecuted) {
			document.addPages(pages);
			isExecuted = true;
		}
	}

	@Override
	public void revoke() {
		if (isExecuted) {
			document.removePages(pages);
			isExecuted = false;
		}		
	}

}
