from flask import Blueprint, request
from http import HTTPStatus

from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType

from . import SanityCheckService
from .reqDtos.LogsReqDto import LogsReqDto
from .resDtos.LogsResDto import LogsResDto
from .reqDtos.EchoWithJsonReqDto import EchoWithJsonReqDto
from .resDtos.EchoWithJsonResDto import EchoWithJsonResDto

bp = Blueprint("sanityCheck", __name__, url_prefix="/sanityCheck")

@bp.route("/", methods=("GET",))
def sanityCheck() -> str :
    CustomLogger.debug(CustomLoggerType.ENTER_EXIT)
    return ("", HTTPStatus.OK)

@bp.route("/logs", methods=("GET",))
def logs() -> LogsResDto :
    try :

        logsReqDto:LogsReqDto = LogsReqDto(request)


        CustomLogger.debug(CustomLoggerType.ENTER)

        logsResDto:LogsResDto = SanityCheckService.logs(logsReqDto)

        CustomLogger.debug(CustomLoggerType.EXIT, "", "<logsSize: {}>".format(len(logsResDto.logs)))
        return (logsResDto.json(), HTTPStatus.OK)

    except Exception as e :
        CustomLogger.error(e, "", "<lineLength: {}>".format(request.args.get("lineLength") or "None"))
        return ("", HTTPStatus.BAD_REQUEST)

# 정상적인 에러 로그 출력 여부를 테스트해보기 위해서
@bp.route("/divByZeroCheck", methods=("GET",))
def divByZeroCheck() -> str :
    try :

        returnNum:int = 1/0
        return (returnNum, HTTPStatus.OK)

    except Exception as e :
        CustomLogger.error(e, "Div By Zero Check Message", "<returnNum: {}>".format("Undefined"))
        return ("", HTTPStatus.INTERNAL_SERVER_ERROR)

# JSON 송수신 여부를 간편하게 테스트해보기 위해서
@bp.route("/echoWithJson", methods=("PUT",))
def echoWithJson() -> EchoWithJsonResDto :
    try :

        echoWithJsonReqDto:EchoWithJsonReqDto = EchoWithJsonReqDto(request)
        CustomLogger.debug(CustomLoggerType.ENTER_EXIT, "", "<echoWithJsonReqDto: {}>".format(echoWithJsonReqDto))
        return (EchoWithJsonResDto(echoWithJsonReqDto.message).json(), HTTPStatus.OK)

    except Exception as e :
        CustomLogger.error(e, "", "<message: {}>".format(request.args.get("message") or "None"))
        return ("", HTTPStatus.BAD_REQUEST)
