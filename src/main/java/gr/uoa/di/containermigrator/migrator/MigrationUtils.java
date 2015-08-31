package gr.uoa.di.containermigrator.migrator;

import com.google.protobuf.ByteString;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class MigrationUtils {
	public static ByteString FileInputStreamToByteString(File file) throws IOException {
		try (FileInputStream fis = new FileInputStream(file)) {
			ByteString bytes = ByteString.copyFrom(org.apache.commons.compress.utils.IOUtils.toByteArray(fis));
			return bytes;
		}
	}
}
