package fr.eni.site.dal.impl;

import fr.eni.site.bo.Categorie;
import fr.eni.site.dal.CategorieDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategorieDAOImpl implements CategorieDAO {
	private static final String SQL_INSERT = "INSERT INTO CATEGORIES (libelle) VALUES (:libelle)";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM CATEGORIES WHERE no_categorie = :id";
	private static final String SQL_SELECT_ALL = "SELECT * FROM CATEGORIES";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public CategorieDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public long create(Categorie categorie) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("libelle", categorie.getLibelle());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(SQL_INSERT, params, keyHolder, new String[]{"no_categorie"});
		return keyHolder.getKey().longValue();
	}

	@Override
	public Categorie read(long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, params, new CategorieRowMapper());
	}

	@Override
	public List<Categorie> findAll() {
		return jdbcTemplate.query(SQL_SELECT_ALL, new CategorieRowMapper());
	}

	private static class CategorieRowMapper implements RowMapper<Categorie> {
		@Override
		public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Categorie(
					rs.getLong("no_categorie"),
					rs.getString("libelle")
			);
		}
	}
}

