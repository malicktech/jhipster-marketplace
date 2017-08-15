package fr.sne.market.service.mapper;

import fr.sne.market.domain.*;
import fr.sne.market.service.dto.OperatorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Operator and its DTO OperatorDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface OperatorMapper extends EntityMapper <OperatorDTO, Operator> {

    @Mapping(source = "user.id", target = "userId")
    OperatorDTO toDto(Operator operator); 

    @Mapping(source = "userId", target = "user")
    Operator toEntity(OperatorDTO operatorDTO); 
    default Operator fromId(Long id) {
        if (id == null) {
            return null;
        }
        Operator operator = new Operator();
        operator.setId(id);
        return operator;
    }
}
