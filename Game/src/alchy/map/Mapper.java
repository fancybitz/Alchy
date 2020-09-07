package alchy.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mapper implements Runnable {

    public static int W = 1200;
    public static int H = 1200;
    private int[][] map;
    private int[][] mapStreets;
    private int nRooms = 20;
    private int minWRoom = 50;
    private int maxWRoom = 100;
    private int minHRoom = 50;
    private int maxHRoom = 100;
    public ArrayList<Room> rooms;

    public Mapper() {
        map = new int[H][W];
        mapStreets = new int[H][W];
        rooms = new ArrayList<Room>();

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                map[i][j] = 0;
                mapStreets[i][j] = 0;
            }
        }

        for (int i = 0; i < nRooms; i++) {
            rooms.add(
                    new Room(
                            512,
                            512,
                            Integer.parseInt(String.valueOf(Math.round(Math.random() * (maxWRoom - minWRoom) + minWRoom))),
                            Integer.parseInt(String.valueOf(Math.round(Math.random() * (maxHRoom - minHRoom) + minHRoom))),
                            Integer.parseInt(String.valueOf(Math.round(Math.random() * 255))),
                            i
                    )
            );
        }

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                int intersections = 0;

                for (Room room : rooms) {
                    if (room.shouldMove()) {
                        if (intersects(room, rooms)) {
                            room.move();
                            intersections++;
                        } else {
                            room.stop();
                        }
                    }
                }
                for (Room room : rooms) {
                    room.clean(map);
                    room.draw(map);
                }

                if (intersections == 0) {
                    break;
                }
            } catch (Exception ex) {
                Logger.getLogger(Mapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Stop");
        Room old = null;
        int k = 0;
        Room next = null;
        ArrayList<Room> roomsRemove = new ArrayList<Room>();
        for (Room actual : rooms) {
            Room nearestRoom = null;
            if (next != null) {
                actual = next;
            }

            System.out.println(actual.getIndex());

            //lista di copia senza actual
            ArrayList<Room> copy = new ArrayList();
            ArrayList<Room> copy2 = new ArrayList();
            for (Room tmp : rooms) {
                if (!actual.equals(tmp)) {
                    copy.add(tmp);
                    copy2.add(tmp);
                }
            }
            //rimuovo anche la stanza linkata e la prossima stanza
            for (Room tmp : copy2) {
                if ((actual.getDistanceRoom() != null && actual.getDistanceRoom().equals(tmp)) || (actual.getNextRoom() != null)) {
                    copy.remove(tmp);
                }
            }

            for (Room tmp : copy) {
                if (nearestRoom == null || actual.distance(tmp) < actual.distance(nearestRoom)) {
                    nearestRoom = tmp;
                }
            }

            if (nearestRoom == null) {
                for (Room tmp : rooms) {
                    if (!actual.equals(tmp) && (tmp.getDistanceRoom() == null || tmp.getNextRoom() == null)) {
                        nearestRoom = tmp;
                    }
                }
            }

            nearestRoom.setDistanceFromRoom(actual);
            actual.setNextRoom(nearestRoom);
            actual.link(nearestRoom, map, mapStreets);
            next = nearestRoom;
            nearestRoom = null;
        }
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getDistanceRoom() == null && rooms.get(i).getNextRoom() == null) {
                roomsRemove.add(rooms.get(i));
            }
        }
        for (int i = 0; i < roomsRemove.size(); i++) {
            roomsRemove.get(i).clean(map);
            rooms.remove(roomsRemove.get(i));
        }

        setDoors();
    }

    private void setDoors() {
        setDoorsUp();
        setDoorsRight();
        setDoorsDown();
        setDoorsLeft();

        reduceDoors();
    }

    private void setDoorsLeft() {
        for (Room room : rooms) {
            for (int i = (int) room.getY(); i < room.getY() + 1; i++) {
                for (int j = (int) room.getX(); j < room.getX() + room.getW(); j++) {
                    if (mapStreets[i - 1][j] == 100
                            || mapStreets[i + 1][j] == 100
                            || mapStreets[i][j - 1] == 100
                            || mapStreets[i][j + 1] == 100) {
                        map[i][j] = 101;

                        room.setDoor(i, j);
                    }
                }
            }
        }
    }

    private void setDoorsRight() {
        for (Room room : rooms) {
            for (int i = (int) room.getY() + room.getH(); i < room.getY() + room.getH() + 1; i++) {
                for (int j = (int) room.getX(); j < room.getX() + room.getW(); j++) {
                    if (mapStreets[i - 1][j] == 100
                            || mapStreets[i + 1][j] == 100
                            || mapStreets[i][j - 1] == 100
                            || mapStreets[i][j + 1] == 100) {
                        map[i][j] = 101;

                        room.setDoor(i, j);
                    }
                }
            }
        }
    }

    private void setDoorsDown() {
        for (Room room : rooms) {
            for (int i = (int) room.getY(); i < room.getY() + room.getH(); i++) {
                for (int j = (int) room.getX() + room.getW(); j < room.getX() + room.getW() + 1; j++) {
                    if (mapStreets[i - 1][j] == 100
                            || mapStreets[i + 1][j] == 100
                            || mapStreets[i][j - 1] == 100
                            || mapStreets[i][j + 1] == 100) {
                        map[i][j] = 101;

                        room.setDoor(i, j);
                    }
                }
            }
        }
    }

    private void setDoorsUp() {
        for (Room room : rooms) {
            for (int i = (int) room.getY(); i < room.getY() + room.getH(); i++) {
                for (int j = (int) room.getX(); j < room.getX() + 1; j++) {
                    if (mapStreets[i - 1][j] == 100
                            || mapStreets[i + 1][j] == 100
                            || mapStreets[i][j - 1] == 100
                            || mapStreets[i][j + 1] == 100) {
                        map[i][j] = 101;

                        room.setDoor(i, j);
                    }
                }
            }
        }
    }

    private void reduceDoors() {
        for (Room actual : rooms) {
            System.out.println(actual.getDoors().size());
        }
    }

    public int[][] getMap() {
        return map;
    }

    public int getW() {
        return W;
    }

    public int getH() {
        return H;
    }

    //controlla che la stanza non si intersechi con le altre
    private boolean intersects(Room room, ArrayList<Room> rooms) {
        int span = 10;
        for (Room actual : rooms) {
            if (!actual.equals(room)) {
                //+span serve per una distanza minima tra le stanze
                if (room.getX() < actual.getX() + actual.getW() + span && room.getX() + room.getW() + span > actual.getX() && room.getY() < actual.getY() + actual.getH() + span && room.getY() + room.getH() + span > actual.getY()) {
                    return true;
                }
            }
        }

        return false;
    }

    public int[][] getMapStreets() {
        return mapStreets;
    }

}
