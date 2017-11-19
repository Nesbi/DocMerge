package net.nesbi.core.actions;

import net.nesbi.core.pdf.PDFDocument;

/**
 * Revocable action for moving pages inside of a PDFDocument
 *
 */
public class ActionMovePage implements Action{

	private boolean isExecuted = false;
	private PDFDocument document;
	private int newIndex, oldIndex;

	public ActionMovePage(PDFDocument document, int newIndex, int oldIndex) {
		this.document = document;
		this.newIndex = newIndex;
		this.oldIndex = oldIndex;
	}
	
	@Override
	public void execute() {
		if (!isExecuted) {
			document.movePage(oldIndex, newIndex);
			isExecuted = true;
		}
	}

	@Override
	public void revoke() {
		if (isExecuted) {
			document.movePage(newIndex, oldIndex);
			isExecuted = false;
		}		
	}

}
