package com.talentoapirest.talanto_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.talentoapirest.talanto_java.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    
}
