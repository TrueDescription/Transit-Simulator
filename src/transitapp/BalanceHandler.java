package transitapp;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class BalanceHandler implements EventHandler<ActionEvent> {

	private ComboBox<Card> value;
	private CardHolder user;
	private Label update;
	private Button ten;
	private Button twenty;
	private Button fifty;

	/**
	 * The constructor that takes the combo box and cardholder being acted on
	 * 
	 * @param balanceList The combo box that holds the balance
	 * @param user        The user that is logged in
	 * @param update      the label that changes in gui that confirms balance has
	 *                    been changed
	 * @param fifty 	  button for adding 50$ to balance
	 * @param twenty 	  button for adding 20$ to balance
	 * @param ten  		  button for adding 10$ to balance
	 */
	public BalanceHandler(ComboBox<Card> balanceList, CardHolder user, Label update, Button ten, Button twenty, Button fifty) {
		this.value = balanceList;
		this.user = user;
		this.update = update;
		this.ten = ten;
		this.twenty = twenty;
		this.fifty = fifty;
	}

	/**
	 * Handles adding balances when the desired button is clicked
	 */
	@Override
	public void handle(ActionEvent arg0) {
		String source = ((Button) arg0.getSource()).getText();
		if (!(this.value.getValue() == null)) {
			if (source.equals("Add $10 To Balance")) {
				this.value.getValue().addBalance(10, LocalDate.now());
				try {
					Writer.removeCard(this.value.getValue(), user);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Writer.writeCard(user.getEmail(), "" + (this.value.getValue().getBalance()),
							"" + this.value.getValue().getCard_id(), this.value.getValue().isActivated(),
							this.value.getValue().getTimeInitialized());
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.update.setText("Successfully Added $10 to Balance");
			} else if (source.equals("Add $20 To Balance")) {
				this.value.getValue().addBalance(20, LocalDate.now());
				try {
					Writer.removeCard(this.value.getValue(), user);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Writer.writeCard(user.getEmail(), "" + (this.value.getValue().getBalance()),
							"" + this.value.getValue().getCard_id(), this.value.getValue().isActivated(),
							this.value.getValue().getTimeInitialized());
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.update.setText("Successfully Added $20 to Balance");
			} else if (source.equals("Add $50 To Balance")) {
				this.value.getValue().addBalance(50, LocalDate.now());
				try {
					Writer.removeCard(this.value.getValue(), user);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Writer.writeCard(user.getEmail(), "" + (this.value.getValue().getBalance()),
							"" + this.value.getValue().getCard_id(), this.value.getValue().isActivated(),
							this.value.getValue().getTimeInitialized());
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.update.setText("Successfully Added $50 to Balance");
			}
			StartUp.cards.get(this.user.getEmail()).add(this.value.getValue());
		}

	}
}
