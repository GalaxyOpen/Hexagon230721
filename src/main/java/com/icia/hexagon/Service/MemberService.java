package com.icia.hexagon.Service;

import com.icia.hexagon.Config.Security.PasswordUtils;
import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.Entity.MemberEntity;
import com.icia.hexagon.Repository.MemberRepository;
import com.icia.hexagon.Util.UtilClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
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


    @Transactional
    public Page<MemberDTO> paging(Pageable pageable, String type, String q) {
        int page = pageable.getPageNumber()-1;
        int pageLimit = 10;
        Page<MemberEntity> memberEntities = null;
        if(type.equals("memberId")){
            memberEntities=memberRepository.findByMemberIdContaining(q,PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
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


    public MemberDTO findById(Long id) {
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(()->new NoSuchElementException());
        return MemberDTO.toDTO(memberEntity);
    }

    public MemberDTO findByMemberId(String loginId) {
        Optional<MemberEntity> memberEntityOptional = memberRepository.findByMemberId(loginId);
        return memberEntityOptional.map(MemberDTO::toDTO).orElse(null);
    }

    @Transactional
    public void update(MemberDTO memberDTO) {
        String encryptedPassword = PasswordUtils.encryptPassword(memberDTO.getMemberPassword());  // 비밀번호 암호화
        memberDTO.setMemberPassword(encryptedPassword);  // 암호화된 비밀번호로 설정
        memberRepository.save(MemberEntity.toUpdateEntity(memberDTO));  // MemberDTO를 MemberEntity로 변환하여 저장
    }
    public void delete(MemberDTO memberDTO){
        memberRepository.delete(MemberEntity.toUpdateEntity(memberDTO));
    }

}
