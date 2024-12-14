package maratik.budget_manager.api.services;

import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.exceptions.EntityNotFoundException;
import maratik.budget_manager.api.repositories.AccountIncomeRepository;
import maratik.budget_manager.model.dto.account.AccountDto;
import maratik.budget_manager.model.dto.account.AccountDtoWithId;
import maratik.budget_manager.model.entities.AccountIncome;
import maratik.budget_manager.model.mappers.AccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountIncomeRepository accountIncomeRepository;
    private final AccountMapper accountMapper;
    private final TotalIncomeService totalIncomeService;

    public List<AccountDto> getAllByUserIdAndNames(UUID userId, Collection<String> names) {
        if (names == null || names.isEmpty()) {
            return accountIncomeRepository.findAllByUserId(userId).stream()
                    .map(accountMapper::toAccountDto)
                    .toList();
        }
        return accountIncomeRepository.findAllByUserIdAndAndNameSet(userId, names).stream()
                .map(accountMapper::toAccountDto)
                .toList();
    }

    public AccountDto getByIdAndUserId(UUID incomeId, UUID userId) {
        return accountMapper.toAccountDto(
                accountIncomeRepository.findByIdAndUserId(incomeId, userId).orElseThrow(() ->
                        new EntityNotFoundException("Account not found"))
        );
    }

    @Transactional
    public AccountDto save(AccountDto dto, UUID userId) {
        totalIncomeService.saveOrUpdate(dto.name(), dto.amount(), userId);

        return accountMapper.toAccountDto(
                accountIncomeRepository.save(
                        accountMapper.toEntity(dto, userId)
                ));
    }

    @Transactional
    public AccountDto update(AccountDtoWithId dto, UUID userId) {
        AccountIncome account = accountIncomeRepository.findByIdAndUserId(dto.id(), userId).orElseThrow(() ->
                new EntityNotFoundException("Account not found"));

        if (dto.amount() != null) {
            account.setAmount(dto.amount());
            totalIncomeService.saveOrUpdate(account.getName(), dto.amount(), userId);
        }
        if (dto.proportion() != 0) {
            account.setProportion(dto.proportion());
        }

        return accountMapper.toAccountDto(
                accountIncomeRepository.save(account)
        );
    }

    @Transactional
    public void deleteByIdAndUserId(UUID id, UUID userId) {
        AccountIncome accountIncome = accountIncomeRepository.findByIdAndUserId(id, userId).orElseThrow(() ->
                new EntityNotFoundException("Account not found"));
        totalIncomeService.deleteAmount(accountIncome.getName(), accountIncome.getAmount(), userId);
        accountIncomeRepository.delete(accountIncome);
    }
}
