package com.korit.BoardStudyReview.repositroy;

import com.korit.BoardStudyReview.entity.Board;
import com.korit.BoardStudyReview.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {
    @Autowired
    private BoardMapper boardMapper;

    public int addBoard(Board board) {
        return boardMapper.addBoard(board);
    }

    public Optional<Board> getBoardByBoardId(Integer boardId) {
        return boardMapper.getBoardByBoardId(boardId);
    }

    public List<Board> getBoardList() {
        return boardMapper.getBoardList();
    }
}
