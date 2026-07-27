package tuktukjava;

public class CartItem {
    private Item item;
    private int qty;
    public CartItem(Item item, int qty) {
        this.item = item;
        this.qty = qty;
    }

    public Item getItem() {
        return item;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    public double getUnitPrice() {
        String priceStr = item.item[3];
        if (priceStr == null) return 0;

        String numPrice = "";
        boolean found = false;
        for (int i = 0; i < priceStr.length(); i++) {
            char c = priceStr.charAt(i);
            if (Character.isDigit(c)) {
                numPrice = numPrice + c;
                found = true;
            } else if (c == '.' && found) {
                numPrice = numPrice + c;
            }
        }

        if (numPrice.isEmpty()) return 0;   // guard against empty result too

        try {
            return Double.parseDouble(numPrice);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    public double getSubtotal() {
        return getUnitPrice() * qty;
    }
}
