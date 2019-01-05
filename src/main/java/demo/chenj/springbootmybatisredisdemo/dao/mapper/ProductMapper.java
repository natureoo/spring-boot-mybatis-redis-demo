package demo.chenj.springbootmybatisredisdemo.dao.mapper;

import demo.chenj.springbootmybatisredisdemo.dao.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author chenj
 * @date 2019-01-01 17:02
 * @company 广州易站通计算机科技有限公司
 * @email chenj@gzyitop.com
 */
@Mapper
public interface ProductMapper {
    Product select(
            @Param("id")
                    long id);

    void update(Product product);

    void insert(Product product);
}