package util;
import java.util.Scanner;

import world.User;

public class CUtil
{
    //#region Color codes
    // Thanks to Alexander Bezrodniy on SO for the explaination.
    // When this project is finished, delete any unused variables.
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_GRAY = "\u001B[90m";
    public static final String ANSI_CYAN_BG = "\u001B[46m";
    //#endregion

    // global scanner
    public static final Scanner input = new Scanner(System.in);
    
    private CUtil(){
      throw new IllegalStateException("Utility Class");
    }
    
    /**
     * Clears the terminal.
     * 
     * The method does this by using ANSI characters to move
     * the cursor to the top left, and then clears everything
     * from the end to that point.
     */
    public static void clearConsole()
    {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    /**
     * Moves the cursor to a point in the console. Thank you Tom Jefferys on SO for the code block.
     * 
     * @param row    the console's row you want to nav to  
     * @param column the console's column you want to nav to
     */
    public static void moveConsoleCursor(int row, int column)
    {
        System.out.print(String.format("%c[%d;%df",0x1B,row,column));
    }
    /**
     * Prompts to user to press enter before continuing.
     */
    public static void entCont(){
      System.out.print(ANSI_GRAY+"press enter to continue..."+ANSI_RESET);
      input.nextLine();
    }

    public static void wallOfTreesNotification(){
      System.out.println(
        ANSI_RED+"There's a wall of trees in the way..."+ANSI_RESET
      );
    }

    /**
     * Informs the user that the entered input in unrecognized. Good for menus.
     * @param userInput What did the user say?
     */
    public static void unrecognizedInput(String userInput){
      System.out.println(
          "\n'"+userInput+"' is not a recognized input."
      );
    }

    /**
     * Lets the use know where they're current position is by highlighting a String.
     * @param str the String you'd like to have a cyan background.
     * @return colored character (still a String, though).
     */
    public static String currentPlayerPosition(String str){
      return ANSI_CYAN_BG+str+ANSI_RESET;
    }

    public static String nameColorFormat(User user){
      return ANSI_YELLOW+user.getName()+ANSI_RESET;
    }

    public static String nameColorFormat(String name){
      return ANSI_YELLOW+name+ANSI_RESET;
    }

    public static String homeColorFormat(String name){
      return ANSI_GREEN+name+ANSI_RESET;
    }

    public static String enemyColorFormat(String enemy){
      return ANSI_RED+enemy+ANSI_RESET;
    }

    public static String numberColorFormat(String number){
      return ANSI_BLUE+number+ANSI_RESET;
    }

  }