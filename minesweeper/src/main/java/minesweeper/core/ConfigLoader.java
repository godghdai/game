package minesweeper.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author godghdai
 * #Description ConfigLoader
 * #Date: 2020/10/3 13:57
 */
public class ConfigLoader {
    static char[] keys=new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    private Properties pps=null;
    public ConfigLoader() {
        pps = new Properties();
    }
    public boolean load(String filePath){
        try {
            pps.load(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public BaseConfig getBaseConfig() {
        if(pps==null) return null;
        BaseConfig baseConfig = new BaseConfig();
        baseConfig.rows = Integer.parseInt(pps.getProperty("rows"));
        baseConfig.cols = Integer.parseInt(pps.getProperty("cols"));
        baseConfig.mine = Integer.parseInt(pps.getProperty("mine"));
        try {
            baseConfig.title = pps.getProperty("title");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
        return baseConfig;
    }

    public static void showBuffer( byte[] bytes){
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            System.out.print(keys[(b >> 4) & 0x0F]);
            System.out.print(keys[b&0x0F]);
            System.out.print(" ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] bytes = new byte[]{(byte)0xE6,(byte) 0x89,(byte)0xAB,(byte)0xE9,(byte)0x9B,(byte)0xB7};
        String ss=new String(bytes,"utf8");
        showBuffer(bytes);
        System.out.println(ss);
    }
}
