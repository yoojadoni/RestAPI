package com.example.RestApi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) // 만약 orm.xml에 설정을 따로하면 해당 엔티티마다 리스너 적용안해도 된다고함.
@MappedSuperclass
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false, name = "created_date", insertable = true)
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_date", updatable = true, insertable = false)
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime updatedDate;

    @PrePersist
    public void setCreatedDate(){
        this.createdDate = LocalDateTime.now();
        this.updatedDate = null;
    }

    @PreUpdate
    public void setUpdatedDate(){
        this.updatedDate = LocalDateTime.now();
    }
}
