package minionz.backend.board.qa_board;

import lombok.RequiredArgsConstructor;
import minionz.backend.board.qa_board.model.request.CreateQaBoardRequest;
import minionz.backend.board.qa_board.model.response.CreateQaBoardResponse;
import minionz.backend.board.qa_board.model.response.GetQaBoardResponse;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.utils.CloudFileUpload;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qaboard")
public class QaBoardController {
    private final QaBoardService qaBoardService;
    private final CloudFileUpload cloudFileUpload;

    // 게시글 생성
    @PostMapping("/write")
    public BaseResponse<CreateQaBoardResponse> createQaBoard(
            @RequestParam Long workspaceId,
            @RequestPart(name = "request") CreateQaBoardRequest request,
            @RequestPart(name = "files") MultipartFile[] files) throws BaseException {
        List<String> fileNames = cloudFileUpload.multipleUpload(files);
        CreateQaBoardResponse response = qaBoardService.create(fileNames, request,workspaceId);
        return new BaseResponse<>(BaseResponseStatus.QABOARD_CREATE_SUCCESS, response);
    }
    //게시글 하나 조회
    @GetMapping("/search")
    public BaseResponse<GetQaBoardResponse>search(
            @RequestParam Long workspaceId,
            @RequestParam Long boardId) throws BaseException {
        GetQaBoardResponse response = qaBoardService.read(boardId,workspaceId);
        return new BaseResponse<>(BaseResponseStatus.QABOARD_SEARCH_SUCCESS, response);
    }
    //게시글 전체 조회
    @GetMapping("/search-all")
    public BaseResponse<Page<GetQaBoardResponse>> readAllQaBoard(
            @RequestParam Long workspaceId,
            @RequestParam int page,
            @RequestParam int size) throws BaseException {
        Page<GetQaBoardResponse> response = qaBoardService.readAll(page, size,workspaceId);
        return new BaseResponse<>(BaseResponseStatus.QABOARD_SEARCH_SUCCESS, response);
    }
    //단어별 조회
    @GetMapping("/search-keyword")
    public BaseResponse<Page<GetQaBoardResponse>> readKeyword(
            @RequestParam Long workspaceId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String keyword) throws BaseException {
        Page<GetQaBoardResponse> response = qaBoardService.readKeyword(keyword, page, size,workspaceId);
        return new BaseResponse<>(BaseResponseStatus.QABOARD_SEARCH_SUCCESS, response);
    }
    }

