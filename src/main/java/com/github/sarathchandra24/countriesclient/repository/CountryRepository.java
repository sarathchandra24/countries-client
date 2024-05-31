package com.github.sarathchandra24.countriesclient.repository;

import com.github.sarathchandra24.countriesclient.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository  extends JpaRepository<Country, Long> {
    Optional<Country> findByCode(String code);
}
