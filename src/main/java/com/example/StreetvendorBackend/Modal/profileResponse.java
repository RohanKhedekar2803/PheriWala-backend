package com.example.StreetvendorBackend.Modal;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class profileResponse {
	private boolean Success;
	private String errormessage;
	private ProfileBody profile;
}
