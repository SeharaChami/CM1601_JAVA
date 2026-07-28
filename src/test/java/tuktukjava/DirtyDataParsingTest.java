package tuktukjava;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class DirtyDataParsingTest {

    private InventoryCleaner cleaner() {
        return new InventoryCleaner(new java.io.File("nonexistent_file.txt"), 8);
    }

    @Test
    public void testPriceFormatPlainNumber() {
        assertEquals("4500", cleaner().priceFormat("4500"));
    }

    @Test
    public void testPriceFormatWithRsPrefix() {
        assertEquals("4500.00", cleaner().priceFormat("Rs 4500.00"));
    }

    @Test
    public void testPriceFormatWithCurrency() {
        assertEquals("1250.50", cleaner().priceFormat("$1250.50"));
    }

    @Test
    public void testPriceFormatDecimalPreserved() {
        assertEquals("8500.50", cleaner().priceFormat("Rs 8500.50"));
    }

    @Test
    public void testPriceFormatNoDigits() {
        assertEquals("", cleaner().priceFormat("Rs N/A"));
    }

    @Test
    public void testDateFormat_yyyyMMdd() {
        LocalDate result = InventoryCleaner.dateFormat("2023-10-12");
        assertNotNull(result);
        assertEquals(LocalDate.of(2023, 10, 12), result);
    }

    @Test
    public void testDateFormat_ddMMyyyySlash() {
        LocalDate result = InventoryCleaner.dateFormat("12/10/2023");
        assertNotNull(result);
        assertEquals(LocalDate.of(2023, 10, 12), result);
    }

    @Test
    public void testDateFormat_ddMMyyyyDash() {
        LocalDate result = InventoryCleaner.dateFormat("12-10-2023");
        assertNotNull(result);
        assertEquals(LocalDate.of(2023, 10, 12), result);
    }

    @Test
    public void testDateFormat_monthNameFormat() {
        LocalDate result = InventoryCleaner.dateFormat("Oct 12, 2023");
        assertNotNull(result);
        assertEquals(LocalDate.of(2023, 10, 12), result);
    }

    @Test
    public void testDateFormatInvalidDate() {
        assertNull(InventoryCleaner.dateFormat("not-a-date"));
    }

    @Test
    public void testDateFormatEmptyString() {
        assertNull(InventoryCleaner.dateFormat(""));
    }

    @Test
    public void testMigration8FieldLine() {
        String line = "P001|Piston|Bajaj|Rs 4500.00|13|ENGINE|2023-10-12|piston.jpg";
        String[] parts = line.split("\\|", -1);

        if (parts.length == 8) {
            String[] migrated = new String[9];
            for (int i = 0; i < 8; i++) {
                migrated[i] = parts[i];
            }
            migrated[8] = "10";
            parts = migrated;
        }
        assertEquals(9, parts.length);
        assertEquals("10", parts[8]);
        assertEquals("P001", parts[0]);
    }

    @Test
    public void testMigration9FieldLine() {
        String line = "P001|Piston|Bajaj|Rs 4500.00|13|ENGINE|2023-10-12|piston.jpg|15";
        String[] parts = line.split("\\|", -1);

        if (parts.length == 8) {
            String[] migrated = new String[9];
            for (int i = 0; i < 8; i++) migrated[i] = parts[i];
            migrated[8] = "10";
            parts = migrated;
        }

        assertEquals(9, parts.length);
        assertEquals("15", parts[8]);
    }

    @Test
    public void testMigrationShortLine() {
        String line = "P001|Piston|Bajaj";
        String[] parts = line.split("\\|", -1);
        assertFalse(parts.length == 8 || parts.length == 9);
    }
}
