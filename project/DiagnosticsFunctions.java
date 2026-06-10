/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectdatastructure;

import java.util.Random;
 
public class DiagnosticsFunctions {
 
    private static final String[] SPORTS = {
        "volleyball", "rugby", "taekwondo", "swimming",
        "basketball", "football"
    };
 
    private static final String[] FIRST_NAMES = {
        "Ana","Luis","Marta","Pedro","Sara","Jorge","Camilo","Valentina",
        "Andres","Laura","Diego","Sofia","Juan","Maria","Carlos","Paula"
    };
 
    private static final String[] LAST_NAMES = {
        "Garcia","Lopez","Martinez","Rodriguez","Hernandez",
        "Perez","Gomez","Torres","Ramirez","Vargas"
    };
 
    private Random rng;
    private HashSet<Integer> usedIDs;
 
    public void initialize() {
        rng      = new Random(42);
        usedIDs  = new HashSet<>();
    }
 
    public String randomNameAssign() {
        String first = FIRST_NAMES[rng.nextInt(FIRST_NAMES.length)];
        String last  = LAST_NAMES [rng.nextInt(LAST_NAMES.length)];
        return first + " " + last;
    }
 
    public int randomIDAssign() {
        int id;
        do {
            id = 1000 + rng.nextInt(999000);
        } while (usedIDs.find(id));
        usedIDs.add(id);
        return id;
    }
    
    public String randomSport() {
        return SPORTS[rng.nextInt(SPORTS.length)];
    }
    
    public int randomSportCount() {
        return rng.nextInt(4);
    }
    
    public void prefill(SportsSystem system, int count) 
    {
        for (int i = 0; i < count; i++) {
            String name = randomNameAssign();
            int    id   = randomIDAssign();

            
            system.practicedSportsBuffer.head = null;
            system.interestSportsBuffer.head = null;
            
            int sc = randomSportCount();
            for (int j = 0; j < sc; j++) {
                system.practicedSportsBuffer.pushFront(randomSport());
            }
            
            sc = randomSportCount();
            for (int j = 0; j < sc; j++) {
                system.interestSportsBuffer.pushFront(randomSport());
            }
            system.createStudent(name, id);
            
            
           
        }
    }
 
    public long testInsertions(SportsSystem system) {
        long total = 0;
        
        for (int i = 0; i < 100; i++) {
            String name = randomNameAssign();
            int    id   = randomIDAssign();

            
            system.practicedSportsBuffer.head = null;
            system.interestSportsBuffer.head = null;
            
            int sc = randomSportCount();
            for (int j = 0; j < sc; j++) {
                system.practicedSportsBuffer.pushFront(randomSport());
            }
            
            sc = randomSportCount();
            for (int j = 0; j < sc; j++) {
                system.interestSportsBuffer.pushFront(randomSport());
            }

            long t0, t1;
            t0 = System.nanoTime();
            system.createStudent(name, id);
            t1 = System.nanoTime();
            
            total += (t1 - t0);
        }
        
        return total;
    }
    
    public long testDeletions(SportsSystem system) {
        long total = 0;
        
        for (int i = 0; i < 100; i++) {
            String name = randomNameAssign();
            int    id   = randomIDAssign();

            system.practicedSportsBuffer.head = null;
            system.interestSportsBuffer.head = null;
            
            int sc = randomSportCount();
            for (int j = 0; j < sc; j++) {
                system.practicedSportsBuffer.pushFront(randomSport());
            }
            
            sc = randomSportCount();
            for (int j = 0; j < sc; j++) {
                system.interestSportsBuffer.pushFront(randomSport());
            }

            system.createStudent(name, id);
            
            long t0, t1;
            t0 = System.nanoTime();
            system.removeStudent(id); 
            t1 = System.nanoTime();
            
            total += (t1 - t0);
        }
        
        return total;
    }
    
    public long testSearches(SportsSystem system) 
    {
        long total = 0;
        
        for (int i = 0; i < 100; i++) {
            String name = randomNameAssign();
            int    id   = randomIDAssign();

            system.practicedSportsBuffer.head = null;
            system.interestSportsBuffer.head = null;
            
            int sc = randomSportCount();
            for (int j = 0; j < sc; j++) {
                system.practicedSportsBuffer.pushFront(randomSport());
            }
            
            sc = randomSportCount();
            for (int j = 0; j < sc; j++) {
                system.interestSportsBuffer.pushFront(randomSport());
            }

            system.createStudent(name, id);
            
            long t0, t1;
            t0 = System.nanoTime();
            system.getStudent(id); 
            t1 = System.nanoTime();
            
            total += (t1 - t0);
        }
        
        return total;
    }
    
    public long testConnectivity(SportsSystem system) 
    {
        long total = 0;
        
        for (int i = 0; i < 100; i++) {
            String name = randomNameAssign();
            int    id   = randomIDAssign();

            system.practicedSportsBuffer.head = null;
            system.interestSportsBuffer.head = null;
            
            int sc = randomSportCount();
            for (int j = 0; j < sc; j++) {
                system.practicedSportsBuffer.pushFront(randomSport());
            }
            
            sc = randomSportCount();
            for (int j = 0; j < sc; j++) {
                system.interestSportsBuffer.pushFront(randomSport());
            }

            system.createStudent(name, id);
            
            String randomSport = randomSport();
            
            long t0, t1;
            t0 = System.nanoTime();
            system.isConnected(id, randomSport);
            t1 = System.nanoTime();
            
            total += (t1 - t0);
        }
        
        return total;
    }
    
    public long testCommunities(SportsSystem system) 
    {
        long time = 0;
        
        long t0, t1;
        t0 = System.nanoTime();
        system.buildCommunities();
        t1 = System.nanoTime();
        
        time = t1 - t0;
        
        return time;
    }
}