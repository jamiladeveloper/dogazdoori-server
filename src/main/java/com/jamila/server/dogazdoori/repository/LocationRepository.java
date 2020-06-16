package com.jamila.server.dogazdoori.repository;

import com.jamila.server.dogazdoori.model.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, String> {
}
