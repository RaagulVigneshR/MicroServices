package org.bridgelabz.quizservice.model;
import lombok.*;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private int code;
    private String message;
    private T data;
    public Response(int code,String message){
        this.code=code;
        this.message=message;
    }
}
