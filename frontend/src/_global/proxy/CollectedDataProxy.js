import axios from 'axios';

class CollectedDataProxy {
    static async joinedRooms(jwtTokenState) {
        console.log(`[EFFECT] joinedRooms`)
        
        const requestHeader = {headers: {Authorization: jwtTokenState.jwtToken.Authorization}};
        const response = await axios.get(`http://${window.location.host}/api/collectedData/rooms/joinedRooms`, requestHeader);
        
        console.log(response)
        return response.data.rooms
    }


    // 주어진 RoomId를 이용해서 등록된 유저들에 대한 정보를 얻기 위해서
    static async roomUsersByRoomId(roomId, jwtTokenState) {
        console.log(`[EFFECT] roomUsersByRoomId : <roomId:${roomId}>`)

        const requestHeader = {headers: {Authorization: jwtTokenState.jwtToken.Authorization}};
        const response = await axios.get(`http://${window.location.host}/api/collectedData/roomUsers/search/findAllByRoomId?roomId=${roomId}`, requestHeader);
        
        console.log(response)
        return response.data._embedded.roomUsers
    }


    // 따로 봤는지 체크를 하지 않고, 특정 그룹 채팅의 메세지들을 얻기 위해서
    static async messagesWithoutCheck(roomId, jwtTokenState) {
        console.log(`[EFFECT] messagesWithoutCheck : <roomId:${roomId}>`)

        const requestHeader = {headers: {Authorization: jwtTokenState.jwtToken.Authorization}};
        const response = await axios.get(`http://${window.location.host}/api/collectedData/messages/search/findAllByRoomId?roomId=${roomId}`, requestHeader);
        
        console.log(response)
        return response.data._embedded.messages
    }
}

export default CollectedDataProxy