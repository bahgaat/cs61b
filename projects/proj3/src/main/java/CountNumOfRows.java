class CountNumOfRows implements Count {

    @Override
    public boolean notMatchedTheRequestedQuery(Double lrlonTheUserRequested, Double helperLrlon,
                                               Double max) {

        return lrlonTheUserRequested < helperLrlon && helperLrlon > max;
    }
}
