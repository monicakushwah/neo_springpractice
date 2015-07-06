package dbConnection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class NamedParaSup extends NamedParameterJdbcDaoSupport {
	@Override
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		// TODO Auto-generated method stub
		return super.getNamedParameterJdbcTemplate();
	}

	public int getCircleCount() {
		String sql = "Select * from circle";
		// jdbcTemplate.setDataSource(getDataSource());
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
		System.out.println(list.get(0));
		return list.size();
	}
}
