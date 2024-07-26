package fr.eni.site.bll.services.impl;

import fr.eni.site.bll.services.ArticleAVendreService;
import fr.eni.site.bo.ArticleAVendre;
import fr.eni.site.bo.ArticleStatus;
import fr.eni.site.dal.ArticleAVendreDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleAVendreServiceImpl implements ArticleAVendreService {
	private final ArticleAVendreDAO articleAVendreDAO;

	public ArticleAVendreServiceImpl(ArticleAVendreDAO articleAVendreDAO) {
		this.articleAVendreDAO = articleAVendreDAO;
	}

	@Override
	public long createArticle(ArticleAVendre article) {
		return articleAVendreDAO.create(article);
	}

	@Override
	public ArticleAVendre getArticleById(long id) {
		return articleAVendreDAO.read(id);
	}

	@Override
	public List<ArticleAVendre> getAllArticles() {
		return articleAVendreDAO.findAll();
	}

	@Override
	public List<ArticleAVendre> getArticlesByUtilisateur(String pseudo) {
		return articleAVendreDAO.findByUtilisateur(pseudo);
	}

	@Override
	public List<ArticleAVendre> getAllActiveArticles() {
		return articleAVendreDAO.findAllActive();
	}

	@Override
	public void setArticleStatus(long id, ArticleStatus statutEnchere) {
		articleAVendreDAO.setStatus(id, statutEnchere);
	}

}