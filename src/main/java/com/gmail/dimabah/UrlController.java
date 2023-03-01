package com.gmail.dimabah;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping ("/")
    public String index(@RequestParam(required = false) String shortUrl, Model model){
        var urls = urlService.getAll();
        model.addAttribute("urls",urls);
        model.addAttribute("shortUrl",shortUrl);
        return "index";
    }

    @PostMapping("/shorten")
    public String shorten (@RequestParam String longUrl, RedirectAttributes redirectAttributes){
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setUrl(longUrl);
        long id= urlService.saveUrl(urlDTO);
        String shortUrl = "http://localhost:8080/my/"+id;
        redirectAttributes.addAttribute("shortUrl", shortUrl);
        return "redirect:/";
    }
    @PostMapping("/delete")
    public String delete (@RequestParam long id){
        urlService.delete(id);
        return "redirect:/";
    }

}
