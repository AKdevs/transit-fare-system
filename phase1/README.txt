/***********************************************************************************************
**
**                                   CSC 207, 2018 Summer
**                              Project: Transit System, Phase 1
**                                     By: Group 0134
**
***********************************************************************************************/

Transit System

Nowadays, many cities have introduced travel card to replace cash fare and paper tickets in public transportation. It has been well adopted by residents of cities in Asia, such as Beijing, Hong Kong, and Tokyo, where passengers tap their travel cards to a reader when entering and exiting a bus or subway station. Our program will not only process payment transactions as they happen, but also allow passengers to view usage details of their cards and provide information to transit admin users for cost analysis.

The development of the program is divided into 2 phases. In Phase 1, we are building the core backend of the program. In Phase 2, we will add graphical user interface (GUI) for more user-friendly access.


Key Features

	- Create, store, and remove user account
	- Create, store, and remove travel card
	- Create, update, and store map of transit system 
	- Calculate fare amount based on transportation type
	- Deduct fare amount from card balance
	- Load money to card
	- View recent trips travelled with certain card
	- View average monthly cost across cards possessed by a user
	- View total number of bus stops and subway stations reached by passengers each day
	- View total amount of fare collected each day 


Setup

For Phase 1, no installation is required for the program. The program will execute a list of events defined in ‘events.txt’. This file should be placed in the same folder as the class codes - ~/src/

	EventHandler(TransitSystem system) throws Exception {
		this.system = system;
		this.eventsBuffer = new Scanner(new File("events.txt"));
	}

Each line of ‘events.txt’ contains one single event. A specific format, “action|field1|field2|...|fieldn”, is to be used when drafting an event. The keyword and convention for each type of supported event is as following:

	Entry into a subway station / board a bus
	Format: eventType | cardNumber | name of station/stop | transportationType | time | date
	Example: “entry | 1204 | Bloor | S | 10:49:11 | 2018-02-11” 
		*Time is in 24-hour standard format

	Exit from a bus / subway station
	Format: eventType | cardNumber | name of station/stop | transportationType | time | date
	Example: “exit | 1204 | Bloor |  B | 10:49:11 | 2018-02-11” 
	*Time is in 24-hour standard format

	UserAccount Creation
	Format: eventType1 | eventType2 | name | e-mail
	Example: “create | account | James | james@gmail.com”

	Card creation/registration/purchase
	Format: eventType1 | eventType2
	Example: “create | card”
		*Card is not linked to any user upon creation

	Activate Card
	Format: eventType  | cardNumber
	Example: “activate | 1204”
		*This action can only be applied to any inactive card

	Deactivate Card
	Format: eventType | accountNumber | cardNumber
	Example: “deactivate | 18 | 1204”
		*This action can only be applied to any active card

	View Account Information
	Format: eventType1 | eventType2 | accountNumber
	Example: “view | info | 18”

	View Recent Trips
	Format: eventType1 | eventType2 | cardNumber
	Example: “view | trips | 1204”

	View Card Balance
	Format: eventType1 | eventType2 | cardNumber
	Example: “view | balance | 1204”

	View Average Cost per Month
	Format: eventType1 | eventType2 | accountNumber
	Example: “view | cost | 18”

	Change UserAccount Name
	Format: eventType | accountNumber | newName
	Example: “change | 18 | David”

	Remove Card
	Format: eventType1 | eventType2 | cardNumber
	Example: “remove | card | 1204”

	Remove UserAccount
	Format: eventType1 | eventType2 | accountNumber
	Example: “remove | account | 18”

	Load Money to Card (Add Balance)
	Format: eventType | cardNumber | Amount
	Example: “load | 1204 | 20” 

	Link Card to UserAccount
	Format: eventType | accountNumber | cardNumber 
	Example: “link | 18 | 1204”

	Unlink Card from UserAccount
	Format: eventType | accountNumber | cardNumber 
	Example: “unlink | 18 | 1204”

	AdminUser - Generate Daily Report
	Format: eventType1 | eventType2
	Example: “view | report”


How to Run

The main method of this program is defined in ‘EventHandler.java’. You could run ‘EventHandler.java’ in IntelliJ IDEA.

	public class EventHandler {
		private TransitSystem system;
		private Scanner eventsBuffer;

		public static void main(String[] args) throws Exception {
		TransitSystem mainSystem = new TransitSystem();
		EventHandler mainHandler = new EventHandler(mainSystem);
		for (int i = 0; i < 3; i++) {
			mainHandler.play();
		}
		System.out.println(mainSystem.getCards());
		System.out.println(mainSystem.getUserAccounts());
		}


Built With
	JAVA 8


Version Control
	Git repository: https://markus.teach.cs.toronto.edu/git/csc207-2018-05/group_0134


Developed by:
	Atharva Milind Karandikar
	Jingjing Zhan
	Wenyue Feng
	Yang Chen
