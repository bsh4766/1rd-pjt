package com.ssafy.easysign.store.service;

import com.ssafy.easysign.global.auth.PrincipalDetails;
import com.ssafy.easysign.store.dto.response.ItemResponse;
import com.ssafy.easysign.store.entity.Store;
import com.ssafy.easysign.store.mapper.StoreMapper;
import com.ssafy.easysign.store.repository.StoreRepository;
import com.ssafy.easysign.user.entity.User;
import com.ssafy.easysign.user.entity.UserItem;
import com.ssafy.easysign.user.exception.NotFoundException;
import com.ssafy.easysign.user.repository.UserItemRepository;
import com.ssafy.easysign.user.repository.UserRepository;
import com.ssafy.easysign.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  UserItemRepository userItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public List<ItemResponse> getItemResponseList() {
        List<Long> except = new ArrayList<>();
        except.add(1L);
        except.add(2L);
        List<Store> stores = storeRepository.findAllByItemIdNotIn(except);
        List<ItemResponse> itemResponses = stores.stream()
                .map(storeMapper::toItemResponse)
                .collect(Collectors.toList());
        log.info("Item Responses : {}", itemResponses);
        return itemResponses;
    }

    @Override
    public ItemResponse getItemDetails(Long itemId) {
        return storeRepository.findByItemId(itemId)
                .map(storeMapper::toItemResponse)
                .orElseThrow();
    }

    @Override
    public Boolean buyItem(Long itemId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();
        int userSticker = user.getSticker();
        log.info("userSticker : " + userSticker);
        Optional<Store> storeOptional = storeRepository.findByItemId(itemId);

        if (storeOptional.isPresent()) {
            Store store = storeOptional.get();
            int requiredSticker = store.getPrice();
            log.info("requiredSticker : " + requiredSticker);
            if (userSticker >= requiredSticker) {
                UserItem userItem = new UserItem();
                userItem.setUser(user);
                userItem.setItem(store);
                userItemRepository.save(userItem);
                userService.updateStickerCountAfter(user.getUserId(),-requiredSticker);
                // 성공적으로 아이템을 구매했을 경우
                return true;
            } else {
                // 스티커 잔액이 부족한 경우
                return false;
            }
        } else {
            // 해당 itemId에 해당하는 상점이 없는 경우
            throw new RuntimeException("Store not found for itemId: " + itemId);
        }
    }


    private User getUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException("사용자를 찾을 수 없습니다.");
        }
    }
}
