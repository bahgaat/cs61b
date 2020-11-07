package byog.Core.Draw;

import byog.Core.Position;
import byog.Core.RandomUtils;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.*;

/* draw the random world. I have divided drawing the world into 2 parts, and every part into 3 parts. */
/* don't use static unless you want it, don't make it the default */
class Draw2dWorld {
    private final String positive = "positive";
    private final String negative = "negative";
    private final String vertical = "vertical";
    private final String horizontal = "horizontal";
    private final int WIDTH = 100;
    private final int HEIGHT = 100;
    private final TETile[][] world = new TETile[WIDTH][HEIGHT];
    private boolean itIsOkToMoveHorizontally = true;
    private boolean itIsOkToMoveVertically = true;
    private Position startMainPlayerPosition;
    private Position doorPosition;
    /* A queue which stores in it the evil players. */
    private Queue<Map> queueOfEvilPlayersPositions = new LinkedList<>();
    /* arrayList which stores in it positions, this positions will be replaced by flowers(points) in the game. */
    private ArrayList<Position> arrayOfPointsPositions = new ArrayList<Position>();
    private DrawShapes drawShapes;


    /* Constructor. */
    public DrawWorld(DrawShapes drawShapes) {
        this.drawShapes = drawShapes;
    }

    Position getStartMainPlayerPosition() {
        int startPlayerPositionX = startMainPlayerPosition.getX();
        int startPlayerPositionY = startMainPlayerPosition.getY();
        Position copyOfStartMainPlayerPosition = new Position(startPlayerPositionX, startPlayerPositionY);
        return copyOfStartMainPlayerPosition;
    }


