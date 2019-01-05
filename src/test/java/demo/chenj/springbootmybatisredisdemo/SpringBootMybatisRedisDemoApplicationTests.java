package demo.chenj.springbootmybatisredisdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.chenj.springbootmybatisredisdemo.dao.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootMybatisRedisDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class SpringBootMybatisRedisDemoApplicationTests {

    private static final Logger logger= LoggerFactory.getLogger(SpringBootMybatisRedisDemoApplicationTests.class);


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCache() throws URISyntaxException, JsonProcessingException {
        long productId = 1;
        ObjectMapper mapper = new ObjectMapper();

        Product product = restTemplate.getForObject("http://localhost:" + port + "/product/get/" + productId, Product.class);
        logger.info("product1:"+mapper.writeValueAsString(product));
        assertThat(product.getPrice()).isEqualTo(40);


        Product newProduct = new Product();
        long newPrice = new Random().nextLong();
        newProduct.setName("吸尘器");
        newProduct.setPrice(newPrice);
        URI uri = new URI("http://localhost:" + port + "/product/update");
        HttpEntity<Product> request = new HttpEntity<>(newProduct);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        assertThat(result.getStatusCodeValue()).isEqualTo(200);

        Product testProduct = restTemplate.getForObject("http://localhost:" + port + "/product/get/" + productId, Product.class);
        logger.info("product2:"+mapper.writeValueAsString(testProduct));


    }

}

