package com.coffee.coffee_shop.repository;

import com.coffee.coffee_shop.model.TechnicalDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicalDetailRepository extends JpaRepository<TechnicalDetail, Long> {

    List<TechnicalDetail> findByProductId(long id);
}
