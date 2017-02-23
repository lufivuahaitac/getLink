/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.netbit.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

/**
 *
 * @author Truong
 */
@ToString
@Builder
@Getter
@Setter
public class Get4ShareResponse {
    private String url;
    private String error;
}
