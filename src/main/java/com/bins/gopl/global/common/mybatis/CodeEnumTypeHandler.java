package com.bins.gopl.global.common.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * {@link CodeEnum}인터페이스를 구현화한 {@link Enum}클래스 내에 정의된 코드값을 데이터베이스에 저장하거나<br>
 * 데이터베이스로부터 가져온 코드값을 알맞은 {@link Enum}으로 변환 후 반환하는 역할을 수행하는 타입 핸들러 클래스
 *
 * @author 박 수 빈
 * @version 1.0.0
 */

@MappedTypes(CodeEnum.class)
public class CodeEnumTypeHandler<E extends Enum<E> & CodeEnum> extends BaseTypeHandler<E> {
    /**
     * 타입 핸들러를 통해 데이터베이스 처리를 하고자 하는 {@link Enum}클래스
     */
    private final Class<E> type;

    /**
     * 타입 핸들러를 통해 데이터베이스 처리를 하고자 하는 {@link Enum}클래스에 정의된 상수들로 이루어진 배열
     */
    private final E[] constants;

    public CodeEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null.");
        }
        this.type = type;
        this.constants = type.getEnumConstants();
        if (!type.isInterface() && this.constants == null) {
            throw new IllegalArgumentException("'" + type.getSimpleName() + "' does not represent an enum type.");
        }
    }

    /**
     * 파라미터로 전달된 {@link Enum}의 코드 값을 {@link PreparedStatement}을 통해 SQL 쿼리에 바인딩 처리를 수행한다.
     *
     *
     * @param ps            SQL 쿼리를 실행하는 {@link PreparedStatement} 객체
     * @param i             파라미터의 인덱스 위치
     * @param parameter     바인딩할 {@link Enum} 값
     * @param jdbcType      {@link Enum}에 해당되는 JDBC 타입
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    /**
     * SQL 쿼리 결과에서 특정 컬럼의 값을 가져온 후, 그 값을 {@link Enum}으로 변환하며<br>
     * 이 때 컬럼의 값이 {@code null}일 경우 {@code null}을 반환한다.
     *
     * @param rs            SQL 쿼리 결과 저장 객체
     * @param columnName    코드 값의 컬럼명
     * @return              {@code null} 또는 가져온 코드값을 토대로 변환한 {@link Enum}
     * @throws SQLException
     */
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.wasNull() ? null : convertColumnToCodeEnum(rs.getString(columnName));
    }

    /**
     * SQL 쿼리 결과에서 특정 인덱스에 위치한 값을 가져온 후, 그 값을 {@link Enum}으로 변환하며<br>
     * 이 때 컬럼의 값이 {@code null}일 경우 {@code null}을 반환한다.
     *
     * @param rs            SQL 쿼리 결과 저장 객체
     * @param columnIndex   코드 값의 인덱스
     * @return              {@code null} 또는 가져온 코드값을 토대로 변환한 {@link Enum}
     * @throws SQLException
     */
    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.wasNull() ? null : convertColumnToCodeEnum(rs.getString(columnIndex));
    }

    /**
     * SQL 저장 프로시저에서 특정 인덱스에 해당되는 값을 가져온 후, 그 값을 {@link Enum}으로 변환하며<br>
     * 이 때 컬럼의 값이 {@code null}일 경우 {@code null}을 반환한다.
     *
     * @param cs            SQL 저장 프로시저 호출시 사용되는 인터페이스
     * @param columnIndex   값을 조회할 컬럼의 인덱스
     * @return              {@code null} 또는 가져온 코드값을 토대로 변환한 {@link Enum}
     * @throws SQLException
     */
    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.wasNull() ? null : convertColumnToCodeEnum(cs.getString(columnIndex));
    }

    /**
     * {@link Enum}클래스 내의 상수값들 중 데이터베이스로부터 가져온 코드값과 일치하는 상수값이 존재할 경우 해당 {@link Enum}의 인스턴스를 반환한다.
     *
     * @param code  데이터베이스로부터 가져온 코드값
     * @return      데이터베이스의 코드값과 일치한 {@link Enum}의 인스턴스
     */
    private E convertColumnToCodeEnum(String code) {
        return Arrays.stream(constants)
                .filter(e -> e.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot convert '" + code + "' to '" + type.getSimpleName() + "."));
    }
}