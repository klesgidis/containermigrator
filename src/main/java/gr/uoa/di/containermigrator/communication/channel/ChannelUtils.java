package gr.uoa.di.containermigrator.communication.channel;

import gr.uoa.di.containermigrator.communication.protocol.Command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author kyriakos
 */
public class ChannelUtils {

//	public static void sendFileData(Filedata.FileData fileData, DataOutputStream dOut) throws IOException {
//		fileData.writeDelimitedTo(dOut);
//	}
//	public static Filedata.FileData recvFileData(DataInputStream dIn) throws IOException{
//		return Filedata.FileData.parseDelimitedFrom(dIn);
//	}
//
//	public static void sendAdminCommand(Command.AdminCmd adminCommand, DataOutputStream dOut) throws IOException {
//		adminCommand.writeDelimitedTo(dOut);
//	}
//	public static Command.AdminCmd recvAdminCommand(DataInputStream dIn) throws IOException{
//		return Command.AdminCmd.parseDelimitedFrom(dIn);
//	}
//
	public static void sendReadyToRestore(Command.ReadyToRestore cmd, DataOutputStream dOut) throws IOException {
		cmd.writeDelimitedTo(dOut);
	}
	public static Command.ReadyToRestore recvReadyToRestore(DataInputStream dIn) throws IOException {
		return Command.ReadyToRestore.parseDelimitedFrom(dIn);
	}
//
//	public static void sendFileParts(Path path, DataOutputStream dOut) throws IOException {
//
//		if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
//			try (InputStream fis = new FileInputStream(path.toFile())) {
//				byte[] buffer = new byte[Preferences.CHUNK_SIZE];
//
//				int len;
//				while ((len = fis.read(buffer, 0, Preferences.CHUNK_SIZE)) != -1) {
//					Testfile.TestFile tf = Testfile.TestFile.newBuilder()
//							.setLen(len)
//							.setData(ByteString.copyFrom(buffer))
//							.setIsLast(false)
//							.build();
//
//					tf.writeDelimitedTo(dOut);
//
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally {
//				Testfile.TestFile tf = Testfile.TestFile.newBuilder().setIsLast(true).build();
//				tf.writeDelimitedTo(dOut);
//			}
//		}
//
//	}
//	public static void recvFileParts(Path path, DataInputStream dIn) throws IOException {
//
//		Testfile.TestFile tf = null;
//		try (FileOutputStream fOut = new FileOutputStream(path.toFile())) {
//			while (true) {
//				tf = Testfile.TestFile.parseDelimitedFrom(dIn);
//				if (tf == null || tf.getIsLast()) break;
//
//				fOut.write(tf.getData().toByteArray(), 0, tf.getLen());
//			}
//		}
//		//System.out.println("Read " + path.toFile().length());
//	}
//
//	public static void sendFileToHost(Filedata.FileData fd, DataOutputStream dOut, Container c) throws IOException {
//		sendFileData(fd, dOut);
//
//		boolean hasToSetData = !fd.getAttributes().getIsDirectory() && !fd.getAttributes().getIsSymbolicLink() &&
//				(fd.getType() == Filedata.FileData.EventType.EXISTING_FILE
//						|| fd.getType() == Filedata.FileData.EventType.CREATE
//						|| fd.getType() == Filedata.FileData.EventType.MODIFY
//						|| fd.getType() == Filedata.FileData.EventType.CLOSE_WRITE
//						|| fd.getType() == Filedata.FileData.EventType.MOVED_TO
//				);
//
//		if (hasToSetData)
//			sendFileParts(
//					Paths.get(DockerUtils.createContainerFilePath(c,
//							/*"/home/kyriakos/Desktop/test" +*/ fd.getRelativePath())),
//					dOut
//			);
//	}
//	public static Filedata.FileData recvFileFromHost(DataInputStream dIn, Container c) throws FileReceiveException {
//		Filedata.FileData fileData = null;
//		try {
//			fileData = recvFileData(dIn);
//
//			boolean hasToReceiveData = !fileData.getAttributes().getIsDirectory() && !fileData.getAttributes().getIsSymbolicLink() &&
//					(fileData.getType() == Filedata.FileData.EventType.EXISTING_FILE
//							|| fileData.getType() == Filedata.FileData.EventType.CREATE
//							|| fileData.getType() == Filedata.FileData.EventType.MODIFY
//							|| fileData.getType() == Filedata.FileData.EventType.CLOSE_WRITE
//							|| fileData.getType() == Filedata.FileData.EventType.MOVED_TO
//					);
//
//			if (hasToReceiveData) {
////			Path path = FileUtils.preparePath(DockerUtils.getDiffDirectoryPath(c)/*"/home/kyriakos/Desktop/testCopy"*/,
////					fileData.getRelativePath());
//				Path path = Paths.get(DockerUtils.getDiffDirectoryPath(c),
//						fileData.getType() == Filedata.FileData.EventType.EXISTING_FILE ? "/tmp_existing_file" : "/tmp_live_file");
//				if (path.toFile().exists()) path.toFile().delete();
//
//				recvFileParts(path, dIn);
//			}
//			return fileData;
//		} catch (Exception e) {
//			throw new FileReceiveException(fileData, e);
//		}
//	}
}
