package org.o7planning.thymeleaf.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptDecrypt_Util {
    public static final Logger logger = LoggerFactory.getLogger(EncryptDecrypt_Util.class);

    private final byte[] key = { -95, -29, -62, 25, 25, -83, -18, -85 };
    private final String algorithm = "DES";
    private SecretKeySpec keySpec = new SecretKeySpec(this.key, this.algorithm);
    private final int UTF_8_BufferSize = 8192;

    /**
     * Hàm khởi tạo
     *
     * @since 26/03/2014 HienDM
     */
    public EncryptDecrypt_Util() {
        this.keySpec = new SecretKeySpec(this.key, this.algorithm);
    }

    /**
     * Hàm mã hóa mảng byte
     *
     * @since 26/03/2014 HienDM
     * @param arrByte mảng byte cần mã hóa
     * @return mảng byte đã mã hóa
     */
    public byte[] encrypt(byte[] arrByte) throws NoSuchAlgorithmException, NoSuchPaddingException,
    InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(this.algorithm);
        cipher.init(1, this.keySpec);
        byte[] data = cipher.doFinal(arrByte);

        return data;
    }

    /**
     * Hàm giải mã mảng byte
     *
     * @since 26/03/2014 HienDM
     * @param arrByte mảng byte cần giải mã
     * @return mảng byte đã giải mã
     */
    public byte[] decrypt(byte[] arrByte) throws NoSuchAlgorithmException, NoSuchPaddingException,
    InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(this.algorithm);
        cipher.init(2, this.keySpec);
        return cipher.doFinal(arrByte);
    }

    /**
     * Hàm mã hóa file
     *
     * @since 26/03/2014 HienDM
     * @param originalFilePath  đường dẫn file chưa mã hóa
     * @param encryptedFilePath đường dẫn file sẽ được mã hóa
     */

    public void encryptFile(String originalFilePath, String encryptedFilePath)
            throws FileNotFoundException, IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        try (FileInputStream stream = new FileInputStream(originalFilePath);
                OutputStream out = new FileOutputStream(encryptedFilePath);) {
            int bytesRead = 0;
            byte[] buffer = new byte[this.UTF_8_BufferSize];
            while ((bytesRead = stream.read(buffer, 0, this.UTF_8_BufferSize)) != -1) {
                byte[] cloneBuffer = new byte[bytesRead];
                if (bytesRead < buffer.length) {
                    for (int i = 0; i < bytesRead; i++) {
                        cloneBuffer[i] = buffer[i];
                    }
                }
                out.write(this.encrypt(cloneBuffer));
            }
        }
    }

    /**
     * Hàm giải mã file
     *
     * @since 26/03/2014 HienDM
     * @param encryptedFilePath đường dẫn file đã mã hóa
     * @param decryptedFilePath đường dẫn file sẽ được giải mã
     */
    public void decryptFile(String encryptedFilePath, String decryptedFilePath)
            throws FileNotFoundException, IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        try (FileInputStream stream = new FileInputStream(encryptedFilePath);
                OutputStream out = new FileOutputStream(decryptedFilePath);) {
            int bytesRead = 0;
            byte[] buffer = new byte[this.UTF_8_BufferSize];

            while ((bytesRead = stream.read(buffer, 0, this.UTF_8_BufferSize)) != -1) {
                byte[] cloneBuffer = new byte[bytesRead];
                if (bytesRead < buffer.length) {
                    for (int i = 0; i < bytesRead; i++) {
                        cloneBuffer[i] = buffer[i];
                    }
                }
                out.write(this.decrypt(cloneBuffer));
            }
        }
    }

    /**
     * Hàm giải mã file
     *
     * @since 26/03/2014 HienDM
     * @param encryptedFilePath đường dẫn file đã mã hóa
     * @return chuỗi chứa nội dung đã giải mã
     */
    public String decryptFile(String encryptedFilePath) {
        StringBuilder returnValue = new StringBuilder();
        try (FileInputStream stream = new FileInputStream(encryptedFilePath);) {
            try {
                int bytesRead = 0;
                byte[] buffer = new byte[this.UTF_8_BufferSize];
                while ((bytesRead = stream.read(buffer, 0, this.UTF_8_BufferSize)) != -1) {
                    byte[] cloneBuffer = new byte[bytesRead];
                    if (bytesRead < buffer.length) {
                        for (int i = 0; i < bytesRead; i++) {
                            cloneBuffer[i] = buffer[i];
                        }
                    }
                    returnValue.append(new String(this.decrypt(cloneBuffer)));
                }
            } catch (Exception ex) {
                logger.error("Encrypt error: ", ex);
            } finally {
                stream.close();
            }
        } catch (Exception ex) {
            logger.error("Encrypt error: ", ex);
        }

        return returnValue.toString();
    }

    /**
     * Hàm giải mã file
     *
     * @since 26/03/2014 HienDM
     * @param encryptedFilePath đường dẫn file đã mã hóa
     * @return chuỗi chứa nội dung đã giải mã
     */
    public StringBuilder decryptFileToStringBuilder(String encryptedFilePath) throws IOException {
        String inputFilePath = encryptedFilePath + ".tmp";
        this.XOREncrypt(encryptedFilePath, inputFilePath);
        FileInputStream inputStream = new FileInputStream(inputFilePath);
        StringBuilder returnValue = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            StringBuilder line = new StringBuilder();
            while (!(line.append(br.readLine())).toString().equals("null")) {
                returnValue.append(line);
                returnValue.append(System.getProperty("line.separator"));
                line = new StringBuilder();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            br.close();
            inputStream.close();
        }

        File tempFile = new File(inputFilePath);
        tempFile.delete();
        String result = returnValue.substring(0, returnValue.length() - 4);
        returnValue = new StringBuilder();
        returnValue.append(result);
        return returnValue;
    }

    /**
     * Hàm giải mã file
     *
     * @since 26/03/2014 HienDM
     * @param stream stream đã mã hóa
     * @return chuỗi chứa nội dung đã giải mã
     */
    public String decryptFile(FileInputStream stream)
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        StringBuilder returnValue = new StringBuilder();
        try {
            int bytesRead = 0;
            byte[] buffer = new byte[this.UTF_8_BufferSize];

            while ((bytesRead = stream.read(buffer, 0, this.UTF_8_BufferSize)) != -1) {

                byte[] cloneBuffer = new byte[bytesRead];
                if (bytesRead < buffer.length) {
                    for (int i = 0; i < bytesRead; i++) {
                        cloneBuffer[i] = buffer[i];
                    }
                }
                returnValue.append(new String(this.decrypt(cloneBuffer)));
            }
        } finally {
            stream.close();
        }
        return returnValue.toString();
    }

    /**
     * Hàm mã hóa mật khẩu một chiều SHA-256
     *
     * @since 26/03/2014 HienDM
     * @param clearTextPassword
     * @return chuỗi mật khẩu đã mã hóa
     */
    public String encodePassword(String clearTextPassword) {
        clearTextPassword = "indus" + clearTextPassword;
        return DigestUtils.sha256Hex(clearTextPassword);
    }

    /**
     * Hàm mã hóa mật khẩu một chiều SHA-256
     *
     * @since 26/03/2014 HienDM
     * @param clearTextPassword
     * @return chuỗi mật khẩu đã mã hóa
     */
    public String encodePasswordSHA1(String clearTextPassword) {
        return DigestUtils.shaHex(clearTextPassword);
    }

    public void XOREncrypt(String inputFile, String outputFile)
            throws FileNotFoundException, IOException {
        int[] key = { 1987, 2015 };
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(inputFile), 2048);
                FileOutputStream out = new FileOutputStream(outputFile);) {
            int read = -1;
            int totalRead = 0;
            long totalSize = (new File(inputFile)).length();
            long curPercentage = -1;
            long tmpPercentage = -1;
            do {
                read = in.read();
                out.write(read ^ key[totalRead % (key.length - 1)]);
                totalRead++;
                tmpPercentage = ((100 * totalRead) / totalSize);
                if (tmpPercentage % 5 == 0 && tmpPercentage != curPercentage) {
                    curPercentage = tmpPercentage;
                }
            } while (read != -1);
        }
    }

    public void XOREncrypt(String inputFile, String keyFile, String outputFile) throws IOException {
        int[] key = this.readKey(keyFile);
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(inputFile), 2048);
                FileOutputStream out = new FileOutputStream(outputFile);) {
            int read = -1;
            int totalRead = 0;
            long totalSize = (new File(inputFile)).length();
            long curPercentage = -1;
            long tmpPercentage = -1;
            do {
                read = in.read();
                out.write(read ^ key[totalRead % (key.length - 1)]);
                totalRead++;
                tmpPercentage = ((100 * totalRead) / totalSize);
                if (tmpPercentage % 5 == 0 && tmpPercentage != curPercentage) {
                    curPercentage = tmpPercentage;
                }
            } while (read != -1);
        }
    }

    private int[] readKey(String keyFile) throws FileNotFoundException, IOException {
        /*
         * if ((new File(keyFile)).length() <= 0) { throw new Exception("key size is zero!"); }
         */
        int[] fileContents = new int[(new Long((new File(keyFile)).length())).intValue() + 1];
        try (FileInputStream in = new FileInputStream(keyFile);) {
            int totalRead = 0;
            int read = -1;
            do {
                read = in.read();
                fileContents[totalRead] = read;
                totalRead++;
            } while (read != -1);
        }
        return fileContents;
    }

    /**
     * Hàm mã hóa 2 chiều Base64
     *
     * @since 26/03/2014 HienDM
     * @param plainText chuỗi chưa mã hóa
     * @return chuỗi đã mã hóa
     */
    public String base64Encode(String plainText) {
        byte[] bytesEncoded = Base64.encodeBase64(plainText.getBytes());
        return new String(bytesEncoded);
    }

    public static String base64UrlDecode(String input) {
        String result = null;
        Base64 decoder = new Base64(true);
        byte[] decodedBytes = decoder.decode(input.getBytes());
        result = new String(decodedBytes);
        return result;
    }

    public static String base64UrlEncode(String input) {
        String result = null;
        Base64 encoder = new Base64(true);
        byte[] encodedBytes = encoder.encode(input.getBytes());
        result = new String(encodedBytes);
        return result;
    }

    /**
     * Hàm mã hóa 2 chiều Base64
     *
     * @since 26/03/2014 HienDM
     * @param encryptText chuỗi mã hóa
     * @return chuỗi đã giải hóa
     */
    public String base64Decode(String encryptText) {
        byte[] valueDecoded = Base64.decodeBase64(encryptText.getBytes());
        return new String(valueDecoded);
    }

    public static String encodeSHA256(String plainText) throws Exception {
        StringBuilder hexString = new StringBuilder();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(plainText.getBytes());
        for (byte element : hash) {
            hexString.append(Integer.toHexString(0xFF & element));
        }
        return hexString.toString();
    }

    /*
     * public static void encryptDatabaseFile(String path) throws Exception { String configFile =
     * path + "Database.properties"; String encryptFile = path + "Database.conf"; File
     * databaseConfigFile = new File(configFile); File databaseEncryptFile = new File(encryptFile);
     * if (databaseConfigFile.exists()) { databaseConfigFile.delete(); } if
     * (databaseEncryptFile.exists()) { databaseEncryptFile.delete(); }
     *
     * String databaseConfigResource = "com.handfate.industry.core.config.Database"; String
     * driverClass = ResourceBundleUtils.getOtherResource("driverClass",databaseConfigResource);
     * String jdbcURL = ResourceBundleUtils.getOtherResource("jdbcURL",databaseConfigResource);
     * String user = ResourceBundleUtils.getOtherResource("user",databaseConfigResource); String
     * password = ResourceBundleUtils.getOtherResource("password",databaseConfigResource); String
     * minPoolSize = ResourceBundleUtils.getOtherResource("minPoolSize",databaseConfigResource);
     * String acquireIncrement =
     * ResourceBundleUtils.getOtherResource("acquireIncrement",databaseConfigResource); String
     * maxPoolSize = ResourceBundleUtils.getOtherResource("maxPoolSize",databaseConfigResource);
     * String maxStatements =
     * ResourceBundleUtils.getOtherResource("maxStatements",databaseConfigResource);
     *
     * String databaseInformation = String.format("driverClass=" + driverClass + "%s" + "jdbcURL=" +
     * jdbcURL + "%s" + "user=" + user + "%s" + "password=" + password + "%s" + "minPoolSize=" +
     * minPoolSize + "%s" + "acquireIncrement=" + acquireIncrement + "%s" + "maxPoolSize=" +
     * maxPoolSize + "%s" + "maxStatements=" + maxStatements, System.getProperty("line.separator"),
     * System.getProperty("line.separator"), System.getProperty("line.separator"),
     * System.getProperty("line.separator"), System.getProperty("line.separator"),
     * System.getProperty("line.separator"), System.getProperty("line.separator"));
     *
     * FileUtils fileUtils = new FileUtils(); fileUtils.writeStringToFile(databaseInformation,
     * configFile, FileUtils.UTF_8); EncryptDecrypt_Util edutils = new EncryptDecrypt_Util();
     * edutils.encryptFile(configFile, encryptFile); }
     *
     * public static void main(String[] args) { try { encryptDatabaseFile(
     * "D:\\MyCareer\\Projects\\HF_140224_Industry\\06.SOURCE\\industry_oracle_2.0\\core-industry\\src\\main\\resources\\com\\handfate\\industry\\core\\config\\"
     * ); System.out.println("Ma hoa file thanh cong"); } catch (Exception ex) {
     * ex.printStackTrace(); } }
     */
}
