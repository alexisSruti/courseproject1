/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaremodelingproject;

/**
 *
 * @author Alexis
 */
public class TextReceiptBuilder implements ReceiptBuilder {
    StringBuilder str = new StringBuilder();
    @Override
    public void setHeader(int orderID, String cashier, String date) {
        str.append("----------------------------------\n");
        str.append("Order Number: " + orderID + "\n");
        str.append("Cashier: " + cashier + "\n");
        str.append("Date: " + date + "\n");
        str.append("ProdID\tProduct\tQty\tPrice\tTotal\n");
        str.append("----------------------------------\n");
    }

    @Override
    public void setFooter(double totalCost) {
        str.append("----------------------------------\n");
        str.append("Total: " + totalCost + "\n");
    }

    @Override
    public void addLine(int productID, String productName, double qty, double price) {
        str.append(productID); str.append('\t');
        str.append(productName); str.append('\t');
        str.append(qty); str.append('\t');
        str.append(price); str.append('\t');
        str.append(qty * price); str.append('\n');
    }

    @Override
    public String toString() {
        return str.toString();
    }
}
