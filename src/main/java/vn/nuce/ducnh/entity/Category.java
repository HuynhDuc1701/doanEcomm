package vn.nuce.ducnh.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "category",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "categoryCode")
        })
public class Category {
    @Id
    private Long id;

    @NotBlank
    private String categoryName;

    @NotBlank
    private String categoryCode;



}
