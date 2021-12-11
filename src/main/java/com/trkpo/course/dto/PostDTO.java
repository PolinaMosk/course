package com.trkpo.course.dto;
import java.util.List;

public class PostDTO {
    private Long id;
    private Long userId;
    private String text;
    private List<SpanDTO> span;
    private boolean isPrivate;
    private Long dateTime;
    private Long pictureId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public List<SpanDTO> getSpan() {
        return span;
    }

    public void setSpan(List<SpanDTO> span) {
        this.span = span;
    }
}
