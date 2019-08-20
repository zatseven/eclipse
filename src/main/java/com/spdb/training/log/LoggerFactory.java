package com.spdb.training.log;
/**
 * ��־����������������Ҫ��־��¼�Ķ�ʹ�ô˹�����ȡ
 * @author wanglw2
 *
 */
public class LoggerFactory {
	/**
	 * ��ȡ��־��¼�Ĺ�������
	 * @param clazz
	 * @return
	 */
	public static ILog getLogger(Class<?> clazz){
		ILog log = new SimpleLogger(clazz);
		return log;
	}
	
	
	
	public static void main(String[] a){
		String usercode = "����";
		//���κ����壬�Է�������������κΰ���
		System.out.println("------------------------------------------");
		//��־��¼Ӧʹ��ͳһ�Ľӿڣ����ܶ��ַ�������ֱ��ƴ��
		System.out.println("��ʼ�����û���["+usercode+"]��н��");
		System.out.println();
		System.out.println();
		System.out.println();
		ILog log = LoggerFactory.getLogger(LoggerFactory.class);
		
		log.debug("��ʼ�����û���[{}]��н��", usercode); //׼ȷ
		log.info("��ʼ�����û���["+usercode+"]��н��"); //����
		
		try{
			throw new NullPointerException("null");
		}catch(Exception e){
			//������Ҫʹ��ͳһ�Ľӿ�
			e.printStackTrace();
			System.out.println("--1--");
			//���󣬶�ʧ�쳣��ջ��Ϣ
			log.error(e.getMessage());
			System.out.println("--2--");
			//���󣬶�ʧ�쳣��ջ��Ϣ
			log.error("error", e.getMessage());
			System.out.println("--3--");
			//��ȷ
			log.error("error",e);
		}
		
	}
}
