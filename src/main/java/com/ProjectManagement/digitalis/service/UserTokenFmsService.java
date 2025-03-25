package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.UserTokenFmcDto;
import com.ProjectManagement.digitalis.entitie.UserTokenFmc;
import com.ProjectManagement.digitalis.repositorie.UserTokenFmcRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Aguibou sow
 * @date 2025-03-20 08:34
 * @package com.ProjectManagement.digitalis.service
 * @project digitalis
 */
@Service
@Data
@RequiredArgsConstructor
public class UserTokenFmsService {

    private final UserTokenFmcRepository userTokenFmcRepository;
    public void saveUserToken(UserTokenFmcDto userTokenFmcDto) {
        Optional<UserTokenFmc> existingToken = userTokenFmcRepository.findByUserId(userTokenFmcDto.getUserId());

        UserTokenFmc userToken = existingToken.orElse(new UserTokenFmc());
        userToken.setUserId(userTokenFmcDto.getUserId());
        userToken.setToken(userTokenFmcDto.getToken());

        userTokenFmcRepository.save(userToken);
    }

    // 🔥 Récupérer le Token d’un utilisateur
    public String getUserToken(Long userId) {
        return userTokenFmcRepository.findByUserId(userId).map(UserTokenFmc::getToken).orElse(null);
    }
}
