package transitapp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpHandler implements EventHandler<ActionEvent> {

	private Stage stage;
	private TransitGui obj;
	private TextField name;
	private TextField email;
	private HashMap<String, CardHolder> users;

	/**
	 * Constructor that handles the sign up action
	 * 
	 * @param stage      The stage that is being changed
	 * @param transitGui The main application transit gui object
	 * @param name       The name of the user signing in
	 * @param email      The email of the user signing in
	 * @param users      The list of all users in the system
	 */
	public SignUpHandler(Stage stage, TransitGui transitGui, TextField name, TextField email,
			HashMap<String, CardHolder> users) {
		this.stage = stage;
		this.obj = transitGui;
		this.name = name;
		this.email = email;
		this.users = users;
	}

	/**
	 * The handle method that takes care of the sign in functions
	 */
	@Override
	public void handle(ActionEvent arg0) {
		String nameTxt = name.getText();
		String emailTxt = email.getText();
		if (users.get(emailTxt) == null) {
			if ((nameTxt.length() > 0) && (emailTxt.length() > 0) && (emailTxt.contains("@"))) {
				CardHolder newUser = new CardHolder(nameTxt, emailTxt);
				Card card = newUser.getCards().get(0);
				try {
					Writer.writeCard(emailTxt, "19", "" + card.getCard_id(), card.isActivated(), LocalDate.now());
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				try {
					Writer.writeCardHolder(newUser);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					StartUp.cardHolders.put(newUser.getEmail(), newUser);
					ArrayList<Card> newList = new ArrayList<Card>();
					newList.add(card);
					StartUp.cards.put(newUser.getEmail(), newList);
					obj.userUI(stage, newUser);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				name.setText("Enter Name Please");
				email.setText("Enter Email Please");
			}
		} else {
			if (!(emailTxt.contains("@"))) {
				email.setText("Email Does Not Contain '@' Symbol");
			} else {
				email.setText("This Email Is Already Registered");
			}

		}
	}

}
