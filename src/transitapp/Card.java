package transitapp;

import java.time.LocalDate;

public class Card {

	private double balance = 19.0;
	private static int CARDS_ISSUED = 0; // total number of cards issued to all users
	private int card_id; // unique identifier
	private boolean isActivated;
	private LocalDate timeOfInitialization;

	/**
	 * The constructor for a Card Object. It contains a unique card id that's always
	 * one more than the total number of cards issued. Initiated at $19.00.
	 */
	public Card(LocalDate date) {
		CARDS_ISSUED += 1;
		this.card_id = CARDS_ISSUED;
		AdminUser.addRevenue(this.balance, date);
		this.isActivated = true;
		this.timeOfInitialization = date;
	}

	public Card(double balance, int card_id, LocalDate date) {
		this.balance = balance;
		this.card_id = card_id;
		CARDS_ISSUED += 1;
		AdminUser.addRevenue(this.balance, date);
		this.isActivated = true;
		this.timeOfInitialization = date;
	}

	/**
	 * 
	 * @return date of which the card was initialized
	 */
	public LocalDate getTimeInitialized() {
		return this.timeOfInitialization;
	}

	/**
	 * @return if the card is active
	 */
	public boolean isActivated() {
		return this.isActivated;
	}

	/**
	 * Activate the card
	 */
	public void activate() {
		this.isActivated = true;
	}

	/**
	 * Desactivate the card
	 */
	public void desactivate() {
		this.isActivated = false;
	}

	/**
	 * @return A string representation of the card
	 */
	public String toString() {
		if (this.isActivated()) {
			return "Card id: " + (Integer) card_id + ". Status: Active";
		} else {
			return "Card id: " + (Integer) card_id + ". Status: Inactive";
		}

	}

	/**
	 * Add an amount to the Card and update the Transit revenue
	 * 
	 * @param amount to be added
	 */
	public void addBalance(double amount, LocalDate date) {
		this.balance += amount;
		AdminUser.addRevenue(amount, date);
	}

	/**
	 * Used for tapping on. Deduct fare amount from card balance.
	 * 
	 * @param the amount to deduct
	 */
	public void deductFare(double amount) {
		this.balance -= amount;
	}

	/**
	 * @return whether the card doesn't have a negative balance
	 */
	public boolean hasBalance() {
		return this.balance >= 0;
	}

	/**
	 * @return the card's id
	 */
	public int getCard_id() {
		return card_id;
	}

	/**
	 * @return The total number of cards issued to all CardHolders
	 */
	public int getCardsIssued() {
		return CARDS_ISSUED;
	}

	/**
	 * @return the card's balance
	 */
	public double getBalance() {
		return this.balance;
	}
}
