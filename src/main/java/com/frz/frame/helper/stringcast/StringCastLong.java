package com.frz.frame.helper.stringcast;

import com.frz.frame.helper.ReflectHelper.StringCast;

public class StringCastLong implements StringCast {

	@Override
	public Object cast(String value) {
		return new Long(value);
	}

}
