package transitapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class writes existing data in their corresponding text file.
 */
public class Writer {

	/**
	 * This method writes information about a CardHolders to CardHolder.txt.
	 * 
	 * @param client CardHolder representing the information to write to
	 *               CardHolder.txt
	 * @throws IOException
	 */
	public static void writeCardHolder(CardHolder client) throws IOException {
		File cardHolderFile = new File("Resources/CardHolders.txt");
		FileWriter writeCardHolders = new FileWriter(cardHolderFile, true);
		PrintWriter pw = new PrintWriter(writeCardHolders);
		pw.println(client.getName() + "," + client.getEmail());
		pw.close();
		writeCardHolders.close();
	}

	/**
	 * This method writes information about a Card to Card.txt
	 * 
	 * @param email   String representing the email that owns this card
	 * @param balance String representing the money in the card
	 * @param id      String representing the id for the card
	 * @param active  boolean representing whether the card is active or not
	 * @param date    LocatDate representing when the card was issued
	 * @throws IOException
	 */
	public static void writeCard(String email, String balance, String id, boolean active, LocalDate date)
			throws IOException {
		File cardFile = new File("Resources/Cards.txt");
		FileWriter writeCard = new FileWriter(cardFile, true);
		PrintWriter pw = new PrintWriter(writeCard);
		pw.println(email + "," + balance + "," + id + "," + Boolean.toString(active) + ","
				+ Integer.toString(date.getYear()) + "-" + Integer.toString(date.getMonthValue()) + "-"
				+ Integer.toString(date.getDayOfMonth()));
		pw.close();
		writeCard.close();

	}

	/**
	 * This method writes information of a busStop to BusStop.txt
	 * 
	 * @param busStop Stop representing the busStop to be written
	 * @throws IOException
	 */
	public static void writeBusStop(Stop busStop) throws IOException {
		File busStopFile = new File("Resources/BusStops.txt");
		FileWriter writeBusStop = new FileWriter(busStopFile, true);
		PrintWriter pw = new PrintWriter(writeBusStop);
		pw.println(busStop.getLocation() + "," + busStop.getAtInjuction());
		pw.close();
		writeBusStop.close();

	}

	/**
	 * This method writes information of a station to Station.txt
	 * 
	 * @param station Station representing the station to be written
	 * @throws IOException
	 */
	public static void writeStation(Station station) throws IOException {
		File busStationsFile = new File("Resources/Stations.txt");
		FileWriter writeStationStop = new FileWriter(busStationsFile, true);
		PrintWriter pw = new PrintWriter(writeStationStop);
		pw.println(station.getLocation() + "," + station.getAtInjuction());
		pw.close();
		writeStationStop.close();
	}

	/**
	 * This method writes information of a bus route to BusRoutes.txt
	 * 
	 * @param route TransitRoutes representing the bus route to be written
	 * @throws IOException
	 */
	public static void writesBusRoute(TransitRoutes route) throws IOException {

		File busRoutesFile = new File("Resources/BusRoutes.txt");
		FileWriter writeBusRoutes = new FileWriter(busRoutesFile, true);
		PrintWriter pw = new PrintWriter(writeBusRoutes);
		ArrayList<Location> routesList = route.getRoute();
		ArrayList<LocalTime> schedule = route.getSchedule();
		String line = route.getName();
		for (int i = 1; i < routesList.size(); i++) {
			line = line + "," + routesList.get(i).getLocation() + ",";
			line = line + Integer.toString(schedule.get(i).getHour()) + ","
					+ Integer.toString(schedule.get(i).getMinute());
		}
		pw.println(line);
		pw.close();
		writeBusRoutes.close();
	}

	/**
	 * This method writes information of a subway route to StationRoutes.txt
	 * 
	 * @param route TransitRoutes representing the subway route to be written
	 * @throws IOException
	 */
	public static void writeSubwayRoute(TransitRoutes route) throws IOException {
		File subwayRoutesFile = new File("Resources/StationRoutes.txt");
		FileWriter writeSubwayRoutes = new FileWriter(subwayRoutesFile, true);
		PrintWriter pw = new PrintWriter(writeSubwayRoutes);
		ArrayList<Location> routesList = route.getRoute();
		ArrayList<LocalTime> schedule = route.getSchedule();
		String line = route.getName();
		for (int i = 0; i < routesList.size(); i++) {
			line = line + "," + routesList.get(i).getLocation() + ",";
			line = line + Integer.toString(schedule.get(i).getHour()) + ","
					+ Integer.toString(schedule.get(i).getMinute());
		}
		pw.println(line);
		pw.close();
		writeSubwayRoutes.close();
	}

	/**
	 * This method writes fares to Settings.txt. If indication is true, write the
	 * bus fare. Otherwise, write the subway fare.
	 * 
	 * @param fare       double fare representing fare
	 * @param indication boolean representing which fare to be written
	 * @throws IOException
	 */
	public static void writeFare(double fare, boolean indication) throws IOException {
		File settingsFile = new File("Resources/Settings.txt");
		FileWriter writeSettingsFile = new FileWriter(settingsFile);
		PrintWriter pw = new PrintWriter(writeSettingsFile);
		if (indication == true) {
			pw.println("BusFare:" + Double.toString(fare));
			pw.println("StationFare:" + Double.toString(TransitRoutes.getSubwayFare()));
		} else {
			pw.println("BusFare:" + Double.toString(TransitRoutes.getBusFare()));
			pw.println("StationFare:" + Double.toString(fare));
		}

		pw.println("Minute Grace Period:" + Integer.toString(Trip.MINUTE_GRACE_PERIOD));
		pw.println("Max Cost:" + Double.toString(Trip.MAX_COST));
		pw.close();
		writeSettingsFile.close();
	}

