package com.osg.purchase.util;

import java.util.Arrays;

import com.osg.purchase.entity.interf.EnumInterface;

public class EnumUtils {

	public static <E extends Enum & EnumInterface> E valueOf(Class<E> target, int code) {

        return Arrays.stream(target.getEnumConstants())
                .filter(data -> data.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

}
