package fr.eni.site.dal.impl;

import fr.eni.site.bo.Adresse;
import fr.eni.site.bo.ArticleAVendre;
import fr.eni.site.bo.Utilisateur;
import fr.eni.site.dal.AdresseDAO;
import fr.eni.site.dal.ArticleAVendreDAO;
import fr.eni.site.dal.UtilisateurDAO;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {
	public static final String SQL_SELECT = "SELECT pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse FROM UTILISATEURS WHERE pseudo = :pseudo";
	private static final String SQL_INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse) VALUES (:pseudo, :nom, :prenom, :email, :telephone, :mot_de_passe, :credit, :administrateur, :no_adresse)";
	private static final String SQL_SELECT_BY_PSEUDO = "SELECT pseudo FROM UTILISATEURS WHERE pseudo = :pseudo";
	private static final String SQL_SELECT_ALL = "SELECT pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse FROM UTILISATEURS";

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final AdresseDAO adresseDAO;
	private final ArticleAVendreDAO articleDAO;

	public UtilisateurDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, AdresseDAO adresseDAO, @Lazy ArticleAVendreDAO articleDAO) {
		this.jdbcTemplate = jdbcTemplate;
		this.adresseDAO = adresseDAO;
		this.articleDAO = articleDAO;
	}

	@Override
	public void create(Utilisateur utilisateur) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("pseudo", utilisateur.getPseudo());
		params.addValue("nom", utilisateur.getNom());
		params.addValue("prenom", utilisateur.getPrenom());
		params.addValue("email", utilisateur.getEmail());
		params.addValue("telephone", utilisateur.getTelephone());
		params.addValue("mot_de_passe", utilisateur.getMotDePasse());
		params.addValue("credit", utilisateur.getCredit());
		params.addValue("administrateur", utilisateur.isAdministrateur());
		params.addValue("no_adresse", utilisateur.getAdresse().getId());
		jdbcTemplate.update(SQL_INSERT, params);
	}

	@Override
	public Utilisateur read(String pseudo) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("pseudo", pseudo);
		try {
			return jdbcTemplate.queryForObject(SQL_SELECT_BY_PSEUDO, params, new UtilisateurRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public String readByPseudo(String pseudo) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("pseudo", pseudo);
		try {
			return jdbcTemplate.queryForObject(SQL_SELECT_BY_PSEUDO, params, String.class);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Utilisateur> findAll() {
		return jdbcTemplate.query(SQL_SELECT_ALL, new UtilisateurRowMapper());
	}

	private class UtilisateurRowMapper implements RowMapper<Utilisateur> {
		@Override
		public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
			List<ArticleAVendre> articles = articleDAO.findByUtilisateur(rs.getString("pseudo"));
			Adresse adresse = new Adresse();
			adresse.setId(rs.getLong("no_adresse"));
			return new Utilisateur(
					rs.getString("pseudo"),
					rs.getString("nom"),
					rs.getString("prenom"),
					rs.getString("email"),
					rs.getString("telephone"),
					rs.getString("mot_de_passe"),
					rs.getInt("credit"),
					rs.getBoolean("administrateur"),
					adresse,
					articles
			);
		}
	}
}

