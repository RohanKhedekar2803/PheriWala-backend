package com.example.StreetvendorBackend.Modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUpdateLocation {
 private String latitude;
 private String longitude;
}
