package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.Ingrediant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngrediantRepository extends JpaRepository<Ingrediant,Long> {
}
