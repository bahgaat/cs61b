package byog.Core.Draw;
import java.io.Serializable;
import java.util.Map;

import java.util.ArrayList;

/* drawing shapes. for example, L, squares, rectangles in many different forms. To initiate drawShapes,
you have to pass to the it a map which has positions as keys such as 'x' and 'y' and an empty arrayList as values.
What drawShapes do is that if you call it with any method. For eg, drawLine it puts
in the arrayList of each key in the map the needed integers and then When drawing ,the other class that called drawShapes
can get the map. */
public class DrawShapes implements Serializable {
    private final String positive = "positive";
    private final String negative = "negative";
    private final String vertical = "vertical";
    private final String horizontal = "horizontal";
    private Map<Character, ArrayList<Integer>> mapOfDirections;

    public DrawShapes(Map<Character, ArrayList<Integer>> mapOfDirections) {
        this.mapOfDirections = mapOfDirections;
    }

    public Map<Character, ArrayList<Integer>> geMapOfDirections() {
        return mapOfDirections;
    }

    public void setMapOfDirections(Map<Character, ArrayList<Integer>> mapOfDirections) {
        this.mapOfDirections = mapOfDirections;
    }

    /* draw letter L, Start drawing from the vertical line. The direction determines if it is a typical letter L
        or a negative L, which is similar to L but looks toward the left side. */
    void drawLStartFromVerticalLine(int sizeOfVerticalPartOfL, int sizeOfHorizontalPartOfL, String direction) {

        drawLine(sizeOfVerticalPartOfL, vertical, negative);
        drawLine(sizeOfHorizontalPartOfL, horizontal, direction);
    }

    /* draw the opposite of L, Start drawing from the vertical line. The direction determines if is a positive
    opposite of L, which looks to the right or a Negative opposite, which looks to the left.. */
    void drawOppositeLStartFromVerticalLine(int sizeOfVerticalPartOfOppositeL,int sizeOfHorizontalPartOfOppositeL,
                                            String direction) {

        drawLine(sizeOfVerticalPartOfOppositeL, vertical, positive);
        drawLine(sizeOfHorizontalPartOfOppositeL, horizontal, direction);
    }

    /* draw letter L, Start drawing from the horizontal line. The direction determines if it is a typical letter L
     or a negative L, which is similar to L but looks toward the left side. */
    void drawLStartFromHorizontalLine(int sizeOfHorizontalLine, int sizeOfVerticalLine,
                                      String direction) {

        String directionOfHorizontalLine;
        if (direction.equals(positive)) {
            directionOfHorizontalLine = negative;
        } else {
            directionOfHorizontalLine = positive;
        }
        drawLine(sizeOfHorizontalLine, horizontal, directionOfHorizontalLine);
        drawLine(sizeOfVerticalLine, vertical, positive);
    }

    /* draw the opposite of L, Start drawing from the horizontal line. The direction determines if is a positive
    opposite of L, which looks to the right or a negative opposite, which looks to the left.. */
    void drawOppositeOfLStartFromHorizontalLine(int sizeOfHorizontalLine, int sizeOfVerticalLine,
                                                String direction) {

        String directionOfHorizontalLine;
        if (direction.equals(positive)) {
            directionOfHorizontalLine = negative;
        } else {
            directionOfHorizontalLine = positive;
        }
        drawLine(sizeOfHorizontalLine, horizontal, directionOfHorizontalLine);
        drawLine(sizeOfVerticalLine, vertical, negative);
    }

    /* draw a shape which is similar to half square, which looks to the left side. Start drawing the shape from bottom.*/
    void drawLeftHalfSquare(int sizeOfBottomHorizontalLine, int sizeOfVerticalLine,
                            int sizeOfUpperHorizontalLine) {

        drawLine(sizeOfBottomHorizontalLine, horizontal, positive);
        drawLine(sizeOfVerticalLine, vertical, positive);
        drawLine(sizeOfUpperHorizontalLine, horizontal, negative);
    }

