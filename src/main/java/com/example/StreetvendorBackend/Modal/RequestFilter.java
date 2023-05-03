package com.example.StreetvendorBackend.Modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestFilter {
	private long  nearby=-1;
	private String area;
	private String shopname;
}
