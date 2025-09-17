package org.tkit.onecx.hello.world.domain.daos;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.criteria.Predicate;

import org.tkit.onecx.hello.world.domain.criteria.HelloSearchCriteria;
import org.tkit.onecx.hello.world.domain.models.Hello;
import org.tkit.onecx.hello.world.domain.models.Hello_;
import org.tkit.quarkus.jpa.daos.AbstractDAO;
import org.tkit.quarkus.jpa.daos.Page;
import org.tkit.quarkus.jpa.daos.PageResult;
import org.tkit.quarkus.jpa.exceptions.DAOException;
import org.tkit.quarkus.jpa.models.AbstractTraceableEntity_;
import org.tkit.quarkus.jpa.models.TraceableEntity_;
import org.tkit.quarkus.jpa.utils.QueryCriteriaUtil;

@ApplicationScoped
public class HelloDAO extends AbstractDAO<Hello> {

    public PageResult<Hello> findHelloByCriteria(HelloSearchCriteria criteria) {
        try {
            var cb = this.getEntityManager().getCriteriaBuilder();
            var cq = cb.createQuery(Hello.class);
            var root = cq.from(Hello.class);
            List<Predicate> predicates = new ArrayList<>();
            QueryCriteriaUtil.addSearchStringPredicate(predicates, cb, root.get(Hello_.NAME), criteria.getName());
            QueryCriteriaUtil.addSearchStringPredicate(predicates, cb, root.get(TraceableEntity_.ID), criteria.getId());

            if (!predicates.isEmpty()) {
                cq.where(predicates.toArray(new Predicate[] {}));
            }

            cq.orderBy(cb.desc(root.get(AbstractTraceableEntity_.CREATION_DATE)));

            return createPageQuery(cq, Page.of(criteria.getPageNumber(), criteria.getPageSize())).getPageResult();
        } catch (Exception ex) {
            throw new DAOException(ErrorKeys.ERROR_FIND_HELLO_BY_CRITERIA, ex);
        }
    }

    public enum ErrorKeys {
        ERROR_FIND_HELLO_BY_CRITERIA,
    }
}
