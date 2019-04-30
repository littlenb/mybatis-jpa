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
@MappedJdbcTypes({JdbcType.INTEGER, JdbcType.TINYINT})
@MappedTypes({ICodeEnum.class})
public class IntCodeEnumTypeHandler<E extends ICodeEnum> extends BaseTypeHandler<E> {

  private Class<E> type;

  public IntCodeEnumTypeHandler(Class<E> type) {
    if (type == null) {
      throw new IllegalArgumentException("Type argument cannot be null");
    }
    this.type = type;
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType)
      throws SQLException {
    if (jdbcType == null) {
      ps.setInt(i, (Integer) parameter.getCode());
    } else {
      ps.setObject(i, parameter.getCode(), jdbcType.TYPE_CODE);
    }
  }

  @Override
  public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
    int i = rs.getInt(columnName);
    if (rs.wasNull()) {
      return null;
    } else {
      return CodeEnumAdapter.toCodeEnum(i, type);
    }
  }

  @Override
  public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    int i = rs.getInt(columnIndex);
    if (rs.wasNull()) {
      return null;
    } else {
      return CodeEnumAdapter.toCodeEnum(i, type);
    }
  }

  @Override
  public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    int i = cs.getInt(columnIndex);
    if (cs.wasNull()) {
      return null;
    } else {
      return CodeEnumAdapter.toCodeEnum(i, type);
    }
  }
}