    Position getDoorPosition() {
        int doorXPosition = doorPosition.getX();
        int doorYPosition = doorPosition.getY();
        Position copyOfDoorPosition = new Position(doorXPosition, doorYPosition);
        return copyOfDoorPosition;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public TETile[][] getWorld() {
        return world;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    void setPositionInWorld(int positionX, int positionY, TETile type) {

    }

    Queue<Map> getQueueEvil() {
        return new LinkedList<Map>(queueOfEvilPlayersPositions);
    }

    Map pollFromQueueEvil() {
        queueOfEvilPlayersPositions.poll();
    }

    ArrayList<Position> getArrayOfPoints() {
        ArrayList<Position> copyOfArrayOfPoints = new ArrayList<Position>(arrayOfPointsPositions);
        return copyOfArrayOfPoints;
    }

    Position getPositionFromArrayOfPointsPositions(int index) {
        Position position = arrayOfPointsPositions.get(index);
        return new Position(position.getX(), position.getY());
    }


    /* draw the whole world, I divide the drawing into 2 parts.*/
    void  drawWorld(long seed) {
        Random r = new Random(seed);
        int y = RandomUtils.uniform(r, 60, 64);
        int x = RandomUtils.uniform(r, 80, 85);

        for (int p = 0; p < WIDTH; p += 1) {
            for (int g = 0; g < HEIGHT; g += 1) {
                world[p][g] = Tileset.NOTHING;
            }
        }
        /*
        ArrayListsOfPositions arrayListsOfPositions = new ArrayListsOfPositions();

         */
        Position upperPosition = new Position(x, y);
        Position bottomPosition = new Position(x, y);
        Position hallWayPosition = new Position(x - 1, y - 1);
        int i = RandomUtils.uniform(r, 3, 6);
        drawFirstPartOfTheWorld(world, upperPosition, bottomPosition, hallWayPosition, i, r);
    }

    /* draw first part of the world. it moves horizontally until it cant move more because
    of the space, and then start drawing second part. I divide drawing the first part
    into  3 parts. Upper wall, Bottom wall, Hallway. and I add evil player which moves horizontally in the
    game. */
    private void drawFirstPartOfTheWorld(TETile[][] world, Position upperPosition, Position bottomPosition,
                                         Position hallWayPosition, int i, Random r) {

        int j = 0;
        while (itIsOkToMoveHorizontally) {
            int randomNumber = r.nextInt(4);
            drawUpperWallOfTheFirstPartOfTheWorld(world, upperPosition, j, i, randomNumber, r);
            if (itIsOkToMoveHorizontally) {
                drawBottomWallOfTheFirstPartOfTheWorld(world, bottomPosition, j, i, randomNumber, r);
                drawHallWayOfTheFirstPartOfTheWorld(world, hallWayPosition, j, i, randomNumber, r);
            }
            j += 1;
        }
        addEvilPlayerToTheQueueEvil(horizontal, hallWayPosition);
        drawSecondPartOfTheWorld(world, upperPosition, bottomPosition, hallWayPosition, i, r);

    }

    private void draw(TETile type, Map map, Position position) {
        /*
        DrawShapes.ArrayListsOfPositions arrayListsOfPositions = drawShapes.getArrayListsOfPositions();
        ArrayList<Integer> arrayOfXPositions = arrayListsOfPositions.getArrayOfPositionX();
        ArrayList<Integer> arrayOfYPositions = arrayListsOfPositions.getArrayOfPositionY();
        int lengthOfBothArrays =  arrayListsOfPositions.getArrayOfPositionX().size();
         */
        arrayOfx = map.getValue(x);
        arrayOfy = map.getValue(y);

        int index = 0;
        while (lengthOfBothArrays > 0) {
            world[position.getX() + arrayOfX.get(index)][position.getY() + arrayOfY.get(index)] = type;
            index += 1;
        }
        position.setX(position.getX() +arrayOfX.get(index) );
        position.setY(position.getY() + arrayOfY.get(index));
        putArrayListsToStartingPoint(arrayListsOfPositions);
    }

    private void putArrayListsToStartingPoint(DrawShapes.ArrayListsOfPositions arrayListsOfPositions) {

        ArrayList<Integer> arrayListOfXPositions = new ArrayList<Integer>();
        ArrayList<Integer> arrayListOfYPositions = new ArrayList<Integer>();
        arrayListsOfPositions.setArrayOfXPositions(arrayListOfXPositions);
        arrayListsOfPositions.setArrayOfYPositions(arrayListOfYPositions);
    }

    /* draw upper wall of the first part of the world, which moves toward left. */
    private void drawUpperWallOfTheFirstPartOfTheWorld(TETile[][] world, Position upperPosition,
                                                       int j, int i, int randomNumber, Random r) {

        int spaceNeededForTheBiggestRoomTobeDrawn = i * 6;
        if (upperPosition._x < spaceNeededForTheBiggestRoomTobeDrawn) {
            itIsOkToMoveHorizontally = false;
        } else if (j == 0 && randomNumber >= 2) {
            /* don't access with static class, put object in the constructor and then play with them. */
            Map map = drawShapes.drawLine(upperPosition, i,
                    horizontal , negative);
            drawShapes.drawLStartFromVerticalLine(world, upperPosition, i, i, negative);
        } else if (j == 0 && randomNumber < 2) {
            drawShapes.drawLine(upperPosition, i * 2, horizontal, negative);
        } else if (randomNumber == 0) {
            drawShapes.drawBottomHalfSquare(world, upperPosition, i + j, i + j,
                    i + j);
            drawShapes.drawLStartFromVerticalLine(world, upperPosition, i + j, i + j,
                    negative);
        } else if (randomNumber == 1) {
            drawShapes.drawBottomHalfSquare(world, upperPosition, i + j, i + j,
                    i + j);
            drawShapes.drawLine(upperPosition, i, horizontal, positive);
            drawShapes.drawLine(upperPosition, i * 2, horizontal, negative);
        } else if (randomNumber == 2) {
            drawShapes.drawLine(upperPosition, i * 4, horizontal, negative);
        } else if (randomNumber == 3) {
            drawShapes.drawOppositeLStartFromVerticalLine(world, upperPosition, i + j + 2,
                    i, positive);
            drawShapes.drawBottomHalfSquare(world, upperPosition, i + j, i * 2,
                    i + j);
            drawShapes.drawOppositeOfLStartFromHorizontalLine(world, upperPosition, i - 2,
                    i + j + 2, negative);
            drawShapes.drawLine(upperPosition, i * 2, horizontal, negative);
        }
        draw(Tileset.WALL, map, position);
    }

    /* draw bottom wall of the first part of the world, which moves toward left. */
    private void drawBottomWallOfTheFirstPartOfTheWorld(TETile[][] world, Position bottomPosition,
                                                        int j, int i, int randomNumber, Random r) {

        if (j == 0 && randomNumber >= 2) {
            drawShapes.drawLine(bottomPosition, i + 2, vertical, negative);
            drawShapes.drawLine(bottomPosition, i * 2 - 1, horizontal, negative);
        } else if (j == 0 && randomNumber < 2) {
            drawShapes.drawUpperHalfSquare(world, bottomPosition, i * 2, i,
                    i * 2 - 2);
            drawShapes.drawLine(bottomPosition, i + 1, horizontal, negative);
        } else if (randomNumber == 0) {
            drawShapes.drawLStartFromVerticalLine(world, bottomPosition, i + j,
                    (i + j) * 2 - 1, negative);
        } else if (randomNumber == 1) {
            drawShapes.drawUpperHalfSquare(world, bottomPosition, i + j, i + j + 3,
                    i + j);
            drawShapes.drawLine(bottomPosition, i, horizontal, positive);
            drawShapes.drawLine(bottomPosition, i * 2 - 3, horizontal, negative);
        } else if (randomNumber == 2) {
            drawShapes.drawLStartFromVerticalLine(world, bottomPosition, i + j + 2,
                    i + j + 2, positive);
            drawShapes.drawUpperHalfSquare(world, bottomPosition, i + j + 2, i + j + 2,
                    i + j);
            drawShapes.drawLStartFromHorizontalLine(world, bottomPosition, i, i + j + 4, positive);
            drawShapes.drawLine(bottomPosition, i * 3 + 1, horizontal, negative);
        } else if (randomNumber == 3) {
            drawShapes.drawLine(bottomPosition, i * 2 - (i - 2) + i + 1, horizontal, negative);
        }
        draw(Tileset.WALL);
    }


    /* draw hallway of the first part of the game, which moves toward left. and add positions to arrayOfPoints
    which will be replaced later as a points (flowers). */
    private void drawHallWayOfTheFirstPartOfTheWorld(TETile[][] world, Position hallWayPosition,
                                                     int j, int i, int randomNumber, Random r) {

        if (j == 0 && randomNumber >= 2) {
            drawHallWayOfTheFirstPartWhenSatisfyFirstCondition(world, hallWayPosition, j, i);
        }  else if (j == 0 && randomNumber < 2) {
            drawHallWayOfTheFirstPartWhenSatisfySecondCondition(world, hallWayPosition, j, i);
        } else if (randomNumber == 0) {
            drawHallWayOfTheFirstPartWhenSatisfyThirdCondition(world, hallWayPosition, j, i);
        } else if (randomNumber == 1) {
            drawHallWayOfTheFirstPartWhenSatisfyFourthCondition(world, hallWayPosition, j, i);
        } else if (randomNumber == 2) {
            drawHallWayOfTheFirstPartWhenSatisfyFifthCondition(world, hallWayPosition, j, i);
        } else if (randomNumber == 3) {
            drawHallWayOfTheFirstWhenSatisfySixthCondition(world, hallWayPosition, j, i);
        }
        draw(Tileset.FLOOR);
    }

    private void drawHallWayOfTheFirstPartWhenSatisfyFirstCondition (TETile[][] world, Position hallWayPosition,
                                                                     int j, int i) {

        drawShapes.drawBottomRectangle(world, hallWayPosition, i, i - 2, negative);
        addPointsPositionToArrayOfPoints(hallWayPosition);
        drawShapes.drawLStartFromVerticalLine(world, hallWayPosition, i, i + 1, negative);
    }

    private void  drawHallWayOfTheFirstPartWhenSatisfySecondCondition (TETile[][] world, Position hallWayPosition,
                                                                       int j, int i) {

        drawShapes.drawBottomRectangle(world, hallWayPosition, (i * 2) - 2, i - 2,
                negative);
        addPointsPositionToArrayOfPoints(hallWayPosition);
        drawShapes.drawLine(hallWayPosition, i + 2, horizontal, negative);
    }

    private void drawHallWayOfTheFirstPartWhenSatisfyThirdCondition (TETile[][] world, Position hallWayPosition,
                                                                     int j, int i) {

        drawShapes.drawLine(hallWayPosition, 2, horizontal, negative);
        drawShapes.drawUpperRectangle(world, hallWayPosition, i + j, (i + j) - 2,
                negative);
        drawShapes.drawBottomRectangle(world, hallWayPosition, i + j, i + j - 2,
                positive);
        addPointsPositionToArrayOfPoints(hallWayPosition);
        drawShapes.drawOppositeOfLStartFromHorizontalLine(world, hallWayPosition, i + j - 2,
                i + j, positive);
        drawShapes.drawLine(hallWayPosition, i + j + 1, horizontal, negative);
    }

    private void drawHallWayOfTheFirstPartWhenSatisfyFourthCondition (TETile[][] world, Position hallWayPosition,
                                                                      int j, int i) {

        drawShapes.drawLine(hallWayPosition, 2, horizontal, negative);
        drawShapes.drawLine(hallWayPosition, 2, vertical, positive);
        drawShapes.drawUpperRectangle(world, hallWayPosition, i + j - 1,
                i + j - 2, negative);
        drawShapes.drawOppositeOfLStartFromHorizontalLine(world, hallWayPosition, i + j - 2,
                3, negative);
        drawShapes.drawBottomRectangle(world, hallWayPosition, i + j - 1, i + j + 3 - 2,
                negative);
        addPointsPositionToArrayOfPoints(hallWayPosition);
        drawShapes.drawLStartFromHorizontalLine(world, hallWayPosition, i + j + 3 - 2, 2,
                negative);
        drawShapes.drawLine(hallWayPosition, (i * 2) + j - 1, horizontal, negative);
    }

    private void drawHallWayOfTheFirstPartWhenSatisfyFifthCondition (TETile[][] world, Position hallWayPosition,
                                                                     int j, int i) {

        drawShapes.drawLine(hallWayPosition, 2, horizontal, negative);
        drawShapes.drawBottomRectangle(world, hallWayPosition, i + j + 4, i - 2,
                 negative);
        drawShapes.drawLStartFromVerticalLine(world, hallWayPosition, i + j + 4, i,
                 positive);
        drawShapes.drawBottomRectangle(world, hallWayPosition, i + j, i + j,
                 positive);
        addPointsPositionToArrayOfPoints(hallWayPosition);
        drawShapes.drawLStartFromHorizontalLine(world, hallWayPosition, (i + j + 1) + (i - 2),
                i + j + 4, positive);
        drawShapes.drawLine(hallWayPosition, i * 3 + 2, horizontal, negative);
    }

    private void drawHallWayOfTheFirstWhenSatisfySixthCondition (TETile[][] world, Position hallWayPosition,
                                                                 int j, int i) {

        drawShapes.drawLine(hallWayPosition, 2, horizontal, negative);
        drawShapes.drawUpperRectangle(world, hallWayPosition, i + j + 3, i - (i - 2),
              negative);
        drawShapes.drawLine(hallWayPosition, i + j + 4, vertical, positive);
        drawShapes.drawUpperRectangle(world, hallWayPosition, i + j - 2, i * 2 - 1 - (i - 2),
                 positive);
        addPointsPositionToArrayOfPoints(hallWayPosition);
        if (i > 3) {
            drawShapes.drawLine(hallWayPosition, i * 2 - (i - 2), horizontal, negative);
            drawShapes.drawUpperRectangle(world, hallWayPosition, i + j - 2, i - 2 - 1,
                     negative);
            drawShapes.drawOppositeOfLStartFromHorizontalLine(world, hallWayPosition, i - 2,
                    i + j + 4,  negative);
            drawShapes.drawLine(hallWayPosition, i * 2 + 1, horizontal, negative);
        } else {
            drawShapes.drawLine(hallWayPosition, i + 1, horizontal, negative);
            drawShapes.drawLine(hallWayPosition, i + j + 4, vertical, negative);
            drawShapes.drawLine(hallWayPosition, i * 2 + 1, horizontal, negative);
        }
    }


    /* draw second part of the world. it takes L shape and then moves vertically
    until it can't move more and then the drawing is ended. I divide drawing the second part into 3 parts
    Upper wall, Bottom wall, and hallway. */
    private void drawSecondPartOfTheWorld(TETile[][] world, Position upperPosition, Position bottomPosition,
                                          Position hallWayPosition, int i, Random r) {

        int j = 0;
        i = RandomUtils.uniform(r, 3, 5 );
        while (itIsOkToMoveVertically) {
            int randomNumber = r.nextInt(3);
            drawUpperWallOfTheSecondPartOfTheWorld(world, upperPosition, j, i, randomNumber);
            if (itIsOkToMoveVertically) {
                drawBottomWallOfTheSecondPartOfTheWorld(world, bottomPosition, j, i, randomNumber);
                drawHallWayOfTheSecondPartOfTheWorld(world, hallWayPosition, j, i, randomNumber);
            }
            j += 1;
        }
        drawShapes.drawLine(bottomPosition, 2, horizontal, positive);
        startMainPlayerPosition = new Position(hallWayPosition._x, hallWayPosition._y - 1);
    }

    /* draw upper wall of the second part of the world. which moves toward up. */
    private void drawUpperWallOfTheSecondPartOfTheWorld(TETile[][] world, Position upperPosition, int j, int i,
                                                        int randomNumber) {

        int spaceNeededForTheBiggestRoomTobeDrawn  = i * 2 + 3;
        int maximumHeight = 78;
        if (j == 0) {
            drawShapes.drawLine(upperPosition, 3, horizontal, negative);
            while (upperPosition._y > 28) {
                drawShapes.drawLine(upperPosition, 2, vertical, negative);
            }
            while (upperPosition._x < 89) {
                drawShapes.drawLine(upperPosition, 2, horizontal, positive);
            }
            drawShapes.drawLStartFromHorizontalLine(world, upperPosition, 3, i + 2,
                    negative);
        } else if (maximumHeight < upperPosition._y + spaceNeededForTheBiggestRoomTobeDrawn) {
            itIsOkToMoveVertically = false;
        } else if (randomNumber == 0) {
            drawShapes.drawLeftHalfSquare(world, upperPosition, i, i, i);
            drawShapes.drawLine(upperPosition, i, vertical, positive);
        } else if (randomNumber == 1) {
            drawShapes.drawLeftHalfSquare(world, upperPosition, i + 2, i + 2,
                    i + 2);
            drawShapes.drawLine(upperPosition, i, vertical, negative);
            drawShapes.drawLine(upperPosition, i * 2, vertical, positive);
        } else if (randomNumber == 2) {
            drawShapes.drawLine(upperPosition, i * 2 + 2, vertical, positive);
        }
        draw(Tileset.WALL);
    }

    /* draw bottom wall of the second part of the world. which moves toward up. */
    private void drawBottomWallOfTheSecondPartOfTheWorld(TETile[][] world, Position bottomPosition,
                                                         int j, int i, int randomNumber) {
        if (j == 0) {
            while (bottomPosition._y > 30) {
                drawShapes.drawLine(bottomPosition, 2, vertical, negative);
            }
            while (bottomPosition._x < 89) {
                drawShapes.drawLine(bottomPosition, 2, horizontal, positive);
            }
            drawShapes.drawLine(bottomPosition, i - 2, vertical, positive);
            draw(Tileset.WALL);
            drawShapes.drawLine(bottomPosition, 2, vertical, positive);
            doorPosition = new Position(bottomPosition._x, bottomPosition._y - 1);
            draw(Tileset.LOCKED_DOOR);
            drawShapes.drawLine(bottomPosition,  2, vertical, positive);
        } else if (randomNumber == 0) {
            drawShapes.drawRightHalfSquare(world, bottomPosition, i, i, i);
            drawShapes.drawLine(bottomPosition, i, vertical, positive);
        } else if (randomNumber == 1) {
            drawShapes.drawLine(bottomPosition, i * 2 + 2, vertical, positive);
        } else if (randomNumber == 2) {
            drawShapes.drawRightHalfSquare(world, bottomPosition, i + 2, i * 2,
                    i + 2);
            drawShapes.drawLine(bottomPosition, i * 2 - 2, vertical, negative);
            drawShapes.drawLine(bottomPosition, i * 2, vertical, positive);
        }
        draw(Tileset.WALL);
    }

    /* draw hallway of the second part of the world. which moves toward up. and add random room positions in arraylist
    which will be replaced later as points (flowers). */
    private void drawHallWayOfTheSecondPartOfTheWorld(TETile[][] world, Position hallWayPosition,
                                                      int j, int i, int randomNumber) {
        if (j == 0) {
            drawHallWayOfTheSecondPartWhenSatisfyFirstCondition(world, hallWayPosition,
                    j, i, randomNumber);
        } else if (randomNumber == 0) {
            drawHallWayOfTheSecondPartWhenSatisfySecondCondition(world, hallWayPosition,
                            j, i, randomNumber);
        } else if (randomNumber == 1) {
            drawHallWayOfTheSecondPartWhenSatisfyThirdCondition(world, hallWayPosition,
                    j, i, randomNumber);
        } else if (randomNumber == 2) {
            drawHallWayOfTheSecondPartWhenSatisfyFourthCondition(world, hallWayPosition,
                    j, i, randomNumber);
        }
    }

    private void drawHallWayOfTheSecondPartWhenSatisfyFirstCondition(TETile[][] world, Position hallWayPosition,
                                                                     int j, int i, int randomNumber) {

        drawShapes.drawLine(hallWayPosition, 2, horizontal, negative);
        while (hallWayPosition._y > 29) {
            drawShapes.drawLine(hallWayPosition, 2, vertical, negative);
        }
        while (hallWayPosition._x < 89) {
            drawShapes.drawLine(hallWayPosition, 2, horizontal, positive);
        }
        addPointsPositionToArrayOfPoints(hallWayPosition);
        drawShapes.drawLStartFromHorizontalLine(world, hallWayPosition, 2, i + 1,
                negative);
        addEvilPlayerToTheQueueEvil(vertical, hallWayPosition);
    }

    private void drawHallWayOfTheSecondPartWhenSatisfySecondCondition(TETile[][] world, Position hallWayPosition,
                                                                      int j, int i, int randomNumber) {

        drawShapes.drawLine(hallWayPosition, 2, vertical, positive);
        drawShapes.drawUpperRectangle(world, hallWayPosition, i - 2, i, negative);
        drawShapes.drawLine(hallWayPosition, i, horizontal, positive);
        drawShapes.drawUpperRectangle(world, hallWayPosition, i - 2, i, positive);
        addPointsPositionToArrayOfPoints(hallWayPosition);
        drawShapes.drawLStartFromHorizontalLine(world, hallWayPosition, i, i - 1,
                positive);
        drawShapes.drawLine(hallWayPosition, i, vertical, positive);
    }

    private void drawHallWayOfTheSecondPartWhenSatisfyThirdCondition(TETile[][] world, Position hallWayPosition,
                                                                     int j, int i, int randomNumber) {

        drawShapes.drawLine(hallWayPosition, 2, vertical, positive);
        drawShapes.drawLine(hallWayPosition, 3, horizontal, positive);
        drawShapes.drawUpperRectangle(world, hallWayPosition, i, i, positive);
        addPointsPositionToArrayOfPoints(hallWayPosition);
        drawShapes.drawLine(hallWayPosition, i + 2, horizontal, negative);
        drawShapes.drawLine(hallWayPosition, i * 2 + 1, vertical, positive);
    }

    private void drawHallWayOfTheSecondPartWhenSatisfyFourthCondition(TETile[][] world, Position hallWayPosition,
                                                                      int j, int i, int randomNumber) {

        drawShapes.drawLine(hallWayPosition, 2, vertical, positive);
        drawShapes.drawLine(hallWayPosition, 3, horizontal, negative);
        drawShapes.drawUpperRectangle(world, hallWayPosition, i * 2 - 2, i, negative);
        addPointsPositionToArrayOfPoints(hallWayPosition);
        drawShapes.drawLine(hallWayPosition, i + 2, horizontal, positive);
        drawShapes.drawLine(hallWayPosition, i * 2 + 1, vertical, positive);
    }

    static void addPointsPositionToArrayOfPoints(Position hallWayPosition) {
        Position pointPosition = new Position(hallWayPosition._x, hallWayPosition._y);
        arrayOfPointsPositions.add(pointPosition);
    }

    static void addEvilPlayerToTheQueueEvil(String position, Position hallWayPosition) {
        Position evilPosition = new Position(hallWayPosition._x, hallWayPosition._y);
        Map<String, Position> map = new HashMap<String, Position>();
        map.put(position, evilPosition);
        queueOfEvilPlayersPositions.add(map);
    }


}
