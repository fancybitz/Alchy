package misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceLoader {

    private static ResourceLoader instance = null;
    private Map<String, String> confs = null;
    private OSHandler osh = null;

    private ResourceLoader() {
        init();
    }
    
    private void init(){
        osh = new OSHandler();

        confs = new HashMap<String, String>();

        loadIniFile(confs, osh.changePath("src//assets//conf.ini"));
    }

    //signleton implementation
    public static ResourceLoader getInstance() {
        if (instance == null) {
            instance = new ResourceLoader();
        }

        return instance;
    }

    public String getStringValueFromConfs(String key) {
        for(Map.Entry<String, String> entry : confs.entrySet()){
            if(entry.getKey().equals(key)){
                return entry.getValue();
            }
        }
        
        return null;
    }
    
    public Integer getIntegerValueFromConfs(String key) {
        String value = getStringValueFromConfs(key);
        if(value==null){
            throw new NullPointerException("ResourceLoader.java getIntegerValueFromConfs --> "+key);
        }
        
        return Integer.parseInt(value);
    }

    //legge riga per riga il file che gli si passa  chiave:valore e ne riempie la mappa che gli viene passata
    private void loadIniFile(Map<String, String> rows, String filePath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(filePath)));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (!line.contains(":")) {
                    continue;
                }
                
                String[] all = line.split(":");
                rows.put(all[0].trim(), all[1].trim());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ResourceLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ResourceLoader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(ResourceLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void newGame() {
        init();
    }
}
