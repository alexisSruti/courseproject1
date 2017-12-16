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
public class HTMLReceiptBuilder implements ReceiptBuilder{
    StringBuilder str = new StringBuilder();
    @Override
    public void setHeader(int orderID, String cashier, String date) {
        str.append("<p>----------------------------------\n");
        str.append("<br>Order Number: " + orderID + "\n");
        str.append("<br>Cashier: " + cashier + "\n");
        str.append("<br>Date: " + date + "\n");
        str.append("<br>ProdID      Product     Qty    Price   Total\n");
        str.append("<br>----------------------------------\n");
    }

    @Override
    public void setFooter(double totalCost) {
        str.append("<br>----------------------------------\n");
        str.append("<br>Total: " + totalCost + "</p>\n");
    }

    @Override
    public void addLine(int productID, String productName, double qty, double price) {
        str.append("<br>");
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
