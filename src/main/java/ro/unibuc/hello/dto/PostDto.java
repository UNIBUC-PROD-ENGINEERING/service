package ro.unibuc.hello.dto;

import java.util.Date;

public class PostDto {
    private String userId;
    private String content;
    private String mediaUrl;
    private Date createdAt = new Date();

    public PostDto(){

    }
}
