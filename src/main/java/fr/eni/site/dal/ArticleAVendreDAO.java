package fr.eni.site.dal;

import fr.eni.site.bo.ArticleAVendre;

import java.util.List;

public interface ArticleAVendreDAO {
    List<ArticleAVendre> findByUtilisateur(String pseudo);
}
