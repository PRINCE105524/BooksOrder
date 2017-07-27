package com.priem.booksorder;

;

public class ordercartentry {

    private Product mProduct;
    private int mQuantity;

    public ordercartentry(Product product, int quantity) {
        mProduct = product;
        mQuantity = quantity;
    }

    public Product getProduct() {
        return mProduct;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

}
