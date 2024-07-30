package fr.eni.site.dal.impl;

import fr.eni.site.bo.*;
import fr.eni.site.dal.ArticleAVendreDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ArticleAVendreDAOImpl implements ArticleAVendreDAO {
	private static final String SQL_INSERT = "INSERT INTO ARTICLES_A_VENDRE (nom_article, description, photo, date_debut_encheres, date_fin_encheres, statut_enchere, prix_initial, prix_vente, id_utilisateur, no_categorie, no_adresse_retrait) VALUES (:nom_article, :description, :photo, :date_debut_encheres, :date_fin_encheres, :statut_enchere, :prix_initial, :prix_vente, :id_utilisateur, :no_categorie, :no_adresse_retrait)";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM ARTICLES_A_VENDRE WHERE no_article = :id";
	private static final String SQL_SELECT_ALL = "SELECT * FROM ARTICLES_A_VENDRE";
	private static final String SQL_SELECT_BY_UTILISATEUR = "SELECT * FROM ARTICLES_A_VENDRE WHERE id_utilisateur = :id_utilisateur";
	private static final String SQL_UPDATE_STATUS = "UPDATE ARTICLES_A_VENDRE SET statut_enchere = :statut_enchere WHERE no_article = :id";

	private static final String SQL_WHERE = " WHERE ";
	private static final String SQL_AND = " AND ";
	private static final String SQL_OR = " OR ";
	private static final String SQL_STATUT_ENCHERE = "statut_enchere = :statut_enchere";
	private static final String SQL_ID_UTILISATEUR = "id_utilisateur = :id_utilisateur";
	private static final String SQL_NOM_ARTICLE = "nom_article LIKE :nom_article";
	private static final String SQL_NO_CATEGORIE = "no_categorie = :no_categorie";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public ArticleAVendreDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public long create(ArticleAVendre article) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("nom_article", article.getNom());
		params.addValue("description", article.getDescription());
		params.addValue("photo", article.getPhoto());
		params.addValue("date_debut_encheres", article.getDateDebutEncheres());
		params.addValue("date_fin_encheres", article.getDateFinEncheres());
		params.addValue("statut_enchere", article.getStatutEnchere().getCode());
		params.addValue("prix_initial", article.getPrixInitial());
		params.addValue("prix_vente", article.getPrixVente());
		params.addValue("id_utilisateur", article.getVendeur().getPseudo());
		params.addValue("no_categorie", article.getCategorie().getId());
		params.addValue("no_adresse_retrait", article.getAdresseRetrait().getId());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(SQL_INSERT, params, keyHolder, new String[]{"no_article"});
		return keyHolder.getKey().longValue();
	}

	@Override
	public ArticleAVendre read(long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, params, new ArticleAVendreRowMapper());
	}

	@Override
	public List<ArticleAVendre> findAll() {
		return jdbcTemplate.query(SQL_SELECT_ALL, new ArticleAVendreRowMapper());
	}

	@Override
	public List<ArticleAVendre> findByUtilisateur(String pseudo) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_utilisateur", pseudo);
		return jdbcTemplate.query(SQL_SELECT_BY_UTILISATEUR, params, new ArticleAVendreRowMapper());
	}

	@Override
	public void setStatus(long id, ArticleStatus statutEnchere) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		params.addValue("statut_enchere", statutEnchere.getCode());
		jdbcTemplate.update(SQL_UPDATE_STATUS, params);
	}

	@Override
	public List<ArticleAVendre> findStatusByFiltre(ArticleStatus[] articleStatuses, String pseudo, String nomArticle, CategorieArticle categorie) {
		StringBuilder sql = new StringBuilder(SQL_SELECT_ALL).append(SQL_WHERE);

		MapSqlParameterSource params = new MapSqlParameterSource();

		if (articleStatuses != null && articleStatuses.length > 0) {
			sql.append("(");
			for (int i = 0; i < articleStatuses.length; i++) {
				sql.append(SQL_STATUT_ENCHERE).append(i);
				params.addValue("statut_enchere" + i, articleStatuses[i].getCode());
				if (i < articleStatuses.length - 1) {
					sql.append(SQL_OR);
				}
			}
			sql.append(")");
		}

		if (pseudo != null && !pseudo.isEmpty()) {
			sql.append(SQL_AND).append(SQL_ID_UTILISATEUR);
			params.addValue("id_utilisateur", pseudo);
		}

		if (nomArticle != null && !nomArticle.isEmpty()) {
			sql.append(SQL_AND).append(SQL_NOM_ARTICLE);
			params.addValue("nom_article", "%" + nomArticle + "%");
		}

		if (categorie != null) {
			sql.append(SQL_AND).append(SQL_NO_CATEGORIE);
			params.addValue("no_categorie", categorie.getCode());
		}

		return jdbcTemplate.query(sql.toString(), params, new ArticleAVendreRowMapper());
	}


	private class ArticleAVendreRowMapper implements RowMapper<ArticleAVendre> {
		@Override
		public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {
			Categorie categorie = new Categorie();
			categorie.setId(rs.getLong("no_categorie"));
			Utilisateur vendeur = new Utilisateur();
			vendeur.setPseudo(rs.getString("id_utilisateur"));
			Adresse adresseRetrait = new Adresse();
			adresseRetrait.setId(rs.getLong("no_adresse_retrait"));
			ArticleStatus statutEnchere = ArticleStatus.fromCode(rs.getInt("statut_enchere"));
			return new ArticleAVendre(
					rs.getLong("no_article"),
					rs.getString("nom_article"),
					rs.getString("description"),
					rs.getInt("photo"),
					rs.getObject("date_debut_encheres", LocalDate.class),
					rs.getObject("date_fin_encheres", LocalDate.class),
					statutEnchere,
					rs.getInt("prix_initial"),
					rs.getInt("prix_vente"),
					vendeur,
					adresseRetrait,
					categorie
			);
		}
	}
}
