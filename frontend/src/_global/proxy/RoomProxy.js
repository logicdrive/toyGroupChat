import axios from 'axios';

class RoomProxy {
    static async createRoom(name, jwtTokenState) {
        console.log(`[EFFECT] createRoom : <name:${name}>`)
        
        const requestHeader = {headers: {Authorization: jwtTokenState.jwtToken.Authorization}};
        const reqDto = {
            "name": name
        }
        const response = await axios.put(`http://${window.location.host}/api/room/rooms/createRoom`, reqDto, requestHeader);
        
        console.log(response)
        return response.data.id;
    }

    // 유저를 해당 채팅방에 추가하기 위해서
    static async addRoomUser(sharedCode, jwtTokenState) {
        console.log(`[EFFECT] createRoom : <sharedCode:${sharedCode}>`);
        
        const requestHeader = {headers: {Authorization: jwtTokenState.jwtToken.Authorization}};
        const reqDto = {
            "sharedCode": sharedCode
        }
        const response = await axios.put(`http://${window.location.host}/api/room/rooms/addRoomUser`, reqDto, requestHeader);
        
        console.log(response);
        return response.data.id;
    }
}

export default RoomProxy