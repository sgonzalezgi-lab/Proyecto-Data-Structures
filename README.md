# Red Deportiva UNAL

> **Sistema de conexion estudiantil por deporte — Universidad Nacional de Colombia**  
> Proyecto académico de Estructuras de Datos

---

## Tabla de contenido

- [El problema](#el-problema)
- [Solucion implementada](#solucion-implementada)
- [Interfaz grafica](#interfaz-grafica)
- [Arquitectura y estructuras de datos](#arquitectura-y-estructuras-de-datos)
- [Complejidad Big O](#complejidad-big-o)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Como ejecutar](#como-ejecutar)
- [Ejemplo de uso](#ejemplo-de-uso)
- [Decisiones de diseno](#decisiones-de-diseno)
- [Autores](#autores)

---

## El problema

La Universidad Nacional de Colombia ofrece una amplia oferta deportiva: voleibol, rugby, taekwondo, natacion, baloncesto, futbol, entre otros. Muchos estudiantes desean explorar nuevas disciplinas pero no conocen a quien contactar dentro de la comunidad universitaria.

La conexion puede existir de forma **indirecta**: si Ana practica voleibol y rugby, y Luis practica rugby y natacion, entonces Ana puede llegar a alguien de natacion a traves de Luis, aunque nunca hayan interactuado directamente.

```
Ana --(rugby)--> Luis --(natacion)--> Marta
 |                                      |
 +-- practica: voleibol, rugby          +-- practica: natacion, futbol
```

El sistema resuelve este problema de **alcanzabilidad en un grafo bipartito implicito** (estudiantes ↔ deportes) utilizando estructuras de datos implementadas desde cero.

---

## Solucion implementada

El sistema ofrece dos interfaces de uso:

| Modo | Descripcion |
|------|-------------|
| **GUI Swing** | Interfaz grafica completa con tablas, grafo interactivo y panel de rendimiento |
| **Consola** | Acceso directo a traves de la API de `SportsSystem` para integraciones |

### Funcionalidades principales

| # | Funcionalidad | Descripcion |
|---|--------------|-------------|
| 1 | **Registrar estudiante** | Nombre, ID, deportes practicados y deportes de interes |
| 2 | **Eliminar estudiante** | Remocion completa del sistema en O(k) |
| 3 | **Acceso por ID** | Busqueda directa en HashMap en O(1) |
| 4 | **Conectividad (BFS)** | Determina si un estudiante esta conectado directa o indirectamente con alguien que practique un deporte de interes |
| 5 | **Comunidades deportivas** | Agrupa automaticamente a estudiantes conectados por deportes en comun; visualizacion como grafo con fuerzas fisicas |
| 6 | **Ranking de deportes** | Lista deportes ordenados por numero de practicantes (insertion sort) |
| 7 | **Agregar/quitar deporte a estudiante** | Actualiza listas y referencias cruzadas en O(1) |
| 8 | **Gestion de deportes del sistema** | Crear y eliminar deportes globales |
| 9 | **Benchmarks de rendimiento** | Pruebas de estres con 100, 1.000 y 10.000 operaciones sobre el sistema real |

---

## Interfaz grafica

La aplicacion cuenta con una interfaz Swing con tema oscuro que incluye cinco modulos:

### Estudiantes
Tabla completa de estudiantes registrados con busqueda por ID, registro mediante dialogo con seleccion de deportes via checkboxes, y eliminacion directa.

### Deportes
Tabla de deportes con conteo de practicantes, listado de nombres, y ordenamiento por popularidad.

### Conectividad
Verificacion visual de conexion entre un estudiante y un deporte objetivo. Muestra resultado con indicador de exito/fracaso y detalle de la cadena encontrada.

### Comunidades
**Visualizacion interactiva del grafo** de conexiones estudiante-deporte:
- Nodos representan estudiantes
- Aristas representan deportes en comun
- **Simulacion de fuerzas** (repulsion Coulomb + atraccion por resorte) con amortiguamiento
- Los nodos se agrupan por comunidad detectada via BFS, cada una con color distintivo
- **Arrastrar nodos** con el mouse para explorar la red
- **Tooltips** al pasar el cursor sobre un nodo (nombre, ID, deportes)
- Leyenda de comunidades

### Rendimiento
Panel con dos componentes:
- **Grafico de complejidad empirica**: curvas de tiempo promedio por operacion (insercion, busqueda, eliminacion, listado, BFS, comunidades) a diferentes escalas (n=10², 10³, 10⁴, 10⁵)
- **Prueba de estres ejecutable**: benchmarks aislados sobre el sistema real con 100, 1.000 y 10.000 operaciones, mostrando tiempos totales y promedios por operacion con barras comparativas

---

## Arquitectura y estructuras de datos

### Diagrama del sistema

```
+---------------------------------------------------------+
|                    SPORTS SYSTEM                        |
|                                                         |
|   HashMap<Integer, Student>    HashMap<String, Sport>   |
|   (clave: ID)                  (clave: nombre)          |
|       |                              |                  |
|   +---v---+                      +---v---+               |
|   |Student|                      | Sport |               |
|   | node  |                      | node  |               |
|   +---|---+                      +---|---+               |
|       |                              |                  |
|  SLL practice -----------------------+ (puntero compartido)
|  SLL interests                                          |
|  SportEntry (sport + ref DLL)                           |
|  boolean visited                                        |
|                              DLL<Student> practicers    |
|                              int amountStu               |
|                              boolean visited             |
+---------------------------------------------------------+
```

### Grafo implicito

El sistema modela una red social deportiva como un **grafo bipartito**:

- **Conjunto A (estudiantes)**: Conectados entre si indirectamente a traves de deportes compartidos
- **Conjunto B (deportes)**: Conectan estudiantes que los practican
- **Arista**: Relacion "practica" entre un estudiante y un deporte

La conectividad se resuelve con **BFS** que alterna entre estudiantes y deportes, con flags `visited` para evitar ciclos.

### Estructuras implementadas desde cero

| Estructura | Uso | Justificacion |
|------------|-----|---------------|
| `HashMap<K, V>` | Indices principales de estudiantes (ID) y deportes (nombre) | O(1) amortizado en insercion, busqueda y eliminacion. Redimensionamiento automatico con factor de carga 0.75 |
| `HashSet<T>` | Conjunto de IDs unicos para generacion de datos de prueba | O(1) en add, find y remove mediante encadenamiento separado |
| `SinglyLinkedList<T>` | Deportes practicados (`practice`) e intereses (`interests`) de cada estudiante | Solo recorrido secuencial (BFS). Insercion O(1) al frente |
| `DoublyLinkedList<T>` | Practicantes de cada deporte (`practicers`) | `removeByReference()` en O(1) con puntero directo. Critico para eliminacion eficiente |
| `DinamicArray<T>` | Almacenamiento dinamico de comunidades, listados y datos de graficos | Redimensionamiento automatico (duplicacion). Acceso O(1) por indice |
| `Queue<T>` | Cola circular sobre arreglo para BFS | Enqueue/dequeue O(1). Garantiza exploracion nivel por nivel. Redimensionamiento automatico |
| `SingleNode<T>` | Nodo para SLL (valor + siguiente) | |
| `DobleNode<T>` | Nodo para DLL (valor + anterior + siguiente) | Permite remocion O(1) por referencia |
| `SportEntry` | Par (Sport, DobleNode) que vincula un deporte con la referencia del estudiante en la DLL de practicantes | Patron clave para eliminacion en O(k) en vez de O(k·m) |

### El patron SportEntry (referencia cruzada)

El mecanismo que hace eficiente la eliminacion de estudiantes:

```
Student Ana
+-- practice SLL: [SportEntry] --> [SportEntry] --> null
|      |                |
|      |                v
|      |    Rugby.DLL: Pedro <-> [Ana] <-> Carlos
|      |
|      +---> SportEntry:
|            sport = rugby
|            studentRef = DobleNode que apunta a Ana en la DLL de rugby
|
+-- interests SLL: [swimming] --> null
+-- visited: false

// Eliminar Ana de rugby:
// 1. Obtener studentRef del SportEntry de rugby -> O(k) recorriendo practice
// 2. DLL.removeByReference(studentRef) -> O(1)
// 3. Repetir para cada deporte que practica Ana -> O(k) total
```

Sin `SportEntry` y su referencia al nodo DLL, eliminar a Ana de cada deporte requeriria buscarla en cada lista de practicantes: O(k·m) donde m es el numero de practicantes por deporte.

---

## Complejidad Big O

**Variables:**
- `n` = total de estudiantes registrados
- `d` = total de deportes en el sistema
- `k` = deportes que practica un estudiante
- `m` = practicantes de un deporte
- `V` = vertices del grafo bipartito (estudiantes + deportes = n + d)
- `E` = aristas del grafo (relaciones estudiante-deporte)

| Operacion | Estructura | Costo | Nota |
|-----------|-----------|-------|------|
| Insertar estudiante | HashMap + DLL | **O(k)** | k inserciones O(1) en HashMap + k inserciones O(1) en DLL |
| Buscar por ID | HashMap | **O(1)** amortizado | Encadenamiento separado con redimensionamiento |
| Eliminar estudiante | HashMap + SportEntry + DLL | **O(k)** | k remociones O(1) por referencia directa + O(1) en HashMap |
| Agregar deporte al sistema | HashMap | **O(1)** amortizado | |
| Eliminar deporte del sistema | HashMap + SLL | **O(n·m)** | Recorre todos los practicantes y todos los estudiantes para limpiar referencias |
| Agregar deporte a estudiante | HashMap + DLL | **O(1)** | Insercion en HashMap + DLL |
| Quitar deporte a estudiante | SportEntry + DLL | **O(k)** para encontrar + **O(1)** para remover | Busqueda lineal en practice SLL, luego remocion O(1) por referencia |
| Listar practicantes | DLL | **O(m)** | Recorrido lineal |
| Listar deportes por conteo | DinamicArray + insertion sort | **O(d²)** | Insertion sort sobre arreglo de deportes |
| BFS conectividad | Queue + flags | **O(V + E)** | Optimo teorico para busqueda en grafos no ponderados |
| Construir comunidades | BFS multiple | **O(V + E)** | Cada nodo procesado exactamente una vez |
| Acceso por indice | DinamicArray | **O(1)** | Arreglo subyacente |

---

## Estructura del proyecto

```
com.mycompany.projectdatastructure/
|
+-- Projectdatastructure.java    # Clase principal del proyecto (NetBeans)
+-- GUI.java                     # Interfaz grafica Swing (punto de entrada)
+-- SportsSystem.java            # Logica central: todas las operaciones del sistema
+-- DiagnosticsFunctions.java    # Benchmarks de rendimiento con datos aleatorios
|
+-- Student.java                 # Estudiante: practice, interests, visited
+-- Sport.java                   # Deporte: practicers (DLL), amountStu, visited
+-- SportEntry.java              # Par (Sport, DobleNode) para referencias cruzadas
|
+-- HashMap.java                 # Tabla hash generica con encadenamiento separado
+-- HashSet.java                 # Conjunto hash sobre DoublyLinkedList[]
+-- SinglyLinkedList.java        # Lista simple: pushFront, find, remove, print
+-- DoublyLinkedList.java        # Lista doble: pushFront, find, remove, removeByReference
+-- DinamicArray.java            # Arreglo dinamico con redimensionamiento por duplicacion
+-- Queue.java                   # Cola circular sobre arreglo con redimensionamiento
|
+-- SingleNode.java              # Nodo para SLL
+-- DobleNode.java               # Nodo para DLL (prev + next)
```

---

## Como ejecutar

### Requisitos

- Java Development Kit (JDK) 11 o superior
- Terminal / linea de comandos

### Compilar

```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/red-deportiva-unal.git
cd red-deportiva-unal

# Compilar todos los archivos
javac com/mycompany/projectdatastructure/*.java
```

### Ejecutar (interfaz grafica)

```bash
java com.mycompany.projectdatastructure.GUI
```

### Ejecutar (desde codigo)

```java
import com.mycompany.projectdatastructure.*;

public class Main {
    public static void main(String[] args) {
        SportsSystem sys = new SportsSystem();

        // Registrar deportes
        sys.addSport("volleyball");
        sys.addSport("rugby");
        sys.addSport("swimming");

        // Registrar estudiante
        sys.practicedSportsBuffer.pushFront("volleyball");
        sys.practicedSportsBuffer.pushFront("rugby");
        sys.interestSportsBuffer.pushFront("swimming");
        sys.createStudent("Ana Garcia", 1001);
        sys.clearBuffers();

        // Verificar conectividad
        boolean connected = sys.isConnected(1001, "swimming");
        System.out.println("Conectada: " + connected);

        // Obtener comunidades
        DinamicArray<DinamicArray<Student>> communities = sys.buildCommunities();
        System.out.println("Comunidades encontradas: " + communities.getSize());
    }
}
```

---

## Ejemplo de uso

### Interfaz grafica

Al ejecutar `GUI.java`, se abre una ventana con barra lateral de navegacion:

1. **Registrar estudiante**: Pestaña *Estudiantes* → boton *Registrar* → completar formulario con checkboxes de deportes
2. **Ver conectividad**: Pestaña *Conectividad* → ingresar ID y deporte → clic en *Verificar conexion*
3. **Ver comunidades**: Pestaña *Comunidades* → clic en *Actualizar grafo* → nodos interactivos con simulacion fisica
4. **Benchmarks**: Pestaña *Rendimiento* → seleccionar escala (100, 1.000 o 10.000 ops) → ver barras comparativas

### API programatica

```java
SportsSystem sys = new SportsSystem();

// --- Configurar buffers de deportes ---
sys.practicedSportsBuffer.pushFront("rugby");
sys.practicedSportsBuffer.pushFront("volleyball");
sys.interestSportsBuffer.pushFront("swimming");

// --- Crear estudiante ---
sys.createStudent("Ana Garcia", 1001);
sys.clearBuffers();

// --- Crear segundo estudiante que conecta la cadena ---
sys.practicedSportsBuffer.pushFront("rugby");
sys.practicedSportsBuffer.pushFront("swimming");
sys.createStudent("Luis Mora", 1002);
sys.clearBuffers();

// --- Verificar conexion: Ana -> swimming ---
// Ana practica volleyball, rugby
// Luis practica rugby, swimming
// Existe cadena: Ana --rugby--> Luis --swimming--> ...
boolean ok = sys.isConnected(1001, "swimming");
// Resultado: true

// --- Eliminar estudiante en O(k) ---
sys.removeStudent(1001);
// Se remueve de todos los deportes que practicaba via referencias cruzadas

// --- Obtener comunidades (componentes conexas del grafo) ---
DinamicArray<DinamicArray<Student>> coms = sys.buildCommunities();
```

---

## Decisiones de diseno

### Por que HashMap y no AVL o BST?

El sistema utiliza tablas hash con encadenamiento separado en lugar de arboles de busqueda. Las operaciones dominantes son insercion, busqueda y eliminacion, todas O(1) amortizado en una tabla hash bien dimensionada. El redimensionamiento automatico (factor de carga 0.75) garantiza que el encadenamiento permanezca corto. Para los tamanos tipicos del problema (miles de estudiantes y decenas de deportes), el acceso constante supera en rendimiento al O(log n) de un arbol.

### Por que DLL y no SLL en la lista de practicantes?

La operacion `removeByReference()` de una DLL es O(1) cuando se posee un puntero directo al nodo. Con SLL seria necesario recorrer la lista para encontrar el nodo previo: O(m). Como las eliminaciones de estudiantes son una operacion frecuente, la DLL minimiza el costo acumulado total. El patrón `SportEntry` almacena esta referencia directa en el nodo de practica del estudiante.

### Por que SLL y no arreglo dinamico para los deportes del estudiante?

Las listas `practice` e `interests` solo se recorren secuencialmente durante el BFS. No requieren acceso aleatorio. La SLL ofrece insercion O(1) al frente sin el costo de redimensionamiento del arreglo dinamico.

### Por que flags `visited` y no un conjunto auxiliar?

Un conjunto de visitados durante BFS costaria O(1) por insercion y consulta en una tabla hash, pero requeriria crear y destruir estructuras adicionales. Una bandera booleana directa en el nodo es O(1) sin overhead de memoria. El reseteo previo al BFS recorre todos los estudiantes y deportes: O(n + d), absorbido en la complejidad total O(V + E).

### Por que Queue sobre arreglo en vez de lista enlazada?

La cola circular sobre arreglo ofrece mejor localidad de cache y menor overhead de memoria (sin punteros prev/next por nodo). El redimensionamiento automatico mantiene las operaciones O(1) amortizado sin complejidad adicional.

### Por que simulacion de fuerzas en el grafo de comunidades?

La visualizacion del grafo emplea un algoritmo de layout por fuerzas: repulsion Coulomb entre todos los pares de nodos y atraccion por resorte entre nodos conectados por un deporte en comun. Esto produce una disposicion visual donde las comunidades (componentes conexas) emergen naturalmente como clusters espacialmente separados, facilitando la comprension de la estructura de la red deportiva.

---

## Autores

| Nombre | Rol |
|--------|-----|
| Sebastian Gonzalez Giraldo | Implementacion de estructuras de datos, logica de grafo |
| Juan Diego Cardona Cortes | Interfaz grafica, visualizacion de comunidades, benchmarks |
| Jesus David Pinillos | Modelo de dominio, pruebas, documentacion |
| Jeronimo Quinones Rueda | Arquitectura del sistema, integracion, optimizacion |

---

<div align="center">

**Estructuras implementadas desde cero:** `HashMap` · `HashSet` · `DoublyLinkedList` · `SinglyLinkedList` · `DinamicArray` · `Queue` · `BFS`

**Complejidades clave:** `O(1)` busqueda · `O(k)` insercion/eliminacion · `O(V+E)` conectividad y comunidades

*Universidad Nacional de Colombia — 2025*

</div>
