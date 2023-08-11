package shop.mtcoding.blogv2.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/*
* 레파지토리 어노테이션 안해도 컴포넌트 스캔이 된다.
* save(), findById(), findAll(), count(), deleteById() 
* 기본 CRUD 다 만들어져 있다.(update 빼고)
*/
public interface BoardRepository extends JpaRepository<Board, Integer> {

}
