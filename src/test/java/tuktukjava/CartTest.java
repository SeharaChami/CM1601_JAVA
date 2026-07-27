package tuktukjava;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartTest {

    private Cart cart;
    private Item engineItem;
    private Item electricalItem;
    private Item brakeItem;
    private Inventory inventory;

    private Item makeItem(String code, String name, String brand, String price,
                          String qty, String category, String date, String img, String threshold) {
        String[] parts = {code, name, brand, price, qty, category, date, img, threshold};
        return new Item(parts);
    }

    @BeforeEach
    public void setUp() throws IOException {
        cart = new Cart();
        engineItem     = makeItem("P001", "Piston",     "Bajaj", "Rs 4500.00", "20", "ENGINE",     "2023-10-12", "piston.jpg", "10");
        electricalItem = makeItem("P004", "Spark Plug", "NGK",   "Rs 850.00",  "50", "ELECTRICAL", "2024-01-05", "spark.jpg",  "10");
        brakeItem      = makeItem("P002", "Brake Pad",  "TVS",   "Rs 1250.00", "15", "BRAKES",     "2023-05-12", "brake.png",  "10");

        List<Item> items = new ArrayList<>();
        items.add(engineItem);
        items.add(electricalItem);
        items.add(brakeItem);
        inventory = new Inventory(items);
    }
    @Test
    public void testAddItemNewItem() {
        cart.addItem(engineItem, 1);
        assertEquals(1, cart.getItemCount());
    }

    @Test
    public void testAddItemSameItemTwice() {
        cart.addItem(engineItem, 2);
        cart.addItem(engineItem, 3);
        assertEquals(1, cart.getItemCount());
        assertEquals(5, cart.getCartItems().get(0).getQty());
    }

    @Test
    public void testAddItemDifferentItems() {
        cart.addItem(engineItem, 1);
        cart.addItem(brakeItem, 1);
        assertEquals(2, cart.getItemCount());
    }

    @Test
    public void testRemoveItem() {
        cart.addItem(engineItem, 1);
        cart.addItem(brakeItem, 1);
        cart.removeItem("P001");
        assertEquals(1, cart.getItemCount());
        assertEquals("P002", cart.getCartItems().get(0).getItem().item[0]);
    }

    @Test
    public void testClearCart() {
        cart.addItem(engineItem, 2);
        cart.addItem(brakeItem, 1);
        cart.clear();
        assertEquals(0, cart.getItemCount());
    }

    @Test
    public void testGetSubtotal() {
        cart.addItem(brakeItem, 2); // 1250 * 2 = 2500
        assertEquals(2500.00, cart.getSubtotal(), 0.01);
    }

    @Test
    public void testGetBulkDiscount() {
        cart.addItem(brakeItem, 3);
        assertEquals(187.50, cart.getBulkDiscount(), 0.01);
    }

    @Test
    public void testGetBulkDiscount_qtyMoreThan3() {
        cart.addItem(brakeItem, 5);
        assertEquals(312.50, cart.getBulkDiscount(), 0.01);
    }

    @Test
    public void testGetBulkDiscount_qtyLessThan3() {
        cart.addItem(brakeItem, 2);
        assertEquals(0.0, cart.getBulkDiscount(), 0.01);
    }

    @Test
    public void testGetBulkDiscount_qty1() {
        cart.addItem(engineItem, 1);
        assertEquals(0.0, cart.getBulkDiscount(), 0.01);
    }

    // --- synergy discount ---

    @Test
    public void testHasSynergyCombo_engineAndElectrical() {
        cart.addItem(engineItem, 1);
        cart.addItem(electricalItem, 1);
        assertTrue(cart.hasSynergyCombo());
    }

    @Test
    public void testHasSynergyCombo_engineOnly() {
        cart.addItem(engineItem, 1);
        assertFalse(cart.hasSynergyCombo());
    }

    @Test
    public void testHasSynergyCombo_electricalOnly() {
        cart.addItem(electricalItem, 1);
        assertFalse(cart.hasSynergyCombo());
    }

    @Test
    public void testGetSynergyDiscount_withCombo() {
        cart.addItem(engineItem, 1);     // 4500
        cart.addItem(electricalItem, 1); // 850 → afterBulk = 5350, 10% = 535
        assertEquals(535.00, cart.getSynergyDiscount(), 0.01);
    }

    @Test
    public void testGetSynergyDiscount_withoutCombo() {
        cart.addItem(engineItem, 1);
        assertEquals(0.0, cart.getSynergyDiscount(), 0.01);
    }

    @Test
    public void testGetTotal_bulkAndSynergyBoth() {
        cart.addItem(engineItem, 3);
        cart.addItem(electricalItem, 1);
        assertEquals(12307.50, cart.getTotal(), 0.01);
    }


    @Test
    public void testValidateSaleEmptyCart() {
        String result = cart.validateSale(inventory);
        assertEquals("Cart is empty", result);
    }

    @Test
    public void testValidateSaleValidCart() {
        cart.addItem(engineItem, 2);
        assertNull(cart.validateSale(inventory));
    }

    @Test
    public void testValidateSaleQtyExceedsStock() {
        cart.addItem(engineItem, 100);
        assertNotNull(cart.validateSale(inventory));
    }

    @Test
    public void testValidateSaleQtyEqualsStock() {
        cart.addItem(engineItem, 20);
        assertNull(cart.validateSale(inventory));
    }

    @Test
    public void testReduceStock() {
        cart.addItem(engineItem, 5);
        cart.reduceStock();
        assertEquals("15", engineItem.item[4]);
    }

    @Test
    public void testReduceStock_fullStock() {
        cart.addItem(brakeItem, 15);
        cart.reduceStock();
        assertEquals("0", brakeItem.item[4]);
    }
}
