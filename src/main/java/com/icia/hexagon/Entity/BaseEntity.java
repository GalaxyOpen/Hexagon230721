package com.icia.hexagon.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
//얘를 상속 받는 객체가 되면

@Getter
@Setter
public class BaseEntity {
    //작성 시간과 수정 시간을 가진 Entity.
    // 별도의 테이블이 만들어지는 것이 아닌 어떤 식으로 사용할 거냐면
    //다른 엔터티에서 이 엔터티를 상속 받아서 사용.
    //
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    //작성시간

    @UpdateTimestamp
    @Column(insertable=false)
    private LocalDateTime updatedAt;
    //수정시간
}