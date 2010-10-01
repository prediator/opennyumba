package org.openhouse.api.database.liquibase.customchanges;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import liquibase.FileOpener;
import liquibase.change.custom.CustomSqlChange;
import liquibase.database.Database;
import liquibase.database.sql.RawSqlStatement;
import liquibase.database.sql.SqlStatement;
import liquibase.exception.CustomChangeException;
import liquibase.exception.InvalidChangeDefinitionException;
import liquibase.exception.SetupException;
import liquibase.exception.UnsupportedChangeException;

/**
 * Manages liquibase changeset [permissions_change_id_021] for mapping the
 * administrator role to the administrator user
 * 
 * Look at changeset permissions_change_id_021 in liquibase-update-to-latest.xml
 * 
 * @author Samuel Mbugua
 */
public class AdminRoleMapToUser implements CustomSqlChange {

	private int userId = 0;
	private int roleId = 0;

	private String tableName = "user_role";

	private FileOpener fileOpener;

	private String userName = "admin";
	private String roleName = "Role_Administrator";

	public SqlStatement[] fileOpener(Database database)
			throws UnsupportedChangeException, CustomChangeException,
			ClassNotFoundException {

		Connection conn = null;
		try {

			loadDBDriver();
			conn = processConnection();

			userId = processUserId(conn);
			roleId = processRoleId(conn);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		if (roleId == 0 || userId == 0)
			return null;

		return new SqlStatement[] { new RawSqlStatement("Insert Into "
				+ tableName + "(`user_id`, `role_id`)" + "Values(" + userId
				+ "," + roleId + ")") };
	}

	public SqlStatement[] generateStatements(Database arg0)
			throws UnsupportedChangeException, CustomChangeException {

		Connection conn = null;
		try {

			loadDBDriver();
			conn = processConnection();

			userId = processUserId(conn);
			roleId = processRoleId(conn);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		if (roleId == 0 || userId == 0)
			return null;

		return new SqlStatement[] { new RawSqlStatement("Insert Into "
				+ tableName + "(`user_id`, `role_id`)" + "Values(" + userId
				+ "," + roleId + ")") };
	}

	private Connection processConnection() throws SQLException {
		Connection conn;

		Properties appSettings = org.openhouse.api.context.Context
				.getRuntimeProperties();

		String dbUsername = appSettings
				.getProperty("hibernate.connection.username");
		String password = appSettings
				.getProperty("hibernate.connection.password");
		String url = appSettings.getProperty("hibernate.connection.url");

		conn = DriverManager.getConnection(url, dbUsername, password);

		return conn;
	}

	private int processRoleId(Connection conn) throws SQLException {

		int id = 0;

		java.sql.Statement roleStatement = conn.createStatement();
		java.sql.ResultSet roleResultSet = roleStatement
				.executeQuery("SELECT role_id FROM role WHERE name LIKE '"
						+ roleName + "'");

		if (roleResultSet.first())
			id = roleResultSet.getInt("role_id");

		return id;
	}

	private int processUserId(Connection conn) throws SQLException {

		int id = 0;

		java.sql.Statement statement = conn.createStatement();
		java.sql.ResultSet userResultSet = statement
				.executeQuery("SELECT user_id FROM users WHERE user_name LIKE '"
						+ userName + "'");

		if (userResultSet.first())
			id = userResultSet.getInt("user_id");

		return id;
	}

	public String getConfirmationMessage() {
		return " {Mbugua - Custom Id=C001} Custom class updated " + tableName
				+ "By Samuel";

	}

	public void setFileOpener(FileOpener fileOpener) {

		if (this.fileOpener != null && fileOpener != null)
			this.fileOpener = fileOpener;

	}

	public void setUp() throws SetupException {
	}

	public void validate(Database database)
			throws InvalidChangeDefinitionException {
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	/**
	 * Loads the JDBC Driver
	 * 
	 * @throws Throwable
	 */
	private void loadDBDriver() throws Throwable {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
}
