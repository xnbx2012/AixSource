package cn.peterzhong.Encrypt;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@DesignerComponent(category=ComponentCategory.EXTENSION, description="An extension to encrypt/hash some String.including Rsa Encrypt/Decrypt,Aes encrypt/Decrypt,Md5 hash,SHha1 and Sha256 hash,generate Aes/Rsa keymethod.More method will be added in the future.<br>Developed by PeterZhong at <a href=\"https://peterzhong1219.gitee.io\">peterzhong1219.gitee.io</a>", version=1, iconName="E:/Zhonglang/0.jpg", nonVisible=true)
@SimpleObject(external=true)
public class Security
  extends AndroidNonvisibleComponent
  implements Component
{
  public static final int VERSION = 1;
  private String AesKey = "";
  private String RsaPrivateKey = "";
  private String RsaPublicKey = "";
  private String IV = "";
  
  public Security(ComponentContainer container)
  {
    super(container.$form());
  }
  
  @DesignerProperty(defaultValue="", editorType="string")
  @SimpleProperty(category=PropertyCategory.UNSET, description="Set AES key")
  public void AesKey(String AesKey)
  {
    this.AesKey = AesKey;
  }
  
  @SimpleProperty(category=PropertyCategory.UNSET, description="Get AES key")
  public String AesKey()
  {
    return this.AesKey;
  }
  
  @DesignerProperty(defaultValue="", editorType="string")
  @SimpleProperty(category=PropertyCategory.UNSET, description="Set RSA private key,it will be set automatically when the method \"GenerateRsaPrivateKey\" was called")
  public void RsaPrivateKey(String RsaPrivateKey)
  {
    this.RsaPrivateKey = RsaPrivateKey;
  }
  
  @SimpleProperty(category=PropertyCategory.UNSET, description="Get RSA private key,it's the same as the event variable")
  public String RsaPrivateKey()
  {
    return this.RsaPrivateKey;
  }
  
  @DesignerProperty(defaultValue="", editorType="string")
  @SimpleProperty(category=PropertyCategory.UNSET, description="Set RSA public key,it will be set automatically when the method \"GenerateRsaPublicKey\" was called")
  public void RsaPublicKey(String RsaPublicKey)
  {
    this.RsaPublicKey = RsaPublicKey;
  }
  
  @SimpleProperty(category=PropertyCategory.UNSET, description="Get RSA public key,it's the same as the event variable")
  public String RsaPublicKey()
  {
    return this.RsaPublicKey;
  }
  
  @DesignerProperty(defaultValue="", editorType="string")
  @SimpleProperty(category=PropertyCategory.UNSET, description="Set IV(initialization vector) of AES encrypt method.<a href=\"https://whatis.techtarget.com/definition/initialization-vector-IV\">More information of IV here.</a>")
  public void IV(String IV)
  {
    this.IV = IV;
  }
  
  @SimpleProperty(category=PropertyCategory.UNSET, description="Get IV(initialization vector) of AES encrypt method")
  public String IV()
  {
    return this.IV;
  }
  
  @SimpleFunction(description="BASE64 encode")
  public String BASE64Encode(String plaintext)
  {
    try
    {
      BASE64Encoder base64Encoder = new BASE64Encoder();
      return base64Encoder.encode(plaintext.getBytes("UTF-8"));
    }
    catch (Exception e)
    {
      OnErrorOccurred(e.getMessage(), "BASE64Encode");
    }
    return null;
  }
  
  @SimpleFunction(description="BASE64 decode")
  public String BASE64Decode(String ciphertext)
  {
    BASE64Decoder base64Decoder = new BASE64Decoder();
    try
    {
      byte[] decode = base64Decoder.decodeBuffer(ciphertext);
      return new String(decode);
    }
    catch (Exception e)
    {
      OnErrorOccurred(e.getMessage(), "BASE64Decode");
      return e.getMessage();
    }
  }
  
  @SimpleFunction(description="Make RSA key pair and call the event \"OnRsaKeyPairGenerated\"")
  public void GenerateRsaKeyPair(int bit)
  {
    try
    {
      String PublicKey = "";
      String PrivateKey = "";
      BASE64Encoder encoder = new BASE64Encoder();
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(bit);
      KeyPair keyPair = keyPairGenerator.generateKeyPair();
      RSAPublicKey rsaPublicKey = (RSAPublicKey)keyPair.getPublic();
      RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)keyPair.getPrivate();
      PublicKey = encoder.encode(rsaPublicKey.getEncoded());
      PrivateKey = encoder.encode(rsaPrivateKey.getEncoded());
      this.RsaPrivateKey = PrivateKey;
      this.RsaPublicKey = PublicKey;
      OnRsaKeyPairGenerated(PrivateKey, PublicKey);
    }
    catch (Exception e)
    {
      OnErrorOccurred(e.getMessage(), "GenerateRsaKeyPair");
      return;
    }
  }
  
  @SimpleFunction(description="RSA encrypt with public key")
  public String RSAEncrypt(String plaintext)
  {
    try
    {
      String publicKey = this.RsaPublicKey;
      BASE64Decoder base64Decoder = new BASE64Decoder();
      BASE64Encoder base64Encoder = new BASE64Encoder();
      byte[] decoded = base64Decoder.decodeBuffer(publicKey);
      RSAPublicKey pubKey = (RSAPublicKey)KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
      Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
      cipher.init(1, pubKey);
      return base64Encoder.encode(cipher.doFinal(plaintext.getBytes()));
    }
    catch (Exception e)
    {
      OnErrorOccurred(e.getMessage(), "RSAEncrypt");
      return e.getMessage();
    }
  }
  
  @SimpleFunction(description="RSA decrypt with private key")
  public String RSADecrypt(String ciphertext)
  {
    try
    {
      String privateKey = this.RsaPrivateKey;
      BASE64Decoder base64Decoder = new BASE64Decoder();
      byte[] inputByte = base64Decoder.decodeBuffer(ciphertext);
      byte[] decoded = base64Decoder.decodeBuffer(privateKey);
      RSAPrivateKey priKey = (RSAPrivateKey)KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
      Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
      cipher.init(2, priKey);
      return new String(cipher.doFinal(inputByte));
    }
    catch (Exception e)
    {
      OnErrorOccurred(e.getMessage(), "RSADecrypt");
      return e.getMessage();
    }
  }
  
  @SimpleFunction(description="Make AES key")
  public String GenerateAesKey(int bit)
  {
    try
    {
      BASE64Encoder base64Encoder = new BASE64Encoder();
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(bit);
      SecretKey secretKey = keyGenerator.generateKey();
      byte[] keyBytes = secretKey.getEncoded();
      this.AesKey = base64Encoder.encode(keyBytes);
      return this.AesKey;
    }
    catch (Exception e)
    {
      OnErrorOccurred(e.getMessage(), "GenerateAesKey");
      return e.getMessage();
    }
  }
  
  @SimpleFunction(description="MD5 hash")
  public String MD5hash(String content, boolean uppercase)
  {
    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      char[] charArray = content.toCharArray();
      byte[] byteArray = new byte[charArray.length];
      for (int i = 0; i < charArray.length; i++) {
        byteArray[i] = ((byte)charArray[i]);
      }
      byte[] md5Bytes = md.digest(byteArray);
      
      StringBuffer hexValue = new StringBuffer();
      for (int i = 0; i < md5Bytes.length; i++)
      {
        int val = md5Bytes[i] & 0xFF;
        if (val < 16) {
          hexValue.append("0");
        }
        hexValue.append(Integer.toHexString(val));
      }
      if (uppercase) {
        return hexValue.toString().toUpperCase();
      }
      return hexValue.toString().toLowerCase();
    }
    catch (Exception e)
    {
      OnErrorOccurred(e.getMessage(), "MD5hash");
      return e.getMessage();
    }
  }
  
  @SimpleFunction(description="Sha1 hash")
  public String Sha1(String str, boolean uppercase)
  {
    if ((str == null) || (str.length() == 0)) {
      return "Attempt to invoke the method on a null object";
    }
    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    try
    {
      MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
      mdTemp.update(str.getBytes("UTF-8"));
      
      byte[] md = mdTemp.digest();
      int j = md.length;
      char[] buf = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++)
      {
        byte byte0 = md[i];
        buf[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
        buf[(k++)] = hexDigits[(byte0 & 0xF)];
      }
      if (uppercase) {
        return new String(buf).toUpperCase();
      }
      return new String(buf);
    }
    catch (Exception e)
    {
      OnErrorOccurred(e.getMessage(), "Sha1");
      return e.getMessage();
    }
  }
  
  @SimpleFunction(description="Sha256 hash")
  public String SHA256(String str)
  {
    String encodestr = "";
    try
    {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
      messageDigest.update(str.getBytes("UTF-8"));
      StringBuffer stringBuffer = new StringBuffer();
      String temp = null;
      byte[] bytes = messageDigest.digest();
      for (int i = 0; i < bytes.length; i++)
      {
        temp = Integer.toHexString(bytes[i] & 0xFF);
        if (temp.length() == 1) {
          stringBuffer.append("0");
        }
        stringBuffer.append(temp);
      }
      return stringBuffer.toString();
    }
    catch (Exception e)
    {
      OnErrorOccurred(e.getMessage(), "SHA256");
      return e.getMessage();
    }
  }
  
  public String parseByte2HexStr(byte[] buf)
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < buf.length; i++)
    {
      String hex = Integer.toHexString(buf[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex);
    }
    return sb.toString();
  }
  
  public byte[] parseHexStr2Byte(String hexStr)
  {
    if (hexStr.length() < 1) {
      return null;
    }
    byte[] result = new byte[hexStr.length() / 2];
    for (int i = 0; i < hexStr.length() / 2; i++)
    {
      int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
      int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
      result[i] = ((byte)(high * 16 + low));
    }
    return result;
  }
  
  @SimpleFunction(description="AES encrypt")
  public String AesEncrypt(String content, String mode, String padding)
    throws Exception
  {
    try
    {
      String keyWord = this.AesKey;
      SecretKeySpec key = new SecretKeySpec(keyWord.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + padding);
      IvParameterSpec iv = new IvParameterSpec(this.IV.getBytes());
      if (mode.equals("ECB")) {
        cipher.init(1, key);
      } else {
        cipher.init(1, key, iv);
      }
      byte[] encryptedData = cipher.doFinal(content.getBytes("UTF-8"));
      return parseByte2HexStr(encryptedData);
    }
    catch (Exception e)
    {
      OnErrorOccurred(e.getMessage(), "AesEncrypt");
      return e.getMessage();
    }
  }
  
  @SimpleFunction(description="AES decrypt")
  public String AesDecrypt(String content, String mode, String padding)
    throws Exception
  {
    byte[] contentBytes = parseHexStr2Byte(content);
    try
    {
      String keyWord = this.AesKey;
      SecretKeySpec key = new SecretKeySpec(keyWord.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + padding);
      IvParameterSpec iv = new IvParameterSpec(this.IV.getBytes());
      if (mode.equals("ECB")) {
        cipher.init(2, key);
      } else {
        cipher.init(2, key, iv);
      }
      byte[] result = cipher.doFinal(contentBytes);
      return new String(result, "UTF-8");
    }
    catch (Exception e)
    {
      OnErrorOccurred(e.getMessage(), "AesDecrypt");
      return e.getMessage();
    }
  }
  
  @SimpleEvent(description="On error occurred")
  public void OnErrorOccurred(String message, String method)
  {
    EventDispatcher.dispatchEvent(this, "OnErrorOccurred", new Object[] { message, method });
  }
  
  @SimpleEvent(description="on RSA Key Pair genrated")
  public void OnRsaKeyPairGenerated(String privateKey, String publicKey)
  {
    EventDispatcher.dispatchEvent(this, "OnRsaKeyPairGenerated", new Object[] { privateKey, publicKey });
  }
}
