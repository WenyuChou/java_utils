package com.zhou.utils.service.mongodb;


import com.zhou.utils.pojo.ProductPropertyDTO;

import java.util.List;
import java.util.Map;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/6/21
 * description : 描述
 */
public interface DataSpecsService {
    int dosave(ProductPropertyDTO dto);
    List<ProductPropertyDTO> findByProductId(int productId);
    ProductPropertyDTO findById(String id);
    String removeById(String id);
    String updateById(Map<String, Object> map);
}
