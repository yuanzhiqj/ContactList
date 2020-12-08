package com.example.demo.Dao;
import com.example.demo.Entity.Contack;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContackRepository extends CrudRepository<Contack,Integer> {
    @Override
    List<Contack> findAll();

}
