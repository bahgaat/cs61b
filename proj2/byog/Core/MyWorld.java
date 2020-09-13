package byog.Core;

import byog.Core.Position;
import byog.SaveDemo.World;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MouseEvent;
import org.w3c.dom.views.AbstractView;

import java.awt.*;
import java.io.*;
import java.util.Random;


/**
 *  Draws a random world.
 */
public class MyWorld implements Serializable {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    static TETile[][] world = new TETile[WIDTH][HEIGHT];
    static Position hallWayPosition;
    File f;

    /* draw line, its position determines if it is vertical or horizontal, and its direction determines if the line
    is positive or negative. While drawing any line, we start counting from the present line. for eg, if i want to
    draw aline 1 step from where i am standing, I have to move 2 steps because the step that i stand on is counted. */
    static void drawLine(TETile[][] world, Position p, int size,
                          TETile type, String position, String direction) {
        while (size > 0) {
            try {
                world[p._x][p._y] = type;
            } catch (Exception e) {
                break;
            }

            if (position.equals("vertical") && direction.equals("positive") && size > 1) {
                p._y += 1;
            } else if (position.equals("vertical") && direction.equals("negative") && size > 1) {
                p._y -= 1;
            } else if (position.equals("horizontal") && direction.equals("positive") && size > 1) {
                p._x += 1;
            } else if (position.equals("horizontal") && direction.equals("negative") && size > 1) {
                p._x -= 1;
            }
            p._type = type;
            size -= 1;
        }
    }

    private static void moveOneStep(TETile[][] world, Position p,
                             TETile type, String position, String direction) {
        if (position.equals("vertical") && direction.equals("positive")) {
            p._y += 1;
        } else if (position.equals("vertical") && direction.equals("negative")) {
            p._y -= 1;
        } else if (position.equals("horizontal") && direction.equals("positive")) {
            p._x += 1;
        } else if (position.equals("horizontal") && direction.equals("negative")) {
            p._x -= 1;
        }
    }

    /* draw letter L, Start drawing from the vertical line. The direction determines if it is a typical letter L
     or a negative L, which is similar to L but looks toward the left side. */
    private static void drawLStartFromVerticalLine(TETile[][] world, Position p, int sizeOfVerticalPartOfL,
                                            int sizeOfHorizontalPartOfL,  TETile type, String direction) {
        drawLine(world, p, sizeOfVerticalPartOfL, type, "vertical", "negative");
        drawLine(world, p, sizeOfHorizontalPartOfL, type, "horizontal", direction);
    }

    /* draw the opposite of L, Start drawing from the vertical line. The direction determines if is a positive
    opposite of L, which looks to the right or a Negative opposite, which looks to the left.. */
    private static void drawOppositeLStartFromVerticalLine(TETile[][] world, Position p, int sizeOfVerticalPartOfOppositeL,
                                                    int sizeOfHorizontalPartOfOppositeL, TETile type, String direction) {
        drawLine(world, p, sizeOfVerticalPartOfOppositeL, type, "vertical", "positive");
        drawLine(world, p, sizeOfHorizontalPartOfOppositeL, type, "horizontal", direction);
    }

    /* draw letter L, Start drawing from the horizontal line. The direction determines if it is a typical letter L
     or a negative L, which is similar to L but looks toward the left side. */
    private static void drawLStartFromHorizontalLine(TETile[][] world, Position p, int sizeOfHorizontalLine,
                                              int sizeOfVerticalLine, TETile type, String direction) {
        String directionOfHorizontalLine;
        if (direction.equals("positive")) {
            directionOfHorizontalLine = "negative";
        } else {
            directionOfHorizontalLine = "positive";
        }
        drawLine(world, p, sizeOfHorizontalLine, type, "horizontal", directionOfHorizontalLine);
        drawLine(world, p, sizeOfVerticalLine, type, "vertical", "positive");
    }

    /* draw the opposite of L, Start drawing from the horizontal line. The direction determines if is a positive
    opposite of L, which looks to the right or a negative opposite, which looks to the left.. */
    private static void drawOppositeOfLStartFromHorizontalLine(TETile[][] world, Position p, int sizeOfHorizontalLine,
                                                        int sizeOfVerticalLine, TETile type, String direction) {
        String directionOfHorizontalLine;
        if (direction.equals("positive")) {
            directionOfHorizontalLine = "negative";
        } else {
            directionOfHorizontalLine = "positive";
        }
        drawLine(world, p, sizeOfHorizontalLine, type, "horizontal", directionOfHorizontalLine);
        drawLine(world, p, sizeOfVerticalLine, type, "vertical", "negative");
    }

