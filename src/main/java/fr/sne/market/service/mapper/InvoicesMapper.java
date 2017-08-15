package fr.sne.market.service.mapper;

import fr.sne.market.domain.*;
import fr.sne.market.service.dto.InvoicesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Invoices and its DTO InvoicesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InvoicesMapper extends EntityMapper <InvoicesDTO, Invoices> {
    
    
    default Invoices fromId(Long id) {
        if (id == null) {
            return null;
        }
        Invoices invoices = new Invoices();
        invoices.setId(id);
        return invoices;
    }
}
