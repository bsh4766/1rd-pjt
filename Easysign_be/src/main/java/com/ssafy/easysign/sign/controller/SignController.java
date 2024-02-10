package com.ssafy.easysign.sign.controller;

import com.ssafy.easysign.global.jpaEnum.Gubun;
import com.ssafy.easysign.sign.dto.response.CategoryResponse;
import com.ssafy.easysign.sign.dto.response.SignResponse2;
import com.ssafy.easysign.sign.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sign")
@RequiredArgsConstructor
@Slf4j
public class SignController {

    private final SignService signService;

    @GetMapping("/category")
    public ResponseEntity<List<CategoryResponse>> getCategoryResponseList() {
        List<CategoryResponse> CategoryResponse = signService.getCategoryList();
        log.info("categoryResponses : " + CategoryResponse);
        return new ResponseEntity<>(CategoryResponse, HttpStatus.OK);
    }

    @GetMapping("/jihwa")
    public ResponseEntity<List<SignResponse2>> getSignResponseListJihwa(
            @RequestParam(value = "categoryname", required = false) String categoryName) {
        return getSignResponseList(Gubun.jihwa, categoryName);
    }

    @GetMapping("/word")
    public ResponseEntity<List<SignResponse2>> getSignResponseListWord(
            @RequestParam(value = "categoryname", required = false) String categoryName) {
        return getSignResponseList(Gubun.word, categoryName);
    }

    @GetMapping("/sentence")
    public ResponseEntity<List<SignResponse2>> getSignResponseListSentence(@RequestParam(value = "categoryname", required = false) String categoryName) {
        return getSignResponseList(Gubun.sentence, categoryName);
    }

    private ResponseEntity<List<SignResponse2>> getSignResponseList(Gubun gubun, String categoryName) {
        if (categoryName != null) {
            try {
                List<SignResponse2> signResponses = signService.getSignResponseList(categoryName, gubun);
                log.info("signResponses : " + signResponses);
                return new ResponseEntity<>(signResponses, HttpStatus.OK);
            } catch (Exception e) {
                // 실패 시 400 Bad Request 반환
                log.error("Error retrieving sign responses: " + e.getMessage());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            // 실패 시 403 Forbidden 반환
            log.info("gubun : " + gubun + ", categoryId: " + categoryName);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}