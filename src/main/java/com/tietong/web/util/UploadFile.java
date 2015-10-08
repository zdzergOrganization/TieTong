package com.tietong.web.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pages")
public class UploadFile {

	@RequestMapping(value = "/upload.do")
	public String upload(@RequestParam(value = "file", required = false) MultipartFile file, String url,
			String truncateFlag, String uploadMonth, String tableName, HttpServletRequest request,
			RedirectAttributes attr) {

		String path = request.getSession().getServletContext().getRealPath("upload");
		String fileName = file.getOriginalFilename();
		// String fileName = new Date().getTime()+".jpg";

		File targetDir = new File(path);
		// 创建文件夹
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}

		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			try {
				targetFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 传入路径
		attr.addFlashAttribute("filePath", targetFile.getAbsolutePath());
		attr.addFlashAttribute("truncateFlag", truncateFlag);
		attr.addFlashAttribute("uploadMonth", uploadMonth);
		attr.addFlashAttribute("tableName", tableName);
		return "redirect:" + url;
	}

}