package com.icia.hexagon.Service;

import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.Entity.MemberEntity;
import com.icia.hexagon.Repository.MemberRepository;
import com.icia.hexagon.Util.UtilClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public Long save(MemberDTO memberDTO){
        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
        return memberRepository.save(memberEntity).getId();
    }

    public boolean IDCheck(String memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(memberId);
        if(optionalMemberEntity.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    public Page<MemberDTO> paging(Pageable pageable, String type, String q) {
        int page = pageable.getPageNumber()-1;
        int pageLimit =5;
        Page<MemberEntity> memberEntities = null;
        if(type.equals("memberEmail")){
            memberEntities=memberRepository.findByMemberEmailContaining(q,PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        }else if(type.equals("memberName")){
            memberEntities=memberRepository.findByMemberNameContaining(q,PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        }else{
            memberEntities=memberRepository.findAll(PageRequest.of(page,pageLimit,Sort.by(Sort.Direction.DESC,"id")));
        }
        Page<MemberDTO> memberDTOS = memberEntities.map(memberEntity -> MemberDTO.builder()
                .id(memberEntity.getId())
                .memberId(memberEntity.getMemberId())
                .memberName(memberEntity.getMemberName())
                .memberMobile(memberEntity.getMemberMobile())
                .createdAt(UtilClass.dateFormat(memberEntity.getCreatedAt()))
                .build());
        return memberDTOS;
    }

    public boolean login(MemberDTO memberDTO) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberIdAndMemberPassword(memberDTO.getMemberId(), memberDTO.getMemberPassword());
        if(memberEntity.isPresent()){
            return true;
        }else{
            return false;
        }
    }

    public void loginAxios(MemberDTO memberDTO) {
        memberRepository.findByMemberIdAndMemberPassword(memberDTO.getMemberId(), memberDTO.getMemberPassword())
                        .orElseThrow(()->new NoSuchElementException("이메일 또는 비밀번호가 틀립니다"));
    }

    public MemberDTO findById(Long id) {
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(()->new NoSuchElementException());
        return MemberDTO.toDTO(memberEntity);
    }

    public MemberDTO findByMemberId(String loginId) {
        MemberEntity memberEntity = memberRepository.findByMemberId(loginId).orElseThrow(()-> new NoSuchElementException("something wrong"));
        return MemberDTO.toDTO(memberEntity);
    }

    @Transactional
    public void update(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(memberEntity);
    }
    public void delete(Long id){
        memberRepository.deleteById(id);
    }


}
