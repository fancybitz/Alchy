package alchy;

import java.util.ArrayList;
import java.util.List;
import alchy.states.Menu;
import java.util.logging.Level;
import java.util.logging.Logger;
import misc.ResourceLoader;
import alchy.states.StateIDs;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import alchy.states.Game;

public class Alchy extends StateBasedGame {

    private Menu menu = null;
    private Game game = null;
    private ResourceLoader rl = null;

    private List<Integer> oldStates = null;

    Alchy(String title) {
        super(title);

        oldStates = new ArrayList<Integer>();

        try {
            rl = ResourceLoader.getInstance();

            AppGameContainer agc = new AppGameContainer(this);
            agc.setDisplayMode(rl.getIntegerValueFromConfs("ScreenWidth"), rl.getIntegerValueFromConfs("ScreenHeight"), false);
            agc.setShowFPS(true);
            agc.start();

        } catch (SlickException ex) {
            Logger.getLogger(Alchy.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Alchy game = new Alchy("Game");
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        menu = new Menu(this, StateIDs.MENU.getId());
        game = new Game(this, StateIDs.GAME.getId());

        addState(menu);
        addState(game);

        enterState(game.getID());
    }

    public void changeState(StateIDs from, StateIDs to) {
        oldStates.add(from.getId());
        enterState(to.getId());
    }

    public void back() {
        if (oldStates.size() > 0) {
            Integer last = oldStates.get(oldStates.size() - 1);
            enterState(last);
            oldStates.remove(oldStates.size() - 1);
        }
    }
}
