import java.util.Random;

public class DiagnosticsFunctions 
{
    public Random rng;
    
    public String[] nameTable = {"Juan", "Diego", "Sebastian", "Jeronimo", "Jesus", "David", "Samuel", "Miguel", "Santiago", "Ricardo", "Daniel", "Laura", "Carolina", "Nicole", 
    "James", "Bruno", "Maria", "Tomas", "Juliette", "Alicia", "Mariana", "Hugo", "Martin", "John", "Chris", "Leon", "William", "Grace", "Claire", "Ema", "Mia", "Jose"};
    public String[] surnameTable = {"Cardona", "Gonzalez", "Quiñones", "Pinillos", "Rojas", "Giraldo", "Perez", "Rodriguez", "Marquez", "Jaramillo", "Bond", "Ramirez", "Fonseca", "Kramer", "Mayorca", "Olmos",
        "Neuer", "Ronaldo", "Díaz", "Bonilla", "Mejía", "Bustos", "García", "Honda", "Stark", "Parker", "Easton", "Blanco", "Quevedo", "Quesada", "Gordon", "Carpenter"};
    public String[] sportsTable = {"taekwondo", "football", "basketball", "volleyball", "rugby", "swimming"};
    
    public void initialize() 
    {
        rng = new Random();
    }
    
    public int randomIDAssign() 
    {
        return rng.nextInt(1999999999);
    }
    
    public int randomSportCount() {
        return rng.nextInt(3);
    }
    
    public String randomNameAssign() 
    {
        int maxNameLength = nameTable.length;
        int maxSurnameLength = surnameTable.length;
        
        int nameIndex = rng.nextInt(maxNameLength) % maxNameLength;
        int surnameIndex = rng.nextInt(maxSurnameLength) % maxSurnameLength;
        
        String name = nameTable[nameIndex];
        String surname = surnameTable[surnameIndex];
        String secondName = "";
        String secondSurname = "";
        
        nameIndex = rng.nextInt(maxNameLength) % maxNameLength;
        surnameIndex = rng.nextInt(maxSurnameLength) % maxSurnameLength;
        
        boolean useSecond = rng.nextInt() % 2 == 0;
        if (useSecond) secondName = nameTable[nameIndex];
        
        useSecond = rng.nextInt() % 2 == 0;
        if (useSecond) secondSurname = surnameTable[surnameIndex];
        
        return name + " " + secondName + " " + surname + " " + secondSurname;
    }
    
    public String randomSport() 
    {
        int sportSelection = rng.nextInt(sportsTable.length);
        return sportsTable[sportSelection];
    }
}
