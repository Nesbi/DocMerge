package net.nesbi.fx.gui;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Button;

/**
 * Constructor class for JavaFX elements
 *
 */
public class JavaFXConstructor {
	
	public static ImageView createImageView(BufferedImage bufferedImage) {
		Image image = null;
		image = SwingFXUtils.toFXImage(bufferedImage, null);
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		
		return imageView;
	}
	
	public static ToggleButton createToggleImage(ImageView imageView) {
		ToggleButton imageToggle = new ToggleButton();
		imageToggle.setGraphic(imageView);
		
		return imageToggle;
	}
	
	public static Button createButtonImage(ImageView imageView) {
		Button imageToggle = new Button();
		imageToggle.setGraphic(imageView);
		
		return imageToggle;
	}
}
