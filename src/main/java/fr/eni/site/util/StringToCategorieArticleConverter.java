package fr.eni.site.util;

import fr.eni.site.bo.CategorieArticle;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategorieArticleConverter implements Converter<String, CategorieArticle> {

	@Override
	public CategorieArticle convert(String source) {
		return CategorieArticle.fromDescription(source);
	}
}
