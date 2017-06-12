package oscar.demo.petstore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import oscar.demo.petstore.controller.PetController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PetController_Test {
    
    @Autowired
    private PetController petController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(petController).isNotNull();
    }
}
