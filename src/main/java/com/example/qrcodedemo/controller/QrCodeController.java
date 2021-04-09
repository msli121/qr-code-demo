package com.example.qrcodedemo.controller;

import com.example.qrcodedemo.utils.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Description
 * @Author msli
 * @Date 2021/04/09
 */

@RestController
@RequestMapping("/api/qr-code")
public class QrCodeController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    /**
     * 根据数据生成二维码
     */
    @PostMapping("/code")
    public void createQrCode(@RequestBody Map map) throws IOException {
        ServletOutputStream stream = null;
        String code = MapUtils.getString(map, "code", "empty");
//        String code = request.getParameter("code");
        try {
            stream = response.getOutputStream();
            //使用工具类生成二维码
            QRCodeUtil.encode(code, stream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    /**
     * 根据数据生成带有logo二维码
     */
    @PostMapping(value = "/code-logo")
    public void createQrCodeWithLogo(@RequestBody Map map) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            String logoPath = "D://logo.png";
            //使用工具类生成二维码
//            String code = request.getParameter("code");
            String code = MapUtils.getString(map, "code", "empty");
            QRCodeUtil.encode(code, logoPath, stream, true);
        } catch (Exception e) {
            e.getStackTrace();

        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

}
