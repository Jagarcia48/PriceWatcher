package edu.utep.cs.cs4381.pricewatcher;

public class Product {
    private String item;
    private String url;
    private double initialPrice;
    private double price;
    private double percentChange;
    private String addedDate;

    public Product(String name, String url, double price,String addedDate){
        this.item = name;
        this.url = url;
        this.price = price;
        this.addedDate = addedDate;
        this.initialPrice = price;
    }
    public void setInitialPrice(double initialPrice){
        this.initialPrice = initialPrice;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setCurrentPrice(double currentPrice) {
        this.price = currentPrice;
    }

    public void updatePrice(){
        PriceFinder priceFind = new PriceFinder();
        this.price = priceFind.getPrice(url);
    }
    public double getInitialPrice(){
        return this.initialPrice;
    }

    public double getCurrentPrice(){
        return this.price;
    }

    public String getItem(){
        return this.item;
    }

    public String getUrl(){
        return url;
    }

    public double getPercentChange(){
        this.percentChange = ((this.initialPrice - this.price) / this.initialPrice) * 100;;
        return this.percentChange;
    }



}
