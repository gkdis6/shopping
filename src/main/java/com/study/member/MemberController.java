package com.study.member;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.study.contents.ContentsDTO;
import com.study.utility.Utility;

@Controller
public class MemberController {

	@Autowired
	@Qualifier("com.study.member.MemberServiceImpl")
	private MemberService service;

	@GetMapping("/")
	public String home() {

		return "/home";
	}

	@GetMapping("/member/agree")
	public String agree() {
		return "/member/agree";
	}

	@PostMapping("/member/createForm")
	public String create() {
		return "/member/create";
	}

	@GetMapping("/member/login")
	public String login(HttpServletRequest request) {

		String c_id = "";
		String c_id_val = "";

		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("c_id")) {
					c_id = cookie.getValue();
				} else if (cookie.getName().equals("c_id_val")) {
					c_id_val = cookie.getValue();
				}
			}
		}

		request.setAttribute("c_id", cookie);
		request.setAttribute("c_id_val", c_id_val);

		return "/member/login";
	}

	@PostMapping("/member/login")
	public String login(@RequestParam Map<String, String> map, HttpSession session, HttpServletResponse response,
			Model model) {

		int cnt = service.loginCheck(map);

		if (cnt > 0) {
			String grade = service.getGrade(map.get("id"));
			session.setAttribute("id", map.get("id"));
			session.setAttribute("grade", grade);
			// Cookie ??????,id?????? ?????? ??? id
			Cookie cookie = null;
			String c_id = map.get("c_id");
			if (c_id != null) {
				cookie = new Cookie("c_id", c_id); // c_id=> Y
				cookie.setMaxAge(60 * 60 * 24 * 365);// 1???
				response.addCookie(cookie);// ?????????(client:???????????? ???) ?????? ??????

				cookie = new Cookie("c_id_val", map.get("id"));
				cookie.setMaxAge(60 * 60 * 24 * 365);// 1???
				response.addCookie(cookie);// ?????????(client:???????????? ???) ?????? ??????
			} else {
				cookie = new Cookie("c_id", ""); // ?????? ??????
				cookie.setMaxAge(0);
				response.addCookie(cookie);

				cookie = new Cookie("c_id_val", "");// ?????? ??????
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}

		if (cnt > 0) {
			return "redirect:/";
		} else {
			model.addAttribute("msg", "????????? ?????? ??????????????? ?????? ?????? ????????? <br>????????? ????????????. ???????????? ?????????");
			return "/member/errorMsg";
		}
	}

	@GetMapping("member/logout")
	public String logout(HttpSession session) {
		session.invalidate();

		return "redirect:/";
	}

	@PostMapping("/member/create")
	public String create(MemberDTO dto) throws IOException {
		//String upDir = new ClassPathResource("/static/member/storage").getFile().getAbsolutePath();
		
		String upDir = Member.getUploadDir();
		String fname = Utility.saveFileSpring(dto.getFnameMF(), upDir);
		long size = dto.getFnameMF().getSize();

		if (size > 0) {
			dto.setFname(fname);
		} else {
			dto.setFname("member.jpg");
		}

		if (service.create(dto) > 0) {
			return "redirect:/";
		} else {
			return "error";
		}
	}

	@GetMapping("/member/read")
	public String read(String id, Model model, HttpSession session) {

		if (id == null) {
			id = (String) session.getAttribute("id");
		}

		MemberDTO dto = service.read(id);

		model.addAttribute("dto", dto);

		return "/member/read";
	}

	@GetMapping("member/update")
	public String update(String id, HttpSession session, Model model) {

		if (id == null) {
			id = (String) session.getAttribute("id");
		}

		MemberDTO dto = service.read(id);

		model.addAttribute("dto", dto);

		return "/member/update";
	}

	@PostMapping("/member/update")
	public String update(MemberDTO dto, HttpSession session, Model model) {
		int cnt = service.update(dto);

		String id = dto.getId();
		dto = service.read(id);
		model.addAttribute("dto", dto);
		if (cnt == 1) {
			return "/member/updateFile";
		} else {
			return "error";
		}
	}

	@PostMapping("/member/updateFile")
	public String updateFile(MultipartFile fnameMF, String oldfile, HttpSession session, HttpServletRequest request) throws IOException {
		//String basePath = new ClassPathResource("/static/storage").getFile().getAbsolutePath();
		String basePath = Member.getUploadDir();
        
        if(oldfile !=null  && !oldfile.equals("member.jpg")) { //???????????? ??????
                Utility.deleteFile(basePath, oldfile);
        }
        
        //storage??? ?????? ?????? ??????
        Map map = new HashMap();
        map.put("id", session.getAttribute("id"));
        map.put("fname", Utility.saveFileSpring(fnameMF, basePath));
        
        //????????? ????????? ??????
        int cnt = service.updateFile(map);
        
        return "redirect:./read";
	}
	
	@GetMapping("/member/updateFile")
    public String updateFile(MemberDTO dto, HttpSession session) {
            
            return "/member/updateFile";
    }
	
	@RequestMapping("/admin/list")
    public String list(HttpServletRequest request) {
            // ????????????------------------------
            String col = Utility.checkNull(request.getParameter("col"));
            String word = Utility.checkNull(request.getParameter("word"));

            if (col.equals("total")) {
                    word = "";
            }

            // ???????????????-----------------------
            int nowPage = 1;// ?????? ???????????? ?????????
            if (request.getParameter("nowPage") != null) {
                    nowPage = Integer.parseInt(request.getParameter("nowPage"));
            }
            int recordPerPage = 3;// ??????????????? ????????? ???????????????

            // DB?????? ????????? ??????-----------------
            int sno = ((nowPage - 1) * recordPerPage) + 1;
            int eno = nowPage * recordPerPage;

            Map map = new HashMap();
            map.put("col", col);
            map.put("word", word);
            map.put("sno", sno);
            map.put("eno", eno);

            int total = service.total(map);

            List<MemberDTO> list = service.list(map);

            String paging = Utility.paging(total, nowPage, recordPerPage, col, word);

            // request??? Model?????? ?????? ?????????
            request.setAttribute("list", list);
            request.setAttribute("nowPage", nowPage);
            request.setAttribute("col", col);
            request.setAttribute("word", word);
            request.setAttribute("paging", paging);
            
            return "/member/list";

    }
	
	@GetMapping("/member/download")
    public void download(HttpServletRequest request,
                    HttpServletResponse response) throws IOException {
            // ????????????
            //String dir = new ClassPathResource("/static/member/storage").getFile().getAbsolutePath();
		
            String dir = Member.getUploadDir();
            String filename = request.getParameter("filename");
            byte[] files = FileUtils.readFileToByteArray(new File(dir, filename));
            response.setHeader("Content-disposition",
                            "attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8") + "\";");
            // Content-Transfer-Encoding : ?????? ???????????? body??? ???????????? ????????? ?????????.
            response.setHeader("Content-Transfer-Encoding", "binary");
            /**
             * Content-Disposition??? attachment??? ?????? ?????????????????? 'Save As'??? ????????? ??????????????? ????????? ?????? ???????????????
             * ????????????.
             */
            response.setContentType("application/octet-stream");
            response.setContentLength(files.length);
            response.getOutputStream().write(files);
            response.getOutputStream().flush();
            response.getOutputStream().close();
    }
	
	@GetMapping(value = "/member/idcheck", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, String> idcheck(String id) {

		int cnt = service.duplicatedId(id);
		Map<String, String> map = new HashMap<String, String>();

		if (cnt > 0) {
			map.put("str", id + "??? ??????????????? ????????? ??? ????????????.");
		} else {
			map.put("str", id + "??? ????????????, ???????????? ?????????.");
		}

		return map;
	}

	@GetMapping(value = "/member/emailcheck", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, String> emailcheck(String email) {

		int cnt = service.duplicatedEmail(email);
		Map<String, String> map = new HashMap<String, String>();

		if (cnt > 0) {
			map.put("str", email + "??? ??????????????? ????????? ??? ????????????.");
		} else {
			map.put("str", email + "??? ????????????, ???????????? ?????????.");
		}

		return map;
	}
	
	@GetMapping("/member/mypage")
	public String mypage(HttpSession session, Model model) {
	  String id = (String)session.getAttribute("id");
	 
	  if(id==null) {
	       return "redirect:./login";
	  }else {
	  
	       MemberDTO dto = service.mypage(id);
	      
	       model.addAttribute("dto", dto);
	      
	   return "/member/mypage";
	  }
	}
	
	@GetMapping("/member/delete/{id}")
	public String delete(@PathVariable("id") String id, Model model) {
		MemberDTO dto = service.read(id);
		
		model.addAttribute("id", id);
		model.addAttribute("oldfile", dto.getFname());
		
		return "/member/delete";
	}

	@PostMapping("/member/delete")
	public String delete(HttpServletRequest request, String id, Model model) throws IOException {
		MemberDTO dto = service.read(id);
		String oldfile = dto.getFname();
		
		//String basePath = new ClassPathResource("/static/member/storage").getFile().getAbsolutePath();
		String basePath = Member.getUploadDir();

		if (oldfile != null && !oldfile.equals("default.jpg")) { // ???????????? ??????
			Utility.deleteFile(basePath, oldfile);
		}
		
		service.deleteOrder(id);
		service.deleteCart(id);
		service.delete(id);

		return "redirect:/admin/list";

	}
}