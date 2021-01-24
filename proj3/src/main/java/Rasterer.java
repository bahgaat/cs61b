import java.util.Map;
import java.util.HashMap;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */

    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Double lrlonTheUserRequested = params.get("lrlon");
        Double ullonTheUserRequested = params.get("ullon");
        Double ullatTheUserRequested = params.get("ullat");
        Double lrlatTheUserRequested = params.get("lrlat");
        Double widthTheUserRequested = params.get("w");
        Double LonDPP = (lrlonTheUserRequested - ullonTheUserRequested) / (widthTheUserRequested);
        int depthOfTheImages = findDepthOfTheImages(LonDPP);

        /* calculate the ullatOf_dDepth_x0_y0, ullongOf_dDepth_x0_y0, lrlatOf_dDepth_x0_y0, and lrlongOf_dDpeth_x0_y0
        to help me in finding the needed images. */
        Double ullatOf_dDepth_x0_y0 = MapServer.ROOT_ULLAT;
        Double ullongOf_dDepth_x0_y0 = MapServer.ROOT_ULLON;
        Double lrlatOf_dDepth_x0_y0 = MapServer.ROOT_LRLAT;
        Double lrlongOf_dDpeth_x0_y0 = MapServer.ROOT_LRLON;
        Double spaceBetweenUppAndLowerLat = ((ullatOf_dDepth_x0_y0 - lrlatOf_dDepth_x0_y0) /
                (Math.pow(2, depthOfTheImages)));
        Double spaceBetweenUppAndLowerLong = ((ullongOf_dDepth_x0_y0 - lrlongOf_dDpeth_x0_y0) /
                (Math.pow(2, depthOfTheImages)));
        lrlatOf_dDepth_x0_y0 = ullatOf_dDepth_x0_y0 - spaceBetweenUppAndLowerLat;
        lrlongOf_dDpeth_x0_y0 = ullongOf_dDepth_x0_y0 - spaceBetweenUppAndLowerLong;

        /* calculate x and y values of the first matched image. */
        int xOfTheFirstImage = (int) ((ullonTheUserRequested - ullongOf_dDepth_x0_y0) / (-spaceBetweenUppAndLowerLong));
        int yOfTheFirstImage = (int) ((ullatTheUserRequested - ullatOf_dDepth_x0_y0) / (-spaceBetweenUppAndLowerLat));


        /* calculate ullong, ullat, llrat, llrlon of the first matched image. */
        Double ullongOfTheFirstImage = (xOfTheFirstImage * spaceBetweenUppAndLowerLong) - (ullongOf_dDepth_x0_y0);
        Double ullatOfTheFirstImage = (yOfTheFirstImage * spaceBetweenUppAndLowerLat) - (ullatOf_dDepth_x0_y0);
        ullongOfTheFirstImage = (ullongOfTheFirstImage) * -1;
        ullatOfTheFirstImage = (ullatOfTheFirstImage) * -1;
        Double lrlatOfTheFirstMatchedImage = ullatOfTheFirstImage - spaceBetweenUppAndLowerLat;
        Double lrlongOfTheFirstMatchedImage = ullongOfTheFirstImage - spaceBetweenUppAndLowerLong;


        /* find number of columns and rows that will be displayed to the user. */
        Count countNumOfCol = new CountNumOfCol();
        int numberOfColToBeDisplayed = calcNumThatWillBeDisplayedToUser(ullongOfTheFirstImage,
                lrlongOfTheFirstMatchedImage, lrlonTheUserRequested, spaceBetweenUppAndLowerLong,
                MapServer.ROOT_LRLON, countNumOfCol);

        Count countNumOfRows = new CountNumOfRows();
        int numbOfRowsToBeDisplayed = calcNumThatWillBeDisplayedToUser(ullatOfTheFirstImage,
                lrlatOfTheFirstMatchedImage, lrlatTheUserRequested, spaceBetweenUppAndLowerLat,
                MapServer.ROOT_LRLAT, countNumOfRows);


        Map<String, Object> results = new HashMap<>();
        addAllNeededValuesToMap(results, numbOfRowsToBeDisplayed, numberOfColToBeDisplayed, xOfTheFirstImage,
                yOfTheFirstImage, depthOfTheImages, lrlongOfTheFirstMatchedImage, spaceBetweenUppAndLowerLong,
                spaceBetweenUppAndLowerLat, lrlatOfTheFirstMatchedImage, ullongOfTheFirstImage, ullatOfTheFirstImage);
        return results;
    }

    private void addAllNeededValuesToMap(Map<String, Object> results, int numbOfRowsToBeDisplayed,
                                         int numberOfColToBeDisplayed, int xOfTheFirstImage,
                                         int yOfTheFirstImage, int depthOfTheImages,
                                         Double lrlongOfTheFirstMatchedImage,
                                         Double spaceBetweenUppAndLowerLong, Double spaceBetweenUppAndLowerLat,
                                         Double lrlatOfTheFirstMatchedImage,
                                         Double ullongOfTheFirstImage, Double ullatOfTheFirstImage) {

        String[][] matchedImages = new String[numbOfRowsToBeDisplayed][numberOfColToBeDisplayed];
        putMatchedImagesInThArr(matchedImages, numbOfRowsToBeDisplayed, numberOfColToBeDisplayed,
                xOfTheFirstImage, yOfTheFirstImage, depthOfTheImages);
        Double lrlongOfTheLastMatchedImage = lrlongOfTheFirstMatchedImage -
                (spaceBetweenUppAndLowerLong * (numberOfColToBeDisplayed - 1));
        Double lrlatOfTheLastMatchedImage = lrlatOfTheFirstMatchedImage -
                (spaceBetweenUppAndLowerLat * (numbOfRowsToBeDisplayed - 1));
        results.put("raster_ul_lon", ullongOfTheFirstImage);
        results.put("raster_ul_lat", ullatOfTheFirstImage);
        results.put("depth", depthOfTheImages);
        results.put("raster_lr_lon", lrlongOfTheLastMatchedImage);
        results.put("raster_lr_lat", lrlatOfTheLastMatchedImage);
        results.put("render_grid", matchedImages);
        results.put("query_success", true);
    }

    /* iterate through the matched images and add them to the array(matchedImages). */
    private void putMatchedImagesInThArr(String[][] matchedImages, int numbOfRowsToBeDisplayed,
                              int numberOfColToBeDisplayed, int xOfTheFirstImage, int yOfTheFirstImage,
                              int depthOfTheImages) {

        int xOfTheImage = xOfTheFirstImage;
        int yOfTheImage = yOfTheFirstImage;
        for (int row = 0; row < numbOfRowsToBeDisplayed; row += 1) {
            xOfTheImage = xOfTheFirstImage;
            for (int col = 0; col < numberOfColToBeDisplayed; col += 1) {
                matchedImages[row][col] = "d"+depthOfTheImages+"_x"+xOfTheImage+"_y"+yOfTheImage+".png";
                xOfTheImage += 1;
            }
            yOfTheImage += 1;
        }
    }

    /* calculate num of columns or rows that will be displayed to the user from the given query. */
    private int calcNumThatWillBeDisplayedToUser(Double ulOfTheFirstImage, Double lrOfTheFirstImage,
                                           Double lrTheUserRequested, Double spaceBetweenUppAndLower,
                                           Double max, Count countNum) {
        int num = 1;
        Double helperUl = ulOfTheFirstImage;
        Double helperLr = lrOfTheFirstImage;
        while (countNum.notMatchedTheRequestedQuery(lrTheUserRequested, helperLr, max)) {
            helperUl  = helperLr;
            helperLr = helperLr - spaceBetweenUppAndLower;
            num += 1;
        }
        return num;
    }

    /* find depth of the matched images. */
    private int findDepthOfTheImages(Double queryLonDPP) {
        int depth = 0;
        Double LonDPP = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE);
        /* This is the LonDPP of depth 0. */
        while (LonDPP > queryLonDPP && depth < 7) {
            LonDPP = LonDPP * 0.5;
            depth += 1;
        }
        return depth;
    }


}
