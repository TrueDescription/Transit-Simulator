package transitapp;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.Math;
import java.time.LocalDateTime;

public class CardHolder {

	private String name;
	private String email;
	private ArrayList<Trip> trips = new ArrayList<Trip>();
	private ArrayList<Card> cards = new ArrayList<Card>();
	private Trip currTrip;
	private boolean onRoute;
	private Location tapOnLocation;
	private Location tapOffLocation;

	/**
	 * The constructor for CardHolder
	 * 
	 * @param name: This CardHolder's name
	 * @param email: This CardHolder's email address
	 */
	public CardHolder(String name, String email) {
		this.name = name;
		this.email = email;
		this.onRoute = false;
		this.currTrip = null;
		this.cards.add(new Card(LocalDate.now()));
		this.tapOnLocation = null;
		this.tapOffLocation = null;
	}

	public CardHolder(String name, String email, boolean s) {
		this.name = name;
		this.email = email;
		this.onRoute = false;
		this.currTrip = null;
		this.tapOnLocation = null;
		this.tapOffLocation = null;
	}

	/**
	 * Add $10 to one of this CardHolder's cards Precondition: card_id is valid id
	 * within the cards
	 * 
	 * @param card_id: The card's id (int)
	 */
	public void add10toCard(int card_id) {
		Card card = findCard(this.cards, card_id);
		card.addBalance(10, LocalDate.now());
	}

	/**
	 * Add $20 to one of this CardHolder's cards Precondition: card_id is valid id
	 * within the cards
	 * 
	 * @param card_id: The card's id (int)
	 */
	public void add20toCard(int card_id) {
		Card card = findCard(this.cards, card_id);
		card.addBalance(20, LocalDate.now());
	}

	/**
	 * Add $50 to one of this CardHolder's cards Precondition: card_id is valid id
	 * within the cards
	 * 
	 * @param card_id: The card's id (int)
	 */
	public void add50toCard(int card_id) {
		Card card = findCard(this.cards, card_id);
		card.addBalance(50, LocalDate.now());
	}

	/**
	 * A getter for the CardHolder's recent trips
	 * 
	 * @return the 3 most recents trips that this CardHolder has taken.
	 */
	public ArrayList<Trip> getRecentTrips() {
		if (this.trips.size() <= 2) {
			return this.trips;
		} else {
			ArrayList<Trip> recents = new ArrayList<Trip>();
			recents.add(trips.get(trips.size() - 3));
			recents.add(trips.get(trips.size() - 2));
			recents.add(trips.get(trips.size() - 1));
			return recents;
		}
	}

	/**
	 * A getter for all of the CardHolder's trips
	 * 
	 * @return an ArrayList of trips
	 */
	public ArrayList<Trip> getTrips() {
		return this.trips;
	}

	/**
	 * Load the existing trips to the CardHolder's trips
	 * 
	 * @param ArrayList of trips
	 */
	public void loadTrip(ArrayList<Trip> trips) {
		this.trips = trips;
	}

	/**
	 * Delete all the cards this CardHolder has
	 */
	public void deleteAllCards() {
		this.cards.clear();
	}

	/**
	 * A getter for the CardHolder's cards
	 * 
	 * @return Arraylist of cards
	 */
	public ArrayList<Card> getCards() {
		return this.cards;
	}

	/**
	 * Add a card to the Cardholder's existing cards
	 * 
	 * @param An exisitng card object
	 */
	public void addCard(Card card) {
		this.cards.add(card);
	}

	/**
	 * A getter for the CardHolder's name
	 * 
	 * @return the CardHolder's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * A getter for the CardHolder's email address
	 * 
	 * @return the CardHolder's email address
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * A setter method to change the CardHolder's name
	 * 
	 * @param the new name
	 */
	public void changeName(String name) {
		this.name = name;
	}

	/**
	 * A method used to access the CardHolder's average monthly cost. Divides the
	 * total cost by the number of months with at least 1 trip.
	 * 
	 * @return a double of the CardHolder's average monthly cost.
	 */
	public double averageMonthlyCost() {
		ArrayList<LocalDate> months = new ArrayList<LocalDate>();
		double cost = 0.0;
		
		for (Trip trip : this.trips) {
			
			if (trip.getTimes().size() > 0) {
				LocalDate date = LocalDate.of(trip.getTimes().get(0).getYear(), trip.getTimes().get(0).getMonth(), 1);
				if (!months.contains(date)) {
					months.add(date);
				}
				cost += trip.getMoneySpentOnTrip();
			}
		}
		if (months.size() == 0) {
			return 0.0;
		}
		return cost / months.size();
	}

