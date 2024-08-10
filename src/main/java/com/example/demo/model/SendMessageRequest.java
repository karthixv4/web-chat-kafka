package com.example.demo.model;

public class SendMessageRequest {
    private String content;
    private Long senderId;
    private Long groupId;
    // Default constructor
    public SendMessageRequest() {}

    // Parameterized constructor
    public SendMessageRequest(String content, Long senderId) {
        this.content = content;
        this.senderId = senderId;
    }

    // Getter and Setter for 'content'
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Getter and Setter for 'senderId'
    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
