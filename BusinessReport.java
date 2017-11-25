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
public class BusinessReport {
    String name;
    int id;
    int totalSold;
    double totalRevenue;
    
    public BusinessReport() {
        name = "null";
        id = 0;
        totalSold = 0;
        totalRevenue = 0;
    }
    public BusinessReport(String nameIn, int idIn, int totalSoldIn, double totalRevenueIn) {
        name = nameIn;
        id = idIn;
        totalSold = totalSoldIn;
        totalRevenue = totalRevenueIn;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getTotalSold() {
        return totalSold;
    }
    public void setTotalSold(int totalSold) {
        this.totalSold = totalSold;
    }
    public double getTotalRevenue() {
        return totalRevenue;
    }
    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
