package net.nesbi.core.actions;

import net.nesbi.core.pdf.PDFDocument;
import net.nesbi.core.pdf.PDFPage;

/**
 * Revocable action to hide a page in a PDFDocument
 *
 */
public class ActionHidePage implements Action{

	private boolean isExecuted = false;
	private PDFPage page;

	public ActionHidePage(PDFDocument document, int pageIndex) {
		this.page = document.getPages().get(pageIndex);
	}
	
	@Override
	public void execute() {
		if (!isExecuted) {
			page.hide();
			isExecuted = true;
		}
	}

	@Override
	public void revoke() {
		if (isExecuted) {
			page.display();
			isExecuted = false;
		}		
	}
}
