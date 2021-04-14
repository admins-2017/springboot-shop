package com.zhike.dto;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class OrderAddressDTO {
    private String userName;
    private String province;
    private String city;
    private String county;
    private String mobile;
    private String nationalCode;
    private String postalCode;
    private String detail;
}