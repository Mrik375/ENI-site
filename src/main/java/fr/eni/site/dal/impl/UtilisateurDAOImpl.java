package fr.eni.site.dal.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.site.bo.Adresse;
import fr.eni.site.bo.ArticleAVendre;
import fr.eni.site.bo.Utilisateur;
import fr.eni.site.dal.UtilisateurDAO;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {
	private static final String SQL_INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse) VALUES (:pseudo, :nom, :prenom, :email, :telephone, :mot_de_passe, :credit, :administrateur, :no_adresse)";
	private static final String SQL_SELECT_BY_PSEUDO = "SELECT * FROM UTILISATEURS WHERE pseudo = :pseudo";
	private static final String SQL_SELECT_ALL = "SELECT pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse FROM UTILISATEURS";
	private static final String SQL_EXISTS_BY_PSEUDO = "SELECT CASE WHEN EXISTS (SELECT 1 FROM UTILISATEURS WHERE pseudo = :pseudo) THEN 1 ELSE 0 END AS RowExists";
	private static final String SQL_UPDATE = "UPDATE UTILISATEURS SET nom = :nom, prenom = :prenom, email = :email, telephone = :telephone WHERE pseudo = :pseudo";
	private static final String SQL_UPDATE_PSEUDO = "UPDATE UTILISATEURS SET pseudo= :pseudo WHERE pseudo = :oldPseudo";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public UtilisateurDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void create(Utilisateur utilisateur) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("pseudo", utilisateur.getPseudo());
		params.addValue("nom", utilisateur.getNom());
		params.addValue("prenom", utilisateur.getPrenom());
		params.addValue("email", utilisateur.getEmail());
		params.addValue("telephone", utilisateur.getTelephone());
		params.addValue("mot_de_passe", utilisateur.getPassword());
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
	public boolean exists(String pseudo) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("pseudo", pseudo);
		try {
			Integer rowExists = jdbcTemplate.queryForObject(SQL_EXISTS_BY_PSEUDO, params, Integer.class);
			return rowExists != null && rowExists == 1;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}

	@Override
	public List<Utilisateur> findAll() {
		return jdbcTemplate.query(SQL_SELECT_ALL, new UtilisateurRowMapper());
	}

	private class UtilisateurRowMapper implements RowMapper<Utilisateur> {
		@Override
		public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
			List<ArticleAVendre> articles = new ArrayList<>();
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

	@Override
    public void update(Utilisateur utilisateur) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        
        params.addValue("nom", utilisateur.getNom());
        params.addValue("prenom", utilisateur.getPrenom());
        params.addValue("email", utilisateur.getEmail());
        params.addValue("telephone", utilisateur.getTelephone());

        jdbcTemplate.update(SQL_UPDATE, params);
    }
	
	public void updatePseudo(String pseudo, String oldPseudo) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("pseudo", pseudo);
		params.addValue("oldPseudo", oldPseudo);
		jdbcTemplate.update(SQL_UPDATE_PSEUDO, params);
	}
}