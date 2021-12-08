package com.fakestoreapi.fakestoredemo.controllers;

import com.fakestoreapi.fakestoredemo.models.FakeStoreModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Collections;

@RestController
@RequestMapping("/api/fakestore")
public class FakeStoreController {

    public static final String FSURL = "https://fakestoreapi.com/products";

    // Works
    @GetMapping("/getproducts")
    public Object getProducts(RestTemplate restTemplate) {

        return restTemplate.getForObject(FSURL, FakeStoreModel[].class);
    }

    // Works
    @GetMapping("/{id}")
    public Object getOneProduct(RestTemplate restTemplate,
                                @PathVariable String id) {
        String URL = FSURL + "/" + id;

        try {

            return restTemplate.getForObject(URL, FakeStoreModel.class);

        } catch (HttpClientErrorException.NotFound e) {

            return "ID does not exist";

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    // Not working
    @GetMapping("/someprices")
    public Object getPrice(RestTemplate restTemplate,
                           @RequestParam(name = "price") String price) {
        int newPrice = Integer.parseInt(price);

        ArrayList<FakeStoreModel> prices = new ArrayList<>();

        for (int i = 0; i < newPrice; i++) {

            if (newPrice > 20d) {
                String URL = FSURL + "/" + i;
                FakeStoreModel fakeProducts = restTemplate.getForObject(URL, FakeStoreModel.class);
                Collections.addAll(prices, fakeProducts);
            }
        }
        return prices;
    }

    // Works
    @GetMapping("/products")
    public ArrayList<FakeStoreModel> getLimit(RestTemplate restTemplate, @RequestParam(name = "limit") String limit) {

        int n = Integer.parseInt(limit);

        ArrayList<FakeStoreModel> products = new ArrayList<>();

        for (int i = 1; i <= n; i++) {

            String URL = FSURL + "/" + i;
            FakeStoreModel fakeProducts = restTemplate.getForObject(URL, FakeStoreModel.class);
            products.add(fakeProducts);
        }
        return products;
    }

    // // Not working
    @GetMapping("/{rate}")
    public ArrayList<FakeStoreModel.Rating> getRatings(RestTemplate restTemplate, @PathVariable String rate) {

        int rating = Integer.parseInt(rate);
        ArrayList<FakeStoreModel.Rating> fourAndUp = new ArrayList<>();

        FakeStoreModel.Rating fs = new FakeStoreModel.Rating();

        int fourRating = Integer.parseInt(fs.getRate());


        if (rating >= fourRating) {
            FakeStoreModel.Rating fakeProducts = restTemplate.getForObject(FSURL, FakeStoreModel.Rating.class);
            fourAndUp.add(fakeProducts);
        }

        for (FakeStoreModel.Rating fsm : fourAndUp) {

        }
        return fourAndUp;
    }

    // Works
    @GetMapping("/jewelery")
    public Object getJewelery(RestTemplate restTemplate) {

        String URL = FSURL + "/category/jewelery";

        return restTemplate.getForObject(URL, FakeStoreModel[].class);
    }

    // Not working
    @GetMapping("/mensclothing")
    public Object getMensClothing(RestTemplate restTemplate) {

        String URL = FSURL + "/category/mensclothing";

        return restTemplate.getForObject(URL, FakeStoreModel[].class);
    }

    // Works
    @GetMapping("/electronics")
    public Object getElectronics(RestTemplate restTemplate) {

        String URL = FSURL + "/category/electronics";

        return restTemplate.getForObject(URL, FakeStoreModel[].class);
    }

}
