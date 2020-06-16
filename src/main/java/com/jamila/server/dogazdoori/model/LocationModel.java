package com.jamila.server.dogazdoori.model;

import com.jamila.server.dogazdoori.dto.LocationDTO;

public class LocationModel implements GenericModel<LocationEntity, LocationDTO> {


    @Override
    public LocationEntity toEntity(LocationDTO locationDTO) {
        LocationEntity entity = new LocationEntity();
        entity.setId(locationDTO.userId);
        entity.setLatitude(locationDTO.latitude);
        entity.setLongitude(locationDTO.longitude);
        return entity;
    }

    @Override
    public LocationDTO toDTO(LocationEntity locationEntity) {
        LocationDTO dto = new LocationDTO();
        dto.userId = locationEntity.getId();
        dto.latitude = locationEntity.getLatitude();
        dto.longitude = locationEntity.getLongitude();
        return dto;
    }
}
