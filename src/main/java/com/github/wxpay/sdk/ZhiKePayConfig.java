package com.github.wxpay.sdk;

import java.io.InputStream;

/**
 * @author Administrator
 */
public class ZhiKePayConfig extends WXPayConfig{

    @Override
    public String getAppID() {
        return "wx6e2d73d74bf11f91";
    }

    @Override
    public String getMchID() {
        return "1589111117";
    }

    @Override
    public String getKey() {
        return "yDDDsDv6kFG1qXXX6jP";
    }

    @Override
    InputStream getCertStream() {
        return null;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
