package com.waes.base64compare.domain.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"id", "equal", "equalSize", "differences", "left", "right"})
public class DifferenceResponse {

    private Long id;

    private String left;

    private String right;

    private boolean equal;

    private boolean equalSize;

    private List<Difference> differences;

    public DifferenceResponse(Long id, String left, String right) {
        this.id = id;
        this.left = left;
        this.right = right;
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
}
