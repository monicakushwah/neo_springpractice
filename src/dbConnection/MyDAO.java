package dbConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

@Component
public class MyDAO {
	// @Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;// = new JdbcTemplate();
	private NamedParameterJdbcTemplate nameParaJdbcTemp;

	/*
	 * it is deprecated to declare both kinds of templates. hence use simple jdbc
	 * template. used for 1.5 or above jdks. par ye to idhar ulta dikha raha hai....kya fart hai bc!
	 */
	private SimpleJdbcTemplate smpleJdbcTemp;
	
	
	public NamedParameterJdbcTemplate getNameParaJdbcTemp() {
		return nameParaJdbcTemp;
	}

	public void setNameParaJdbcTemp(NamedParameterJdbcTemplate nameParaJdbcTemp) {
		this.nameParaJdbcTemp = nameParaJdbcTemp;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		// normally, you use only either jdbc temp or named para jdbc temp. but
		// since the coding is only an example, let both remain.
		this.nameParaJdbcTemp = new NamedParameterJdbcTemplate(dataSource);
	}

	public Circle getCircle(int circleID) {
		Circle circle = null;
		try {
			Connection con = dataSource.getConnection();

			PreparedStatement pst = con
					.prepareStatement("Select * from circle where circleID = ?");
			pst.setInt(1, circleID);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				circle = new Circle(circleID, rs.getString("name"));
			}

			rs.close();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return circle;

	}

	public int getCircleCount() {
		String sql = "Select * from circle";
		// jdbcTemplate.setDataSource(getDataSource());
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		System.out.println(list.get(0));
		return list.size();
	}

	public List<Circle> getCircles(int circleID) {

		String sql = "Select * from circle where circleID = ?";
		return jdbcTemplate.query(sql, new Object[] { circleID },
				new CircleMapper());

	}

	public List<Circle> getAllCircles() {

		String sql = "Select * from circle";
		return jdbcTemplate.query(sql, new CircleMapper());

	}

	public void insert(int id, String name) {
		String sql = "insert into Circle (circleID, name) values (?,?)";
		jdbcTemplate.update(sql, new Object[] { id, name });
	}

	public void insert2(int id, String name) {
		String sql = "insert into Circle (circleID, name) values (:circleID,:name)";
		// sql param is an interface, map sql param is one of its
		// implementations
		// works only for one parameter... only one place holder.
		// SqlParameterSource namedPara = new MapSqlParameterSource("id", id);
		SqlParameterSource namedPara = new MapSqlParameterSource("circleID", id)
				.addValue("name", name);
		nameParaJdbcTemp.update(sql, namedPara);
	}

	public void createTriangleTable() {
		String sql = "create table triangle (id integer not null, name varchar(50))";
		jdbcTemplate.execute(sql);
	}

	private static final class CircleMapper implements RowMapper<Circle> {

		@Override
		public Circle mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Circle circle = new Circle();
			circle.setId(rs.getInt("circleID"));
			circle.setName(rs.getString("name"));

			return circle;
		}

	}

}
