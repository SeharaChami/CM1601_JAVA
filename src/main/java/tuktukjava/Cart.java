package tuktukjava;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> cartItems = new ArrayList<>();

    public void addItem(Item item, int qty) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getItem().item[0].equals(item.item[0])) {
                cartItem.setQty(cartItem.getQty() + qty);
                return;
            }
        }
        cartItems.add(new CartItem(item, qty));
    }

    public void removeItem(String code) {
        CartItem toRemove = null;
        for (CartItem cartItem : cartItems) {
            if (cartItem.getItem().item[0].equals(code)) {
                toRemove = cartItem;
            }
        }
        if (toRemove != null) {
            cartItems.remove(toRemove);
        }
    }

    public void clear() {
        cartItems.clear();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public int getItemCount() {
        return cartItems.size();
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (CartItem cartItem : cartItems) {
            subtotal += cartItem.getSubtotal();
        }
        return subtotal;
    }

    public double getBulkDiscount() {
        double discount = 0;
        for (CartItem cartItem : cartItems) {
            if (cartItem.getQty() >= 3) {
                discount += cartItem.getSubtotal() * 0.05;
            }
        }
        return discount;
    }

    public double getAfterBulk() {
        return getSubtotal() - getBulkDiscount();
    }

    public boolean hasSynergyCombo() {
        boolean hasEngine = false;
        boolean hasElectrical = false;

        for (CartItem cartItem : cartItems) {
            String category = cartItem.getItem().item[5].trim().toUpperCase();
            if (category.equals("ENGINE")) hasEngine = true;
            if (category.equals("ELECTRICAL")) hasElectrical = true;
        }

        return hasEngine && hasElectrical;
    }

    public double getSynergyDiscount() {
        if (hasSynergyCombo()) {
            return getAfterBulk() * 0.10;
        }
        return 0;
    }

    public double getTotal() {
        return getAfterBulk() - getSynergyDiscount();
    }

    public String getDiscountReason() {
        String reason = "";

        boolean hasBulk = false;
        for (CartItem cartItem : cartItems) {
            if (cartItem.getQty() >= 3) {
                hasBulk = true;
            }
        }
        if (hasBulk) reason = "qty greater than 3 bulk -> 5%";
        if (hasSynergyCombo()) {
            if (!reason.isEmpty()) reason = reason + " + ";
            reason = reason + "Engine and Electrical synergy 10%";
        }
        return reason;
    }
    public String validateSale(Inventory inventory) {
        if (cartItems.isEmpty()) return "Cart is empty";
        for (CartItem cartItem : cartItems) {
            if (cartItem.getQty() <= 0) {
                return cartItem.getItem().item[0] + " has invalid quantity";
            }
            int stock = 0;
            try { stock = Integer.parseInt(cartItem.getItem().item[4].trim()); }
            catch (NumberFormatException ignored) {}
            if (cartItem.getQty() > stock) {
                return cartItem.getItem().item[0] + " only has " + stock + " in stock";
            }
        }
        return null;
    }

    public void reduceStock() {
        for (CartItem cartItem : cartItems) {
            int stock = Integer.parseInt(cartItem.getItem().item[4].trim());
            int newStock = stock - cartItem.getQty();
            cartItem.getItem().item[4] = String.valueOf(newStock);
        }
    }
}
