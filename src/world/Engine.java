package world;
import util.*;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
public class Engine {

    public Engine(){
        // x
    }

    public static final Engine engine = new Engine();
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
                    Engine.moveMenu(user);
                    break;
                case "x": 
                    System.out.print(
                        "Are you sure you want to exit?\n\n"+
                        CUtil.ANSI_RED+"Your progress will NOT be saved(y/N): "+CUtil.ANSI_RESET
                    );
                    if(CUtil.input.nextLine().equalsIgnoreCase("y")){
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
                    Location.exploreCurrentLocation(user, engine);
                    break;
                default:
                    CUtil.unrecognizedInput(userDirectionString);
                    break;
            }
        }
    }

    public void locationDialogueHandler(File file, User user, Mob mob) {
        try {
            Document doc = parseDoc(file);

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("*");
            for (int i = 0; i < nList.getLength(); i++) {
                CUtil.clearConsole();
                Node nNode = nList.item(i);
                Element eElement = (Element) nNode;
                boolean exitCondition = false;
                switch (eElement.getNodeName()) {
                    case "message":
                        messagesHandler(eElement, nNode, user, mob);
                        CUtil.entCont();
                        break;
                    case "choices":
                        while (!exitCondition) {
                            CUtil.clearConsole();
                            NodeList nChoiceList = eElement.getElementsByTagName("choice");
                            for (int j = 0; j < nChoiceList.getLength(); j++) {
                                Node nChoiceNode = nChoiceList.item(j);
                                Element fElement = (Element) nChoiceNode;
                                System.out.println((j + 1) + ". " + fElement.getAttribute("name"));
                            }
                            System.out.print("\nWhich option do you pick?: ");
                            String rawUserOption = CUtil.input.nextLine();
                            exitCondition = readChoiceXMLFiles(rawUserOption, nChoiceList, user, mob);
                        }
                        break;
                    case "yes-no-choice":
                        readYesNoChoiceXmlFiles(nNode, user, mob);
                        break;
                    case "FILL_HEALTH":
                        user.setHealth(user.getHealth());
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Document parseDoc(File file){
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            
            return documentBuilder.parse(new FileInputStream(file));
        } catch(Exception e){
            e.printStackTrace();
        } return null;
    }

    public void readYesNoChoiceXmlFiles(Node nNode, User user, Mob mob){
        int inputID = 0;
        boolean exitCondition = false;
        while(!exitCondition){
            CUtil.clearConsole();
            System.out.print("Would you like to '"+nNode.getTextContent()+"'(y/N)?: ");
            
            String userInput = CUtil.input.nextLine();
            if(userInput.equalsIgnoreCase("y")){
                inputID = Integer.parseInt(((Element) nNode).getAttribute("yes-id"));
                exitCondition = true;
            } else if(userInput.equalsIgnoreCase("n")){
                inputID = Integer.parseInt(((Element) nNode).getAttribute("no-id"));
                exitCondition = true;
            } else {
                CUtil.unrecognizedInput(userInput);
            }
        }
        try {
            File file = new File(Location.DIALOGUE_PATH+"/choices/"+inputID+"."+CUtil.getCodeHsFix());
            locationDialogueHandler(file, user, mob);
        } catch(Exception e){
            e.printStackTrace();
            CUtil.entCont();
        }
    }

    public boolean readChoiceXMLFiles(String rawUserOption, NodeList nChoiceList, User user, Mob mob){
        try {
            int userOption = Integer.parseInt(rawUserOption);
            File file = new File(
                Location.DIALOGUE_PATH+
                "/choices/"+
                (((Element) nChoiceList.item(userOption-1)).getAttribute("id"))+
                "."+CUtil.getCodeHsFix()
            );
            locationDialogueHandler(file, user, mob); // i love recurssion!!!!!!!
            return true;
        } catch (Exception e) {
            CUtil.unrecognizedInput(rawUserOption);
            CUtil.entCont();
            return false;
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
