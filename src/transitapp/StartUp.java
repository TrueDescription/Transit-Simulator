package transitapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class StartUp {

	public static HashMap<String, Stop> stops = new HashMap<String, Stop>();
	public static HashMap<String, Station> stations = new HashMap<String, Station>();
	public static ArrayList<TransitRoutes> busRoutes = new ArrayList<TransitRoutes>();
	public static ArrayList<TransitRoutes> subwayRoutes = new ArrayList<TransitRoutes>();
	public static HashMap<String, CardHolder> cardHolders = new HashMap<String, CardHolder>();
	public static HashMap<String, ArrayList<Card>> cards = new HashMap<String, ArrayList<Card>>();

	public static void main() throws IOException {
		stops = loadStops();
		stations = loadStation();
		busRoutes = loadBusRoutes();
		subwayRoutes = loadSubwayRoute();
		cards = loadCards();
		cardHolders = loadCardHolders(cards);

		loadSettings();
		loadEvents(cardHolders, stops, stations);
	}

	/**
	 * Load the bus stops from the file
	 * 
	 * @return a HashMap of the stops, keyed by name
	 * @throws IOException
	 */
	public static HashMap<String, Stop> loadStops() throws IOException {
		HashMap<String, Stop> stops = new HashMap<String, Stop>();
		BufferedReader fileStops = new BufferedReader(new FileReader("Resources/BusStops.txt"));
		Scanner scanStops = new Scanner(fileStops);

		while (scanStops.hasNextLine()) {
			String line = scanStops.nextLine();
			String[] data = line.split(",");
			stops.put(data[0], new Stop(data[0], Boolean.parseBoolean(data[1])));
		}
		fileStops.close();
		scanStops.close();
		return stops;
	}

	/**
	 * Load the subway stations from the file
	 * 
	 * @return a HashMap of the stations, keyed by name
	 * @throws IOException
	 */
	public static HashMap<String, Station> loadStation() throws IOException {
		HashMap<String, Station> stations = new HashMap<String, Station>();
		BufferedReader fileStations = new BufferedReader(new FileReader("Resources/Stations.txt"));
		Scanner scanStations = new Scanner(fileStations);

		while (scanStations.hasNextLine()) {
			String line = scanStations.nextLine();
			String[] data = line.split(",");
			stations.put(data[0], new Station(data[0], Boolean.parseBoolean(data[1])));
		}

		fileStations.close();
		scanStations.close();
		return stations;
	}

	/**
	 * Load the bus routes from the file
	 * 
	 * @return an ArrayList with the bus routes
	 * @throws IOException
	 */
	public static ArrayList<TransitRoutes> loadBusRoutes() throws IOException {
		ArrayList<TransitRoutes> busRoutes = new ArrayList<TransitRoutes>();
		BufferedReader fileBusRoutes = new BufferedReader(new FileReader("Resources/BusRoutes.txt"));
		Scanner scanBusRoutes = new Scanner(fileBusRoutes);
		HashMap<String, Stop> stops = loadStops();

		while (scanBusRoutes.hasNextLine()) {
			String line = scanBusRoutes.nextLine();
			ArrayList<String> data = new ArrayList<String>(Arrays.asList(line.split(",")));
			ArrayList<Location> destinations = new ArrayList<Location>();
			ArrayList<LocalTime> schedule = new ArrayList<LocalTime>();

			for (int i = 1; i < data.size(); i += 3) {
				destinations.add(stops.get(data.get(i)));
				int hour = Integer.parseInt(data.get(i + 1));
				int min = Integer.parseInt(data.get(i + 2));
				schedule.add(LocalTime.of(hour, min));
			}
			TransitRoutes r = new TransitRoutes(data.get(0), destinations, schedule);
			busRoutes.add(r);
		}
		fileBusRoutes.close();
		scanBusRoutes.close();
		return busRoutes;
	}

	/**
	 * Load the subway route from the file
	 * 
	 * @return An ArrayList containing (only) the subway route
	 * @throws IOException
	 */
	public static ArrayList<TransitRoutes> loadSubwayRoute() throws IOException {
		ArrayList<TransitRoutes> subwayRoutes = new ArrayList<TransitRoutes>();
		BufferedReader fileBusRoutes = new BufferedReader(new FileReader("Resources/StationRoutes.txt"));
		Scanner scanStationRoutes = new Scanner(fileBusRoutes);
		HashMap<String, Station> stops = loadStation();

		while (scanStationRoutes.hasNextLine()) {
			String line = scanStationRoutes.nextLine();
			ArrayList<String> data = new ArrayList<String>(Arrays.asList(line.split(",")));
			ArrayList<Location> destinations = new ArrayList<Location>();
			ArrayList<LocalTime> schedule = new ArrayList<LocalTime>();

			for (int i = 1; i < data.size(); i += 3) {
				destinations.add(stops.get(data.get(i)));
				int hour = Integer.parseInt(data.get(i + 1));
				int min = Integer.parseInt(data.get(i + 2));
				schedule.add(LocalTime.of(hour, min));
			}
			subwayRoutes.add(new TransitRoutes(data.get(0), destinations, schedule));
		}
		fileBusRoutes.close();
		scanStationRoutes.close();
		return subwayRoutes;
	}

	/**
	 * Load the card holders from the file
	 * 
	 * @return A HashMap of CardHolders, keyed by name
	 * @throws IOException
	 */
	public static HashMap<String, CardHolder> loadCardHolders(HashMap<String, ArrayList<Card>> updatedCards)
			throws IOException {
		loadSettings();
		HashMap<String, CardHolder> cardHolders = new HashMap<String, CardHolder>();
		BufferedReader fileCardHolders = new BufferedReader(new FileReader("Resources/CardHolders.txt"));
		Scanner scanCardHolders = new Scanner(fileCardHolders);

		while (scanCardHolders.hasNextLine()) {
			String line = scanCardHolders.nextLine();
			String[] data = line.split(",");
			cardHolders.put(data[1], new CardHolder(data[0], data[1], false));
		}

		for (String key : updatedCards.keySet()) {
			for (int i = 0; i < updatedCards.get(key).size(); i++) {
				cardHolders.get(key).addCard(updatedCards.get(key).get(i));
			}
		}
		fileCardHolders.close();
		scanCardHolders.close();
		return cardHolders;
	}

	/**
	 * Load the cards from the file
	 * 
	 * @return A HashMap of the cards
	 * @throws IOException
	 */
	public static HashMap<String, ArrayList<Card>> loadCards() throws IOException {
		HashMap<String, ArrayList<Card>> cards = new HashMap<String, ArrayList<Card>>();
		BufferedReader fileCards = new BufferedReader(new FileReader("Resources/Cards.txt"));
		Scanner scanCards = new Scanner(fileCards);

		while (scanCards.hasNextLine()) {
			String line = scanCards.nextLine();
			String[] data = line.split(",");
			String[] dateInfo = data[4].split("-");
			Card c = new Card(Double.parseDouble(data[1]), Integer.parseInt(data[2]), LocalDate
					.of(Integer.parseInt(dateInfo[0]), Integer.parseInt(dateInfo[1]), Integer.parseInt(dateInfo[2])));
			if (data[3].equals("true")) {
				c.activate();
			} else {
				c.desactivate();
			}

			if (cards.containsKey(data[0])) {
				cards.get(data[0]).add(c);
			} else {
				cards.put(data[0], new ArrayList<Card>(Arrays.asList(c)));
			}
		}
		fileCards.close();
		scanCards.close();
		return cards;

	}

	/**
	 * Load the events that have already been done
	 * 
	 * @param cardHolders A HashMap of the existing card holders
	 * @param stops       A HashMap of the existing bus stops
	 * @param stations    A HashMap of the existing subway stations
	 * @throws IOException
	 */
	public static void loadEvents(HashMap<String, CardHolder> cardHolders, HashMap<String, Stop> stops,
			HashMap<String, Station> stations) throws IOException {

		BufferedReader fileEvents = new BufferedReader(new FileReader("Resources/events.txt"));
		Scanner scanEvents = new Scanner(fileEvents);

		while (scanEvents.hasNextLine()) {
			String line = scanEvents.nextLine();
			ArrayList<String> data = new ArrayList<String>(Arrays.asList(line.split(",")));
			Location location;

			if (data.get(1).charAt(0) == '!') {
				location = stations.get(data.get(1).substring(1));
			} else {
				location = stops.get(data.get(1).substring(1));
			}


			CardHolder cardHolder = cardHolders.get(data.get(8));
			int cardID = Integer.parseInt(data.get(2));

			LocalDateTime time = LocalDateTime.of(Integer.parseInt(data.get(3)), Integer.parseInt(data.get(4)),
					Integer.parseInt(data.get(5)), Integer.parseInt(data.get(6)), Integer.parseInt(data.get(7)));
			if (data.get(0).equals("tapOn")) {
				cardHolder.tapOn(location, cardID, time, true);
			} else {
				cardHolder.tapOff(location, cardID, time, true);
			}

		}
		fileEvents.close();
		scanEvents.close();

	}

	/**
	 * Load the parameters containing the BusFare, StationFare, Minute Grace Period
	 * and Max Cost
	 * 
	 * @throws IOException
	 */
	public static void loadSettings() throws IOException {
		BufferedReader fileSettings = new BufferedReader(new FileReader("Resources/Settings.txt"));
		Scanner scanSettings = new Scanner(fileSettings);

		while (scanSettings.hasNextLine()) {
			String line = scanSettings.nextLine();
			String[] data = line.split(":");
			if (data[0].equals("BusFare")) {
				TransitRoutes.setBusFare(Double.parseDouble(data[1]));
			} else if (data[0].equals("StationFare")) {
				TransitRoutes.setSubwayFare(Double.parseDouble(data[1]));
			} else if (data[0].equals("Minute Grace Period")) {
				Trip.MINUTE_GRACE_PERIOD = Integer.parseInt(data[1]);
			} else if (data[0].equals("Max Cost")) {
				Trip.MAX_COST = Double.parseDouble(data[1]);
			}

		}
		fileSettings.close();
		scanSettings.close();
	}

}
