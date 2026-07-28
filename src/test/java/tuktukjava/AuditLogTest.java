package tuktukjava;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
public class AuditLogTest {

    private static final String TEST_LOG = "test_audit_log.txt";

    private Item makeItem(String code) {
        String[] parts = {code,"Test Item","Brand","Rs 1000.00","10","ENGINE","2024-01-01","img.jpg","5"};
        return new Item(parts);
    }

    @BeforeEach
    public void setUp() {
        new File(TEST_LOG).delete();
    }

    @AfterEach
    public void tearDown() {
        new File(TEST_LOG).delete();
    }

    private void writeToTestLog(Item item, String msg) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_LOG, true));
        for (String detail : item.item) {
            writer.write(detail + "|");
        }
        writer.write("->" + msg + "\n");
        writer.close();
    }

    private String readLog() throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(TEST_LOG));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    @Test
    public void testAuditLogWritesItemCode() throws IOException {
        writeToTestLog(makeItem("P001"), "P001 added to the inventory");
        assertTrue(readLog().contains("P001"));
    }

    @Test
    public void testAuditLogWritesAction() throws IOException {
        writeToTestLog(makeItem("P002"), "P002 deleted");
        assertTrue(readLog().contains("P002 deleted"));
    }

    @Test
    public void testAuditLogAppends() throws IOException {
        writeToTestLog(makeItem("P001"), "P001 added to the inventory");
        writeToTestLog(makeItem("P002"), "P002 added to the inventory");
        String log = readLog();
        assertTrue(log.contains("P001"));
        assertTrue(log.contains("P002"));
    }

    @Test
    public void testAuditLogMultipleEntries() throws IOException {
        writeToTestLog(makeItem("P001"), "entry one");
        writeToTestLog(makeItem("P001"), "entry two");
        String[] lines = readLog().split("\n");
        assertEquals(2, lines.length);
    }

    @Test
    public void testAuditLogSaleEntry() throws IOException {
        writeToTestLog(makeItem("P003"), "SALE: 5 units sold");
        assertTrue(readLog().contains("SALE: 5 units sold"));
    }

    @Test
    public void testAuditLogFileCreated() throws IOException {
        assertFalse(new File(TEST_LOG).exists());
        writeToTestLog(makeItem("P001"), "test");
        assertTrue(new File(TEST_LOG).exists());
    }
}
