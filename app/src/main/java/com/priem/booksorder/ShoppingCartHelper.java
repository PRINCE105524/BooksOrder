package com.priem.booksorder;

import android.content.res.Resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

;

public class ShoppingCartHelper {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<Product> catalog;
    private static Map<Product, ordercartentry> cartMap = new HashMap<Product, ordercartentry>();

    public static List<Product> getCatalog(Resources res) {
        if (catalog == null) {
            catalog = new Vector<Product>();
            catalog.add(new Product("A Few Youth In the Moon", 135));

            catalog.add(new Product("The Five Major pieces to the life puzzle", 270));

            catalog.add(new Product("The Art of War", 450));

            catalog.add(new Product("Gresham's Law Syndrome And Beyond", 595));

            catalog.add(new Product("Re-Thinking Failures", 132));

            catalog.add(new Product("Aurora", 176));

            catalog.add(new Product("The Prophet", 135));

            catalog.add(new Product("Anna Karenina", 450));

            catalog.add(new Product("Of Love and other demons", 450));

            catalog.add(new Product("The White Mughal(PaperBack)", 1255));

            catalog.add(new Product("Aleph", 630));

            catalog.add(new Product("Tukunjil", 213));

            catalog.add(new Product("The Prince", 270));

            catalog.add(new Product("Ninteen- Eightyfour", 270));

            catalog.add(new Product("Animal Farm", 275));

        }

        return catalog;
    }

    public static void setQuantity(Product product, int quantity) {
        // Get the current cart entry
        ordercartentry curEntry = cartMap.get(product);

        // If the quantity is zero or less, remove the products
        if (quantity <= 0) {
            if (curEntry != null)
                removeProduct(product);
            return;
        }

        // If a current cart entry doesn't exist, create one
        if (curEntry == null) {
            curEntry = new ordercartentry(product, quantity);
            cartMap.put(product, curEntry);
            return;
        }

        // Update the quantity
        curEntry.setQuantity(quantity);
    }

    public static int getProductQuantity(Product product) {
        // Get the current cart entry
        ordercartentry curEntry = cartMap.get(product);

        if (curEntry != null)
            return curEntry.getQuantity();

        return 0;
    }

    public static void removeProduct(Product product) {
        cartMap.remove(product);
    }

    public static List<Product> getCartList() {
        List<Product> cartList = new Vector<Product>(cartMap.keySet().size());
        for (Product p : cartMap.keySet()) {
            cartList.add(p);
        }

        return cartList;
    }

}
