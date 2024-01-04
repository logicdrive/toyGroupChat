from flask import request as flaskRequest

class UploadFileReqDto:
    def __init__(self, request:flaskRequest) :
        self.__jsonData = request.get_json()
        self.__dataUrl:str = self.__jsonData["dataUrl"] or ""

    def __str__(self) :
        return "<UploadFileReqDto dataUrlSize: {}>".format(len(self.__dataUrl))


    @property
    def dataUrl(self) -> str :
        return self.__dataUrl