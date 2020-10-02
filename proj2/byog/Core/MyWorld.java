package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.*;



/**
 *  Draws a random world.
 */
public class MyWorld implements Serializable {
    static final int WIDTH = 100;
    static final int HEIGHT = 100;
    public static TETile[][] world = new TETile[WIDTH][HEIGHT];
    static boolean gameOver = false;
    static Queue<Map> queueEvil = new LinkedList<Map>();
    static ArrayList<Position> arrayOfPoints = new ArrayList<Position>();
    public static int round = 1;
    static Position hallWayPosition;
    static Position doorPosition;
    static Position upperPosition;
    static Position bottomPosition;
    static Position playerPosition;
    static int i = 0;
    static boolean itIsOkToMoveHorizontally = true;
    static boolean itIsOkToMoveVertically = true;


    /* read input from the user, and make decision based on his input. If he typed 'N', get seed from him and draw the world
    with its  components and then play the game. if he typed 'L', load all the saved objects and play the game. */
    static void readFromTheUserBeforeStartingTheGame(InputDevice input, String seed) {
        if (input.hasNextChar()) {
            char nextInputChar = input.nextChar();
            if (nextInputChar == 'N') {
                boolean collectedSeed = false;
                while (!collectedSeed) {
                    if (input.hasNextChar()) {
                        nextInputChar = input.nextChar();
                        String convertCharToString = String.valueOf(nextInputChar);
                        if (nextInputChar == 'S') {
                            collectedSeed = true;
                            input.generateTheWorld();
                        } else {
                            input.collectTheSeed(convertCharToString);
                        }
                    }
                }
            } else if (nextInputChar == 'L') {
                input.generateTheWorldAfterLoading();
            }
        }
    }



    /* draw the world and add all the components. which are mainPlayer, evilPlayer, and points. */
    static void drawAndAddAllTheComponentsOfTheWorld(long seed, InputDevice input) {
        world = drawWorld(seed);
        MainPlayer player = new MainPlayer();
        Point point = new Point();
        point.addPoint(0);
        EvilPlayer evilPlayer = new EvilPlayer();
        ArrayList<EvilPlayer> arrayOfEvilPlayers = new ArrayList<EvilPlayer>();
        arrayOfEvilPlayers.add(evilPlayer);
        playGame(world, player, point, arrayOfEvilPlayers, input);
    }


    /* play the game. This including moving the mainPlayer toward his direction, and moving evilPlayer (his speed
    depends on the round. The higher the round, the faster the evilPlayer. */
    static void playGame(TETile[][] world, MainPlayer player, Point point, ArrayList<EvilPlayer> arrayOfEvilPlayers,
                                 InputDevice input) {
        while (!input.theGameEnded()) {
            input.renderTheWorld(world);
            if (input.hasNextChar()) {
                char nextChar = input.nextChar();
                if (nextChar == 'W') {
                    player.checkIfItIsPossibleToMoveToTheNewPositionAndMoveIfItIsOk("up", Tileset.PLAYER, "floor");
                } else if (nextChar == 'A') {
                    player.checkIfItIsPossibleToMoveToTheNewPositionAndMoveIfItIsOk("left", Tileset.PLAYER, "floor");
                } else if (nextChar == 'S') {
                    player.checkIfItIsPossibleToMoveToTheNewPositionAndMoveIfItIsOk("down", Tileset.PLAYER, "floor");
                } else if (nextChar == 'D') {
                    player.checkIfItIsPossibleToMoveToTheNewPositionAndMoveIfItIsOk("right", Tileset.PLAYER, "floor");
                } else if (nextChar== 'Q') {
                    putAllTheObjectsInAMap(player, point, arrayOfEvilPlayers);
                }
                player.attack();
                if (player.winTheRound()) {
                    startNewRound(player, point);
                    updateEvilPlayerSpeed(arrayOfEvilPlayers);
                }
            }


            for (int j = 0; j < arrayOfEvilPlayers.size(); j += 1) {
                EvilPlayer evilPlayer = arrayOfEvilPlayers.get(j);
                if (i % evilPlayer.speed == 0) {
                    evilPlayer.attack();
                }
            }

            i += 1;

            if (round == 6) {
                EvilPlayer evilPlayer = new EvilPlayer();
                arrayOfEvilPlayers.add(evilPlayer);
            }
        }
        input.endTheGame();

    }

