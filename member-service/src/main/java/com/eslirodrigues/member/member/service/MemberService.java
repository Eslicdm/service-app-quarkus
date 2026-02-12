package com.eslirodrigues.member.member.service;

import com.eslirodrigues.member.core.entity.Member;
import com.eslirodrigues.member.core.exception.DuplicateEmailException;
import com.eslirodrigues.member.member.dto.CreateMemberRequest;
import com.eslirodrigues.member.member.dto.UpdateMemberRequest;
import com.eslirodrigues.member.member.repository.MemberRepository;
import io.quarkus.security.ForbiddenException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MemberService {

    private final MemberRepository memberRepository;

    MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembersByManagerId(String managerId) {
        return memberRepository.findAllByManagerId(managerId);
    }

    public Member getMemberById(String managerId, Long memberId) {
        Member member = memberRepository.findByIdOptional(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member with id " + memberId + " not found"));

        if (!member.managerId.equals(managerId)) {
            throw new ForbiddenException("Not authorized to access this member");
        }

        return member;
    }

    @Transactional
    public Member createMember(String managerId, CreateMemberRequest request) {
        memberRepository.findByEmail(request.email()).ifPresent(m -> {
            throw new DuplicateEmailException();
        });

        Member member = new Member();
        member.name = request.name();
        member.email = request.email();
        member.birthDate = request.birthDate();
        member.photo = request.photo();
        member.serviceType = request.serviceType();
        member.managerId = managerId;

        memberRepository.persist(member);

        return member;
    }

    @Transactional
    public Member updateMember(Long memberId, UpdateMemberRequest request) {
        Member member = memberRepository.findByIdOptional(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member with id " +
                        memberId + " not found"));

        Optional.ofNullable(request.name()).ifPresent(name -> member.name = name);
        Optional.ofNullable(request.email()).ifPresent(email -> member.email = email);
        Optional.ofNullable(request.birthDate())
                .ifPresent(birthDate -> member.birthDate = birthDate);
        Optional.ofNullable(request.photo()).ifPresent(photo -> member.photo = photo);
        Optional.ofNullable(request.serviceType())
                .ifPresent(serviceType -> member.serviceType = serviceType);

        memberRepository.persist(member);

        return member;
    }

    @Transactional
    public void deleteMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new EntityNotFoundException("Member with id " + memberId + " not found");
        }

        memberRepository.deleteById(memberId);
    }
}