package byog;

import byog.Core.RandomUtils;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import byog.lab5.HexWorld;

import java.util.Random;

public class MyGame {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    /* draw line, its position determines if it is vertical or horizontal, and its direction determines if the line is positive or negative.
    While drawing any line, we start counting from the present line. for eg, if i want to draw aline 1 step form
    where i am standing, I have to move 2 steps because the step that i stand on is counted. */
    private void drawLine(TETile[][] world, Position p, int size, TETile type, String position, String direction) {
        while (size > 0) {
            world[p._x][p._y] = type;
            if (position == "vertical" && direction == "positive" && size > 1) {
                p._y += 1;
            } else if (position == "vertical" && direction == "negative" && size > 1) {
                p._y -= 1;
            } else if (position == "horizontal" && direction == "positive" && size > 1) {
                p._x += 1;
            } else if (position == "horizontal" && direction == "negative" && size > 1) {
                p._x -= 1;
            }
            size -= 1;
        }
    }

    /* draw letter L, Start drawing from the vertical line. The direction determines if it is a typical letter L
     or a negative L, which is similar to L but looks toward the left side. */
    private void drawLStartFromVerticalLine(TETile[][] world, Position p, int sizeOfVerticalPartOfL, int sizeOfHorizontalPartOfL,
                                            TETile type, String direction) {
        drawLine(world, p, sizeOfVerticalPartOfL, type, "vertical", "negative");
        drawLine(world, p, sizeOfHorizontalPartOfL, type, "horizontal", direction);
    }

    /* draw the opposite of L, Start drawing from the vertical line. The direction determines if is a positive
    opposite of L, which looks to the right or a Negative opposite, which looks to the left.. */
    private void drawOppositeLStartFromVerticalLine(TETile[][] world, Position p, int sizeOfVerticalPartOfOppositeL, int sizeOfHorizontalPartOfOppositeL,
                                                    TETile type, String direction) {
        drawLine(world, p, sizeOfVerticalPartOfOppositeL, type, "vertical", "positive");
        drawLine(world, p, sizeOfHorizontalPartOfOppositeL, type, "horizontal", direction);
    }

    /* draw letter L, Start drawing from the horizontal line. The direction determines if it is a typical letter L
     or a negative L, which is similar to L but looks toward the left side. */
    private void drawLStartFromHorizontalLine(TETile[][] world, Position p, int sizeOfHorizontalLine,
                                              int sizeOfVerticalLine, TETile type, String direction) {
        String directionOfHorizontalLine;
        if (direction == "positive") {
            directionOfHorizontalLine = "negative";
        } else {
            directionOfHorizontalLine = "positive";
        }
        drawLine(world, p, sizeOfHorizontalLine, type, "horizontal", directionOfHorizontalLine);
        drawLine(world, p, sizeOfVerticalLine, type, "vertical", "positive");
    }

    /* draw the opposite of L, Start drawing from the horizontal line. The direction determines if is a positive
    opposite of L, which looks to the right or a negative opposite, which looks to the left.. */
    private void drawOppositeOfLStartFromHorizontalLine(TETile[][] world, Position p, int sizeOfHorizontalLine,
                                                        int sizeOfVerticalLine, TETile type, String direction) {
        String directionOfHorizontalLine;
        if (direction == "positive") {
            directionOfHorizontalLine = "negative";
        } else {
            directionOfHorizontalLine = "positive";
        }
        drawLine(world, p, sizeOfHorizontalLine, type, "horizontal", directionOfHorizontalLine);
        drawLine(world, p, sizeOfVerticalLine, type, "vertical", "negative");
    }

    /* draw the whole game, I divide the drawing into 2 parts.*/
    public TETile[][] drawGame(long randomNumber) {
        Random r = new Random(randomNumber);
        int y = RandomUtils.uniform(r, 60, 65);
        int x = RandomUtils.uniform(r, 60, 70);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int p = 0; p < WIDTH; p += 1) {
            for (int g = 0; g < HEIGHT; g += 1) {
                world[p][g] = Tileset.NOTHING;
            }
        }

