package com.ssafy.easysign.user.service;

import com.ssafy.easysign.store.entity.Store;
import com.ssafy.easysign.store.repository.StoreRepository;
import com.ssafy.easysign.user.dto.response.UserInfoResponse;
import com.ssafy.easysign.user.dto.response.UserProfileResponse;
import com.ssafy.easysign.user.entity.User;
import com.ssafy.easysign.user.entity.UserItem;
import com.ssafy.easysign.user.exception.NotFoundException;
import com.ssafy.easysign.user.repository.UserItemRepository;
import com.ssafy.easysign.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserItemRepository userItemRepository;
    private final StoreRepository storeRepository;
    @Override
    public UserInfoResponse getNavUserInfo(String loginId) {
        UserInfoResponse response = new UserInfoResponse();
        Optional<User> user = userRepository.findByLoginId(loginId);
        if(user.isEmpty()) throw new NotFoundException("사용자를 찾을 수 없습니다.");
        response.setName(user.get().getName());
        response.setSticker(user.get().getSticker());
        return response;
    }

    @Override
    public UserProfileResponse getProfileInfo(Long userId) {
        UserProfileResponse response = new UserProfileResponse();
        Optional<List<UserItem>> userProfile = userItemRepository.findByUser_UserIdAndIsUse(userId, true);
        if(userProfile.get().size()==0) throw new NotFoundException("사용자를 찾을 수 없습니다.");
        for(UserItem item : userProfile.get()) {
            Optional<Store> itemInfo = storeRepository.findByItemId(item.getItem().getItemId());
            if(itemInfo.isEmpty()) throw new NotFoundException("해당하는 아이템을 찾을 수 없습니다.");
            if(itemInfo.get().getCategoryName().toString().equals("background")) {
                response.setProfileBackgroundPath(itemInfo.get().getImagePath());
            } else {
                response.setProfileCharacterPath(itemInfo.get().getImagePath());
            }
            System.out.println(item);
        }
        return response;
    }
}
