package com.coffee.coffee_shop.repository;

import com.coffee.coffee_shop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findCartItemsByUserId(long id);

    CartItem findCartItemByUserIdAndProductId(long userId, long productId);

    //fol query deoarece nu pot sa dau save direct: depind de 2 coloane ca sa updatez cantitatea(user.id si product.id)
    @Modifying//pt ca se modifica baza de date la op. de UPDATE
    @Query("UPDATE CartItem c SET c.quantity = ?1 WHERE c.user.id = ?2 AND c.product.id = ?3")
//tabela user coloana id coresp cu al 2-lea parametru
    void updateProductQuantity(short quantity, long userId, long productId);//cand modifici cantitatea

    void deleteCartItemByUserIdAndProductId(long userId, long productId);//sterge un anumit produs pt un anumit utilizator

    void deleteCartItemsByUserId(long userId);//sterge toate prod unui anumit utilizator

}