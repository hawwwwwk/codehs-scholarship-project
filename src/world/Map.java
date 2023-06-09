package world;

import util.*;

public class Map {
  private static int xCord = 2; // starting value is 2
  private static int yCord = 1; // starting value is 1
  private static final String MAPBLANK = 
      "o0o0o0o0o0o0o0o0\n" + // w:15 h: 7
      "0    B         o\n" +
      "o          X   0\n" +
      "0      X       o\n" +
      "o           X  0\n" +
      "0    X         o\n" + // up is -16
      "o ⌂            0\n" + // down is +16
      "0o0o0o0o0o0o0o0o"; // bottom-left corner is (0, 0)
  private static String map1 = MAPBLANK;
  private static Location currentLocation;
  private static int currentLocationIndex;

  private Map() {
    throw new IllegalStateException("Not a public class.");
  }

  public static void updateMap(int translation, String cordType) {
    switch (cordType) {
      case "x":
        if (1 > getXCord() + translation || getXCord() + translation > 14) {
          CUtil.wallOfTreesNotification();
          CUtil.entCont();
          mapClear();
        } else {
          mapClear();
          setXCord(getXCord() + translation);
        }
        break;
      case "y":
        if (1 > getYCord() + translation || getYCord() + translation > 6) {
          CUtil.wallOfTreesNotification();
          CUtil.entCont();
          mapClear();
        } else {
          mapClear();
          setYCord(getYCord() + translation);
        }
        break;
      default:
        throw new IllegalArgumentException(
            "Neither 'x' nor 'y' provided to checkMapBounds function.");
    }
    updateMapWithCoordinates();
  }

  public static void updateMapWithCoordinates() {
    String str1 = map1.substring(0, getPlayerCordIndex() - 1);
    String str2 = map1.substring(getPlayerCordIndex(), map1.length());
    String cyanCharacter = CUtil.currentPlayerPosition(map1.substring(getPlayerCordIndex() - 1, getPlayerCordIndex()));
    
    Map.setMap1String(str1 + cyanCharacter + str2);
    Location.scanCurrentTile();
  }

  public static int getPlayerCordIndex(){
    return map1.length() - (15 - xCord) - (17 * yCord);
  }

  public static int getPlayerCordIndex(int xCorda, int yCorda){
    return map1.length() - (15 - xCorda) - (17 * yCorda);
  }

  public static void mapClear() {
    map1 = MAPBLANK;
  }

  public static void setXCord(int axCord) {
    xCord = axCord;
  }

  public static int getXCord() {
    return xCord;
  }

  public static void setYCord(int ayCord) {
    yCord = ayCord;
  }

  public static int getYCord() {
    return yCord;
  }

  public static String getMap1String() {
    return map1;
  }

  public static void setMap1String(String aMap1) {
    map1 = aMap1;
  }

  public static void setCurrentLocation(int index){
    currentLocation = Location.getLocation(index);
    currentLocationIndex = index;
  }

  public static Location getCurrentLocation(){
    return currentLocation;
  }

  public static int getCurrentLocationInt(){
    return currentLocationIndex;
  }
}