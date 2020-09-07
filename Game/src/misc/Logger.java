package misc;

import alchy.states.StateIDs;
import alchy.Alchy;

public class Logger {
    //no singleton for multiple loggers
    private ResourceLoader rl = null;
    private String consoleColor = "";
    private String warnConsoleColor = "";
    
    public Logger(StateIDs state){
        consoleColor = state.getColor();
        warnConsoleColor = state.getWarnColor();
    }
    
    public void write(String phrase){
        System.out.print(consoleColor+phrase);
    }
    
    public void writeln(String phrase){
        System.out.println(consoleColor+phrase);
    }
    
    public void warn(String phrase){
        System.out.println(warnConsoleColor+phrase);
    }
}
