package transitapp;

import java.util.ArrayList;
import java.util.Observer;

public class Stop extends Location implements Observer {

	private static ArrayList<Stop> ALL_STOPS = new ArrayList<Stop>();

	/**
	 * Constructor for the Stop class
	 * 
	 * @param location     the name of this stop
	 * @param atInjunction if it is an injuction
	 */
	public Stop(String location, boolean atInjunction) {
		super(location, atInjunction);
		Stop.ALL_STOPS.add(this);
	}

	@Override
	/**
	 * @return all the destinations that this is stop is (indirectly) linked to
	 */
	public ArrayList<Location> getAllDestinations() {
		ArrayList<Location> onRoutDestinations = new ArrayList<Location>();
		for (TransitRoutes r : this.getOnRoutes()) {
			boolean found = false;
			for (Location s : r.getRoute()) {
				String stop = ((Stop) s).getLocation();
				if (found) {
					onRoutDestinations.add(s);
				} else {
					if (this.getLocation().equals(stop)) {
						found = true;
					}
				}
			}
		}
		return onRoutDestinations;

	}

	/**
	 * A helper for the above method
	 * 
	 * @param route the corresponding route to the stop
	 * @return An ArrayList of all destinations
	 */
	private ArrayList<Location> getRouteDestinations(TransitRoutes route) {
		ArrayList<Location> allDestinations = new ArrayList<Location>();
		for (TransitRoutes r : this.getOnRoutes()) {
			boolean found = false;
			for (Location l : r.getRoute()) {
				if (found) {
					allDestinations.add(l);
				} else {
					if (l.getLocation().equals(this.getLocation())) {
						found = true;
					}
				}
			}
		}
		return allDestinations;

	}

	/**
	 * @return all the stops
	 */
	public static ArrayList<Stop> getAllStops() {
		return Stop.ALL_STOPS;
	}

	/**
	 * @return A String representation of this bus stop
	 */
	public String toString() {
		return ("Bus Stop: " + this.getLocation());
	}
}
