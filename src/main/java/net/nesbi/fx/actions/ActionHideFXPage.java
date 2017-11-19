package net.nesbi.fx.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import net.nesbi.core.actions.Action;
import net.nesbi.core.actions.ActionHidePage;
import net.nesbi.core.pdf.PDFDocument;

/**
 * Revocable action for hiding document pages in the JavaFX interface
 *
 */
public class ActionHideFXPage implements Action {

	private boolean isExecuted = false;
	private ActionHidePage hidePage;
	private ToggleButton pageToggle;

	public ActionHideFXPage(PDFDocument document, int pageIndex, ToggleButton pageToggle) {
		this.hidePage = new ActionHidePage(document, pageIndex);
		this.pageToggle = pageToggle;
	}

	@Override
	public void execute() {
		if (!isExecuted) {
			hidePage.execute();

			// Prevent that the event fires twice
			EventHandler<ActionEvent> event = pageToggle.getOnAction();
			pageToggle.setOnAction(null);
			pageToggle.setSelected(false);

			pageToggle.setOnAction(event);
			
			isExecuted = true;
		}
	}

	@Override
	public void revoke() {
		if (isExecuted) {
			hidePage.revoke();
			
			// Prevent that the event fires twice
			EventHandler<ActionEvent> event = pageToggle.getOnAction();
			pageToggle.setOnAction(null);
			pageToggle.setSelected(true);

			pageToggle.setOnAction(event);
			isExecuted = false;
		}
	}
}
