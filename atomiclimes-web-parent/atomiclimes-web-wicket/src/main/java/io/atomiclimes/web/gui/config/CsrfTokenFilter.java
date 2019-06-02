package io.atomiclimes.web.gui.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

public class CsrfTokenFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		// Spring Security will allow the Token to be included in this header name
		response.setHeader("X-CSRF-HEADER", token.getHeaderName());
		// Spring Security will allow the token to be included in this parameter name
		response.setHeader("X-CSRF-PARAM", token.getParameterName());
		// this is the value of the token to be included as either a header or an HTTP
		// parameter
		response.setHeader("X-CSRF-TOKEN", token.getToken());

		// Modify HTML
		// Wrap the response in a wrapper so we can get at the text after calling the
		// next filter
		PrintWriter out = response.getWriter();
		CharResponseWrapper wrapper = new CharResponseWrapper((HttpServletResponse) response);
		filterChain.doFilter(request, wrapper);
		String modifiedHtml = wrapper.toString(); // Extract the text from the completed servlet and apply the regexes
		modifiedHtml = modifiedHtml.replace("${_csrf.token}", token.getToken());
		modifiedHtml = modifiedHtml.replace("${_csrf.parameterName}", token.getParameterName());
		modifiedHtml = modifiedHtml.replace("${_csrf.headerName}", token.getHeaderName());
		// Write our modified text to the real response
		response.setContentLength(modifiedHtml.getBytes().length);
		out.write(modifiedHtml);
		out.close();
	}

}
