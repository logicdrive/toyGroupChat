import urllib

from .._global.workdir.WorkDirManager import WorkDirManager
from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType

from .reqDtos.UploadFileReqDto import UploadFileReqDto
from .resDtos.UploadFileResDto import UploadFileResDto
from .reqDtos.RemoveFileReqDto import RemoveFileReqDto
from .resDtos.RemoveFileResDto import RemoveFileResDto

from .services.S3ProxyService import uploadToPublicS3, deleteToPublic3

# 주어진 DataUrl을 기반으로 파일을 S3에 업로드시키고, 관련 URL을 반환하기 위해서
def uploadFile(uploadFileReqDto:UploadFileReqDto) -> UploadFileResDto :
    uploadFileResDto:UploadFileResDto = UploadFileResDto()

    with WorkDirManager() as path:
        uploadFilePath:str = __writeFileByUsingDataUrl(uploadFileReqDto.dataUrl, "uploadFile", path())
        uploadFileResDto.fileUrl = uploadToPublicS3(uploadFilePath)

    return uploadFileResDto

# 주어진 DataUrl을 파일로 변환해서 출력하고, 관련 경로를 반환시키기 위해서
def __writeFileByUsingDataUrl(dataUrl:str, fileName:str, extractPath:str) :
    dataUrlRes = urllib.request.urlopen(dataUrl)

    fileExt = dataUrl.split(",")[0].split(";")[0].split("/")[1]
    filePath = extractPath + f"{fileName}.{fileExt}"

    CustomLogger.debug(CustomLoggerType.EFFECT, "Try to write file", "<filePath: {}>".format(filePath))
    with open(filePath, "wb") as f:
        f.write(dataUrlRes.file.read())
    
    return filePath

# 주어진 경로에 있는 파일을 삭제시키기 위해서
def removeFile(removeFileReqDto:RemoveFileReqDto) -> RemoveFileResDto :
    deleteToPublic3(removeFileReqDto.fileUrl.split("/")[-1])
    return RemoveFileResDto(removeFileReqDto.fileUrl)
