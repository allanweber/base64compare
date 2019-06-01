package com.waes.base64compare.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Objects;

/**
 * Response to get in API to check base64 differences.
 */
@JsonPropertyOrder({"id", "equal", "equalSize", "differences", "left", "right"})
public class DifferenceResponse {

    /**
     * The id of transaction.
     */
    private Long id;

    /**
     * Value of base64 on left side.
     */
    private String left;

    /**
     * Value of base64 on right side.
     */
    private String right;

    /**
     * Indicates if both sides are equals.
     */
    private boolean equal;

    /**
     * Indicates if both sides have the same length.
     */
    private boolean equalSize;

    /**
     * List of differences if arrays are not equal but have same length.
     */
    private List<Difference> differences;

    @JsonIgnore
    private List<byte[]> sides;

    /**
     * Only possible constructor setting common data.
     * @param id is the id of the transaction.
     * @param left value of left side.
     * @param right value of right side.
     */
    public DifferenceResponse(Long id, String left, String right) {
            this.id = Objects.requireNonNull(id, "Id is a required argument.");
        this.left =  Objects.requireNonNull(left, "Left is a required argument.");
        this.right =  Objects.requireNonNull(right, "Right is a required argument.");
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
        this.equalSize = equal;
    }

    public void setEqualSize(boolean equalSize) {
        this.equalSize = equalSize;
    }

    public void setDifferences(List<Difference> differences) {
        this.differences = differences;
    }

    public Long getId() {
        return id;
    }

    public Boolean getEqual() {
        return equal;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public Boolean getEqualSize() {
        return equalSize;
    }

    public List<Difference> getDifferences() {
        return differences;
    }

    public List<byte[]> getSides() {
        return sides;
    }

    public void setSides(List<byte[]> sides) {
        this.sides = sides;
    }
}
