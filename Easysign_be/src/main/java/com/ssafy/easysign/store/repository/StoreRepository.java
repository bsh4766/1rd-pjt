package com.ssafy.easysign.store.repository;

import com.ssafy.easysign.store.entity.Store;
import com.ssafy.easysign.store.entity.StoreLike;
import com.ssafy.easysign.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByItemId(Long itemId);

    List<Store> findAllByItemIdNotIn(List<Long> itemId);
}

