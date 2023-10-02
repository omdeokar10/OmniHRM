package com.deokarkaustubh.geolocation.controller;

import com.deokarkaustubh.geolocation.config.JacksonConfig;
import com.deokarkaustubh.geolocation.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    JacksonConfig objectMapper;

    @GetMapping("")
    public ResponseEntity<String> findLocation() {

        try {
            String ipAddress = getIpAddress();
            String curlReq = "curl http://www.geoplugin.net/json.gp?ip=" + ipAddress;
            Process process = Runtime.getRuntime().exec(curlReq);
            InputStream locationRequest = process.getInputStream();
            String location = new String(locationRequest.readAllBytes());
            String[] splits = location.split("geoplugin_");
            Map<String, String> mapGeoAttributes = new HashMap<>();
            Set<String> set = new HashSet<>();
            set.addAll(List.of("city", "region", "latitude", "longitude"));
            for (String split : splits) {
                if (set.contains(split.split("\"")[0])) {
                    mapGeoAttributes.put(split.split("\"")[0], split.split("\"")[2]);
                }
            }

            Location location1 = new Location(mapGeoAttributes.get("city"),
                    mapGeoAttributes.get("region"),
                    mapGeoAttributes.get("latitude"),
                    mapGeoAttributes.get("longitude"));

            String locationString = objectMapper.objectMapper().writeValueAsString(location1);
            return new ResponseEntity<>(locationString, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("ip")
    public String getIpAddress() throws IOException {
        String command = "curl -X GET https://myexternalip.com/raw";
        Process process = Runtime.getRuntime().exec(command);
        InputStream inputStream = process.getInputStream();
        return new String(inputStream.readAllBytes());
    }


}
