package fr.eni.site.dal;

import fr.eni.site.bo.Adresse;
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
public class AdresseDAOImpl implements AdresseDAO {
	private static final String SQL_INSERT = "INSERT INTO ADRESSES (rue, code_postal, ville, adresse_eni) VALUES (:rue, :code_postal, :ville, :adresse_eni)";
	private static final String SQL_SELECT_BY_ID = "SELECT no_adresse, rue, code_postal, ville, adresse_eni FROM ADRESSES WHERE no_adresse = :id";
	private static final String SQL_SELECT_ALL = "SELECT no_adresse, rue, code_postal, ville, adresse_eni FROM ADRESSES";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public AdresseDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public long create(Adresse adresse) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("rue", adresse.getRue());
		params.addValue("code_postal", adresse.getCodePostal());
		params.addValue("ville", adresse.getVille());
		params.addValue("adresse_eni", adresse.isAdresseEni());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(SQL_INSERT, params, keyHolder, new String[] {"no_adresse"});

		return keyHolder.getKey().longValue();
	}


	@Override
	public Adresse read(long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, params, new AdresseRowMapper());
	}

	@Override
	public List<Adresse> findAll() {
		return jdbcTemplate.query(SQL_SELECT_ALL, new AdresseRowMapper());
	}

	private static class AdresseRowMapper implements RowMapper<Adresse> {
		@Override
		public Adresse mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Adresse(
					rs.getLong("no_adresse"),
					rs.getString("rue"),
					rs.getString("code_postal"),
					rs.getString("ville")
			);
		}
	}
}

