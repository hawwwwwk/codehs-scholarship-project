package world;

import java.util.ArrayList;
import util.CUtil;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jdom2.*;

public class Location {
    private String name;
    private int locationX;
    private int locationY;
    private int locationIndex;
    private static ArrayList<Location> locations = new ArrayList<>();

    public Location(String name, int locationX, int locationY) {
        this.name = name;
        this.locationIndex = Map.getLocationIndexFromCords(locationX, locationY);
    }

    public static void buildWorld() { // needs to be ran before scan...
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
        
        Map.setCurrentLocation(6); 
    }

    public static void exploreCurrentLocation(User user){
        System.out.print(
            "\nWould you like to explore the '"+Map.getCurrentLocation().getName()+"' tile?(y/N): "
        );
        String exploreCurrentLocationYN = CUtil.input.nextLine();
        if(!exploreCurrentLocationYN.equalsIgnoreCase("y")){return;}
        Mob trainingMob = new Mob("Training Mob", 1);
        File file = new File("dialogue/enemy1.xml");
        Engine.locationDialogueHandler(file, user, trainingMob);
    }

    public static Location scanCurrentTile() { 
        
        switch (Map.getCurrentLocationInt()){
            case 105: // home
                Engine.setMoveUpdateString(CUtil.homeColorFormat("Home, sweet home..."));
                Map.setCurrentLocation(0);
                return locations.get(0);
            case 91: // enemy1
                Engine.setMoveUpdateString(CUtil.enemyColorFormat("Enemy1"));
                Map.setCurrentLocation(1);
                return locations.get(1);
            case 81: // enemy2
                Engine.setMoveUpdateString(CUtil.enemyColorFormat("Enemy2"));
                Map.setCurrentLocation(2);
                return locations.get(2);
            case 59: // enemy3
                Engine.setMoveUpdateString(CUtil.enemyColorFormat("Enemy3"));
                Map.setCurrentLocation(3);
                return locations.get(3);
            case 46: // enemy4
                Engine.setMoveUpdateString(CUtil.enemyColorFormat("Enemy4"));
                Map.setCurrentLocation(4);
                return locations.get(4);
            case 23: // boss
                Engine.setMoveUpdateString(CUtil.enemyColorFormat("Boss"));
                Map.setCurrentLocation(5);
                return locations.get(5);
            default: // empty tile
                Engine.setMoveUpdateString("...");
                Map.setCurrentLocation(6);
                return locations.get(6);
        }
    }