    /* draw a shape which is similar to half square, which looks to the right. Start drawing the shape from bottom */
    void drawRightHalfSquare(int sizeOfBottomHorizontalLine, int sizeOfVerticalLine,
                             int sizeOfUpperHorizontalLine) {

        drawLine(sizeOfBottomHorizontalLine, horizontal, negative);
        drawLine(sizeOfVerticalLine, vertical, positive);
        drawLine(sizeOfUpperHorizontalLine, horizontal, positive);
    }

    /* draw a shape which is similar to half square, which looks down. Start drawing the shape from right. */
    void drawBottomHalfSquare(int sizeOfRightVerticalLine, int sizeOfHorizontalLine,
                              int sizeOfLeftVerticalLine) {

        drawLine(sizeOfRightVerticalLine, vertical, positive);
        drawLine(sizeOfHorizontalLine, horizontal, negative);
        drawLine(sizeOfLeftVerticalLine, vertical, negative);
    }

    /* draw a shape which is similar to half square, which looks up. Start drawing the shape from right. */
    void drawUpperHalfSquare(int sizeOfRightVerticalLine, int sizeOfHorizontalLine,
                             int sizeOfLeftVerticalLine) {

        drawLine(sizeOfRightVerticalLine, vertical, negative);
        drawLine(sizeOfHorizontalLine, horizontal, negative);
        drawLine(sizeOfLeftVerticalLine, vertical, positive);
    }

    /* draw a rectangle, which looks down. its direction determines if the rectangle will be drawn
    toward right or left. the shape ends at the highest part of the last size */
    void drawBottomRectangle(int sizeOfVerticalLine, int sizeOfHorizontalLine,
                             String direction) {

        String directionOfHorizontalLine;
        if (direction.equals(positive)) {
            directionOfHorizontalLine = positive;
        } else {
            directionOfHorizontalLine = negative;
        }
        if (sizeOfHorizontalLine == 1) {
            drawLine(sizeOfVerticalLine, vertical, negative);
            drawLine(sizeOfVerticalLine, vertical, positive);
        }
        while (sizeOfHorizontalLine > 1) {
            drawLine(sizeOfVerticalLine, vertical, negative);
            drawLine(2, horizontal, directionOfHorizontalLine);
            drawLine(sizeOfVerticalLine, vertical, positive);
            sizeOfHorizontalLine -= 1;
        }
    }


    /* draw a rectangle, which looks up. its direction determines if the rectangle will be drawn
    toward right or left. The shape ends at the lowest part of the last size. */
    void drawUpperRectangle(int sizeOfVerticalLine, int sizeOfHorizontalLine, String direction) {

        String directionOfHorizontalLine;
        if (direction.equals(positive)) {
            directionOfHorizontalLine = positive;
        } else {
            directionOfHorizontalLine = negative;
        }
        if (sizeOfHorizontalLine == 1) {
            drawLine(sizeOfVerticalLine, vertical, positive);
            drawLine(sizeOfVerticalLine, vertical, negative);
        }
        while (sizeOfHorizontalLine > 1) {
            drawLine(sizeOfVerticalLine, vertical, positive);
            drawLine(2, horizontal, directionOfHorizontalLine);
            drawLine(sizeOfVerticalLine, vertical, negative);
            sizeOfHorizontalLine -= 1;
        }
    }

    /* draw line, its position determines if it is vertical or horizontal, and its direction determines if the line
    is positive or negative. While drawing any line, we start counting from the present line. for eg, if i want to
    draw aline 1 step from where i am standing, I have to move 2 steps because the step that i stand on is counted. */
    void drawLine(int size, String position, String direction) {
        int addToX = 0;
        int addToY = 0;
        ArrayList<Integer> distancesToAddToX = mapOfDirections.get('x');
        ArrayList<Integer> distancesToAddToY = mapOfDirections.get('y');
        while (size > 0) {
            distancesToAddToX.add(addToX);
            distancesToAddToY.add(addToY);
            if (position.equals(vertical) && direction.equals(positive) && size > 1) {
                addToY = 1;
            } else if (position.equals(vertical) && direction.equals(negative) && size > 1) {
                addToY = -1;
            } else if (position.equals(horizontal) && direction.equals(positive) && size > 1) {
                addToX = 1;
            } else if (position.equals(horizontal) && direction.equals(negative) && size > 1) {
                addToX = -1;
            }
            size -= 1;
        }
    }

}
