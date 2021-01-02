package byog.Core.EndTheGame;

import byog.Core.Input.InputString;
import byog.Core.InteractivityInTheWorld;

import java.io.Serializable;

public class EndTheGameWhenUsingInputString implements Serializable, EndTheGame {
    private InputString inputString;
    private InteractivityInTheWorld interactivityInTheWorld;

    public EndTheGameWhenUsingInputString(InputString inputString, InteractivityInTheWorld interactivityInTheWorld) {
        this.inputString = inputString;
        this.interactivityInTheWorld = interactivityInTheWorld;
    }

    @Override
    public boolean isTheGameEnded() {
        int lengthOfInput = inputString.getLengthOfInput();
        int endSlicingIndex = inputString.getEndSlicingIndex();
        return lengthOfInput < endSlicingIndex || interactivityInTheWorld.isTheUserQuitTheGame();
    }
}
