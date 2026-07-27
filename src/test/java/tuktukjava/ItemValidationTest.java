package tuktukjava;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemValidationTest {

    private Item item;

    @BeforeEach
    public void setUp() {
        item = new Item(new String[9]);
    }
    @Test
    public void testGetPriceNumber() {
        String result = item.getPrice("4500");
        assertEquals("Rs 4500.00", result);
    }

    @Test
    public void testGetPriceDecimal() {
        String result = item.getPrice("1250.50");
        assertEquals("Rs 1250.50", result);
    }

    @Test
    public void testGetPrice_nonNumeric() {
        String result = item.getPrice("abc");
        assertNull(result);
    }

    @Test
    public void testGetPrice_zero() {
        String result = item.getPrice("0");
        assertNull(result);
    }

    @Test
    public void testGetPrice_negative() {
        String result = item.getPrice("-500");
        assertNull(result);
    }

    @Test
    public void testGetPriceEmpty() {
        String result = item.getPrice("");
        assertNull(result);
    }

    @Test
    public void testGetQtyNumber() {
        String result = item.getQty("10");
        assertEquals("10", result);
    }

    @Test
    public void testGetQtyZero() {
        String result = item.getQty("0");
        assertNull(result);
    }

    @Test
    public void testGetQtyNegative() {
        String result = item.getQty("-5");
        assertNull(result);
    }

    @Test
    public void testGetQty_nonNumeric() {
        String result = item.getQty("abc");
        assertNull(result);
    }

    @Test
    public void testGetQtyEmpty() {
        String result = item.getQty("");
        assertNull(result);
    }
    @Test
    public void testImageValidate_png() {
        assertTrue(item.imageValidate("photo.png"));
    }

    @Test
    public void testImageValidate_jpg() {
        assertTrue(item.imageValidate("photo.jpg"));
    }

    @Test
    public void testImageValidate_jpeg() {
        assertTrue(item.imageValidate("photo.jpeg"));
    }

    @Test
    public void testImageValidate_txt() {
        assertFalse(item.imageValidate("photo.txt"));
    }

    @Test
    public void testImageValidateNoExtension() {
        assertFalse(item.imageValidate("photo"));
    }
}
