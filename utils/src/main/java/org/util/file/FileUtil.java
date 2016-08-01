package org.util.file;

import java.io.File;
import java.io.IOException;

import org.util.object.ObjectUtil;
import org.util.str.StrUtil;

/**
 * File文件操作工具类
 * @author 肖鸿
 */
public final class FileUtil {
	/**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
	/**
	 * 根据文件路径filePath创建文件<BR>
	 * 内部调用<code>createNewFile(File file)</code>，即此方法始终是以创建文件为目的<BR> 
	 * @param filePath 文件路径
	 * @return boolean 是否创建成功。成功：true；失败：false
	 */
	public static boolean createNewFile(String filePath){
		if(StrUtil.isNullOrEmpty(filePath)){
			System.err.println("FileUtil.createNewFile中filePath路径为空！"+"\n");
			return false;
		}
		File file = new File(filePath);
		return createNewFile(file);
	}
	/**
	 * 创建文件file<BR>
	 * 如果存在一个文件夹名与要创建的file文件名同路径同名，则会创建失败<BR>
	 * 如果文件file是一文件夹目录，例如a/b/c/d，则d将会以文件形式创建。<BR>
	 * 即此方法始终是以创建文件为目的<BR>
	 * @param file 要创建的文件
	 * @return boolean 是否创建成功。成功：true；失败：false
	 */
	public static boolean createNewFile(File file){
		if(ObjectUtil.isNull(file)){
			System.err.println("FileUtil.createNewFile中file值为空！");
			return false;
		}
		boolean success = false;
		/*
		 * 是否能创建file。即是父路径是否存在
		 */
		boolean isCanCreate = false;
		/*
		 * 如果file没有指定父路径，则file.getParentFile()返回NULL
		 */
		File parentFile = file.getParentFile();
		/*
		 * 指定了父目录
		 */
		if(!ObjectUtil.isNull(parentFile)){
			//父路径不存在，即没有创建
			if(!parentFile.exists()){
				//创建父目录，判断是否创建成功
				if(parentFile.mkdirs()){
					isCanCreate = true;
				}
				else {
					isCanCreate = false;
				}
			}
			//父路径已经存在，即已经创建
			else{
				isCanCreate = true;
			}
		}
		/*
		 * 没指定父目录
		 * 即file的抽象路径中只有文件名,此路径名没有指定父目录。例如：new File("a.txt");
		 */
		else{
			//可以创建没指定父目录的文件
			isCanCreate = true;
		}
		/*
		 * 文件已经存在
		 */
		if(file.exists()){
			isCanCreate = false;
			if(file.isFile()){
				success = true;
			}
			else{
				System.out.println("所要创建的"+file.getAbsolutePath()+"已经存在！\n" +
								   "但不是文件，是文件夹！");
			}
		}
		/*
		 * 可以创建文件file
		 */
		if(isCanCreate){
			try {
				success = file.createNewFile();
			} catch (IOException e) {
				success = false;
				e.printStackTrace();
			}
		}
		return success;
	}
	
	/**
	 * 根据文件路径filePath创建文件或获得已经存在的文件<BR>
	 * 如果存在一个文件夹名与要创建的file文件名同路径同名，则会创建失败<BR>
	 * 内部调用<code>createNewFile(File file)</code>，即此方法始终是以创建文件为目的<BR> 
	 * @param filePath 文件路径
	 * @return 创建成功或已经存在：File；创建失败：null
	 */
	public static File createOrGetNewFile(String filePath){
		if(StrUtil.isNullOrEmpty(filePath)){
			System.err.println("FileUtil.createNewFile中filePath路径为空！"+"\n");
			return null;
		}
		File file = new File(filePath);
		return createNewFile(file) ? file : null;
	}
	
	/**
	 * 创建文件夹<BR>
	 * 要创建的file已经存在，但不是文件夹，而是文件时，创建失败<BR>
	 * @param file
	 * @return false:文件夹创建失败<BR>
	 * 		   true:创建成功<BR>
	 */
	public static boolean createDirectory(File file){
		if(ObjectUtil.isNull(file)){
			System.err.println("FileUtil.createDirectory中file值为空！");
			return false;
		}
		boolean success = false;
		if(file.exists()){
			if(file.isDirectory()){
				success = true;
			}
			else{
				System.out.println("所要创建的"+file.getAbsolutePath()+"已经存在！\n" +
				   "但不是文件夹，是文件！");
			}
		}
		else {
			success = file.mkdirs();
		}
		return success;
	}
	
	/**
	 * 创建或获取已经存在的文件夹<BR>
	 * 要创建的filePath已经存在，但不是文件夹，而是文件时，创建失败<BR>
	 * @param filePath
	 * @return 创建成功或已经存在则返回File文件夹；创建失败返回null;
	 */
	public static File createOrGetDirectory(String filePath){
		if(StrUtil.isNullOrEmpty(filePath)){
			System.err.println("FileUtil.createDirectory中filePath路径为空！"+"\n");
			return null;
		}
		File file = new File(filePath);
		return createDirectory(file) ? file : null;
	}
	
	/**
	 * 获得当前文件file(也可以是文件夹)的上层目录(即父文件)<BR>
	 * @param file 可以是文件夹
	 * @return 上层目录(即父文件)；当传入的file为空时：null。
	 */
	public static File getFileParent(File file){
		File directory = null;
		if(ObjectUtil.isNull(file)){
			System.err.println("FileUtil.getFileParent中file值为空！");
			return null;
		}
		directory = file.getParentFile();
		return ObjectUtil.isNull(directory) ? file : directory;
	}
	
	/**
	 * 获得当前文件filePath(也可以是文件夹)的上层目录(即父文件)<BR>
	 * @param filePath 可以是文件夹目录
	 * @return 上层目录(即父文件)；当传入的filePath为空时：null。
	 */
	public static File getFileParent(String filePath){
		if(StrUtil.isNullOrEmpty(filePath)){
			System.err.println("FileUtil.getFileParent中filePath路径为空！"+"\n");
			return null;
		}
		File file = new File(filePath);
		return getFileParent(file);
	}
	
}