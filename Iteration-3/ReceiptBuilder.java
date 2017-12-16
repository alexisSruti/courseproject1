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
public interface ReceiptBuilder {
    public void setHeader(int orderID, String cashier, String date);
    public void addLine(int productID, String productName, double qty, double price);
    public void setFooter(double totalCost);
    public String toString();
}
