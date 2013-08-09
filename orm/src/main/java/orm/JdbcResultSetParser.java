package orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public interface JdbcResultSetParser<T> {
    T parse(ResultSet rs, int i);
    
    public static JdbcResultSetParser<Integer> parseInt = new JdbcResultSetParser<Integer>() {
        public Integer parse(ResultSet rs, int i) {
            try {
                return rs.getInt(i);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };
    
    public static JdbcResultSetParser<Double> parseDouble = new JdbcResultSetParser<Double>() {
        public Double parse(ResultSet rs, int i) {
            try {
                return rs.getDouble(i);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };
    
    public static JdbcResultSetParser<Float> parseFloat = new JdbcResultSetParser<Float>() {
        public Float parse(ResultSet rs, int i) {
            try {
                return rs.getFloat(i);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };
    
    public static JdbcResultSetParser<String> parseString = new JdbcResultSetParser<String>() {
        public String parse(ResultSet rs, int i) {
            try {
                return rs.getString(i);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };
    
    public static JdbcResultSetParser<Date> parseDate = new JdbcResultSetParser<Date>() {
        public Date parse(ResultSet rs, int i) {
            try {
                return rs.getDate(i);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };
}
