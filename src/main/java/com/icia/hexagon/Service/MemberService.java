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

    public boolean authenticate(String memberEmail, String memberPassword) {
        // 사용자 이메일에 해당하는 회원 정보 조회
        Optional<MemberEntity> memberEntityOptional = memberRepository.findByMemberEmail(memberEmail);

        // 사용자가 입력한 이메일과 일치하는 회원 정보가 없는 경우
        if (memberEntityOptional.isEmpty()) {
            return false;
        }

        MemberEntity memberEntity = memberEntityOptional.get();

        // 사용자가 입력한 비밀번호와 저장된 비밀번호 비교
        String hashedPassword = memberEntity.getMemberPassword();

        // BCrypt.checkpw() 메서드는 BCrypt 해시 알고리즘을 사용하여 입력한 비밀번호와 해시된 비밀번호를 비교하는 역할.
        // BCrypt 해시 알고리즘은 무작위 솔트(salt)와 함께 비밀번호를 해시화하여 저장하므로, 안전한 비밀번호 비교를 위해 사용.
        boolean passwordMatch = BCrypt.checkpw(memberPassword, hashedPassword);

        return passwordMatch;
    }

//    public void loginAxios(MemberDTO memberDTO) {
//        memberRepository.findByMemberIdAndMemberPassword(memberDTO.getMemberId(), memberDTO.getMemberPassword())
//                        .orElseThrow(()->new NoSuchElementException("이메일 또는 비밀번호가 틀립니다"));
//    }

    public MemberDTO findById(Long id) {
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(()->new NoSuchElementException());
        return MemberDTO.toDTO(memberEntity);
    }

    public MemberDTO findByMemberId(String loginId) {
        Optional<MemberEntity> memberEntityOptional = memberRepository.findByMemberId(loginId);
        MemberEntity memberEntity = memberEntityOptional.orElseThrow(() -> new UsernameNotFoundException("Member not found"));
        return MemberDTO.toDTO(memberEntity);
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
