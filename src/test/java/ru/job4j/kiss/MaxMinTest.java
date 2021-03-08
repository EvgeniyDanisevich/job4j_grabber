package ru.job4j.kiss;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MaxMinTest {

    @Test
    public void whenFindMax() {
        MaxMin maxMin = new MaxMin();
        List<Integer> list = Arrays.asList(4, 2, 5, 1, 3);
        Comparator<Integer> comparator = Integer::compare;
        Assert.assertEquals(Integer.valueOf(5), maxMin.max(list, comparator));
    }

    @Test
    public void whenFindMin() {
        MaxMin maxMin = new MaxMin();
        List<Integer> list = Arrays.asList(4, 2, 5, 1, 3);
        Comparator<Integer> comparator = Integer::compare;
        Assert.assertEquals(Integer.valueOf(1), maxMin.min(list, comparator));
    }
}