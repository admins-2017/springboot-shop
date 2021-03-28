package com.zhike.model;

import com.zhike.util.MapAndJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Map;

/**
 * @author Administrator
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "delete_time is null")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String openid;
    /**
     * 用户昵称
     */
    private String nickname;
    private Integer unifyUid;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码 微信登录不需要密码
     */
    private String password;
    /**
     *  手机号
     */
    private String mobile;
    /**
     * 用户微信资料
     */
    @Convert(converter = MapAndJson.class)
    private Map<String,Object> wxProfile;

}
