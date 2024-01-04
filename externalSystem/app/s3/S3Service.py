from .._global.workdir.WorkDirManager import WorkDirManager

from .reqDtos.UploadFileReqDto import UploadFileReqDto
from .resDtos.UploadFileResDto import UploadFileResDto
from .reqDtos.RemoveFileReqDto import RemoveFileReqDto
from .resDtos.RemoveFileResDto import RemoveFileResDto

from .services.S3ProxyService import uploadToPublicS3, deleteToPublic3

# 주어진 DataUrl을 기반으로 파일을 S3에 업로드시키고, 관련 URL을 반환하기 위해서
def uploadFile(uploadFileReqDto:UploadFileReqDto) -> UploadFileResDto :
    uploadYoutubeVideoResDto:UploadFileResDto = UploadFileResDto()

    # with WorkDirManager() as path:
    #     videoMetadataDto:VideoMetadataDto = downloadCuttedYoutubeVideo(
    #         uploadedYoutubVideoReqDto.youtubeUrl, path(), "video.mp4", "thumbnail.jpg",
    #         uploadedYoutubVideoReqDto.cuttedStartSecond, uploadedYoutubVideoReqDto.cuttedEndSecond
    #     )

    #     uploadYoutubeVideoResDto.videoTitle = videoMetadataDto.title
    #     uploadYoutubeVideoResDto.uploadedUrl = uploadToPublicS3(videoMetadataDto.outputVideoPath)
    #     uploadYoutubeVideoResDto.thumbnailUrl = uploadToPublicS3(videoMetadataDto.outputThumbnailPath)
    
    uploadYoutubeVideoResDto.fileUrl = "http://testurl.png"
    return uploadYoutubeVideoResDto

# 주어진 경로에 있는 파일을 삭제시키기 위해서
def removeFile(removeFileReqDto:RemoveFileReqDto) -> RemoveFileResDto :
    # deleteToPublic3(removeFileVideoReqDto.fileUrl.split("/")[-1])
    return RemoveFileResDto(removeFileReqDto.fileUrl)
