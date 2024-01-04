import urllib.request
import uuid

# 주어진 DataUrl을 파일로 변환해서 출력하고, 관련 경로를 반환시키기 위해서
def writeFileByUsingDataUrl(dataUrl:str, extractPath:str) :
    dataUrlRes = urllib.request.urlopen(dataUrl)

    fileName = str(uuid.uuid4())
    fileExt = dataUrl.split(",")[0].split(";")[0].split("/")[1]
    filePath = extractPath + "/" + f"{fileName}.{fileExt}"

    with open(filePath, "wb") as f:
        f.write(dataUrlRes.file.read())
    
    return filePath

dataUrl = "".join(open("ProfileImage1_Base64.txt", "r").readlines())
print(writeFileByUsingDataUrl(dataUrl, "."))
