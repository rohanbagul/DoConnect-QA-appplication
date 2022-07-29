package com.grp10.doconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grp10.doconnect.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	public Admin findByEmail(String email);

}
