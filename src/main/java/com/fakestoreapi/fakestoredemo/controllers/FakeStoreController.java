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

    @GetMapping("/getproducts")
    public Object getProducts(RestTemplate restTemplate) {

        return restTemplate.getForObject(FSURL, FakeStoreModel[].class);
    }

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

}
