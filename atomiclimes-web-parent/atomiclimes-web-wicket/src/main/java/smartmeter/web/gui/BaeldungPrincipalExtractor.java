package smartmeter.web.gui;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;

public class BaeldungPrincipalExtractor implements PrincipalExtractor {

	@Override
	public Object extractPrincipal(Map<String, Object> map) {
		return map.get("name");
	}

}