    /* draw a shape which is similar to half square, which looks to the left side. Start drawing the shape from bottom.*/
    private static void drawLeftHalfSquare(TETile[][] world, Position p, int sizeOfBottomHorizontalLine,
                                    int sizeOfVerticalLine, int sizeOfUpperHorizontalLine, TETile type) {
        drawLine(world, p, sizeOfBottomHorizontalLine, type, "horizontal", "positive");
        drawLine(world, p, sizeOfVerticalLine, type, "vertical", "positive");
        drawLine(world, p, sizeOfUpperHorizontalLine, type, "horizontal", "negative");
    }

    /* draw a shape which is similar to half square, which looks to the right. Start drawing the shape from bottom */
    private static void drawRightHalfSquare(TETile[][] world, Position p, int sizeOfBottomHorizontalLine,
                                     int sizeOfVerticalLine, int sizeOfUpperHorizontalLine, TETile type) {
        drawLine(world, p, sizeOfBottomHorizontalLine, type, "horizontal", "negative");
        drawLine(world, p, sizeOfVerticalLine, type, "vertical", "positive");
        drawLine(world, p, sizeOfUpperHorizontalLine, type, "horizontal", "positive");
    }

    /* draw a shape which is similar to half square, which looks down. Start drawing the shape from right. */
    private static void drawBottomHalfSquare(TETile[][] world, Position P, int sizeOfRightVerticalLine,
                                      int sizeOfHorizontalLine, int sizeOfLeftVerticalLine, TETile type) {
        drawLine(world, P, sizeOfRightVerticalLine, type, "vertical", "positive");
        drawLine(world, P, sizeOfHorizontalLine, type, "horizontal", "negative");
        drawLine(world, P, sizeOfLeftVerticalLine, type, "vertical", "negative");
    }

    /* draw a shape which is similar to half square, which looks up. Start drawing the shape from right. */
    private static void drawUpperHalfSquare(TETile[][] world, Position p, int sizeOfRightVerticalLine,
                                     int sizeOfHorizontalLine, int sizeOfLeftVerticalLine, TETile type) {
        drawLine(world, p, sizeOfRightVerticalLine, type, "vertical", "negative");
        drawLine(world, p, sizeOfHorizontalLine, type, "horizontal", "negative");
        drawLine(world, p, sizeOfLeftVerticalLine, type, "vertical", "positive");
    }

    /* draw a rectangle, which looks down. its direction determines if the rectangle will be drawn
    toward right or left. the shape ends at the highest part of the last size */
    private static void drawBottomRectangle(TETile[][] world, Position p, int sizeOfVerticalLine,
                                     int sizeOfHorizontalLine, TETile type, String direction) {
        String directionOfHorizontalLine;
        if (direction.equals("positive")) {
            directionOfHorizontalLine = "positive";
        } else {
            directionOfHorizontalLine = "negative";
        }
        if (sizeOfHorizontalLine == 1) {
            drawLine(world, p, sizeOfVerticalLine, type, "vertical", "negative");
            drawLine(world, p, sizeOfVerticalLine, type, "vertical", "positive");
        }
        while (sizeOfHorizontalLine > 1) {
            drawLine(world, p, sizeOfVerticalLine, type, "vertical", "negative");
            drawLine(world, p, 2, type, "horizontal", directionOfHorizontalLine);
            drawLine(world, p, sizeOfVerticalLine, type, "vertical", "positive");
            sizeOfHorizontalLine -= 1;
        }
    }


    /* draw a rectangle, which looks up. its direction determines if the rectangle will be drawn
    toward right or left. The shape ends at the lowest part of the last size. */
    private static void drawUpperRectangle(TETile[][] world, Position P, int sizeOfVerticalLine,
                                    int sizeOfHorizontalLine, TETile type, String direction) {

        String directionOfHorizontalLine;
        if (direction.equals("positive")) {
            directionOfHorizontalLine = "positive";
        } else {
            directionOfHorizontalLine = "negative";
        }
        if (sizeOfHorizontalLine == 1) {
            drawLine(world, P, sizeOfVerticalLine, type, "vertical", "positive");
            drawLine(world, P, sizeOfVerticalLine, type, "vertical", "negative");
        }
        while (sizeOfHorizontalLine > 1) {
            drawLine(world, P, sizeOfVerticalLine, type, "vertical", "positive");
            drawLine(world, P, 2, type, "horizontal", directionOfHorizontalLine);
            drawLine(world, P, sizeOfVerticalLine, type, "vertical", "negative");
            sizeOfHorizontalLine -= 1;
        }
    }


