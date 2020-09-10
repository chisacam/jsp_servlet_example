package middle_test.ex20173081;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ViewServlet
 */
@WebServlet("/viewStudent")
public class ViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// dao 선언(db에서 현재 저장된 학생 리스트를 가져오기 위함)
		StudentDAO dao = new StudentDAO();
		PrintWriter out = response.getWriter();
		// dao를 통해 학생리스트 가져옴
		List<StudentVO> list = dao.listMembers();
		// out으로 결과를 응답(html + 학생 리스트 데이터)
		out.print("<html><body>");
		out.print("<table border = 1>");
		out.print("<tr align = 'center' bgcolor = 'lightgreen'>");
		out.print("<td> 학번 </td>");
		out.print("<td> 비밀번호 </td>");
		out.print("<td> 이름 </td>");
		out.print("<td> 이메일 </td>");
		out.print("<td> 학과 </td>");
		out.print("<td> 가입일 </td>");
		out.print("<td> 삭제여부 </td>");
		// list이므로 list 전체의 데이터를 한줄씩 출력
		for(int i = 0; i < list.size(); i++) {
			StudentVO studentVO = (StudentVO)list.get(i);
			String snum = studentVO.getSnum();
			String pwd = studentVO.getPwd();
			String name = studentVO.getName();
			String email = studentVO.getEmail();
			String department = studentVO.getDepartment();
			Date joinDate = studentVO.getJoinDate();
			
			out.print("<tr><td>" + snum + "</td><td>" + pwd + "</td><td>" + name + "</td><td>"
					+ email + "</td><td>" + department +"</td><td>" + joinDate + "</td><td>" + 
					"<a href='/20173081_ijw/students?command=delStudent&snum=" + snum +"'> 삭제</a></td></tr>");
		}// href로 각 버튼을 눌렀을때 호출할 주소를 지정함
		out.print("</table></body></html>");
		out.print("<a href='/20173081_ijw/studentForm.html'>새 회원 등록하기</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
