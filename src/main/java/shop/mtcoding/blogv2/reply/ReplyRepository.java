package shop.mtcoding.blogv2.reply;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * save(), findById(), findAll(), count(), deleteById()
 */
public interface ReplyRepository extends JpaRepository<Reply, Integer> {

}
