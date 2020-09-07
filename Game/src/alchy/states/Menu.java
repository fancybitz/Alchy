package alchy.states;



import misc.Logger;
import misc.ResourceLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import alchy.Alchy;

public class Menu extends BasicGameState {
    private Alchy skol = null;
    private int ID = 0;

    private String[] voices = {"New", "Load", "Save", "Options", "Exit"};
    private boolean[] actives = {true, true, false, true, true};

    private int selected = 0;

    private ResourceLoader rl;
    private Logger logger = null;

    public Menu(Alchy skol, int ID) {
        this.skol = skol;
        this.ID = ID;

        rl = ResourceLoader.getInstance();
        logger = new Logger(StateIDs.MENU);
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
        for (int i = 0; i < voices.length; i++) {
            if (i == selected) {
                g.setColor(Color.red);
            } else {
                g.setColor(Color.white);
            }
            g.drawString(voices[i], rl.getIntegerValueFromConfs("ScreenWidth") / 2 - 30, i * 100 + 50);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input in = gc.getInput();

        if (in.isKeyPressed(Input.KEY_DOWN)) {
            selected++;
            if (selected > voices.length - 1) {
                selected = voices.length - 1;
            }
        } else if (in.isKeyPressed(Input.KEY_UP)) {
            selected--;
            if (selected < 0) {
                selected = 0;
            }
        }else if(in.isKeyPressed(Input.KEY_ENTER)){
            act();
        }
    }
    
    private void act(){
        if(selected==0){    //new
            logger.writeln("MENU new game selected");
            rl.newGame();
            skol.changeState(StateIDs.MENU, StateIDs.GAME);
        }else if(selected==1){      //load
            logger.writeln("MENU load selected");
        }else if(selected==2){      //save
            logger.writeln("MENU save selected");
        }else if(selected==3){      //options
            logger.writeln("MENU options selected");
        }else if(selected==4){      //exit
            logger.writeln("MENU exit selected");
            System.exit(0);
        }else{
            logger.warn("MENU no option selected");
        }
    }
}
