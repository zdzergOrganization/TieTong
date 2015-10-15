package com.tietong.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownloaderService {
	public void download(String path, HttpServletRequest request, HttpServletResponse response) {
		int BUF_SIZE = 4096;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		ServletContext context = request.getServletContext();
		String appPath = context.getRealPath("");
		String fullpath = /*appPath + */path;

		try {
			File downloadFile = new File(fullpath);
			inputStream = new FileInputStream(downloadFile);
			// get mime type of the file
			String mimeType = context.getMimeType(fullpath);
			if (mimeType == null) {
				// set to binary type if mime mapping not found
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			//TODO: response.setContentLengthLong(downloadFile.length());
			response.setContentLength((int)downloadFile.length());

			// set headers
			response.setHeader("Content-Disposition",
					String.format("attachment; filename=\"%s\"", downloadFile.getName()));

			outputStream = response.getOutputStream();
			byte[] buffer = new byte[BUF_SIZE];
			int byteRead = -1;
			while ((byteRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, byteRead);
			}

		} catch (IOException e) {

		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
				if (outputStream != null)
					outputStream.close();
			} catch (Exception e) {

			}
		}
	}
}
