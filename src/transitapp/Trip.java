package transitapp;

import java.lang.Math;
import java.util.ArrayList;
import java.time.Duration;
import java.time.LocalDateTime;

public class Trip {

	public static double MAX_COST = 6.0;
	public static int MINUTE_GRACE_PERIOD = 120;
	public static int MAX_RIDE_TIME = 180; // in minutes

	private int timeOnTrip = 0; // in minutes
	private double moneySpentOnTrip = 0.0;
	private ArrayList<Location> locationsTravelled = new ArrayList<Location>();
	private ArrayList<String> taps = new ArrayList<String>();
	private ArrayList<LocalDateTime> times = new ArrayList<LocalDateTime>();
	private ArrayList<Integer> cardUsed = new ArrayList<Integer>();
	private LocalDateTime startTime;

	/**
	 * The Trip constructor initiating a new trip
	 */
	public Trip() {
		this.moneySpentOnTrip = 0.0;
		this.timeOnTrip = 0;
		locationsTravelled = new ArrayList<Location>();
	}

	/**
	 * Update the time that has been spent on the trip by getting the duration from
	 * the first time to the last
	 */
	public void updateTimeOnTrip() {
		if (this.times.size() > 1) {
			this.timeOnTrip = (int) (Duration.between(this.times.get(0), this.times.get(this.times.size() - 1))
					.toMinutes());
		}
	}

	/**
	 * @return the minute grace period
	 */
	public double getMINUTE_GRACE_PERIOD() {
		return MINUTE_GRACE_PERIOD;
	}

	/**
	 * Add a card to the cards used in the trip
	 * 
	 * @param cardID the card's identifier
	 */
	public void addCardUsed(Integer cardID) {
		this.cardUsed.add(cardID);
	}

	/**
	 * A getter for the cards used
	 * 
	 * @return an ArrayList of cards used
	 */
	public ArrayList<Integer> getCardUsed() {
		return this.cardUsed;
	}

	/**
	 * Add a time
	 * 
	 * @param time object year-month-date-hour-minute
	 */
	public void addTimes(LocalDateTime time) {
		this.times.add(time);
	}

	/**
	 * A getter for the time ArrayList
	 * 
	 * @return the ArrayList of the times
	 */
	public ArrayList<LocalDateTime> getTimes() {
		return this.times;
	}

	/**
	 * Update the taps of this trip, with tap on / off
	 * 
	 * @param tap A string representing the tap action
	 */
	public void addTaps(String tap) {
		this.taps.add(tap);
	}

	/**
	 * A getter for the trips' taps
	 * 
	 * @return An ArrayList with the string taps of the trip
	 */
	public ArrayList<String> getTaps() {
		return this.taps;
	}

	/**
	 * A setter to accumulate/add money spent on the trip
	 * 
	 * @param amount in dollars to add
	 */
	public void addMoneySpentOnTrip(double amount) {
		this.moneySpentOnTrip += amount;
	}

	/**
	 * A getter for the amount of money spent on the trip
	 * 
	 * @return the amount
	 */
	public double getMoneySpentOnTrip() {
		return this.moneySpentOnTrip;
	}

	/**
	 * Update the locations visited during the trip (i.e. add a location)
	 * 
	 * @param loc Location Object
	 */
	public void addLocation(Location loc) {
		this.locationsTravelled.add(loc);
	}

	/**
	 * A getter for the number of stations travelled
	 * 
	 * @param start    the station at which the tap on occurred
	 * @param end      the station at which the tap off occurred
	 * @param stations The ArrayList with all the stations in the route
	 * @return the number of stations travelled
	 */
	public int stationsTravelled(Station start, Station end, ArrayList<Station> stations) {
		int i = 0;
		while (!(start.getLocation().equals(stations.get(i).getLocation()))) {
			i++;
		}
		int j = 0;
		while (!(end.getLocation().equals(stations.get(j).getLocation()))) {
			j++;
		}
		return Math.abs(i - j);
	}

	/**
	 * A getter for the max cost that can be reached during a trip
	 * 
	 * @return
	 */
	public double getMaxCost() {
		return MAX_COST;
	}

	/**
	 * A getter for the time this trip started
	 * 
	 * @return the date time object
	 */
	public LocalDateTime getStartTime() {
		return this.startTime;
	}

	/**
	 * A setter for the trip's start time
	 * 
	 * @param time the time of start
	 */
	public void setStartTime(LocalDateTime time) {
		this.startTime = time;
	}

	/**
	 * @return the max ride time with charge
	 */
	public int getMAX_RIDE_TIME() {
		return MAX_RIDE_TIME;
	}

	/**
	 * Increment the time spent on the trip
	 * 
	 * @param time the number of minutes to add
	 */
	public void addTimeToTrip(int time) {
		this.timeOnTrip += time;
	}

	/**
	 * A getter for the time spent on this trip
	 * 
	 * @return the time spent on this trip
	 */
	public int getTimeOnTrip() {
		return this.timeOnTrip;
	}

	/**
	 * A getter for all the locations travelled in the trip
	 * 
	 * @return an ArrayList of locations travelled
	 */
	public ArrayList<Location> getLocations() {
		return this.locationsTravelled;
	}

	@Override
	/**
	 * @return A string representation of the trip
	 */
	public String toString() {
		StringBuilder string = new StringBuilder();
		if (this.locationsTravelled.size() > 0) {
			string = new StringBuilder(this.getTimes().get(0).getMonth().getValue() + "/"
					+ this.getTimes().get(0).getDayOfWeek().getValue() + "/" + this.getTimes().get(0).getYear() + ", ");

			for (int i = 0; i < this.locationsTravelled.size(); i++) {
				if (i != this.locationsTravelled.size() - 1) {
					string.append(this.locationsTravelled.get(i).getLocation()).append(" -> ");
				} else {
					string.append(this.locationsTravelled.get(i).getLocation());
				}
			}
		}
		return string.toString();
	}
}
