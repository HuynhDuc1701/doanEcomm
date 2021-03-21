package vn.nuce.ducnh.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.*;

import java.time.ZonedDateTime;

public abstract class BaseEntity {
    @JsonIgnore
    @CreatedBy
    private String createdBy;
    @JsonIgnore
    @CreatedDate
    private ZonedDateTime createdDate;
    @JsonIgnore
    @LastModifiedBy
    private String updatedBy;
    @JsonIgnore
    @LastModifiedDate
    private ZonedDateTime updatedDate;
    @JsonIgnore
    @Version
    private String version;
}
