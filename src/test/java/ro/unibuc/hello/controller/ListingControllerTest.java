package ro.unibuc.hello.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ro.unibuc.hello.data.ListingRepository;
import ro.unibuc.hello.data.ProductRepository;
import ro.unibuc.hello.dto.Listing;
import ro.unibuc.hello.dto.Product;
import ro.unibuc.hello.dto.User;

import java.util.List;

import static org.mockito.Mockito.when;

class ListingControllerTest {

    @Mock
    ListingRepository mockListingRepository;
    @Mock
    ProductRepository mockProductRepository;

    @Test
    void postListing() {
        User listingOwner = new User(
                "Florian",
                "Marcu"
        );
        Product listingProduct = new Product(
                "New Balance",
                "Space Grey",
                200
        );
        Listing listing = new Listing(
                listingOwner,
                200,
                listingProduct
        );
        ListingController controller = new ListingController();
        controller.postListing(listing);
        when(mockListingRepository.findListingById(listing.getListingId())).thenReturn(listing);
        Assertions.assertEquals(listing.getListingOwner(),  listingOwner);
    }

    @Test
    void bidForListing() {
        User listingOwner = new User(
                "Florian",
                "Marcu"
        );
        Product listingProduct = new Product(
                "New Balance",
                "Space Grey",
                200
        );
        Listing listing = new Listing(
                listingOwner,
                200,
                listingProduct
        );
        ListingController controller = new ListingController();
        controller.postListing(listing);
        controller.bidForListing(listing,250);
        when(mockListingRepository.findListingById(listing.getListingId())).thenReturn(listing);
        Assertions.assertEquals(listing.getCurrentPrice(),  250);
    }

    @Test
    void registerProduct() {
        Product product = new Product(
          "New Balance",
          "Space Grey",
          200
        );
        ListingController controller = new ListingController();
        controller.registerProduct(product);
        when(mockProductRepository.findProductById(product.getProductId())).thenReturn(product);
        Assertions.assertEquals(product.getModelName(),  "New Balance");
    }

    @Test
    void getAllProducts() {
        ListingController controller = new ListingController();
        List<Product> products = controller.getAllProducts();
        when(mockProductRepository.findAll()).thenReturn(products);
        // TODO: Assert
    }

    @Test
    void getAllListings() {
        ListingController controller = new ListingController();
        List<Listing> listings = controller.getAllListings();
        when(mockListingRepository.findAll()).thenReturn(listings);
        // TODO: Assert
    }
}