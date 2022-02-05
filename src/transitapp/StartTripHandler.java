package transitapp;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class StartTripHandler implements EventHandler<ActionEvent> {

	private ComboBox<Card> cardList;
	private ComboBox<Location> lList;
	private TransitGui obj;
	private CardHolder user;
	private Stage stage;

	/**
	 * 
	 * @param stage      The stage that is being changed
	 * @param user       The user that is being acted on
	 * @param cardList   The cardlist that is asociated with the user
	 * @param list       The combo box containing all the possible locations
	 * @param transitGui The main application object
	 */
	public StartTripHandler(Stage stage, CardHolder user, ComboBox<Card> cardList, ComboBox<Location> list,
			TransitGui transitGui) {
		this.cardList = cardList;
		this.lList = list;
		this.obj = transitGui;
		this.user = user;
		this.stage = stage;
	}

	/**
	 * Handles the actions when a client clicks the start trip button
	 */
	@Override
	public void handle(ActionEvent arg0) {
		if (((Button) arg0.getSource()).getText().equals("Start Trip") && this.cardList.getValue() instanceof Card
				&& this.lList.getValue() instanceof Location) {
			Location start = this.lList.getValue();
			try {
				this.obj.continueTrip(this.stage, this.cardList.getValue(), start, this.user);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
