package ro.unibuc.hello.dto;

import org.springframework.data.annotation.Id;

public class Listing {
    @Id
    public String listingId;

    public User listingOwner;
    public Integer startingPrice;
    public Integer currentPrice;
    public Product listedProduct;

    public Listing(){}

    public Listing(User listingOwner, Integer startingPrice, Product listedProduct) {
        this.listingOwner = listingOwner;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice;
        this.listedProduct = listedProduct;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public User getListingOwner() {
        return listingOwner;
    }

    public void setListingOwner(User listingOwner) {
        this.listingOwner = listingOwner;
    }

    public Integer getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(Integer startingPrice) {
        this.startingPrice = startingPrice;
    }

    public Product getListedProduct() {
        return listedProduct;
    }

    public void setListedProduct(Product listedProduct) {
        this.listedProduct = listedProduct;
    }

    public Integer getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Integer currentPrice) {
        this.currentPrice = currentPrice;
    }
}
