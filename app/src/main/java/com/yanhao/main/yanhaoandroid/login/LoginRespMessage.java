package com.yanhao.main.yanhaoandroid.login;

import com.yanhao.main.yanhaoandroid.http.InvalidFormatException;
import com.yanhao.main.yanhaoandroid.http.NHttpResponse;

import org.json.JSONObject;

/**
 * Created by Administrator on 2015/11/16 0016.
 */
public class LoginRespMessage extends NHttpResponse{




    @Override
    public void parseJson(JSONObject jsonObject) throws InvalidFormatException {
        super.parseJson(jsonObject);
    }

    @Override
    protected void unWrapByCustom(String respContent) throws InvalidFormatException {

    }

    @Override
    public void testRespond() {

    }
}
