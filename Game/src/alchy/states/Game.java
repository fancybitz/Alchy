package alchy.states;

import misc.Logger;
import misc.ResourceLoader;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import alchy.Alchy;
import alchy.map.Mapper;
import alchy.map.Room;
import org.newdawn.slick.Color;

public class Game extends BasicGameState {

    private int ID = -1;
    private Alchy skol = null;
    private ResourceLoader rl = null;
    private Logger logger = null;
    private Mapper mapper = null;

    public Game(Alchy skol, int ID) {
        this.skol = skol;
        this.ID = ID;

        rl = ResourceLoader.getInstance();
        logger = new Logger(StateIDs.GAME);

        mapper = new Mapper();
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawString("Game", 100, 100);
        int[][] map = mapper.getMap();
        int[][] mapStreets = mapper.getMapStreets();
        for (int i = 0; i < mapper.getH(); i++) {
            for (int j = 0; j < mapper.getW(); j++) {
                if (mapStreets[i][j] == 100) {
//                    g.setColor(new Color(map[i][j], map[i][j], map[i][j]));
                    g.setColor(new Color(255, 128, 128));
                    g.fillRect(i, j, 1, 1);
                }

                g.setColor(Color.black);
            }
        }
        for (int i = 0; i < mapper.getH(); i++) {
            for (int j = 0; j < mapper.getW(); j++) {
                if (map[i][j] != 0 && map[i][j] != 101) {
//                    g.setColor(new Color(map[i][j], map[i][j], map[i][j]));
                    g.setColor(new Color(128, 128, 128));
                    g.fillRect(i, j, 1, 1);
                } else if (map[i][j] == 101) {
//                    g.setColor(new Color(map[i][j], map[i][j], map[i][j]));
                    g.setColor(new Color(255, 0, 0));
                    g.fillRect(i, j, 1, 1);
                }

                g.setColor(Color.black);
            }
        }
//        for (int i = 0; i < mapper.getH(); i++) {
//            for (int j = 0; j < mapper.getW(); j++) {
//                if (map[i][j] != 0 && mapStreets[i][j] == 100) {
//                    g.setColor(new Color(255, 0, 0));
//                    g.fillRect(i, j, 1, 1);
//                } 
//
//                g.setColor(Color.black);
//            }
//        }
//
//        for (Room room : mapper.rooms) {
//            g.setColor(Color.white);
//            g.drawString(String.valueOf(room.getIndex()), (int) room.getY(), (int) room.getX());
//        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {

    }

}
