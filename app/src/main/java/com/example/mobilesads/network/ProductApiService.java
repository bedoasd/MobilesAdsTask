package com.example.mobilesads.network;

import com.example.mobilesads.model.ProductsResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductApiService {

    @GET("products")
    Observable<ProductsResponse> getProducts();
}
