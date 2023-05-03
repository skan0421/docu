package springBoot_team_pj_126.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import springBoot_team_pj_126.dto.MemberDTO;
import springBoot_team_pj_126.dto.UserDTO;


public interface MemberService {

	public List<UserDTO> listAll(HttpServletRequest req, Model model) 
			throws ServletException, IOException;

	
//	public void insertMember(MemberDTO dto) throws ServletException, IOException;
//	
	public void editEmployee(UserDTO dto) throws ServletException, IOException;
//	
	public void deleteMember(int id) throws ServletException, IOException;
//	
//	public MemberDTO selectMember(MemberDTO dto) throws ServletException, IOException;
	
	
	
}
