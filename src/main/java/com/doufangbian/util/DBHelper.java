package com.doufangbian.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.ResultSetMetaData;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {


//	private static final String URL = "jdbc:mysql://sh6682805.gotoftp3.com/sh6682805?useUnicode=true&characterEncoding=utf-8";
//	private static final String USER = "sh6682805";
//	private static final String PWD = "doufangbian2013";

/*    // 定义常量驱动
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    // 创建连接池
    private static ComboPooledDataSource ds = new ComboPooledDataSource();

    private static final String USER = "web_test";// 定义常量用户名
    private static final String PWD = "aaa123";// 定义常量密码
    private static final String URL = "jdbc:mysql://localhost:3306/doufangbian?useUnicode=true&characterEncoding=utf-8";// 定义url


    static {

        try {
            // 设置次c3p0连接池的连接信息

            // 设置驱动driver
            ds.setDriverClass(DRIVER_CLASS);

            // 设置url
            ds.setJdbcUrl(URL);

            // 设置数据库链接用户
            ds.setUser(USER);

            // 设置数据库连接密码
            ds.setPassword(PWD);

            // 初始化连接数
            ds.setInitialPoolSize(2);

            // 设置连接池中保留的最大连接数
            ds.setMaxPoolSize(5);

            // 设置最大空闲时间
            ds.setMaxIdleTime(8000);

            // 设置连接池保留的最小连接数
            ds.setMinPoolSize(1);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }*/


    /**
     * 获取连接
     *
     * @return
     */
    public static Connection getConn() {

        try {
//            return ds.getConnection();
            return SpringUtil.getBean("dataSource", DataSource.class).getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 关闭资源
     *
     * @return
     */
    public static void closeConn(Connection conn, ResultSet rs, PreparedStatement pstmt) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }

        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }

    }

    /**
     * 通用增删改
     *
     * @param sql
     * @param params
     * @return
     */
    public static boolean commonUpdate(String sql, Object... params) {

        Connection con = DBHelper.getConn();// 获取数据库连接

        PreparedStatement pstmt = null;// 创建操作数据库对象

        try {
            pstmt = con.prepareStatement(sql);

            if (params != null) {// 判断是否有参数

                for (int i = 0; i < params.length; i++) {

                    pstmt.setObject(i + 1, params[i]);
                }
            }
            // 是否执行成功
            if (pstmt.executeUpdate() > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeConn(con, null, pstmt);
        }

        return false;
    }

    /**
     * 利用反射通用查询
     *
     * @param sql
     * @param cls
     * @param params
     * @return
     */
    public static List commQuery(String sql, Class cls, Object... params) {

        Connection conn = getConn();
        PreparedStatement pstmt = null;// 创建操作数据库对象
        ResultSet rs = null;// 创建结果集对象

        List list = new ArrayList();

        try {

            pstmt = conn.prepareStatement(sql);
            // 判断是否传有参数
            if (params != null) {
                for (int i = 0; i < params.length; i++) { // 循环添加参数

                    pstmt.setObject(i + 1, params[i]);

                }
            }
            rs = pstmt.executeQuery();// 执行查询操作

            // 获取用户查询的列
            ResultSetMetaData rsMeta = (ResultSetMetaData) rs.getMetaData();

            // 获取列数
            int count = rsMeta.getColumnCount();

            while (rs.next()) {

                // 创建对象
                Object obj = cls.newInstance();

                // 获取类中的所有属性
                Field fds[] = cls.getDeclaredFields();

                // 给属性赋值
                for (Field f : fds) {

                    // 强制赋值
                    f.setAccessible(true);

                    // 获得属性名
                    String propName = f.getName();

                    // 获取属性的数据类型
                    String typeName = f.getType().getName();

                    // 跟所有的列来比较
                    for (int i = 0; i < count; i++) {
                        // rsMeta.getColumnName(i+1) 表获得列名
                        if (propName.equals(rsMeta.getColumnName(i + 1))) {// 当属性名和列名相同的时候给属性赋值

                            // 数据类型为int或Integer
                            if (typeName.endsWith("int")
                                    || typeName.endsWith("java.lang.Integer")) {

                                f.setInt(obj, rs.getInt(propName));
                                // 数据类型为String
                            } else if (typeName.endsWith("String")
                                    || typeName.endsWith("java.lang.String")) {

                                f.set(obj, rs.getString(propName));
                                // 数据类型为double
                            } else if (typeName.endsWith("double")
                                    || typeName.endsWith("java.lang.Double")) {

                                f.setDouble(obj, rs.getDouble(propName));
                                // 数据类型为float
                            } else if (typeName.endsWith("float")
                                    || typeName.endsWith("java.lang.Float")) {

                                f.setFloat(obj, rs.getFloat(propName));
                                // 其他类型
                            } else {

                                System.out.println("暂不支持该转换类型！");
                            }

                        }
                    }
                }

                // 将对象存放在集合中
                list.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, rs, pstmt);

        }

        return list;
    }

    /**
     * 通用查询记录数的方法
     *
     * @param sql    要执行的sql
     * @param object
     * @return 返回第一行的第一列
     */
    public static int commonQueryCount(String sql, Object... object) {

        // 定义连接对象
        Connection conn = null;

        // 定义预编译对象
        PreparedStatement pst = null;

        // 定义结果集对象
        ResultSet rs = null;
        try {

            // 获取数据库连接
            conn = DBHelper.getConn();

            // 得到预编译对象
            pst = conn.prepareStatement(sql);

            // 给sql中的参数赋值
            if (object != null) {

                for (int i = 0; i < object.length; i++) {
                    pst.setObject(i + 1, object[i]);
                }
            }
            // 得到sql查询的结果集
            rs = pst.executeQuery();
            // 读取结果集
            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            DBHelper.closeConn(conn, rs, pst);
        }
        return 0;
    }

    /**
     * 查询图片路劲
     *
     * @param sql
     * @param params
     * @return
     */
    public static List<String> queryImageName(String sql, Object... params) {

        Connection conn = getConn();

        PreparedStatement pstmt = null;// 创建操作数据库对象

        ResultSet rs = null;// 创建结果集对象

        List<String> list = new ArrayList<String>();

        try {
            pstmt = conn.prepareStatement(sql);
            // 判断是否传有参数
            if (params != null) {
                for (int i = 0; i < params.length; i++) { // 循环添加参数

                    pstmt.setObject(i + 1, params[i]);

                }
            }
            rs = pstmt.executeQuery();// 执行查询操作

            while (rs.next()) {// 读取数据

                String name = rs.getString(1);

                list.add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }


}
