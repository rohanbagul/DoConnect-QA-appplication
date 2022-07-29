package com.grp10.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grp10.chat.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
