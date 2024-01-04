from flask import request as flaskRequest

class RemoveFileReqDto:
    def __init__(self, request:flaskRequest) :
        self.__jsonData = request.get_json()
        self.__fileUrl:str = self.__jsonData["fileUrl"] or ""

    def __str__(self) :
        return "<RemoveFileReqDto fileUrl: {}>".format(self.__fileUrl)


    @property
    def fileUrl(self) -> str :
        return self.__fileUrl
