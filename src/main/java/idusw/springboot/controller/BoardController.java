package idusw.springboot.controller;

import idusw.springboot.domain.Board;
import idusw.springboot.domain.Member;
import idusw.springboot.domain.PageRequestDTO;
import idusw.springboot.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/reg-form")
    public String getRegForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("mb");
        if (member != null) {
            model.addAttribute("board", Board.builder().build());
            return "/boards/reg-form"; // view
        } else
            return "redirect:/members/login"; // 로그인이 안된 상태인 경우
    }

    @PostMapping("")
    public String postBoard(@ModelAttribute("board") Board dto, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("mb");
        if(member != null) {
            // form에서 hidden 전송하는 방식으로 변경
            dto.setWriterSeq(member.getSeq());
            dto.setWriterEmail(member.getEmail());
            dto.setWriterName(member.getName());

            Long bno = Long.valueOf(boardService.registerBoard(dto));

            return "redirect:/boards"; // 등록 후 목록 보기, redirection, get method
        }else
            return "redirect:/members/login"; // 로그인이 안된 상태인 경우
    }

    @GetMapping("")
    public String getBoards(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("list", boardService.findBoardAll(pageRequestDTO));
        return "boards/list";
    }

    @GetMapping("/{bno}")
    public String getBoardByBno(@PathVariable Long bno, Model model) {
        // Long bno 값을 사용하는 방식을 Board 객체에 bno를 설정하여 사용하는 방식으로 변경
        Board board = boardService.findBoardById(Board.builder().bno(bno).build());
        boardService.updateBoard(board);
        model.addAttribute("board", boardService.findBoardById(board));
        return "/boards/read";
    }

    @GetMapping("/{bno}/up-form")
    public String getUpForm(@PathVariable Long bno, Model model) {
        Board board = boardService.findBoardById(Board.builder().bno(bno).build());
        model.addAttribute("board", board);
        return "/boards/upform";
    }

    @PutMapping("/{bno}")
    public String putBoard(@ModelAttribute Board board, Model model) {
        boardService.updateBoard(board);
        model.addAttribute(boardService.findBoardById(board));
        return "redirect:/boards/" + board.getBno();
    }

    @GetMapping("/{bno}/del-form")
    public String getDelForm(@PathVariable Long bno, Model model) {
        Board board = boardService.findBoardById(Board.builder().bno(bno).build());
        model.addAttribute("board", board);
        return "/boards/del-form";
    }

    @DeleteMapping("/{bno}")
    public String deleteBoard(@ModelAttribute Board board, Model model) {
        boardService.deleteBoard(board);
        model.addAttribute(board);
        return "redirect:/boards";
    }

}
