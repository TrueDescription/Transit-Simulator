package transitapp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class TransitGui extends Application {

	public static void main(String[] args) throws FileNotFoundException {
		launch(args);
	}

	/**
	 * Renders the opening scene of the GUI Application
	 */
	@Override
	public void start(Stage stage) throws Exception {
		StartUp.main();
		HashMap<String, CardHolder> users = StartUp.cardHolders;
		stage.setTitle("UTM Metro");
		stage.setMaxHeight(700);
		stage.setMinHeight(700);
		stage.setMaxWidth(900);
		stage.setMinWidth(900);
		Label welcome = new Label("Welcome to UTM Metro");
		welcome.setMinSize(150, 100);
		welcome.setFont(new Font(50));
		welcome.setTextFill(Color.web("#fbfbfb"));
		TextField name = new TextField();
		name.setPrefSize(400, 30);
		TextField email = new TextField();
		email.setPrefSize(400, 30);
		TextField admin = new TextField();
		email.setPrefSize(400, 30);
		Label ntxt = new Label("Name: ");
		ntxt.setFont(new Font(14));
		ntxt.setTextFill(Color.web("#fbfbfb"));
		Label etxt = new Label("Email: ");
		etxt.setFont(new Font(14));
		etxt.setTextFill(Color.web("#fbfbfb"));
		Label atxt = new Label("Admin ID");
		atxt.setFont(new Font(14));
		atxt.setTextFill(Color.web("#fbfbfb"));
		GridPane center = new GridPane();
		center.setMinSize(500, 500);
		center.setMaxSize(500, 500);
		center.setVgap(5);
		center.setHgap(5);
		Button login = new Button("Log In");
		login.setPrefWidth(100);
		login.setOnAction(new LoginHandler(name, email, stage, this, StartUp.cardHolders));
		Button signup = new Button("Sign Up");
		signup.setPrefWidth(100);
		signup.setOnAction(new SignUpHandler(stage, this, name, email, StartUp.cardHolders));
		Button adminin = new Button("Admin Log In");
		adminin.setPrefWidth(100);
		adminin.setOnAction(new LoginHandler(admin, stage, this));
		center.add(ntxt, 0, 0);
		center.add(name, 1, 0);
		center.add(etxt, 0, 2);
		center.add(email, 1, 2);
		center.add(login, 1, 3);
		center.add(signup, 0, 3);
		center.add(atxt, 0, 6);
		center.add(admin, 1, 6);
		center.add(adminin, 0, 7);
		center.setAlignment(Pos.CENTER);
		Image back = new Image("file:resources/backdrop.png");
		ImageView back2 = new ImageView();
		back2.setImage(back);
		StackPane pane = new StackPane(back2, welcome, center);
		StackPane.setAlignment(welcome, Pos.TOP_CENTER);
		center.setAlignment(Pos.CENTER);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Renders the user side of the application once a user logs in from the start
	 * page
	 * 
	 * @param stage the stage being rendered
	 * @param user  the card holder that has signed in
	 * @throws FileNotFoundException
	 */
	public void userUI(Stage stage, CardHolder user) throws FileNotFoundException {
		StackPane pane = new StackPane();
		Image back = new Image("file:resources/backdrop.png");
		ImageView back2 = new ImageView();
		back2.setImage(back);
		pane.getChildren().add(back2);
		this.UserUIAfter(stage, user, StartUp.stops, StartUp.stations);
	}

	/**
	 * Renders the userFunction for the user that is currently logged in
	 * 
	 * @param stage    the stage that is being rendered
	 * @param user     the user that the page is currently logged into
	 * @param stops    A Hashmap key is stop name and value is the stop object
	 * @param stations A Hashmap key is a station name and value is the station
	 *                 object
	 * @throws FileNotFoundException
	 */
	public void userFunctionsUI(Stage stage, CardHolder user, HashMap<String, Stop> stops,
			HashMap<String, Station> stations) throws FileNotFoundException {

		HashMap<String, CardHolder> cardHolders = StartUp.cardHolders;
		StackPane pane = new StackPane();
		Image back = new Image("file:resources/backdrop.png");
		ImageView back2 = new ImageView();
		back2.setImage(back);
		pane.getChildren().add(back2);
		Button begin = new Button("Begin a Trip");
		begin.setPrefSize(150, 75);
		begin.setFont(new Font(20));
		begin.setOnAction(new UserFunctionHandler(user, this, stage, stops, stations));
		Button suspend = new Button("Suspend Selected Card");
		suspend.setOnAction(new UserFunctionHandler(user, this, stage, stops, stations));
		suspend.setPrefWidth(150);
		Button cname = new Button("Change Name");
		cname.setPrefWidth(150);
		Button activate = new Button("Activate Selected Card");
		activate.setOnAction(new UserFunctionHandler(user, this, stage, stops, stations));
		activate.setPrefWidth(150);
		TextField cNameTxt = new TextField();
		cname.setOnAction(new UserFunctionHandler(cNameTxt, user, this, stage, stops, stations));
		CardHolder currUser = user;
		Label name = new Label("Name: " + currUser.getName());
		name.setTextFill(Color.web("#fbfbfb"));
		name.setFont(new Font(20));
		Label email = new Label("Email: " + currUser.getEmail());
		email.setTextFill(Color.web("#fbfbfb"));
		email.setFont(new Font(20));
		DecimalFormat df2 = new DecimalFormat("0.00");
		Label mCost = new Label("Average Monthly Cost: $" + df2.format(currUser.averageMonthlyCost()));
		mCost.setTextFill(Color.web("#fbfbfb"));
		mCost.setFont(new Font(20));
		VBox top = new VBox();
		top.getChildren().add(name);
		top.getChildren().add(email);
		top.getChildren().add(mCost);
		pane.getChildren().add(top);
		top.setAlignment(Pos.TOP_CENTER);
		ObservableList<Card> cList = FXCollections.observableArrayList();
		for (Card s : user.getCards()) {
			if (s.isActivated()) {
				cList.add(s);
			}
		}
		ComboBox<Card> cardListActive = new ComboBox<Card>(cList);
		cardListActive.setPrefWidth(150);
		cardListActive.setOnAction(new UserFunctionHandler(cardListActive));
		ObservableList<Card> cList2 = FXCollections.observableArrayList();
		for (Card s : user.getCards()) {
			if (!s.isActivated()) {
				cList2.add(s);
			}
		}
		ComboBox<Card> cardListSus = new ComboBox<Card>(cList2);
		cardListSus.setPrefWidth(150);
		cardListSus.setOnAction(new UserFunctionHandler(cardListSus));
		ArrayList<Trip> recent = currUser.getRecentTrips();
		TextArea rt = new TextArea();
		rt.setFont(new Font(15));
		rt.setEditable(false);
		String total = "Recent Trips:\n";
		int i = 1;
		for (Trip t : recent) {
			total = total + "Trip " + i + ": " + t.toString() + "\n";
			i++;
		}
		rt.setText(total);
		rt.setMaxSize(550, 150);
		rt.setTranslateY(20);
		GridPane center = new GridPane();
		center.setHgap(5);
		center.setVgap(5);
		center.add(cname, 0, 0);
		center.add(cNameTxt, 1, 0);
		center.add(suspend, 0, 1);
		center.add(cardListActive, 1, 1);
		center.add(activate, 0, 2);
		center.add(cardListSus, 1, 2);
		center.setAlignment(Pos.CENTER);
		VBox finalPane = new VBox();
		finalPane.setAlignment(Pos.CENTER);
		finalPane.getChildren().add(center);
		finalPane.getChildren().add(rt);
		finalPane.getChildren().add(begin);
		begin.setTranslateY(50);
		pane.getChildren().add(finalPane);
		Button ten = new Button("Add $10 To Balance");
		ten.setPrefWidth(180);
		Button twenty = new Button("Add $20 To Balance");
		twenty.setPrefWidth(180);
		Button fifty = new Button("Add $50 To Balance");
		fifty.setPrefWidth(180);
		Label update = new Label("Choose A Card Above to Add Balance");
		update.setTextAlignment(TextAlignment.CENTER);
		update.setTextFill(Color.web("#fbfbfb"));
		update.setAlignment(Pos.CENTER);
		ComboBox<Card> balanceList = new ComboBox<Card>(cList);
		balanceList.setPrefWidth(180);
		ten.setOnAction(new BalanceHandler(balanceList, user, update, ten, twenty, fifty));
		twenty.setOnAction(new BalanceHandler(balanceList, user, update, ten, twenty, fifty));
		fifty.setOnAction(new BalanceHandler(balanceList, user, update, ten, twenty, fifty));
		VBox add = new VBox(balanceList, ten, twenty, fifty, update);
		finalPane.getChildren().add(add);
		add.setAlignment(Pos.TOP_RIGHT);
		add.setTranslateY(30);
		add.setTranslateX(-20);
		cNameTxt.setPrefWidth(150);
		update.setFont(new Font(12));
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Renders the stage where a user can plan a trip
	 * 
	 * @param stage    The stage that is being modified by this method
	 * @param user     The user that is currently logged in
	 * @param stops    A Hashmap key is stop name and value is the stop object
	 * @param stations A Hashmap key is a station name and value is the station
	 *                 object
	 * @throws FileNotFoundException
	 */
	public void UserUIAfter(Stage stage, CardHolder user, HashMap<String, Stop> stops,
			HashMap<String, Station> stations) throws FileNotFoundException {
		Pane pane = new StackPane();
		Image back = new Image("file:resources/backdrop.png");
		ImageView back2 = new ImageView();
		back2.setImage(back);
		pane.getChildren().add(back2);
		GridPane center = new GridPane();
		ObservableList<Location> oList = FXCollections.observableArrayList();
		for (Stop s : stops.values()) {
			oList.add(s);
		}
		for (Station s : stations.values()) {
			oList.add(s);
		}
		ComboBox<Location> list = new ComboBox<Location>(oList);
		list.setPrefSize(400, 30);
		center.add(list, 1, 1);
		Label loc = new Label("Choose Start Location:");
		loc.setTextFill(Color.web("#fbfbfb"));
		center.add(loc, 0, 1);
		ObservableList<Card> cList = FXCollections.observableArrayList();
		for (Card s : user.getCards()) {
			if (s.hasBalance()) {
				cList.add(s);
			}
		}
		ComboBox<Card> cardList = new ComboBox<Card>(cList);
		cardList.setPrefSize(400, 30);
		Label c = new Label("Choose A Card:");
		c.setTextFill(Color.web("#fbfbfb"));
		center.add(c, 0, 0);
		center.add(cardList, 1, 0);
		center.setAlignment(Pos.CENTER);
		center.setHgap(10);
		center.setVgap(10);
		Button startTrip = new Button("Start Trip");
		startTrip.setOnAction(new StartTripHandler(stage, user, cardList, list, this));
		Button userFunctions = new Button("User Functions");
		center.add(startTrip, 0, 3);
		center.add(userFunctions, 1, 3);
		userFunctions.setOnAction(new UserFunctionHandler(user, this, stage, stops, stations));
		Button backButton = new Button("Back");
		backButton.setOnAction(new BackButtonsHandler(this, stage, "HomePage"));
		backButton.setAlignment(Pos.TOP_LEFT);
		VBox userInfo = new VBox();
		Label name = new Label("Name: " + user.getName());
		name.setTextFill(Color.web("#fbfbfb"));
		name.setFont(new Font(25));
		Label email = new Label("Email: " + user.getEmail());
		email.setTextFill(Color.web("#fbfbfb"));
		email.setFont(new Font(25));
		userInfo.getChildren().addAll(name, email);
		pane.getChildren().add(userInfo);
		userInfo.setAlignment(Pos.TOP_CENTER);
		pane.getChildren().add(center);
		center.add(backButton, 0, 4);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Renders the admin side of the GUI Application
	 * 
	 * @param stage The stage to be changed and renders
	 * @param l     the label that is printed to the screen
	 */
	public void adminUI(Stage stage, Label l) {
		StackPane pane = new StackPane();
		Image back = new Image("file:resources/backdrop.png");
		ImageView back2 = new ImageView();
		back2.setImage(back);
		pane.getChildren().add(back2);
		l.setTextFill(Color.web("#fbfbfb"));
		l.setAlignment(Pos.BOTTOM_CENTER);
		Button go = new Button("GO");
		Button backButton = new Button("Back");
		VBox vb = new VBox();
		backButton.setAlignment(Pos.TOP_LEFT);
		Label action = new Label("Please Select An Admin Function From The List Below, Then Click Go:");
		action.setTextFill(Color.web("#fbfbfb"));
		GridPane gp = new GridPane();
		String actions[] = { "Get Daily Report", "Set Fair For Bus Routes", "Set Fair For Stations" };
		ComboBox<String> actionList = new ComboBox(FXCollections.observableArrayList(actions));
		actionList.setPrefSize(400, 10);
		gp.add(action, 1, 0);
		gp.add(actionList, 1, 1);
		gp.add(go, 1, 2);
		gp.setAlignment(Pos.CENTER);
		gp.setTranslateY(300);
		vb.getChildren().add(backButton);
		vb.getChildren().add(gp);
		vb.getChildren().add(l);
		pane.getChildren().add(vb);
		actionList.setOnAction(new AdminFunctionsHandler(actionList));
		backButton.setOnAction(new BackButtonsHandler(this, stage, "HomePage"));
		go.setOnAction(new AdminFunctionsHandler(this, stage));
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * Renders the stage where an Admin can request a daily report for a certain day
	 * 
	 * @param stage The stage that is being changed and rendered
	 */
	public void showDR(Stage stage) {
		StackPane pane = new StackPane();
		Image back = new Image("file:resources/backdrop.png");
		ImageView back2 = new ImageView();
		back2.setImage(back);
		pane.getChildren().add(back2);
		GridPane grid = new GridPane();
		Label label = new Label("Please Input the date you would like to check out (Format: YYYY-MM-DD):");
		label.setTextFill(Color.web("#fbfbfb"));
		TextField tf = new TextField();
		Button rep = new Button("Show Daily Report");
		grid.add(label, 1, 0);
		grid.add(tf, 1, 1);
		grid.add(rep, 1, 2);
		grid.setAlignment(Pos.CENTER);
		pane.getChildren().add(grid);
		rep.setOnAction(new DailyReportHandler(tf, this, stage));
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * The method renders a stage where an admin can change the fare of a subway
	 * 
	 * @param stage The stage that is being changed and rendered
	 */
	public void showSetStation(Stage stage) {
		StackPane pane = new StackPane();
		Image back = new Image("file:resources/backdrop.png");
		ImageView back2 = new ImageView();
		back2.setImage(back);
		pane.getChildren().add(back2);
		GridPane grid = new GridPane();
		Label label = new Label("Please Input dollar amount:");
		label.setTextFill(Color.web("#fbfbfb"));
		TextField tf = new TextField();
		Button change = new Button("Change");
		grid.add(label, 1, 0);
		grid.add(tf, 1, 1);
		grid.add(change, 1, 2);
		grid.setAlignment(Pos.CENTER);
		change.setOnAction(new ChangeFareHandler(this, tf, stage, false));
		pane.getChildren().add(grid);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * The method that renders the stage where an admin can change the fare of a bus
	 * 
	 * @param stage The stage that is being changed and rendered
	 */
	public void showSetBusFair(Stage stage) {
		StackPane pane = new StackPane();
		Image back = new Image("file:resources/backdrop.png");
		ImageView back2 = new ImageView();
		back2.setImage(back);
		pane.getChildren().add(back2);
		GridPane grid = new GridPane();
		Label label = new Label("Please Input dollar amount:");
		label.setTextFill(Color.web("#fbfbfb"));
		TextField tf = new TextField();
		Button change = new Button("Change");
		grid.add(label, 1, 0);
		grid.add(tf, 1, 1);
		grid.add(change, 1, 2);
		grid.setAlignment(Pos.CENTER);
		change.setOnAction(new ChangeFareHandler(this, tf, stage, true));
		pane.getChildren().add(grid);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * The method renders the daily report display
	 * 
	 * @param stage The stage that is being changed and altered
	 * @param ld    the local date that is being requested
	 * @throws FileNotFoundException
	 */
	public void showDailyReport(Stage stage, LocalDate ld) throws FileNotFoundException {
		Pane pane = new Pane();
		Image back = new Image("file:resources/backdrop.png");
		ImageView back2 = new ImageView();
		back2.setImage(back);
		pane.getChildren().add(back2);
		Button backButton = new Button("Back");
		backButton.setAlignment(Pos.TOP_LEFT);
		TextArea ta = new TextArea();
		ta.setMinSize(450, 375);
		ta.setFont(new Font(15));
		ta.setLayoutX(150);
		ta.setLayoutY(150);
		ta.setText(AdminUser.showDailyReport(ld));
		pane.getChildren().add(ta);
		pane.getChildren().add(backButton);
		backButton.setOnAction(new BackButtonsHandler(this, stage, "Admin"));
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}


	/**
	 * The method renders the Stage that displays when creating a trip
	 * 
	 * @param stage        The stage that is being altered
	 * @param selectedCard The card that is being charged on the trip
	 * @param start        The location that was selected
	 * @param user         The card holder that is currently logged in
	 * @throws IOException
	 */
	public void continueTrip(Stage stage, Card selectedCard, Location start, CardHolder user) throws IOException {

		HashMap<String, Stop> stops = StartUp.stops;
		HashMap<String, Station> stations = StartUp.stations;
		ArrayList<TransitRoutes> busRoutes = StartUp.busRoutes;
		ArrayList<TransitRoutes> subwayRoutes = StartUp.subwayRoutes;
		StackPane pane = new StackPane();
		Image back = new Image("file:resources/backdrop.png");
		ImageView back2 = new ImageView();
		back2.setImage(back);
		pane.getChildren().add(back2);
		Label balance = new Label("Balance on Card: " + selectedCard.getBalance());
		balance.setTextFill(Color.web("#fbfbfb"));
		Location currL = start;
		Label currLocation = new Label("Current Location: " + start);
		currLocation.setTextFill(Color.web("#fbfbfb"));
		String sOrS = "";
		Label atInjuction = new Label("");
		atInjuction.setTextFill(Color.web("#fbfbfb"));
		if (currL.getAtInjuction()) {
			try {
				Stop s = (Stop) currL;
				atInjuction = new Label("This Stop Has a Station");
			} catch (Exception e) {
				Station s = (Station) currL;
				atInjuction = new Label("This Station Has a Stop");
			}
		} else {
			try {
				Stop s = (Stop) currL;
				atInjuction = new Label("This Stop Does Not Have a Station");
			} catch (Exception e) {
				Station s = (Station) currL;
				atInjuction = new Label("This Station Does Not Have a Stop");
			}
		}
		Label sDest = new Label("Select Possible Destination:");
		sDest.setTextFill(Color.web("#fbfbfb"));
		atInjuction.setTextFill(Color.web("#fbfbfb"));
		Button tapOn = new Button("Tap On");
		Button tapOff = new Button("Tap Off");
		Button endTrip = new Button("End Trip");
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

		ComboBox<Location> posibleDest = new ComboBox<Location>(oList);
		tapOn.setOnAction(new ContinueTripHandler(stage, this, selectedCard, currL, user, posibleDest, balance,
				currLocation, atInjuction, tapOn, tapOff));
		tapOff.setOnAction(new ContinueTripHandler(stage, this, selectedCard, currL, user, posibleDest, balance,
				currLocation, atInjuction, tapOn, tapOff));
		endTrip.setOnAction(new ContinueTripHandler(stage, this, selectedCard, currL, user, posibleDest, balance,
				currLocation, atInjuction, tapOn, tapOff));
		tapOff.setDisable(true);
		HBox tap = new HBox();
		tap.setAlignment(Pos.CENTER);
		tap.getChildren().add(tapOn);
		tap.getChildren().add(tapOff);
		tap.setSpacing(5);
		HBox drop = new HBox();
		drop.setAlignment(Pos.CENTER);
		drop.getChildren().add(sDest);
		drop.getChildren().add(posibleDest);
		drop.setSpacing(5);
		VBox center = new VBox();
		center.setSpacing(5);
		center.getChildren().add(balance);
		center.getChildren().add(currLocation);
		center.getChildren().add(atInjuction);
		center.getChildren().add(drop);
		center.getChildren().add(tap);
		center.getChildren().add(endTrip);
		center.setAlignment(Pos.CENTER);
		pane.getChildren().add(center);
		
		
		tapOn.setFont(new Font(21));
		tapOff.setFont(new Font(21));
		sDest.setFont(new Font(21));
		atInjuction.setFont(new Font(21));
		balance.setFont(new Font(21));
		currLocation.setFont(new Font(21));
		
		balance.setTranslateY(-200);
		currLocation.setTranslateY(-200);
		atInjuction.setTranslateY(-200);
		
		
		
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();

	}
}