        Position upperPosition = new Position(x, y);
        Position bottomPosition = new Position(x, y);
        Position hallWayPosition = new Position(x - 1, y - 1);
        int i = RandomUtils.uniform(r, 3, 5);
        drawFirstPartOfTheGame(world, upperPosition, bottomPosition, hallWayPosition, i, r);
        drawSecondPartOfTheGame(world, upperPosition, bottomPosition, hallWayPosition, i, r);
        drawLine(world, bottomPosition, 2, Tileset.WALL, "horizontal", "positive");
        return world;
    }

    /* draw first part of the game. its direction is toward left. */
    private void drawFirstPartOfTheGame(TETile[][] world, Position upperPosition, Position bottomPosition,
                                        Position hallwayPosition, int i, Random r) {
        int h = RandomUtils.uniform(r, 4, 6);
        for (int j = 0; j < h; j += 1) {
            int randomNumber = r.nextInt(4);
            drawUpperWallOfTheFirstPartOfTheGame(world, upperPosition, j, i, randomNumber);
            drawBottomWallOfTheFirstPartOfTheGame(world, bottomPosition, j, i, randomNumber);
            drawHallWayOfTheFirstPartOfTheGame(world, hallwayPosition, j, i, randomNumber);
        }
    }

    /* draw second part of the game. its direction is toward up. */
    private void drawSecondPartOfTheGame(TETile[][] world, Position upperPosition, Position bottomPosition,
                                         Position hallwayPosition, int i, Random r) {
        int h = RandomUtils.uniform(r, 5, 7);
        for (int j = 0; j < h; j += 1) {
            int randomNumber = r.nextInt(3);
            drawUpperWallOfTheSecondPartOfTheGame(world, upperPosition, j, i, randomNumber);
            drawBottomWallOfTheSecondPartOfTheGame(world, bottomPosition, j, i, randomNumber);
            drawHallWayOfTheSecondPartOfTheGame(world, hallwayPosition, j, i, randomNumber);
        }
    }

    /* draw upper wall of the first part of the game, which moves toward left. */
    private void drawUpperWallOfTheFirstPartOfTheGame(TETile[][] world, Position upperPosition, int j, int i, int number) {
        if (j == 0 && number >= 2) {
            drawLine(world, upperPosition, i, Tileset.WALL, "horizontal", "negative");
            drawLStartFromVerticalLine(world, upperPosition, i, i, Tileset.WALL, "negative");
        } else if (j == 0 && number < 2) {
            drawLine(world, upperPosition, i * 2, Tileset.WALL, "horizontal", "negative");
        } else if (number == 0) {
            drawBottomHalfSquare(world, upperPosition, i + j, i + j, i + j, Tileset.WALL);
            drawLStartFromVerticalLine(world, upperPosition, i + j, i + j, Tileset.WALL, "negative");
        } else if (number == 1) {
            drawBottomHalfSquare(world, upperPosition, i + j, i + j, i + j, Tileset.WALL);
            drawLine(world, upperPosition, i, Tileset.WALL, "horizontal", "positive");
            drawLine(world, upperPosition, i * 2, Tileset.WALL, "horizontal", "negative");
        } else if (number == 2) {
            drawLine(world, upperPosition, i * 4, Tileset.WALL, "horizontal", "negative");
        } else if (number == 3) {
            drawOppositeLStartFromVerticalLine(world, upperPosition, i + j + 2, i, Tileset.WALL, "positive");
            drawBottomHalfSquare(world, upperPosition, i + j, i * 2, i + j, Tileset.WALL);
            drawOppositeOfLStartFromHorizontalLine(world, upperPosition, i - 2, i + j + 2, Tileset.WALL, "negative");
            drawLine(world, upperPosition, i * 2, Tileset.WALL, "horizontal", "negative");
        }
    }

    /* draw bottom wall of the first part of the game, which moves toward left. */
    private void drawBottomWallOfTheFirstPartOfTheGame(TETile[][] world, Position bottomPosition, int j, int i, int number) {
        if (j == 0 && number >= 2) {
            drawLine(world, bottomPosition, i + 2, Tileset.WALL, "vertical", "negative");
            drawLine(world, bottomPosition, i * 2 - 1, Tileset.WALL, "horizontal", "negative");
        } else if (j == 0 && number < 2) {
            drawUpperHalfSquare(world, bottomPosition, i * 2, i, i * 2 - 2, Tileset.WALL);
            drawLine(world, bottomPosition, i + 1, Tileset.WALL, "horizontal", "negative");
        } else if (number == 0) {
            drawLStartFromVerticalLine(world, bottomPosition, i + j, (i + j) * 2 - 1, Tileset.WALL, "negative");
        } else if (number == 1) {
            drawUpperHalfSquare(world, bottomPosition, i + j, i + j + 3, i + j, Tileset.WALL);
            drawLine(world, bottomPosition, i, Tileset.WALL, "horizontal", "positive");
            drawLine(world, bottomPosition, i * 2 - 3, Tileset.WALL, "horizontal", "negative");
        } else if (number == 2) {
            drawLStartFromVerticalLine(world, bottomPosition, i + j + 2, i + j + 2, Tileset.WALL, "positive");
            drawUpperHalfSquare(world, bottomPosition, i + j + 2, i + j + 2, i + j, Tileset.WALL);
            drawLStartFromHorizontalLine(world, bottomPosition, i, i + j + 4, Tileset.WALL, "positive");
            drawLine(world, bottomPosition, i * 3 + 1, Tileset.WALL, "horizontal", "negative");
        } else if (number == 3) {
            drawLine(world, bottomPosition, i * 2 - (i - 2) + i + 1, Tileset.WALL, "horizontal", "negative");
        }
    }

    /* draw hallway of the first part of the game, which moves toward left. */
    private void drawHallWayOfTheFirstPartOfTheGame(TETile[][] world, Position hallWayPosition, int j, int i, int number) {
        if (j == 0 && number >= 2) {
            drawBottomRectangle(world, hallWayPosition, i, i - 2, Tileset.FLOOR, "negative");
            drawLStartFromVerticalLine(world, hallWayPosition, i, i + 1, Tileset.FLOOR, "negative");
        } else if (j == 0 && number < 2) {
            drawBottomRectangle(world, hallWayPosition, (i * 2) - 2, i - 2, Tileset.FLOOR, "negative");
            drawLine(world, hallWayPosition, i + 2, Tileset.FLOOR, "horizontal", "negative");
        } else if (number == 0) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
            drawUpperRectangle(world, hallWayPosition, i + j, (i + j) - 2, Tileset.FLOOR, "negative");
            drawBottomRectangle(world, hallWayPosition, i + j, i + j - 2, Tileset.FLOOR, "positive");
            drawOppositeOfLStartFromHorizontalLine(world, hallWayPosition, i + j - 2, i + j, Tileset.FLOOR, "positive");
            drawLine(world, hallWayPosition, i + j + 1, Tileset.FLOOR, "horizontal", "negative");
        } else if (number == 1) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "vertical", "positive");
            drawUpperRectangle(world, hallWayPosition, i + j - 1, i + j - 2, Tileset.FLOOR, "negative");
            drawOppositeOfLStartFromHorizontalLine(world, hallWayPosition, i + j - 2, 3, Tileset.FLOOR, "negative");
            drawBottomRectangle(world, hallWayPosition, i + j - 1, i + j + 3 - 2, Tileset.FLOOR, "negative");
            drawLStartFromHorizontalLine(world, hallWayPosition, i + j + 3 - 2, 2, Tileset.FLOOR, "negative");
            drawLine(world, hallWayPosition, (i * 2) + j - 1, Tileset.FLOOR, "horizontal", "negative");
        } else if (number == 2) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
            drawBottomRectangle(world, hallWayPosition, i + j + 4, i - 2, Tileset.FLOOR, "negative");
            drawLStartFromVerticalLine(world, hallWayPosition, i + j + 4, i, Tileset.FLOOR, "positive");
            drawBottomRectangle(world, hallWayPosition, i + j, i + j, Tileset.FLOOR, "positive");
            drawLStartFromHorizontalLine(world, hallWayPosition, (i + j + 1) + (i - 2), i + j + 4, Tileset.FLOOR, "positive");
            drawLine(world, hallWayPosition, i * 3 + 2, Tileset.FLOOR, "horizontal", "negative");
        } else if (number == 3) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
            drawUpperRectangle(world, hallWayPosition, i + j + 3, i - (i - 2), Tileset.FLOOR, "negative");
            drawLine(world, hallWayPosition, i + j + 4, Tileset.FLOOR, "vertical", "positive");
            drawUpperRectangle(world, hallWayPosition, i + j - 2, i * 2 - 1 - (i - 2), Tileset.FLOOR, "positive");
            if (i > 3) {
                drawLine(world, hallWayPosition, i * 2 - (i - 2), Tileset.FLOOR, "horizontal", "negative");
                drawUpperRectangle(world, hallWayPosition, i + j - 2, i - 2 - 1, Tileset.FLOOR, "negative");
                drawOppositeOfLStartFromHorizontalLine(world, hallWayPosition, i - 2, i + j + 4, Tileset.FLOOR, "negative");
                drawLine(world, hallWayPosition, i * 2 + 1, Tileset.FLOOR, "horizontal", "negative");
            } else {
                drawLine(world, hallWayPosition, i + 1, Tileset.FLOOR, "horizontal", "negative");
                drawLine(world, hallWayPosition, i + j + 4, Tileset.FLOOR, "vertical", "negative");
                drawLine(world, hallWayPosition, i * 2 + 1, Tileset.FLOOR, "horizontal", "negative");
            }
        }
    }

    /* draw upper wall of the second part of the game. which moves toward up. */
    private void drawUpperWallOfTheSecondPartOfTheGame(TETile[][] world, Position upperPosition, int j, int i, int number) {
        if (j == 0) {
            drawLine(world, upperPosition, 3, Tileset.WALL, "horizontal", "negative");
            drawLStartFromVerticalLine(world, upperPosition, i + 8 + 8 + 1 + 4, i * 16 + 2, Tileset.WALL, "positive");
            drawLStartFromHorizontalLine(world, upperPosition, 3, i + 2, Tileset.WALL, "negative");
        } else if (number == 0) {
            drawLeftHalfSquare(world, upperPosition, i, i, i, Tileset.WALL);
            drawLine(world, upperPosition, i, Tileset.WALL, "vertical", "positive");
        } else if (number == 1) {
            drawLeftHalfSquare(world, upperPosition, i + 2, i + 2, i + 2, Tileset.WALL);
            drawLine(world, upperPosition, i, Tileset.WALL, "vertical", "negative");
            drawLine(world, upperPosition, i * 2, Tileset.WALL, "vertical", "positive");
        } else if (number == 2) {
            drawLine(world, upperPosition, i * 2 + 2, Tileset.WALL, "vertical", "positive");
        }
    }

    /* draw bottom wall of the second part of the game. which moves toward up. */
    private void drawBottomWallOfTheSecondPartOfTheGame(TETile[][] world, Position bottomPosition, int j, int i, int number) {
        if (j == 0) {
            drawLStartFromVerticalLine(world, bottomPosition, i + 8 + 4 + 1 + 4, i * 16, Tileset.WALL, "positive");
            drawLine(world, bottomPosition, i, Tileset.WALL, "vertical", "positive");
        } else if (number == 0) {
            drawRightHalfSquare(world, bottomPosition, i, i, i, Tileset.WALL);
            drawLine(world, bottomPosition, i, Tileset.WALL, "vertical", "positive");
        } else if (number == 1) {
            drawLine(world, bottomPosition, i * 2 + 2, Tileset.WALL, "vertical", "positive");
        } else if (number == 2) {
            drawRightHalfSquare(world, bottomPosition, i + 2, i * 2, i + 2, Tileset.WALL);
            drawLine(world, bottomPosition, i * 2 - 2, Tileset.WALL, "vertical", "negative");
            drawLine(world, bottomPosition, i * 2, Tileset.WALL, "vertical", "positive");
        }
    }

    /* draw hallway of the second part of the game. which moves toward up. */
    private void drawHallWayOfTheSecondPartOfTheGame(TETile[][] world, Position hallWayPosition, int j, int i, int number) {
        if (j == 0) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
            drawLStartFromVerticalLine(world, hallWayPosition, i + 8 + 8 + 1 + 2, i * 16 + 1, Tileset.FLOOR, "positive");
            drawLStartFromHorizontalLine(world, hallWayPosition, 2, i + 1, Tileset.FLOOR, "negative");
        } else if (number == 0) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "vertical", "positive");
            drawUpperRectangle(world, hallWayPosition, i - 2, i, Tileset.FLOOR, "negative");
            drawLine(world, hallWayPosition, i, Tileset.FLOOR, "horizontal", "positive");
            drawUpperRectangle(world, hallWayPosition, i - 2, i, Tileset.FLOOR, "positive");
            drawLStartFromHorizontalLine(world, hallWayPosition, i, i - 1, Tileset.FLOOR, "positive");
            drawLine(world, hallWayPosition, i, Tileset.FLOOR, "vertical", "positive");
        } else if (number == 1) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "vertical", "positive");
            drawLine(world, hallWayPosition, 3, Tileset.FLOOR, "horizontal", "positive");
            drawUpperRectangle(world, hallWayPosition, i, i, Tileset.FLOOR, "positive");
            drawLine(world, hallWayPosition, i + 2, Tileset.FLOOR, "horizontal", "negative");
            drawLine(world, hallWayPosition, i * 2 + 1, Tileset.FLOOR, "vertical", "positive");
        } else if (number == 2) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "vertical", "positive");
            drawLine(world, hallWayPosition, 3, Tileset.FLOOR, "horizontal", "negative");
            drawUpperRectangle(world, hallWayPosition, i * 2 - 2, i, Tileset.FLOOR, "negative");
            drawLine(world, hallWayPosition, i + 2, Tileset.FLOOR, "horizontal", "positive");
            drawLine(world, hallWayPosition, i * 2 + 1, Tileset.FLOOR, "vertical", "positive");
        }
    }

    /* draw a shape which is similar to half square, which looks to the left side. Start drawing the shape from bottom  */
    private void drawLeftHalfSquare(TETile[][] world, Position p, int sizeOfBottomHorizontalLine,
                                    int sizeOfVerticalLine, int sizeOfUpperHorizontalLine, TETile type) {
        drawLine(world, p, sizeOfBottomHorizontalLine, type, "horizontal", "positive");
        drawLine(world, p, sizeOfVerticalLine, type, "vertical", "positive");
        drawLine(world, p, sizeOfUpperHorizontalLine, type, "horizontal", "negative");
    }

    /* draw a shape which is similar to half square, which looks to the right. Start drawing the shape from bottom */
    private void drawRightHalfSquare(TETile[][] world, Position p, int sizeOfBottomHorizontalLine,
                                     int sizeOfVerticalLine, int sizeOfUpperHorizontalLine, TETile type) {
        drawLine(world, p, sizeOfBottomHorizontalLine, type, "horizontal", "negative");
        drawLine(world, p, sizeOfVerticalLine, type, "vertical", "positive");
        drawLine(world, p, sizeOfUpperHorizontalLine, type, "horizontal", "positive");
    }

    /* draw a shape which is similar to half square, which looks down. Start drawing the shape from right. */
    private void drawBottomHalfSquare(TETile[][] world, Position P, int sizeOfRightVerticalLine,
                                      int sizeOfHorizontalLine, int sizeOfLeftVerticalLine, TETile type) {
        drawLine(world, P, sizeOfRightVerticalLine, type, "vertical", "positive");
        drawLine(world, P, sizeOfHorizontalLine, type, "horizontal", "negative");
        drawLine(world, P, sizeOfLeftVerticalLine, type, "vertical", "negative");
    }

    /* draw a shape which is similar to half square, which looks up. Start drawing the shape from right. */
    private void drawUpperHalfSquare(TETile[][] world, Position p, int sizeOfRightVerticalLine,
                                     int sizeOfHorizontalLine, int sizeOfLeftVerticalLine, TETile type) {
        drawLine(world, p, sizeOfRightVerticalLine, type, "vertical", "negative");
        drawLine(world, p, sizeOfHorizontalLine, type, "horizontal", "negative");
        drawLine(world, p, sizeOfLeftVerticalLine, type, "vertical", "positive");
    }

    /* draw a rectangle, which looks down. its direction determines if the rectangle will be drawn
    toward right or left. the shape ends at the highest part of the last size */
    private void drawBottomRectangle(TETile[][] world, Position p, int sizeOfVerticalLine,
                                     int sizeOfHorizontalLine, TETile type, String direction) {
        String directionOfHorizontalLine;
        if (direction == "positive") {
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
    private void drawUpperRectangle(TETile[][] world, Position P, int sizeOfVerticalLine,
                                    int sizeOfHorizontalLine, TETile type, String direction) {

        String directionOfHorizontalLine;
        if (direction == "positive") {
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
}






