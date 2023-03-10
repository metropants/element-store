package xyz.metropants.element.modal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("uploads")
public class Upload {

    @Id
    private String id;

    private String name;

    @Field("content_type")
    private String contentType;

    public Upload(String name, String contentType) {
        this.name = name;
        this.contentType = contentType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }

}
