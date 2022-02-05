package transitapp;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public abstract class Location implements Observer {
	/**
	 * atInjuction location onRoutes ALL_LOCATIONS
	 */

	private boolean atInjunction;
	private String location;
	private ArrayList<TransitRoutes> onRoutes;
	private static ArrayList<Location> ALL_LOCATIONS = new ArrayList<Location>();

	/**
	 * The constructor for the Location class
	 * 
	 * @param location     name
	 * @param atInjunction if the location is an injuction
	 */
	public Location(String location, boolean atInjunction) {
		this.atInjunction = atInjunction;
		this.location = location;
		this.onRoutes = new ArrayList<TransitRoutes>();
		Location.ALL_LOCATIONS.add(this);
	}

	/**
	 * Abstract class to get all the destinations from this location
	 * 
	 * @return
	 */
	public abstract ArrayList<Location> getAllDestinations();

	// Getters and setters

	/**
	 * Getter for is at an injunction
	 * 
	 * @return whether the location is at an injunction
	 */
	public boolean getAtInjuction() {
		return this.atInjunction;
	}

	/**
	 * @return the name of this location
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * @return the routes that the location is on
	 */
	public ArrayList<TransitRoutes> getOnRoutes() {
		return this.onRoutes;
	}

	/**
	 * @return all locations (static)
	 */
	public static ArrayList<Location> getAllLocations() {
		return Location.ALL_LOCATIONS;
	}

	/**
	 * Add this location to a route
	 * 
	 * @param TransitRoute the route
	 */
	public void addOnRoute(TransitRoutes TransitRoute) {
		this.onRoutes.add(TransitRoute);
	}

	@Override
	/**
	 * Called when location is added to the route
	 */
	public void update(Observable arg0, Object TransitRoute) {
		boolean found = false;
		TransitRoutes route = (TransitRoutes) TransitRoute;
		this.addOnRoute(route);
	}
}
