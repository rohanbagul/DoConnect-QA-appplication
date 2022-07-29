package com.grp10.doconnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grp10.doconnect.entity.ImageModel;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel, Long> {
	public Optional<ImageModel> findByName(String name);

}
