package com.example.ft.entity.rule;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RuleRepository extends CrudRepository<Rule, RulePk> {

    @Query("select r from Rule r" +
            " inner join r.application a" +
            " where a.name=?1 and a.version=?2")
    List<Rule> getAllByApplicationNameVersion(String name, String version);

    @Query("select r from Rule r" +
            " inner join r.application a" +
            " inner join r.toggle t" +
            " where a.name=?1 and a.version=?2 and t.name=?3")
    Optional<Rule> getByApplicationNameVersionAndToggleName(String name, String version, String toggleName);
}
