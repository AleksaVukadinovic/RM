package zad3_shopping_cart;

public class ShoppingItem {
    private final String name;
    private final double price;

    public ShoppingItem(String name, double price) {
        this.name = name;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
