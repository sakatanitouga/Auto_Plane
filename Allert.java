package application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Allert {
	public Allert(String message) {
		Alert dialog = new Alert(AlertType.INFORMATION);
		dialog.setHeaderText(null);
		dialog.setContentText(message);
		dialog.showAndWait();
	}
}
