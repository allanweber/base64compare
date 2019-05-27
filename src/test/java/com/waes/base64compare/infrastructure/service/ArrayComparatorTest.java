package com.waes.base64compare.infrastructure.service;

import com.waes.base64compare.domain.dto.Difference;
import com.waes.base64compare.domain.exception.ArrayNotEqualException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ArrayComparatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void should_return_null_differences_when_arrays_do_not_have_differences() {
        byte[] left = "Allan Weber".getBytes();
        byte[] right = "Allan Weber".getBytes();

        List<Difference> differences = ArrayComparator.getDifferences(left, right);
        assertNull(differences);
    }

    @Test
    public void should_return_null_differences_when_arrays_are_different() {
        byte[] left = "Allan Cassiano Weber".getBytes();
        byte[] right = "Allan Weber Cassiano".getBytes();

        List<Difference> differences = ArrayComparator.getDifferences(left, right);
        assertNotNull(differences);

        Difference difference = differences.iterator().next();
        assertEquals(6,difference.getPosition());
        assertEquals(14,difference.getOffSet());
    }

    @Test
    public void should_throw_NullPointerException_when_left_array_is_null(){
        expectedException.expectMessage("Left side array must not be null.");
        expectedException.expect(NullPointerException.class);

        ArrayComparator.getDifferences(null, null);
    }

    @Test
    public void should_throw_NullPointerException_when_right_array_is_null(){
        expectedException.expectMessage("Right side array must not be null.");
        expectedException.expect(NullPointerException.class);

        byte[] left = "Allan Cassiano Weber".getBytes();
        ArrayComparator.getDifferences(left, null);
    }

    @Test
    public void should_throw_ArrayNotEqualException_when_arrays_sizes_are_different(){
        expectedException.expectMessage("Arrays have different length: 20 to the Left and 14 to the Right.");
        expectedException.expect(ArrayNotEqualException.class);

        byte[] left = "Allan Cassiano Weber".getBytes();
        byte[] right = "Allan Cassiano".getBytes();

        List<Difference> differences = ArrayComparator.getDifferences(left, right);
    }
}