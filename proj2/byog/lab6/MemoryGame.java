package byog.lab6;

import byog.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }
        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();

    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        this.rand = new Random(seed);
        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String answer = "";
        while (n > 0) {
           answer += CHARACTERS[rand.nextInt(CHARACTERS.length)];
           n -= 1;
        }
        return answer;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen

        StdDraw.enableDoubleBuffering();
        StdDraw.clear(Color.black);
        StdDraw.line(0, 35, 40, 35);
        if (gameOver == false) {
            StdDraw.text(5, 37, "Round : " + "" + round);
            if (playerTurn == true) {
                StdDraw.text(15, 37, "Type");
            } else {
                StdDraw.text(15, 37, "Watch");
            }
            StdDraw.text(25, 37, ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
        }
        StdDraw.setFont();
        StdDraw.setPenColor(Color.white);
        StdDraw.text(20, 20, s);
        StdDraw.show();
        StdDraw.pause(1500);

    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i ++) {
            drawFrame(String.valueOf(letters.charAt(i)));
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        playerTurn = true;
        String input = "";
        drawFrame(input);

        while (input.length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                input += String.valueOf(key);

            }
        }
        playerTurn = false;
        return input;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        while (gameOver == false) {
            drawFrame("Round" + "" + round);
            String randomString = generateRandomString(round);
            flashSequence(randomString);
            String userString = solicitNCharsInput(randomString.length());
            if (!randomString.equals(userString)) {
                drawFrame("Game over. You made it to the round" + "" + round);
                this.gameOver = true;
            }
            round += 1;
        }
    }

}
