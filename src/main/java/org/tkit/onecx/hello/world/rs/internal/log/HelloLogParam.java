package org.tkit.onecx.hello.world.rs.internal.log;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import org.tkit.quarkus.log.cdi.LogParam;

import gen.org.tkit.onecx.hello.world.rs.internal.model.SearchHelloRequestDTO;
import gen.org.tkit.onecx.hello.world.rs.internal.model.UpdateHelloRequestDTO;

@ApplicationScoped
public class HelloLogParam implements LogParam {

    @Override
    public List<Item> getClasses() {
        return List.of(
                item(10, SearchHelloRequestDTO.class, x -> {
                    SearchHelloRequestDTO d = (SearchHelloRequestDTO) x;
                    return SearchHelloRequestDTO.class.getSimpleName() + "[" + d.getPageNumber() + "," + d.getPageSize() + "]";
                }),
                item(10, UpdateHelloRequestDTO.class,
                        x -> x.getClass().getSimpleName() + ":" + ((UpdateHelloRequestDTO) x).getResource().getId()));
    }
}
