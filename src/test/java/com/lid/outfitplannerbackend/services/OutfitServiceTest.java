package com.lid.outfitplannerbackend.services;

import com.lid.outfitplannerbackend.model.Color;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OutfitServiceTest {
    private OutfitService outfitService = new OutfitService(null, null, null, null, null);

    @Test
    void getFirstColor() {
        assertThrows(IndexOutOfBoundsException.class, () -> outfitService.getFirstColor(emptyList()));
        Color color = new Color();
        color.setName("gray");
        assertEquals(color, outfitService.getFirstColor(singletonList(color)));
        color.setName("blue");
        assertEquals(color, outfitService.getFirstColor(singletonList(color)));
        Color black = new Color();
        black.setName("black");
        assertEquals(color, outfitService.getFirstColor(asList(black, color)));
    }
}