package net.nesbi.core.pdf;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * PDF document wrapper, to manage the merge of PDF documents
 *
 */
public class PDFDocument {

	private LinkedList<PDFPage> pages;

	public PDFDocument(PDDocument document) {
		this.pages = new LinkedList<PDFPage>();
		addPages(document);
	}

	public PDFDocument() {
		this.pages = new LinkedList<PDFPage>();
	}

	public void addPages(PDDocument document) {
		for (PDPage pdPage : document.getPages()) {
			pages.addLast(new PDFPage(document, pdPage));
		}
	}

	public void addPages(List<PDFPage> newPages) {
		pages.addAll(newPages);
	}

	public void removePages(Collection<PDFPage> pages) {
		pages.removeAll(pages);
	}

	public void displayPage(int pageIndex) {
		PDFPage page = pages.get(pageIndex);
		page.display();
	}

	public void hidePage(int pageIndex) {
		PDFPage page = pages.get(pageIndex);
		page.hide();
	}

	public void movePage(int pageIndex, int targetPageIndex) {
		PDFPage page = pages.remove(pageIndex);
		pages.add(targetPageIndex, page);
	}

	public LinkedList<PDFPage> getPages() {
		return pages;
	}
}
