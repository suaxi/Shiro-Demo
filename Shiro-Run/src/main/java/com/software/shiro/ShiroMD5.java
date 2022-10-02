package com.software.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author Wang Hao
 * @date 2022/10/2 22:21
 */
public class ShiroMD5 {
    public static void main(String[] args) {
        String password = "123456";
        System.out.println("md5加密：" + new Md5Hash(password));
        System.out.println("md5带盐加密：" + new Md5Hash(password, "123"));
        System.out.println("md5带盐迭代加密：" + new Md5Hash(password, "123456", 3));

        SimpleHash simpleHash = new SimpleHash("MD5", password, "123", 3);
        System.out.println("使用父类进行带盐迭代加密：" + simpleHash);
    }
}
