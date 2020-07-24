package com.example.servicito.domains.home.controllers.web;//package com.example.servicito.domains.home.controllers.web;
//
//import com.example.auth.config.security.WebSecurityConfig;
//import com.example.servicito.domains.statistics.services.StatsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.io.IOException;
//
//@Controller
//public class AdminHomeController {
//    private final StatsService statsService;
//
//    @Autowired
//    public AdminHomeController(StatsService statsService) {
//        this.statsService = statsService;
//    }
//
////    @GetMapping("")
////    @ResponseBody
////    private String index() {
////        return "Hello world";
////    }
//
//    @GetMapping("")
//    private String index() throws IOException, InterruptedException {
//        return "redirect:/login";
////        return "material/fragments/dashboard/dashboard";
//    }
//
//    @GetMapping("/login")
//    private String loginPage() {
//        if (WebSecurityConfig.isAuthenticated())
//            return "redirect:/admin/dashboard";
//        return "material/pages/login";
//    }
//
//    @GetMapping("/admin/dashboard")
//    private String dashboard(@RequestParam(value = "period", defaultValue = "this_month") String period,
//                             Model model) {
//        /*
//        Temporarily redirect to buildings for now
//         */
//        return "redirect:/admin/buildings";
//
////        AdminStats stats = this.statsService.getStatsInAPeriod(DateUtil.getPeriod(period));
////        model.addAttribute("stats", stats);
////        return "adminlte/fragments/dashboard/dashboard";
//    }
//}
