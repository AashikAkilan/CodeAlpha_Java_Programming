package com.hotelreservation;

import java.io.*;
import java.util.*;

public class FileManager {

    public static void saveRooms(List<Room> rooms, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(rooms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
@SuppressWarnings("unchecked")
public static List<Room> loadRooms(String filename) {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
        return (List<Room>) ois.readObject();
    } catch (Exception e) {
        return new ArrayList<>();
    }
}

}
