package byog.Core.Input;
import edu.princeton.cs.introcs.StdDraw;
import java.io.Serializable;


public class KeyBoardInput implements InputDevice, Serializable {
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
