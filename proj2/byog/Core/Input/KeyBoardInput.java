package byog.Core.Gui.Input;

import edu.princeton.cs.introcs.StdDraw;
 /* anything that i put it by keyboard, i have to put it her.
  */
public class KeyBoardInput implements InputDevice {
    private String seed = "";


    @Override
    public String getSeed() {
         return seed;
     }

    @Override
    /* return thr character the user typed. */
    public char getNextChar() {
        char keyTypedFromTheUser = StdDraw.nextKeyTyped();
        return keyTypedFromTheUser;
    }

    @Override
    /* return true if the user has typed a character, else false. */
    public boolean hasNextChar() {
        return StdDraw.hasNextKeyTyped();
    }


    @Override
    /* collect the seed form the user. */
    public String collectTheSeed(String input){
        seed += input;
        return seed;
    }

 }
