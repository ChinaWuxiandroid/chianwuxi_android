package com.wuxi.app.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author wanglu 泰得利通 解析帮助类
 * 
 */
public class JAsonPaserUtil {

	/**
	 * 
	 * wanglu 泰得利通 解析
	 * 
	 * @param t
	 * @param jsArray
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws JSONException
	 */
	public static <T> List<T> getListByJassory(Class<T> t, JSONArray jsArray)
			throws JSONException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			IllegalArgumentException, InvocationTargetException {

		List<T> ts = new ArrayList<T>();

		T o;

		for (int index = 0; index < jsArray.length(); index++) {

			JSONObject jb = jsArray.getJSONObject(index);

			o = t.newInstance();

			Field[] fields = t.getDeclaredFields();
			for (Field field : fields) {
				String fileName = field.getName();
				String tyeName = field.getType().getName();
				String methodName = "set"
						+ fileName.substring(0, 1).toUpperCase()
						+ fileName.substring(1);

				Method method = null;

				if (tyeName.equals("int") && jb.has(fileName)) {

					method = t.getDeclaredMethod(methodName, int.class);
					method.invoke(o, jb.getInt(fileName));

				} else if (tyeName.equals("boolean") && jb.has(fileName)) {
					method = t.getDeclaredMethod(methodName, boolean.class);
					method.invoke(o, jb.getBoolean(fileName));
				} else if (tyeName.equals("java.lang.String")) {
					method = t.getDeclaredMethod(methodName,
							java.lang.String.class);
					method.invoke(o, jb.getString(fileName));
				}
			}
			ts.add(o);
		}

		return ts;

	}

	/**
	 * 
	 *wanglu 泰得利通 
	 * @param t
	 * @param fileNames  要解析的字段属性名称
	 * @param jsArray
	 * @return
	 * @throws JSONException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchFieldException
	 */
	public static <T> List<T> getListByFeildNames(Class<T> t,
			String fileNames[], JSONArray jsArray) throws JSONException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, IllegalArgumentException,
			InvocationTargetException, NoSuchFieldException {

		List<T> ts = new ArrayList<T>();

		T o;

		for (int index = 0; index < jsArray.length(); index++) {

			JSONObject jb = jsArray.getJSONObject(index);

			o = t.newInstance();

			for (String fileName : fileNames) {
				Field field = t.getDeclaredField(fileName);
				String tyeName = field.getType().getName();
				String methodName = "set"
						+ fileName.substring(0, 1).toUpperCase()
						+ fileName.substring(1);

				Method method = null;

				if (tyeName.equals("int") && jb.has(fileName)) {

					method = t.getDeclaredMethod(methodName, int.class);
					method.invoke(o, jb.getInt(fileName));

				} else if (tyeName.equals("boolean") && jb.has(fileName)) {
					method = t.getDeclaredMethod(methodName, boolean.class);
					method.invoke(o, jb.getBoolean(fileName));
				} else if (tyeName.equals("java.lang.String")) {
					method = t.getDeclaredMethod(methodName,
							java.lang.String.class);
					method.invoke(o, jb.getString("fileName"));
				}

			}

			ts.add(o);

		}

		return ts;

	}
}
