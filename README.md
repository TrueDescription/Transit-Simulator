=====
INSTRUCTIONS
=====

To run our program, please run Main.java as an application.

Text File Formatting:

***NOTE: For all text files in resources please ensure that they all have only 1 empty string line at the end of the file. Otherwise the loaders in StartUp.java will error and the GUI will not run.
***IMPORTANT NOTE:  If you call methods separate from the GUI (within a main method), please reset txt files to originals, we do not always write to file within the individual methods, writing is a feature of the GUI that some methods implement for ease of coding.

Cards.txt:
The format of Cards.txt: email,balance,card-id,isactived,YYYY-MM-DD
(e.g.) hello@gmail.com,10,12,true,2020-11-16
You can add cards to a certain card holder in the GUI by signing into a user and adding a card in the user functions menu, but, if you would like to add it manually for the GUI to use you may write it directly into the text file but know that if you are adding a card then the email provided must be associated with a card holder so you will have to write a card holder with the email you have written for the card in the way specified in CardHolders.txt below.

CardHolders.txt:
The format for CardHolders.txt: name,email
(e.g.) Marcus Man,hello@gmail.com
You can make a new card holder directly in the GUI by signing up. This will save the data in CardHolders.txt and Cards.txt (Cards.txt is modified because every user starts off with a card set to a default balance of $19.00). If you would like to add a card directly in the CardHolders.txt you may do so by adding in an extra line following the format described above. Please make sure that the person you have added as a corresponding card in Cards.txt.

events.txt :
The format for events.txt: 
tapOn/tapOff,(! Or ?)Location Name,card-id,Year(YYYY),Month(MM),Day(DD),Hour,Min,email
(e.g.) tapOn,?Chung Chung Go,1,2020,11,16,16,0,parlefrancais@gmail.com

Every line in events.txt represents a singular event (tap on or tap off). If there is a “!” in front of the location name this represents a station and if there is a “?” in front of the location name this represents a bus stop. The rest of the line from here represents the card-id that tapped on or off, the date the event occurred, the time of which the event occurred, and finally the email of the user that made the action.


BusRoutes.txt:
The format for BusRoutes.txt: Route name,Stop 1,hour,minute,Stop 2,hour,minute,Stop 3,hour,minute (etc..)
Where hour,minute represents the time at which the bus is scheduled to come
(e.g.) Union Route,Toronto Plaza,10,10,Woodchester,10,35,Port Credit,12,00,Clarkson,12,25

StationRoutes.txt:
The format for StationRoute.txt: Route name,Station 1,hour,minute,Station 2,hour,minute,Station 3,hour,minute (etc..)
Where hour,minute represents the time at which the subway is scheduled to come
(e.g.) ON Provincial Route,Clarkson,10,12,Faisal Go,10,25,Nick Go,10,55

Stations.txt:
The format for Stations.txt: StationName,isAtInjuction
The first parameter represents the name, and the second parameter (all lower case) is a boolean indicate if it is an injunction (i.e. crossing another route)
(e.g.) Clarkson,true

BusStops.txt:
The format for BusStops.txt: Stop Name,is at injunction
(e.g) Toronto Plaza,false
The first parameter represents the name of the stop and the second parameter is a boolean that indicates if the intersects at a station

Settings.txt:
The format for Settings.txt: parameter:#
Where parameter is one of the following: BusFare, StationFare, Minute Grace Period, Max Cost
# is a decimal number (e.g. 2.0)
(e.g.) BusFare:2.0

=====
LIST OF ALL FEATURES 
=====

Before all features, launch Main.java to initialize gui.

(Describe any decisions you made in the organization of code relevant to this feature. If any OOP principles, SOLID principles or design patterns were used, clearly highlight this here).

FEATURE 1


Description: Loading and Writing to file

Usage Instructions: When using anything within the GUI that requires the information to be written, this will happen automatically. Please refer to the config files above if you want to change any info that is loaded in, i.e. in settings.txt you can define things like BusFare, StationFare, Minute Grace Period, Max Cost. Ensure that the format stated above is met and the program will run without issues. 

Design Decisions: This was done for the sake of being able to close the program and opening it without any data being lost. Note: when using the program, LocalDateTime.now() is written to file. 


FEATURE 2

Description: Tapping On or Off (Both bus and station)

Usage Instructions: Log In to the GUI with a name and email that exist within the CardHolders.txt in the Resources file in the root of the a2 file alongside FEATURES.txt. For instance, you may login as “Zach Lavine” with the email “zachtt@nba.com”. Choose a starting location and choose an ACTIVE card. Note: if all the cards of the CardHolder are suspended/inactive, they will not show up. Then select "Start Trip" button. Once pressed the GUI will render and it will display a dropdown list of all possible destinations that are on the corresponding route of that location (If the starting location you choose does not lead anywhere i.e. is at the end of a bus route). Then select a location you wish to travel to and click "Tap On". This will render the GUI accordingly and allow you to tap off. The trip is continuous and you will be able to continue choosing locations on the route that the location you are at is apart of. At any time you may choose to end trip.

