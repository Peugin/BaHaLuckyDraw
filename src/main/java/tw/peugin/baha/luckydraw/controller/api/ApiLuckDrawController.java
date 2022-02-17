package tw.peugin.baha.luckydraw.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import tw.peugin.baha.bahaForum.entity.BahaCrawlerData;
import tw.peugin.baha.luckydraw.entity.RawWinnersResponse;
import tw.peugin.baha.luckydraw.entity.Winner;
import tw.peugin.baha.luckydraw.entity.WinnerGroup;
import tw.peugin.baha.luckydraw.exception.BadRequestException;
import tw.peugin.baha.luckydraw.model.DrawWinnersQueryParameter;
import tw.peugin.baha.luckydraw.model.DuplicatePost;
import tw.peugin.baha.luckydraw.service.DrawService;
import tw.peugin.baha.luckydraw.service.IWinnerGroupService;
import tw.peugin.baha.luckydraw.service.IWinnerService;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/draw",produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiLuckDrawController {
    @Autowired
    private IWinnerService winnerService;
    @Autowired
    private DrawService drawService;

    /*@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(RawWinnersResponse.class, new RawWinnersResponseEditorSupport());
    }*/

    @GetMapping("/drawWinners")
    public ResponseEntity<List<Winner>> drawWinners(@Valid @ModelAttribute DrawWinnersQueryParameter parameter) throws IOException, URISyntaxException {
        return ResponseEntity.ok().body(drawService.drawWinners(parameter));
    }

    @GetMapping("/winners")
    public List<Winner> winners(@RequestParam(name="group_id",required = true)  long groupID){
        return winnerService.getWinnersByID(groupID);
    }
}
