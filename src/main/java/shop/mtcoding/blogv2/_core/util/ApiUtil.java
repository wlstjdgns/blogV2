package shop.mtcoding.blogv2._core.util;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ApiUtil<T> {//제네릭 쓸거라서 T로 잡은것이다. 제네릭-타입매개변수(재사용성높임)
    private boolean sucuess; //true
    private T data; //댓글쓰기 성공 

    public ApiUtil(boolean sucuess, T data) {
        this.sucuess = sucuess;
        this.data = data;
    }
//공통 DTO..? 

    
}
