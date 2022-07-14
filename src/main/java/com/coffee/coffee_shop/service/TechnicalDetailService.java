package com.coffee.coffee_shop.service;

import com.coffee.coffee_shop.exception.TechnicalDetailNotFound;
import com.coffee.coffee_shop.model.TechnicalDetail;
import com.coffee.coffee_shop.repository.TechnicalDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TechnicalDetailService {

    private final TechnicalDetailRepository technicalDetailRepository;


    public List<TechnicalDetail> findAllTechnicalDetails() {
        return technicalDetailRepository.findAll();
    }

    public TechnicalDetail findByIdTechnicalDetail(Long id) {
        return technicalDetailRepository.findById(id).orElseThrow(() -> new TechnicalDetailNotFound("TechnicalDetail: " + id + " not found."));
    }

    public TechnicalDetail createTechnicalDetail(TechnicalDetail technicalDetail) {
        return technicalDetailRepository.save(technicalDetail);
    }

    public TechnicalDetail updateTechnicalDetail(Long id, TechnicalDetail newTechnicalDetail) {
        TechnicalDetail technicalDetailToUpdate = findByIdTechnicalDetail(id);
        technicalDetailToUpdate.setName(newTechnicalDetail.getName());
        technicalDetailToUpdate.setValue(newTechnicalDetail.getValue());
        technicalDetailToUpdate.setProduct(newTechnicalDetail.getProduct());
        return technicalDetailRepository.save(technicalDetailToUpdate);
    }

    public void deleteByIdTechnicalDetail(Long id) {
        findByIdTechnicalDetail(id);
        technicalDetailRepository.deleteById(id);
    }

    public List<TechnicalDetail> findTechnicalDetailsByProductId(Long id){
        return technicalDetailRepository.findByProductId(id);
    }

}
