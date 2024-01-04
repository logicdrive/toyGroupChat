from flask import Blueprint, request
from http import HTTPStatus

from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType

from . import S3Service
from .reqDtos.UploadFileReqDto import UploadFileReqDto
from .resDtos.UploadFileResDto import UploadFileResDto
from .reqDtos.RemoveFileReqDto import RemoveFileReqDto
from .resDtos.RemoveFileResDto import RemoveFileResDto


bp = Blueprint("s3", __name__, url_prefix="/s3")

# 주어진 DataUrl을 기반으로 파일을 S3에 업로드시키고, 관련 URL을 반환하기 위해서
@bp.route("/uploadFile", methods=("PUT",))
def uploadFile() -> UploadFileReqDto :
    try :

        uploadFileReqDto:UploadFileReqDto = UploadFileReqDto(request)
        CustomLogger.debug(CustomLoggerType.ENTER, "", "<uploadFileReqDto: {}>".format(uploadFileReqDto))

        uploadFileResDto:UploadFileResDto = S3Service.uploadFile(uploadFileReqDto)

        CustomLogger.debug(CustomLoggerType.EXIT, "", "<uploadFileResDto: {}>".format(uploadFileResDto))
        return (uploadFileResDto.json(), HTTPStatus.OK)

    except Exception as e :
        jsonData = request.get_json()
        dataUrl = jsonData["dataUrl"] or ""
        CustomLogger.error(e, "", "<dataUrlSize: {}>".format(len(dataUrl)))
        return ("", HTTPStatus.BAD_REQUEST)

# 주어진 경로에 있는 파일을 삭제시키기 위해서
@bp.route("/removeFile", methods=("PUT",))
def removeFile() -> RemoveFileReqDto :
    try :

        removeFileReqDto:RemoveFileReqDto = RemoveFileReqDto(request)
        CustomLogger.debug(CustomLoggerType.ENTER, "", "<removeFileReqDto: {}>".format(removeFileReqDto))

        removeFileResDto:RemoveFileResDto = S3Service.removeFile(removeFileReqDto)
        
        CustomLogger.debug(CustomLoggerType.EXIT, "", "<removeFileResDto: {}>".format(removeFileResDto))
        return (removeFileResDto.json(), HTTPStatus.OK)

    except Exception as e :
        jsonData = request.get_json()
        fileUrl = jsonData["fileUrl"] or ""
        CustomLogger.error(e, "", "<fileUrl: {}>".format(fileUrl))
        return ("", HTTPStatus.BAD_REQUEST)