    private static void startNewRound(MainPlayer player, Point point) {
        player.setPlayerToStartPosition();
        MyWorld.world[MyWorld.doorPosition._x][MyWorld.doorPosition._y] = Tileset.LOCKED_DOOR;
        MyWorld.round += 1;
        int x = 0;
        while (MyWorld.round  > x) {
            try {
                point.addPoint(x);
                x += 1;
            } catch (Exception e) {
                drawFrame("Congratulations, You have won the game");
            }
        }
    }

    private static void updateEvilPlayerSpeed(ArrayList<EvilPlayer> arrayOfEvilPlayers) {
        for (int y = 0; y < arrayOfEvilPlayers.size(); y += 1) {
            EvilPlayer evilPlayer = arrayOfEvilPlayers.get(y);
            evilPlayer.updateSpeed();
        }
    }

    static void putAllTheObjectsInAMap(MainPlayer player, Point point,
                                                         ArrayList<EvilPlayer> arrayOfEvilPlayers) {
        Map<Object, String> mapOfTheWorld = new HashMap<Object, String>();
        mapOfTheWorld.put(world, "world.txt");
        mapOfTheWorld.put(player, "mainPlayer.txt");
        mapOfTheWorld.put(point, "point.txt");
        mapOfTheWorld.put(arrayOfEvilPlayers, "arrayOfEvilPlayers.txt");
        mapOfTheWorld.put(hallWayPosition, "hallWayPosition.txt");
        mapOfTheWorld.put(gameOver, "gameOver.txt");
        mapOfTheWorld.put(queueEvil, "queueEvil.txt");
        mapOfTheWorld.put(arrayOfPoints, "arrayOfPoints.txt");
        mapOfTheWorld.put(round, "round.txt");
        mapOfTheWorld.put(doorPosition, "doorPosition.txt");
        saveGame(mapOfTheWorld);

    }


