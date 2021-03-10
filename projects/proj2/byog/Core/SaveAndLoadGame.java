package byog.Core;
import java.io.*;
import java.util.ArrayList;

public class SaveAndLoadGame<T> implements Serializable {
    private String fileNameToBeSaved;


    public SaveAndLoadGame(String fileNameToBeSaved) {
        this.fileNameToBeSaved = fileNameToBeSaved;
    }


    /* save the needed object. */
    public void saveGame(ArrayList<T> saveToFile) {
        File f = new File(fileNameToBeSaved);
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(saveToFile);
            fos.close();
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /* load all the objects of the game and then play the game as usual. */
    public ArrayList<T> loadGame() {
        File f = new File(fileNameToBeSaved);
        ArrayList<T> objectToBeReturned = null;
        try {
            FileInputStream fos = new FileInputStream(f);
            ObjectInputStream oos = new ObjectInputStream(fos);
            objectToBeReturned = (ArrayList<T>) oos.readObject();
            fos.close();
            oos.close();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return objectToBeReturned;
    }

}

