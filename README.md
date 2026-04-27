# Red Deportiva UNAL

> Sistema de conexión estudiantil por deporte — Universidad Nacional de Colombia  
> Proyecto · Estructuras de Datos

---

## Tabla de contenido

- [El problema](#-el-problema)
- [Funcionalidades](#-funcionalidades)
- [Arquitectura y estructuras de datos](#-arquitectura-y-estructuras-de-datos)
- [Complejidad Big O](#-complejidad-big-o)
- [Estructura del proyecto](#-estructura-del-proyecto)
- [Cómo ejecutar](#-cómo-ejecutar)
- [Ejemplo de uso](#-ejemplo-de-uso)
- [Decisiones de diseño](#-decisiones-de-diseño)
- [Autores](#-autores)

---

## El problema

La UNAL ofrece una amplia oferta deportiva: voleibol, rugby, taekwondo, natación, baloncesto, fútbol, entre otros. Muchos estudiantes quieren explorar nuevas disciplinas pero no saben a quién contactar.

La conexión puede existir de forma **indirecta**: si Ana practica voleibol y rugby, y Luis practica rugby y natación, entonces Ana puede llegar a alguien de natación a través de Luis, aunque nunca hayan hablado directamente.

```
Ana ──(rugby)──→ Luis ──(natación)──→ Marta
 │                                      │
 └── practica: voleibol, rugby           └── practica: natación, fútbol
```

El sistema resuelve este problema de **alcanzabilidad en un grafo implícito** usando las estructuras de datos vistas hasta el momento.

---

## ⚙️ Funcionalidades

| # | Funcionalidad | Descripción |
|---|---|---|
| 1 | **Registrar estudiante** | ID, nombre, lista de deportes practicados y de interés |
| 2 | **Eliminar estudiante** | Remoción completa del sistema en O(k·log d + log n) |
| 3 | **Acceso por ID** | Búsqueda directa en AVL en O(log n) |
| 4 | **Conectividad** | Determina si un estudiante está conectado directa o indirectamente con alguien que practique un deporte de interés |
| 5 | **Comunidades deportivas** | Agrupa automáticamente a los estudiantes conectados entre sí |
| 6 | **Ranking de deportes** | Lista los deportes ordenados por número de practicantes |
| 7 | **Agregar/quitar deporte a estudiante** | Actualiza listas y referencias en tiempo constante |

---

## Arquitectura y estructuras de datos

El sistema se basa en **dos árboles AVL** como índices principales, complementados con listas enlazadas y un arreglo de referencias cruzadas.

```
┌─────────────────────────────────────────────────────────┐
│                    SISTEMA                              │
│                                                         │
│   AVL<Student>              AVL<Sport>                  │
│   (clave: ID)               (clave: nombre)             │
│       │                         │                       │
│   ┌───▼───┐                 ┌───▼───┐                   │
│   │Student│                 │ Sport │                   │
│   │  node │                 │  node │                   │
│   └───┬───┘                 └───┬───┘                   │
│       │                         │                       │
│  SLL practice ──────────────────┘ (puntero compartido)  │
│  SLL interests                                          │
│  DinamicArray nodeRefs[] ──→ DobleNode en DLL           │
│  boolean visited                                        │
│                             DLL<Student> practicers     │
└─────────────────────────────────────────────────────────┘
```

### Estructuras utilizadas

| Estructura | Uso | Justificación |
|---|---|---|
| `AVL<Student>` | Índice principal de estudiantes por ID | O(log n) garantizado en peor caso. BST sin balanceo degeneraría a O(n) con IDs secuenciales |
| `AVL<Sport>` | Índice de deportes por nombre | O(log d) por búsqueda. In-order entrega deportes en orden sin costo adicional |
| `SinglyLinkedList<Sport>` | Deportes practicados e intereses por estudiante | Solo recorrido lineal (BFS). Inserción O(1) al frente, sin acceso aleatorio |
| `DoublyLinkedList<Student>` | Practicantes de cada deporte | `removeByReference()` en O(1) con puntero directo. Crítico para eliminación eficiente |
| `DinamicArray<DobleNode>` | `nodeRefs[]` — referencias cruzadas en el estudiante | Elimina búsqueda O(m) al remover: el puntero directo al nodo DLL hace la remoción O(1) |
| `Queue<Student>` | Cola circular para BFS | Enqueue/dequeue O(1). Garantiza exploración nivel por nivel |

### El patrón nodeRefs[]

El puente que hace eficiente la eliminación de estudiantes:

```
Student Ana
├── practice SLL:  [voleibol*] → [rugby*] → null
├── nodeRefs[]:    [ref_en_DLL_voleibol, ref_en_DLL_rugby]
│                        │                    │
│                        ▼                    ▼
│            Voleibol.DLL: Luis ←→ [Ana] ←→ Marta
│            Rugby.DLL:   Pedro ←→ [Ana] → null
└── visited: false

// Eliminar Ana de voleibol → O(1):
ref.prev.next = ref.next
ref.next.prev = ref.prev
```

Sin `nodeRefs[]`, encontrar el nodo de Ana en cada DLL costaría O(m) por deporte.

---

## Complejidad Big O

**Variables:**
- `n` = total de estudiantes
- `d` = total de deportes
- `k` = deportes que practica un estudiante
- `m` = practicantes de un deporte
- `V` = vértices del grafo (= n)
- `E` = aristas del grafo (pares estudiante-deporte)

| Operación | Estructura | Costo | Nota |
|---|---|---|---|
| Insertar estudiante | AVL estudiantes | `O(log n)` | |
| Buscar por ID | AVL estudiantes | `O(log n)` | Peor caso garantizado |
| Eliminar estudiante | AVL + DLL + nodeRefs | `O(k·log d + log n)` | Sin nodeRefs sería O(k·m) |
| Agregar deporte al sistema | AVL deportes | `O(log d)` | |
| Insertar en DLL | DLL | `O(1)` | Operación dominante |
| Remover nodo de DLL | DLL + nodeRefs | `O(1)` | Con puntero directo al nodo |
| Listar practicantes | DLL | `O(m)` | Recorrido lineal |
| Listar practicantes en orden | DLL + sort | `O(m log m)` | Extracción + ordenamiento |
| Listar deportes por conteo | AVL + sort | `O(d log d)` | d es siempre pequeño |
| BFS conectividad | Cola + flags | `O(V + E)` | **Óptimo teórico absoluto** |
| Construir comunidades | BFS múltiple | `O(V + E)` | Cada nodo procesado una vez |

---

## Estructura del proyecto

```
sports/
├── Main.java               # Menú principal e interacción con el usuario
├── SportsSystem.java       # Lógica central del sistema y todas las operaciones
│
├── Student.java            # Nodo de estudiante (practice, interests, nodeRefs, visited)
├── Sport.java              # Nodo de deporte (practicers DLL, amountStu)
│
├── AVL.java                # Árbol AVL genérico con insert, delete, find, isTheElement
├── SinglyLinkedList.java   # Lista simple con find por referencia (==)
├── DoublyLinkedList.java   # Lista doble con removeByReference() en O(1)
├── DinamicArray.java       # Arreglo dinámico con redimensionamiento automático
├── Queue.java              # Cola circular sobre arreglo
│
├── SingleNode.java         # Nodo para SLL
└── DobleNode.java          # Nodo para DLL (prev + next)
```

---

## Cómo ejecutar

### Requisitos

- Java Development Kit (JDK) 11 o superior
- Terminal / línea de comandos

### Compilar

```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/red-deportiva-unal.git
cd red-deportiva-unal/sports

# Compilar todos los archivos
javac *.java
```

### Ejecutar

```bash
java Main
```

### Menú principal

```
======= UNAL Sports Network =======
 1.  Registrar estudiante
 2.  Eliminar estudiante
 3.  Buscar estudiante por ID
 4.  Listar estudiantes por deporte
 5.  Listar deportes por número de practicantes
 6.  Verificar conectividad (estudiante ↔ deporte)
 7.  Mostrar comunidades deportivas
 8.  Agregar nuevo deporte
 9.  Eliminar deporte del sistema
 10. Agregar deporte a estudiante existente
 11. Quitar deporte a estudiante existente
 0.  Salir
```

---

## Ejemplo de uso

```
Opción: 1
Nombre: Ana García
ID: 1001

Deportes disponibles:
  volleyball
  rugby
  football
  swimming

¿Cuáles practica Ana? (0 para terminar)
> volleyball
> rugby
> 0

¿Cuáles le interesan? (0 para terminar)
> swimming
> 0

✓ Estudiante Ana García (ID 1001) registrado.

---

Opción: 6
ID del estudiante: 1001
Deporte a buscar: swimming

Buscando conexión: Ana García → swimming
  Ana García   practica  volleyball, rugby
  Luis Mora    practica  rugby, swimming   ← conexión encontrada

✓ CONECTADO en 2 saltos.
```

---

## Decisiones de diseño

### ¿Por qué AVL y no BST?

Con IDs asignados secuencialmente (1001, 1002, 1003...) un BST sin balanceo degenera a una lista enlazada con búsqueda O(n). El AVL garantiza O(log n) sin importar el orden de inserción.

### ¿Por qué DLL y no SLL en la lista de practicantes?

La operación `removeByReference()` de una DLL es O(1) cuando se tiene un puntero directo al nodo. Con SLL sería necesario recorrer la lista para encontrar el nodo anterior: O(m). Como las inserciones y eliminaciones de estudiantes son las operaciones dominantes del sistema, la DLL minimiza el costo acumulado total.

### ¿Por qué SLL y no arreglo dinámico para los deportes del estudiante?

Las listas `practice` e `interests` solo se recorren linealmente durante el BFS. No hay acceso aleatorio. La SLL evita el costo de redimensionamiento y la copia de elementos del arreglo dinámico cuando crece, con inserción O(1) al frente.

### ¿Por qué flags `visited` y no un AVL auxiliar?

Un AVL de visitados durante BFS costaría O(log n) por inserción y consulta. Una bandera booleana directa en el nodo es O(1). El reseteo previo al BFS cuesta O(n + d), pero ese costo está absorbido en la complejidad total O(V + E) del algoritmo.

### ¿Por qué DLL sobre AVL en `practicers`, si hay que listarlos en orden?

Las inserciones y eliminaciones de estudiantes son las operaciones dominantes (ocurren constantemente durante el semestre). La consulta de listado es ocasional. DLL garantiza O(1) en las operaciones dominantes, a costa de O(m log m) en el sort para listar. Con el patrón de uso real, el costo acumulado total de DLL es menor que el de AVL.

---

## Autores

- Sebastian Gonzalez Giraldo
- Juan Diego Cardona Cortes
- Jesus David Pinillos
- Jeronimo Quiñones Rueda

---

<div align="center">

**Estructuras usadas:** `AVL` · `DLL` · `SLL` · `DinamicArray` · `Queue` · `BFS`

**Complejidades clave:** `O(log n)` búsqueda · `O(1)` inserción/remoción · `O(V+E)` conectividad

</div>
