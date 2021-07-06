package com.shopme.site;

import javax.servlet.http.HttpServletRequest;

public class Utility {
	public static String getSite(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
}
}