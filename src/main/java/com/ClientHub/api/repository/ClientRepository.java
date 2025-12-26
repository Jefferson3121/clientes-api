package com.ClientHub.api.repository;

import com.ClientHub.api.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository  extends JpaRepository<Client,Integer> {

   boolean existsByEmail(String email);
}
