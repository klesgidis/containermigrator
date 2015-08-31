/*
 * @Copyright Mkyong.com
 * http://www.mkyong.com/java/how-to-compress-files-in-zip-format/
 */
package gr.uoa.di.containermigrator.migrator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip
{
	List<String> fileList;
	private final String output;
	private final String source;

	public Zip(String source, String output){
		this.source = source.endsWith("/") ? source.substring(0, source.length()-1) : source;
		this.output = output;
		fileList = new ArrayList<>();
	}

	//	public static void main( String[] args )
//	{
//		AppZip appZip = new AppZip();
//		appZip.generateFileList(new File(SOURCE_FOLDER));
//		appZip.zipIt(OUTPUT_ZIP_FILE);
//	}

	/**
	 * Zip it
	 */
	public void zipIt(){

		byte[] buffer = new byte[1024];

		try{

			FileOutputStream fos = new FileOutputStream(this.output);
			ZipOutputStream zos = new ZipOutputStream(fos);

			//System.out.println("Output to Zip : " + this.output);

			for(String file : this.fileList){

				//System.out.println("File Added : " + file);
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in =
						new FileInputStream(this.source + File.separator + file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			//remember close it
			zos.close();

			System.out.println("Done");
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public void generateFileList() {
		File node = new File(this.source);
		generateFileList(node);
	}

	/**
	 * Traverse a directory and get all files,
	 * and add the file into fileList  
	 * @param node file or directory
	 */
	private void generateFileList(File node){

		//add file only
		if(node.isFile()){
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
		}

		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				generateFileList(new File(node, filename));
			}
		}

	}

	/**
	 * Format the file path for zip
	 * @param file file path
	 * @return Formatted file path
	 */
	private String generateZipEntry(String file){
		return file.substring(this.source.length()+1, file.length());
	}
}
