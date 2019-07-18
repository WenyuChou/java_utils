package com.zhou.utils.service.mongodb.impl;

import com.zhou.utils.dao.DataSpecsMapper;
import com.zhou.utils.pojo.ProductPropertyDTO;
import com.zhou.utils.service.mongodb.DataSpecsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/6/21
 * description : mongodb操作 mapper ，Template两种方式皆可
 */
@Service
public class DataSpecsServiceImpl implements DataSpecsService {
    @Resource
    private DataSpecsMapper mapper;

    private MongoTemplate mongo;
    @Autowired
    public void setMongo(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public int dosave(ProductPropertyDTO dto) {
        mapper.save(dto);
        return 1;
    }

    @Override
    public List<ProductPropertyDTO> findByProductId(int productId) {
        return mapper.findByProductId(productId);
    }

    @Override
    public ProductPropertyDTO findById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongo.findOne(query, ProductPropertyDTO.class);
    }

    @Override
    public String removeById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongo.remove(query,ProductPropertyDTO.class).toString();
    }

    @Override
    public String updateById(Map<String,Object> map) {
        Query query = new Query(Criteria.where("id").is(String.valueOf(map.get("id"))));
        Update update = new Update();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(!"id".equals(entry.getKey())){
                update.set(entry.getKey(),entry.getValue());
            }
        }
        return mongo.updateFirst(query,update,ProductPropertyDTO.class).toString();
    }
}
