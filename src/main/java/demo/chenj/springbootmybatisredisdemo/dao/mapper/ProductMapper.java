package demo.chenj.springbootmybatisredisdemo.dao.mapper;

import demo.chenj.springbootmybatisredisdemo.dao.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author chenj
 * @date 2019-01-13 17:21
 * @email 924943578@qq.com
 */
@Mapper
public interface ProductMapper {
    Product select(
            @Param("id")
                    long id);

    void update(Product product);

    void insert(Product product);
}