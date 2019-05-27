package com.waes.base64compare.domain.dto;

public class Difference {

    private int position;

    private int offSet;

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
