package transitapp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ContinueTripHandler implements EventHandler<ActionEvent> {

	private Stage stage;
	private Card selectedCard;
	private Location currL;
	private CardHolder user;
	private ComboBox<Location> combo;
	private Label balance;
	private Label currLocation;
	private Label atInjuction;
	private Button tapOn;
	private Button tapOff;
	private TransitGui obj;

	/*
	 * Takes in all information that is required when rendering the gui
	 * 
	 * @param stage the stage of the gui that needs to be loaded
	 * 
	 * @param
	 */
	public ContinueTripHandler(Stage stage, TransitGui obj, Card selectedCard, Location currL, CardHolder user,
			ComboBox<Location> posibleDest, Label balance, Label currLocation, Label atInjuction, Button tapOn,
			Button tapOff) {
		this.stage = stage;
		this.selectedCard = selectedCard;
		this.currL = currL;
		this.user = user;
		this.combo = posibleDest;
		this.balance = balance;
		this.currLocation = currLocation;
		this.atInjuction = atInjuction;
		this.tapOn = tapOn;
		this.tapOff = tapOff;
		this.obj = obj;
	}

	/**
	 * Handles Trip methods like tapOn and tapOff for the gui
	 */
	@Override
	public void handle(ActionEvent arg0) {
		Button source = (Button) arg0.getSource();
		if (!(this.combo.getValue() == null)) {
			if (source.getText().equals("Tap On") && this.selectedCard.hasBalance()
					&& this.selectedCard.isActivated()) {
				this.tapOff.setDisable(false);
				this.tapOn.setDisable(true);
				this.combo.setDisable(true);

				try {
					Writer.removeCard(this.selectedCard, this.user);

					this.user.tapOn(currL, this.selectedCard.getCard_id(), LocalDateTime.now(), false);
					Writer.writeCard(this.user.getEmail(), "" + this.selectedCard.getBalance(),
							"" + this.selectedCard.getCard_id(), true, this.selectedCard.getTimeInitialized());

				} catch (IOException e) {
					e.printStackTrace();
				}
				Location nextDest = this.combo.getValue();
				this.currL = nextDest;
				this.updateAtInjuction();
				this.currLocation.setText("Current Location: " + this.currL);
				this.balance.setText("Balance on Card: " + this.selectedCard.getBalance());
			} else {
				if (!this.selectedCard.isActivated()) {
					this.balance.setText("This Card Is Not Activated");
				} else if (!this.selectedCard.hasBalance()) {
					this.balance.setText("Balance on Card: " + this.selectedCard.getBalance()
							+ " -> You Will Not Be Able To Tap On Until You Add Balance In User Functions");
				}
			}
			if (source.getText().equals("Tap Off")) {
				Location nextDest = this.combo.getValue();
				this.tapOff.setDisable(true);
				this.tapOn.setDisable(false);
				this.combo.setDisable(false);
				this.currL = nextDest;
				this.combo.setItems(getPosibleDests());
				if (this.currL instanceof Stop) {
					try {
						this.user.tapOff(this.currL, this.selectedCard.getCard_id(), LocalDateTime.now(), false);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						Writer.removeCard(this.selectedCard, this.user);
						this.user.tapOff((Station) this.currL, this.selectedCard.getCard_id(), LocalDateTime.now(),
								false);
						Writer.writeCard(this.user.getEmail(), "" + this.selectedCard.getBalance(),
								"" + this.selectedCard.getCard_id(), true, this.selectedCard.getTimeInitialized());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				this.balance.setText("Balance on Card: " + this.selectedCard.getBalance());

			}

		}
		if (source.getText().equals("End Trip")) {
			try {
				this.obj.UserUIAfter(stage, user, StartUp.stops, StartUp.stations);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public ObservableList<Location> getPosibleDests() {
		HashMap<String, Stop> stops = StartUp.stops;
		HashMap<String, Station> stations = StartUp.stations;
		ArrayList<TransitRoutes> busRoutes = StartUp.busRoutes;
		ArrayList<TransitRoutes> subwayRoutes = StartUp.subwayRoutes;
		ObservableList<Location> oList = FXCollections.observableArrayList();
		if (currL instanceof Stop) {
			for (TransitRoutes r : busRoutes) {
				boolean found = false;
				for (Location l : r.getRoute()) {
					if (found) {
						oList.add(l);
					}
					if (l.getLocation().equals(currL.getLocation())) {
						found = true;
					}
				}
			}
		} else {
			for (TransitRoutes r : subwayRoutes) {
				for (Location l : r.getRoute()) {
					if (!l.getLocation().equals(currL.getLocation())) {
						oList.add(l);
					}
				}
			}
		}
		return oList;
	}

	public void updateAtInjuction() {
		if (currL.getAtInjuction()) {
			if (currL instanceof Stop) {
				atInjuction.setText("This Stop Has a Station");
			} else {
				atInjuction.setText("This Station Has a Stop");
			}
		} else {
			if (currL instanceof Stop) {
				atInjuction.setText("This Stop Does Not Have a Station");
			} else {
				atInjuction.setText("This Station Does Not Have a Stop");
			}
		}
	}

}
