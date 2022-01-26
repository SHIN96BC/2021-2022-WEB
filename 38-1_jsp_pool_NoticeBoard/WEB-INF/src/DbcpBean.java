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
			ds = (DataSource)envContext.lookup("jdbc/myoracle");  // DataSource 이게 우리가 알고있는 pool이다.
			//Connection conn = ds.getConnection();
		}catch(NamingException net){
			System.out.println("#tomcat이 만든 dbcp객체(jdbc/myoracle)이름을 못찾음");
		}
	}
	public DataSource getDs(){
		return ds;
	}
}
