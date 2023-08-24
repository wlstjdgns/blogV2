package shop.mtcoding.blogv2.user;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.FileWrite;
import shop.mtcoding.blogv2._core.vo.MyPath;
import shop.mtcoding.blogv2.user.UserRequest.JoinDTO;
import shop.mtcoding.blogv2.user.UserRequest.LoginDTO;
import shop.mtcoding.blogv2.user.UserRequest.UpdateDTO;

// 핵심로직 처리, 트랜잭션 관리, 예외 처리
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    // @Transactional
    // public void 회원가입(JoinDTO joinDTO) {

    //     UUID uuid = UUID.randomUUID();//랜덤한 해시값을 만들어준다.
    //     String fileName = uuid+"_"+joinDTO.getPic().getOriginalFilename();

    //     //프로젝트 실행 파일변경 ->  blogv2-1.0.jar
    //     //해당 실행파일 경로에 images 폴더가 피료함.......
    //     Path filePath = Paths.get(MyPath.IMG_PATH+fileName);
    //     try {
    //         Files.write(filePath, joinDTO.getPic().getBytes());
    //     } catch (Exception e) {
    //         throw new MyException(e.getMessage());
    //     }

    //     User user = User.builder()
    //             .username(joinDTO.getUsername())
    //             .password(joinDTO.getPassword())
    //             .email(joinDTO.getEmail())
    //             .picUrl(fileName)
    //             .build();
    //     userRepository.save(user); // em.persist
    // }

    @Transactional
    public void 회원가입(JoinDTO joinDTO) {
        String originalFilename = joinDTO.getPic().getOriginalFilename();
        if (originalFilename.isEmpty()) {
            originalFilename = "basic.jpg";
            
        } else {
            originalFilename = FileWrite.save(joinDTO.getPic(), originalFilename);
        }
        // 이미지 불러오기 방법
        // : 외부파일에서는 안되고 static 폴더에서 꺼내오는 건 가능
        // 하지만 static은 정적인 파일이기 때문에 넣으면 안됨

        // 외부경로를 static폴더처럼 쓸 수 있게 설정
        // /images 라는 경로가 들어오면 저 경로를 찾을 수 있게 설정

        System.out.println("ori : " + joinDTO.getPic().getOriginalFilename());

        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .email(joinDTO.getEmail())
                .picUrl(originalFilename)
                // .picUrl("./images/" + fileName)
                // 이렇게 넣으면 위험 - 사진폴더를 변경하고 싶은데 폴더 변경이 안되기 때문에
                // DB에는 파일에 이름만 저장f
                // 하드디스크에 저장하고 저장되어 있는 경로를 넣기
                .build();
        userRepository.save(user); // em.persist 영속화, 응답될 때 다 날려버림
        // persist 되는 것임
    }

    public User 로그인(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());

        // 1. 유저네임 검증
        if (user == null) {
             throw new MyException("해당 유저 네임이 없습니다."); 
        }

        // 2. 패스워드 검증
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new MyException("패스워드가 잘못 되었습니다."); 
        } //여기서 이렇게 스로우로 다 날려버리면 컨트롤러에서 예외처리해줄필요가 없어. 컨트롤러가 간단해지지

        // 3. 로그인 성공
        return user;
    }

    public User 회원정보보기(Integer id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public User 회원수정(UpdateDTO updateDTO, Integer id) {


        UUID uuid = UUID.randomUUID(); // 랜덤한 해시값을 만들어줌
        String fileName = uuid+"_"+updateDTO.getPic().getOriginalFilename();
        System.out.println("fileName : "+fileName);

        // 프로젝트 실행 파일변경 -> blogv2-1.0.jar
        // 해당 실행파일 경로에 images 폴더가 필요함
        Path filePath = Paths.get(MyPath.IMG_PATH+fileName);
        try {
            Files.write(filePath, updateDTO.getPic().getBytes());
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }

        // 1. 조회 (영속화)
        User user = userRepository.findById(id).get();

        // 2. 변경
        user.setPassword(updateDTO.getPassword());
        user.setPicUrl(fileName);

        return user;
    } // 3. flush

    public ApiUtil<String> checkUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null){
            throw new MyApiException("유저네임을 사용할 수 없습니다.");
        }
        return new ApiUtil<String>(true, "사용할 수 있습니다.");
        
    }

}
