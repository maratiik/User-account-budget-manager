package maratik.budget_manager.api.services;

import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.exceptions.EntityNotFoundException;
import maratik.budget_manager.api.repositories.IncomeRepository;
import maratik.budget_manager.model.dto.income.IncomeDto;
import maratik.budget_manager.model.dto.income.IncomeDtoWithId;
import maratik.budget_manager.model.entities.Income;
import maratik.budget_manager.model.mappers.IncomeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final IncomeMapper incomeMapper;
    private final TotalIncomeService totalIncomeService;
    private final AccountService accountService;

    public List<IncomeDto> getAllByUserId(UUID userId) {
        return incomeRepository.findAllByUserId(userId).stream()
                .map(incomeMapper::toIncomeDto)
                .toList();
    }

    public IncomeDto getByIdAndUserId(UUID id, UUID userId) {
        return incomeMapper.toIncomeDto(
                incomeRepository.findByIdAndUserId(id, userId).orElseThrow(() ->
                        new EntityNotFoundException("No income found with id: " + id))
        );
    }

    @Transactional
    public IncomeDto save(IncomeDto dto, UUID userId) {
        totalIncomeService.saveOrUpdate(null, dto.amount(), userId);

        Income income = incomeRepository.save(incomeMapper.toEntity(dto, userId));
        accountService.saveAccountIncomesFromIncomeAndUserId(income, userId);

        return incomeMapper.toIncomeDto(income);
    }

    @Transactional
    public void deleteByIdAndUserId(UUID id, UUID userId) {
        Income income = incomeRepository.findByIdAndUserId(id, userId).orElseThrow(() ->
                new EntityNotFoundException("No income found"));
        totalIncomeService.deleteAmount(null, income.getAmount(), userId);
        incomeRepository.delete(income);
    }
}
