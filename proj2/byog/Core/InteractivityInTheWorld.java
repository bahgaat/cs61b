package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.*;

import static byog.Core.DrawingState.DrawLineHorizontalNegative;


/**
 *  Draws a random world.
 */

/**
 * TODO Apply single responsibility principle https://howtodoinjava.com/design-patterns/single-responsibility-principle/
 * initializing Player ? Points ? its not the responsibility of the [MyWorld]
 * the good solution is passing to the constructor
 */

public class MyWorld implements Serializable {
    static boolean gameOver = false;

    /* A queue which stores in it the evil players. */
    static Queue<Map> queueEvil = new LinkedList<>();

    /* arrayList which stores in it positions, which will be replaced by flowers(points) in the game. */
    static ArrayList<Position> arrayOfPoints = new ArrayList<Position>();

    static Position playerPosition;
    private static int i = 0;
    private static Position doorPosition;
    private static int round = 1;



    /* read input , and make decision based on the input. If the input is 'N', get seed from the input and draw the world
    with its  components and then play the game. if the input is 'L', load all the saved objects and play the game. */
    static void readTheInputBeforeStartingTheGame(InputDevice input) {
        //TODO Refactor
        if (input.hasNextChar()) {
            char nextInputChar = input.getNextChar();
            if (nextInputChar == 'N') {
                boolean collectedSeed = false;
                while (!collectedSeed) {
                    if (input.hasNextChar()) {
                        nextInputChar = input.getNextChar();
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



    /* draw the world and add all the components. which are mainPlayer, evilPlayer, and one point. */
    static void drawAndAddAllTheComponentsOfTheWorld(long seed, InputDevice input) {
        /**
         * TODO Try to isolate @param MainPlayer and EvilPlayer and Point from World
         */
        world = drawWorld(seed);
        MainPlayer player = new MainPlayer();
        Point point = new Point();
        point.addPoint(0);
        EvilPlayer evilPlayer = new EvilPlayer();
        ArrayList<EvilPlayer> arrayOfEvilPlayers = new ArrayList<EvilPlayer>();
        arrayOfEvilPlayers.add(evilPlayer);
        playGame(world, player, point, arrayOfEvilPlayers, input);
    }


    /* play the game. This including moving the mainPlayer toward his direction, and moving evilPlayer ,his speed
    depends on the round. The higher the round, the faster the evilPlayer. */
    private static void playGame(TETile[][] world, MainPlayer player, Point point, ArrayList<EvilPlayer> arrayOfEvilPlayers,
                                 InputDevice input) {
        while (!input.theGameEnded()) {
            input.renderTheWorld(world);
            String newDirection = null;
            if (input.hasNextChar()) {
                char nextChar = input.getNextChar();
                if (nextChar == 'W') {
                    newDirection = "up";
                } else if (nextChar == 'A') {
                    newDirection = "left";
                } else if (nextChar == 'S') {
                    newDirection = "down";
                } else if (nextChar == 'D') {
                    newDirection = "right";
                } else if (nextChar== 'Q') {
                    saveGame(player, point, arrayOfEvilPlayers);
                }

                if (player.itIsPossibleToMoveToTheNewPosition(newDirection, "floor")) {
                    player.move(Tileset.PLAYER);
                    player.attack();
                } else if (player.winTheRound()) {
                    player.move(Tileset.PLAYER);
                    startNewRound(player, point);
                    updateEvilPlayerSpeed(arrayOfEvilPlayers);
                }
            }


            /* evilPlayer attacks every now and then and this depends on the round and the speed. The higher the round
            and the less the number speed is,the more powerful and faster the evilPlayer is. the more he attacks. */
            for (int j = 0; j < arrayOfEvilPlayers.size(); j += 1) {
                EvilPlayer evilPlayer = arrayOfEvilPlayers.get(j);
                if (i % evilPlayer.getSpeed() == 0) {
                    evilPlayer.attack();
                }
            }
            i += 1;

            /* if round == 5 the game has to be much harder and this happens by adding another evilPlayer that attacks
            vertically. */
            if (round == 5) {
                EvilPlayer evilPlayer = new EvilPlayer();
                arrayOfEvilPlayers.add(evilPlayer);
            }
        }
        input.endTheGame();

    }

    /* start newRound in the game, and this happens when the MainPlayer win the round. The new round
    will be harder. For example, the evilPlayer will be faster and the points (flowers) will be more. */
    private static void startNewRound(MainPlayer player, Point point) {
        player.setPlayerToStartPosition();
        world[doorPosition._x][doorPosition._y] = Tileset.LOCKED_DOOR;
        round += 1;
        int x = 0;
        while (round  > x) {
            try {
                point.addPoint(x);
                x += 1;
            } catch (Exception e) {
                drawFrame("Congratulations, You have won the game");
            }
        }
    }

    /* update evilPlayer speed to be faster, and this happens when the round increases.
    Important note. The bigger the speed is, the less faster the evilPlayer is. */
    private static void updateEvilPlayerSpeed(ArrayList<EvilPlayer> arrayOfEvilPlayers) {
        for (int y = 0; y < arrayOfEvilPlayers.size(); y += 1) {
            EvilPlayer evilPlayer = arrayOfEvilPlayers.get(y);
            evilPlayer.updateSpeed();
        }
    }

    /* save the needed objects of the game in a map. */
    private static void saveGame(MainPlayer player, Point point,
                                 ArrayList<EvilPlayer> arrayOfEvilPlayers) {

        Map<Object, String> mapOfTheWorld = putAllTheObjectsInAMap(player, point, arrayOfEvilPlayers);
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

    /* helper method for saveGame method. put All The Objects In A Map and then return the map. */
    private static Map<Object, String> putAllTheObjectsInAMap(MainPlayer player, Point point,
                                                              ArrayList<EvilPlayer> arrayOfEvilPlayers) {

        //TODO use statics for final values

        Map<Object, String> mapOfTheWorld = new HashMap<Object, String>();
        mapOfTheWorld.put(world, "world.txt");
        mapOfTheWorld.put(player, "mainPlayer.txt");
        mapOfTheWorld.put(point, "point.txt");
        mapOfTheWorld.put(arrayOfEvilPlayers, "arrayOfEvilPlayers.txt");
        mapOfTheWorld.put(gameOver, "gameOver.txt");
        mapOfTheWorld.put(queueEvil, "queueEvil.txt");
        mapOfTheWorld.put(arrayOfPoints, "arrayOfPoints.txt");
        mapOfTheWorld.put(round, "round.txt");
        mapOfTheWorld.put(doorPosition, "doorPosition.txt");
        return mapOfTheWorld;

    }

    /* load all the objects of the game and then play the game as usual. */
    static void loadGame(InputDevice input) {
        String[] arrayOfTheSavedFiles;
        arrayOfTheSavedFiles = new String[]{"world.txt", "mainPlayer.txt", "point.txt", "arrayOfEvilPlayers.txt",
                "hallWayPosition.txt", "gameOver.txt", "queueEvil.txt", "arrayOfPoints.txt", "round.txt",
                "doorPosition.txt"};
        Object[] arrayOfObjects = loadObjectsOfTheGameInAnArray(arrayOfTheSavedFiles);
        world = (TETile[][]) arrayOfObjects[0];
        MainPlayer player = (MainPlayer) arrayOfObjects[1];
        Point point = (Point) arrayOfObjects[2];
        ArrayList<EvilPlayer> arrayOfEvilPlayers = new ArrayList<EvilPlayer>();
        arrayOfEvilPlayers = (ArrayList<EvilPlayer>) arrayOfObjects[3];
        gameOver = (boolean) arrayOfObjects[4];
        queueEvil = (Queue<Map>) arrayOfObjects[5];
        arrayOfPoints = (ArrayList<Position>) arrayOfObjects[6];
        round = (int) arrayOfObjects[7];
        doorPosition = (Position) arrayOfObjects[8];
        playGame(world, player, point, arrayOfEvilPlayers, input);
    }


    /* An helper method for loadGame method. it loads all the saved objects of the game in an array and then return the array. */
    private static Object[] loadObjectsOfTheGameInAnArray(String[] arrayOfTheSavedFiles)  {
        Object[] arrayOfObjects = new Object[9];
        try {
            for(int i = 0; i < arrayOfTheSavedFiles.length; i += 1) {
                FileInputStream fisItem = new FileInputStream(arrayOfTheSavedFiles[i]);
                ObjectInputStream oisItem = new ObjectInputStream(fisItem);
                Object item = oisItem.readObject();
                arrayOfObjects[i] = item;
            }
            return arrayOfObjects;
        } catch (Exception e) {
            e.printStackTrace();
            return arrayOfObjects;
        }
    }

    /* draw to the user either the basic Ui if the argument passed to drawFrame was "Ui" or draw the passed argument. */
    static void drawFrame(String s) {
        StdDraw.setCanvasSize(40 * 16, 40 * 16);
        Font font = new Font("Monaco", Font.BOLD, 25);
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
            font = new Font("Monaco", Font.BOLD, 15);
            StdDraw.setFont(font);
            StdDraw.text(20, 20, s);
        }
        StdDraw.show();
        StdDraw.pause(2500);
    }

    public static int getRound() {
        return round;
    }
 }

    private static void addPointsPositionToArrayOfPoints(Position hallWayPosition) {
        Position pointPosition = new Position(hallWayPosition._x, hallWayPosition._y);
        arrayOfPoints.add(pointPosition);
    }

    private static void addEvilPlayerToTheQueueEvil(String position, Position hallWayPosition) {
        Position evilPosition = new Position(hallWayPosition._x, hallWayPosition._y);
        Map<String, Position> map = new HashMap<String, Position>();
        map.put(position, evilPosition);
        queueEvil.add(map);
    }




}






