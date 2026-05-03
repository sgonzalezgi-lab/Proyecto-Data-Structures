import java.util.Scanner;
import java.time.*;

public class Main {

	public static boolean consoleLog = true;

	public static Scanner scan;

	public static SportsSystem system;

	public static boolean running;

	public static void main(String[] args) {
		system = new SportsSystem();
		scan = new Scanner(System.in);

		consoleLog = true;


		system.addSport("volleyball");
		system.addSport("rugby");
		system.addSport("taekwondo");
		system.addSport("swimming");
		system.addSport("basketball");
		system.addSport("football");



		System.out.println("If you would like to start the diagnostics, type 1. For normal functioning, type anything else");
		int selection = Integer.parseInt(scan.nextLine().trim());

		if (selection == 1)
		{
			consoleLog = false;
			do
			{
				diagnosticsTest();
			} while (running);
			scan.close();
			return;
		}

		running = true;
		while (running)
		{

			optionMenu();

		}
		scan.close();
	}

	private static void printMenu() {
		//if (!consoleLog) return;

		System.out.println("\n======= UNAL Sports Network =======");
		System.out.println(" 1.  Register student");
		System.out.println(" 2.  Remove student");
		System.out.println(" 3.  Search student by ID");
		System.out.println(" 4.  List students by sport");
		System.out.println(" 5.  List sports by practitioner count");
		System.out.println(" 6.  Check connectivity (student ↔ sport)");
		System.out.println(" 7.  Show sport communities");
		System.out.println(" 8.  Add new sport");
		System.out.println(" 9.  Remove sport from system");
		System.out.println(" 10. Add sport to student");
		System.out.println(" 11. Remove sport from student");
		//System.out.println(" 12. Inorder data print");
		System.out.println(" 0.  Exit");
		System.out.print("Option: ");
	}

	private static void optionMenu()
	{
		printMenu();
		String choice = scan.nextLine().trim();

		switch (choice) {
		case "1":
			if (consoleLog) System.out.print("Name: ");
			String name = scan.nextLine().trim();

			if (consoleLog) System.out.print("ID: ");
			int id = Integer.parseInt(scan.nextLine().trim());

			system.createStudent(name, id, false);
			break;

		case "2":
			if (consoleLog) System.out.print("Student ID to remove: ");
			int removeId = Integer.parseInt(scan.nextLine().trim());
			system.removeStudent(removeId);
			break;

		case "3":
			if (consoleLog) System.out.print("Student ID: ");
			int searchId = Integer.parseInt(scan.nextLine().trim());
			system.searchStudent(searchId);
			break;

		case "4":
			if (consoleLog) System.out.print("Sport name: ");
			String sportForList = scan.nextLine().trim();
			system.printStudentsBySport(sportForList);
			break;

		case "5":
			system.printSportsByCount();
			break;

		case "6":
			if (consoleLog) System.out.print("Student ID: ");
			int connId = Integer.parseInt(scan.nextLine().trim());
			if (consoleLog) System.out.print("Sport to find connection to: ");
			String connSport = scan.nextLine().trim();
			system.isConnected(connId, connSport);
			break;

		case "7":
			system.buildCommunities();
			break;

		case "8":
			if (consoleLog) System.out.print("New sport name: ");
			String newSport = scan.nextLine().trim();
			system.addSport(newSport);
			break;

		case "9":
			if (consoleLog) System.out.print("Sport name to remove: ");
			String remSport = scan.nextLine().trim();
			system.removeSport(remSport);
			break;

		case "10":
			if (consoleLog) System.out.print("Student ID: ");
			int stuId = Integer.parseInt(scan.nextLine().trim());
			if (consoleLog) System.out.print("Sport to add: ");
			String addSp = scan.nextLine().trim();
			system.addSportToStudent(stuId, addSp);
			break;

		case "11":
			if (consoleLog) System.out.print("Student ID: ");
			int stuId2 = Integer.parseInt(scan.nextLine().trim());
			if (consoleLog) System.out.print("Sport to remove: ");
			String remSp = scan.nextLine().trim();
			system.removeSportFromStudent(stuId2, remSp);
			break;

		case "0":
			running = false;
			if (consoleLog) System.out.println("Goodbye.");
			break;

		default:
			if (consoleLog) System.out.println("Invalid option.");
		}

	}

