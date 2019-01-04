/**
 * 
 */
package com.tcl.shbc.comet.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;

import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.comet4j.core.event.ConnectEvent;
import org.comet4j.core.listener.ConnectListener;

/**
 * @author zsj
 *
 */
public class AjaxComet implements ServletContextListener {
	
	/**
	 * 频道号
	 */
	private static final String CHANNEL = "H5AppChannel";
	
	/**
	 * 用户id和connId的映射
	 */
	private static final Map<String, String> mapID = new HashMap<>();

//	Thread testModule = null;

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
//		if (testModule != null)
//			testModule.interrupt();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		CometContext cc = CometContext.getInstance();  
		cc.registChannel(CHANNEL);
		
		cc.getEngine().addConnectListener(new JoinListener());
		
//		testModule = new Thread(new TestModule(),  
//	            "Sender App Module");
//		
//		testModule.setDaemon(true);
//		testModule.start();
	}
	
	/**
	 * 推送消息接口
	 * @param uid
	 * @param message
	 * @return
	 */
	public static boolean pushMessage(String uid, String message) {
		CometEngine engine = CometContext.getInstance().getEngine();  
		
		if (!mapID.containsKey(uid))
			return false;
		
		String cid = mapID.get(uid);
		
		// 开始发送 
		engine.sendTo(CHANNEL, engine.getConnection(cid), message);
		
		return true;
	}
	
	/**
	 * 连接监听类
	 * @author zsj
	 *
	 */
	class JoinListener extends ConnectListener {

		@Override
		public boolean handleEvent(ConnectEvent arg0) {
			// TODO Auto-generated method stub
			//取得用户id，此处将来改为从session或者redis里获得
			HttpServletRequest req = arg0.getConn().getRequest();
			
			String uid = req.getParameter("uid");
			
			//取得连接id
			String cid = arg0.getConn().getId();
			
			mapID.put(uid, cid);
			
			return true;
		}


	}
	
//    class TestModule implements Runnable {  
//        public void run() {  
//            while (true) {  
//                try {  
//                    // 睡眠时间  
//                    Thread.sleep(10000);  
//                } catch (Exception ex) {  
//                    ex.printStackTrace();  
//                }  
//                // 获取消息内容  
//                String msg = "" + Runtime.getRuntime().freeMemory() + "Byte";  
//                // 开始发送  
//                AjaxComet.pushMessage("testid", msg);
//            }  
//        }  
//    }  

}
