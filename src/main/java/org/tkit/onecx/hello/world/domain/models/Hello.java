package org.tkit.onecx.hello.world.domain.models;

import jakarta.persistence.*;

import org.hibernate.annotations.TenantId;
import org.tkit.quarkus.jpa.models.TraceableEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "HELLO", uniqueConstraints = {
        @UniqueConstraint(name = "HELLO_NAME", columnNames = { "NAME", "TENANT_ID" })
})
@SuppressWarnings("java:S2160")
public class Hello extends TraceableEntity {

    @TenantId
    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "NAME")
    private String name;
}
