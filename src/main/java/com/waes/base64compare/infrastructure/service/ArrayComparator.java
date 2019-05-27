package com.waes.base64compare.infrastructure.service;

import com.waes.base64compare.domain.dto.Difference;
import com.waes.base64compare.domain.exception.ArrayNotEqualException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class with static methods to compare arrays differences.
 */
public class ArrayComparator {

    /**
     * Check the difference between two arrays with same size.
     * If any array is null, throw NullPointerException.
     * If arrays have different length throw ArrayNotEqualException.
     *
     * @param left  is the array of left side to comparison.
     * @param right is the array of right side to comparison.
     * @return a list of positions and offSets of the differences.
     */
    public static List<Difference> getDifferences(byte[] left, byte[] right) {

        Objects.requireNonNull(left, "Left side array must not be null.");
        Objects.requireNonNull(right, "Right side array must not be null.");

        //Throw a exception if arrays are not same in length.
        if (left.length != right.length) {
            throw new ArrayNotEqualException(left.length, right.length);
        }

        //Create some control variables.
        List<Difference> differences = new ArrayList<>(); //List of differences to return.
        int offSetStart = -1; // the index stating the differences, must bach to -1 when finish the block of differences.
        int offSetSize = 1; // the size of differences, starts with one cause the offSetStart will be the 0 index.
        int lastIndex = (left.length - 1); // store last index to check if finish for loop and have differences until the end.

        //Loop using left because both arrays have same length.
        for (int i = 0; i < left.length; i++) {

            //If both arrays are different in current index start to store the position and offSet.
            if (left[i] != right[i]) {
                if (offSetStart == -1) { // control if already start to check.
                    offSetStart = i; //if its is the first difference store as start.
                } else {
                    offSetSize++; //if already started to store the difference, increse the size.
                }
            } else { //Is there is eny difference in current index.
                if (offSetStart != -1) {//But started to store differences previously.
                    differences.add(new Difference(offSetStart, offSetSize)); // add position and size to the list.
                    offSetSize = 1; //reset size;
                    offSetStart = -1;// reset start index.
                }
            }
            //If is the last loop array and have some difference;
            if (i == lastIndex && offSetStart != -1) {
                differences.add(new Difference(offSetStart, offSetSize)); // add position and size to the list.
            }
        }

        //return null if there is no differences.
        return differences.size() > 0 ? differences : null;
    }

}
