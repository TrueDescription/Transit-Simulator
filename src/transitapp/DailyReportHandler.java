package transitapp;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DailyReportHandler implements EventHandler<ActionEvent> {

	private TextField tf;
	private TransitGui transitGui;
	private Stage stage;

	/**
	 * Constructor for the report buttons
	 * 
	 * @param tf         Text Field that contains input from the user
	 * @param transitGui The main application object
	 * @param stage      The stage to be changed
	 */
	public DailyReportHandler(TextField tf, TransitGui transitGui, Stage stage) {
		this.tf = tf;
		this.transitGui = transitGui;
		this.stage = stage;
	}

	/**
	 * handles the users input and accesses the appropriate daily report
	 */
	@Override
	public void handle(ActionEvent arg0) {
		if (this.tf.getText().equals("")) {
			this.tf.setText("Please enter a valid Input");
		} else if (this.tf.getText().equals("Please enter a valid Input")) {
			this.tf.setText("Please enter a valid Input");
		} else {
			
			try {
				String[] data = tf.getText().split("-");			
				LocalDate ld = LocalDate.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]),
						Integer.parseInt(data[2]));
				this.transitGui.showDailyReport(this.stage, ld);
			} catch (Exception e) {
				this.tf.setText("Invalid Date Format. Please Enter A Valid Date");
			}
		}
	}

}