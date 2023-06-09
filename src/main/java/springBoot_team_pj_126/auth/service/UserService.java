package springBoot_team_pj_126.auth.service;

import java.nio.CharBuffer;
import java.util.Optional;

import org.apache.ibatis.javassist.expr.Instanceof;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import springBoot_team_pj_126.auth.dto.CredentialsDTO;
import springBoot_team_pj_126.auth.dto.SignDTO;
import springBoot_team_pj_126.auth.dto.UserDTO;
import springBoot_team_pj_126.auth.entities.User;
import springBoot_team_pj_126.auth.exception.AppException;
import springBoot_team_pj_126.dao.UserMapper;
import springBoot_team_pj_126.dao.UserRepository;

//로그인 및 회원가입 처리를 한다.
@RequiredArgsConstructor
@Service
public class UserService {

	
	
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	//private final PasswrodConfig passwordEncoder;
	
	public UserDTO findById(String id) {
		
		
	
		System.out.println("UserService - findById");
		
		
		 User user = userMapper.findById(id);
//		User user = userRepository.findById(id)
//				.orElseThrow(()-> new AppException("UnKnown user" , HttpStatus.NOT_FOUND));
//		
		return userMapper.toUserDTO(user);
	}
	
	public User login(CredentialsDTO credentialsDTO) {
	      
	      System.out.println("<<< UserService - login >>>");
	  	System.out.println(credentialsDTO.getId());
	 	System.out.println(credentialsDTO.getPwd());
		System.out.println(credentialsDTO.getId() instanceof String);
		
	

	      User user = userMapper.findById(credentialsDTO.getId());
	      System.out.println("user" + user);
		  // User user = userRepository.findById(credentialsDTO.getId())
	       //    .orElseThrow(() -> new AppException("UnKnown user", HttpStatus.NOT_FOUND));
	      
	      // 비밀번호 인코더를 사용하여 비밀번호가 일반 텍스트로 저장되는 것을 방지하지만 해시된 비밀번호는 읽을수없다.
	      //import java.nio.CharBuffer;
	      if (passwordEncoder.matches(CharBuffer.wrap(credentialsDTO.getPwd()), user.getPwd())) {
	         // return userMapper.toUserDTO(user);
	         return user;
	      }
	      throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
	   }
	   
	
	
	
	
	
	
	   public User register(SignDTO userDTO) {
	      
	      System.out.println("<<< UserService - register >>>");
	      System.out.println("id : " + userDTO.getId());
	      System.out.println("Password : " + userDTO.getPwd());
	      System.out.println("name :" + userDTO.getName());
	      System.out.println("birth : " + userDTO.getBirth());
	      System.out.println("hp : " + userDTO.getHp());
	      System.out.println("email : "+ userDTO.getEmail());
	      System.out.println("address : " + userDTO.getAddress());
	      
	      //import java.util.Optional;
	     // Optional<User> optionalUser = userRepository.findById(userDTO.getId());
	 
	     Optional<User> optionalUser = userMapper.selectId(userDTO.getId());
	      System.out.println("optionalUser : "  + optionalUser);
	      
	      
	      
	      if(optionalUser.isPresent()) {
	         throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
	      }
	      System.out.println("악");
	      // User user = userMapper.signUpToUser(userDTO);
	      User user = new User();
	     // user.setNo(userDTO.getNo());
	      user.setId(userDTO.getId());   // username
	      user.setPwd(userDTO.getPwd());
	      user.setName(userDTO.getName());
	      user.setBirth(userDTO.getBirth());
	      user.setEmail(userDTO.getEmail());
	      user.setAddress(userDTO.getAddress());
	      user.setHp(userDTO.getHp());
	  
	      // passwordEncoder를 사용하여 암호를 일반 텍스트로 저장하지 않고 해시한다.
	      user.setPwd(passwordEncoder.encode(CharBuffer.wrap(userDTO.getPwd())));
	      
	      System.out.println(" user.setPassword : "+  user.getPwd());
	      
	      
	      //User savedUser = userRepository.save(user);
	      User savedUser = userMapper.join(user);
	      // return userMapper.toUserDTO(user);
	      return savedUser;
	   }
}
