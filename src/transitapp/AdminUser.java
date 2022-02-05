package transitapp;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.DecimalFormat;

public class AdminUser {
	private static String transitName = "UTM Metro";
	private static double totalRevenue;
	private static HashMap<LocalDate, Double> monthlyRevenue = new HashMap<LocalDate, Double>();
	private static double totalCost;
	private static int numBuses;
	private static double busPrice;
	private static double busTravelCost; // the cost (expenses) for a bus to go from one stop to another.
	private static double subwayTravelCost; // the cost (expenses) for a subway to go from one station to another.
	// For simplicity, the travel expenses will be calculated using the 2 above ^
	protected static ArrayList<TransitRoutes> busRoutes;

	/**
	 * The Constructor for AdminUser
	 * 
	 * @param numBuses: The number of busses owned by the transit routes
	 * @param busPrice: The cost to purchase a bus
	 * @param busTravelCost: the cost (expenses) for a bus to go from one stop to
	 *        another.
	 * @param subwayTravelCost: the cost (expenses) for a subway to go from one
	 *        station to another.
	 * @param busRoutes: An ArrayList of all bus routes
	 * @param transitName: The name of this Transit System
	 */
	public AdminUser(int numBuses, double busPrice, double busTravelCost, double subwayTravelCost,
			ArrayList<TransitRoutes> busRoutes, String transitName) {
		totalRevenue = 0;
		totalCost = 0;
		AdminUser.busPrice = busPrice;
		AdminUser.numBuses = numBuses;
		AdminUser.subwayTravelCost = subwayTravelCost;
		AdminUser.busTravelCost = busTravelCost;
		AdminUser.busRoutes = busRoutes;
		AdminUser.transitName = transitName;
		AdminUser.monthlyRevenue = new HashMap<LocalDate, Double>();
	}

	/**
	 * 
	 * @return a hashmap of revenues by date
	 */
	public static HashMap<LocalDate, Double> getMonthlyRev() {
		return AdminUser.monthlyRevenue;
	}

	/**
	 * Sets the cost of getting onto a bus
	 * 
	 * @param fare to be changed to
	 */
	public static void setBusTravelCost(double fare) {
		busTravelCost = fare;

	}

	/**
	 * Sets the cost of getting onto and traveling through stations
	 * 
	 * @param fare to be changed to
	 */
	public static void setStaionPrice(double fare) {
		subwayTravelCost = fare;
	}

	/**
	 * @return the Transit name
	 */
	public static String getTransitName() {
		return AdminUser.transitName;
	}

	/**
	 * Change the Transit name
	 * 
	 * @param name: the new transit name
	 */
	public void setTransitName(String name) {
		AdminUser.transitName = name;
	}

	/**
	 * Output a daily report of given a date including: number of rides, total
	 * revenue all the bus routes and all the subway routes.
	 * 
	 * @param date: to display its report
	 * @throws FileNotFoundException
	 * @return Returns string representation of daily report
	 */
	public static String showDailyReport(LocalDate date) throws FileNotFoundException {
		double revenue = 0;
		String line = "";
		int rides = 0;
		if (AdminUser.monthlyRevenue.containsKey(LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth()))) {
			revenue = AdminUser.monthlyRevenue.get(LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth()));
		}

		DecimalFormat df2 = new DecimalFormat("0.00");

		for (String customer : StartUp.cardHolders.keySet()) {
			for (Trip trip : StartUp.cardHolders.get(customer).getTrips()) {

				if ((trip.getTimes().size() > 0) && trip.getTimes().get(0).toLocalDate().equals(date)) {
					rides++;
				}
			}
		}

		revenue /= 2;

		line = "Report " + getTransitName() + "'s summary for " + date + ":\n\n" + "Number of rides: " + rides
				+ "\n\nTotal revenue: $" + df2.format(revenue) + "\n\nBus Routes: (Fare : "
				+ df2.format(TransitRoutes.getBusFare()) + ")";
		for (TransitRoutes busRoute : StartUp.busRoutes) {
			line = line + "\n\nRoute: " + busRoute.getName();
		}
		for (TransitRoutes subRoute : StartUp.subwayRoutes) {
			line = line + "\n\nRoute: " + subRoute.getName();
		}

		return line;
	}

	/**
	 * Add revenue to the total revenue
	 * 
	 * @param rev: the amount to add to the total revenue
	 */
	public static void addRevenue(double rev, LocalDate date) {
		totalRevenue += rev;
		LocalDate key = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
		if (AdminUser.monthlyRevenue.containsKey(key)) {
			AdminUser.monthlyRevenue.put(key, rev + AdminUser.monthlyRevenue.get(key));
		} else {
			AdminUser.monthlyRevenue.put(key, rev);
		}
	}

	/**
	 * Purchase a bus for the transit route. Busses have a price that will be
	 * subtracted from the revenue.
	 * 
	 * @param numBuses: The number of busses to purchase
	 */
	public static void purchaseBuses(int numBuses) {
		AdminUser.numBuses += numBuses;
		totalCost += numBuses * busPrice;
	}

	/**
	 * Add a new bus route to the existing routes
	 * 
	 * @param route to be added
	 */
	public static void createNewBusRoute(TransitRoutes route) {
		AdminUser.busRoutes.add(route);
	}

	/**
	 * Delete a bus route Precondition: This bus route already exists
	 * 
	 * @param route to be deleted
	 */
	public static void deleteNewBusRoute(TransitRoutes route) {
		AdminUser.busRoutes.remove(route);
	}

	/**
	 * Add a new location to the existing bus routes
	 * 
	 * @param loc: The location to be added
	 * @param route: The transit route to add to
	 * @param index: The position of the new route
	 */
	public static void addLocation(Location loc, TransitRoutes route, int index) {
		route.addLocationToRoute(loc, index);
	}

	/**
	 * Remove an existing location from a route Precondition: The loc exists
	 * 
	 * @param loc: The location be removed
	 * @param route: The corresponding route
	 */
	public static void removeLocation(Location loc, TransitRoutes route) {
		route.removeLocationFromRoute(loc);
	}

}
