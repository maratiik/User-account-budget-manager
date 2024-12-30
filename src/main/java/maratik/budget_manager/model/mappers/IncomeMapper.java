package maratik.budget_manager.model.mappers;

import maratik.budget_manager.api.repositories.UserRepository;
import maratik.budget_manager.model.dto.IncomeDto;
import maratik.budget_manager.model.dto.SharedIncomeDto;
import maratik.budget_manager.model.entities.Income;
import maratik.budget_manager.model.entities.SharedIncome;
import maratik.budget_manager.model.entities.User;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface IncomeMapper {

    Income toEntity(IncomeDto dto);

    @Mapping(source = "sharedIncomes", target = "sharedIncomes", qualifiedByName = "mapSharedIncomes")
    IncomeDto toDto(Income entity,
                    @Context SharedIncomeMapper sharedIncomeMapper);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "sharedIncomes", ignore = true)
    @Mapping(target = "date", ignore = true)
    Income partialUpdate(IncomeDto dto, @MappingTarget Income income);

    @Named("mapSharedIncomes")
    default List<SharedIncomeDto> mapSharedIncomes(List<SharedIncome> sharedIncomes,
                                                   @Context SharedIncomeMapper sharedIncomeMapper) {
        return sharedIncomes.stream()
                .map(sharedIncomeMapper::toDto)
                .toList();
    }
}
