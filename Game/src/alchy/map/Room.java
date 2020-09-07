package alchy.map;

import java.util.ArrayList;

public class Room {

    private double x;
    private double y;
    private int w;
    private int h;
    private int direction;
    private int identifier;
    private int index;
    private boolean free = false;
    private int beforeChange = Integer.parseInt(String.valueOf(Math.round(Math.random() * 255)));
    private int counterChange = 0;
    private double order = -1;
    private Room distanceRoom = null;
    private int W = Mapper.W;
    private int H = Mapper.H;
    private Room nextRoom = null;
    private ArrayList<Door> doors;

    Room(int x, int y, int w, int h, int identifier, int index) {
        this.x = y;
        this.y = x;
        this.w = w;
        this.h = h;
        this.identifier = identifier;
        this.index = index;

        doors = new ArrayList<Door>();
    }

    public void setDistanceFromRoom(Room distanceRoom) {
        this.distanceRoom = distanceRoom;
    }

    public Room getDistanceRoom() {
        return distanceRoom;
    }

    //sposta la stanza
    public void move() {
        if (counterChange >= beforeChange) {

            direction = Integer.parseInt(String.valueOf(Math.round(Math.random() * 4)));

            counterChange = 0;
        } else {
            counterChange++;
        }

        if (direction == 0) {
            if (y - 1 > 0) {
                y--;
            }
        } else if (direction == 1) {
            if (x + 1 < W) {
                x++;
            }
        } else if (direction == 2) {
            if (y + 1 < H) {
                y++;
            }
        } else if (direction == 3) {
            if (x - 1 > 0) {
                x--;
            }
        }
    }

    //disegna la stanza
    public void draw(int[][] map) {
        for (int i = (int) y; i < y + h; i++) {
            for (int j = (int) x; j < x + w; j++) {
                if (j > W - 1 || i > H - 1 || j < 0 || i < 0) {
                    continue;
                }

                map[i][j] = identifier;
            }
        }
    }

    //pulisce la matrice dalla stanza disegnata
    public void clean(int[][] map) {
        for (int i = (int) y - 1; i < y + h + 1; i++) {
            for (int j = (int) x - 1; j < x + w + 1; j++) {
                if (j > W - 1 || i > H - 1 || j < 0 || i < 0) {
                    continue;
                }
                if (map[i][j] == identifier) {
                    map[i][j] = 0;
                }
            }
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        Room room = (Room) obj;

        return x == room.getX() && y == room.getY() && w == room.getW() && h == room.getH();
    }

    //ritorna se la stanza deve essere riposizionata
    public boolean shouldMove() {
        return !free;
    }

    //ferma la stanza
    public void stop() {
        free = true;
    }

    public int getIdentifier() {
        return identifier;
    }

    public double getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    //crea i corridoi tra due stanze
    public void link(Room other, int[][] map, int[][] mapStreets) {
        System.out.println("Linking: " + getIndex() + " to " + other.getIndex());
        double startX = -1;
        double startY = -1;
        double endX = -1;
        double endY = -1;
        boolean a = false;
        boolean b = false;
        if (x <= other.getX() && y <= other.getY()) {
            startX = x + w / 2;
            startY = y + h / 2;
            endX = other.getX() + other.getW() / 2;
            endY = other.getY() + other.getH() / 2;

            a = true;
        } else if (x <= other.getX() && y >= other.getY()) {
            startX = x + w / 2;
            startY = other.getY() + other.getH() / 2;
            endX = other.getX() + other.getW() / 2;
            endY = y + h / 2;
        } else if (x >= other.getX() && y >= other.getY()) {
            startX = other.getX() + other.getW() / 2;
            startY = other.getY() + other.getH() / 2;
            endX = x + w / 2;
            endY = y + h / 2;

            b = true;
        } else if (x >= other.getX() && y <= other.getY()) {
            startX = other.getX() + other.getW() / 2;
            startY = y + h / 2;
            endX = x + w / 2;
            endY = other.getY() + other.getH() / 2;
        }

        System.out.println(startX + " -> " + endX + " ; " + startY + " -> " + endY);
        for (int j = (int) startX - 1; j < endX + 2; j++) {
            try {
                if (mapStreets[(int) startY + ((a) ? (int) (endY - startY) : 0)][j] == 0) {
                    mapStreets[(int) startY + ((a) ? (int) (endY - startY) : 0)][j] = 100;
                    mapStreets[(int) startY + ((a) ? (int) (endY - startY) : 0) + 1][j] = 100;
                    mapStreets[(int) startY + ((a) ? (int) (endY - startY) : 0) - 1][j] = 100;
                }
            } catch (Exception e) {

            }
        }
        for (int i = (int) startY - 1; i < endY + 2; i++) {
            try {
                if (mapStreets[i][(int) startX + ((b) ? (int) (endX - startX) : 0)] == 0) {
                    mapStreets[i][(int) startX + ((b) ? (int) (endX - startX) : 0)] = 100;
                    mapStreets[i][(int) startX + ((b) ? (int) (endX - startX) : 0) + 1] = 100;
                    mapStreets[i][(int) startX + ((b) ? (int) (endX - startX) : 0) - 1] = 100;
                }
            } catch (Exception e) {

            }
        }
    }

    public double distance(Room r2) {
        Room r1 = this;

        return Math.sqrt(
                Math.pow(
                        Math.abs(
                                (r1.getX() - r2.getX())
                        ),
                        2
                )
                + Math.pow(
                        Math.abs(
                                (r1.getY() - r2.getY())
                        ),
                        2
                ));
    }

    public int getIndex() {
        return index;
    }

    public Room getNextRoom() {
        return nextRoom;
    }

    public void setNextRoom(Room nextRoom) {
        this.nextRoom = nextRoom;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    void setDoor(int i, int j) {
        if (!doors.contains(new Door(i, j))) {
            doors.add(new Door(i, j));
        }
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }
}
