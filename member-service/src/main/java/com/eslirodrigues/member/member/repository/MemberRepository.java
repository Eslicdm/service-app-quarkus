package com.eslirodrigues.member.member.repository;

import com.eslirodrigues.member.core.entity.Member;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MemberRepository implements PanacheRepository<Member> {

    public List<Member> findAllByManagerId(String managerId) {
        return list("managerId = ?1 order by createdAt desc", managerId);
    }

    @Override
    public Optional<Member> findByIdOptional(Long id) {
        return find("id", id).firstResultOptional();
    }

    public Optional<Member> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public boolean existsById(Long id) {
        return count("id", id) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        delete("id", id);
        return false;
    }
}