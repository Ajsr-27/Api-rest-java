package com.talentoapirest.talanto_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.talentoapirest.talanto_java.model.Producto;

public interface ProductoRespository extends JpaRepository<Producto, Integer> {
    
    
}
