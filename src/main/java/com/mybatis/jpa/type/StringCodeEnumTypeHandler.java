package com.mybatis.jpa.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author sway.li
 **/
@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({ICodeEnum.class})
public class StringCodeEnumTypeHandler<E extends ICodeEnum> extends BaseTypeHandler<E> {

  private Class<E> type;

  public StringCodeEnumTypeHandler(Class<E> type) {
    if (type == null) {
      throw new IllegalArgumentException("Type argument cannot be null");
    }
    this.type = type;
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType)
      throws SQLException {
    if (jdbcType == null) {
      ps.setString(i, (String) parameter.getCode());
    } else {
      ps.setObject(i, parameter.getCode(), jdbcType.TYPE_CODE);
    }
  }

  @Override
  public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String s = rs.getString(columnName);
    return s == null ? null : CodeEnumAdapter.toCodeEnum(s, type);
  }

  @Override
  public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String s = rs.getString(columnIndex);
    return s == null ? null : CodeEnumAdapter.toCodeEnum(s, type);
  }

  @Override
  public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String s = cs.getString(columnIndex);
    return s == null ? null : CodeEnumAdapter.toCodeEnum(s, type);
  }
}
