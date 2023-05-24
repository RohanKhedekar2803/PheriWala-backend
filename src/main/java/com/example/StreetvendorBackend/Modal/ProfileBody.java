package com.example.StreetvendorBackend.Modal;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileBody {
	private String vendorusername;
	private String vendorcontact;
	private String location;
}
