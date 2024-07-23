package fr.eni.site.dal;

import fr.eni.site.bo.Adresse;
import fr.eni.site.bo.ArticleAVendre;
import fr.eni.site.bo.Utilisateur;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UtilisateurDAOImpl implements UtilisateurDAO {
    public static final String SQL_SELECT = "SELECT * FROM UTILISATEURS WHERE pseudo = :pseudo";
    private static final String SQL_INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse) VALUES (:pseudo, :nom, :prenom, :email, :telephone, :mot_de_passe, :credit, :administrateur, :no_adresse)";
    private static final String SQL_SELECT_BY_PSEUDO = "SELECT * FROM UTILISATEURS WHERE pseudo = :pseudo";
    private static final String SQL_SELECT_ALL = "SELECT * FROM UTILISATEURS";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final AdresseDAOImpl adresseDAO;
    private final ArticleAVendreDAOImpl articleDAO;

    public UtilisateurDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, AdresseDAOImpl adresseDAO, ArticleAVendreDAOImpl articleDAO) {
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
        return jdbcTemplate.queryForObject(SQL_SELECT, params, new UtilisateurRowMapper());
    }

    @Override
    public Utilisateur readByPseudo(String pseudo) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pseudo", pseudo);
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_PSEUDO, params, new UtilisateurRowMapper());
    }

    @Override
    public List<Utilisateur> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, new UtilisateurRowMapper());
    }

    private class UtilisateurRowMapper implements RowMapper<Utilisateur> {
        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
            long adresseId = rs.getLong("no_adresse");
            Adresse adresse = adresseDAO.read(adresseId);
            List<ArticleAVendre> articles = articleDAO.findByUtilisateur(rs.getString("pseudo"));

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

