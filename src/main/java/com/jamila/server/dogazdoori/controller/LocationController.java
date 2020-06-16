package com.jamila.server.dogazdoori.controller;

import com.jamila.server.dogazdoori.dto.LocationDTO;
import com.jamila.server.dogazdoori.dto.ResponseBody;
import com.jamila.server.dogazdoori.model.LocationEntity;
import com.jamila.server.dogazdoori.model.LocationModel;
import com.jamila.server.dogazdoori.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController {

    private Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationRepository repository;

    @GetMapping("/location/update")
    public String updateLocation(@RequestParam(name = "location") String location, @RequestParam(name = "id")  String userId){

        logger.info("User ID: " + userId + ", Location: " + location);

        // Save user location
        LocationDTO dto = new LocationDTO();
        dto.userId = userId;
        dto.latitude = Double.parseDouble(location.split(",")[0]);
        dto.longitude = Double.parseDouble(location.split(",")[1]);

        repository.save(new LocationModel().toEntity(dto));

        List<LocationEntity> allUserLocations = repository.findAll();

        String nearestDistance = getNearestDistance(allUserLocations, dto);

        return nearestDistance;
    }

    private String getNearestDistance(List<LocationEntity> allUserLocations, LocationDTO currentUserLocation) {
        Integer nearestDistance = Integer.MAX_VALUE;
        for(LocationEntity entity : allUserLocations) {
            if(entity.getId() != currentUserLocation.userId) {
                LocationDTO otherUserLocation = new LocationModel().toDTO(entity);
                Integer distance = getDistance(currentUserLocation, otherUserLocation);
                if(nearestDistance > distance) {
                    nearestDistance = distance;
                }
            }
        }
        return String.valueOf(nearestDistance);
    }

    private Integer getDistance(LocationDTO currentUserLocation, LocationDTO otherUserLocation) {

        double lon1 = Math.toRadians(currentUserLocation.longitude);
        double lon2 = Math.toRadians(otherUserLocation.longitude);
        double lat1 = Math.toRadians(currentUserLocation.latitude);
        double lat2 = Math.toRadians(otherUserLocation.latitude);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        double kms = c * r;
        double meters = kms * 1000;


        return (int) meters;
    }


}
