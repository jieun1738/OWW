package oww.banking.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalModelAdvice {

    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpSession session) {
        // 세션에서 가져와서 모델에 추가
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userEmail", session.getAttribute("userEmail"));
        model.addAttribute("totalAssets", session.getAttribute("totalAssets"));
        model.addAttribute("safeboxBalance", session.getAttribute("safeboxBalance"));
        model.addAttribute("goalPercent", session.getAttribute("goalPercent"));
    }
}
