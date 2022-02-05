package transitapp;

import java.io.FileNotFoundException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginHandler implements EventHandler<ActionEvent> {

	private TextField name;
	private TextField email;
	private TextField adminId;
	private Stage stage;
	private TransitGui obj;
	private HashMap<String, CardHolder> users;

	/**
	 * The constructor for client log ins
	 * 
	 * @param name  The name of the user loging in
	 * @param email The email of the user logging in
	 * @param stage The stage being changed
	 * @param obj   The Transit gui main application Object
	 * @param users The Hashmap of all the users that exist
	 */
	public LoginHandler(TextField name, TextField email, Stage stage, TransitGui obj,
			HashMap<String, CardHolder> users) {
		this.name = name;
		this.email = email;
		this.stage = stage;
		this.obj = obj;
		this.users = users;
	}

	/**
	 * 
	 * @param adminId The id of the admin logging in
	 * @param stage   The stage being changed
	 * @param obj     The main application object
	 */
	public LoginHandler(TextField adminId, Stage stage, TransitGui obj) {
		this.adminId = adminId;
		this.stage = stage;
		this.obj = obj;
	}

	/**
	 * The handle method that handles both client and admin log ins
	 */
	@Override
	public void handle(ActionEvent arg0) {
		String source = ((Button) arg0.getSource()).getText();
		if (source.equals("Log In")) {
			String name = this.name.getText();
			String email = this.email.getText();
			CardHolder user = users.get(email);
			if (!(user == null)) {
				String userName = user.getName();
				if (userName.equals(name)) {
					try {
						this.obj.userUI(this.stage, user);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				} else {
					this.name.setText("Name and Email Do Not Match Directory of Users");
				}
			} else {
				this.email.setText("User Not Found!");
			}
		}
		if (source.equals("Admin Log In")) {
			if (adminId.getText().equals("Teaching Assistant"))
				this.obj.adminUI(this.stage, new Label(""));
		}
	}

}
