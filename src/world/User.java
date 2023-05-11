package world;
import util.CUtil;

public class User
{
    private String name;
    private double balance;
    private int health;

    // new user form variable

    public User(String name)
    {
        this.name = name;
        balance = 0;
        health = 3;
    }
  
    /**
     * Starts a form that prompt's the user to give info to their character.
     */
    public static User newUserForm() {
      String inputUserName = null; 
      boolean breakCondition = false;

      while(!breakCondition){

        CUtil.clearConsole();
        System.out.print(
          "Name: "
        );
        inputUserName = CUtil.input.nextLine();
        if (inputUserName.equals("")) {
          CUtil.moveConsoleCursor(3, 1);
          System.out.println("<Err> Please don't leave your name empty.");
          CUtil.entCont();
        } else {
          System.out.print("\nHmm... so you're " + CUtil.nameColorFormat(inputUserName) + ", right?(y/N): ");
          String userFormYNResponse = CUtil.input.nextLine();

          if (userFormYNResponse.equalsIgnoreCase("y")){
            breakCondition = true;
          }
        }
      }
      return new User(inputUserName);
    }

    public static void welcomeNewUser(User user){
      CUtil.clearConsole();

      System.out.println(
        "New user added!\n\n"+
        "Welcome to \u001B[32mConsole Clashers\u001B[0m, "+
        CUtil.ANSI_YELLOW+user.getName()+CUtil.ANSI_RESET+"!"
      );
    }

    public static void tutorialPrompts(){
      CUtil.clearConsole();
      // map
      System.out.println(
        "Here is a map of your world:\n\n"+
        Map.getMap1String()+
        "\nLegend:\n"+
        CUtil.currentPlayerPosition(" ") + 
        " - Your current position!\n"+
        "⌂ - This is your house. This is also where you start.\n"+
        "X - This is an interactable area. Check them out!\n"+
        "B - This is a boss room. Tread lightly...\n"
      );
      CUtil.entCont();
      // movement
      // pve
    }

    public String userPrint(String print){
      return CUtil.nameColorFormat(this.getName())+": "+print;
    }

    public String getName(){
      return name;
    }

    public void setName(String name){
      this.name = name;
    }

    public double getBalance(){
      return balance;
    }

    public void setBalance(double balance){
      this.balance = balance;
    }

    public int getHealth(){
      return health;
    }

    public void setHealth(int health){
      this.health = health;
    }

    public String toString(){
      return CUtil.ANSI_YELLOW+this.name+CUtil.ANSI_RESET;
    }
}