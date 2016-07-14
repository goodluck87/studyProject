package org.util.object;

import org.junit.Test;

public class ObjectUtilTest {

	@Test
	public void testObjToPrimitive(){
		Boolean b = new Boolean("");
		Character c = new Character('a');
		System.out.println(ObjectUtil.objToPrimitive("Hello World", String.class));
		System.out.println(ObjectUtil.objToPrimitive("TRUE1", Boolean.class));
	}
}
