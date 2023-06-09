package world;

import util.CUtil;

public class Mob {
    private String name;
    private double hp;
    private int maxHp;

    public Mob(String name, double hp){
        this.name = name;
        this.hp = hp;
        this.maxHp = (int)hp;
    }

    public void attackEnemy(double damage){
        hp -= damage;
    }
    
    public String enemyPrint(String print){
        return CUtil.enemyColorFormat(this.getName()) + ": " + print;
      }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setHp(double hp){
        this.hp = hp;
    }

    public double getHp(){
        return hp;
    }

    public void setMaxHp(int maxHp){
        this.maxHp = maxHp;
    }

    public int getMaxHp(){
        return maxHp;
    }
}
