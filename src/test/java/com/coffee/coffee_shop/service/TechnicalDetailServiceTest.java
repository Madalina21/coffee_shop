package com.coffee.coffee_shop.service;

import com.coffee.coffee_shop.exception.TechnicalDetailNotFound;
import com.coffee.coffee_shop.model.TechnicalDetail;
import com.coffee.coffee_shop.repository.TechnicalDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TechnicalDetailServiceTest {

    @Mock//taie legatura cu baza de date
    private TechnicalDetailRepository technicalDetailRepository;

    private TechnicalDetailService technicalDetailService;

    @BeforeEach
    void setUp() {
        technicalDetailService = new TechnicalDetailService(technicalDetailRepository);
    }

    @Test
    void canFindAllTechnicalDetails() {
        // given //date initiale  - creez o mini baza de date
        List<TechnicalDetail> technicalDetails = List.of(
                new TechnicalDetail(1L,
                        "TechDet 1",
                        "TechDet-1",
                        null),
                new TechnicalDetail(2L,
                        "TechDet 2",
                        "TechDet-2",
                        null));
        //ii spun lui repo ce sa faca => cand apelez findAll din repo sa returneze lista de mai sus
        given(technicalDetailRepository.findAll()).willReturn(technicalDetails);

        // when
        List actualTechnicalDetails = technicalDetailService.findAllTechnicalDetails();

        // then
        verify(technicalDetailRepository).findAll();//daca a apelat metoda
        assertThat(actualTechnicalDetails).isEqualTo(technicalDetails);//daca lista obtinuta = lista init
    }

    @Test
    void canFindTechnicalDetailById() {
        TechnicalDetail technicalDetail = new TechnicalDetail(1L,
                "TechDet 1",
                "TechDet-1",
                null);

        given(technicalDetailRepository.findById(anyLong())).willReturn(Optional.of(technicalDetail));

        //when
        TechnicalDetail actualTechnicalDetail = technicalDetailService.findByIdTechnicalDetail(anyLong());

        //then
        verify(technicalDetailRepository).findById(anyLong());
        assertThat(actualTechnicalDetail).isEqualTo(technicalDetail);
    }

    @Test
    void willThrowCannotFindTechnicalDetailById() {
        //given
        given(technicalDetailRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> technicalDetailService.findByIdTechnicalDetail(anyLong()))
                .isInstanceOf(TechnicalDetailNotFound.class)
                .hasMessageContaining("TechnicalDetail: "+ 0 + " not found.");
    }

    @Test
    void canCreateTechnicalDetail() {
        TechnicalDetail technicalDetail1 = new TechnicalDetail(1L,
                "TechDet 1",
                "TechDet-1",
                null);

        given(technicalDetailRepository.save(technicalDetail1)).willReturn(technicalDetail1);

        //when
        TechnicalDetail actualTechDet = technicalDetailService.createTechnicalDetail(technicalDetail1);

        //then
        verify(technicalDetailRepository).save(any());
        assertThat(actualTechDet).isEqualTo(technicalDetail1);
    }

    @Test
    void canUpdateTechncialDetail() {
        TechnicalDetail technicalDetail1 = new TechnicalDetail(1L,
                "TechDet 1",
                "TechDet-1",
                null);
        //fol Optional pt a evita nullpointerexception// in loc sa returnez un ob de tipul techDet care poate sa fie null
        //returnez un Optional//daca ob e null => returneaza un optional gol
        given(technicalDetailRepository.findById(anyLong())).willReturn(Optional.of(technicalDetail1));
        given(technicalDetailRepository.save(technicalDetail1)).willReturn(technicalDetail1);

        //when
        technicalDetailService.updateTechnicalDetail(1L, technicalDetail1);

        //then
        //ca sa (prind) un argument din metoda mock//sa stiu ce technicalDetail a fost salvat cu .save
        ArgumentCaptor<TechnicalDetail> technicalDetailArgumentCaptor =
                ArgumentCaptor.forClass(TechnicalDetail.class);
        //verificam daca a fost apelata metoda de save //utilizam captor ca sa obtinem instanta transmisa prin apelul metodei save
        //capturez argumentul
        verify(technicalDetailRepository).save(technicalDetailArgumentCaptor.capture());
        //extragem ce a fost capturat
        TechnicalDetail capturedTechnicalDetail = technicalDetailArgumentCaptor.getValue();
        //verific daca valoarea prinsa e egala cu techDet
        assertThat(capturedTechnicalDetail).isEqualTo(technicalDetail1);
    }

    @Test
    void willThrowTechnicalDetailNotFound() {
        TechnicalDetail technicalDetail1 = new TechnicalDetail(1L,
                "TechDet 1",
                "TechDet-1",
                null);

        given(technicalDetailRepository.findById(1L)).willReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> technicalDetailService.updateTechnicalDetail(1L, technicalDetail1))
                .isInstanceOf(TechnicalDetailNotFound.class)
                .hasMessageContaining("TechnicalDetail: "+ 1L + " not found.");
        verify(technicalDetailRepository, never()).save(technicalDetail1);
    }

    @Test
    void canDeleteTechnicalDetailById() {
        TechnicalDetail technicalDetail1 = new TechnicalDetail(1L,
                "TechDet 1",
                "TechDet-1",
                null);

        given(technicalDetailRepository.findById(1L)).willReturn(Optional.of(technicalDetail1));

        // when
        technicalDetailService.deleteByIdTechnicalDetail(1L);

        // then
        verify(technicalDetailRepository).deleteById(1L);
    }

    @Test
    void willThrowTechnicalDetailNotFoundForDelete() {
        // given
        TechnicalDetail technicalDetail1 = new TechnicalDetail(1L,
                "TechDet 1",
                "TechDet-1",
                null);

        given(technicalDetailRepository.findById(1L)).willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> technicalDetailService.deleteByIdTechnicalDetail(1L))
                .isInstanceOf(TechnicalDetailNotFound.class)
                .hasMessageContaining("TechnicalDetail: " + 1L + " not found.");

        // then
        verify(technicalDetailRepository, never()).deleteById(1L);
    }

    @Test
    void canFindTechnicalDetailsByProduct() {
        List<TechnicalDetail> technicalDetails = List.of(
                new TechnicalDetail(1L,
                        "TechDet 1",
                        "TechDet-1",
                        null),
                new TechnicalDetail(2L,
                        "TechDet 1",
                        "TechDet-1",
                        null));

        given(technicalDetailRepository.findByProductId(anyLong())).willReturn(technicalDetails);

        //when
        List<TechnicalDetail> actualTechnicalDetail = technicalDetailService.findTechnicalDetailsByProductId(anyLong());

        //then
        verify(technicalDetailRepository).findByProductId(anyLong());
        assertThat(actualTechnicalDetail).isEqualTo(technicalDetails);
    }

}


