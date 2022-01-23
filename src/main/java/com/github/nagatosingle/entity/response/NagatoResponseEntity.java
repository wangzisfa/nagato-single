package com.github.nagatosingle.entity.response;
import com.github.nagatosingle.constants.ResponseCode;

import java.util.HashMap;

/**
 * Description:
 * <p>
 * date: 2021/10/22
 *
 * @author wangzisfa
 * @version 0.31
 */
public class NagatoResponseEntity extends HashMap<String, Object> {
    private static final long serialVersionUID = 3824281817305166528L;
    
    public NagatoResponseEntity message(String message) {
        this.put("message", message);
        return this;
    }
    
    public NagatoResponseEntity data(Object data) {
        this.put("data", data);
        return this;
    }

    public NagatoResponseEntity code(ResponseCode code) {
        this.put("statusCode", code);
        return this;
    }
    
    public String getMessage() {
        return this.get("message") != null ? String.valueOf(this.get("message")) : "";
    }
    
    public Object getData() {
        return this.get("data");
    }

    public ResponseCode getCode() {
        return (ResponseCode) this.get("statusCode");
    }
    
    @Override
    public Object put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
