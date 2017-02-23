/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.netbit.controller;

import com.google.gson.Gson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.netbit.beans.FshareResponse;
import vn.netbit.beans.RequestBean;
import vn.netbit.config.SysConfig;

/**
 *
 * @author Truong
 */
@Controller
public class GetLinkController {

    private static final Logger logger = LogManager.getLogger(GetLinkController.class);
    private static final Gson GSON = new Gson();
    private static final OkHttpClient client = new OkHttpClient();

    
    @RequestMapping(value = "/")
    public String home(HttpServletRequest request) {
        return "view";
    }

    @RequestMapping(value = "/getlink")
    public String getLink(HttpServletRequest request) {
        return "view";
    }

    @RequestMapping(value = "/getFshare", method = RequestMethod.POST)
    @ResponseBody
    public String get(HttpServletRequest request) {
        try {

            String ip = request.getRemoteAddr();
            String url = request.getParameter("url");
            if (url.isEmpty()) {
                throw new Exception("Chưa nhập link");
            }
            String password = request.getParameter("password");
            RequestBean req = RequestBean.builder()
                    .ipV4(ip.contains(":"))
                    .password(password)
                    .url(url)
                    .build();
            RequestBody body = RequestBody.create(MediaType.parse("text"), GSON.toJson(req));
            Request requestData = new Request.Builder()
                    .url(SysConfig.getFshareApi())
                    .post(body)
                    .build();

            Response response = client.newCall(requestData).execute();
            String responseBody = response.body().string();
            logger.info("Response Fshare: {}", responseBody);
            FshareResponse res = GSON.fromJson(responseBody, FshareResponse.class);
            if (isNullorEmpty(res.getUrl())) {
                res.setError(isNullorEmpty(res.getMsg()) ? res.getError() : res.getMsg());
                res.setError(isNullorEmpty(res.getDownloadForm_pwd()) ? res.getError() : res.getDownloadForm_pwd());
                return GSON.toJson(res);
            }
            return responseBody;
        } catch (Exception ex) {
            return "{\"error\":\"" + ex + "\"}";
        }

    }

    @RequestMapping(value = "/get4share", method = RequestMethod.POST)
    @ResponseBody
    public String get4share(HttpServletRequest request) {
        try {
            String ip = request.getRemoteAddr();
            String url = request.getParameter("url");
            if (isNullorEmpty(url)) {
                throw new Exception("Chưa nhập link");
            }
            String password = request.getParameter("password");
            RequestBean req = RequestBean.builder()
                    .ipV4(ip.contains(":"))
                    .password(password)
                    .url(url)
                    .build();
            RequestBody body = RequestBody.create(MediaType.parse("text"), GSON.toJson(req));
            Request requestData = new Request.Builder()
                    .url(SysConfig.get4shareApi())
                    .post(body)
                    .build();

            Response response = client.newCall(requestData).execute();
            String responseBody = response.body().string();
            logger.info("Response 4Share: {}", responseBody);
            return responseBody;
        } catch (Exception ex) {
            return "{\"error\":\"" + ex + "\"}";
        }

    }

    private static boolean isNullorEmpty(String a) {
        return (a == null || a.isEmpty());
    }

}
