package transitapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AdminFunctionsHandler implements EventHandler<ActionEvent> {

	ComboBox value = null;
	private TransitGui obj;
	private Stage stage;
	private Button button;
	static String action = null;

	/**
	 * Constructor for function handler
	 * 
	 * @param actionList combo box being acted on
	 */
	public AdminFunctionsHandler(ComboBox actionList) {
		this.value = actionList;
	}

	/**
	 * Constructor for admin functions
	 * 
	 * @param obj   the main application object
	 * @param stage Stage being acted on
	 */
	public AdminFunctionsHandler(TransitGui obj, Stage stage) {
		this.obj = obj;
		this.stage = stage;
	}

	/**
	 * The method performs action based on the button clicked
	 */
	public void handle(ActionEvent arg0) {
		if (arg0.getSource() instanceof ComboBox) {
			AdminFunctionsHandler.action = (String) this.value.getValue();
		}
		if (arg0.getSource() instanceof Button) {

			if (AdminFunctionsHandler.action == "Get Daily Report") {
				this.obj.showDR(this.stage);
			}

			if (AdminFunctionsHandler.action == "Set Fair For Bus Routes") {
				this.obj.showSetBusFair(this.stage);
			}

			if (AdminFunctionsHandler.action == "Set Fair For Stations") {
				this.obj.showSetStation(this.stage);
			}

		}
	}

}