    /* draw first part of the world. its direction is toward left. I divide drawing the first part
    into   3 parts. Upper wall, Bottom wall, Hallway. */
    private static void drawFirstPartOfTheWorld(TETile[][] world, Position upperPosition, Position bottomPosition,
                                                Position hallwayPosition, int i, Random r) {
        int h = RandomUtils.uniform(r, 4, 6);
        for (int j = 0; j < h; j += 1) {
            int randomNumber = r.nextInt(3);
            drawUpperWallOfTheFirstPartOfTheWorld(world, upperPosition, j, i, randomNumber);
            drawBottomWallOfTheFirstPartOfTheWorld(world, bottomPosition, j, i, randomNumber);
            drawHallWayOfTheFirstPartOfTheWorld(world, hallwayPosition, j, i, randomNumber);
        }
    }

    /* draw upper wall of the first part of the world, which moves toward left. */
    private static void drawUpperWallOfTheFirstPartOfTheWorld(TETile[][] world, Position upperPosition,
                                                       int j, int i, int randomNumber) {
        if (j == 0 && randomNumber >= 2) {
            drawLine(world, upperPosition, i, Tileset.WALL, "horizontal", "negative");
            drawLStartFromVerticalLine(world, upperPosition, i, i, Tileset.WALL, "negative");
        } else if (j == 0 && randomNumber < 2) {
            drawLine(world, upperPosition, i * 2, Tileset.WALL, "horizontal", "negative");
        } else if (randomNumber == 0) {
            drawBottomHalfSquare(world, upperPosition, i + j, i + j, i + j, Tileset.WALL);
            drawLStartFromVerticalLine(world, upperPosition, i + j, i + j, Tileset.WALL, "negative");
        } else if (randomNumber == 1) {
            drawBottomHalfSquare(world, upperPosition, i + j, i + j, i + j, Tileset.WALL);
            drawLine(world, upperPosition, i, Tileset.WALL, "horizontal", "positive");
            drawLine(world, upperPosition, i * 2, Tileset.WALL, "horizontal", "negative");
            /*
        } else if (randomNumber == 2) {
            drawLine(world, upperPosition, i * 4, Tileset.WALL, "horizontal", "negative");

             */
        } else if (randomNumber == 2) {
            drawOppositeLStartFromVerticalLine(world, upperPosition, i + j + 2, i, Tileset.WALL, "positive");
            drawBottomHalfSquare(world, upperPosition, i + j, i * 2, i + j, Tileset.WALL);
            drawOppositeOfLStartFromHorizontalLine(world, upperPosition, i - 2, i + j + 2, Tileset.WALL, "negative");
            drawLine(world, upperPosition, i * 2, Tileset.WALL, "horizontal", "negative");
        }
    }

    /* draw bottom wall of the first part of the world, which moves toward left. */
    private static void drawBottomWallOfTheFirstPartOfTheWorld(TETile[][] world, Position bottomPosition,
                                                        int j, int i, int randomNumber) {
        if (j == 0 && randomNumber >= 2) {
            drawLine(world, bottomPosition, i + 2, Tileset.WALL, "vertical", "negative");
            drawLine(world, bottomPosition, i * 2 - 1, Tileset.WALL, "horizontal", "negative");
        } else if (j == 0 && randomNumber < 2) {
            drawUpperHalfSquare(world, bottomPosition, i * 2, i, i * 2 - 2, Tileset.WALL);
            drawLine(world, bottomPosition, i + 1, Tileset.WALL, "horizontal", "negative");
        } else if (randomNumber == 0) {
            drawLStartFromVerticalLine(world, bottomPosition, i + j, (i + j) * 2 - 1, Tileset.WALL, "negative");
        } else if (randomNumber == 1) {
            drawUpperHalfSquare(world, bottomPosition, i + j, i + j + 3, i + j, Tileset.WALL);
            drawLine(world, bottomPosition, i, Tileset.WALL, "horizontal", "positive");
            drawLine(world, bottomPosition, i * 2 - 3, Tileset.WALL, "horizontal", "negative");
            /*
        } else if (randomNumber == 2) {
            drawLStartFromVerticalLine(world, bottomPosition, i + j + 2, i + j + 2, Tileset.WALL, "positive");
            drawUpperHalfSquare(world, bottomPosition, i + j + 2, i + j + 2, i + j, Tileset.WALL);
            drawLStartFromHorizontalLine(world, bottomPosition, i, i + j + 4, Tileset.WALL, "positive");
            drawLine(world, bottomPosition, i * 3 + 1, Tileset.WALL, "horizontal", "negative");

             */
        } else if (randomNumber == 2) {
            drawLine(world, bottomPosition, i * 2 - (i - 2) + i + 1, Tileset.WALL, "horizontal", "negative");
        }
    }

