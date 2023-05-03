package com.example.StreetvendorBackend.Repositrory;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.StreetvendorBackend.Entity.Product;
import com.example.StreetvendorBackend.Entity.Vendor;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{

	Set<Product> findAllByVendor(Vendor vendor);



}
