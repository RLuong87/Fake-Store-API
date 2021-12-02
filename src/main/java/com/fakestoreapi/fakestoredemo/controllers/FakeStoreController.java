package com.fakestoreapi.fakestoredemo.controllers;

import com.fakestoreapi.fakestoredemo.models.FakeStoreModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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

}
