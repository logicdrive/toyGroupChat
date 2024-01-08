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
}

export default RoomProxy