	/**
	 * The Method CardHolders use to tap on a bus/subway route. Precondition: the
	 * card id is a valid card id within the CardHolder's cards
	 * 
	 * @param location: The location where the CardHolder taps on to enter a bus or
	 *        subway
	 * @param card_id: the id of the card used to tap on
	 * @param time: the time at which they tap on including the year, month, day,
	 *        hour and minute
	 * @return whether the tapOn was successful: Must have enough money and have the
	 *         card activated
	 * @throws IOException
	 */
	public boolean tapOn(Location location, int card_id, LocalDateTime time, boolean load) throws IOException {
		Card current_card = findCard(this.cards, card_id);
		if (current_card == null) {
			return false;
		}

		if (!current_card.isActivated()) {
			return false;
		}
		
		if (this.currTrip == null) {

			this.currTrip = new Trip();
			this.trips.add(this.currTrip);

		} else if (this.tapOnLocation != null) {

			this.currTrip = new Trip();
			this.trips.add(this.currTrip);
			if (!load) {
				current_card.deductFare(this.currTrip.getMaxCost());
			}

		}

		if (this.currTrip.getTimes().size() > 0) {
			int temp = (int) (Duration.between(this.currTrip.getTimes().get(0), time).toMinutes());
			if (temp > this.currTrip.getMINUTE_GRACE_PERIOD()) {
				this.currTrip = new Trip();
				this.trips.add(this.currTrip);
			}

		}

		if (this.tapOffLocation != null) {
			if (this.tapOffLocation.getLocation() != location.getLocation()) {
				this.currTrip = new Trip();
				this.trips.add(this.currTrip);
			}
		}

		if (current_card.hasBalance()) {

			this.tapOnLocation = location;
			this.currTrip.addLocation(location);
			this.currTrip.addTaps("tapOn");
			this.currTrip.addCardUsed(card_id);
			this.currTrip.addTimes(time);
			this.currTrip.updateTimeOnTrip();

			if (location instanceof Stop) {

				if (this.currTrip.getMoneySpentOnTrip() + findFare(location) <= this.currTrip.getMaxCost()) {
					this.currTrip.addMoneySpentOnTrip(findFare(location));
					if (!load) {
						Writer.writeEvent("tapOn", "?" + location.getLocation(), card_id, time, this.email);
						current_card.deductFare(findFare(location));
					}
					return true;

				} else {
					double cost = this.currTrip.getMaxCost() - this.currTrip.getMoneySpentOnTrip();
					this.currTrip.addMoneySpentOnTrip(cost);
					if (!load) {
						Writer.writeEvent("tapOn", "?" + location.getLocation(), card_id, time, this.email);
						current_card.deductFare(cost);
					}
					return true;
				}
			}
			if (!load) {
				Writer.writeEvent("tapOn", "!" + location.getLocation(), card_id, time, this.email);
			}
			return true;
		}
		return false;
	}

	/**
	 * The method for CardHolders used to tap Off SUBWAYS (busses don't tap off)
	 * Precondition: the card id is a valid card id within the CardHolder's cards
	 * 
	 * @param location: where the tap off occurs
	 * @param card_id: the id of the card used to tap off (must be same as tap on
	 *        card!)
	 * @param time: the time at which they tap on including the year, month, day,
	 *        hour and minute
	 * @throws IOException
	 */
	public void tapOff(Location location, int card_id, LocalDateTime time, boolean load) throws IOException {

		Card current_card = findCard(this.cards, card_id);

		if (location instanceof Station) {

			Station tempLocation = (Station) location;
			
			if(this.currTrip.getLocations().size() == 0 || 
        			this.currTrip.getLocations().get(this.currTrip.getLocations().size() - 1) instanceof Stop) {
        		if (!load) {
		        	current_card.deductFare(this.currTrip.getMaxCost());
		        	Writer.writeEvent("tapOff", "!" + location.getLocation(), card_id, time, this.email);
		        }
        	}
			
			if (current_card.isActivated()) {

				double cost;
				double fare = findFare(tempLocation);
				this.currTrip.addLocation(tempLocation);
				this.currTrip.addTaps("tapOff");
				this.currTrip.addTimes(time);

				if (tapOnLocation == null || this.currTrip.getCardUsed().size() == 0
						|| this.currTrip.getCardUsed().get(this.currTrip.getCardUsed().size() - 1) != card_id
						|| this.tapOnLocation instanceof Stop) {

					cost = this.currTrip.getMaxCost();

				} else {
					double numStations = this.currTrip.stationsTravelled((Station) this.tapOnLocation, tempLocation,
							tempLocation.getAllStations());
					cost = numStations * fare;
				}

				if (cost + this.currTrip.getMoneySpentOnTrip() > this.currTrip.getMaxCost()) {

					cost = this.currTrip.getMaxCost() - this.currTrip.getMoneySpentOnTrip();
				}

				if (!load) {
					current_card.deductFare(cost);
					Writer.writeEvent("tapOff", "!" + location.getLocation(), card_id, time, this.email);
				}
				this.currTrip.updateTimeOnTrip();
				this.currTrip.addMoneySpentOnTrip(cost);
				this.tapOnLocation = null;
				this.tapOffLocation = tempLocation;
			}

		} else if (location instanceof Stop && !(this.currTrip == null)) {
			this.tapOnLocation = null;
			this.tapOffLocation = (Stop) location;
			this.currTrip.addLocation((Stop) location);
			this.currTrip.addTimes(time);
			this.currTrip.updateTimeOnTrip();
			if (!load) {
				Writer.writeEvent("tapOff", "?" + location.getLocation(), card_id, time, this.email);
			}
		}
	}

	/**
	 * Find the Card object based on its id Precondition: the card id is a valid id
	 * in the list of card objects
	 * 
	 * @param cards: An ArrayList of all cards owned by the CardHolder
	 * @param id: The card's id
	 * @return the card Object
	 */
	public Card findCard(ArrayList<Card> cards, int id) {
		Card found = null;
		for (Card card : cards) {
			if (card.getCard_id() == id) {
				found = card;
			}
		}
		return found;
	}

	/**
	 * Find the fare of this location (either bus or subway)
	 * 
	 * @param location
	 * @return a double representing the transit fare
	 */
	public double findFare(Location location) {
		double fare = 0;
		if (location instanceof Station) {
			fare = TransitRoutes.getSubwayFare();
		} else if (location instanceof Stop) {
			fare = TransitRoutes.getBusFare();
		}
		return fare;
	}

	/**
	 * A setter to change the CardHolder's name
	 * 
	 * @param the new name
	 */
	public void setName(String text) {
		this.name = text;
	}
}
