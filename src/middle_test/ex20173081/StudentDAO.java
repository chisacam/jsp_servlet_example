package middle_test.ex20173081;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class StudentDAO{

	private Connection conn;
	private PreparedStatement pstmt;
	private DataSource dataFactory;

	public StudentDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		}  catch(Exception e) {
			e.printStackTrace();
		} // DataSource를 이용해서 좀더 호율높은 DB통신
	}
	public List<StudentVO> listMembers() {
		List<StudentVO> list = new ArrayList<StudentVO>();
		try { // db에 접속해서 모든 학생정보를 가져와 VO리스트에 담고 리스트 반환
			conn = dataFactory.getConnection();
			String query = "SELECT * FROM students";
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery(query);
			
			while(rs.next()) {
				String snum = rs.getString("snum");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String department = rs.getString("department");
				Date joinDate = rs.getDate("joinDate");
				
				StudentVO vo = new StudentVO();
				vo.setSnum(snum);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setDepartment(department);
				vo.setJoinDate(joinDate);
				list.add(vo);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void addMember(StudentVO studentVO) {
		try { // 폼으로 부터 받은 정보를 db에 입력
			conn = dataFactory.getConnection();
			String snum = studentVO.getSnum();
			String pwd = studentVO.getPwd();
			String name = studentVO.getName();
			String email = studentVO.getEmail();
			String department = studentVO.getDepartment();
			String query = "INSERT INTO students";
			query += "(snum, pwd, name, email, department)";
			query += "VALUES(?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, snum);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.setString(5, department);
			pstmt.executeUpdate();
			pstmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delMember(String snum) {
		try { // 버튼으로부터 받은 학번을 이용해 삭제
			conn =dataFactory.getConnection();
			String query = "DELETE FROM students";
			query += " WHERE snum = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, snum);
			pstmt.executeUpdate();
			pstmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
