package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

public class BinaryUploader {

	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		InputStream fileStream = null;
		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}
		try {
			//修改了百度使用原生的commons上传方式
			DefaultMultipartHttpServletRequest multipartRequest=(DefaultMultipartHttpServletRequest)request;
			Iterator<String> fileNames=multipartRequest.getFileNames();
			MultipartFile file=null;
			while (fileNames.hasNext()){
				file=multipartRequest.getFiles(fileNames.next()).get(0);
				fileStream=file.getInputStream();
			}
			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}
			String savePath = (String) conf.get("savePath");
			String originFileName = file.getOriginalFilename();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			savePath = savePath + suffix;
			long maxSize = ((Long) conf.get("maxSize")).longValue();
			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath, originFileName);

			String physicalPath = (String) conf.get("rootPath") + savePath;
			State storageState = StorageManager.saveFileByInputStream(fileStream,
					physicalPath, maxSize);
			fileStream.close();
			if (storageState.isSuccess()) {
				storageState.putInfo("url", PathFormat.format(savePath));
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}

			return storageState;
		} catch (ClassCastException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		}catch (IOException e){
			return new BaseState(false, AppInfo.IO_ERROR);
		}
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
