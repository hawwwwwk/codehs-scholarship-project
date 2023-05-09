package bin;
import util.*;
import world.Engine;
import world.User;

public class App{
    public static void main(String[] args) {
        CUtil.clearConsole();
    
        User user = User.newUserForm();
        User.welcomeNewUser(user);
        System.out.print("\nWould you like a quick tutorial?(y/N): ");
        if (CUtil.input.nextLine().equalsIgnoreCase("y")){
            User.tutorialPrompts();
        }
        Engine.mainMenu(user);
    }
}