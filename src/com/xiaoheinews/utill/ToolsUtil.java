package com.xiaoheinews.utill;

public class ToolsUtil {
	
	public static int objToInt(Object object)
	{
		if (null == object)
		{
			return 0;
		}
		try
		{
			return Integer.valueOf(String.valueOf(object));
		}
		catch (Exception e)
		{
			return 0;
		}
	}

}
