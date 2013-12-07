package com.doufangbian.util;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class DeleteServicesImage {
	/**
	 * 删除服务器上的图片
	 * 
	 * @return
	 */
	public static void deleteServicesImage(HttpServletRequest request,
			String dir, List<String> imageName) {
		// 获取根路劲
		String path = request.getSession().getServletContext().getRealPath("/")
				+ "/" + dir + "/";

		if (imageName != null && imageName.size() > 0) {

			for (int i = 0; i < imageName.size(); i++) {

				File f = new File(path + imageName.get(i));
				// 删除存在图片 且图片名不为系统默认图片
				if (f.exists() && !"null.jpg".equals(f.getName())) {
					f.delete();
				}
			}
		}

	}
}