    /* draw hallway of the first part of the game, which moves toward left. */
    private static void drawHallWayOfTheFirstPartOfTheWorld(TETile[][] world, Position hallWayPosition,
                                                     int j, int i, int randomNumber) {
        if (j == 0 && randomNumber >= 2) {
            drawBottomRectangle(world, hallWayPosition, i, i - 2, Tileset.FLOOR, "negative");
            drawLStartFromVerticalLine(world, hallWayPosition, i, i + 1, Tileset.FLOOR, "negative");
        } else if (j == 0 && randomNumber < 2) {
            drawBottomRectangle(world, hallWayPosition, (i * 2) - 2, i - 2, Tileset.FLOOR, "negative");
            drawLine(world, hallWayPosition, i + 2, Tileset.FLOOR, "horizontal", "negative");
        } else if (randomNumber == 0) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
            drawUpperRectangle(world, hallWayPosition, i + j, (i + j) - 2, Tileset.FLOOR, "negative");
            drawBottomRectangle(world, hallWayPosition, i + j, i + j - 2, Tileset.FLOOR, "positive");
            drawOppositeOfLStartFromHorizontalLine(world, hallWayPosition, i + j - 2, i + j, Tileset.FLOOR, "positive");
            drawLine(world, hallWayPosition, i + j + 1, Tileset.FLOOR, "horizontal", "negative");
        } else if (randomNumber == 1) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "vertical", "positive");
            drawUpperRectangle(world, hallWayPosition, i + j - 1, i + j - 2, Tileset.FLOOR, "negative");
            drawOppositeOfLStartFromHorizontalLine(world, hallWayPosition, i + j - 2, 3, Tileset.FLOOR, "negative");
            drawBottomRectangle(world, hallWayPosition, i + j - 1, i + j + 3 - 2, Tileset.FLOOR, "negative");
            drawLStartFromHorizontalLine(world, hallWayPosition, i + j + 3 - 2, 2, Tileset.FLOOR, "negative");
            drawLine(world, hallWayPosition, (i * 2) + j - 1, Tileset.FLOOR, "horizontal", "negative");
            /*
        } else if (randomNumber == 2) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
            drawBottomRectangle(world, hallWayPosition, i + j + 4, i - 2, Tileset.FLOOR, "negative");
            drawLStartFromVerticalLine(world, hallWayPosition, i + j + 4, i, Tileset.FLOOR, "positive");
            drawBottomRectangle(world, hallWayPosition, i + j, i + j, Tileset.FLOOR, "positive");
            drawLStartFromHorizontalLine(world, hallWayPosition, (i + j + 1) + (i - 2), i + j + 4, Tileset.FLOOR,
                    "positive");
            drawLine(world, hallWayPosition, i * 3 + 2, Tileset.FLOOR, "horizontal", "negative");

             */
        } else if (randomNumber == 2) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
            drawUpperRectangle(world, hallWayPosition, i + j + 3, i - (i - 2), Tileset.FLOOR, "negative");
            drawLine(world, hallWayPosition, i + j + 4, Tileset.FLOOR, "vertical", "positive");
            drawUpperRectangle(world, hallWayPosition, i + j - 2, i * 2 - 1 - (i - 2), Tileset.FLOOR, "positive");
            if (i > 3) {
                drawLine(world, hallWayPosition, i * 2 - (i - 2), Tileset.FLOOR, "horizontal", "negative");
                drawUpperRectangle(world, hallWayPosition, i + j - 2, i - 2 - 1, Tileset.FLOOR, "negative");
                drawOppositeOfLStartFromHorizontalLine(world, hallWayPosition, i - 2, i + j + 4, Tileset.FLOOR,
                        "negative");
                drawLine(world, hallWayPosition, i * 2 + 1, Tileset.FLOOR, "horizontal", "negative");
            } else {
                drawLine(world, hallWayPosition, i + 1, Tileset.FLOOR, "horizontal", "negative");
                drawLine(world, hallWayPosition, i + j + 4, Tileset.FLOOR, "vertical", "negative");
                drawLine(world, hallWayPosition, i * 2 + 1, Tileset.FLOOR, "horizontal", "negative");
            }
        }
    }

    /* draw second part of the world. its direction is toward up. I divide drawing the second part into 3 parts
   Upper wall, Bottom wall, and hallway. */
    private static void drawSecondPartOfTheWorld(TETile[][] world, Position upperPosition, Position bottomPosition,
                                                 Position hallwayPosition, int i, Random r) {
        int h = RandomUtils.uniform(r, 4, 6);
        for (int j = 0; j < h; j += 1) {
            int randomNumber = r.nextInt(3);
            drawUpperWallOfTheSecondPartOfTheWorld(world, upperPosition, j, i, randomNumber);
            drawBottomWallOfTheSecondPartOfTheWorld(world, bottomPosition, j, i, randomNumber);
            drawHallWayOfTheSecondPartOfTheWorld(world, hallwayPosition, j, i, randomNumber);
        }
    }

    /* draw upper wall of the second part of the world. which moves toward up. */
    private static void drawUpperWallOfTheSecondPartOfTheWorld(TETile[][] world, Position upperPosition,
                                                        int j, int i, int randomNumber) {
        if (j == 0) {
            drawLine(world, upperPosition, 3, Tileset.WALL, "horizontal", "negative");
            drawLStartFromVerticalLine(world, upperPosition, i + 8 + 8 + 1 + 4, i * 16 + 2, Tileset.WALL, "positive");
            drawLStartFromHorizontalLine(world, upperPosition, 3, i + 2, Tileset.WALL, "negative");
        } else if (randomNumber == 0) {
            drawLeftHalfSquare(world, upperPosition, i, i, i, Tileset.WALL);
            drawLine(world, upperPosition, i, Tileset.WALL, "vertical", "positive");
        } else if (randomNumber == 1) {
            drawLeftHalfSquare(world, upperPosition, i + 2, i + 2, i + 2, Tileset.WALL);
            drawLine(world, upperPosition, i, Tileset.WALL, "vertical", "negative");
            drawLine(world, upperPosition, i * 2, Tileset.WALL, "vertical", "positive");
        } else if (randomNumber == 2) {
            drawLine(world, upperPosition, i * 2 + 2, Tileset.WALL, "vertical", "positive");
        }
    }

    /* draw bottom wall of the second part of the world. which moves toward up. */
    private static void drawBottomWallOfTheSecondPartOfTheWorld(TETile[][] world, Position bottomPosition,
                                                         int j, int i, int randomNumber) {
        if (j == 0) {
            drawLStartFromVerticalLine(world, bottomPosition, i + 8 + 4 + 1 + 4, i * 16, Tileset.WALL, "positive");
            drawLine(world, bottomPosition, i, Tileset.WALL, "vertical", "positive");
        } else if (randomNumber == 0) {
            drawRightHalfSquare(world, bottomPosition, i, i, i, Tileset.WALL);
            drawLine(world, bottomPosition, i, Tileset.WALL, "vertical", "positive");
        } else if (randomNumber == 1) {
            drawLine(world, bottomPosition, i * 2 + 2, Tileset.WALL, "vertical", "positive");
        } else if (randomNumber == 2) {
            drawRightHalfSquare(world, bottomPosition, i + 2, i * 2, i + 2, Tileset.WALL);
            drawLine(world, bottomPosition, i * 2 - 2, Tileset.WALL, "vertical", "negative");
            drawLine(world, bottomPosition, i * 2, Tileset.WALL, "vertical", "positive");
        }
    }

    /* draw hallway of the second part of the world. which moves toward up. */
    private static void drawHallWayOfTheSecondPartOfTheWorld(TETile[][] world, Position hallWayPosition,
                                                      int j, int i, int randomNumber) {
        if (j == 0) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
            drawLStartFromVerticalLine(world, hallWayPosition, i + 8 + 8 + 1 + 2, i * 16 + 1, Tileset.FLOOR,
                    "positive");
            drawLStartFromHorizontalLine(world, hallWayPosition, 2, i + 1, Tileset.FLOOR, "negative");
        } else if (randomNumber == 0) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "vertical", "positive");
            drawUpperRectangle(world, hallWayPosition, i - 2, i, Tileset.FLOOR, "negative");
            drawLine(world, hallWayPosition, i, Tileset.FLOOR, "horizontal", "positive");
            drawUpperRectangle(world, hallWayPosition, i - 2, i, Tileset.FLOOR, "positive");
            drawLStartFromHorizontalLine(world, hallWayPosition, i, i - 1, Tileset.FLOOR, "positive");
            drawLine(world, hallWayPosition, i, Tileset.FLOOR, "vertical", "positive");
        } else if (randomNumber == 1) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "vertical", "positive");
            drawLine(world, hallWayPosition, 3, Tileset.FLOOR, "horizontal", "positive");
            drawUpperRectangle(world, hallWayPosition, i, i, Tileset.FLOOR, "positive");
            drawLine(world, hallWayPosition, i + 2, Tileset.FLOOR, "horizontal", "negative");
            drawLine(world, hallWayPosition, i * 2 + 1, Tileset.FLOOR, "vertical", "positive");
        } else if (randomNumber == 2) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "vertical", "positive");
            drawLine(world, hallWayPosition, 3, Tileset.FLOOR, "horizontal", "negative");
            drawUpperRectangle(world, hallWayPosition, i * 2 - 2, i, Tileset.FLOOR, "negative");
            drawLine(world, hallWayPosition, i + 2, Tileset.FLOOR, "horizontal", "positive");
            drawLine(world, hallWayPosition, i * 2 + 1, Tileset.FLOOR, "vertical", "positive");
        }
    }

    private static void drawThirdPartOfTheWorld(TETile[][] world, Position upperPosition, Position bottomPosition,
                                                Position hallwayPosition, int i, Random r) {
        int h = RandomUtils.uniform(r, 2, 3);
        for (int j = 0; j < 3; j += 1) {
            int randomNumber = r.nextInt(2);
            drawUpperWallOfTheThirdPartOfTheWorld(world, upperPosition, j, i, randomNumber, r);
            drawBottomWallOfTheThirdPartOfTheWorld(world, bottomPosition, j, i, randomNumber, r);
            drawHallWayOfTheThirdPartOfTheWorld(world, hallwayPosition, j, i, randomNumber, r);


        }
    }

    private static void drawUpperWallOfTheThirdPartOfTheWorld(TETile[][] world, Position upperPosition,
                                                               int j, int i, int randomNumber, Random r) {
        if (j == 0) {
            drawOppositeLStartFromVerticalLine(world, upperPosition, 3, i * 2 + 4,
                    Tileset.WALL, "negative");
             while (upperPosition._y > 50) {
                 drawLine(world, upperPosition,  2, Tileset.WALL, "vertical", "negative");
             }


            drawLine(world, upperPosition, i, Tileset.WALL, "horizontal", "negative");
        } else if (randomNumber == 0) {

            j = 0;
            drawLine(world, upperPosition, i * 4 + 2 , Tileset.WALL, "horizontal", "negative");
            drawLStartFromVerticalLine(world, upperPosition, i * 2, i * 2, Tileset.WALL, "negative");
        } else if (randomNumber == 1 || randomNumber == 2) {
            drawLine(world, upperPosition, i * 2 + 2, Tileset.WALL, "horizontal", "negative");
        }
    }

    private static void drawBottomWallOfTheThirdPartOfTheWorld(TETile[][] world, Position bottomPosition,
                                                               int j, int i, int randomNumber, Random r) {
        if (j == 0) {
            drawLine(world, bottomPosition, i * 2, Tileset.WALL, "horizontal", "negative");

            while (bottomPosition._y > 48) {
                drawLine(world, bottomPosition,  2, Tileset.WALL, "vertical", "negative");
            }
            /*
            drawLStartFromVerticalLine(world, bottomPosition, i - 1, i + 2,
                    Tileset.WALL, "negative");
             */
            drawLine(world, bottomPosition, i + 2, Tileset.WALL, "horizontal", "negative");
        } else if (randomNumber == 0) {

            j = 0;
            drawLStartFromVerticalLine(world, bottomPosition, i + j + 2, i + j + 2, Tileset.WALL, "positive");
            drawUpperHalfSquare(world, bottomPosition, i + j + 2, i + j + 2, i + j, Tileset.WALL);
            drawLStartFromHorizontalLine(world, bottomPosition, i, i + j + 4, Tileset.WALL, "positive");
            drawLine(world, bottomPosition, i * 3 + 1, Tileset.WALL, "horizontal", "negative");
            drawLStartFromVerticalLine(world, bottomPosition, i * 2, i * 2 + 2, Tileset.WALL, "negative");
        } else if (randomNumber == 1 || randomNumber == 2) {
            drawUpperHalfSquare(world, bottomPosition, i + 2, i + 2, i + 2, Tileset.WALL);
            drawLine(world, bottomPosition, i + 1, Tileset.WALL, "horizontal", "negative");
        }

    }

    private static void drawHallWayOfTheThirdPartOfTheWorld(TETile[][] world, Position hallWayPosition,
                                                            int j, int i, int randomNumber, Random r) {
        if (j == 0) {
            drawOppositeLStartFromVerticalLine(world, hallWayPosition, 2, i * 2 + 2,
                    Tileset.FLOOR, "negative");

            while (hallWayPosition._y > 49) {
                drawLine(world, hallWayPosition,  2, Tileset.FLOOR, "vertical", "negative");
            }
            /*
            drawLStartFromVerticalLine(world, hallWayPosition, i - 2 , i + 1,
                    Tileset.FLOOR, "negative");
             */
            drawLine(world, hallWayPosition, i + 1, Tileset.FLOOR, "horizontal", "negative");
        } else if (randomNumber == 0) {

            j = 0;
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
            drawBottomRectangle(world, hallWayPosition, i + j + 4, i - 2, Tileset.FLOOR, "negative");
            drawLStartFromVerticalLine(world, hallWayPosition, i + j + 4, i, Tileset.FLOOR, "positive");
            drawBottomRectangle(world, hallWayPosition, i + j, i + j, Tileset.FLOOR, "positive");
            drawLStartFromHorizontalLine(world, hallWayPosition, (i + j + 1) + (i - 2), i + j + 4, Tileset.FLOOR,
                    "positive");
            drawLine(world, hallWayPosition, i * 3 + 3, Tileset.FLOOR, "horizontal", "negative");
            drawLStartFromVerticalLine(world, hallWayPosition, i * 2, i * 2 + 1, Tileset.FLOOR, "negative");
        } else if (randomNumber == 1 || randomNumber == 2)  {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
            drawBottomRectangle(world, hallWayPosition, i + 2 , i + 2 - 2 , Tileset.FLOOR, "negative");
            drawLine(world, hallWayPosition, i + 2, Tileset.FLOOR, "horizontal", "negative");
        }

    }




    /* draw the whole world, I divide the drawing into 2 parts.*/
    static TETile[][]  drawWorld(long seed) {
        Random r = new Random(seed);
        int y = RandomUtils.uniform(r, 60, 65);
        int x = RandomUtils.uniform(r, 50, 60);

        for (int p = 0; p < WIDTH; p += 1) {
            for (int g = 0; g < HEIGHT; g += 1) {
                world[p][g] = Tileset.NOTHING;
            }
        }

        Position upperPosition = new Position(x, y, Tileset.WALL);
        Position bottomPosition = new Position(x, y, Tileset.WALL);
        hallWayPosition = new Position(x - 1, y - 1, Tileset.FLOOR);
        int i = RandomUtils.uniform(r, 3, 5  );
        drawFirstPartOfTheWorld(world, upperPosition, bottomPosition, hallWayPosition, i, r);
        drawSecondPartOfTheWorld(world, upperPosition, bottomPosition, hallWayPosition, i, r);
        drawThirdPartOfTheWorld(world, upperPosition, bottomPosition, hallWayPosition, i, r);
        drawLine(world, bottomPosition, 2, Tileset.WALL, "vertical", "positive");
        return world;
    }

     static void drawFrame(String s) {
        StdDraw.setCanvasSize(40 * 16, 40 * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, 40);
        StdDraw.setYscale(0, 40);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear();
        StdDraw.clear(Color.black);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);

        if (s.equals("Ui")) {
            StdDraw.text(20, 20, "NewGame(N)");
            StdDraw.text(20, 18, "LoadGame(L)");
            StdDraw.text(20, 16, "Quit(Q)");
        } else {
            StdDraw.text(20, 20, s);
        }
        StdDraw.show();
        StdDraw.pause(1500);
    }


    private static void readFromTheUserBeforeStartingTheGame() {
        if (StdDraw.hasNextKeyTyped()) {
            Player player;
            char keyTypedFromUser = StdDraw.nextKeyTyped();
            if (keyTypedFromUser == 'N') {
                drawFrame("Enter the seed");
                String seed = "";
                while (true) {
                    if (StdDraw.hasNextKeyTyped()) {
                        char keyTypedFromTheUser = StdDraw.nextKeyTyped();
                        String convertCharToString = String.valueOf(keyTypedFromTheUser);
                        if (keyTypedFromTheUser == 'S') {
                            TERenderer ter = new TERenderer();
                            ter.initialize(MyWorld.WIDTH, MyWorld.HEIGHT);
                            long convertStringToLong = Long.parseLong(seed);
                            world = drawWorld(convertStringToLong);
                            player = new Player();
                            while (true) {
                                ter.renderFrame(world);
                                playGame(world, player, 'p');
                            }
                        } else {
                            seed += convertCharToString;
                            drawFrame(seed);
                        }
                    }
                }
                } else if (keyTypedFromUser == 'L') {
                    TERenderer ter = new TERenderer();
                    ter.initialize(MyWorld.WIDTH, MyWorld.HEIGHT);
                    world = loadWorld();
                    player = loadPlayer();
                    while (true) {
                        ter.renderFrame(world);
                        playGame(world, player, 'p');

                    }
                }
            }
        }


    static void playGame(TETile[][] world, Player player, char keyTypedFromTheUser) {
        if (keyTypedFromTheUser == 'p') {
            if (StdDraw.hasNextKeyTyped()) {
                keyTypedFromTheUser = StdDraw.nextKeyTyped();
            }
        }
        if (keyTypedFromTheUser == 'W' || keyTypedFromTheUser == 'w') {
            player.moveOneStep("up");
        } else if (keyTypedFromTheUser == 'A' || keyTypedFromTheUser == 'a') {
            player.moveOneStep("left");
        } else if (keyTypedFromTheUser== 'S' || keyTypedFromTheUser == 's') {
            player.moveOneStep("down");
        } else if (keyTypedFromTheUser == 'D' || keyTypedFromTheUser == 'd') {
            player.moveOneStep("right");
        } else if (keyTypedFromTheUser == 'Q' || keyTypedFromTheUser == 'q') {
            saveGame(world, player);
        }
    }


    static void saveGame(TETile[][] world, Player player)  {
        try {
            FileOutputStream fosWorld = new FileOutputStream("world.txt");
            ObjectOutputStream oosWorld = new ObjectOutputStream(fosWorld);
            oosWorld.writeObject(world);
            fosWorld.close();
            oosWorld.close();
            FileOutputStream fosPlayer = new FileOutputStream("player1.txt");
            ObjectOutputStream oosPlayer = new ObjectOutputStream(fosPlayer);
            oosPlayer.writeObject(player);
            fosWorld.close();
            oosWorld.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    static TETile[][] loadWorld()  {
        try {
            FileInputStream fisWorld = new FileInputStream("world.txt");
            ObjectInputStream oisWorld = new ObjectInputStream(fisWorld);
            TETile[][] world = (TETile[][]) oisWorld.readObject();
            oisWorld.close();
            fisWorld.close();
            return world;
        } catch (Exception e) {
            e.printStackTrace();
            return new TETile[0][0];
        }
    }

    static Player loadPlayer() {
        try {
            FileInputStream fisPlayer = new FileInputStream("player1.txt");
            ObjectInputStream oisPlayer = new ObjectInputStream(fisPlayer);
            Player player = (Player) oisPlayer.readObject();
            oisPlayer.close();
            fisPlayer.close();
            return player;
        } catch (Exception e) {
            e.printStackTrace();
            return new Player();
        }
    }



    static void startGame() {
        try {
            drawFrame("Ui");
            readFromTheUserBeforeStartingTheGame();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}






