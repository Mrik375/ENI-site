package fr.eni.site.dal;

import fr.eni.site.bo.ArticleAVendre;
import fr.eni.site.bo.Enchere;
import fr.eni.site.bo.Utilisateur;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class EnchereDAOImpl implements EnchereDAO {
	private static final String SQL_INSERT = "INSERT INTO ENCHERES (id_utilisateur, no_article, montant_enchere, date_enchere) VALUES (:id_utilisateur, :no_article, :montant_enchere, :date_enchere)";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM ENCHERES WHERE id_utilisateur = :id_utilisateur AND no_article = :no_article AND montant_enchere = :montant_enchere";
	private static final String SQL_SELECT_ALL = "SELECT * FROM ENCHERES";
	private static final String SQL_SELECT_BY_UTILISATEUR = "SELECT * FROM ENCHERES WHERE id_utilisateur = :id_utilisateur";
	private static final String SQL_SELECT_BY_ARTICLE = "SELECT * FROM ENCHERES WHERE no_article = :no_article";
	private static final String SQL_SELECT_HIGHEST_BY_ARTICLE = "SELECT * FROM ENCHERES WHERE no_article = :no_article ORDER BY montant_enchere DESC LIMIT 1";

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final UtilisateurDAO utilisateurDAO;
	private final ArticleAVendreDAO articleDAO;

	public EnchereDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, UtilisateurDAO utilisateurDAO, ArticleAVendreDAO articleDAO) {
		this.jdbcTemplate = jdbcTemplate;
		this.utilisateurDAO = utilisateurDAO;
		this.articleDAO = articleDAO;
	}

	@Override
	public void create(Enchere enchere) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_utilisateur", enchere.getAcquereur().getPseudo());
		params.addValue("no_article", enchere.getArticleAVendre().getId());
		params.addValue("montant_enchere", enchere.getMontant());
		params.addValue("date_enchere", enchere.getDate());
		jdbcTemplate.update(SQL_INSERT, params);
	}

	@Override
	public Enchere read(String idUtilisateur, long noArticle, int montantEnchere) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_utilisateur", idUtilisateur);
		params.addValue("no_article", noArticle);
		params.addValue("montant_enchere", montantEnchere);
		return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, params, new EnchereRowMapper());
	}

	@Override
	public List<Enchere> findAll() {
		return jdbcTemplate.query(SQL_SELECT_ALL, new EnchereRowMapper());
	}

	@Override
	public List<Enchere> findByUtilisateur(String pseudo) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_utilisateur", pseudo);
		return jdbcTemplate.query(SQL_SELECT_BY_UTILISATEUR, params, new EnchereRowMapper());
	}

	@Override
	public List<Enchere> findByArticle(long noArticle) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("no_article", noArticle);
		return jdbcTemplate.query(SQL_SELECT_BY_ARTICLE, params, new EnchereRowMapper());
	}

	@Override
	public Enchere findHighestEnchereForArticle(long noArticle) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("no_article", noArticle);
		return jdbcTemplate.queryForObject(SQL_SELECT_HIGHEST_BY_ARTICLE, params, new EnchereRowMapper());
	}

	private class EnchereRowMapper implements RowMapper<Enchere> {
		@Override
		public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
			Utilisateur acquereur = utilisateurDAO.readByPseudo(rs.getString("id_utilisateur"));
			ArticleAVendre article = articleDAO.read(rs.getLong("no_article"));

			return new Enchere(
					rs.getObject("date_enchere", LocalDate.class),
					rs.getInt("montant_enchere"),
					article,
					acquereur
			);
		}
	}
}

