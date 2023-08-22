package shop.mtcoding.blogv2.user;

import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserJPQLRepositoryTest {

    @Autowired
    private UserJPQLRepository userJPQLRepository;


    @Test
    public void findByUsername_test() {
        User user = userJPQLRepository.findByUsername("ssar");
        System.out.println("테스트 : " + user.getEmail());
    } // rollback

    @Test
    public void findById_test() {
        Optional<User> userOP = userJPQLRepository.mFindById(3);

        if (userOP.isPresent()) {
            User user = userOP.get();
            System.out.println(user.getEmail());
        } else {
            System.out.println("해당 id를 찾을 수 없습니다");
        }
    }

}
