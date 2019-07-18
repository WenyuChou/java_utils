package com.zhou.utils.dao;

import com.zhou.utils.pojo.ProductPropertyDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/6/21
 * description : 描述
 */
public interface DataSpecsMapper extends MongoRepository<ProductPropertyDTO,Long> {

    @Query(value = "{\"productId\": {\"$eq\": ?0}}")
    List<ProductPropertyDTO> findByProductId(int productId);

}
