package demo.chenj.springbootmybatisredisdemo.ctrl;

import demo.chenj.springbootmybatisredisdemo.dao.domain.Product;
import demo.chenj.springbootmybatisredisdemo.dao.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenj
 * @date 2019-01-01 16:18
 * @company 广州易站通计算机科技有限公司
 * @email chenj@gzyitop.com
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    @GetMapping("/get/{id}")
    public Product getProductInfo(@PathVariable("id") Long productId) {
        // TODO
        return productMapper.select(productId);

    }

    //curl --trace-ascii /dev/stdout -XPOST -H "Content-Type: application/json" 'http://localhost:8080/product/update' -d '{"id":1,"name":"水壶","price":50}'
    @PostMapping(value = "/update")
    public Product updateProductInfo(@RequestBody Product newProduct) {
        // TODO
         productMapper.update(newProduct);
         return newProduct;
    }

    @PostMapping(value = "/insert")
    public Product insertProductInfo(@RequestBody Product newProduct) {
        // TODO
        productMapper.insert(newProduct);
        return newProduct;
    }
}