package com.socialize.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
//@Veto
public class Status implements Serializable {
    private Long id;

    private String username;

    private Long replyTo;
    
    private Date created;

    private String text;

    public Status() {}
    
    public Status(String username) {
        this.username = username;
        this.created = new Date();
    }
    
    public Status(String username, String text) {
        this(username);
        this.text = text;
    }
    
    @Id @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @NotNull(message = "username required")
    @Size(min = 3, max = 25, message = "invalid username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "reply_to")
    public Long getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Long replyTo) {
        this.replyTo = replyTo;
    }
    
    @Transient
    public boolean isReply() {
        return replyTo != null;
    }

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @NotNull
    @Size(min = 1, message = "status cannot be empty")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