	/**
	 * This method removes the specified CardHolder (client) from CardHolder.txt
	 * 
	 * @param client CardHolder representing the client to be removed
	 * @throws IOException
	 */
	public static void removeCardHolder(CardHolder client) throws IOException {
		StartUp.cardHolders.remove(client.getEmail());
		FileWriter fw = new FileWriter("Resources/CardHolders.txt", false);
		PrintWriter pw = new PrintWriter(fw);
		pw.close();
		fw.close();
		for (String key : StartUp.cardHolders.keySet()) {
			writeCardHolder(StartUp.cardHolders.get(key));
		}

	}

	/**
	 * This method removes the specified Card (c) owned by CardHolder (client) from
	 * Cards.txt
	 * 
	 * @param c      Card representing the card to be removed
	 * @param client CardHolder representing the CardHolder that own Card (c)
	 * @throws IOException
	 */
	public static void removeCard(Card c, CardHolder client) throws IOException {
		StartUp.cards.get(client.getEmail()).remove(c);
		FileWriter fw2 = new FileWriter("Resources/Cards.txt", false);
		PrintWriter pw2 = new PrintWriter(fw2);
		pw2.close();
		fw2.close();
		for (String key : StartUp.cards.keySet()) {
			for (Card card : StartUp.cards.get(key)) {
				writeCard(key, Double.toString(card.getBalance()), Integer.toString(card.getCard_id()),
						card.isActivated(), card.getTimeInitialized());
			}
		}

	}

	/**
	 * This method removes the specified busStop from BusStop.txt
	 * 
	 * @param busStop Stop representing the busStop to be removed
	 * @throws IOException
	 */
	public static void removeBusStop(Stop busStop) throws IOException {
		StartUp.stops.remove(busStop.getLocation());
		FileWriter fw = new FileWriter("Resources/BusStops.txt", false);
		PrintWriter pw = new PrintWriter(fw);
		pw.close();
		fw.close();
		for (String key : StartUp.stops.keySet()) {
			writeBusStop(StartUp.stops.get(key));
		}
	}

	/**
	 * This method removes the specified station from Stations.txt
	 * 
	 * @param station Station representing the station to be removed
	 * @throws IOException
	 */
	public static void removeStation(Station station) throws IOException {
		StartUp.stations.remove(station.getLocation());
		FileWriter fw = new FileWriter("Resources/Stations.txt", false);
		PrintWriter pw = new PrintWriter(fw);
		pw.close();
		fw.close();
		for (String key : StartUp.stations.keySet()) {
			writeStation(StartUp.stations.get(key));
		}
	}

	/**
	 * This method removes the specified bus route from BusRoutes.txt
	 * 
	 * @param busRoute TransitRoutes representing the bus route to be removed
	 * @throws IOException
	 */
	public static void removeBusRoute(TransitRoutes busRoute) throws IOException {

		StartUp.busRoutes.remove(busRoute);
		FileWriter fw = new FileWriter("Resources/BusRoutes.txt", false);
		PrintWriter pw = new PrintWriter(fw);
		pw.close();
		fw.close();
		for (TransitRoutes route : StartUp.busRoutes) {
			writesBusRoute(route);
		}
	}

	/**
	 * This method removes the specified subway route from StationRoute.txt
	 * 
	 * @param subwayRoute TransitRoutes representing the subway route to be removed
	 * @throws IOException
	 */
	public static void removeSubwayRoute(TransitRoutes subwayRoute) throws IOException {
		StartUp.subwayRoutes.remove(subwayRoute);
		FileWriter fw = new FileWriter("Resources/StationRoutes.txt", false);
		PrintWriter pw = new PrintWriter(fw);
		pw.close();
		fw.close();
		for (TransitRoutes route : StartUp.subwayRoutes) {
			writeSubwayRoute(route);
		}
	}

	/**
	 * This method writes when a tapOn or tapOff event occurs to events.txt
	 * 
	 * @param tap      String representing tapOn or tapOff
	 * @param location String representing the name of the location where the event
	 *                 occurred
	 * @param card_id  int representing the card_id used to tapOn and tapOff
	 * @param time     LocalDateTime representing the when the event occurred
	 * @param email    String representing the email the CardHolder authorizing the
	 *                 event
	 * @throws IOException
	 */
	public static void writeEvent(String tap, String location, int card_id, LocalDateTime time, String email)
			throws IOException {
		File eventFile = new File("Resources/events.txt");
		FileWriter writeEvent = new FileWriter(eventFile, true);
		PrintWriter pw = new PrintWriter(writeEvent);
		String line = tap + "," + location + "," + +card_id + "," + time.getYear() + "," + time.getMonthValue() + ","
				+ time.getDayOfMonth() + "," + time.getHour() + "," + time.getMinute() + "," + email;

		pw.println(line);
		pw.close();

	}

}
