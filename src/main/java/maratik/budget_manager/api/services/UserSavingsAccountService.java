package maratik.budget_manager.api.services;

import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.exceptions.EntityNotFoundException;
import maratik.budget_manager.api.repositories.UserRepository;
import maratik.budget_manager.api.repositories.UserSavingsAccountRepository;
import maratik.budget_manager.model.dto.UserSavingsAccountDto;
import maratik.budget_manager.model.entities.UserSavingsAccount;
import maratik.budget_manager.model.mappers.UserSavingsAccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSavingsAccountService {

    private final UserSavingsAccountRepository userSavingsAccountRepository;
    private final UserSavingsAccountMapper userSavingsAccountMapper;
    private final UserRepository userRepository;

    @Transactional
    public UserSavingsAccountDto save(UserSavingsAccountDto dto, UUID userId) {
        List<UserSavingsAccount> savings = userSavingsAccountRepository.findAllByUserId(userId);
        BigDecimal portionsSum = BigDecimal.ZERO;
        for (UserSavingsAccount saving : savings) {
            portionsSum = portionsSum.add(saving.getPortion());
        }
        if (!portionsSum.equals(BigDecimal.ONE)) {
            throw new IllegalArgumentException("Sum of proportions must be 1.");
        }
        UserSavingsAccount saving = userSavingsAccountMapper.toEntity(dto);
        saving.setUser(userRepository.getReferenceById(userId));
        return userSavingsAccountMapper.toDto(
                userSavingsAccountRepository.save(
                        saving
        ));
    }

    @Transactional
    public void delete(UUID userId, UUID savingsAccountId) {
        UserSavingsAccount userSavingsAccount = userSavingsAccountRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User savings account not found"));
        userSavingsAccountRepository.delete(userSavingsAccount);
    }

    public List<UserSavingsAccountDto> findAll(UUID userId) {
        return userSavingsAccountRepository.findAllByUserId(userId).stream()
                .map(userSavingsAccountMapper::toDto)
                .toList();
    }

    public UserSavingsAccountDto findById(UUID userId, UUID savingsAccountId) {
        return userSavingsAccountMapper.toDto(
                userSavingsAccountRepository.findById(userId).orElseThrow(() ->
                        new EntityNotFoundException("User savings account not found")
        ));
    }
}
