package org.tkit.onecx.hello.world.rs.internal.controllers;

import static jakarta.transaction.Transactional.TxType.NOT_SUPPORTED;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.tkit.onecx.hello.world.domain.daos.HelloDAO;
import org.tkit.onecx.hello.world.rs.internal.mappers.ExceptionMapper;
import org.tkit.onecx.hello.world.rs.internal.mappers.HelloMapper;
import org.tkit.quarkus.jpa.exceptions.ConstraintException;
import org.tkit.quarkus.log.cdi.LogService;

import gen.org.tkit.onecx.hello.world.rs.internal.HelloInternalApi;
import gen.org.tkit.onecx.hello.world.rs.internal.model.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@LogService
@ApplicationScoped
@Transactional(value = NOT_SUPPORTED)
public class HelloRestController implements HelloInternalApi {

    @Inject
    HelloDAO dao;

    @Inject
    HelloMapper mapper;

    @Inject
    ExceptionMapper exceptionMapper;

    @Override
    public Response createHello(CreateHelloRequestDTO createHelloRequestDTO) {
        var hello = mapper.create(createHelloRequestDTO.getResource());
        var created = dao.create(hello);
        return Response.status(Response.Status.CREATED).entity(mapper.mapCreated(created)).build();
    }

    @Override
    public Response deleteHello(String id) {
        dao.deleteQueryById(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Response getHelloById(String id) {
        var hello = dao.findById(id);
        if (hello == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        GetHelloByIdResponseDTO responseDTO = new GetHelloByIdResponseDTO();
        responseDTO.setResource(mapper.map(hello));
        return Response.status(Response.Status.OK).entity(responseDTO).build();
    }

    @Override
    public Response searchHellos(SearchHelloRequestDTO searchHelloRequestDTO) {
        var criteria = mapper.mapCriteria(searchHelloRequestDTO);
        var result = dao.findHelloByCriteria(criteria);
        return Response.ok(mapper.mapPage(result)).build();
    }

    @Override
    public Response updateHello(String id, UpdateHelloRequestDTO updateHelloRequestDTO) {

        var hello = dao.findById(id);
        if (hello == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        mapper.update(updateHelloRequestDTO.getResource(), hello);
        dao.update(hello);
        return Response.noContent().build();
    }

    @ServerExceptionMapper
    public RestResponse<ProblemDetailResponseDTO> exception(ConstraintException ex) {
        return exceptionMapper.exception(ex);
    }

    @ServerExceptionMapper
    public RestResponse<ProblemDetailResponseDTO> constraint(ConstraintViolationException ex) {
        return exceptionMapper.constraint(ex);
    }

    @ServerExceptionMapper
    public RestResponse<ProblemDetailResponseDTO> constraint(OptimisticLockException ex) {
        return exceptionMapper.optimisticLock(ex);
    }

}
