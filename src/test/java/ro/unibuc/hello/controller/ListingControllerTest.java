package ro.unibuc.hello.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ro.unibuc.hello.data.ListingRepository;
import ro.unibuc.hello.data.ProductRepository;
import ro.unibuc.hello.dto.Listing;
import ro.unibuc.hello.dto.Product;
import ro.unibuc.hello.dto.User;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(ListingController.class)
@SpringBootTest
@AutoConfigureMockMvc
class ListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    void postListing() throws Exception {
        Product product = new Product("Yeezy 500","Tan",350);
        User user = new User("Alexandru", "Voiculescu");
        Listing listing = new Listing(user,650,product);

        mockMvc.perform(post("/post_listing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(listing)))
                .andExpect(status().isOk());
    }

    @Test
    void registerProduct() throws Exception {
        Product product = new Product("Yeezy 500","Tan",350);

        mockMvc.perform(post("/register_product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(product)))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllListings() throws Exception {
        mockMvc.perform(get("/listings").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].listingId", is("6225ff22b5d5fc349f9d721b")));
    }

    @Test
    public void getAllProducts() throws Exception {
        mockMvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId", is("6225fedfb5d5fc349f9d721a")));
    }



/*
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

    }

    @Test
    void getAllListings() {
        ListingController controller = new ListingController();
        List<Listing> listings = controller.getAllListings();
        when(mockListingRepository.findAll()).thenReturn(listings);

    }

 */
}