package net.nesbi.core.actions;

import net.nesbi.core.pdf.PDFDocument;
import net.nesbi.core.pdf.PDFPage;

/**
 * Revocable action to hide a page in a PDFDocument
 *
 */
public class ActionHidePage implements Action {

	private boolean isExecuted = false;
	private PDFPage page;
	private boolean invert;

	public ActionHidePage(PDFDocument document, int pageIndex) {
		this(document,pageIndex,false);
	}

	public ActionHidePage(PDFDocument document, int pageIndex, boolean invert) {
		this.page = document.getPages().get(pageIndex);
		this.invert = invert;
	}

	@Override
	public void execute() {
		if (!isExecuted) {
			if (invert) {
				page.display();
			} else {
				page.hide();
			}
			isExecuted = true;
		}
	}

	@Override
	public void revoke() {
		if (isExecuted) {
			if (invert) {
				page.hide();
			} else {
				page.display();
			}
			isExecuted = false;
		}
	}
}