    /** Starts dialogue with the area that you're currently on, if the user wants.
     * 
     * @param i Location's position on ArrayList location<>
     */
    public static void locationDialogue(int i, User user){
        CUtil.clearConsole();
        switch(i){
            case 0: // home
                CUtil.clearConsole();


                System.out.println("It's cold outside, so you head into the house.");
                CUtil.entCont();
                System.out.print("Do you want to heal yourself by the fire?(y/N): ");
                String homeHealResponse = CUtil.input.nextLine();
                if (homeHealResponse.equalsIgnoreCase("n")){
                    System.out.println("You decide you'd rather be cold for some reason, and head back out.");
                    CUtil.entCont();
                } else if (homeHealResponse.equalsIgnoreCase("y")){
                    System.out.println("You sit by the fire for a second, it warms your "+CUtil.ANSI_CYAN+"soul"+CUtil.ANSI_RESET+"...");
                    System.out.println("(Your health was refilled!)");
                    user.setHealth(3);
                    CUtil.entCont();
                } else {
                    System.out.println("\nStunned by the fact that you are somehow unable to type 'y' nor 'n', you leave the house.");
                    CUtil.entCont();
                }
                break;

            case 1: // enemy1
                CUtil.clearConsole();
                System.out.println("...");
                CUtil.entCont();
                user.userPrint("It's oddly quiet.");
                CUtil.entCont();
                System.out.println("You hear some russeling in the leaves...");
                CUtil.clearConsole();
                Mob trainingMob = new Mob("Training Mob", 1);
                user.userPrint("Gahh! A monster appeared!");
                CUtil.entCont();
                System.out.println("...");
                CUtil.entCont();
                System.out.println("The monster was a tiny thing, a small sphere shaped gelantonis blob. It was almost comical.");
                user.userPrint("You're not very big.");
                CUtil.entCont();
                trainingMob.enemyPrint("Well, thanks.");
                CUtil.entCont();
                user.userPrint("You're supposed to be an enemy?");
                CUtil.entCont();
                trainingMob.enemyPrint("And you're supposed to be an adventurer?");
                CUtil.entCont();
                user.userPrint("I guess? I don't knkw, I just expected my first mob to be bigger.");
                CUtil.entCont();
                trainingMob.enemyPrint("Check the name, buddy.");
                CUtil.entCont();
                trainingMob.enemyPrint("And you know? Not all of us get what we want in life. I didn't ask to be 'baby's first enemy'. I was just born into this profession.");
                CUtil.entCont();
                CUtil.clearConsole();
                System.out.println("...");
                CUtil.entCont();
                user.userPrint("Yeesh dude, I'm sorry.");
                CUtil.entCont();
                System.out.println("The thing rolled it's tiny black eyes.");
                trainingMob.enemyPrint("It's whatever. I'm used to it.");
                CUtil.entCont();
                System.out.println("You really don't know what to do here, it feels awkward to push questions about his size any longer.");
                user.userPrint("So, what, am I supposed to fight you or something?");
                CUtil.entCont();
                trainingMob.enemyPrint("I mean I feel the name is self explanatory.");
                CUtil.entCont();
                user.userPrint("Ok, yeah, I guess.");
                CUtil.entCont();
                trainingMob.enemyPrint("Then bring it.");
                CUtil.entCont();
                
                System.out.println("You've started a battle against "+ CUtil.enemyColorFormat(trainingMob.getName())+"!\n");
                boolean fightActive = true;
                while (fightActive){ // put this into seperate combat form in mob class
                    CUtil.clearConsole();
                    System.out.println(CUtil.enemyColorFormat(trainingMob.getName())+" has "+trainingMob.getHp()+" health.");
                    System.out.println("What do you want to do?\n");
                    System.out.println(
                        "a: attack\n"+
                        "x: flee\n"+
                        "e: analyze\n"
                    );
                    String userInput = CUtil.input.nextLine();
                    switch(userInput){
                        case "a":
                            trainingMob.setHp(trainingMob.getHp()-1);
                            System.out.println("\nYou attack "+CUtil.enemyColorFormat(trainingMob.getName())+"!");
                            System.out.println("You do "+CUtil.numberColorFormat("1")+" damage!");
                            CUtil.entCont();
                            if(trainingMob.getHp() <= 0){
                                System.out.println("\n"+CUtil.enemyColorFormat(trainingMob.getName())+" was defeated!\n");
                                System.out.println("Ignoring the moral implecations of killing something so innocent, you move on.");
                                CUtil.entCont();
                                fightActive = false;
                            }
                            break;
                        case "x":
                            System.out.println("\nTrying to flee...");
                            trainingMob.enemyPrint("Running away so soon?\n");
                            System.out.println("Unable to flee.");
                            CUtil.entCont();
                            break;
                        case "e":
                            System.out.println("\nYou analyze the "+CUtil.enemyColorFormat(trainingMob.getName())+".\n");
                            System.out.println(
                                "Mob: "+CUtil.enemyColorFormat(trainingMob.getName())+
                                "\nHealth: "+trainingMob.getHp()+"/"+trainingMob.getMaxHp()+
                                "\nDesc: A slimey blob. Don't touch it."
                            );
                            CUtil.entCont();
                            break;
                        default:break;
                    }
                }
                break;
            case 2: // enemy2
                break;
            case 3: // enemy3
                break;
            case 4: // enemy4
                break;
            case 5: // boss
                break;
            case 6: // empty tile
                System.out.println(
                    "There doesn't seem to be anything here..."
                );
                CUtil.entCont();
                break;
            default: break;
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