Design Decisions: If you tap on a bus your balance will be charged immedietly. Thus tapping off a bus is irrelevent other then updating information in the Trip object. If you tap off a bus without tapping on, this case is irrelevent since the GUI requires the person to tap on before they can ever tap off, and the balance is chareged when tapping off. If you tap on a subway and don't tap off, this will charge the card the max cost defined. The ladder will also occur if you tap off without tapping on. 


FEATURE 3

Description: A trip is continous

Usage Instructions: As mentioned before, once you start a trip, you will be able to go from one location to another given that there exists a path between routes such that you can go from a location on one route to a location on another. 

Design Decisions: If you choose a starting location that is a bus, you will only be given options of possible destinations that are bus routes. The same for stations. this was done to ensure that a person isn't tapping on a bus then tapping of a station in the GUI.


FEATURE 4

Description: Changing or viewing CardHolder data within GUI 

Usage Instructions: Please navigate to the user functions page by logging in if not already logged in, navigate to the first page where you can choose your card and starting location and click "User Functions". That will load a scene where you will be able to add balance in increments of 10,20,50 to the chosen card as well as changing the name of the card holder and suspending or activating a card. Here, the CardHolder can also view the three most recent trips that are on file. Once finished with user functions, click "Begin Trip" button to return to the page where you can choose an active card and starting location. Navigate as desired. 

Design Decisions: If no cards are active, there will not exist any cards in the dropdown list to add balance to. 


FEATURE 5

Description: Negative Card Balance

Usage Instructions: go on a trip in the GUI

Design Decisions: During the trip section of the GUI. If the tap on or tap off would cause the card balance to go negative, then you won't be able to tap on another bus or station.

FEATURE 6

Description: Signing up as a new user

Usage Instructions: When you first launch the GUI, input a name and email that must include "@" and click sign up

Design Decisions: An email is unique, thus the gui will not allow you to sign up if the email is already assigned to a user in CardHolders.Txt. If the CardHolder is successfully registered, the cardholder will be assigned a new card with a balance of $19 and the card and cardholder are written to file

FEATURE 7

Description: Admin User Functions 

Usage Instructions: On the login page when starting the GUI, type "Teaching Assistant" in the admin ID text field and click "Admin Log In" (Other login ID's won't work!). This will direct you to a page where the adminuser can choose from a list of adminuser functions of the following. Display daily report, set bus fare and station fare. Once selected click go. At any time click back to return to the previous page. if you selected Daily report, it will ask for a date in the format YYYY-MM-DD, to get a date that had revenue, refer to the cards.txt file and choose a date. if the other two functions are chosen, input the fare you wish to set and click change. This will write to file settings.txt.

Design Decisions: Daily report, we are using a decimal format that will always display two decimal places. Daily report revenue shows the total ammount of revenue of a given date. Revenue is a total of all card purchasing (i.e. signing up) and adding balances to the card on the date specified when navigating to the report. The daily report also shows the number of rides on that date, the total current bus and subway routes, and fares that are set.


...




===========
OTHER NOTES
===========

You may purchase a bus using the purchaseBus method, create a new bus route using createNewBusRoute, delete a bus route using deleteNewBusRoute, add a location using addLocation and remove a location using removeLocation all in AdminUser.java (Please note: these are not GUI features)


Design choices:
OOP based. We have over 20 classes that interact with each other.
Inheritance: Stop and Station are both children of Location.java (who has a some abstract classes)
Data structures: We use several data structures in our program such as ArrayLists, HashMaps, ComboBox, ObservableLists
GUI: We decided to have a working graphical user interface instead of a text/console based program. We wanted it to be a better experience for the user. We used grid panes, stack panes, VBoxes, HBoxes, Buttons, Labels, TextFields, TextAreas, ComboBox, Scenes, ImageView, Action Listeners
Getter / Setter methods: instead of accessing attributes directly, we implemented getter and setters methods in our classes for a better overall design
EventHandler files: we have 10 event handlers for the sake of modularity and so that we can better debugg out event handlers when we notice something went wrong
Loader methods: These read and load our data from our text files to the program
Writer methods: In order to always have the most up to date data, we overwrite our text files to keep our data when using the GUI

Architecture design explanation:
-Firstly, we have Location object that is an abstract class the defines the common functionality between Stop and Station objects. This was done because the objects Stop and Station had very similar functionality.
-TransitRoutes object defines the route for all bus routes and the only subway route. This was done for the intention of extensibility in the case that the municipality that this program is done for decides to build more than one subway route.
-The third major aspect of our program are the classes CardHolder.java Card.java and Trip.java
  -Trip.java holds and contains all data pertaining to a trip, i.e. the static variables defined for max cost of a trip and the grace period set and the max ride time.
  -Card.java holds data i.e. balance, if the card is activated, card id, time of initialization of the card
    -Card.java also has a static variable CARDS_ISSUED that is used in the constructor to ensure every card has a unique identifier
- StartUp.java contains methods that loads everthing from the files defined when StartUp.main() is called.
- Writter.java contains methods that remove and write things like cards, events, cardholders etc.










