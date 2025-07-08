package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JsonTemplateManager {
	public static String populateTemplate(String templatePath, Map<String, Object> values) throws IOException {
		String template = Files.readString(Paths.get(templatePath));
		for (Map.Entry<String, Object> entry : values.entrySet()) {
			template = template.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
		}
		return template;
	}
}