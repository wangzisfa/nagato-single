package com.github.nagatosingle.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * <p>
 * date: 2021/11/10
 *
 * @author wangzisfa
 * @version 0.31
 */
@Data
public class NagatoUserProfile {
    public String uuid;
    public String username;
    public String realName;
    public String gender;
    public String userIconURI;
    public String email;
    public String userCurrentMood;
    public String userSign;
    public String userPhone;
    public String userFaceId;
    public double userCredit;
    public int isInspector;
    public int accessPropertyDevice;
    public int accessPropertyLog;
    public int accessPropertyUser;
    public Date userCreate;
    public Date userHireDate;
    public List<NagatoDeviceDetail> userHoldingDevices;

    public NagatoUserProfile() {
    }
}
