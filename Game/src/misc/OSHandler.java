package misc;

class OSHandler {
    private boolean isWindows(){
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
    
    public static String changePath(String path){
        return path.replace("//", System.getProperty("file.separator"));
    }
}
