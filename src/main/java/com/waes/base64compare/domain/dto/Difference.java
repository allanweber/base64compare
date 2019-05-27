package com.waes.base64compare.domain.dto;

/**
 * Store the difference data.
 */
public class Difference {

    /**
     * The position where difference started.
     */
    private int position;

    /**
     * The size of difference from start position.
     */
    private int offSet;

    /**
     * Only possible constructor setting both class variables.
     * @param position the position where difference started.
     * @param offSet the size of difference from start position.
     */
    public Difference(int position, int offSet) {
        this.position = position;
        this.offSet = offSet;
    }

    public int getPosition() {
        return position;
    }

    public int getOffSet() {
        return offSet;
    }
}
