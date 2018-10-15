import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class UploadImg {

	public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
		// 1.���������ļ��������ļ��е����ݾ���tracker����ĵ�ַ(����·��)
		ClientGlobal.init("E:\\github\\pingyougou\\dome\\dubbo\\fastdfs-dome\\src\\main\\resources\\fdfs_client.conf");
		
		// 2.����һ��TrackerClick����
		TrackerClient trackerClient = new TrackerClient();
		
		// 3.ʹ��TrackerClick���󴴽������ӣ����һ��TrackerServer����
		TrackerServer trackerServer = trackerClient.getConnection();
		
		// 4.����һ��StorageServer�����ã�ֵΪ null
		StorageServer storageServer = null;
		
		// 5.����һ��StorageClient������Ҫ�������� TackerServer ����StorageServer������
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		
		// 6.ʹ��StorageClient�����ϴ�ͼƬ����չ������"."
		String[] Strings = storageClient.upload_file("C:\\wallpaper\\360\\319354.jpg", "jpg", null);
		
		// 7.�������飬������������ͼƬ��·��
		for(String string: Strings) {
			System.out.println(string);
		}
	}
}
