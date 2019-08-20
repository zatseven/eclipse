package com.spdb.training.socket.xml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;

/**
 * @author 作者: 王腾蛟
 * @version time：2019年6月19日 上午10:41:28 类说明:
 */
public class ReflectParseUtils {
	
	@SuppressWarnings("unused")
	private final static ILog LOG = LoggerFactory.getLogger(ReflectParseUtils.class);
	/**
	 * xml --> object
	 * @param xml
	 * @param class1
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T XmlToJava(String xml, Class<T> class1) throws Exception {

		JAXBContext newInstance = JAXBContext.newInstance(class1);
		Unmarshaller unmarshaller = newInstance.createUnmarshaller();
		return (T) unmarshaller.unmarshal(new StringReader(xml));
	}
	/**
	 * Object对象 --> String类型的xml
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	public static String objectToXml(Object object) throws Exception {

		StringWriter stringWriter = new StringWriter();
		JAXBContext newInstance = JAXBContext.newInstance(object.getClass());
		Marshaller marshaller = newInstance.createMarshaller();
		// 格式化xml格式
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
		// objec --> stream xml
		marshaller.marshal(object, stringWriter);
		return stringWriter.toString().replace("standalone=\"yes\"", "").replace("requestBean", "RequestBean");
	}
	
	
	/**
	 * string类型的xml --> Map对象
	 * @throws Exception 
	 */
	public static Map<String, Object> convertXmlToMap(String msgString) throws Exception {
		// xml类型转换
		DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = newDocumentBuilder.parse(new ByteArrayInputStream(msgString.getBytes("utf8")));
		NodeList childNodes = document.getChildNodes();
		Map<String, Object> listNodesMap = listNodes(childNodes);
		return listNodesMap;
	}
	/**
	 * 遍历map节点
	 * @param childNodes
	 * @return
	 */
	private static Map<String, Object> listNodes(NodeList childNodes) {

		HashMap<String, Object> hashMap = new HashMap<>();
		int length = childNodes.getLength();
		for (int i = 0; i < length; i++) {
			Node item = childNodes.item(i);
			if(!item.hasChildNodes())
			{
				return hashMap;
			}
			if (item.hasChildNodes() && item.getChildNodes().item(0).getNodeType() != Element.TEXT_NODE) {
				hashMap.put(item.getNodeName(), listNodes(item.getChildNodes()));
			} else {
				hashMap.put(item.getNodeName(), item.getChildNodes().item(0).getTextContent());
			}
		}
		return hashMap;
	}


	/**
	 * .xml文件 --> String
	 * @param xmlPathname
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "resource" })
	public static String xmlToString(String xmlPathname) throws Exception {
		String str = null;
		StringBuffer sb = new StringBuffer();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(xmlPathname)));
		while ((str = bufferedReader.readLine()) != null) {
			sb.append(str.trim());
		}
		return sb.toString();
	}
	
	/**
	 * javabean --> map
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> convertObjectToMap(Object object) throws Exception {
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		Field[] declaredFields = object.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			if (field.getType() == String.class || field.getType() == Integer.class || field.getType() == BigDecimal.class) {
				hashMap.put(field.getName(), field.get(object));
			} else {
				hashMap.put(field.getName(),convertObjectToMap(field.get(object)));
			}				
		}
		return hashMap;
	}
	/**
	 * map --> javabean
	 * @param map
	 * @param class1
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Object mapToObject(Map<String, Object> map, Class<?> class1) throws Exception{
		Object newInstance = class1.newInstance();
		Field[] declaredFields = newInstance.getClass().getDeclaredFields();
		for (int i=0; i<declaredFields.length; i++) {
			Field field = declaredFields[i];
			int modifiers = field.getModifiers();
			if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
				continue;
			}
			field.setAccessible(true);
			if (map.get(field.getName()) instanceof HashMap) {
				field.set(newInstance, mapToObject((Map<String, Object>)map.get(field.getName()), field.getType()));
			} else {
				if (field.getType() == Integer.class) 
					field.set(newInstance, Integer.valueOf((String) map.get(field.getName()))); // 要用包装类型Integer 
				if (field.getType() == BigDecimal.class) 
					field.set(newInstance, new BigDecimal((String) map.get(field.getName())));
				if (field.getType() == String.class) 
					field.set(newInstance, map.get(field.getName()));
			}
		}
		return newInstance;		
	}
	
	/**
	 * 反射获取子类继承父类中的泛型的具体类型
	 * @param class1
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList<Class> getGenericExtendsType(Class<?> class1) {
		ArrayList<Class> arrayList = new ArrayList<>();
		Type genericSuperclass = class1.getGenericSuperclass(); //得到带父类泛型的type对象
		if (genericSuperclass instanceof ParameterizedType) {
			Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
			for (Type type : actualTypeArguments) {
				arrayList.add((Class)type);
			}
			return arrayList;
		}
		return null;
	}
	/**
	 * 反射获取子类实现接口中的泛型的具体类型
	 * @param class1
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	public static Map<Integer, ArrayList> getGenericInterfaceType(Class<?> class1) {
		Map<Integer, ArrayList> map = new HashMap<Integer, ArrayList>();
		Type[] genericInterfaces = class1.getGenericInterfaces(); // 有可能实现多个接口
		for (int i = 0; i < genericInterfaces.length; i++) {
			Type type = genericInterfaces[i];
			ArrayList<Class> arrayList = new ArrayList<>();
			Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments(); //得到实现接口的泛型的type对象
			for (Type type1 : actualTypeArguments) {
				arrayList.add((Class)type1);
			}
			map.put(i, arrayList);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			String fileToString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><reqService><reqHeader><tranCode>TS01</tranCode><transDate>20190619</transDate><sysId>0515</sysId><addr>com.spdb.training.service.servicedemo1</addr></reqHeader><body><globalSeq>05152345676576</globalSeq><transSeq>32456788654334001</transSeq><transDate>20190619</transDate><name>zhangSan</name><age>19</age><telNo>18800009999</telNo></body></reqService>";
					
//					xmlToString("D:\\test\\Request.xml");
			System.out.println("xml内容："+xmlToString("D:\\eclipse\\workspace64\\Java-Socket\\src\\main\\resources\\UserRegisterReq.xml"));
			System.out.println("xml内容：" + fileToString);

//			com.spdb.training.xml.ReqService reqService = XmlToJava(fileToString, ReqService.class);
//			System.out.println("header:" + reqService.getHeader().toString());

			Map<String, Object> xml2Map = convertXmlToMap(fileToString);
			Map<String, Object> reqServiceMap = (Map<String, Object>) xml2Map.get("reqService");
			Map<String, Object> reqHeaderMap = (Map<String, Object>) reqServiceMap.get("reqHeader");
//			Map<String, Object> tranCodeMap = (Map<String, Object>) reqHeaderMap.get("tranCode");
			String tranCode = (String) reqHeaderMap.get("tranCode");
			System.out.println("tranCode:" + tranCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
