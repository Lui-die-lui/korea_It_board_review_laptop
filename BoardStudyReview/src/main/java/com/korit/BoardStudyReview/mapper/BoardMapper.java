package com.korit.BoardStudyReview.mapper;

import com.korit.BoardStudyReview.entity.Board;
import com.korit.BoardStudyReview.entity.OAuth2User;
import com.korit.BoardStudyReview.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    int addBoard(Board board); // 결과값 반환만 해주기
    Optional<Board> getBoardByBoardId(Integer boardId);

    //다건 조회용 List
    List<Board> getBoardList();
    }

