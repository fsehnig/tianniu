package wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.tianniu.common.Constants;

public class Test {
	public static void main(String[] args) {
		/*String a = "%E6%9D%8E%E5%9B%9B";
		try {
//			a = new String(a.getBytes("ISO-8859-1"), "utf-8");
			//java.net.URLDecoder.decode("%e6%a1%8c%e9%9d%a2", "UTF-8")
			a = new URLDecoder().decode(a, "utf-8"); 
			//a = new String(a.getBytes("utf-8"), "ISO-8859-1");
			System.out.println(a);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*int count = 9;
		int per = 10;
		int re = count%per > 0 ? 1 : 0;
		System.out.println(re);*/
		for (int i = 0; i < 15; i=i+1) {
			int re =i/10 + (i%10 > 0 ? 1 : 0);
			System.out.println(i + ":" +re);
		}
	}

}
