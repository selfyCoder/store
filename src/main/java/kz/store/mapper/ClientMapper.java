package kz.store.mapper;

import kz.store.dto.response.ClientResponse;
import kz.store.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {
    ClientResponse toResponse(ClientEntity entity);
}
