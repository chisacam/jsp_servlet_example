package middle_test.ex20173081;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/students")
public class StudentServlet extends HttpServlet {

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doHandle(request, response);
	}

	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		StudentDAO dao = new StudentDAO();
		
		String command = request.getParameter("command");
		//command가 addStudent면 폼에서 넘겨받은 정보를 dao를 통해 db에 입력
		if(command != null && command.equals("addStudent")) {
			String snum = request.getParameter("snum");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String department = request.getParameter("department");
			StudentVO vo = new StudentVO();
			
			vo.setSnum(snum);
			vo.setPwd(pwd);
			vo.setName(name);
			vo.setEmail(email);
			vo.setDepartment(department);
			dao.addMember(vo);
		} else if(command != null && command.equals("delStudent")) {
			String snum = request.getParameter("snum");
			dao.delMember(snum);
		}// command 인자가 delStudent면 snum에 해당하는 학생정보 삭제
		
	      RequestDispatcher dispatch = request.getRequestDispatcher("viewStudent");
	      dispatch.forward(request, response); // db에 요청하는 작업이 끝나면 결과를 보여주기 위해 리스트를 보여주는 view로 리다이렉트
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
}
