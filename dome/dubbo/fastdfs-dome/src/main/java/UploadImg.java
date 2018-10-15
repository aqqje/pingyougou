import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class UploadImg {

	public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
		// 1.加载配置文件，配置文件中的内容就是tracker服务的地址(绝对路径)
		ClientGlobal.init("E:\\github\\pingyougou\\dome\\dubbo\\fastdfs-dome\\src\\main\\resources\\fdfs_client.conf");
		
		// 2.创建一个TrackerClick对象，
		TrackerClient trackerClient = new TrackerClient();
		
		// 3.使用TrackerClick对象创建并连接，获得一个TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		
		// 4.创建一个StorageServer的引用，值为 null
		StorageServer storageServer = null;
		
		// 5.创建一个StorageClient对象，需要两个参数 TackerServer 对象，StorageServer的引用
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		
		// 6.使用StorageClient对象上传图片，扩展名不带"."
		String[] Strings = storageClient.upload_file("C:\\wallpaper\\360\\319354.jpg", "jpg", null);
		
		// 7.返回数组，包含引用名和图片的路径
		for(String string: Strings) {
			System.out.println(string);
		}
	}
}
