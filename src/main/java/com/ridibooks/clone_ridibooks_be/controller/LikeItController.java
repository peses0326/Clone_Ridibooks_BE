package com.ridibooks.clone_ridibooks_be.controller;

import com.ridibooks.clone_ridibooks_be.security.UserDetailsImpl;
import com.ridibooks.clone_ridibooks_be.service.LikeItService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"3. LikeIt_댓글 좋아요api"}) // Swagger # 2. Comment 댓글
@RequiredArgsConstructor
@RestController
public class LikeItController {
    private final LikeItService likeItService;

    @PostMapping("/likeIt/{commentId}")
    public String LikeIt(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null){
            return likeItService.addLike(userDetails.getUser(), commentId);
        }else {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
    }
}
