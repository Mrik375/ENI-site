package fr.eni.site.dal;

import fr.eni.site.bo.ArticleAVendre;
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

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final UtilisateurDAO utilisateurDAO;
	private final AdresseDAO adresseDAO;
	private final CategorieDAO categorieDAO;

	public ArticleAVendreDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, UtilisateurDAO utilisateurDAO, AdresseDAO adresseDAO, CategorieDAO categorieDAO) {
		this.jdbcTemplate = jdbcTemplate;
		this.utilisateurDAO = utilisateurDAO;
		this.adresseDAO = adresseDAO;
		this.categorieDAO = categorieDAO;
	}

	@Override
	public long create(ArticleAVendre article) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("nom_article", article.getNom());
		params.addValue("description", article.getDescription());
		params.addValue("photo", article.getPhoto());
		params.addValue("date_debut_encheres", article.getDateDebutEncheres());
		params.addValue("date_fin_encheres", article.getDateFinEncheres());
		params.addValue("statut_enchere", article.getStatutEnchere());
		params.addValue("prix_initial", article.getPrixInitial());
		params.addValue("prix_vente", article.getPrixVente());
		params.addValue("id_utilisateur", article.getVendeurId());
		params.addValue("no_categorie", article.getCategorieId());
		params.addValue("no_adresse_retrait", article.getAdresseRetraitId());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(SQL_INSERT, params, keyHolder, new String[] {"no_article"});
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

	private class ArticleAVendreRowMapper implements RowMapper<ArticleAVendre> {
		@Override
		public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new ArticleAVendre(
					rs.getLong("no_article"),
					rs.getString("nom_article"),
					rs.getString("description"),
					rs.getInt("photo"),
					rs.getObject("date_debut_encheres", LocalDate.class),
					rs.getObject("date_fin_encheres", LocalDate.class),
					rs.getInt("statut_enchere"),
					rs.getInt("prix_initial"),
					rs.getInt("prix_vente"),
					rs.getString("id_utilisateur"),
					rs.getLong("no_adresse_retrait"),
					rs.getLong("no_categorie")
			);
		}
	}
}