    /* save the needed objects of the game in a map. */
    private static void saveGame(Map<Object, String> mapOfTheWorld) {
        try {
            for (Map.Entry<Object, String> entry : mapOfTheWorld.entrySet()) {
                FileOutputStream fosEntry = new FileOutputStream(entry.getValue());
                ObjectOutputStream oosEntry = new ObjectOutputStream(fosEntry);
                oosEntry.writeObject(entry.getKey());
                fosEntry.close();
                oosEntry.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void loadAllObjectsOfTheGame(InputDevice input) {
        String[] arrayOfTheSavedFiles;
        arrayOfTheSavedFiles = new String[]{"world.txt", "mainPlayer.txt", "point.txt", "arrayOfEvilPlayers.txt",
                "hallWayPosition.txt", "gameOver.txt", "queueEvil.txt", "arrayOfPoints.txt", "round.txt",
                "doorPosition.txt"};
        Object[] arrayOfObjects = loadGame(arrayOfTheSavedFiles);
        world = (TETile[][]) arrayOfObjects[0];
        MainPlayer player = (MainPlayer) arrayOfObjects[1];
        Point point = (Point) arrayOfObjects[2];
        ArrayList<EvilPlayer> arrayOfEvilPlayers = new ArrayList<EvilPlayer>();
        arrayOfEvilPlayers = (ArrayList<EvilPlayer>) arrayOfObjects[3];
        hallWayPosition = (Position) arrayOfObjects[4];
        gameOver = (boolean) arrayOfObjects[5];
        queueEvil = (Queue<Map>) arrayOfObjects[6];
        arrayOfPoints = (ArrayList<Position>) arrayOfObjects[7];
        round = (int) arrayOfObjects[8];
        doorPosition = (Position) arrayOfObjects[9];
        playGame(world, player, point, arrayOfEvilPlayers, input);
    }


    /* load all the saved objects of the game. */
    private static Object[] loadGame(String[] arrayOfTheSavedFiles)  {
        Object[] arrayOfObjects = new Object[10];
        try {
            for(int i = 0; i < arrayOfTheSavedFiles.length; i += 1) {
                FileInputStream fisItem = new FileInputStream(arrayOfTheSavedFiles[i]);
                ObjectInputStream oisItem = new ObjectInputStream(fisItem);
                Object item = (Object) oisItem.readObject();
                arrayOfObjects[i] = item;
            }
            return arrayOfObjects;
        } catch (Exception e) {
            e.printStackTrace();
            return arrayOfObjects;
        }
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

        // draw the actual Ui
        if (s.equals("Ui")) {
            StdDraw.text(20, 20, "NewGame(N)");
            StdDraw.text(20, 18, "LoadGame(L)");
            StdDraw.text(20, 16, "Quit(Q)");
        } else {
            // draw the text
            StdDraw.text(20, 20, s);
        }
        StdDraw.show();
        StdDraw.pause(1500);
    }


    /* draw the whole world, I divide the drawing into 2 parts.*/
    private static TETile[][]  drawWorld(long seed) {
        Random r = new Random(seed);
        int y = RandomUtils.uniform(r, 60, 64);
        int x = RandomUtils.uniform(r, 80, 85);

        for (int p = 0; p < WIDTH; p += 1) {
            for (int g = 0; g < HEIGHT; g += 1) {
                world[p][g] = Tileset.NOTHING;
            }
        }

        upperPosition = new Position(x, y);
        bottomPosition = new Position(x, y);
        hallWayPosition = new Position(x - 1, y - 1);
        int i = RandomUtils.uniform(r, 3, 7);
        drawFirstPartOfTheWorld(world, upperPosition, bottomPosition, hallWayPosition, i, r);
        return world;
    }

    /* draw first part of the world. it moves horizontally until it cant move more because
    of the space, and then start drawing second part. I divide drawing the first part
    into  3 parts. Upper wall, Bottom wall, Hallway. and I add evil player which moves horizontally in the
    game. */
    private static void drawFirstPartOfTheWorld(TETile[][] world, Position upperPosition, Position bottomPosition,
                                                Position hallwayPosition, int i, Random r) {

        int j = 0;
        while (itIsOkToMoveHorizontally) {
            int randomNumber = r.nextInt(4);
            drawUpperWallOfTheFirstPartOfTheWorld(world, upperPosition, j, i, randomNumber, r);
            if (itIsOkToMoveHorizontally) {
                drawBottomWallOfTheFirstPartOfTheWorld(world, bottomPosition, j, i, randomNumber, r);
                drawHallWayOfTheFirstPartOfTheWorld(world, hallwayPosition, j, i, randomNumber, r);
            }
            j += 1;
        }
        addEvilPlayerToTheQueueEvil("horizontal");
        drawSecondPartOfTheWorld(world, upperPosition, bottomPosition, hallWayPosition, i, r);

    }

    /* draw upper wall of the first part of the world, which moves toward left. */
    private static void drawUpperWallOfTheFirstPartOfTheWorld(TETile[][] world, Position upperPosition,
                                                       int j, int i, int randomNumber, Random r) {

        int spaceNeededForTheBiggestRoomTobeDrawn = i * 6;
        if (upperPosition._x < spaceNeededForTheBiggestRoomTobeDrawn) {
            itIsOkToMoveHorizontally = false;
        } else if (j == 0 && randomNumber >= 2) {
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
        } else if (randomNumber == 2) {
            drawLine(world, upperPosition, i * 4, Tileset.WALL, "horizontal", "negative");
        } else if (randomNumber == 3) {
            drawOppositeLStartFromVerticalLine(world, upperPosition, i + j + 2, i, Tileset.WALL, "positive");
            drawBottomHalfSquare(world, upperPosition, i + j, i * 2, i + j, Tileset.WALL);
            drawOppositeOfLStartFromHorizontalLine(world, upperPosition, i - 2, i + j + 2, Tileset.WALL, "negative");
            drawLine(world, upperPosition, i * 2, Tileset.WALL, "horizontal", "negative");
        }

    }

    /* draw bottom wall of the first part of the world, which moves toward left. */
    private static void drawBottomWallOfTheFirstPartOfTheWorld(TETile[][] world, Position bottomPosition,
                                                        int j, int i, int randomNumber, Random r) {
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
        } else if (randomNumber == 2) {
            drawLStartFromVerticalLine(world, bottomPosition, i + j + 2, i + j + 2, Tileset.WALL, "positive");
            drawUpperHalfSquare(world, bottomPosition, i + j + 2, i + j + 2, i + j, Tileset.WALL);
            drawLStartFromHorizontalLine(world, bottomPosition, i, i + j + 4, Tileset.WALL, "positive");
            drawLine(world, bottomPosition, i * 3 + 1, Tileset.WALL, "horizontal", "negative");
        } else if (randomNumber == 3) {
            drawLine(world, bottomPosition, i * 2 - (i - 2) + i + 1, Tileset.WALL, "horizontal", "negative");
        }
    }


    /* draw hallway of the first part of the game, which moves toward left. and add positions to arrayOfPoints
    which will be replaced later as a points (flowers). */
    private static void drawHallWayOfTheFirstPartOfTheWorld(TETile[][] world, Position hallWayPosition,
                                                     int j, int i, int randomNumber, Random r) {

        if (j == 0 && randomNumber >= 2) {
            drawHallWayOfTheFirstPartOfTheWorldWhenJEqual0AndRandomNumberEqual2(world, hallWayPosition, j, i);
        }  else if (j == 0 && randomNumber < 2) {
             drawHallWayOfTheFirstPartOfTheWorldWhenJEqual0AndRandomNumberLessThan2(world, hallWayPosition, j, i);
        } else if (randomNumber == 0) {
            drawHallWayOfTheFirstPartOfTheWorldWhenRandomNumberEqual0(world, hallWayPosition, j, i);
        } else if (randomNumber == 1) {
            drawHallWayOfTheFirstPartOfTheWorldWhenRandomNumberEqual1(world, hallWayPosition, j, i);
        } else if (randomNumber == 2) {
            drawHallWayOfTheFirstPartOfTheWorldWhenRandomNumberEqual2(world, hallWayPosition, j, i);
        } else if (randomNumber == 3) {
            drawHallWayOfTheFirstPartOfTheWorldWhenRandomNumberEqual3(world, hallWayPosition, j, i);
        }
    }

    private static void drawHallWayOfTheFirstPartOfTheWorldWhenJEqual0AndRandomNumberEqual2
            (TETile[][] world, Position hallWayPosition, int j, int i) {

        drawBottomRectangle(world, hallWayPosition, i, i - 2, Tileset.FLOOR, "negative");
        addPointsPositionToArrayOfPoints();
        drawLStartFromVerticalLine(world, hallWayPosition, i, i + 1, Tileset.FLOOR, "negative");
    }

    private static void drawHallWayOfTheFirstPartOfTheWorldWhenJEqual0AndRandomNumberLessThan2
            (TETile[][] world, Position hallWayPosition, int j, int i) {
        drawBottomRectangle(world, hallWayPosition, (i * 2) - 2, i - 2, Tileset.FLOOR, "negative");
        Position pointPosition = new Position(hallWayPosition._x, hallWayPosition._y);
        addPointsPositionToArrayOfPoints();
        drawLine(world, hallWayPosition, i + 2, Tileset.FLOOR, "horizontal", "negative");
    }

    private static void drawHallWayOfTheFirstPartOfTheWorldWhenRandomNumberEqual0
            (TETile[][] world, Position hallWayPosition, int j, int i) {

        drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
        drawUpperRectangle(world, hallWayPosition, i + j, (i + j) - 2, Tileset.FLOOR, "negative");
        drawBottomRectangle(world, hallWayPosition, i + j, i + j - 2, Tileset.FLOOR, "positive");
        addPointsPositionToArrayOfPoints();
        drawOppositeOfLStartFromHorizontalLine(world, hallWayPosition, i + j - 2, i + j, Tileset.FLOOR, "positive");
        drawLine(world, hallWayPosition, i + j + 1, Tileset.FLOOR, "horizontal", "negative");
    }

    private static void drawHallWayOfTheFirstPartOfTheWorldWhenRandomNumberEqual1
            (TETile[][] world, Position hallWayPosition, int j, int i) {

        drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
        drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "vertical", "positive");
        drawUpperRectangle(world, hallWayPosition, i + j - 1, i + j - 2, Tileset.FLOOR, "negative");
        drawOppositeOfLStartFromHorizontalLine(world, hallWayPosition, i + j - 2, 3, Tileset.FLOOR, "negative");
        drawBottomRectangle(world, hallWayPosition, i + j - 1, i + j + 3 - 2, Tileset.FLOOR, "negative");
        addPointsPositionToArrayOfPoints();
        drawLStartFromHorizontalLine(world, hallWayPosition, i + j + 3 - 2, 2, Tileset.FLOOR, "negative");
        drawLine(world, hallWayPosition, (i * 2) + j - 1, Tileset.FLOOR, "horizontal", "negative");
    }

    private static void drawHallWayOfTheFirstPartOfTheWorldWhenRandomNumberEqual2
            (TETile[][] world, Position hallWayPosition, int j, int i) {

        drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
        drawBottomRectangle(world, hallWayPosition, i + j + 4, i - 2, Tileset.FLOOR, "negative");
        drawLStartFromVerticalLine(world, hallWayPosition, i + j + 4, i, Tileset.FLOOR, "positive");
        drawBottomRectangle(world, hallWayPosition, i + j, i + j, Tileset.FLOOR, "positive");
        addPointsPositionToArrayOfPoints();
        drawLStartFromHorizontalLine(world, hallWayPosition, (i + j + 1) + (i - 2), i + j + 4, Tileset.FLOOR,
                "positive");
        drawLine(world, hallWayPosition, i * 3 + 2, Tileset.FLOOR, "horizontal", "negative");
    }

    private static void drawHallWayOfTheFirstPartOfTheWorldWhenRandomNumberEqual3
            (TETile[][] world, Position hallWayPosition, int j, int i) {

        drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
        drawUpperRectangle(world, hallWayPosition, i + j + 3, i - (i - 2), Tileset.FLOOR, "negative");
        drawLine(world, hallWayPosition, i + j + 4, Tileset.FLOOR, "vertical", "positive");
        drawUpperRectangle(world, hallWayPosition, i + j - 2, i * 2 - 1 - (i - 2), Tileset.FLOOR, "positive");
        addPointsPositionToArrayOfPoints();
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


    /* draw second part of the world. it takes L shape and then moves vertically
    until it cant move more and then the drawing is ended. I divide drawing the second part into 3 parts
    Upper wall, Bottom wall, and hallway. */
    private static void drawSecondPartOfTheWorld(TETile[][] world, Position upperPosition, Position bottomPosition,
                                                 Position hallwayPosition, int i, Random r) {

        int j = 0;
        i = RandomUtils.uniform(r, 3, 5 );
        while (itIsOkToMoveVertically) {
            int randomNumber = r.nextInt(3);
            drawUpperWallOfTheSecondPartOfTheWorld(world, upperPosition, j, i, randomNumber);
            if (itIsOkToMoveVertically) {
                drawBottomWallOfTheSecondPartOfTheWorld(world, bottomPosition, j, i, randomNumber);
                drawHallWayOfTheSecondPartOfTheWorld(world, hallwayPosition, j, i, randomNumber);
            }
            j += 1;
        }
        drawLine(world, bottomPosition, 2, Tileset.WALL, "horizontal", "positive");
        playerPosition = new Position(MyWorld.hallWayPosition._x, MyWorld.hallWayPosition._y - 1);
    }

    /* draw upper wall of the second part of the world. which moves toward up. */
    private static void drawUpperWallOfTheSecondPartOfTheWorld(TETile[][] world, Position upperPosition,
                                                        int j, int i, int randomNumber) {

        int spaceNeededForTheBiggestRoomTobeDrawn  = i * 2 + 3;
        int maximumHeight = 78;
        if (j == 0) {
            drawLine(world, upperPosition, 3, Tileset.WALL, "horizontal", "negative");
            while (upperPosition._y > 28) {
                drawLine(world, upperPosition, 2, Tileset.WALL, "vertical", "negative");
            }
            while (upperPosition._x < 89) {
                drawLine(world, upperPosition, 2, Tileset.WALL, "horizontal", "positive");
            }
            drawLStartFromHorizontalLine(world, upperPosition, 3, i + 2, Tileset.WALL, "negative");
        } else if (maximumHeight < upperPosition._y + spaceNeededForTheBiggestRoomTobeDrawn) {
            itIsOkToMoveVertically = false;
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
            while (bottomPosition._y > 30) {
                drawLine(world, bottomPosition, 2, Tileset.WALL, "vertical", "negative");
            }
            while (bottomPosition._x < 89) {
                drawLine(world, bottomPosition, 2, Tileset.WALL, "horizontal", "positive");
            }
            drawLine(world, bottomPosition, i - 2, Tileset.WALL, "vertical", "positive");
            drawLine(world, bottomPosition, 2, Tileset.LOCKED_DOOR, "vertical", "positive");
            doorPosition = new Position(bottomPosition._x, bottomPosition._y - 1);
            drawLine(world, bottomPosition,  2, Tileset.WALL, "vertical", "positive");
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

    /* draw hallway of the second part of the world. which moves toward up. and add random room positions in arraylist
    which will be replaced later as points (flowers). the number of points depending on the round. The higher the round,
    the more the points. Also, add position which will be replaced by evil player in round 4. */
    private static void drawHallWayOfTheSecondPartOfTheWorld(TETile[][] world, Position hallWayPosition,
                                                      int j, int i, int randomNumber) {
        if (j == 0) {
            drawHallWayOfTheSecondPartOfTheWorldWhenJEqual0(world, hallWayPosition,
                    j, i, randomNumber);
        } else if (randomNumber == 0) {
            drawHallWayOfTheSecondPartOfTheWorldWhenRandomNumberEqual0(world, hallWayPosition,
                    j, i, randomNumber);
        } else if (randomNumber == 1) {
            drawHallWayOfTheSecondPartOfTheWorldWhenRandomNumberEqual1(world, hallWayPosition,
                    j, i, randomNumber);
        } else if (randomNumber == 2) {
            drawHallWayOfTheSecondPartOfTheWorldWhenRandomNumberEqual2(world, hallWayPosition,
                    j, i, randomNumber);
        }
    }

    private static void drawHallWayOfTheSecondPartOfTheWorldWhenJEqual0(TETile[][] world, Position hallWayPosition,
                                         int j, int i, int randomNumber) {
        drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "negative");
        while (hallWayPosition._y > 29) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "vertical", "negative");
        }
        while (hallWayPosition._x < 89) {
            drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "horizontal", "positive");
        }
        addPointsPositionToArrayOfPoints();
        drawLStartFromHorizontalLine(world, hallWayPosition, 2, i + 1, Tileset.FLOOR, "negative");
        addEvilPlayerToTheQueueEvil("vertical");
    }

