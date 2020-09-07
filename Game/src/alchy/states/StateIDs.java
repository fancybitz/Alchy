package alchy.states;

public enum StateIDs {
    GAME(0),
    MENU(1);

    private final int id;

    StateIDs(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        if (id == 0) {
            return "\033[0;33m";  // YELLOW
        } else if (id == 1) {
            return "\033[0;32m";   // GREEN
        } else if (id == 2) {
            return "\033[0;36m";    // CYAN
        } else if (id == 3) {
            return "\033[0;34m";    // BLUE
        } else if (id == 4) {
            return "\033[0;35m";  // PURPLE
        } else {
            System.out.println("Skol.java add new color for console");
            return "\033[0;37m";   // WHITE
        }
    }

    public String getWarnColor() {
        return "\033[0;31m";     // RED
    }
}
