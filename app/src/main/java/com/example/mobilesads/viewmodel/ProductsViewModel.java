package com.example.mobilesads.viewmodel;

import android.text.style.ReplacementSpan;
import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobilesads.model.Products;
import com.example.mobilesads.repository.Repository;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductsViewModel extends ViewModel {

    private Repository repository;

    @ViewModelInject
    public ProductsViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData <ArrayList<Products>>productsList=new MutableLiveData<>();

    public void getProducts() {
        repository.getProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> productsList.setValue(result.getProducts()),
                        error -> Log.e("ViewModel", "" + error.getMessage()));


    }

}