    private static void drawHallWayOfTheSecondPartOfTheWorldWhenRandomNumberEqual0(TETile[][] world, Position hallWayPosition,
                                                                        int j, int i, int randomNumber) {
        drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "vertical", "positive");
        drawUpperRectangle(world, hallWayPosition, i - 2, i, Tileset.FLOOR, "negative");
        drawLine(world, hallWayPosition, i, Tileset.FLOOR, "horizontal", "positive");
        drawUpperRectangle(world, hallWayPosition, i - 2, i, Tileset.FLOOR, "positive");
        addPointsPositionToArrayOfPoints();
        drawLStartFromHorizontalLine(world, hallWayPosition, i, i - 1, Tileset.FLOOR, "positive");
        drawLine(world, hallWayPosition, i, Tileset.FLOOR, "vertical", "positive");
    }

    private static void drawHallWayOfTheSecondPartOfTheWorldWhenRandomNumberEqual1(TETile[][] world, Position hallWayPosition,
                                                                                   int j, int i, int randomNumber) {
        drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "vertical", "positive");
        drawLine(world, hallWayPosition, 3, Tileset.FLOOR, "horizontal", "positive");
        drawUpperRectangle(world, hallWayPosition, i, i, Tileset.FLOOR, "positive");
        addPointsPositionToArrayOfPoints();
        drawLine(world, hallWayPosition, i + 2, Tileset.FLOOR, "horizontal", "negative");
        drawLine(world, hallWayPosition, i * 2 + 1, Tileset.FLOOR, "vertical", "positive");
    }

    private static void drawHallWayOfTheSecondPartOfTheWorldWhenRandomNumberEqual2(TETile[][] world, Position hallWayPosition,
                                                                                   int j, int i, int randomNumber) {
        drawLine(world, hallWayPosition, 2, Tileset.FLOOR, "vertical", "positive");
        drawLine(world, hallWayPosition, 3, Tileset.FLOOR, "horizontal", "negative");
        drawUpperRectangle(world, hallWayPosition, i * 2 - 2, i, Tileset.FLOOR, "negative");
        addPointsPositionToArrayOfPoints();
        drawLine(world, hallWayPosition, i + 2, Tileset.FLOOR, "horizontal", "positive");
        drawLine(world, hallWayPosition, i * 2 + 1, Tileset.FLOOR, "vertical", "positive");
    }

    private static void addPointsPositionToArrayOfPoints() {
        Position pointPosition = new Position(hallWayPosition._x, hallWayPosition._y);
        arrayOfPoints.add(pointPosition);
    }

    private static void addEvilPlayerToTheQueueEvil(String position) {
        Position evilPosition = new Position(hallWayPosition._x, hallWayPosition._y);
        Map<String, Position> map = new HashMap<String, Position>();
        map.put(position, evilPosition);
        queueEvil.add(map);
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

    /* draw line, its position determines if it is vertical or horizontal, and its direction determines if the line
    is positive or negative. While drawing any line, we start counting from the present line. for eg, if i want to
    draw aline 1 step from where i am standing, I have to move 2 steps because the step that i stand on is counted. */
    private static void drawLine(TETile[][] world, Position p, int size,
                         TETile type, String position, String direction) {
        while (size > 0) {
            world[p._x][p._y] = type;
            if (position.equals("vertical") && direction.equals("positive") && size > 1) {
                p._y += 1;
            } else if (position.equals("vertical") && direction.equals("negative") && size > 1) {
                p._y -= 1;
            } else if (position.equals("horizontal") && direction.equals("positive") && size > 1) {
                p._x += 1;
            } else if (position.equals("horizontal") && direction.equals("negative") && size > 1) {
                p._x -= 1;
            }

            size -= 1;
        }
    }

}






