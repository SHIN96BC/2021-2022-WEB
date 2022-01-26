package sbc.dbcp;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;

public class DbcpBean {
	private DataSource ds;
	public DbcpBean(){
		try{
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/myoracle");  // DataSource �̰� �츮�� �˰��ִ� pool�̴�.
			//Connection conn = ds.getConnection();
		}catch(NamingException net){
			System.out.println("#tomcat�� ���� dbcp��ü(jdbc/myoracle)�̸��� ��ã��");
		}
	}
	public DataSource getDs(){
		return ds;
	}
}
