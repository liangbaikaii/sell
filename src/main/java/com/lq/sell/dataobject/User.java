package com.lq.sell.dataobject;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "seller_info")
@Data
public class User {

    @Id
    private String id;

    @Column(name = "username")
    private String userName;

    @Column(name = "openid")
    private String openId;

    private String password;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;


}
