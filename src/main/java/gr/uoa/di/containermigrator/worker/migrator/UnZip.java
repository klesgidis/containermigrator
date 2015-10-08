/*
 * @Copyright Mkyong.com
 * http://www.mkyong.com/java/how-to-decompress-files-from-a-zip-file/
 */
package gr.uoa.di.containermigrator.worker.migrator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZip
{
	private List<String> fileList;
	private final String source;
	private final String output;

	public UnZip(String source, String output) {
		this.source = source;
		this.output = output.endsWith("/") ? output.substring(0, output.length()-1) : output;
	}

	/**
	 * Unzip it
	 */
	public void unZipIt(){
		byte[] buffer = new byte[1024];

		try{

			//create output directory is not exists
			File folder = new File(output);
			if(!folder.exists()){
				folder.mkdir();
			}

			//get the zip file content
			ZipInputStream zis =
					new ZipInputStream(new FileInputStream(source));
			//get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			while(ze!=null){

				String fileName = ze.getName();
				File newFile = new File(output + File.separator + fileName);

				//create all non exists folders
				//else you will hit FileNotFoundException for compressed folder
				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

			System.out.println("Done");

		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
