package com.icia.hexagon.Service;

import com.icia.hexagon.DTO.BucketDetailDTO;
import com.icia.hexagon.DTO.BucketSaveDTO;
import com.icia.hexagon.Entity.BucketEntity;
import com.icia.hexagon.Entity.GameEntity;
import com.icia.hexagon.Entity.MemberEntity;
import com.icia.hexagon.Repository.BucketRepository;
import com.icia.hexagon.Repository.GameRepository;
import com.icia.hexagon.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BucketService {
    private final BucketRepository bucketRepository;
    private final MemberRepository memberRepository;
    private final GameRepository gameRepository;

    public boolean check(BucketSaveDTO bucketSaveDTO) {
        MemberEntity memberEntity = memberRepository.findByMemberId(bucketSaveDTO.getMemberId()).orElse(null);
        GameEntity gameEntity = gameRepository.findById(bucketSaveDTO.getGameId()).orElse(null);
        Optional<BucketEntity> check = bucketRepository.findByMemberEntityAndGameEntity(memberEntity, gameEntity);
        if(check.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public Long save(BucketSaveDTO bucketSaveDTO) {
        MemberEntity memberEntity = memberRepository.findByMemberId(bucketSaveDTO.getMemberId()).get();
        GameEntity gameEntity = gameRepository.findById(bucketSaveDTO.getGameId()).get();

        memberEntity = memberRepository.save(memberEntity);
        gameEntity = gameRepository.save(gameEntity);

        return bucketRepository.save(BucketEntity.toBucketSaveEntity(memberEntity,gameEntity)).getId();
    }

    public void deleteById(Long id) {
        bucketRepository.deleteById(id);
    }


    public List<BucketDetailDTO> findByMemberId(String memberId) {
        List<BucketEntity> bucketEntityList = bucketRepository.findByMemberEntity_MemberId(memberId);
        List<BucketDetailDTO> bucketList = new ArrayList<>();
        for (BucketEntity b : bucketEntityList) {
            bucketList.add(BucketDetailDTO.toBucketDetailDTO(b));
        }
        return bucketList;
    }

    public List<BucketDetailDTO> findById(Long id) {
        System.out.println("id ="+id);
        List<BucketEntity> bucketEntityList = bucketRepository.findByMemberEntity_Id(id);
        List<BucketDetailDTO> bucketList = new ArrayList<>();
        for (BucketEntity b : bucketEntityList) {
            bucketList.add(BucketDetailDTO.toBucketDetailDTO(b));
        }
        return bucketList;
    }

}