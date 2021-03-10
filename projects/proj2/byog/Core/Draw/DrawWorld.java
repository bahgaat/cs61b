package byog.Core.Draw;
import byog.Core.Position;
import byog.Core.RandomUtils;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.Serializable;
import java.util.*;


/* draw the random world. I have divided drawing the world into 2 parts, and every part into 3 parts. */
/* don't use static unless you want it, don't make it the default */
public class DrawWorld implements Serializable {
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
    private ArrayList<Position> arrayOfFlowersPositions = new ArrayList<Position>();
    private DrawShapes drawShapes;
    private Position upperPosition;
    private Position bottomPosition;
    private Position hallWayPosition;


    /* Constructor. */
    public DrawWorld(DrawShapes drawShapes) {
        this.drawShapes = drawShapes;
    }

    public Position getStartMainPlayerPosition() {
        int startPlayerPositionX = startMainPlayerPosition.getX();
        int startPlayerPositionY = startMainPlayerPosition.getY();
        Position copyOfStartMainPlayerPosition = new Position(startPlayerPositionX, startPlayerPositionY);
        return copyOfStartMainPlayerPosition;
    }


    public Position getDoorPosition() {
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

    public void setPositionInWorld(int positionX, int positionY, TETile type) {
        world[positionX][positionY] = type;
    }

    Queue<Map> getQueueEvil() {
        return new LinkedList<Map>(queueOfEvilPlayersPositions);
    }

    public Map pollFromQueueEvil() {
        return queueOfEvilPlayersPositions.poll();
    }

    public ArrayList<Position> getArrayOfFlowersPositions() {
        ArrayList<Position> copyOfArrayOfFlowersPositions = new ArrayList<Position>(arrayOfFlowersPositions);
        return copyOfArrayOfFlowersPositions;
    }

    Position getPositionFromArrayOfFlowersPositions(int index) {
        Position position = arrayOfFlowersPositions.get(index);
        return new Position(position.getX(), position.getY());
    }


    /* draw the whole world, I divide the drawing into 2 parts.*/
    public void drawWorld(long seed) {
        Random r = new Random(seed);
        int y = RandomUtils.uniform(r, 60, 64);
        int x = RandomUtils.uniform(r, 80, 85);


        drawEmptyWorld();

        upperPosition = new Position(x, y);
        bottomPosition = new Position(x, y);
        hallWayPosition = new Position(x - 1, y - 1);
        int i = RandomUtils.uniform(r, 3, 6);
        drawFirstPartOfTheWorld(i, r);
        addEvilPlayerToTheQueueEvil(horizontal, hallWayPosition);
        drawSecondPartOfTheWorld(i, r);
    }

    private void drawEmptyWorld() {
        for (int p = 0; p < WIDTH; p += 1) {
            for (int g = 0; g < HEIGHT; g += 1) {
                world[p][g] = Tileset.NOTHING;
            }
        }
    }

    /* draw first part of the world. it moves horizontally until it cant move more because
    of the space, and then start drawing second part. I divide drawing the first part
    into  3 parts. Upper wall, Bottom wall, Hallway. and I add evil player which moves horizontally in the
    game. */
    private void drawFirstPartOfTheWorld( int i, Random r) {

        int j = 0;
        while (itIsOkToMoveHorizontally) {
            int randomNumber = r.nextInt(4);
            drawUpperWallOfTheFirstPartOfTheWorld(j, i, randomNumber, r);
            if (itIsOkToMoveHorizontally) {
                drawBottomWallOfTheFirstPartOfTheWorld( j, i, randomNumber, r);
                drawHallWayOfTheFirstPartOfTheWorld( j, i, randomNumber, r);
            }
            j += 1;
        }
    }


    private void putMapToStartingPoint(Map<Character, ArrayList<Integer>> mapOfDirections) {
        mapOfDirections.replace('x', new ArrayList<Integer>());
        mapOfDirections.replace('y', new ArrayList<Integer>());
        drawShapes.setMapOfDirections(mapOfDirections);
    }

    /* draw upper wall of the first part of the world, which moves toward left. */
    private void drawUpperWallOfTheFirstPartOfTheWorld(int j, int i, int randomNumber, Random r) {

        int spaceNeededForTheBiggestRoomTobeDrawn = i * 6;
        if (upperPosition.getX() < spaceNeededForTheBiggestRoomTobeDrawn) {
            itIsOkToMoveHorizontally = false;
        } else if (j == 0 && randomNumber >= 2) {
            drawShapes.drawLine(i, horizontal , negative);
            drawShapes.drawLStartFromVerticalLine(i, i, negative);
        } else if (j == 0 && randomNumber < 2) {
            drawShapes.drawLine(i * 2, horizontal, negative);
        } else if (randomNumber == 0) {
            drawShapes.drawBottomHalfSquare(i + j, i + j, i + j);
            drawShapes.drawLStartFromVerticalLine(i + j, i + j, negative);
        } else if (randomNumber == 1) {
            drawShapes.drawBottomHalfSquare(i + j, i + j, i + j);
            drawShapes.drawLine(i, horizontal, positive);
            drawShapes.drawLine(i * 2, horizontal, negative);
        } else if (randomNumber == 2) {
            drawShapes.drawLine(i * 4, horizontal, negative);
        } else if (randomNumber == 3) {
            drawShapes.drawOppositeLStartFromVerticalLine(i + j + 2, i, positive);
            drawShapes.drawBottomHalfSquare(i + j, i * 2, i + j);
            drawShapes.drawOppositeOfLStartFromHorizontalLine(i - 2, i + j + 2, negative);
            drawShapes.drawLine(i * 2, horizontal, negative);
        }
        draw(Tileset.WALL, upperPosition);
    }

    /* draw bottom wall of the first part of the world, which moves toward left. */
    private void drawBottomWallOfTheFirstPartOfTheWorld(int j, int i, int randomNumber, Random r) {

        if (j == 0 && randomNumber >= 2) {
            drawShapes.drawLine(i + 2, vertical, negative);
            drawShapes.drawLine(i * 2 - 1, horizontal, negative);
        } else if (j == 0 && randomNumber < 2) {
            drawShapes.drawUpperHalfSquare(i * 2, i, i * 2 - 2);
            drawShapes.drawLine(i + 1, horizontal, negative);
        } else if (randomNumber == 0) {
            drawShapes.drawLStartFromVerticalLine(i + j, (i + j) * 2 - 1, negative);
        } else if (randomNumber == 1) {
            drawShapes.drawUpperHalfSquare(i + j, i + j + 3, i + j);
            drawShapes.drawLine(i, horizontal, positive);
            drawShapes.drawLine(i * 2 - 3, horizontal, negative);
        } else if (randomNumber == 2) {
            drawShapes.drawLStartFromVerticalLine(i + j + 2, i + j + 2, positive);
            drawShapes.drawUpperHalfSquare(i + j + 2, i + j + 2,i + j);
            drawShapes.drawLStartFromHorizontalLine(i, i + j + 4, positive);
            drawShapes.drawLine(i * 3 + 1, horizontal, negative);
        } else if (randomNumber == 3) {
            drawShapes.drawLine(i * 2 - (i - 2) + i + 1, horizontal, negative);
        }
        draw(Tileset.WALL, bottomPosition);
    }


    /* draw hallway of the first part of the game, which moves toward left. and add positions to arrayOfPoints
    which will be replaced later as a points (flowers). */
    private void drawHallWayOfTheFirstPartOfTheWorld(int j, int i, int randomNumber, Random r) {

        if (j == 0 && randomNumber >= 2) {
            drawHallWayOfTheFirstPartWhenSatisfyFirstCondition(j, i);
        }  else if (j == 0 && randomNumber < 2) {
            drawHallWayOfTheFirstPartWhenSatisfySecondCondition(j, i);
        } else if (randomNumber == 0) {
            drawHallWayOfTheFirstPartWhenSatisfyThirdCondition(j, i);
        } else if (randomNumber == 1) {
            drawHallWayOfTheFirstPartWhenSatisfyFourthCondition(j, i);
        } else if (randomNumber == 2) {
            drawHallWayOfTheFirstPartWhenSatisfyFifthCondition(j, i);
        } else if (randomNumber == 3) {
            drawHallWayOfTheFirstWhenSatisfySixthCondition(j, i);
        }
        draw(Tileset.FLOOR, hallWayPosition);
    }

    private void drawHallWayOfTheFirstPartWhenSatisfyFirstCondition(int j, int i) {

        drawShapes.drawBottomRectangle(i, i - 2, negative);
        addFLowerPositionToArrayOfFlowers();
        drawShapes.drawLStartFromVerticalLine(i, i + 1, negative);
    }

    private void  drawHallWayOfTheFirstPartWhenSatisfySecondCondition(int j, int i) {

        drawShapes.drawBottomRectangle((i * 2) - 2, i - 2, negative);
        addFLowerPositionToArrayOfFlowers();
        drawShapes.drawLine(i + 2, horizontal, negative);
    }

    private void drawHallWayOfTheFirstPartWhenSatisfyThirdCondition(int j, int i) {

        drawShapes.drawLine(2, horizontal, negative);
        drawShapes.drawUpperRectangle(i + j, (i + j) - 2, negative);
        drawShapes.drawBottomRectangle(i + j, i + j - 2, positive);
        addFLowerPositionToArrayOfFlowers();
        drawShapes.drawOppositeOfLStartFromHorizontalLine(i + j - 2, i + j, positive);
        drawShapes.drawLine(i + j + 1, horizontal, negative);
    }

    private void drawHallWayOfTheFirstPartWhenSatisfyFourthCondition(int j, int i) {

        drawShapes.drawLine(2, horizontal, negative);
        drawShapes.drawLine(2, vertical, positive);
        drawShapes.drawUpperRectangle(i + j - 1, i + j - 2, negative);
        drawShapes.drawOppositeOfLStartFromHorizontalLine(i + j - 2, 3, negative);
        drawShapes.drawBottomRectangle( i + j - 1, i + j + 3 - 2, negative);
        addFLowerPositionToArrayOfFlowers();
        drawShapes.drawLStartFromHorizontalLine( i + j + 3 - 2, 2, negative);
        drawShapes.drawLine((i * 2) + j - 1, horizontal, negative);
    }

    private void drawHallWayOfTheFirstPartWhenSatisfyFifthCondition(int j, int i) {

        drawShapes.drawLine(2, horizontal, negative);
        drawShapes.drawBottomRectangle(i + j + 4, i - 2, negative);
        drawShapes.drawLStartFromVerticalLine(i + j + 4, i, positive);
        drawShapes.drawBottomRectangle(i+ j, i + j, positive);
        addFLowerPositionToArrayOfFlowers();
        drawShapes.drawLStartFromHorizontalLine((i + j + 1) + (i - 2), i + j + 4, positive);
        drawShapes.drawLine(i * 3 + 2, horizontal, negative);
    }

    private void drawHallWayOfTheFirstWhenSatisfySixthCondition(int j, int i) {

        drawShapes.drawLine(2, horizontal, negative);
        drawShapes.drawUpperRectangle(i + j + 3, i - (i - 2), negative);
        drawShapes.drawLine(i + j + 4, vertical, positive);
        drawShapes.drawUpperRectangle(i + j - 2, i * 2 - 1 - (i - 2), positive);
        addFLowerPositionToArrayOfFlowers();
        if (i > 3) {
            drawShapes.drawLine(i * 2 - (i - 2), horizontal, negative);
            drawShapes.drawUpperRectangle(i + j - 2, i - 2 - 1, negative);
            drawShapes.drawOppositeOfLStartFromHorizontalLine( i - 2, i + j + 4,  negative);
            drawShapes.drawLine(i * 2 + 1, horizontal, negative);
        } else {
            drawShapes.drawLine(i + 1, horizontal, negative);
            drawShapes.drawLine(i + j + 4, vertical, negative);
            drawShapes.drawLine(i * 2 + 1, horizontal, negative);
        }
    }


    /* draw second part of the world. it takes L shape and then moves vertically
    until it can't move more and then the drawing is ended. I divide drawing the second part into 3 parts
    Upper wall, Bottom wall, and hallway. */
    private void drawSecondPartOfTheWorld(int i, Random r) {

        int j = 0;
        i = RandomUtils.uniform(r, 3, 5 );
        while (itIsOkToMoveVertically) {
            int randomNumber = r.nextInt(3);
            drawUpperWallOfTheSecondPartOfTheWorld(j, i, randomNumber);
            if (itIsOkToMoveVertically) {
                drawBottomWallOfTheSecondPartOfTheWorld( j, i, randomNumber);
                drawHallWayOfTheSecondPartOfTheWorld( j, i, randomNumber);
            }
            j += 1;
        }
        drawShapes.drawLine(2, horizontal, positive);
        draw(Tileset.WALL, bottomPosition);
        startMainPlayerPosition = new Position(hallWayPosition.getX(), hallWayPosition.getY() - 1);
    }


    /* draw upper wall of the second part of the world. which moves toward up. */
    private void drawUpperWallOfTheSecondPartOfTheWorld(int j, int i, int randomNumber) {

        int spaceNeededForTheBiggestRoomTobeDrawn  = i * 2 + 3;
        int maximumHeight = 78;
        if (j == 0) {
            drawShapes.drawLine(3, horizontal, negative);
            draw(Tileset.WALL, upperPosition);
            while (upperPosition.getY() > 28) {
                drawShapes.drawLine(2, vertical, negative);
                draw(Tileset.WALL, upperPosition);
            }
            while (upperPosition.getX() < 89) {
                drawShapes.drawLine(2, horizontal, positive);
                draw(Tileset.WALL, upperPosition);
            }
            drawShapes.drawLStartFromHorizontalLine(3, i + 2, negative);
        } else if (maximumHeight < upperPosition.getY() + spaceNeededForTheBiggestRoomTobeDrawn) {
            itIsOkToMoveVertically = false;
        } else if (randomNumber == 0) {
            drawShapes.drawLeftHalfSquare(i, i, i);
            drawShapes.drawLine(i, vertical, positive);
        } else if (randomNumber == 1) {
            drawShapes.drawLeftHalfSquare( i + 2, i + 2, i + 2);
            drawShapes.drawLine(i, vertical, negative);
            drawShapes.drawLine(i * 2, vertical, positive);
        } else if (randomNumber == 2) {
            drawShapes.drawLine(i * 2 + 2, vertical, positive);
        }
        draw(Tileset.WALL, upperPosition);
    }

    /* draw bottom wall of the second part of the world. which moves toward up. */
    private void drawBottomWallOfTheSecondPartOfTheWorld(int j, int i, int randomNumber) {
        if (j == 0) {
            while (bottomPosition.getY() > 30) {
                drawShapes.drawLine(2, vertical, negative);
                draw(Tileset.WALL, bottomPosition);
            }
            while (bottomPosition.getX() < 89) {
                drawShapes.drawLine(2, horizontal, positive);
                draw(Tileset.WALL, bottomPosition);
            }
            drawShapes.drawLine(i - 2, vertical, positive);
            draw(Tileset.WALL, bottomPosition);
            drawShapes.drawLine(2, vertical, positive);
            /*doorPosition = new Position(bottomPosition.getX(), bottomPosition.getY() - 1);*/
            draw(Tileset.LOCKED_DOOR, bottomPosition);
            Position secondEvilPlayerPosition = new Position(bottomPosition.getX() + 1,
                    bottomPosition.getY());
            addEvilPlayerToTheQueueEvil(vertical, secondEvilPlayerPosition);
            drawShapes.drawLine(2, vertical, positive);
        } else if (randomNumber == 0) {
            drawShapes.drawRightHalfSquare(i, i, i);
            drawShapes.drawLine(i, vertical, positive);
        } else if (randomNumber == 1) {
            drawShapes.drawLine(i * 2 + 2, vertical, positive);
        } else if (randomNumber == 2) {
            drawShapes.drawRightHalfSquare(i + 2, i * 2, i + 2);
            drawShapes.drawLine(i * 2 - 2, vertical, negative);
            drawShapes.drawLine(i * 2, vertical, positive);
        }
        draw(Tileset.WALL, bottomPosition);
    }

    /* draw hallway of the second part of the world. which moves toward up. and add random room positions in arraylist
    which will be replaced later as points (flowers). */
    private void drawHallWayOfTheSecondPartOfTheWorld(int j, int i, int randomNumber) {
        if (j == 0) {
            drawHallWayOfTheSecondPartWhenSatisfyFirstCondition(j, i, randomNumber);
        } else if (randomNumber == 0) {
            drawHallWayOfTheSecondPartWhenSatisfySecondCondition(j, i, randomNumber);
        } else if (randomNumber == 1) {
            drawHallWayOfTheSecondPartWhenSatisfyThirdCondition(j, i, randomNumber);
        } else if (randomNumber == 2) {
            drawHallWayOfTheSecondPartWhenSatisfyFourthCondition(j, i, randomNumber);
        }
        draw(Tileset.FLOOR, hallWayPosition);
    }

    private void drawHallWayOfTheSecondPartWhenSatisfyFirstCondition(int j, int i, int randomNumber) {

        drawShapes.drawLine(2, horizontal, negative);
        draw(Tileset.FLOOR, hallWayPosition);
        while (hallWayPosition.getY() > 29) {
            drawShapes.drawLine(2, vertical, negative);
            draw(Tileset.FLOOR, hallWayPosition);
        }
        while (hallWayPosition.getX() < 89) {
            drawShapes.drawLine(2, horizontal, positive);
            draw(Tileset.FLOOR, hallWayPosition);
        }
        addFLowerPositionToArrayOfFlowers();
        drawShapes.drawLStartFromHorizontalLine(2, i + 1, negative);
    }

    private void drawHallWayOfTheSecondPartWhenSatisfySecondCondition(int j, int i, int randomNumber) {

        drawShapes.drawLine(2, vertical, positive);
        drawShapes.drawUpperRectangle(i - 2, i, negative);
        drawShapes.drawLine(i, horizontal, positive);
        drawShapes.drawUpperRectangle(i - 2, i, positive);
        addFLowerPositionToArrayOfFlowers();
        drawShapes.drawLStartFromHorizontalLine(i, i - 1, positive);
        drawShapes.drawLine(i, vertical, positive);
    }

    private void drawHallWayOfTheSecondPartWhenSatisfyThirdCondition(int j, int i, int randomNumber) {

        drawShapes.drawLine(2, vertical, positive);
        drawShapes.drawLine(3, horizontal, positive);
        drawShapes.drawUpperRectangle(i, i, positive);
        addFLowerPositionToArrayOfFlowers();
        drawShapes.drawLine(i + 2, horizontal, negative);
        drawShapes.drawLine(i * 2 + 1, vertical, positive);
    }

    private void drawHallWayOfTheSecondPartWhenSatisfyFourthCondition(int j, int i, int randomNumber) {

        drawShapes.drawLine(2, vertical, positive);
        drawShapes.drawLine(3, horizontal, negative);
        drawShapes.drawUpperRectangle(i * 2 - 2, i, negative);
        addFLowerPositionToArrayOfFlowers();
        drawShapes.drawLine(i + 2, horizontal, positive);
        drawShapes.drawLine(i * 2 + 1, vertical, positive);
    }

    private void draw(TETile type, Position position) {
        Map<Character, ArrayList<Integer>> mapOfDirections = drawShapes.geMapOfDirections();
        ArrayList<Integer> distancesToAddToX = mapOfDirections.get('x');
        ArrayList<Integer> distancesToAddToY = mapOfDirections.get('y');
        int index = 0;
        while (distancesToAddToX.size() > index) {
            world[position.getX() + distancesToAddToX.get(index)][position.getY() + distancesToAddToY.get(index)] = type;
            position.setX(position.getX() + distancesToAddToX.get(index));
            position.setY(position.getY() + distancesToAddToY.get(index));
            index += 1;
        }
        putMapToStartingPoint(mapOfDirections);
    }

    void addFLowerPositionToArrayOfFlowers() {
        Position pointPosition = new Position(hallWayPosition.getX(), hallWayPosition.getY());
        arrayOfFlowersPositions.add(pointPosition);
    }

    void addEvilPlayerToTheQueueEvil(String direction, Position position) {
        Position evilPosition = new Position(position.getX(), position.getY());
        Map<String, Position> map = new HashMap<String, Position>();
        map.put(direction, evilPosition);
        queueOfEvilPlayersPositions.add(map);
    }

}
