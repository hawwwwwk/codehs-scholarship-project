package world;

import java.util.ArrayList;
import util.CUtil;
import java.io.File;

public class Location {
    private String name;
    private int locationX;
    private int locationY;
    private static ArrayList<Location> locations = new ArrayList<>();

    public static final String DIALOGUE_PATH = System.getProperty("user.dir")+"/dialogue/";

    public Location(String name, int locationX, int locationY) {
        this.name = name;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public static void buildWorld() { // (needs to be ran before scan!)
        
        Location homeLocation = new Location(CUtil.homeColorFormat("Home"), 2, 1);
        Location enemyLocation1 = new Location(CUtil.enemyColorFormat("Enemy1"), 5, 2);
        Location enemyLocation2 = new Location(CUtil.enemyColorFormat("Enemy2"), 12, 3);
        Location enemyLocation3 = new Location(CUtil.enemyColorFormat("Enemy3"), 6, 4);
        Location enemyLocation4 = new Location(CUtil.enemyColorFormat("Enemy4"), 11, 5);
        Location bossLocation = new Location(CUtil.enemyColorFormat("Boss"), 5, 7);
        Location emptyTile = new Location("Empty", -1, -1);
        locations.add(homeLocation);
        locations.add(enemyLocation1);
        locations.add(enemyLocation2);
        locations.add(enemyLocation3);
        locations.add(enemyLocation4);
        locations.add(bossLocation);
        locations.add(emptyTile);
    }

    public static void exploreCurrentLocation(User user, Engine engine){
        System.out.print(
            "\nWould you like to explore the '"+Map.getCurrentLocation().getName()+"' tile?(y/N): "
        );
        String exploreCurrentLocationYN = CUtil.input.nextLine();
        if(!exploreCurrentLocationYN.equalsIgnoreCase("y")){return;}

        switch(Map.getCurrentLocationInt()){
            case 0: // home
                Mob homeDummy = new Mob("home dummy", -1);
                File fHome = new File(DIALOGUE_PATH+"/home."+CUtil.getCodeHsFix());
                engine.locationDialogueHandler(fHome, user, homeDummy);
                break;
            case 1: // enemy1
                Mob trainingMob = new Mob("Training Mob", 3);
                File fEnemy1 = new File(DIALOGUE_PATH+"/enemy1."+CUtil.getCodeHsFix());
                engine.locationDialogueHandler(fEnemy1, user, trainingMob);
                break;
            case 2: // enemy2
            case 3: // enemy3
            case 4: // enemy4
            case 5: // boss
            default: // empty tile
                Mob emptyTileMob = new Mob("emptyTile", -1);
                File fEmpty = new File(DIALOGUE_PATH+"/empty-tile."+CUtil.getCodeHsFix());
                engine.locationDialogueHandler(fEmpty, user, emptyTileMob);
        }
    }

    public static Location scanCurrentTile() { 
        
        switch (Map.getCurrentLocationInt()){
            case 0: // home
                Engine.setMoveUpdateString(CUtil.homeColorFormat("Home, sweet home..."));
                Map.setCurrentLocation(0);
                return locations.get(0);
            case 1: // enemy1
                Engine.setMoveUpdateString(CUtil.enemyColorFormat("Enemy1"));
                Map.setCurrentLocation(1);
                return locations.get(1);
            case 2: // enemy2
                Engine.setMoveUpdateString(CUtil.enemyColorFormat("Enemy2"));
                Map.setCurrentLocation(2);
                return locations.get(2);
            case 3: // enemy3
                Engine.setMoveUpdateString(CUtil.enemyColorFormat("Enemy3"));
                Map.setCurrentLocation(3);
                return locations.get(3);
            case 4: // enemy4
                Engine.setMoveUpdateString(CUtil.enemyColorFormat("Enemy4"));
                Map.setCurrentLocation(4);
                return locations.get(4);
            case 5: // boss
                Engine.setMoveUpdateString(CUtil.enemyColorFormat("Boss"));
                Map.setCurrentLocation(5);
                return locations.get(5);
            default: // empty tile
                Engine.setMoveUpdateString("...");
                Map.setCurrentLocation(6);
                return locations.get(6);
        }
    }

    public static void addLocation(Location location) {
        locations.add(location);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public static Location getLocation(int index){
        return locations.get(index);
    }
}
