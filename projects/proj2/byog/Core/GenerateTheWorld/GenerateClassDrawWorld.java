package byog.Core.GenerateTheWorld;
import byog.Core.Draw.DrawShapes;
import byog.Core.Draw.DrawWorld;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GenerateClassDrawWorld implements Serializable {

    protected DrawWorld initializeDrawWorld() {
        Map<Character, ArrayList<Integer>> mapOfDirections = new HashMap<>();
        mapOfDirections.put('x', new ArrayList<Integer>());
        mapOfDirections.put('y', new ArrayList<Integer>());
        DrawShapes drawShapes = new DrawShapes(mapOfDirections);
        DrawWorld drawWorld = new DrawWorld(drawShapes);
        return drawWorld;
    }

}
