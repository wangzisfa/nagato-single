package com.github.nagatosingle.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 * <p>
 * date: 2021/11/10
 *
 * @author wangzisfa
 * @version 0.31
 */
@Data
@Builder
public class NagatoUserProfile {
    public String uuid;
    public String username;
    public String realName;
//    public String password;
    public int gender;
    public String userIconURI;
    public String email;
    public String userCurrentMood;
    public String userSign;
    public long userFaceId;
    public double userCredit;
    public boolean isInspector;
    public int accessPropertyDevice;
    public int accessPropertyLog;
    public int accessPropertyUser;
    public Date userCreate;
    public Date userHireDate;
}
