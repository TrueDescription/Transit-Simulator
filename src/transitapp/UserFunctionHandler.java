package transitapp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserFunctionHandler implements EventHandler<ActionEvent> {

	private CardHolder user;
	private TransitGui obj;
	private Stage stage;
	private HashMap<String, Station> stations;
	private HashMap<String, Stop> stops;
	private ComboBox<Card> list;
	private TextField cNameTxt;
	private static Card value = null;

	/**
	 * Constructor for the user fucntions
	 * 
	 * @param user       The user being acted on
	 * @param transitGui The main application GUI obj
	 * @param stage      The stage being changed
	 * @param stops      The HashMap of stops that exist key is the name of the stop
	 *                   and value is the stop object
	 * @param stations
	 */
	public UserFunctionHandler(CardHolder user, TransitGui transitGui, Stage stage, HashMap<String, Stop> stops,
			HashMap<String, Station> stations) {
		this.user = user;
		this.obj = transitGui;
		this.stage = stage;
		this.stops = stops;
		this.stations = stations;
	}

	/**
	 * Constructor for the user functions
	 * 
	 * @param cardListSus List of cards associated with the current user
	 */
	public UserFunctionHandler(ComboBox<Card> cardListSus) {
		this.list = cardListSus;
	}

	/**
	 * Constructor for the User functions
	 * 
	 * @param cNameTxt
	 * @param user       The user being acted on
	 * @param transitGui The main application GUI object
	 * @param stage      The stage that is being changed
	 * @param stops      The Hashmap of all stops that exist
	 * @param stations   The Hashmap of all stations that exist
	 */
	public UserFunctionHandler(TextField cNameTxt, CardHolder user, TransitGui transitGui, Stage stage,
			HashMap<String, Stop> stops, HashMap<String, Station> stations) {
		this.cNameTxt = cNameTxt;
		this.user = user;
		this.obj = transitGui;
		this.stage = stage;
		this.stops = stops;
		this.stations = stations;
	}

	/**
	 * The handler method that takes care of actions of user functions
	 */
	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() instanceof Button) {
			String source = ((Button) event.getSource()).getText();
			if (source.equals("User Functions")) {
				try {
					this.obj.userFunctionsUI(this.stage, this.user, this.stops, this.stations);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			if (source.equals("Begin a Trip")) {
				try {
					this.obj.UserUIAfter(this.stage, this.user, this.stops, this.stations);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			if (source.equals("Activate Selected Card")) {
				if (!(UserFunctionHandler.value == null)) {
					try {
						Writer.removeCard(UserFunctionHandler.value, this.user);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Card chosen = UserFunctionHandler.value;
					chosen.activate();
					try {
						Writer.writeCard(this.user.getEmail(), "" + chosen.getBalance(), "" + chosen.getCard_id(), true,
								chosen.getTimeInitialized());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						this.obj.userFunctionsUI(this.stage, this.user, this.stops, this.stations);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			if (source.equals("Change Name")) {
				if (!(this.cNameTxt.getText() == "")) {

					try {
						Writer.removeCardHolder(this.user);
					} catch (IOException e) {
						e.printStackTrace();
					}

					this.user.setName(this.cNameTxt.getText());

					try {
						Writer.writeCardHolder(this.user);
					} catch (IOException e) {
						e.printStackTrace();
					}

					try {
						this.obj.userFunctionsUI(this.stage, this.user, this.stops, this.stations);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			if (source.equals("Suspend Selected Card")) {
				if (!(UserFunctionHandler.value == null)) {

					try {
						Writer.removeCard(UserFunctionHandler.value, this.user);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Card chosen = UserFunctionHandler.value;
					chosen.desactivate();
					try {
						Writer.writeCard(this.user.getEmail(), "" + chosen.getBalance(), "" + chosen.getCard_id(),
								false, chosen.getTimeInitialized());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						this.obj.userFunctionsUI(this.stage, this.user, this.stops, this.stations);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

				}
			}

		}

		else {
			UserFunctionHandler.value = (Card) this.list.getValue();

		}
		
		if (event.getSource() instanceof Button) {
			String source = ((Button) event.getSource()).getText();
			if (source.equals("Suspend Selected Card") || source.equals("Activate Selected Card")) {
				StartUp.cards.get(this.user.getEmail()).add(UserFunctionHandler.value);
				UserFunctionHandler.value = null;
			}
		}
		
	}

}
