import java.util.Scanner;
 
public class Main {
 
    public static void main(String[] args) {
        SportsSystem system = new SportsSystem();
        Scanner scan = new Scanner(System.in);
 
        
        system.addSport("volleyball");
        system.addSport("rugby");
        system.addSport("taekwondo");
        system.addSport("swimming");
        system.addSport("basketball");
        system.addSport("football");
 
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scan.nextLine().trim();
 
            switch (choice) {
                case "1":
                    System.out.print("Name: ");
                    String name = scan.nextLine().trim();
                    System.out.print("ID: ");
                    int id = Integer.parseInt(scan.nextLine().trim());
                    system.createStudent(name, id);
                    break;
 
                case "2":
                    System.out.print("Student ID to remove: ");
                    int removeId = Integer.parseInt(scan.nextLine().trim());
                    system.removeStudent(removeId);
                    break;
 
                case "3":
                    System.out.print("Student ID: ");
                    int searchId = Integer.parseInt(scan.nextLine().trim());
                    system.searchStudent(searchId);
                    break;
 
                case "4":
                    System.out.print("Sport name: ");
                    String sportForList = scan.nextLine().trim();
                    system.printStudentsBySport(sportForList);
                    break;
 
                case "5":
                    system.printSportsByCount();
                    break;
 
                case "6":
                    System.out.print("Student ID: ");
                    int connId = Integer.parseInt(scan.nextLine().trim());
                    System.out.print("Sport to find connection to: ");
                    String connSport = scan.nextLine().trim();
                    system.isConnected(connId, connSport);
                    break;
 
                case "7":
                    system.buildCommunities();
                    break;
 
                case "8":
                    System.out.print("New sport name: ");
                    String newSport = scan.nextLine().trim();
                    system.addSport(newSport);
                    break;
 
                case "9":
                    System.out.print("Sport name to remove: ");
                    String remSport = scan.nextLine().trim();
                    system.removeSport(remSport);
                    break;
 
                case "10":
                    System.out.print("Student ID: ");
                    int stuId = Integer.parseInt(scan.nextLine().trim());
                    System.out.print("Sport to add: ");
                    String addSp = scan.nextLine().trim();
                    system.addSportToStudent(stuId, addSp);
                    break;
 
                case "11":
                    System.out.print("Student ID: ");
                    int stuId2 = Integer.parseInt(scan.nextLine().trim());
                    System.out.print("Sport to remove: ");
                    String remSp = scan.nextLine().trim();
                    system.removeSportFromStudent(stuId2, remSp);
                    break;
 
                case "0":
                    running = false;
                    System.out.println("Goodbye.");
                    break;
 
                default:
                    System.out.println("Invalid option.");
            }
        }
        scan.close();
    }
 
    private static void printMenu() {
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
        System.out.println(" 0.  Exit");
        System.out.print("Option: ");
    }
}
