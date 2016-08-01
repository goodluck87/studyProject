package org.util.file;

import java.io.File;

import org.junit.Test;

public class FileUtilTest {

	@Test
	public void testDelete(){
		FileUtil.deleteDir(new File("F://tmp"));
	}
}
