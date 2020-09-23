package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

abstract class Player implements Serializable {
    protected int positionX;
    protected int positionY;
    protected String typeToAttack;
    protected int points;



    void moveOneStep(String direction, TETile typeOfAttacker) {
        if (direction.equals("up")) {
            if (MyWorld.world[positionX][positionY + 1].description().equals("floor") || MyWorld.world[positionX][positionY + 1].description().equals(typeToAttack)
                || MyWorld.world[positionX][positionY + 1].description().equals("locked door") && typeToAttack.equals("flower")
                    && MyWorld.round == points)
             {
                if (MyWorld.world[positionX][positionY + 1].description().equals("flower") && typeToAttack.equals("flower")) {
                    attack();
                }
                MyWorld.world[positionX][positionY] = Tileset.FLOOR;
                MyWorld.world[positionX][positionY + 1] = typeOfAttacker;
                positionY += 1;
            }
        } else if (direction.equals("down")) {
            if (MyWorld.world[positionX][positionY- 1].description().equals("floor") || MyWorld.world[positionX][positionY - 1].description().equals(typeToAttack)
                 || MyWorld.world[positionX][positionY - 1].description().equals("locked door") && typeToAttack.equals("flower")
                    && MyWorld.round == points) {
                if (MyWorld.world[positionX][positionY - 1].description().equals("flower") && typeToAttack.equals("flower")) {
                    attack();
                }
                MyWorld.world[positionX][positionY] = Tileset.FLOOR;
                MyWorld.world[positionX][positionY - 1] = typeOfAttacker;
                positionY -= 1;
            }
        } else if (direction.equals("right")) {
            if (MyWorld.world[positionX + 1][positionY].description().equals("floor")  || MyWorld.world[positionX + 1][positionY].description().equals(typeToAttack)
                  || MyWorld.world[positionX + 1][positionY].description().equals("locked door") && typeToAttack.equals("flower")
                    && MyWorld.round == points) {
                if (MyWorld.world[positionX + 1][positionY].description().equals("flower") && typeToAttack.equals("flower")) {
                    attack();
                }
                MyWorld.world[positionX][positionY] = Tileset.FLOOR;
                MyWorld.world[positionX + 1][positionY] = typeOfAttacker;
                positionX += 1;
            }
        } else if (direction.equals("left")) {
            if (MyWorld.world[positionX - 1][positionY].description().equals("floor")  || MyWorld.world[positionX - 1][positionY].description().equals(typeToAttack)
                 || MyWorld.world[positionX - 1][positionY].description().equals("locked door") && typeToAttack.equals("flower")
                    && MyWorld.round == points) {
                if (MyWorld.world[positionX - 1][positionY].description().equals("flower") && typeToAttack.equals("flower")) {
                    attack();
                }
                MyWorld.world[positionX][positionY] = Tileset.FLOOR;
                MyWorld.world[positionX - 1][positionY] = typeOfAttacker;
                positionX -= 1;
            }

        }

    }

    abstract void attack();
}