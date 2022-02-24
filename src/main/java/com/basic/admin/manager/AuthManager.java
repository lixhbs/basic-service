package com.basic.admin.manager;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import com.basic.admin.common.security.AuthSubject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthManager
{
    /**
     * 用户密码加密
     * <p>
     * Description
     * </p>
     *
     * @param password password
     * @return java.lang.String
     * @author Lix.
     * @date 2021-07-20-16:57
     */
    public String encryptPassword(String password)
    {
        String pwdEncrypt = SecureUtil.md5(password);
        return SmUtil.sm3(pwdEncrypt).toUpperCase();
    }


    /**
     * 密码校验
     *
     * @param password   用户输入的密码
     * @param dbPassword 数据中的密码
     * @return boolean
     * @author l.c.y
     * @date 2022/1/10 19:27
     */
    public boolean checkPassword(String password, String dbPassword)
    {
        if (password.equals(dbPassword))
        {
            return true;
        }
        String encrypt = this.encryptPassword(password);
        return StrUtil.isNotBlank(encrypt) && encrypt.equals(dbPassword);
    }
//
//    /**
//     * 用户信息查询
//     *
//     * @param userTable 用户查询条件
//     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<com.chengyu.honi.entity.AdminUser>
//     * @author l.c.y
//     * @date 2022/1/10 19:27
//     */
//    public QueryWrapper<AdminUser> userWrapper(AdminUser userTable)
//    {
//        QueryWrapper<AdminUser> userTableQueryWrapper = new QueryWrapper<>();
//        String userFlag = userTable.getUserFlag();
//        String mobilePhone = userTable.getMobilePhone();
//        String cardNo = userTable.getCardNo();
//        String nickname = userTable.getNickname();
//        if (StrUtil.isNotBlank(userFlag))
//        {
//            userTableQueryWrapper.likeRight("userflag", userFlag);
//        }
//        if (StrUtil.isNotBlank(mobilePhone))
//        {
//            userTableQueryWrapper.eq("mobilephone", mobilePhone);
//        }
//        if (StrUtil.isNotBlank(cardNo))
//        {
//            userTableQueryWrapper.eq("cardno", cardNo);
//        }
//        if (StrUtil.isNotBlank(nickname))
//        {
//            userTableQueryWrapper.eq("nickname", nickname);
//        }
//        return userTableQueryWrapper;
//    }
//
//
    /**
     * 获取当前登录人信息
     * <p>
     * Description
     * </p>
     *
     * @return com.longrise.swms.common.web.security.AuthSubject
     * @author Lix.
     * @date 2021-07-23-23:16
     */
    public AuthSubject getCurrentUserInfo()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (AuthSubject) authentication.getDetails();
    }

}
