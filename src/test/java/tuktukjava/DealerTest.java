package tuktukjava;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class DealerTest {

    private List<Dealer> dealerList;
    private RandomDealers randomDealers;

    private Dealer makeDealer(String id, String name, String phone, String location) {
        String[] parts = {id, name, phone, location};
        return new Dealer(parts);
    }

    @BeforeEach
    public void setUp() {
        dealerList = new ArrayList<>();
        dealerList.add(makeDealer("D001","Saman Parts","0711234567","Colombo"));
        dealerList.add(makeDealer("D002","Perera Spares","0722345678","Kandy"));
        dealerList.add(makeDealer("D003","Silva Motors","0733456789","Galle"));
        dealerList.add(makeDealer("D004","Nimal Traders","0744567890","Matara"));
        dealerList.add(makeDealer("D005","Ravi Auto","0755678901","Negombo"));
        dealerList.add(makeDealer("D006","Kumara Parts","0766789012","Anuradhapura"));
        randomDealers = new RandomDealers(dealerList);
    }
    @Test
    public void testRandomDealers() {
        List<Dealer> result = randomDealers.randomDealers();
        assertEquals(4, result.size());
    }

    @Test
    public void testRandomDealersNoDuplicates() {
        List<Dealer> result = randomDealers.randomDealers();
        for (int i = 0; i < result.size(); i++) {
            for (int j = i + 1; j < result.size(); j++) {
                assertNotEquals(result.get(i).getId(), result.get(j).getId());
            }
        }
    }

    @Test
    public void testRandomDealersAllFromOriginalList() {
        List<Dealer> result = randomDealers.randomDealers();
        for (Dealer d : result) {
            boolean found = false;
            for (Dealer original : dealerList) {
                if (original.getId().equals(d.getId())) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    public void testRandomDealersFewerThan4() {
        List<Dealer> small = new ArrayList<>();
        small.add(makeDealer("D001", "A", "123", "Colombo"));
        small.add(makeDealer("D002", "B", "456", "Kandy"));
        RandomDealers rd = new RandomDealers(small);
        assertEquals(2, rd.randomDealers().size());
    }

    @Test
    public void testGetSortedByLocation() {
        List<Dealer> result = randomDealers.getSortedByLocation();
        assertEquals(4, result.size());
    }

    @Test
    public void testGetSortedByLocationIsSorted() {
        List<Dealer> result = randomDealers.getSortedByLocation();
        for (int i = 0; i < result.size() - 1; i++) {
            String loc1 = result.get(i).getLocation();
            String loc2 = result.get(i + 1).getLocation();
            assertTrue(loc1.compareTo(loc2) <= 0,
                    "Expected " + loc1 + " <= " + loc2);
        }
    }

    @Test
    public void testGetSortedByLocationNoDuplicatesAfterSort() {
        List<Dealer> result = randomDealers.getSortedByLocation();
        for (int i = 0; i < result.size(); i++) {
            for (int j = i + 1; j < result.size(); j++) {
                assertNotEquals(result.get(i).getId(), result.get(j).getId());
            }
        }
    }
}
