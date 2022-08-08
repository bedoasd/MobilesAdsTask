package com.example.mobilesads.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mobilesads.model.ProductsResponse;
import com.example.mobilesads.network.ProductApiService;
import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {
    public ProductApiService newsApiService;


    @Inject
    public Repository(ProductApiService productApiService) {
        this.newsApiService = productApiService;
    }



    //Get products
    public Observable<ProductsResponse> getProducts() {
        return newsApiService.getProducts();
    }


}