package com.hwagain.eagle.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RsaUtils {
//	@Value("${sys.config.RSA.publicKey}")
	private String publicKey;
//	@Value("${sys.config.RSA.publicKey2}")
	private String publicKey2;
//	@Value("${sys.config.RSA.privateKey2}")
	private String privateKey2;
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥

    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
        genKeyPair();
        //加密字符串
//        String message = "df723820";
//        System.out.println("随机生成的公钥为:" + keyMap.get(0));
//        System.out.println("随机生成的私钥为:" + keyMap.get(1));
//        String messageEn = encryptByPublicKey(message, keyMap.get(0));
//        System.out.println(message + "\t加密后的字符串为:" + messageEn);
//        String messageDe = decryptByPrivateKey(messageEn, keyMap.get(1));
//        System.out.println("还原后的字符串为:" + messageDe);
        String   messageEn = encryptByPrivateKey("654321");
        System.out.println(messageEn);
//        System.out.println("私钥加密：" + messageEn);
//        messageDe = decryptByPublicKey(messageEn, keyMap.get(0));
//        System.out.println("公钥解密" + messageDe);
    }

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0, publicKeyString);  //0表示公钥
        keyMap.put(1, privateKeyString);  //1表示私钥
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * 加密过程中的异常信息
     */
    public static String encryptByPublicKey(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥加密
     *
     * @param str        加密字符串
     * @param privateKey 公钥
     * @return 密文
     * 加密过程中的异常信息
     */
    public static String encryptByPrivateKey(String str) throws Exception {
        //base64编码的公钥
//    	 System.err.println(publicKey);
    	String privateKey3 ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK87b1amcsebOxdeLF0YzI1Om2AJwknAU+Xl9aB+FpvQPK5SD3rj3AabWCxra7tPNYFNo7x47YjBxFVc5AeLPh5C7s+CGh0KidGqW3wyrcZyECKmxnJGXPbcknVvndeq21hULqLZi3cuSFU/emMyWvVjZpLPQlyySnnvgelutlflAgMBAAECgYEAiPeyDU7JRNBHHfrUPmiV6pXYyPaX0MHuUjKvDaXA0kWwSKmHxAy6/McL4pyMdrpxHgKryzPSpySd7ANRkbv67qwtdImBn94QsTFr7LcJ7+7Cq0wg8DlUoerm68QShQunKAxYVRDcmtgDwJowVTStgN7+yLIHZpA2XisiJB1hcHkCQQDmMSpDXdJtUaW8Jnit2B6/cAOTmuAoz7WCB3NqDVqUyZceQCS0PSjaya/z3XKCBKiKiavXJ6SFTPYAeP11OwazAkEAwuDYEw6QFJYEhRrYqnPFGW4lf2ThQP/e75z0l/VpalNXyWUTUvHBIn8h4eRd8DN9eE28iWFPhVSt3/sfXu6zBwJBAJJCgPEG5xIww5a19w22eWr32D3sNSuZNmHlLA8hZwGMySkeZnPjPFcvuU3A0eYM4a4M5ZOyi5VrHg+U0K6xD4MCQBNupj0oO5fB6ct4BNjiWGoTwKt2XLvFh+5uwTkR4KQYkW1kpcukjbSp5rAGvJNCHyXK4pz0ViCedAQcn2Ew/isCQAu8Rfx6GDrykyedanthgPLMpsaLo0QZcZ9y5vHIig16SQcETncUEHFR83lNB4+f6pWSaJFQ9M5RvNwwezqjZms="; 
    	byte[] decoded = Base64.decodeBase64(privateKey3);
       
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));

        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA公钥解密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 明文
     * 解密过程中的异常信息
     */
    public String decryptByPublicKey(String str) throws Exception {
        // 64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        // base64编码的公钥
        System.err.println(publicKey);
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        String res = new String(cipher.doFinal(inputByte));
//        System.err.println(res);
        return res;
    }
    public String decryptByPublicKey2(String str) throws Exception {
        // 64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        // base64编码的公钥
        System.err.println(publicKey2);
        byte[] decoded = Base64.decodeBase64(publicKey2);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        String res = new String(cipher.doFinal(inputByte));
//        System.err.println(res);
        return res;
    }
    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     */
    public String decryptByPrivateKey(String str) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey2);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

}
