package com.example.StreetvendorBackend.Repositrory;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.StreetvendorBackend.Entity.Vendor;

@Repository
public interface VendorRepository extends CrudRepository<Vendor, Long>{

	Optional<Vendor> findByVendorusernameAndPassword(String vendorUsername, String password);

	Optional<Vendor> findByVendorusername(String vendorusername);

	void save(Optional<Vendor> optionalVendor);





	
	
}
