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
        Optional<BucketEntity> check = bucketRepository.findByMemberEntity_IdAndGameEntity_Id(bucketSaveDTO.getMemberId(), bucketSaveDTO.getGameId());
        if(check.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public Long save(BucketSaveDTO bucketSaveDTO) {
        MemberEntity memberEntity = memberRepository.findById(bucketSaveDTO.getMemberId()).get();
        GameEntity gameEntity = gameRepository.findById(bucketSaveDTO.getGameId()).get();
        return bucketRepository.save(BucketEntity.toBucketSaveEntity(memberEntity,gameEntity)).getId();
    }

    public List<BucketDetailDTO> findByMemberId(Long memberId) {
        List<BucketEntity> bucketEntityList = bucketRepository.findByMemberEntity_Id(memberId);
        List<BucketDetailDTO> bucketList = new ArrayList<>();
        for(BucketEntity b : bucketEntityList){
            bucketList.add(BucketDetailDTO.toBucketDetailDTO(b));
        }
        return bucketList;
    }

    public void deleteById(Long id) {
        bucketRepository.deleteById(id);
    }
}