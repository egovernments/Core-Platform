package org.egov.collection.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;

import java.math.BigDecimal;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;

class UtilsTest {

    @Test
    void testJsonMerge() {
        MissingNode mainNode = MissingNode.getInstance();
        assertTrue(Utils.jsonMerge(mainNode, MissingNode.getInstance()) instanceof MissingNode);
    }

    @Test
    void testJsonMerge2() {
        assertTrue(Utils.jsonMerge(null, MissingNode.getInstance()) instanceof MissingNode);
    }

    @Test
    void testJsonMerge3() {
        NullNode mainNode = NullNode.getInstance();
        assertTrue(Utils.jsonMerge(mainNode, MissingNode.getInstance()) instanceof MissingNode);
    }

    @Test
    void testJsonMerge4() {
        assertTrue(Utils.jsonMerge(MissingNode.getInstance(), null) instanceof MissingNode);
    }

    @Test
    void testJsonMerge5() {
        MissingNode mainNode = MissingNode.getInstance();
        assertTrue(Utils.jsonMerge(mainNode, NullNode.getInstance()) instanceof MissingNode);
    }

    @Test
    void testIsPositiveInteger() {
        assertTrue(Utils.isPositiveInteger(BigDecimal.valueOf(1L)));
        assertTrue(Utils.isPositiveInteger(BigDecimal.valueOf(0L)));
        assertFalse(Utils.isPositiveInteger(BigDecimal.valueOf(-1L)));
    }
}

