package com.ClientHub.api.repository;

import com.ClientHub.api.domain.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository  extends JpaRepository<Pay, Integer> {}
