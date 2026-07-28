package tuktukjava;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InventoryTest {

    private Inventory inventory;
    private Item itemA;
    private Item itemB;
    private Item itemC;
    private Item itemD;

    private Item makeItem(String code, String name, String brand, String price, String qty, String category, String date, String img, String threshold) {
        String[] parts = {code, name, brand, price, qty, category, date, img, threshold};
        return new Item(parts);
    }

    @BeforeEach
    public void setUp() throws IOException {
        itemA = makeItem("P001","Piston","Bajaj","Rs 4500.00","5","ENGINE","2023-10-12","p.jpg","10");
        itemB = makeItem("P002","Brake Pad","TVS","Rs 1250.00","15","BRAKES","2023-05-12","b.png","10");
        itemC = makeItem("P003","Spark Plug","NGK","Rs 850.00","9","ELECTRICAL","2024-01-05","s.jpg","10");
        itemD = makeItem("P004","Canopy","Local","Rs 8500.00","10","BODYWORK","2023-08-15","c.png","10");

        List<Item> items = new ArrayList<>();
        items.add(itemA);
        items.add(itemB);
        items.add(itemC);
        items.add(itemD);
        inventory = new Inventory(items);
    }
    @Test
    public void testGetLowStockItemsBelowThreshold() {
        List<Item> low = inventory.getLowStockItems();
        assertEquals(2, low.size());
    }

    @Test
    public void testGetLowStockItemsAboveThreshold() {
        List<Item> low = inventory.getLowStockItems();
        for (Item i : low) {
            assertNotEquals("P002", i.item[0]);
        }
    }

    @Test
    public void testGetLowStockItemsEqualToThreshold() {
        List<Item> low = inventory.getLowStockItems();
        for (Item i : low) {
            assertNotEquals("P004", i.item[0]);
        }
    }

    @Test
    public void testGetLowStockItems_nullThresholdField() throws IOException {
        String[] parts = {"P005", "Filter", "Piaggio", "Rs 1100.00", "3", "ENGINE", "2024-02-28", "f.jpg", null};
        Item item = new Item(parts);
        List<Item> items = new ArrayList<>();
        items.add(item);
        Inventory inv = new Inventory(items);
        assertEquals(1, inv.getLowStockItems().size());
    }

    @Test
    public void testGetLowStockItemsNullThreshold() throws IOException {
        String[] parts = {"P005","Filter","Piaggio","Rs 1100.00","3","ENGINE","2024-02-28","f.jpg","null"};
        Item item = new Item(parts);
        List<Item> items = new ArrayList<>();
        items.add(item);
        Inventory inv = new Inventory(items);
        assertEquals(1, inv.getLowStockItems().size());
    }

    @Test
    public void testGetLowStockItemsCustomThreshold() throws IOException {
        String[] parts = {"P006", "Mirror", "N/A", "Rs 800.00", "15", "BODYWORK", "2023-10-10", "m.png", "20"};
        Item item = new Item(parts);
        List<Item> items = new ArrayList<>();
        items.add(item);
        Inventory inv = new Inventory(items);
        assertEquals(1, inv.getLowStockItems().size());
    }

    @Test
    public void testSearchByCodeExistingCode() {
        Item found = inventory.searchByCode("P001");
        assertNotNull(found);
        assertEquals("P001", found.item[0]);
    }

    @Test
    public void testSearchByCodeCaseInsensitive() {
        Item found = inventory.searchByCode("p001");
        assertNotNull(found);
    }

    @Test
    public void testSearchByCodeNonExistentCode() {
        assertNull(inventory.searchByCode("P999"));
    }
    @Test
    public void testSearchByName() {
        List<Item> results = inventory.search("piston", "", "", 0, Double.MAX_VALUE, 0);
        assertEquals(1, results.size());
        assertEquals("P001", results.get(0).item[0]);
    }

    @Test
    public void testSearchByBrand() {
        List<Item> results = inventory.search("", "tvs", "", 0, Double.MAX_VALUE, 0);
        assertEquals(1, results.size());
        assertEquals("P002", results.get(0).item[0]);
    }

    @Test
    public void testSearchByCategory() {
        List<Item> results = inventory.search("", "", "engine", 0, Double.MAX_VALUE, 0);
        assertEquals(1, results.size());
        assertEquals("P001", results.get(0).item[0]);
    }

    @Test
    public void testSearchByPriceRange() {
        List<Item> results = inventory.search("", "", "", 1000, 2000, 0);
        assertEquals(1, results.size());
        assertEquals("P002", results.get(0).item[0]);
    }

    @Test
    public void testSearchoCombined() {
        List<Item> results = inventory.search("spark", "ngk", "electrical", 0, Double.MAX_VALUE, 0);
        assertEquals(1, results.size());
        assertEquals("P003", results.get(0).item[0]);
    }

    @Test
    public void testSearchNoMatch() {
        List<Item> results = inventory.search("nonexistent", "", "", 0, Double.MAX_VALUE, 0);
        assertEquals(0, results.size());
    }

    @Test
    public void testSearchByMinQty() {
        List<Item> results = inventory.search("", "", "", 0, Double.MAX_VALUE, 10);
        assertEquals(2, results.size());
    }

    @Test
    public void testGetItemsByCategory() {
        List<List<Item>> grouped = inventory.getItemsByCategory();
        assertEquals(4, grouped.size());
    }

    @Test
    public void testGetItemsByCategorySorted() throws IOException {
        String[] parts = {"P005", "Filter", "Piaggio", "Rs 1100.00", "8", "ENGINE", "2024-02-28", "f.jpg", "10"};
        Item extraEngine = new Item(parts);
        List<Item> items = new ArrayList<>();
        items.add(extraEngine); // P005 added first
        items.add(itemA);       // P001 added second
        Inventory inv = new Inventory(items);

        List<List<Item>> grouped = inv.getItemsByCategory();
        List<Item> engineGroup = null;
        for (List<Item> group : grouped) {
            if (group.get(0).item[5].trim().equalsIgnoreCase("ENGINE")) {
                engineGroup = group;
                break;
            }
        }
        assertNotNull(engineGroup);
        assertEquals("P001", engineGroup.get(0).item[0]);
    }

    @Test
    public void testGenerateItemCode() {
        assertEquals("P005", inventory.generateItemCode());
    }

    @Test
    public void testGenerateItemCodeEmptyInventory() throws IOException {
        Inventory empty = new Inventory(new ArrayList<>());
        assertEquals("P001", empty.generateItemCode());
    }
}
