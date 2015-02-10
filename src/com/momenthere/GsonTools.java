package com.momenthere;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

;
public class GsonTools {
	
	//json string to json objects
	public static <T> List<T> getMessages(String jsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			//TypeToken，它是gson提供的数据类型转换器，可以支持各种数据集合类型转换。
			list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
			}.getType());
		} catch (Exception e) {
		}
		return list;
	}

}
