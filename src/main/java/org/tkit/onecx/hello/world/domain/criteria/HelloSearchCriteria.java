package org.tkit.onecx.hello.world.domain.criteria;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RegisterForReflection
public class HelloSearchCriteria {

    private String id;

    private String name;

    private Integer pageNumber;

    private Integer pageSize;

}
