package com.icia.hexagon;

import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.Entity.MemberEntity;
import com.icia.hexagon.Repository.MemberRepository;
import com.icia.hexagon.Service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    private MemberDTO newMembers(int i) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId("test" + i);
        memberDTO.setMemberPassword("AAAaaa000!");
        memberDTO.setMemberName(i+"번 겜돌이");
        memberDTO.setMemberEmail("test"+i+"@hexagon.com");
        memberDTO.setMemberBirth("2000-01-01");
        memberDTO.setMemberMobile("010-1111-1111");
        memberDTO.setCreatedAt("2023-07-06 00:00:00");
        return memberDTO;
    }

    @Test
    @Transactional
    @DisplayName("테스트 데이터 생성")
    @Rollback(value = false)
    public void saveList() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            memberRepository.save(MemberEntity.toSaveEntity(newMembers(i)));
        });
    }

}
