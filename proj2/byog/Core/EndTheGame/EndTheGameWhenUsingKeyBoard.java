package byog.Core.EndTheGame;
import byog.Core.InteractivityInTheWorld;
import java.io.Serializable;

public class EndTheGameWhenUsingKeyBoard implements EndTheGame, Serializable {

    @Override
    public boolean isTheGameEnded(InteractivityInTheWorld interactivityInTheWorld) {
        return interactivityInTheWorld.isGameOver() || interactivityInTheWorld.isTheUserQuitTheGame();
    }
}
