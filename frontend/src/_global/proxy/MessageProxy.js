import axios from 'axios';

class MessageProxy {
    // 파일을 포함하여 새로운 메세지를 추가하기 위해서
    static async createMessage(roomId, content, fileName, fileDataUrl, jwtTokenState) {
        console.log(`[EFFECT] createMessage : <roomId:${roomId}, content:${content}, fileName:${fileName}, fileDataUrlLength:${fileDataUrl.length}>`)
        
        const requestHeader = {headers: {Authorization: jwtTokenState.jwtToken.Authorization}};
        const reqDto = {
            "roomId": roomId,
            "content": content,
            "fileName": fileName,
            "fileDataUrl": fileDataUrl
        }
        const response = await axios.put(`http://${window.location.host}/api/message/messages/createMessage`, reqDto, requestHeader);
        
        console.log(response)
        return response.data.id;
    }
}

export default MessageProxy;