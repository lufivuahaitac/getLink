/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.netbit.config;

/**
 *
 * @author Truong
 */
public class SysConfig {

    public static String getFshareApi() {
        return Config.getConfig().getString("API_FSHARE_URL", "");
    }
    public static String get4shareApi() {
        return Config.getConfig().getString("API_4SHARE_URL", "");
    }

}
