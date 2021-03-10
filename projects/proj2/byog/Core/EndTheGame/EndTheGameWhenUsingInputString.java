package byog.Core.EndTheGame;

import byog.Core.Input.InputString;
import byog.Core.InteractivityInTheWorld;

import java.io.Serializable;

public class EndTheGameWhenUsingInputString implements Serializable, EndTheGame {
    private InputString inputString;

    public EndTheGameWhenUsingInputString(InputString inputString) {
        this.inputString = inputString;
    }

    @Override
    public boolean isTheGameEnded(InteractivityInTheWorld interactivityInTheWorld) {
        int lengthOfInput = inputString.getLengthOfInput();
        int endSlicingIndex = inputString.getEndSlicingIndex();
        return lengthOfInput < endSlicingIndex || interactivityInTheWorld.isTheUserQuitTheGame();
    }
}
