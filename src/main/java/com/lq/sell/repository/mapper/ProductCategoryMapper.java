package com.lq.sell.repository.mapper;


import com.lq.sell.dataobject.ProductCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;

/**
 * 简单演示mybaits 注解开发
 */
public interface ProductCategoryMapper {

    @Insert("insert into product_category(category_name,category_type,create_time,update_time) values(#{categoryName},#{categoryType},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})")
    @Options(useGeneratedKeys = true,keyProperty = "categoryId",keyColumn = "category_id")
    int insert(ProductCategory productCategory);
}
