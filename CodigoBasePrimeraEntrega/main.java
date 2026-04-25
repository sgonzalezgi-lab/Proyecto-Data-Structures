public class main
{
	static DynamicArray<Deporte> registroDeportes = new DynamicArray<Deporte>();
	public static void main(String[] args) 
	{
		TestMatchMaker();	
	}
	public static void inscribirDeporte(Estudiante est,String name) {
		Deporte deporteEncontrado = null;
		for (int i = 0; i < registroDeportes.size; i++) {
			Deporte deporte = registroDeportes.getAt(i);
			if (deporte != null && deporte.name.equalsIgnoreCase(name)) {
				deporteEncontrado = deporte;
				break;
			}
		}
		if(deporteEncontrado == null) {
			deporteEncontrado = new Deporte(name);
			registroDeportes.insert(deporteEncontrado);
		}
		est.deportesPracticados.pushBack(deporteEncontrado);	
		deporteEncontrado.students.pushBack(new Referente(est.ID, est.name, -1));
	}
	public static void TestMatchMaker() 
	{
		System.out.println("--- Iniciando Prueba de MatchMaker (AVL + Grafos BFS) ---");
		AVLtree db = new AVLtree();
		Estudiante s1 = new Estudiante(1, "Alice");
		Estudiante s2 = new Estudiante(2, "Bob");
		Estudiante s3 = new Estudiante(3, "Charlie");
		Estudiante s4 = new Estudiante(4, "Diana");
		Estudiante s5 = new Estudiante(5, "Eve");

		System.out.println("Asignando deportes...");
		inscribirDeporte(s1, "Baloncesto");
		inscribirDeporte(s2, "Baloncesto");
		inscribirDeporte(s3, "Baloncesto");

		inscribirDeporte(s2, "Tenis");
		inscribirDeporte(s4, "Tenis");

		inscribirDeporte(s4, "Natación");
		inscribirDeporte(s5, "Natación");

		System.out.println("Insertando estudiantes en la base de datos AVL...");
		db.root = db.insert(db.root, s1);
		db.root = db.insert(db.root, s2);
		db.root = db.insert(db.root, s3);
		db.root = db.insert(db.root, s4);
		db.root = db.insert(db.root, s5);

		System.out.println("Grafo aprobado exitosamente!\n");

		MatchMaker mm = new MatchMaker();
		System.out.println("Alice busca a alguien que juegue Tenis...");
		mm.BuscarConexion(db, s1, "Tenis");

		System.out.println("---------------------------");
		System.out.println("Alice busca a alguien que practique Natación...");
		mm.BuscarConexion(db, s1, "Natación");

		System.out.println("---------------------------");
		System.out.println("Alice busca a alguien que practique Baloncesto...");
		mm.BuscarConexion(db, s1, "Baloncesto");

		System.out.println("Fin de la prueba");
	}
}