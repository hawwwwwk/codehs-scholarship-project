package world;
import util.*;

public class Engine {

    private Engine(){
        throw new IllegalStateException("Not a public class plz no touchy <3");
    }

    private static String moveUpdateString = "...";

    public static void mainMenu(User user){
        Location.buildWorld();
        boolean exitCondition = false;
        Map.updateMapWithCoordinates();
        while (!exitCondition){ 
            CUtil.clearConsole();
            System.out.print(
                "What would you like to do, "+
                user+"?\n\n"+
                "'m' - pull up the map and move spaces\n" +
                "'x' - exit game\n\n"
            );
            String mainMenuInput = CUtil.input.nextLine();
            switch(mainMenuInput){
                case "m":
                    moveMenu(user);
                    break;
                case "x": 
                    System.out.print(
                        "Are you sure you want to exit?\n\n"+
                        CUtil.ANSI_RED+"Your progress will NOT be saved: "+CUtil.ANSI_RESET
                    ); // finish this please
                    if(!CUtil.input.nextLine().equalsIgnoreCase("y")){
                        exitCondition = true;
                    }
                    break;
                default:
                    CUtil.unrecognizedInput(mainMenuInput);
                    CUtil.entCont();
                    break;
            }
        }
    }

    public static void moveMenu(User user){
        boolean exitCondition = false;
        while(!exitCondition){
            CUtil.clearConsole();
            System.out.println(
                "! - Use 'wasd' to move spaces\n"+
                "('e' to explore tile, 'x' to exit back to menu.)\n\n"+
                Map.getMap1String()
            );
            System.out.println("(" + moveUpdateString + ")\n");
            
            String userDirectionString = CUtil.input.nextLine();
            switch(userDirectionString){
                case "w":
                    Map.updateMap(1, "y");
                    break;
                case "a":
                    Map.updateMap(-1, "x");
                    break;
                case "s":
                    Map.updateMap(-1, "y");
                    break;
                case "d":
                    Map.updateMap(1, "x");
                    break;
                case "x":
                    exitCondition=true;
                    break;
                case "e":
                    Location.exploreCurrentLocation(user);
                    break;
                default:
                    CUtil.unrecognizedInput(userDirectionString);
                    break;
            }
        }

    }

    public static String getMoveUpdateString(){
        return moveUpdateString;
    }
    
    public static void setMoveUpdateString(String str){
        moveUpdateString = str;
    }
}
