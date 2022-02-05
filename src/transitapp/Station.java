package transitapp;

import java.util.ArrayList;
import java.util.Observer;

public class Station extends Location implements Observer {
	/**
	 * Initialize all stations, empty ArrayList
	 */
	private static ArrayList<Station> ALL_STAITIONS = new ArrayList<Station>();

	/**
	 * Constructor for Station, takes in a name and atInjuction boolean
	 * 
	 * @param location     the name of the station
	 * @param atInjunction if the station is at an injuction
	 */
	public Station(String location, boolean atInjunction) {
		super(location, atInjunction);
		Station.ALL_STAITIONS.add(this);
	}

	// @Override
	/**
	 * @return an ArrayList of all the destinations for this station
	 */
	public ArrayList<Location> getAllDestinations() {
		ArrayList<Location> all = this.getAllDestinations(this.getOnRoutes().get(0));
		return all;
	}

	/**
	 * A private helper for the method getAllDestinations iterate through transit
	 * route and add all locations until the location is reached
	 * 
	 * @param TransitRoute the corresponding transit route
	 * @return an ArrayList of all location
	 */
	private ArrayList<Location> getAllDestinations(TransitRoutes TransitRoute) {
		ArrayList<Location> leftDestinations = new ArrayList<Location>();
		for (Location l : TransitRoute.getRoute()) {
			if (!(l.getLocation().equals(this.getLocation()))) {
				leftDestinations.add(l);
			}
		}
		return leftDestinations;
	}

	/**
	 * @return an ArrayList of all the stations
	 */
	public ArrayList<Station> getAllStations() {
		return Station.ALL_STAITIONS;
	}

	/**
	 * @return a string representation of the subway station
	 */
	public String toString() {
		return ("Subway Station: " + this.getLocation());
	}

}
