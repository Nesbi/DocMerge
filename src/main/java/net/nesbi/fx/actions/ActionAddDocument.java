package net.nesbi.fx.actions;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import net.nesbi.core.actions.Action;
import net.nesbi.core.actions.ActionAddPages;
import net.nesbi.core.pdf.PDFDocument;
import net.nesbi.fx.gui.JavaFXGUI;

/**
 * Revocable action to add documents in the JavaFX interface
 *
 */
public class ActionAddDocument implements Action {

	private boolean isExecuted = false;
	private JavaFXGUI gui;
	private PDFRenderer renderer;
	private Button documentIcon;
	private List<ToggleButton> pageDisplays;

	private Action addPages;
	private int newDocumentSize;
	private int oldDocumentSize;

	public ActionAddDocument(PDFDocument mainDocument, PDDocument newDocument, JavaFXGUI gui) {
		this.addPages = new ActionAddPages(mainDocument, newDocument);
		this.renderer = new PDFRenderer(newDocument);
		this.newDocumentSize = newDocument.getNumberOfPages();
		this.oldDocumentSize = mainDocument.getNumberOfPages();
		this.gui = gui;
	}

	@Override
	public void execute() {
		if (!isExecuted) {
			addPages.execute();

			if (newDocumentSize > 0) {
				// Document pages
				if (pageDisplays == null) {
					pageDisplays = new LinkedList<ToggleButton>();
					
					for (int pageID = 0; pageID < newDocumentSize; pageID++) {
						try {
							pageDisplays.add(gui.addPageImage(renderer.renderImage(pageID), pageID+oldDocumentSize));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				} else {
					for(ToggleButton page : pageDisplays) {
						gui.addPageImage(page);
					}
				}
				
				// Document icon
				if (documentIcon == null) {
					try {
						documentIcon = gui.addDocumentIcon(renderer.renderImage(0),pageDisplays.get(0));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					gui.addDocumentIcon(documentIcon);
				}
			}
			isExecuted = true;
		}
	}

	@Override
	public void revoke() {
		if (isExecuted) {
			addPages.revoke();

			if (newDocumentSize > 0) {
				// Document icon
				if (documentIcon != null) {
					gui.removeDocumentIcon(documentIcon);
				}

				// Document pages
				if (pageDisplays != null) {
					for (int pageID = oldDocumentSize; pageID < newDocumentSize+oldDocumentSize; pageID++) {
						gui.removePageImage(pageDisplays.get(pageID));
					}
				}
			}
			// Display in View
			isExecuted = false;
		}
	}
}