	private static void diagnosticsTest()
	{
		DiagnosticsFunctions diagnostics = new DiagnosticsFunctions();
		diagnostics.initialize();

		System.out.println("Select the option to diagnose");
		printMenu();




		running = true;

		String choice = scan.nextLine().trim();

		if (Integer.parseInt(choice) == 0) {
			running = false;
			return;
		}


		System.out.println("Type the amount of test runs");
		int testRuns = Integer.parseInt(scan.nextLine().trim());

		long startTimer = 0;
		long endTimer = 0;

		long totalTimer = 0;

		for (int i = 0; i < testRuns; i++)
		{
			//Create details for a random student
			String name = diagnostics.randomNameAssign();
			int id = diagnostics.randomIDAssign();

			system.practicedSportsBuffer.clear();
			system.interestSportsBuffer.clear();

			int sportsCount = diagnostics.randomSportCount();

			for (int j = 0; j < sportsCount; j++) {
				system.practicedSportsBuffer.add(diagnostics.randomSport());
			}

			sportsCount = diagnostics.randomSportCount();

			for (int j = 0; j < sportsCount; j++) {
				system.interestSportsBuffer.add(diagnostics.randomSport());
			}

			switch (choice)
			{
			case "1":



				//Now, add the student


				startTimer = System.nanoTime();
				system.createStudent(name, id, true);
				endTimer = System.nanoTime();

				totalTimer += endTimer - startTimer;

				break;

			case "2":
				if (consoleLog) System.out.print("Student ID to remove: ");

				//Test method: Insert a random student, then remove them

				system.createStudent(name, id, true);


				startTimer = System.nanoTime();
				system.removeStudent(id);
				endTimer = System.nanoTime();

				totalTimer += endTimer - startTimer;
				break;

			case "3":
				if (consoleLog) System.out.print("Student ID: ");
				//Test method: Insert a random student, then search for them

				system.createStudent(name, id, true);


				startTimer = System.nanoTime();
				system.searchStudent(id);
				endTimer = System.nanoTime();

				totalTimer += endTimer - startTimer;
				break;

			case "4":
				if (consoleLog) System.out.print("Sport name: ");
				String sportForList = diagnostics.randomSport();

				startTimer = System.nanoTime();
				system.printStudentsBySport(sportForList);
				endTimer = System.nanoTime();

				totalTimer += endTimer - startTimer;
				break;

			case "5":
				startTimer = System.nanoTime();
				system.printSportsByCount();
				endTimer = System.nanoTime();

				totalTimer += endTimer - startTimer;
				break;

			case "6":
				//Create a student to check against
				system.createStudent(name, id, true);

				if (consoleLog) System.out.print("Student ID: ");
				int connId = id;
				if (consoleLog) System.out.print("Sport to find connection to: ");
				String connSport = diagnostics.randomSport();



				startTimer = System.nanoTime();
				system.isConnected(connId, connSport);
				endTimer = System.nanoTime();

				totalTimer += endTimer - startTimer;
				break;

			case "7":
				startTimer = System.nanoTime();
				system.buildCommunities();
				endTimer = System.nanoTime();

				totalTimer += endTimer - startTimer;
				break;

			case "8":
				if (consoleLog) System.out.print("New sport name: ");
				int randomSequence = diagnostics.randomIDAssign(); //The name doesn't matter, only it being a new sport
				String newSport = Integer.toString(randomSequence);

				startTimer = System.nanoTime();
				system.addSport(newSport);
				endTimer = System.nanoTime();

				totalTimer += endTimer - startTimer;
				break;

			case "9":
				if (consoleLog) System.out.print("Sport name to remove: ");
				String remSport = diagnostics.randomSport();

				startTimer = System.nanoTime();
				system.removeSport(remSport);
				endTimer = System.nanoTime();

				totalTimer += endTimer - startTimer;
				break;

			case "10":

				//Create a student to check against
				system.createStudent(name, id, true);

				if (consoleLog) System.out.print("Student ID: ");
				int stuId = id;
				if (consoleLog) System.out.print("Sport to add: ");
				String addSp = diagnostics.randomSport();

				startTimer = System.nanoTime();
				system.addSportToStudent(stuId, addSp);
				endTimer = System.nanoTime();

				totalTimer += endTimer - startTimer;

				break;

			case "11":
			    //Create a student to check against
                system.createStudent(name, id, true);

				if (consoleLog) System.out.print("Student ID: ");
				int stuId2 = id;
				if (consoleLog) System.out.print("Sport to remove: ");
				String remSp = diagnostics.randomSport();

				startTimer = System.nanoTime();
				system.removeSportFromStudent(stuId2, remSp);
				endTimer = System.nanoTime();

				totalTimer += endTimer - startTimer;
				break;

			case "0":
				running = false;
				if (consoleLog) System.out.println("Goodbye.");
				break;

			default:
				if (consoleLog) System.out.println("Invalid option.");
			}
		}

		System.out.println("Total Execution Time: " + totalTimer/1000f + " microseconds");
		System.out.println("Average Execution Time: " + totalTimer/testRuns + " ns");


	}
}
