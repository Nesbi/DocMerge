package net.nesbi.fx.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.application.Platform;
import net.nesbi.core.manager.ActionManager;
import net.nesbi.core.manager.DataManager;
import net.nesbi.core.pdf.PDFDocument;
import net.nesbi.fx.actions.ActionAddDocument;
import net.nesbi.fx.actions.ActionHideFXPage;

/**
 * JavaFX gui for docmerge Displays the pdf documents and handles user input
 *
 */
public class JavaFXGUI extends Application {

	private BorderPane root;
	private PDFDocument document;
	private ActionManager actions;
	private VBox documentSideBar;
	private VBox mainView;
	private ScrollPane mainScrollPane;
	private final FileChooser fileChooser = new FileChooser();
	private File lastOpened;

	private ProgressBar progressBar;
	private int maxProgress;
	private int progress;
	private boolean inProgress = false;

	public static void main(String[] args) throws IOException {
		Application.launch(JavaFXGUI.class);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		document = new PDFDocument();
		actions = new ActionManager();

		// Main View
		primaryStage.setTitle("DocMerge");
		root = new BorderPane();
		Image applicationIcon = new Image(getClass().getResourceAsStream("/icon_128x128.png"));
		primaryStage.getIcons().add(applicationIcon);
		documentSideBar = new VBox();
		documentSideBar.getStyleClass().add("sidebar");
		final ScrollPane sideBarScroll = new ScrollPane();
		sideBarScroll.setContent(documentSideBar);
		root.setLeft(sideBarScroll);
		progressBar = new ProgressBar(0);

		mainView = new VBox();
		mainView.getStyleClass().add("mainView");
		mainScrollPane = new ScrollPane();
		mainScrollPane.setContent(mainView);
		mainScrollPane.setFitToWidth(true);
		root.setCenter(mainScrollPane);

		// Menu
		MenuBar menu = new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuEdit = new Menu("Edit");

		menu.getMenus().addAll(menuFile, menuEdit);

		root.setTop(menu);

		// Load Button
		final MenuItem loadBtn = new MenuItem();
		loadBtn.setText("Add PDF");
		loadBtn.setOnAction(e -> openDocument());
		menuFile.getItems().add(loadBtn);

		// Save Button
		final MenuItem saveBtn = new MenuItem();
		saveBtn.setText("Save");
		saveBtn.setOnAction(e -> saveDocument());
		menuFile.getItems().add(saveBtn);

		// Action Management
		final MenuItem undoBtn = new MenuItem();
		undoBtn.setText("Undo action");
		undoBtn.setOnAction(e -> actions.undo());
		menuEdit.getItems().add(undoBtn);

		final MenuItem redoBtn = new MenuItem();
		redoBtn.setText("Redo action");
		redoBtn.setOnAction(e -> actions.redo());
		menuEdit.getItems().add(redoBtn);

		// Clear
		final MenuItem clearBtn = new MenuItem();
		clearBtn.setText("Clear");
		clearBtn.setOnAction(e -> clear());
		menuEdit.getItems().add(clearBtn);

		// Display everything
		Scene mainScene = new Scene(root, 800, 700);
		mainScene.getStylesheets().add("main.css");
		primaryStage.setScene(mainScene);
		primaryStage.centerOnScreen();
		primaryStage.show();

		// Key Events
		final KeyCombination ctrlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
		final KeyCombination ctrlY = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
		final KeyCombination ctrlS = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
		final KeyCombination ctrlA = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);
		mainScene.setOnKeyPressed(event -> {
			if (ctrlZ.match(event)) {
				actions.undo();
			} else if (ctrlY.match(event)) {
				actions.redo();
			} else if (ctrlS.match(event)) {
				saveDocument();
			} else if (ctrlA.match(event)) {
				openDocument();
			}
		});
		undoBtn.setAccelerator(ctrlZ);
		redoBtn.setAccelerator(ctrlY);
		saveBtn.setAccelerator(ctrlS);
		loadBtn.setAccelerator(ctrlA);
	}

	public Button addDocumentIcon(Button documentIcon) {
		Platform.runLater(() -> {
			documentSideBar.getChildren().add(documentIcon);
		});
		documentIcon.getStyleClass().add("documentIcon");
		return documentIcon;
	}

	public Button addDocumentIcon(BufferedImage bufferedImage, ToggleButton firstImage) {
		ImageView documentIcon = null;
		documentIcon = JavaFXConstructor.createImageView(bufferedImage);
		documentIcon.setFitWidth(100);
		documentIcon.setPreserveRatio(true);
		documentIcon.setSmooth(true);
		documentIcon.setCache(true);

		Button documentButton = JavaFXConstructor.createButtonImage(documentIcon);
		documentButton.getStyleClass().add("documentIcon");
		documentButton.setOnAction(e -> {
			Bounds bounds = mainScrollPane.getViewportBounds();
			mainScrollPane.setVvalue(firstImage.getLayoutY() * (1 / (mainView.getHeight() - bounds.getHeight())));
		});
		Platform.runLater(() -> {
			documentSideBar.getChildren().add(documentButton);
		});
		return documentButton;
	}

	public void removeDocumentIcon(Button documentIcon) {
		Platform.runLater(() -> {
			documentSideBar.getChildren().remove(documentIcon);
		});
	}

	public ToggleButton addPageImage(ToggleButton toggleButton) {
		Platform.runLater(() -> {
			mainView.getChildren().add(toggleButton);
		});
		toggleButton.getStyleClass().add("pageImage");
		return toggleButton;
	}

	public ToggleButton addPageImage(BufferedImage bufferedImage, int pageID) {
		ImageView pageImage = JavaFXConstructor.createImageView(bufferedImage);
		pageImage.setFitWidth(400);
		pageImage.setPreserveRatio(true);
		pageImage.setSmooth(true);
		pageImage.setCache(true);
		ToggleButton pageToggle = JavaFXConstructor.createToggleImage(pageImage);
		pageToggle.setSelected(true);
		pageToggle.getStyleClass().add("pageImage");

		pageToggle.setOnAction(e -> {
			if (!pageToggle.isSelected()) {
				actions.addAndExecute(new ActionHideFXPage(document, pageID, pageToggle));
			} else {
				actions.addAndExecute(new ActionHideFXPage(document, pageID, pageToggle, true));
			}
		});
		Platform.runLater(() -> {
			mainView.getChildren().add(pageToggle);
			addToProgress(1);
		});
		return pageToggle;
	}

	public void removePageImage(ToggleButton documentIcon) {
		Platform.runLater(() -> {
			mainView.getChildren().remove(documentIcon);
		});
	}

	private void loadDocument(File file) throws IOException {
		if (!inProgress) {
			this.inProgress = true;
			documentSideBar.getChildren().add(progressBar);
			progressBar.setProgress(0.0);
			PDDocument pdDocument = DataManager.loadPDF(file);
			setProgressMax(pdDocument.getNumberOfPages() * 3);
			addToProgress(pdDocument.getNumberOfPages());

			final JavaFXGUI gui = this;
			final Task loadingTask = new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					try {
						actions.addAndExecute(new ActionAddDocument(document, pdDocument, gui));
						Platform.runLater(() -> {
							documentSideBar.getChildren().remove(progressBar);
							inProgress = false;
						});
					} catch (Exception e) {
						System.out.println(e.toString());
					}
					return null;
				}
			};
			Thread th = new Thread(loadingTask);
			th.setDaemon(true);
			th.start();
		}
	}

	private void saveDocument() {
		if (!inProgress) {
			fileChooser.setTitle("Save PDF document");
			fileChooser.setInitialFileName("document.pdf");
			File file = fileChooser.showSaveDialog(null);
			if (file != null) {
				try {
					DataManager.savePDF(file, document);
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		}
	}

	private void openDocument() {
		if (!inProgress) {
			fileChooser.setTitle("Add PDF document");
			if (lastOpened == null) {
				fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			} else {
				fileChooser.setInitialDirectory(lastOpened);
			}
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
			fileChooser.setInitialFileName("");
			File file = fileChooser.showOpenDialog(null);
			if (file != null) {
				try {
					loadDocument(file);
					lastOpened = file.getParentFile();
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		}
	}

	public void clear() {
		documentSideBar.getChildren().clear();
		mainView.getChildren().clear();
		document = new PDFDocument();
		actions.clear();
	}

	public void setProgressMax(int max) {
		this.maxProgress = max;
	}

	public void addToProgress(int progress) {
		this.progress += progress;
		this.progress = (this.progress > maxProgress) ? maxProgress : this.progress;
		this.progressBar.setProgress((this.progress * 1.0) / maxProgress);
	}
}
