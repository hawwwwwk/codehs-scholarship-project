package world;
import util.*;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

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
                    );
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

    public static void locationDialogueHandler(File file, User user, Mob mob) throws FileNotFoundException{
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("*");
            for(int i = 0; i < nList.getLength(); i++){
                CUtil.clearConsole();
                Node nNode = nList.item(i);
                Element eElement = (Element) nNode;
                switch(eElement.getNodeName()){
                    case "message":
                        messagesHandler(eElement, nNode, user, mob);
                        CUtil.entCont();
                        break;
                    case "choices":
                        boolean exitCondition = false;
                        while(!exitCondition){
                            CUtil.clearConsole();
                            NodeList nChoiceList = eElement.getElementsByTagName("choice");
                            for (int j = 0; j < nChoiceList.getLength()-1; j++) {
                                Node nChoiceNode = nChoiceList.item(j);
                                Element fElement = (Element) nChoiceNode;
                                System.out.println((j+1)+". "+fElement.getAttribute("name"));
                            }
                            System.out.print("\nWhich option do you pick?: ");
                            String rawUserOption = CUtil.input.nextLine();
                            try {
                                int userOption = Integer.parseInt(rawUserOption);
                                File file2 = new File(
                                    "dialogue/choices/"+
                                    (((Element) nChoiceList.item(userOption-1)).getAttribute("id"))+
                                    ".xml"
                                );
                                locationDialogueHandler(file2, user, mob); // i love recurssion!!!!!!!
                                exitCondition = true;
                            } catch (Exception e) {
                                CUtil.unrecognizedInput("What you typed");
                                CUtil.entCont();
                            }
                        }
                        break;
                    case "FILL_HEALTH": 
                        user.setHealth(user.getHealth());
                        break;
                    default: break;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void messagesHandler(Element eElement, Node nNode, User user, Mob mob){
        String outputString = "";
        switch(eElement.getAttribute("from")){
            case "user": 
                outputString = user.userPrint(nNode.getTextContent());
                break;
            case "enemy":
                outputString = mob.enemyPrint(nNode.getTextContent());
                break;
            default:
                outputString = nNode.getTextContent();
                break;
        } 
        System.out.println(outputString);
    }

    

    public static String getMoveUpdateString(){
        return moveUpdateString;
    }
    
    public static void setMoveUpdateString(String str){
        moveUpdateString = str;
    }
}
