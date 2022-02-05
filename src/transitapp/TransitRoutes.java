package transitapp;

import java.util.ArrayList;
import java.util.Observable;
import java.time.LocalTime;

/**
 * This class represents a transit route (bus or subway). Each consisting of a
 * String that represents the name of the transit route, an ArrayList of
 * Locations representing a route, an ArrayList of LocalTime representing a
 * schedule, and fares for the bus and subway.
 */
public class TransitRoutes extends Observable {
	private String name;
	private ArrayList<Location> route;
	private ArrayList<LocalTime> schedule; // parallel to routes above
	static double busFare, subwayFare;

	/**
	 * The constructor for TransitRoutes
	 * 
	 * @param name     String represent the name of the route
	 * @param route    ArrayList of Locations representing each stop or station in
	 *                 the route
	 * @param schedule ArrayList of LocalTime representing when each transit vehicle
	 *                 comes
	 */
	public TransitRoutes(String name, ArrayList<Location> route, ArrayList<LocalTime> schedule) {
		this.name = name;
		this.route = route;
		this.schedule = schedule;
		for (Location l : this.route) {
			this.addObserver(l);

		}
		this.setChanged();
		this.notifyObservers(this);
		TransitRoutes.setBusFare(busFare);
		TransitRoutes.setSubwayFare(subwayFare);
	}

	/**
	 * A method that adds a location to the route
	 * 
	 * @param loc   Location representing a stop or station
	 * @param index int representing where to insert loc in the route
	 */
	public void addLocationToRoute(Location loc, int index) {
		this.route.add(index, loc);
		this.addObserver(loc);
		this.notifyObservers(this);
	}

	/**
	 * A method that removes a location from the route
	 * 
	 * @param loc the location to remove in the route
	 */
	public void removeLocationFromRoute(Location loc) {
		this.route.remove(loc);
		this.deleteObserver(loc);
		loc.getOnRoutes().remove(this);
	}

	/**
	 * A method that gets the bus fare
	 * 
	 * @return double representing the bus fare
	 */
	public static double getBusFare() {
		return busFare;
	}

	/**
	 * A method that get the subway fare
	 * 
	 * @return double representing the subway fare
	 */
	public static double getSubwayFare() {
		return subwayFare;
	}

	/**
	 * A method to set bus fare
	 * 
	 * @param newFare double representing the new bus fare
	 */
	public static void setBusFare(double newFare) {
		busFare = newFare;
	}

	/**
	 * A method to set subway fare
	 * 
	 * @param newFare double representing the new subway fare
	 */
	public static void setSubwayFare(double newFare) {
		subwayFare = newFare;
	}

	/**
	 * A method that returns the route this route takes
	 * 
	 * @return ArrayList of Locations representing the route this transit route
	 *         takes
	 */
	public ArrayList<Location> getRoute() {
		return this.route;
	}

	/**
	 * A method that return the schedule for this transit route
	 * 
	 * @return ArrayList of LocalTime representing when each bus arrives at each
	 *         location in route
	 */
	public ArrayList<LocalTime> getSchedule() {
		return this.schedule;
	}

	/**
	 * A method to set a new route for this transit route. This method also adds
	 * each location in the new route to an observer.
	 * 
	 * @param newRoute ArrayList of Locations representing a new route
	 */
	public void setRoute(ArrayList<Location> newRoute) {
		this.route = newRoute;
		for (Location l : this.route) {
			this.addObserver(l);
		}
		this.setChanged();
		this.notifyObservers(this);
	}

	/**
	 * A method to get the name of the station
	 * 
	 * @return String representing the name of the transit routes
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * A method to change the name of the station
	 * 
	 * @param newName String representing the new name of the transit route
	 */
	public void setName(String newName) {
		this.name = newName;
	}

}
