package com.example.servicito.domains.logs;

import com.example.common.utils.Shell;
import com.example.auth.config.security.TokenService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RequestMapping("/admin/logs")
@Controller
public class LogsController {
    private final TokenService tokenService;

    public LogsController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("generate-nginx-report")
    private ResponseEntity generateNginxReport(@RequestParam(value = "date", defaultValue = "today") String date) throws InterruptedException, FileNotFoundException {
        try {
            String command = "";
            if ("today".equalsIgnoreCase(date))
                command = "goaccess /var/log/nginx/access.log -o report.html --log-format=COMBINED";
            else if ("yesterday".equalsIgnoreCase(date))
                command = "goaccess /var/log/nginx/access.log.1 -o report.html --log-format=COMBINED";
            Shell.exec(command);
        } catch (IOException e) {
            try {
                Shell.exec("sudo apt install goaccess");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        File file = new File("report.html");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + date + "-" + file.getName() + "\"")
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }


//    @GetMapping("/generate-pdf")
//    private ResponseEntity generatePdf() throws IOException, InterruptedException {
//        File file = ReportUtils.generatePdf("http://localhost:8080/api/v1/buildings?access_token=" + this.tokenService.getAccessToken(SecurityConfig.getCurrentUser().getUsername()));
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//        return ResponseEntity.ok()
//                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
//                .contentLength(file.length())
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .body(resource);
//    }

}
