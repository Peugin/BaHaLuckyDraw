package tw.peugin.baha.luckydraw.controller;

import org.postgresql.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.peugin.baha.luckydraw.entity.Winner;
import tw.peugin.baha.luckydraw.service.IWinnerService;
import tw.peugin.baha.luckydraw.service.WinnerService;

import java.nio.ByteBuffer;
import java.util.List;

@Controller
public class LuckyDrawController {
    @Autowired
    private IWinnerService winnerService;

    @GetMapping(value = {"/","/index"})
    public String mainPage(){
        return "index";
    }

    @GetMapping(value = "/winners/{groupID}")
    public String winners(@PathVariable(value="groupID") String base64GroupID, Model model){
        int gid = Integer.parseInt(new String(Base64.decode(base64GroupID)));
        List<Winner> winners = winnerService.getWinnersByID(gid);

        if(winners.size() <= 0)
            return "redirect:/error";

        model.addAttribute("winners",winners);
        return "winners";
    }
}
