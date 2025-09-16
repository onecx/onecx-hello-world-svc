package org.tkit.onecx.hello.world.rs.internal.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.tkit.onecx.hello.world.domain.criteria.HelloSearchCriteria;
import org.tkit.onecx.hello.world.domain.models.Hello;
import org.tkit.quarkus.jpa.daos.PageResult;
import org.tkit.quarkus.rs.mappers.OffsetDateTimeMapper;

import gen.org.tkit.onecx.hello.world.rs.internal.model.*;

@Mapper(uses = { OffsetDateTimeMapper.class })
public interface HelloMapper {

    HelloDTO map(Hello hello);

    HelloSearchCriteria mapCriteria(SearchHelloRequestDTO searchHelloRequestDTO);

    @Mapping(target = "removeStreamItem", ignore = true)
    SearchHelloResponseDTO mapPage(PageResult<Hello> result);

    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "persisted", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "controlTraceabilityManual", ignore = true)
    void update(HelloDTO updateHelloRequestDTO, @MappingTarget Hello hello);
}
