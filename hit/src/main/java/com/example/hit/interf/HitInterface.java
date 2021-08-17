package com.example.hit.interf;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.hit.entity.HitEntity;

@Repository
public interface HitInterface extends CrudRepository <HitEntity, Integer> {

}