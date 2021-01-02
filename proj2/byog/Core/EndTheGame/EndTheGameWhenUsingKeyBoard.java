package byog.Core.EndTheGame;

import byog.Core.InteractivityInTheWorld;

import java.io.Serializable;

public class EndTheGameWhenUsingKeyBoard implements EndTheGame, Serializable {
    private InteractivityInTheWorld interactivityInTheWorld;

    public EndTheGameWhenUsingKeyBoard(InteractivityInTheWorld interactivityInTheWorld) {
        this.interactivityInTheWorld = interactivityInTheWorld;
    }

    @Override
    public boolean isTheGameEnded() {
        return interactivityInTheWorld.isGameOver() || interactivityInTheWorld.isTheUserQuitTheGame();
    }
}
