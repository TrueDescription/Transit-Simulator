package transitapp;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeFareHandler implements EventHandler<ActionEvent> {

	private TextField tf;
	private TransitGui obj;
	private Stage stage;
	private boolean bool;

	/**
	 * 
	 * @param obj   The main GUI that is being used
	 * @param tf    Text field that contains the users input
	 * @param stage Stage that is being worked with
	 * @param b     True when changing bus fare false when changing station fare
	 */
	public ChangeFareHandler(TransitGui obj, TextField tf, Stage stage, boolean b) {
		this.obj = obj;
		this.tf = tf;
		this.stage = stage;
		this.bool = b;
	}

	/**
	 * The handle method that handles the actions once the "Go" button is clicked
	 */
	@Override
	public void handle(ActionEvent arg0) {
		Label l = new Label("Successfully Changed Fare");
		l.setAlignment(Pos.BASELINE_RIGHT);

		if (this.tf.getText().equals("")) {
			this.tf.setText("Please Enter A Valid Input");
		}
		if (this.tf.getText().equals("Please Enter A Valid Input")) {
			this.tf.setText("Please Enter A Valid Input");
		} else if (this.bool == true) {
			AdminUser.setBusTravelCost(Double.parseDouble(this.tf.getText()));
			this.obj.adminUI(this.stage, l);
		} else {
			AdminUser.setStaionPrice((Double.parseDouble(this.tf.getText())));
			this.obj.adminUI(this.stage, l);
		}

		try {
			Writer.writeFare(Double.parseDouble(this.tf.getText()), this.bool);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}