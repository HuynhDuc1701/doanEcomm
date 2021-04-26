package vn.nuce.ducnh.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class Products extends BaseEntity {

    @Id
    private Long id;

    private String productName;

    private String productDescription;

    private double productPrice;

    //many to many
    private Category productCategory;

    @Min(0)
    @Max(100)
    private int salePercent;

    //many to one
    private Long shopId;



}
