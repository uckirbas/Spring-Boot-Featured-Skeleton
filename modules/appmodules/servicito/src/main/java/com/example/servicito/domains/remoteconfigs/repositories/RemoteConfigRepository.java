package com.example.servicito.domains.remoteconfigs.repositories;

import com.example.servicito.domains.remoteconfigs.models.entities.RemoteConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemoteConfigRepository extends JpaRepository<RemoteConfig, Long> {
    RemoteConfig findByAppPackage(String appPackage);
}
